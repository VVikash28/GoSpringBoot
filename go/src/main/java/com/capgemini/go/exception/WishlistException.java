/**
 * 
 * Wishlist Exception
 */

package com.capgemini.go.exception;

public class WishlistException extends Exception {

	private static final long serialVersionUID = 2L;

	public WishlistException() {

	}

	public WishlistException(String message) {
		super(message);

	}

	public WishlistException(Throwable arg0) {
		super(arg0);

	}

	public WishlistException(String arg0, Throwable arg1) {
		super(arg0, arg1);

	}

	public WishlistException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);

	}

}
