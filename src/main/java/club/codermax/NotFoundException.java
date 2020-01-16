package club.codermax;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 定义页面资源找不到
 * 如果访问的页面不存在，可以在相应的Controller中新建对象来抛出异常
 *  @ResponseStatus(HttpStatus.NOT_FOUND)将其确定为资源找不到的状态，对应到404页面
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
