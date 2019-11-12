package com.capgemini.go.controller;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.go.dto.ViewDetailedSalesReportByProductDTO;
import com.capgemini.go.dto.ViewSalesReportByUserDTO;
import com.capgemini.go.exception.GoAdminException;
import com.capgemini.go.service.GoAdminReportsService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/Reports")
public class GoAdminReportsController {

	@Autowired
	private GoAdminReportsService goAdminReportsService;

	public GoAdminReportsService getGoAdminReportsService() {
		return goAdminReportsService;
	}

	public void setGoAdminReportsService(GoAdminReportsService goAdminReportsService) {
		this.goAdminReportsService = goAdminReportsService;
	}

	@ResponseBody
	@RequestMapping(value = "/RevenueReports", method = RequestMethod.POST)
	public String getRevenueReports(@RequestBody Map<String, Object> requestData) {

		String userId = requestData.get("retailerId").toString();
		int categoryType = Integer.parseInt(requestData.get("reportType").toString());
		String date1 = requestData.get("startDate").toString();
		String date2 = requestData.get("endDate").toString();

		JsonArray dataList = new JsonArray();
		try {

			Date dentry = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
			Date dexit = new SimpleDateFormat("yyyy-MM-dd").parse(date2);

			List<ViewSalesReportByUserDTO> list = goAdminReportsService.viewSalesReportByUserAndCategory(dentry, dexit,
					userId, categoryType);
			for (ViewSalesReportByUserDTO bean : list) {
				JsonObject dataObj = new JsonObject();
				dataObj.addProperty("userId", bean.getUserId());
				dataObj.addProperty("date", bean.getDate().toString());
				dataObj.addProperty("orderId", bean.getOrderId());
				dataObj.addProperty("productId", bean.getProductId());
				dataObj.addProperty("productCategory", Integer.toString(bean.getProductCategory()));
				dataObj.addProperty("productPrice", Double.toString(bean.getProductPrice()));
				dataList.add(dataObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataList.toString();
	}

	@ResponseBody

	@RequestMapping(value = "/GrowthReports", method = RequestMethod.POST)

	public String getGrowthReports(@RequestBody Map<String, Object> requestData) throws GoAdminException {

		int categoryType = Integer.parseInt(requestData.get("reportType").toString());
		String date1 = requestData.get("startDate").toString();
		String date2 = requestData.get("endDate").toString();

		JsonArray dataList = new JsonArray();
		try {

			Date dentry = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
			Date dexit = new SimpleDateFormat("yyyy-MM-dd").parse(date2);

			List<ViewDetailedSalesReportByProductDTO> list = goAdminReportsService
					.viewDetailedSalesReportByProduct(dentry, dexit, categoryType);
			for (ViewDetailedSalesReportByProductDTO bean : list) {
				JsonObject dataObj = new JsonObject();
				if (categoryType == 1) {
					dataObj.addProperty("period", Month.of(bean.getPeriod() + 1).name());
				} else if (categoryType == 2) {
					dataObj.addProperty("period", "Q" + Integer.toString((bean.getPeriod()) + 1));
				} else {
					dataObj.addProperty("period", "YEAR:" + Integer.toString(bean.getPeriod()));
				}
				dataObj.addProperty("revenue", Double.toString(bean.getRevenue()));
				dataObj.addProperty("amountChange", Double.toString(bean.getAmountChange()));
				dataObj.addProperty("percentageGrowth", Double.toString(bean.getPercentageGrowth()));
				dataObj.addProperty("colorCode", bean.getCode());
				dataList.add(dataObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataList.toString();
	}

}
