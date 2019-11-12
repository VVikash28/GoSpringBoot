package com.capgemini.go.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoLog {	
	public static Logger getLogger (Class<?> className) {
		return LogManager.getLogger(className);
	}
	
	private GoLog() {

	}
}
