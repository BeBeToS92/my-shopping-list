package com.bebetos.shoppinglist.interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bebetos.shoppinglist.interceptors.support.ResettableStreamHttpServletRequest;
import com.bebetos.shoppinglist.interceptors.support.ResettableStreamHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.lang.StringBuffer;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerInterceptor extends HandlerInterceptorAdapter {

        private static final Logger LOGGER = LoggerFactory.getLogger("InfoOperations");

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                        Exception ex) throws Exception {
                if (response instanceof ResettableStreamHttpServletResponse) {
                        ((ResettableStreamHttpServletResponse) response).payloadFilePrefix = ((ResettableStreamHttpServletRequest) request).payloadFilePrefix;
                        ((ResettableStreamHttpServletResponse) response).payloadTarget = ((ResettableStreamHttpServletRequest) request).payloadTarget;
                        writeResponsePayloadAudit((ResettableStreamHttpServletResponse) response);
                }
        }

        public String getRawHeaders(HttpServletRequest request) {
                StringBuffer rawHeaders = new StringBuffer();
                Enumeration<String> headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                        String key = (String) headerNames.nextElement();
                        String value = request.getHeader(key);
                        rawHeaders.append(key).append(":").append(value).append("\n");
                }

                return rawHeaders.toString();
        }

        public String getRawHeaders(HttpServletResponse response) {
                StringBuffer rawHeaders = new StringBuffer();
                Enumeration<String> headerNames = Collections.enumeration(response.getHeaderNames());
                while (headerNames.hasMoreElements()) {
                        String key = (String) headerNames.nextElement();
                        String value = response.getHeader(key);
                        rawHeaders.append(key).append(":").append(value).append("\n");
                }

                return rawHeaders.toString();
        }

        public void writeRequestPayloadAudit(ResettableStreamHttpServletRequest wrappedRequest) {
                try {
                        String requestHeaders = getRawHeaders(wrappedRequest);
                        String requestBody = org.apache.commons.io.IOUtils.toString(wrappedRequest.getReader());

                        LOGGER.info("=================================== Request Start ===================================");
                        LOGGER.info("Request Method: " + wrappedRequest.getMethod());
                        LOGGER.info("Request URL: " + wrappedRequest.getRequestURI());
                        LOGGER.info("Request Headers:" + requestHeaders.replace("\n", ","));
                        LOGGER.info("Request Body:" + requestBody.replace("\n", ""));
                        LOGGER.info("==================================== Request End ====================================");

                } catch (Exception e) {
                        LOGGER.error("Exception Request" + e.getMessage());
                }
        }

        public void writeResponsePayloadAudit(ResettableStreamHttpServletResponse wrappedResponse) {

                String rawHeaders = getRawHeaders(wrappedResponse);

                LOGGER.info("=================================== Response Start ===================================");
                LOGGER.info("Response Status: " + wrappedResponse.getStatus());
                LOGGER.info("Response Headers:" + rawHeaders.replace("\n", ","));

                byte[] data = new byte[wrappedResponse.rawData.size()];
                for (int i = 0; i < data.length; i++) {
                        data[i] = (byte) wrappedResponse.rawData.get(i);
                }
                String responseBody = new String(data);

                LOGGER.info("Response body:" + responseBody);
                LOGGER.info("==================================== Response End ====================================");

        }
}