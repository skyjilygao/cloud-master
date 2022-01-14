package cn.skyjilygao.cloud.exception;


/**
 * 自定义异常处理类。用于接口返回时可以指定异常枚举类。便于返回状态码管理
 *
 * @author skyjilygao
 * @since 1.8
 */
public class PwException extends PwExceptionBase {

    public PwException(ReturnTResponse httpStatus) {
        super(httpStatus.getCode(), httpStatus.getMsg());
    }

    public PwException(ReturnTResponse httpStatus, Exception e) {
        super(httpStatus.getCode(), httpStatus.getMsg(), e);
    }
}
