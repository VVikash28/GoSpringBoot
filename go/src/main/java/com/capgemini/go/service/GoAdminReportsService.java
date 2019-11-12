package com.capgemini.go.service;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;

import com.capgemini.go.dto.ViewSalesReportByUserDTO;
import com.capgemini.go.dto.ViewDetailedSalesReportByProductDTO;
import com.capgemini.go.exception.GoAdminException;

public interface GoAdminReportsService {
	
	
	
	// ------------------------ GreatOutdoor Application --------------------------
			/*******************************************************************************************************
			 * Function Name : viewSalesReportByUserAndCategory 
			 * Input Parameters : entry ,exit , targetuserId, category 
			 * Return Type : List 
			 * Throws : GoAdmin Exception
			 * Author : CAPGEMINI 
			 * Creation Date : 21/9/2019 
			 * Description : To view userId, Order Date, Order Id, Product Id, Product Category, Product Price using the
			 * given inputs
			 * @throws ConnectException
			 ********************************************************************************************************/
		List<ViewSalesReportByUserDTO> viewSalesReportByUserAndCategory(Date entry, Date exit, String TargetuserId,
				int category) throws GoAdminException,ConnectException;

		// ------------------------ GreatOutdoor Application --------------------------
			/*******************************************************************************************************
			 * Function Name : viewDetailedSalesReportByProduct 
			 * Input Parameters : entry ,exit , category 
			 * Return Type : boolean 
			 * Throws : GoAdmin Exception
			 * Author : CAPGEMINI 
			 * Creation Date : 21/9/2019 
			 * Description : To view amount change, percentage change,
			 * color code, month to month, quarter to quarter, year to year change of
			 * specific product
			 * @throws ConnectException 
			 ********************************************************************************************************/


		List<ViewDetailedSalesReportByProductDTO> viewDetailedSalesReportByProduct(Date entry, Date exit, int cat)
				 throws GoAdminException,ConnectException;

	
	

}
