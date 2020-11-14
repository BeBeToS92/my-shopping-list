package com.bebetos.shoppinglist.interceptors.support;

import javax.servlet.ServletOutputStream;
import org.springframework.beans.factory.annotation.Autowired;

import com.bebetos.shoppinglist.interceptors.LoggerInterceptor;

import java.io.OutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;


public class ResettableServletOutputStream extends ServletOutputStream {

        @Autowired LoggerInterceptor logApiInterceptor;

        public OutputStream outputStream;
        private ResettableStreamHttpServletResponse wrappedResponse;
        private ServletOutputStream servletOutputStream = new ServletOutputStream(){
                boolean isReady = true;
                
                @Override
                public void setWriteListener(WriteListener writeListener) {
                }

                public boolean isReady(){
                        return isReady;
                }
                @Override
                public void write(int w) throws IOException{
                        outputStream.write(w);
                        wrappedResponse.rawData.add(Integer.valueOf(w).byteValue());
                }
        };

        public ResettableServletOutputStream(ResettableStreamHttpServletResponse wrappedResponse) throws IOException {
                this.outputStream = wrappedResponse.response.getOutputStream();
                this.wrappedResponse = wrappedResponse;
        }


        @Override
        public void close() throws IOException {
                outputStream.close();
                logApiInterceptor.writeResponsePayloadAudit(wrappedResponse);
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
                servletOutputStream.setWriteListener( writeListener );
        }

        @Override
        public boolean isReady(){
                return servletOutputStream.isReady();
        }

        @Override
        public void write(int w) throws IOException {
                servletOutputStream.write(w);
        }
}