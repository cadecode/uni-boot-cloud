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
import top.cadecode.common.core.response.ResponseCode;
import top.cadecode.common.enums.ClientErrorEnum;
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
        return CommonResponse.of(e.getResponseCode())
                .errorMsg(e.getErrorMsg())
                .path(request.getRequestURI());
    }

    /**
     * 处理一般异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResponse<Object> handleOtherException(Exception e, HttpServletRequest request) {
        log.error("Exception Handler =>", e);
        return CommonResponse.of(ResponseCode.UNKNOWN)
                .errorMsg(e.getMessage())
                .path(request.getRequestURI());
    }

    /**
     * 处理 Spring MVC 异常
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class})
    @ResponseBody
    public CommonResponse<Object> handleMvcException(Exception e, HttpServletRequest request) {
        log.error("Spring MVC Exception Handler =>", e);
        String requestURI = request.getRequestURI();
        if (e instanceof MissingServletRequestParameterException) {
            return CommonResponse.of(ClientErrorEnum.REQ_PARAM_INVALID)
                    .errorMsg(e.getMessage())
                    .path(requestURI);
        }
        if (e instanceof HttpMessageNotReadableException) {
            return CommonResponse.of(ClientErrorEnum.REQ_BODY_INVALID)
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
            return generateErrorAttributes(ClientErrorEnum.REQ_PATH_NOT_EXIST, path, message);
        }
        // 处理 405
        if (status == 405) {
            return generateErrorAttributes(ClientErrorEnum.REQ_METHOD_INVALID, path, message);
        }
        // 处理 500
        if (status == 500) {
            return generateErrorAttributes(ResponseCode.UNKNOWN, path, message);
        }
        return errorAttributes;
    }

    /**
     * 生成返回内容的 Map
     */
    private Map<String, Object> generateErrorAttributes(ResponseCode responseCode, String path, String message) {
        return MapUtil.create()
                .add("code", responseCode.getCode())
                .add("reason", responseCode.getReason())
                .add("path", path)
                .add("errorMsg", message)
                .asMap();
    }
}
