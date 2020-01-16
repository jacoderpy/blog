package club.codermax.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 进行日志处理，使用Spring AOP切面的知识
 *
 */
@Aspect
@Component
public class LogAspect {

    // 获得日志对象,拿到日志记录器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 定义切面
    @Pointcut("execution(* club.codermax.controller.*.*(..))")
    public void log(){}

    // 在切面之前运行
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        //通过ServletRequestAttributes来达到request，进而获得相应的url和ip
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        // 获得相应的ip地址
        String ip = request.getRemoteAddr();

        //通过joinPoint获取请求方式
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        // 获取请求参数
        Object[] args = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        logger.info("Request:{}",requestLog);
    }

    @After("log()")
    public void doAfter(){
//        logger.info("-----------doAfter-----------");
    }
    //在方法执行完之后返回,拦截方法所捕获的内容
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        // 获得请求返回的地址
        logger.info("Result:{}",result);
    }

    /**
     * 封装成一个类
     * 目的是为了获取并记录请求者的url，ip，请求方式，以及请求参数（使用对象数组）
     */
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
