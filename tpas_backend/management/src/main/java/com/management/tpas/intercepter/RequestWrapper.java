package com.management.tpas.intercepter;

import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author dude
 * @version 1.0
 * @classname RequestWrapper
 * @description 包装HttpServletRequest，目的是让其输入流可重复读，便于拦截器获取请求参数
 * @date 2020/9/4
 * https://blog.51cto.com/zero01/2334836
 * https://blog.csdn.net/yaomingyang/article/details/108246334
 **/
public class RequestWrapper extends HttpServletRequestWrapper {

    private static final Logger log = LoggerFactory.getLogger(RequestWrapper.class);

    /**
     * 参数字节数组
     **/
    private String requestBody = null;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            requestBody = StreamUtils.copyToString(request.getInputStream(), Charset.defaultCharset());
        } catch (IOException e) {
            log.error("error getting request inputStream: {}", e.getMessage());
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg);
        }

    }

    /**
     * @return javax.servlet.ServletInputStream
     * @description 每次调用此方法时将数据流中的数据读取出来，然后再回填到InputStream之中
     * 解决通过@RequestBody和@RequestParam（POST方式）读取一次后控制器拿不到参数问题
     * @author dude
     * @date 2020/9/4
     **/
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrIn = new ByteArrayInputStream(requestBody.getBytes(Charset.defaultCharset()));
        return new ServletInputStream() { // 回填

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() {
                return byteArrIn.read();
            }
        };
    }

    public String getRequestBody() {
        return requestBody;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

}
