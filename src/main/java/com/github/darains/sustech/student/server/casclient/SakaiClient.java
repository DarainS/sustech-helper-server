package com.github.darains.sustech.student.server.casclient;

import com.github.darains.sustech.student.server.dto.homework.CourseHomework;
import com.github.darains.sustech.student.server.dto.homework.Homework;
import javaslang.Tuple;
import javaslang.Tuple2;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component(value = "sakaiClient")
@Slf4j
public class SakaiClient implements CasClient{

    //@Value("${school.sakai.login.url}")
    private String loginUrl = "https://cas.sustc.edu.cn/cas/login?service=http%3A%2F%2Fsakai.sustc.edu.cn%2Fportal%2Flogin";

    //@Value("${school.cas.hosturl}")
    private String hostUrl = "http://weblogin.sustc.edu.cn/";
    
    @Setter
    private String mainPageUrl = "http://sakai.sustc.edu.cn/portal";
    
    @Getter
    private String cookie="";
    
    private static final Pattern siteUrlPattern=Pattern.compile("href=\"http://sakai.sustc.edu.cn/portal/site/(.{20,40}) title=\"(.{0,340})\">");
    
    private static final Pattern homeworkUrlPattern=Pattern.compile("<a class=\"toolMenuLink \" href=(.{80,280}) title=\"在线发布、提交和批改作业\"");
    
    @Override
    @SneakyThrows
    public String casLogin(String username,String password){
        
        Connection.Response r1=Jsoup.connect(loginUrl).execute();
        
        String lt=r1.parse().select("#fm1 > section.row.btn-row > input[type=\"hidden\"]:nth-child(1)").first().val();
        String execution=r1.parse().select("#fm1 > section.row.btn-row > input[type=\"hidden\"]:nth-child(2)").first().val();
        
        Connection.Response r2=Jsoup.connect(loginUrl).method(Connection.Method.POST)
            .header("Referer","https://cas.sustc.edu.cn/cas/login?service=http%3A%2F%2Fjwxt.sustc.edu.cn%2Fjsxsd%2F")
            .data("username",username)
            .data("password",password)
            .data("lt",lt)
            .data("execution",execution)
            .data("_eventId","submit")
            .data("submit","登录")
            .followRedirects(false)
            .ignoreHttpErrors(true)
            .timeout(5000)
            .execute();
        
        //r3
 
        Connection.Response r3=Jsoup.connect(r2.header("Location"))
            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36")
            .header("Upgrade-Insecure-Requests","1")
            .header("Host","sakai.sustc.edu.cn")
            .header("Accept-Language","en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4")
            .followRedirects(false)
            .execute();
        
        //r4
        Connection.Response r4=Jsoup.connect(r3.header("Location"))
            .cookies(r3.cookies())
            .execute();
    
        cookie=r3.cookies().toString();
        cookie=cookie.substring(1,cookie.length()-1);
        return cookie;
    }
    
    
    
    private Set<Tuple2> resolveSiteUrls(){
        return resolveSiteUrls(this.cookie);
    }
    
    @SneakyThrows
    private Set<Tuple2> resolveSiteUrls(String cookie){
        Set<Tuple2> ls=new LinkedHashSet<>();
        Connection.Response mainPage=Jsoup.connect(mainPageUrl)
            .header("Cookie",cookie)
            .header("Accept-Language","en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4")
            .method(Connection.Method.GET).execute();
    
        
        String afterDecoding = StringEscapeUtils.unescapeHtml4(mainPage.body());

        
        Matcher matcher2 = siteUrlPattern.matcher(afterDecoding);
        
        while (matcher2.find()){
            String s=matcher2.group();
            String[] ss;
            ss = s.split("\"");
            Tuple2<String, String> tuple = Tuple.of(ss[3], ss[1]);
            ls.add(tuple);
        }
        
        return ls;
    }
    
    @SneakyThrows
    private List<Homework> resolveHomeworks(String url){
        return resolveHomeworks(url,this.cookie);
    }
    
    @SneakyThrows
    private List<Homework> resolveHomeworks(String url,String cookie){
        
        Connection.Response r1=Jsoup.connect(url)
            .header("Cookie",cookie)
            .header("Accept-Language","en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4")
            .execute();
    
        String data=r1.body();
        
        String url2=resolveHomeworkUrl(data);
        
        
        List<Homework> ls=new LinkedList<>();
        
        
        if (StringUtils.isBlank(url2)){
            return ls;
        }
        
        
//        log.info("url1: {}",url);
//        log.info("url2: {}",url2);
        
        Connection.Response homeworkListPage=Jsoup.connect(url2)
            .header("Cookie",cookie)
            .execute();
    
//        log.info("reponse's ulr:{}",homeworkListPage.url().toString());
        
        Pattern pattern=Pattern.compile("href=\"http://sakai.sustc.edu.cn/portal/(.{20,100})/\\?panel=Main\"");
        
        Matcher matcher=pattern.matcher(homeworkListPage.body());
        
        if (!matcher.find()){
            return ls;
        }
        
        String url3=matcher.group();
        url3=url3.substring(6,url3.length()-1);
    
        Connection.Response homeworkList2=Jsoup.connect(url3)
            .header("Cookie",cookie)
            .execute();
        
        Document document=homeworkList2.parse();
        
        for (int i=2;;i++){
            
            String q="body > div > form > table > tbody > tr:nth-child("+(i)+")";
            
            //log.info("query string: {}",q);
            
            Element e=document.select(q).first();
            
            if (e==null){
                //log.info("{}\nchild {} is null",url2,i);
                break;
            }
            
            //log.info(e.toString());
        
            Homework h=resolveHomeworkNode(e);
            
            ls.add(h);
        }
        return ls;
    }
    
    private Homework resolveHomeworkNode(Element element){
        
        Homework h=new Homework();
        
        h.setHomeworkName(element.child(1).text());
        
        h.setUrl(element.child(1).child(0).child(1).attr("href"));
        
        h.setBeginDate(stringToDate(element.child(3).text()));
        
        h.setEndDate(stringToDate(element.child(4).text()));
        
        h.setStat(element.child(2).text());
        
        return h;
    }
    
    private String resolveHomeworkUrl(String body){
        Matcher matcher=homeworkUrlPattern.matcher(body);
        if(!matcher.find()){
            return null;
        }
        String ur=null;
        ur=matcher.group();
        Pattern pattern=Pattern.compile("href=\"(.*?)\"");
        Matcher matcher1=pattern.matcher(ur);
        matcher1.find();
        ur=matcher1.group().substring(6,matcher1.group().length()-1);
        return ur;
    }
    
    private LocalDateTime stringToDate(String s){
        String[] ss=s.split(" ");
        String s1=ss[0];
//        s1=s1.replace("上午","AM");
//        s1=s1.replace("下午","PM");
        LocalDate date= LocalDate.parse(s1, DateTimeFormatter.ofPattern("yyyy-M-d"));
        String s2=ss[1];
        int plusHour=0;
        if (s2.contains("上午")){
            s2=s2.replace("上午","");
        }
        if (s2.contains("下午")){
            s2=s2.replace("下午","");
            plusHour=12;
        }
        LocalTime time=LocalTime.parse(s2,DateTimeFormatter.ofPattern("k:mm"));
        
        time=time.plusHours(plusHour);
        
        LocalDateTime dateTime=LocalDateTime.of(date,time);
        
        return dateTime;
    }
    
    
    private void dateStringTest(){
        String s="2015-3-5 下午11:00";
        String[] ss=s.split(" ");
        String s1=ss[0];
//        s1=s1.replace("上午","AM");
//        s1=s1.replace("下午","PM");
        System.out.println(s1);
        LocalDate date= LocalDate.parse(s1, DateTimeFormatter.ofPattern("yyyy-M-d"));
        String s2=ss[1];
        int plusHour=0;
        if (s2.contains("上午")){
            s2=s2.replace("上午","");
        }
        if (s2.contains("下午")){
            s2=s2.replace("下午","");
            plusHour=12;
        }
        LocalTime time=LocalTime.parse(s2,DateTimeFormatter.ofPattern("k:mm"));
        time=time.plusHours(plusHour);
    
        LocalDateTime dateTime=LocalDateTime.of(date,time);
        System.out.println(date.toString());
        System.out.println(time.toString());
        System.out.println(dateTime);
    }
    
    
    
    /**
     * @return 返回学生在sakai站点加入的所有站点的作业,按课程排列
     */
    public Set<CourseHomework> allHomeworks(){
    
        Set<CourseHomework> result=new HashSet<>();
        
        if (StringUtils.isBlank(cookie)){
            return result;
        }
    
        Set<Tuple2> set= resolveSiteUrls();
        
        
        for (Tuple2 s1:set){
            CourseHomework c=new CourseHomework();
            List<Homework> ls=resolveHomeworks((String)s1._2());
            c.setCourseName((String)s1._1());
            c.setCourseHomework(ls);
            if (c.getCourseHomework().size()>0){
                result.add(c);
            }
        }
        
        return result;
        
    }
    
    private Set<CourseHomework> allHomeworks(String userid,String password){
        this.casLogin(userid,password);
        return allHomeworks();
    }
    
}
