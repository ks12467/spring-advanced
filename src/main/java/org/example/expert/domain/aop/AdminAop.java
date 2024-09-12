package org.example.expert.domain.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Aspect
public class AdminAop {


    @Around("execution(* org.example.expert.domain.comment.controller.CommentAdminController.deleteComment(..)) || execution(* org.example.expert.domain.user.controller.UserAdminController.changeUserRole(..))")
    public Object adviceAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {

        LocalDateTime time = LocalDateTime.now();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        long userId = (long) request.getAttribute("userId");
        StringBuffer url = request.getRequestURL();
        String method = request.getMethod();

        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            log.info("::: 요청한 사용자 ID : {} ::: ", userId);
            log.info("::: API 요청시간 : {} ::: ", time);
            log.info("::: API 요청 URL : {} {} ::: ", method, url);
        }

    }
}