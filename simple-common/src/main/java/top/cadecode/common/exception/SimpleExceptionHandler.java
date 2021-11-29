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
import top.cadecode.common.response.SimpleRes;
import top.cadecode.common.constant.ErrorEnum;
import top.cadecode.common.constant.StatusEnum;
import top.cadecode.common.util.MapUtil;
import top.cadecode.common.response.SimpleRes.ResError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description 统一异常处理类
 */
@Slf4j
@ControllerAdvice(basePackages = {"top.cadecode.top.cadecode.top.cadecode.simple.controller"})
public class SimpleExceptionHandler extends DefaultErrorAttributes {

    /**
     * 处理 SimpleException
     */
    @ExceptionHandler(SimpleException.class)
    @ResponseBody
    public SimpleRes handleSimpleException(SimpleException e, HttpServletRequest request,
                                           HttpServletResponse response) {
        log.error("SimpleException Handler =>", e);
        return SimpleRes.fail(e.getErrorEnum())
                .path(request)
                .status(response);
    }

    /**
     * 处理一般异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public SimpleRes handleOtherException(Exception e, HttpServletRequest request,
                                          HttpServletResponse response) {
        log.error("Exception Handler =>", e);
        // 处理 SpringMVC 异常
        if (e instanceof MissingServletRequestParameterException) {
            return SimpleRes.fail(ErrorEnum.REQ_PARAM_INVALID)
                    .path(request)
                    .status(response);
        }
        if (e instanceof HttpMessageNotReadableException) {
            return SimpleRes.fail(ErrorEnum.REQ_BODY_INVALID)
                    .path(request)
                    .status(response);
        }
        return SimpleRes.fail(ErrorEnum.UNKNOWN)
                .path(request)
                .status(response);
    }

    /**
     * 自定义 SpringMVC 404、405 返回内容
     */
    private static final String STATUS_KEY = "status";
    private static final String MESSAGE_KEY = "message";
    private static final String PATH_KEY = "path";
    private static final String ERROR_KEY = "error";

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        // 获取填充好的 errorAttributes
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        // 获取状态码与路径
        int status = (int) errorAttributes.get(STATUS_KEY);
        String path = (String) errorAttributes.get(PATH_KEY);
        // 处理 404
        if (status == StatusEnum.NOT_EXIT.getStatus()) {
            return MapUtil.create()
                    .add(STATUS_KEY, StatusEnum.NOT_EXIT.getStatus())
                    .add(MESSAGE_KEY, StatusEnum.NOT_EXIT.getMessage())
                    .add(PATH_KEY, path)
                    .add(ERROR_KEY, new ResError(ErrorEnum.API_NOT_EXIST))
                    .asMap();
        }
        // 处理 405
        if (status == StatusEnum.NOT_FIT.getStatus()) {
            return MapUtil.create()
                    .add(STATUS_KEY, StatusEnum.NOT_FIT.getStatus())
                    .add(MESSAGE_KEY, StatusEnum.NOT_FIT.getMessage())
                    .add(PATH_KEY, path)
                    .add(ERROR_KEY, new ResError(ErrorEnum.REQ_METHOD_INVALID))
                    .asMap();
        }
        return errorAttributes;
    }
}
