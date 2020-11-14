package com.bebetos.shoppinglist.interceptors;

import org.springframework.stereotype.Component;

import com.bebetos.shoppinglist.interceptors.support.ResettableStreamHttpServletRequest;
import com.bebetos.shoppinglist.interceptors.support.ResettableStreamHttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RequestFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger("InfoOperations");

    @Autowired LoggerInterceptor logApiInterceptor;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {

        ResettableStreamHttpServletRequest wrappedRequest = null;
        ResettableStreamHttpServletResponse wrappedResponse = null; 

        HttpServletRequest req = (HttpServletRequest) request;
        String reqPath = req.getRequestURI();

        if(request.getContentType() != null && !request.getContentType().equals("application/x-www-form-urlencoded") 
                && reqPath.indexOf("/auth") == -1){
                try {
                        wrappedRequest = new ResettableStreamHttpServletRequest((HttpServletRequest) request);
                        wrappedResponse = new ResettableStreamHttpServletResponse((HttpServletResponse) response);
                        logApiInterceptor.writeRequestPayloadAudit(wrappedRequest);
                    } catch (Exception e) {
                            LOGGER.error("FAILING WRAPPER REQUEST/RESPONSE",e);
                    }
                chain.doFilter(wrappedRequest, wrappedResponse);
        } else {
                chain.doFilter(request, response);
        }
    }
}