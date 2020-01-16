package club.codermax.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 拦截处理所有带有Controller注解
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    // 获取日志对象
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 如果有错误，在页面中返回错误信息
    // ExceptionHandler标记这个方法可以做异常处理
    @ExceptionHandler
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        //getRequestURL获取异常的url
        logger.error("Requst URL : {}，Exception : {}", request.getRequestURL(),e);

        // 当页面返回有响应的状态码是直接交给相应的页面不用拦截
        // 有状态码直接抛出，让SpringBoot去处理
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        // 将日志统一处理到error、error.html中
        mv.setViewName("error/error");

        return mv;
    }


}
