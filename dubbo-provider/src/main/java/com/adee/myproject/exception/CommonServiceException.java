package com.adee.myproject.exception;

/**
 * ��������ع����쳣
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
