package cn.xxstudy.apidemo.demos.configuration.exception;

import cn.xxstudy.apidemo.demos.configuration.response.ResCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @date: 2023/11/30 15:59
 * @author: TangRen
 * @remark:
 */
@Getter
@Setter
public class ApiRuntimeException extends RuntimeException {
    private int code;
    private String msg;

    public ApiRuntimeException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ApiRuntimeException(ResCode resCode) {
        super(resCode.getMsg());
        this.code = resCode.getCode();
        this.msg = resCode.getMsg();
    }
}
