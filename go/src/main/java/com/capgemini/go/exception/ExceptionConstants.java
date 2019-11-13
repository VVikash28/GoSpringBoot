package com.capgemini.go.exception;

public class ExceptionConstants {

	// PRODUCT MASTER EXCEPTION CONSTANTS
	public static final String VIEW_PRODUCT_ERROR = " Error in Viewing Product >>>";
	public static final String PRODUCT_EXISTS = " Product with the given product id already exists";
	public static final String PRODUCT_NOT_EXISTS = " Product with the given product id doesn't exists";
	public static final String PRODUCT_ADD_ERROR = " Error in adding a new Product >>>";
	public static final String PRODUCT_UPDATE_ERROR = " Error in editing the Product >>>";
	public static final String PRODUCT_DELETE_ERROR = " Error in deleting the Product >>>";
	// END OF PRODUCT MASTER EXCEPTION CONSTANTS

	// RETAILER INVENTORY EXCEPTION CONSTANTS
	public static final String PRODUCT_NOT_IN_INVENTORY = "Item is not present in inventory";
	public static final String INAPPROPRIATE_METHOD_INVOCATION = "Method has been invoked at an illegal or inappropriate time";
	public static final String FAILURE_COMMIT_CHANGES = "Could not commit changes to retailer inventory";
	public static final String PRODUCT_ALREADY_PRESENT_IN_INVENTORY = "Item already present in inventory";
	public static final String INAPPROPRIATE_ARGUMENT_PASSED = "Illegal arguments passed";
	public static final String NO_DATA_FOUND = "The requested data was not found";
	public static final String PERSISTENCE_ERROR = "Persistence error occurred";
	public static final String INTERNAL_RUNTIME_ERROR = "Internal runtime error occured";
	public static final String FAILED_TO_RETRIEVE_USERNAME = "Could not retrieve retailer name from Database";
	// END OF RETAILER INVENTORY EXCEPTION CONSTANTS

	// USER EXCEPTION CONSTANTS
	public static final String USER_EXISTS = " User with the given user id already exists";
	public static final String USER_MAIL_EXISTS = " User with the given mail id already exists";
	public static final String USER_NUMBER_EXISTS = " User with the given phone number already exists";
	public static final String USER_NOT_EXISTS = " User with the given user id doesn't exists";
	public static final String USER_REG_ERROR = " Error in registering a new User >>>";
	public static final String USER_LOGIN_ERROR = " Error in logging in >>>";
	public static final String PASSWORD_MISMATCH = " Username or Password is incorrect";
	public static final String ALREADY_LOGGEDIN = " User Already Logged in ! Please log out first";
	public static final String USER_LOGOUT_ERROR = " User logged out succesfully";
	public static final String ITEM_ALREADY_PRESENT_IN_CART = "Item already present in the cart";
	public static final String ITEM_NOT_IN_CART = "Item is not present in the cart";
	public static final String ITEM_ALREADY_MAPPED_TO_ORDER = "Item is already mapped to order";
	// END OF USER EXCEPTION CONSTANTS

	// REPORT CONSTANT
	public static final String INVALID_DATE = "The given date is invalid";
	public static final String EMPTY_DATABASE = "The database is empty";
	public static final String ERROR_IN_VIEWING = "The Reports cant be viewed >>>";
	// END OF REPORT CONSTANT

	// WISHLIST EXCEPTION CONSTANTS
	public static final String PRODUCT_IN_WISHLIST = " Product is already in your wishlist";
	public static final String WISHLIST_PRODUCT = "Error in displaying your wishlist";

	// END OF WISHLIST EXCEPTION CONSTANTS

	// ADDRESS EXCEPTION CONSTANTS
	public static final String VIEW_ADDRESS_ERROR = " Error in Viewing Address >>>";
	public static final String ADDRESS_ADD_ERROR = "Error in adding a new address >>>";
	public static final String ADDRESSID_NOT_EXISTS = "Address Id doesn't exists";
	public static final String ADDRESS_UPDATE_ERROR = "Error in updating address >>>";
	public static final String ADDRESS_DELETE_ERROR = "Error in deleting address >>>";
	// END OF ADDRESS EXCEPTION CONSTANTS

}
