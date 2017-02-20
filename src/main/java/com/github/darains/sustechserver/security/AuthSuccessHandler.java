package com.github.darains.sustechserver.security;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthSuccessHandler implements AuthenticationSuccessHandler{
    
    public static final String SECRET_KEY = "mm120Qx*@S7q";
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException{
        response.setStatus(HttpStatus.OK.value());
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode objectNode = factory.objectNode();
        objectNode.set("resultCode", factory.textNode("200"));
        objectNode.set("status", factory.textNode("success"));
        objectNode.set("token", factory.textNode(createToken(authentication)));
        PrintWriter out = response.getWriter();
        out.write(objectNode.toString());
        out.close();
    }
    
    private String createToken(Authentication authentication) {
        return Jwts.builder()
            .setSubject(authentication.getName())
            .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, SECRET_KEY)
            .compact();
    }
}