package cn.xxstudy.apidemo.demos.configuration.response;

import lombok.Getter;
import org.springframework.lang.Nullable;

/**
 * @date: 2023/11/30 16:25
 * @author: TangRen
 * @remark: 响应体
 */
@Getter
public class ResBody<T> {
    private int code;
    private String msg;
    @Nullable
    private T data = null;
    private final long timestamp;

    public ResBody() {
        timestamp = System.currentTimeMillis();
    }

    public static <T> ResBody<T> success(T data) {
        ResBody<T> body = new ResBody<>();
        body.code = ResCode.OK.getCode();
        body.msg = ResCode.OK.getMsg();
        body.data = data;
        return body;
    }


    public static <T> ResBody<T> failed(int code, String msg) {
        ResBody<T> body = new ResBody<>();
        body.code = code;
        body.msg = msg;
        return body;
    }
}
