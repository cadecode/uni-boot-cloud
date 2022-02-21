package top.cadecode.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import top.cadecode.common.core.exception.BaseException;
import top.cadecode.common.enums.UtilErrorEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Cade Li
 * @date 2021/12/11
 * @description Web 工具类
 */
@Slf4j
public class WebUtil {

    /**
     * 未知 IP
     */
    private static final String UNKNOWN_IP = "unknown";
    /**
     * 本地 IP 地址
     */
    private static final List<String> LOCAL_IP_LIST = Arrays.asList("127.0.0.1", "0:0:0:0:0:0:0:1");

    /**
     * 向响应对象写入 JSON
     *
     * @param response   响应对象
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
            throw BaseException.of(UtilErrorEnum.WRITE_JSON_RES_ERROR).suppressed(e);
        }
    }

    /**
     * 获取被代理的真实 IP
     *
     * @param request 请求对象
     * @return ip
     */
    public static String getIpAddress(HttpServletRequest request) {
        // squid
        String ip = request.getHeader("X-Forwarded-For");
        // tomcat
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        // webLogic
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        // nginx
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        // 从 request 获取
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 获取到的 ip 地址可能经过追加，一般使用 ‘,’ 分隔
        if (ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        // 本地访问，需要获取本机真正的ip地址
        if (LOCAL_IP_LIST.contains(ip)) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error(e.getMessage(), e);
            }
        }
        return ip;
    }
}
