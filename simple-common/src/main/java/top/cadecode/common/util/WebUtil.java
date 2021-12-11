package top.cadecode.common.util;

import org.springframework.http.MediaType;
import top.cadecode.common.core.exception.CommonException;
import top.cadecode.common.enums.FrameErrorEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Cade Li
 * @date 2021/12/11
 * @description Web 工具类
 */
public class WebUtil {

    /**
     * 向响应对象写入 JSON
     *
     * @param response 响应对象
     * @param jsonString JSON 字符串
     */
    public static void writeJsonToResponse(HttpServletResponse response, String jsonString) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print(jsonString);
            writer.flush();
        } catch (IOException e) {
            throw CommonException.of(FrameErrorEnum.RES_WRITE_JSON_ERROR)
                    .suppressed(e);
        }
    }
}
