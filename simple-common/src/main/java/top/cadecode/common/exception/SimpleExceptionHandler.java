package top.cadecode.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import top.cadecode.common.constant.ResCode;
import top.cadecode.common.response.SimpleRes;
import top.cadecode.common.util.MapUtil;
import top.cadecode.common.util.MapUtil.MapBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description 统一异常处理类
 */
@Slf4j
@ControllerAdvice(basePackages = {"top.cadecode.web"})
public class SimpleExceptionHandler extends DefaultErrorAttributes {

    /**
     * 处理 SimpleException
     */
    @ExceptionHandler(SimpleException.class)
    @ResponseBody
    public SimpleRes<?> handleSimpleException(SimpleException e, HttpServletRequest request) {
        log.error("SimpleException Handler =>", e);
        return SimpleRes.of(e.getResCode())
                .errorMsg(e.getMessage())
                .path(request.getRequestURI());
    }

    /**
     * 处理一般异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public SimpleRes<?> handleOtherException(Exception e, HttpServletRequest request) {
        // 获取请求路径
        String requestURI = request.getRequestURI();
        log.error("Exception Handler =>", e);
        // 处理 SpringMVC 异常
        if (e instanceof MissingServletRequestParameterException) {
            return SimpleRes.of(ResCode.REQ_PARAM_INVALID)
                    .path(requestURI);
        }
        if (e instanceof HttpMessageNotReadableException) {
            return SimpleRes.of(ResCode.REQ_BODY_INVALID)
                    .path(requestURI);
        }
        return SimpleRes.of(ResCode.UNKNOWN)
                .path(requestURI);
    }

    /**
     * 自定义 SpringMVC 404、405 返回内容
     */
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        // 获取填充好的 errorAttributes
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        // 获取请求路径
        int status = (int) errorAttributes.get("status");
        String path = (String) errorAttributes.get("path");
        // 处理 404
        if (status == 404) {
            return MapUtil.create()
                    .add("code", ResCode.REQ_PATH_NOT_EXIST.getCode())
                    .add("reason", ResCode.REQ_PATH_NOT_EXIST.getReason())
                    .add("path", path)
                    .asMap();
        }
        // 处理 405
        if (status == 405) {
            return MapUtil.create()
                    .add("code", ResCode.REQ_METHOD_INVALID.getCode())
                    .add("reason", ResCode.REQ_METHOD_INVALID.getReason())
                    .add("path", path)
                    .asMap();
        }
        return errorAttributes;
    }
}
