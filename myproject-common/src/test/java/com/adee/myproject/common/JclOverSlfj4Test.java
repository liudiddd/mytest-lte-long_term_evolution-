package com.adee.myproject.common;

import java.util.logging.Level;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JclOverSlfj4Test {
	
	//jcl
	private static Log logger = LogFactory.getLog(JclOverSlfj4Test.class);
	
	//slf4j
	private static Logger logger1 = LoggerFactory.getLogger(JclOverSlfj4Test.class);
	
	//jul
	private static java.util.logging.Logger logger2 = java.util.logging.Logger.getLogger("jul");
	static {
		logger2.setLevel(Level.INFO);
	}
	
	//log4j
	private static org.apache.log4j.Logger logger3 = org.apache.log4j.Logger.getLogger(JclOverSlfj4Test.class);
	
	public static void main(String[] args) {
		logger.debug("hello jcl");
		logger1.debug("hello slf4j");
		logger2.info("hello jul");
		logger3.info("hello log4j");
	}
}
