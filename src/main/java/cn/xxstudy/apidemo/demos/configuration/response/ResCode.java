package cn.xxstudy.apidemo.demos.configuration.response;

/**
 * @date: 2023/11/30 16:20
 * @author: TangRen
 * @remark: 定义ResCode
 */
public enum ResCode {

    OK(200, "OK"),
    FAILED_400(400, "请求失败，参数错误请检查重试"),
    FAILED_401(401, "Token无效"),
    FAILED_404(404, "资源不存在"),
    FAILED_405(405, "请求方式错误"),
    FAILED_500(500, "服务器错误");

    private final int code;
    private final String msg;

    ResCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
