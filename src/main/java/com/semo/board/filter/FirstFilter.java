package com.semo.board.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

@Slf4j
//@WebFilter(urlPatterns = "/api/filter") //필터를 적용할 uri를 설정한다.
public class FirstFilter implements Filter {

    //필터의 핵심. request와 response를 이용하여 요청과 응답을 처리한다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //전처리 과정 - HttpServletRequest와 HttpServletResponse를 캐시 가능하도록 래핑해준다.
        ContentCachingRequestWrapper httpServletRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

        //전, 후 처리의 기준이되는 메소드
        //filter의 동작에 httpServletRequest, httpServletResponse를 이용한다.
        chain.doFilter(httpServletRequest, httpServletResponse);

        //후 처리 과정
        //request 요청으로 어떤 uri가 들어왔는지 확인
        String uri = httpServletRequest.getRequestURI();
//        String reqContent = new String(httpServletRequest.getContentAsByteArray());
//        reqContent = new String(String.valueOf(httpServletRequest.getRequest()));
        //log.info("uri: {}", uri);
        //request 내용 확인
        Enumeration<String> params = httpServletRequest.getParameterNames();
        StringBuffer buffer = new StringBuffer();
        String req = null;
        while(params.hasMoreElements()) {
            String param = params.nextElement();
            buffer.append(String.format(" & %s=%s", param, request.getParameter(param)));
        }
        if(buffer.length() > 0) {
            req = String.format("request: %s", buffer.toString().substring(1));
            //log.info(req);
        }
        String resContent = new String(httpServletResponse.getContentAsByteArray());
        httpServletResponse.copyBodyToResponse();
        // response 내용 상태 정보, 내용 확인
        int httpStatus = httpServletResponse.getStatus();
        if(httpStatus == HttpServletResponse.SC_OK){
            log.info("Status: {}", httpStatus);
            log.info("URI: {}", httpServletRequest.getRequestURI());
            log.info("Request Body:");
            log.info("  {}", new String(httpServletRequest.getContentAsByteArray()));
            log.info("Response Body:");
            log.info("  {}", new String(httpServletResponse.getContentAsByteArray()));
        }
        else if (httpStatus == HttpStatus.BAD_REQUEST.value()) {
            log.error("Bad Request occurred:");
            log.error("Status: {}", httpStatus);
            log.error("URI: {}", httpServletRequest.getRequestURI());
            log.error("Request Body:");
            log.error("  {}", new String(httpServletRequest.getContentAsByteArray()));
            log.error("Response Body:");
            log.error("  {}", new String(httpServletResponse.getContentAsByteArray()));
        }else{
            log.error("Status: {}", httpStatus);
            log.error("URI: {}", httpServletRequest.getRequestURI());
            log.error("Request Body:");
            log.error("  {}", new String(httpServletRequest.getContentAsByteArray()));
            log.error("Response Body:");
            log.error("  {}", new String(httpServletResponse.getContentAsByteArray()));
        }

//        if (httpStatus != HttpServletResponse.SC_OK) {
//            log.error("status: {}", httpStatus);
//            log.error("error: {}", HttpStatus.valueOf(httpStatus).getReasonPhrase());
//            log.error("path: {}", httpServletRequest.getRequestURI());
//            //log.error("response: {}", resContent);
//            log.error(req);
//        }else{
//            //주의 : response를 클라이언트에서 볼 수 있도록 하려면 response를 복사해야 한다. response를 콘솔에 보여주면 내용이 사라진다.
//            httpServletResponse.copyBodyToResponse();
//
//            log.info("status: {}", httpStatus);
//            //log.info("response: {}", resContent);
//        }


    }

}