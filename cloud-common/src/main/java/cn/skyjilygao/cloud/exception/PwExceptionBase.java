package cn.skyjilygao.cloud.exception;

/**
 * 自定义异常基础类
 *
 * @author skyjilygao
 * @since 1.0
 */
public class PwExceptionBase extends RuntimeException {

    /**
     * 状态码：用于接口返回状态code
     */
    protected int httpCode;
    /**
     * 状态信息：用户接口返回code说明
     */
    protected String httpMessage;

    public PwExceptionBase(ReturnTResponse httpStatus) {
        super(httpStatus.getMsg());
        setHttpInfo(httpStatus);
    }

    public PwExceptionBase(ReturnTResponse httpStatus, Exception e) {
        super(e);
        setHttpInfo(httpStatus);
    }

    public PwExceptionBase(int httpCode, String httpMessage) {
        super(httpMessage);
        setHttpInfo(httpCode, httpMessage);
    }

    public PwExceptionBase(int httpCode, String httpMessage, Exception e) {
        super(e);
        setHttpInfo(httpCode, httpMessage);
    }


    public int getHttpCode() {
        return httpCode;
    }

    public String getHttpMessage() {
        return httpMessage;
    }

    protected void setHttpInfo(ReturnTResponse tResponse) {
        setHttpInfo(tResponse.getCode(), tResponse.getMsg());
    }

    protected void setHttpInfo(int httpCode, String httpMessage) {
        this.httpCode = httpCode;
        this.httpMessage = httpMessage;
    }
}
