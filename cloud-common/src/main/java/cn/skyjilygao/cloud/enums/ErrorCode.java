package cn.skyjilygao.cloud.enums;


import cn.skyjilygao.cloud.exception.ReturnTResponse;

/**
 * @Description: 异常code
 * 当前程序是返回2开头，调用php是返回3开头，服务器内部异常为5开头，AE调用接口异常返回以6开头
 * @Author: liuyun
 * @CreateDate: 2020/9/9 下午 2:17
 */
public enum ErrorCode implements ReturnTResponse {

	SUCCESS(200, "OK"),
	PARAM_NULL(201, "参数为空"),
	AUTH_CODE_STALE(202, "验证码已过期"),
	AUTH_CODE_ERROR(203, "验证码错误,请重新输入"),
	EMAIL_ERROR(204, "提交邮箱与获取验证码邮箱不匹配"),
	COMPANY_NAME_NULL(205, "公司名称为空"),
	BANK_LIST_NULL(206, "付款银行信息为空"),
	BANK_INFO_NULL(207, "付款银行信息不全"),
	CONTRACT_EMAIL_NULL(208, "公司邮箱为空"),
	ADD_COMPANY_FAIL(209, "提交公司信息失败"),
	UPLOAD_IMG_NULL(210, "上传图片为空"),
	SUBMIT_CONTRACT_FAIL(211, "提交合同信息失败"),
	UPLOAD_IMG_FAIL(212, "图片上传失败"),
	PAYMENT_MONEY_NULL(213, "付款金额为空"),
	PAYMENT_IMAGE_NULL(214, "付款截图为空"),
	PAYMENT_IMAGE_UPLOAD_FAIL(215, "上传付款截图失败"),
	PAYMENT_SUBMIT_FAIL(216, "提交付款信息失败,如有问题,请联系客服."),
	AUTH_CODE_FAIL(217, "验证码生成失败"),
	AUTH_CODE_SEND_FAIL(218, "验证码发送失败"),
	GO_SIGN_CONTRACT(219, "签约合同异常"),
	AUTH_CODE_NOT(220, "验证码不存在"),
	BANK_NO_PASS(221, "暂不支持"),
	CONTRACT_PDF_FAIL(222, "合同PDF生成失败"),
	REMIT_LOG_NOT(223, "付款记录不存在"),
	RECHARGE_ERROR(224, "充值异常"),
	PAYMENT_TYPE_ERROR(225, "支付类型异常"),
	CLIENT_TYPE_ERROR(226, "客户类型异常"),
	RECHARGE_INFO_IS_NULL(227, "未找到充值数据"),
	RECHARGE_IS_NOT_PRE(228, "非预付账单，无法下载invoice"),
	RECHARGE_NOT_DOWNLOAD_INVOICE(229, "此账单不可下载invoice"),

	PAYMENT_AUTH_CODE_INFO_NULL(301, "二维码生成信息为空"),
	PAYMENT_AUTH_CODE_FAIL(302, "二维码生成失败"),

	TAG_P_T_ERROR(501, "P,T验证异常"),

	AE_SELLER_JSON_ERROR(601, "json信息异常"),
	AE_SELLER_PARAM_ERROR(602, "字段为空或字段值不合法"),
	AE_SELLER_BIR_NO_EXIST(603, "工单已存在"),
	AE_SELLER_NOT_WORK_ORDER(604, "工单不存在"),
	BUSINESS_ID_NOT_VALID(605, "business id无效"),
	AE_SELLER_LOG_TIME_NULL(606, "时间区间为空"),
	AE_SELLER_DATE_ERROR(607, "结束时间小于开始时间"),
	AE_SELLER_LOG_TYPE_NULL(608, "请求数据类型为空"),
	AE_SELLER_LOG_TIME_ERROR(609, "时间参数格式异常"),
	STORE_NO_IS_NULL(610, "店铺ID为空"),
	AD_ACCOUNT_IS_NULL(611, "广告账户ID为空"),
	RECHARGE_MONEY_IS_NULL(612, "充值金额为空"),
	RECHARGE_ORDER_ID_IS_NULL(613, "充值订单编号为空"),
	RECHARGE_ORDER_ID_IS_LOADING(614, "此订单正在进行充值,请勿重复操作"),
	RECHARGE_ORDER_ID_IS_FULFILL(615, "此订单已充值完成,请勿重复操作"),
	RECHARGE_MONEY_IS_ZERO(616, "当前账期充值为0"),
	RECHARGE_TRANS_DATE_NULL(617, "获取账单账期参数为空"),
	ACCOUNT_STATUS_NOT_ACTIVE(618, "广告账户已被封,充值失败"),
	MAT_ACCOUNT_OPEN_ERROR(619, "开户信息提交异常,请检查商家开户信息"),

	SOMETHING_ERROR(5000, "未知错误"),
	// 内部创建
	// 501x 作为用户相关错误
	CREATE_PARAM_USER_NOT_EXIST(5010, "参数错误，用户不存在"),
	CREATE_PARAM_NOT_NULL(5011, "错误！用户ID/账户ID不可为空."),
	USER_FB_TOKEN_INVALID(5012, "用户Facebook访问口令无效"),
	USER_FB_EMAIL_INVALID(5013, "用户Facebook邮箱无效"),
	BUSINESS_USER_NOT(5014, "该用户不在此平台下"),
	BUSINESS_ADD_USER_ERR(5015, "商务管理平台添加用户失败"),
	AUTH_TOKEN_ERR(4001, "Access Token无效"),
	NOT_NULL(2001, "不能为空"),
	;

	private int code;
	private String msg;

	ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMsg() {
		return msg;
	}
}