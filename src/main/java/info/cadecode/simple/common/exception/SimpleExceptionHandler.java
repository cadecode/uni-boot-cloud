package info.cadecode.simple.common.exception;

import info.cadecode.simple.common.response.SimpleRes;
import info.cadecode.simple.constant.ReasonEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description: 统一异常处理类
 */
@RestController
@ControllerAdvice(basePackages = {"info.cadecode.simple.controller"})
public class SimpleExceptionHandler implements ErrorController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 处理 SimpleException
     */
    @ExceptionHandler(value = SimpleException.class)
    @ResponseBody
    public SimpleRes handleSimpleException(SimpleException e, HttpServletResponse response) {
        log.error("SimpleException Handler =>", e);
        response.setStatus(e.getCode());
        return SimpleRes.exception(e);
    }

    /**
     * 处理一般异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public SimpleRes handleOtherException(Exception e, HttpServletResponse response) {
        log.error("Exception Handler =>", e);
        if (e instanceof HttpMessageNotReadableException
                || e instanceof MissingServletRequestParameterException) {
            response.setStatus(ReasonEnum.NOT_FIT.getCode());
            return SimpleRes.reason(ReasonEnum.NOT_FIT).data(e.getMessage());
        }
        response.setStatus(ReasonEnum.ERROR.getCode());
        return SimpleRes.reason(ReasonEnum.ERROR).data(e.getMessage());
    }

    /**
     * 处理 404 错误（实现 ErrorController 接口）
     */
    @RequestMapping("${server.error.path:${error.path:/error}}")
    public SimpleRes handleError(HttpServletResponse response) {
        response.setStatus(ReasonEnum.NOT_EXIT.getCode());
        return SimpleRes.reason(ReasonEnum.NOT_EXIT);
    }
}
