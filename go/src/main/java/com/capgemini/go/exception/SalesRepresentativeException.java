package com.capgemini.go.exception;

public class SalesRepresentativeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1238507920541017080L;

	public SalesRepresentativeException() {

	}

	public SalesRepresentativeException(String message) {
		super(message);
	}

	public SalesRepresentativeException(Throwable cause) {
		super(cause);
	}

	public SalesRepresentativeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SalesRepresentativeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
