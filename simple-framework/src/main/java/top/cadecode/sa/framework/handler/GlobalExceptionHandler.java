package top.cadecode.sa.framework.handler;

import cn.hutool.core.lang.Dict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import top.cadecode.sa.common.core.exception.BaseException;
import top.cadecode.sa.common.core.response.Result;
import top.cadecode.sa.common.core.response.ResultCode;
import top.cadecode.sa.common.enums.BaseErrorEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description 统一异常处理类
 */
@Slf4j
@ControllerAdvice(basePackages = {"top.cadecode"})
public class GlobalExceptionHandler extends DefaultErrorAttributes {

    /**
     * 处理 BaseException
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public Result<Object> handleSimpleException(BaseException e, HttpServletRequest request) {
        log.error("BaseException Handler =>", e);
        return Result.of(e.getResultCode())
                .errorMsg(e.getErrorMsg())
                .path(request.getRequestURI());
    }

    /**
     * 处理一般异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<Object> handleOtherException(Exception e, HttpServletRequest request) {
        log.error("Exception Handler =>", e);
        return Result.of(ResultCode.UNKNOWN)
                .errorMsg(e.getMessage())
                .path(request.getRequestURI());
    }

    /**
     * 处理 Spring MVC 异常
     */
    @ExceptionHandler({
            ServletRequestBindingException.class,
            HttpMessageNotReadableException.class,
            TypeMismatchException.class
    })
    @ResponseBody
    public Result<Object> handleMvcException(Exception e, HttpServletRequest request) {
        log.error("Spring MVC Exception Handler =>", e);
        String requestURI = request.getRequestURI();
        if (e instanceof ServletRequestBindingException
                || e instanceof TypeMismatchException) {
            return Result.of(BaseErrorEnum.REQ_PARAM_INVALID)
                    .errorMsg(e.getMessage())
                    .path(requestURI);
        }
        if (e instanceof HttpMessageNotReadableException) {
            return Result.of(BaseErrorEnum.REQ_BODY_INVALID)
                    .errorMsg(e.getMessage())
                    .path(requestURI);
        }
        return null;
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
        String message = (String) errorAttributes.get("message");
        // 处理 404
        if (status == 404) {
            return generateErrorAttributes(BaseErrorEnum.REQ_PATH_NOT_EXIST, path, message);
        }
        // 处理 405
        if (status == 405) {
            return generateErrorAttributes(BaseErrorEnum.REQ_METHOD_INVALID, path, message);
        }
        // 处理 500
        if (status == 500) {
            return generateErrorAttributes(ResultCode.UNKNOWN, path, message);
        }
        return errorAttributes;
    }

    /**
     * 生成返回内容的 Map
     */
    private Map<String, Object> generateErrorAttributes(ResultCode resultCode, String path, String message) {
        return Dict.create()
                .set("code", resultCode.getCode())
                .set("reason", resultCode.getReason())
                .set("path", path)
                .set("errorMsg", message);
    }
}
