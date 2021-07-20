package info.cadecode.simple.common.exception;

import info.cadecode.simple.common.response.SimpleRes;
import info.cadecode.simple.constant.ReasonEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description: 统一异常处理类
 */
@ControllerAdvice(basePackages = {"info.cadecode.simple.controller"})
public class SimpleExceptionHandler extends DefaultErrorAttributes {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 处理 SimpleException
     */
    @ExceptionHandler(value = SimpleException.class)
    @ResponseBody
    public SimpleRes handleSimpleException(SimpleException e, HttpServletRequest request,
                                           HttpServletResponse response) {
        log.error("SimpleException Handler =>", e);
        response.setStatus(e.getCode());
        return SimpleRes.exception(e)
                .path(getPath(request))
                .error(getErrorMsg(e));
    }

    /**
     * 处理一般异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public SimpleRes handleOtherException(Exception e, HttpServletRequest request,
                                          HttpServletResponse response) {
        log.error("Exception Handler =>", e);
        // 判断是否为 SpringMVC 原生异常
        if (e instanceof HttpMessageNotReadableException
                || e instanceof MissingServletRequestParameterException) {
            response.setStatus(ReasonEnum.NOT_FIT.getCode());
            return SimpleRes.reason(ReasonEnum.NOT_FIT)
                    .path(getPath(request))
                    .error(getErrorMsg(e));
        }
        response.setStatus(ReasonEnum.FAIL.getCode());
        return SimpleRes.reason(ReasonEnum.FAIL)
                .path(getPath(request))
                .error(getErrorMsg(e));
    }

    /**
     * 定制 404、405 等原生异常的返回内容
     */

    private static final String STATUS_KEY = "status";
    private static final String PATH_KEY = "path";
    private static final String ERROR_KEY = "error";

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        // 获取填充好的 errorAttributes
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        // 获取状态码与路径
        int status = (int) errorAttributes.get(STATUS_KEY);
        String path = (String) errorAttributes.get(PATH_KEY);
        String error = (String) errorAttributes.get(ERROR_KEY);
        // 处理 404 异常
        if (status == ReasonEnum.NOT_EXIT.getCode()) {
            return SimpleRes.reason(ReasonEnum.NOT_EXIT)
                    .path(path)
                    .error(error)
                    .map();
        }
        // 处理 405 异常
        if (status == ReasonEnum.NOT_FIT.getCode()) {
            return SimpleRes.reason(ReasonEnum.NOT_FIT)
                    .path(path)
                    .error(error)
                    .map();
        }
        return SimpleRes.reason(ReasonEnum.FAIL)
                .path(path)
                .map();
    }

    /**
     * 获取请求路径
     */
    private String getPath(HttpServletRequest request) {
        return request.getRequestURI();
    }

    /**
     * 获取异常信息
     */
    private String getErrorMsg(Exception e) {
        return "[" + e.getClass().getName() + "]" + e.getMessage();
    }
}
