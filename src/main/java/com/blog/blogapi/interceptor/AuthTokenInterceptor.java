package com.blog.blogapi.interceptor;


import com.blog.blogapi.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import io.jsonwebtoken.JwtException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Log4j2
public class AuthTokenInterceptor implements HandlerInterceptor {
    private static final String SECRET_KEY="dahsg43654&^78s";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
        String authHeader = request.getHeader("Authorization");
        log.info("AuthToken Interceptor::");
        if(authHeader!=null){
            log.info("token::"+authHeader);
            try{
                Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authHeader).getBody();
                String subject = claims.getSubject();
                request.setAttribute("email",subject);
            }catch(JwtException ex){
                log.error(ex.getLocalizedMessage());
                throw new InvalidTokenException("Invalid Auth Token");
            }
        }else{
            throw new InvalidTokenException("Auth Token is Required");
        }
        return true;
    }
}

