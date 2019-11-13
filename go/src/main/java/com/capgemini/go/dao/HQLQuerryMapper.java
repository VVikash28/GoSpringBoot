package com.capgemini.go.dao;

public class HQLQuerryMapper {

	public static final String GET_ALL_PRODUCTS = "FROM ProductDTO prod WHERE prod.quantity >= 0 ORDER BY prod.productName";

	public static final String USER_ID_EXISTS = "FROM UserEntity WHERE userId =:idExist";
	public static final String USER_CATEGORY = "FROM UserEntity WHERE userCategory =:catgCorrect";
	public static final String USER_PASSWORD_CHECK = "select userPassword FROM UserEntity WHERE userId =:correctUser";
	public static final String CHANGE_ACTIVE_STATUS = "UPDATE UserEntity SET userActiveStatus=1 WHERE userId =:userLoggin";

	public static final String VALIDATE_NUMBER_EMAIL = "SELECT COUNT(*) FROM UserEntity WHERE userNumber =:existNum OR userMail =:existMail";

	public static final String UPDATE_ORDER_PRODUCT_MAP = "UPDATE OrderProductMapDTO opm SET opm.productStatus=0 WHERE ORDER_ID=:orderId and opm.productStatus=:productStatus";

	// GOADMIN REPORTS HQL QUERRY

	public static final String SELECT_REVENUE_DATA = "SELECT order.orderInitiateTime , prod.price  FROM OrderDTO order JOIN OrderProductMapDTO opm ON order.orderId=opm.orderId JOIN ProductDTO prod ON opm.productId=prod.productId";

	public static final String SELECT_DATA_FROM_DATABASE = "SELECT order.userId , order.orderInitiateTime , order.orderId , opm.productId , prod.productCategory , prod.price  FROM OrderDTO order JOIN OrderProductMapDTO opm ON order.orderId=opm.orderId JOIN ProductDTO prod ON opm.productId=prod.productId";

	//Cancel Order, product by Sales Rep
    public static final String IS_ORDER_PRESENT = "SELECT orderId FROM OrderDTO WHERE orderId = :orderID";
   
    public static final String IS_SALES_REP_ID_PRESENT = "FROM SalesRepDTO sre WHERE sre.userId = :userID";
   
    public static final String CHECK_ORDER_DISPATCH_STATUS = "SELECT orderDispatchStatus FROM OrderDTO WHERE orderId= :orderID";
   
    public static final String GET_PRODUCT_MAP = "FROM OrderProductMapDTO WHERE orderId=:orderID";
   
    public static final String UPDATE_ORDER_PRODUCT_MAP_WITH_PRODUCT_UIN = "UPDATE OrderProductMapDTO opm SET opm.productStatus = 0 where opm.orderId =:orderID and opm.productUIN =:productUin";
   
    public static final String GET_PRODUCT_QUANTITY = "SELECT COUNT(*) FROM OrderProductMapDTO WHERE productStatus = 1 AND orderId=:orderID AND productId=:productID";
   
    public static final String UPDATE_ORDER_PRODUCT_MAP_CANCEL_PROD_EQUAL_QUANTITY = "Update OrderProductMapDTO set productStatus = 0 where orderId =:orderID AND productId =:productID";
   
    public static final String UPDATE_ORDER_PRODUCT_MAP_CANCEL_PROD_LESS_QUANTITY  = "UPDATE ORDER_PRODUCT_MAP SET PRODUCT_STATUS = 0 WHERE PRODUCT_UIN IN (SELECT * FROM (SELECT PRODUCT_UIN FROM ORDER_PRODUCT_MAP WHERE ORDER_ID = :orderID AND PRODUCT_ID = :productID LIMIT :quantity) AS L)";   
   
    public static final String GET_ORDER_PRODUCT_MAP_CANCEL_PROD_EQUAL_QUANTITY = "FROM OrderProductMapDTO WHERE orderId =:orderID AND productId =:productID AND productStatus =:value";
   
    public static final String GET_ORDER_PRODUCT_MAP_CANCEL_PROD_LESS_QUANTITY = "FROM OrderProductMapDTO opme WHERE opme.productUIN IN (SELECT opme2.productUIN FROM OrderProductMapDTO opme2 WHERE opme2.orderId =:orderID AND opme2.productId =:productID AND opme2.productStatus = 0)";
   
    public static final String SELECT_SALES_REP_TARGET = "SELECT target FROM SalesRepDTO sre WHERE sre.userId = :userID";
   
    public static final String GET_TARGET_STATUS = "SELECT targetStatus FROM SalesRepDTO sre WHERE sre.userId = :userID";
   
    public static final String SELECT_SALES_REP_BONUS = "SELECT bonus FROM SalesRepDTO sre WHERE sre.userId = :userID";
    //end of cancel
	// sales rep return order
    public static final String GET_ORDER_PRODUCT_MAP = "FROM OrderProductMapDTO opm WHERE opm.orderId=:orderId  AND opm.productStatus=1";
        public static final String UPDATE_ORDER_PRODUCT_MAP_BY_QTY = "UPDATE ORDER_PRODUCT_MAP SET PRODUCT_STATUS = 0 WHERE PRODUCT_UIN IN (SELECT * FROM (SELECT PRODUCT_UIN FROM ORDER_PRODUCT_MAP WHERE ORDER_ID = :orderId AND PRODUCT_ID = :productId LIMIT :qty) AS L)";


        public static final String GET_PRODUCT_UIN = "SELECT PRODUCT_UIN FROM `ORDER_PRODUCT_MAP` WHERE ORDER_ID = :orderId AND PRODUCT_ID = :productId LIMIT  :qty";
         public static final String CHECK_ORDER_DISPATCH_STATUS_RETURN = "SELECT ORDER_DISPATCH_STATUS FROM `ORDER` WHERE ORDER_ID= :orderID";
        




}


