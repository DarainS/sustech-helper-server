package com.github.darains.sustechserver.schoolcas;

import com.github.darains.sustechserver.entity.UserInfo;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("studentInfoClient")
public class StudentInfoClient{
    
    private Map<String,?> cookies;
    
    private String ticket;
    
    @SneakyThrows
    public String checkPassword(String userid,String password){
        Connection.Response r1=Jsoup.connect("https://cas.sustc.edu.cn/cas/login?service=https://student.sustc.edu.cn/")
            .followRedirects(false)
            .execute();
        String lt=(r1.parse().select("#fm1 > section.row.btn-row > input[type=\"hidden\"]:nth-child(1)").get(0).val());
        String execution=(r1.parse().select("#fm1 > section.row.btn-row > input[type=\"hidden\"]:nth-child(2)").get(0).val());
        
        Connection.Response r2=Jsoup.connect("https://cas.sustc.edu.cn/cas/login?service=https://student.sustc.edu.cn/")
            .method(Connection.Method.POST)
            .data("lt",lt)
            .data("execution",execution)
            .data("username",userid)
            .data("password",password)
            .data("_eventId","submit")
            .data("submit","LOGIN")
            .execute();
        String url=r2.url().toString();
        if (r2.url().toString().startsWith("https://student.sustc.edu.cn/?ticket=")){
            ticket=url.substring(url.indexOf("ticket"));
//            System.out.println(generateUserInfo(ticket));
            return ticket;
        }
        return "";
    }
    
    
    @SneakyThrows
    public UserInfo generateUserInfo(String ticket){
        UserInfo userInfo = new UserInfo();
        Connection.Response r1=Jsoup.connect("https://student.sustc.edu.cn/cas/login?"+ticket+"&service=https://student.sustc.edu.cn")
            .execute();
        String info=(new Gson().fromJson(r1.body(),Map.class).get("content")).toString();
    
        JSONObject j = new JSONObject(info);
    
        userInfo
            .setAccessToken(j.get("access_token").toString())
            .setUserid(j.get("sid").toString())
            .setName(j.get("name").toString());
        
        return userInfo;
    }
    
    public static void main(String[] args){
        StudentInfoClient casClient=new StudentInfoClient();
        casClient.checkPassword("11310388","dengakak");
    }
}
