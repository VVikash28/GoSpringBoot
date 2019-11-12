package com.capgemini.go.service;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.go.dto.ViewDetailedSalesReportByProductDTO;
import com.capgemini.go.dto.ViewSalesReportByUserDTO;

import com.capgemini.go.dao.GoAdminReportsDao;
import com.capgemini.go.dao.GoAdminReportsDaoImpl;
import com.capgemini.go.dao.ProductDao;
import com.capgemini.go.exception.GoAdminException;
import com.capgemini.go.utility.GoLog;

@Service(value = "goAdminReportService")
public class GoAdminReportServiceImpl implements GoAdminReportsService {

	@Autowired
	private GoAdminReportsDao goAdminReportsDao;

	public GoAdminReportsDao getGoAdminReportsDao() {
		return goAdminReportsDao;
	}

	public void setProductDao(GoAdminReportsDao goAdminReportsDao) {
		this.goAdminReportsDao = goAdminReportsDao;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * Function Name : viewSalesReportByUserAndCategory Input Parameters :
	 * TargetuserId , entry , exit , category ,connection Return Type : List Throws
	 * : Author : Rintu Creation Date : 21/9/2019 Description : To view sales report
	 * of specific product and User within a given date
	 ********************************************************************************************************/

	public List<ViewSalesReportByUserDTO> viewSalesReportByUserAndCategory(Date entry, Date exit, String TargetuserId,
			int category) throws GoAdminException, ConnectException {
		List<ViewSalesReportByUserDTO> result = new ArrayList<ViewSalesReportByUserDTO>();
		try {
			result = goAdminReportsDao.viewSalesReportByUserAndCategory(entry, exit, TargetuserId, category);
		} catch (GoAdminException e) {
			GoLog.getLogger(GoAdminReportServiceImpl.class).error(e.getMessage());
		}
		return result;

	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * Function Name : viewDetailedSalesReportByProduct Input Parameters : category
	 * , entry , exit ,connection product Return Type : List Throws : Author : Rintu
	 * Creation Date : 21/9/2019 Description : To view amount change, percentage
	 * change, color code, month to month change of products
	 ********************************************************************************************************/

	public List<ViewDetailedSalesReportByProductDTO> viewDetailedSalesReportByProduct(Date entry, Date exit, int cat)
			throws GoAdminException, ConnectException {

		List<ViewDetailedSalesReportByProductDTO> result = new ArrayList<ViewDetailedSalesReportByProductDTO>();
		try {
			result = goAdminReportsDao.viewDetailedSalesReportByProduct(entry, exit, cat);
		} catch (GoAdminException e) {
			GoLog.getLogger(GoAdminReportServiceImpl.class).error(e.getMessage());
		}
		return result;

	}
}
