package com.github.darains.sustech.student.server.casclient;

import com.github.darains.sustech.student.server.dto.course.Course;
import com.github.darains.sustech.student.server.dto.grade.Grade;
import com.github.darains.sustech.student.server.dto.grade.TermGrade;
import com.github.darains.sustech.student.server.dto.grade.StudentAllTermGrade;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public final class EducationalSystemClient implements CasClient{
    
    static final String URL = "http://jwxt.sustc.edu.cn/jsxsd/";
    
    static final String LOGIN_URL="https://cas.sustc.edu.cn/cas/login?service=http%3A%2F%2Fjwxt.sustc.edu.cn%2Fjsxsd%2F";
    
    static final String COURSE_TABLE_URL="http://jwxt.sustc.edu.cn/jsxsd/xskb/xskb_list.do";
    
    static final String QUERY_GRADE_URL="http://jwxt.sustc.edu.cn/jsxsd/kscj/cjcx_list";
    
    @Value("#{'${school.edusystem.termlist}'.split(',')}")
    public List<String> termNameList;
    
    @Getter
    private String cookie;
    
    
    @Override
    @SneakyThrows
    public String casLogin(String username,String password){
        
        Connection.Response r1=Jsoup.connect(LOGIN_URL).execute();
    
        String lt=r1.parse().select("#fm1 > section.row.btn-row > input[type=\"hidden\"]:nth-child(1)").first().val();
        String execution=r1.parse().select("#fm1 > section.row.btn-row > input[type=\"hidden\"]:nth-child(2)").first().val();
    
        Connection.Response r2=Jsoup.connect(LOGIN_URL).method(Connection.Method.POST)
            .header("Referer","https://cas.sustc.edu.cn/cas/login?service=http%3A%2F%2Fjwxt.sustc.edu.cn%2Fjsxsd%2F")
            .data("username",username)
            .data("password",password)
            .data("lt",lt)
            .data("execution",execution)
            .data("_eventId","submit")
            .data("submit","登录")
            .followRedirects(false)
            .ignoreHttpErrors(true)
            .execute();
    
        
        Connection.Response r31=Jsoup.connect(r2.header("Location"))
            .timeout(3000)
            .execute();
    
        cookie=r31.cookies().toString();
        cookie=cookie.substring(1,cookie.length()-1);
        
        return cookie;
    }
    
    
    /*
     * 传入cookie，此方法将爬取课程信息中的所有课程的列表
     */
    public Set<Course> crawlCourseTable(String cookie){
        Set<Course> set=new HashSet<>();
        try{
            Connection.Response r3=Jsoup.connect(COURSE_TABLE_URL)
                .header("Cookie",cookie)
                .execute();
            for (int i=1;i<=6;i++){
                for (int w = 1; w <= 7; w++){
                    String t1="#kbtable > tbody > tr:nth-child("+(i+1)+") > td:nth-child("+(w+1)+")";
                    Element e=r3.parse().select(t1).first();
                    String s=e.child(3).html().trim().replace("&nbsp;","").trim();
                    if (StringUtils.isNotBlank(s)){
                        try{
                            set.add(resolveHtmlToCourse(w, i, s));
                        }
                        catch(Exception e1){
                            System.out.println(s);
                            e1.printStackTrace();
                        }
                    }
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return set;
    }
    
    private Course resolveHtmlToCourse(int w,int i,String s){
        Course course=new Course();
        s=s.replace("<br>\n","");
        s=s.replace("<br>","");
        String[] ss=s.split("\n");
        try{
            course.setCourseName(ss[0]);
            course.setTeachers(ss[1].substring(ss[1].indexOf("\">")+2, ss[1].indexOf("</")));
            course.setClassRoom(ss[ss.length-1].substring(ss[ss.length-1].indexOf("\">")+2, ss[ss.length-1].indexOf("</")));
            course.setWeekly(w);
            course.setSection(i);
        }
        catch(Exception e){
            System.out.println(Arrays.toString(ss));
            e.printStackTrace();
        }
        return course;
    }
    
    /*
     * @param 需传入cookie信息
     * @return 返回学生的所有学期的成绩,按学期排列
     */
    public StudentAllTermGrade crawlStudentAllTermGrades(String cookie){
        StudentAllTermGrade studentGrades=new StudentAllTermGrade();
        for(String name:termNameList){
            try{
                TermGrade t = crawlTermGrade(cookie, name);
                if(t.getGradeList().size()>0) {
                    studentGrades.getTermGradeList().add(t);
                }
            }
            catch(Exception e){
                //log.warn(e.getMessage());
            }
           
        }
        return studentGrades;
    }
    
    /*
     * @return 返回学生的所有学期的成绩,按学期排列
     */
    public StudentAllTermGrade crawlStudentAllTermGrades(){
        return crawlStudentAllTermGrades(this.cookie);
    }
    
    @SneakyThrows
    private TermGrade crawlTermGrade(String cookie,String termName){
        TermGrade termGrade=new TermGrade();
        
        termGrade.setCourseTerm(termName);
        
        Connection.Response r1=Jsoup.connect(QUERY_GRADE_URL)
            .method(Connection.Method.POST)
            .header("Cookie",cookie)
            .data("kksj",termName)
            .data("kcxz","")
            .data("kcmc","")
            .data("xsfs","all")
            .execute();
        
        Element tbody=r1.parse().select("#dataList > tbody").first();
        
        int len=tbody.children().size();
        
        for(int i=1;i<len;i++){
            Grade g=resolveGradeFromElement(tbody.child(i));
            termGrade.getGradeList().add(g);
        }
        log.debug("term grade: {}",termGrade);
        return termGrade;
    }
    
    private Grade resolveGradeFromElement(Element e){
        Grade grade=new Grade();
        grade.setCourseid(e.child(2).html())
            .setCourseName(e.child(3).html())
            .setGrade(e.child(4).child(0).html().trim())
            .setCredit(Double.parseDouble(e.child(5).html()))
            .setCourseAttribute(e.child(8).html().trim());
//        log.debug("grade:{}",grade);
        return grade;
    }
    
    
    private static void CourseGradeTest(){
        EducationalSystemClient client=new EducationalSystemClient();
        client.casLogin("11310388","dengakak");
    
        client.termNameList= new ArrayList<>();
        client.termNameList.add("2015-2016-2");
        client.termNameList.add("2016-2017-1");
    
        StudentAllTermGrade s=client.crawlStudentAllTermGrades(client.cookie);

        System.out.println(s);
    }
    
    
}
