package com.emp.enums;

public enum HttpResultEnum {

	/**
	 *  SUCCESS
	 */
	SUCCESS("200","success"),

	/**
	 *  MIS_PARAM
	 */
	MIS_PARAM("500","缺少参数"),

	/**
	 *  EXCEPTION
	 */
	EXCEPTION("500","系统异常"),

	/**
	 *  AUTHENTICATION_FAILED
	 */
	AUTHENTICATION_FAILED("403","账号密码错误"),

	/**
	 * ADMIN_AUTHENTICATION_FAILED
	 */
	ADMIN_AUTHENTICATION_FAILED("1403","账号密码错误"),

	/**
	 *  ACCOUNT_ALREADY_EXISTS
	 */
	ACCOUNT_ALREADY_EXISTS("1501", "账号已存在"),

	/**
	 *  UNAUTHORIZED
	 */
	UNAUTHORIZED("401","未登录"),

	/**
	 * ADMIN_UNAUTHORIZED
	 */
	ADMIN_UNAUTHORIZED("1401","未登录"),

	/**
	 *  NOT_FOUND
	 */
	NOT_FOUND("404", "内容不存在")
	;

	private String code;

	private String message;

	private HttpResultEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
