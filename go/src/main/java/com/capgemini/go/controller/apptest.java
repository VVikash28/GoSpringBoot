package com.capgemini.go.controller;

import java.net.ConnectException;

import com.capgemini.go.exception.GoAdminException;
import com.capgemini.go.service.GoAdminReportServiceImpl;
import com.capgemini.go.service.GoAdminReportsService;

public class apptest {
	
	public static void main(String[] args) throws ConnectException, GoAdminException {
		GoAdminReportsService go=new GoAdminReportServiceImpl();
		go.viewSalesReportByUserAndCategory(null, null, "ALL", 5);
		
	}

}
