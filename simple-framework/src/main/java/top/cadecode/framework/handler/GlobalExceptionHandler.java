package top.cadecode.framework.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import top.cadecode.common.core.exception.CommonException;
import top.cadecode.common.core.response.CommonResponse;
import top.cadecode.common.enums.ErrorCode;
import top.cadecode.common.util.MapUtil;

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
     * 处理 CommonException
     */
    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public CommonResponse<Object> handleSimpleException(CommonException e, HttpServletRequest request) {
        log.error("CommonException Handler =>", e);
        return CommonResponse.of(e.getErrorCode())
                .errorMsg(e.getMessage())
                .path(request.getRequestURI());
    }

    /**
     * 处理一般异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResponse<Object> handleOtherException(Exception e, HttpServletRequest request) {
        // 获取请求路径
        String requestURI = request.getRequestURI();
        log.error("Exception Handler =>", e);
        // 处理 SpringMVC 异常
        if (e instanceof MissingServletRequestParameterException) {
            return CommonResponse.of(ErrorCode.REQ_PARAM_INVALID)
                    .path(requestURI);
        }
        if (e instanceof HttpMessageNotReadableException) {
            return CommonResponse.of(ErrorCode.REQ_BODY_INVALID)
                    .path(requestURI);
        }
        return CommonResponse.of(ErrorCode.UNKNOWN)
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
        String message = (String) errorAttributes.get("message");
        // 处理 404
        if (status == 404) {
            return generateErrorAttributes(ErrorCode.REQ_PATH_NOT_EXIST, path, message);
        }
        // 处理 405
        if (status == 405) {
            return generateErrorAttributes(ErrorCode.REQ_METHOD_INVALID, path, message);
        }
        // 处理 500
        if (status == 500) {
            return generateErrorAttributes(ErrorCode.UNKNOWN, path, message);
        }
        return errorAttributes;
    }

    /**
     * 生成返回内容的 Map
     */
    private Map<String, Object> generateErrorAttributes(ErrorCode errorCode, String path, String message) {
        return MapUtil.create()
                .add("code", errorCode.getCode())
                .add("reason", errorCode.getReason())
                .add("path", path)
                .add("errorMsg", message)
                .asMap();
    }
}
