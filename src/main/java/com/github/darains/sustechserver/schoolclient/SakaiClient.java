package com.github.darains.sustechserver.schoolcas;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;


@Component("sakaiClient")
@Slf4j
public class SakaiClient{
    @Value("${school.sakai.login.url}")
    @Setter
    private String loginUrl;
    @Value("${school.cas.hosturl}")
    @Setter
    private String hostUrl;
    
    @SneakyThrows
    public boolean checkPasswordBySakai(String userid, String password){
        log.info("check password: {}:{}",userid,password);
    
        Connection.Response r1=Jsoup.connect(loginUrl)
            .header("http.protocol.handle-rediirects","false")
            .method(Connection.Method.GET).execute();
        String lt=r1.parse().select("#fm1 > section.row.btn-row > input[type=\"hidden\"]:nth-child(1)").get(0).val();
        String execution=r1.parse().select("#fm1 > section.row.btn-row > input[type=\"hidden\"]:nth-child(2)").get(0).val();
        String action=r1.parse().select("#fm1").get(0).attr("action");
        log.debug("lt:{}",lt);
        log.debug("excution:{}",execution);
//        log.info("action:{}",action);
        String jsession=action.substring(action.indexOf("jsessionid="),action.indexOf("?service"));
//        log.info("jsession: {}",jsession);
    
        Connection.Response r2=Jsoup.connect(hostUrl+action)
            .method(Connection.Method.POST)
            .cookie("Cookie",jsession)
            .followRedirects(false)
            .data("userid",userid)
            .data("password",password)
            .data("lt",lt)
            .data("execution",execution)
            .data("_eventId","submit")
            .data("submit","LOGIN")
            .execute();

        log.debug("r2.headers:\n{}",r2.headers());
        log.debug("r2.cookies:\n{}",r2.cookies());
        log.debug("r2.location:\n{}\n\n",r2.parse().location());
        
        Connection.Response r3=Jsoup.connect(loginUrl)
            .followRedirects(false)
            .cookies(r2.cookies())
            .method(Connection.Method.GET)
            .execute();

        log.debug("r3.headers:\n{}",r3.headers());
        log.debug("r3 cookies:\n{}",r3.cookies());
        log.debug("r3.location\n{}\n\n",r3.headers().get("Location"));
        
        if (r3.header("Location")==null){
            return false;
        }
        
//        Connection.Response r4=Jsoup.connect(r3.headers().get("Location"))
//            .followRedirects(false)
//            .cookies(r3.cookies())
//            .method(Connection.Method.GET)
//            .execute();
//        log.info("r4.headers:\n{}",r4.headers());
//        log.info("r4 cookies:\n{}",r4.cookies());
//        log.info("r4.location:\n{}\n\n",r4.headers().get("Location"));
////        log.info("r4.location2\n{}",r4.parse().);
////        log.info("r4.body:\n{}",r4.parse().data());
//
//        String cookie=r4.cookies().toString();
//        cookie=cookie.substring(1,cookie.length()-1);
//        Connection.Response r5=Jsoup.connect(r4.headers().get("Location"))
//            .method(Connection.Method.GET)
//            .followRedirects(false)
//            .cookies(r4.cookies())
//            .header("Cookie",cookie)
//            .execute();
//        log.info("r5.headers:\n{}",r5.headers());
//
//        log.info("r5 cookies:\n{}",r5.cookies());
//        log.info("r5.location:\n{}\n\n",r5.headers().get("Location"));
//
//        Connection.Response r6=Jsoup.connect(r5.headers().get("Location"))
//            .method(Connection.Method.GET)
//            .followRedirects(false)
//            .header("Cookie",cookie)
//            .execute();
//
//        log.info("r6:\n{}\n\n",r6.parse().data());

        return true;
    }
    
//    @SneakyThrows
    public void htmlunitCheckPassword(String userid,String password){
    
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
//        LoggerFactory.getLogger(SakaiClient.class)
    
    
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage("http://student.sustc.edu.cn/");
            webClient.getOptions().setCssEnabled(false);//忽略Css
            webClient.getOptions().setThrowExceptionOnScriptError(false);//如果JavaScript有错误是否抛出，这里的抛出指的是下面获取到的ScriptResult对象为空
//            webClient.getOptions().
            
            HtmlElement username = page.getFirstByXPath("//*[@id=\"userid\"]");
            username.click();
            username.type(userid);
    
            HtmlElement pass=page.getFirstByXPath("//*[@id=\"password\"]");
            pass.click();
            pass.type(password);
    
            HtmlSubmitInput elmt=page.getFirstByXPath("//*[@id=\"fm1\"]/section[3]/input[4]");
            HtmlPage page2=null;
            try{
                page2 = elmt.click();
                webClient.getOptions().setJavaScriptEnabled(false);
    
            } catch(IOException e){
                e.printStackTrace();
            }
    
            log.info("\n\n{}\n\n",page2.getPage().getTitleText());
            log.info("\n\n{}\n\n",page2.getPage().getBody().asText());
            
        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        SakaiClient client=new SakaiClient();
        client.setLoginUrl("http://weblogin.sustc.edu.cn/cas/login?service=http://sakai.sustc.edu.cn/portal/login");
        client.setHostUrl("http://weblogin.sustc.edu.cn");
        
    }
}
