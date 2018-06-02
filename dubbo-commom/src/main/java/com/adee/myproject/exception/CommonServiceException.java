package com.adee.myproject.exception;

/**
 * 用于事务回滚的异常
 * @author Administrator
 *
 */
public class CommonServiceException extends ServiceException{
	public CommonServiceException() {
		super();
	}
	
	public CommonServiceException(String msg) {
		super(msg);
	}
	
	public CommonServiceException(String msg, Throwable e) {
		super(msg, e);
	}
}
