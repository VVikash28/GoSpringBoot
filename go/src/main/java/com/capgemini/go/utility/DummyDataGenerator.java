package com.capgemini.go.utility;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

import com.capgemini.go.dao.RetailerInventoryDao;
import com.capgemini.go.dto.RetailerInventoryDTO;
import com.capgemini.go.exception.RetailerInventoryException;

public class DummyDataGenerator {
	
	public static RetailerInventoryDao retailerInventoryDao;
	/**
	 * This function is only to be used to populate the retailer inventory table with dummy data.
	 * It does not check to see if the table exists or not, nor does it delete existing data.
	 * It inserts @numOfSamples number of new entities in the table.
	 */
	public static void initializeRetailerInventory (int numOfSamples) {
		String retailerIds [] = {"avik", "GO09", "QW01", "RT03", "RT1235", "RT1239",
				"RT1240", "RT421", "RT55", "SR01", "TY900", "TY989", "TYP09", "USER01",
				"USERA22", "UU555", "XX99"};
		
		String productIds [] = {"prod01", "prod02", "prod03", "prod04", "prod05",
				"prod06", "prod07", "prod08", "prod11", "prod12", "prod1245", "prod12458",
				"prod18", "prod26", "prod420", "prod99", "prodxxx"};
		
		int productCat [] = {2, 1, 3, 5, 3, 5, 5, 1, -1, 5, 4, 5, 3, 5, 3, 5, 1};
		
		int numberOfSamples = numOfSamples;
		
		Random randomGenerator = new Random();
		Calendar temp = null;
		
		int samplesAdded = 0;
		int samplesRejected = 0;
		boolean flag = false;
		while (samplesAdded != numberOfSamples) {
			int index = randomGenerator.nextInt(retailerIds.length);
			String retailerId = retailerIds[index];
			
			index = randomGenerator.nextInt(productIds.length);
			int productCategory = productCat[index];
			String productId = productIds[index];
			String productUID = productId + Integer.toString(randomGenerator.nextInt(9000) + 1000);// + some random 4 digit number
			
			int baseYear = 2000, baseMonth = 0, baseDay = 1;
			Calendar dispatchTimeStamp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			int year = baseYear + randomGenerator.nextInt(2), month = baseMonth + randomGenerator.nextInt(12), day = baseDay + randomGenerator.nextInt(31);
			dispatchTimeStamp.set (year, month, day);
			
			temp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			year = year + randomGenerator.nextInt(2); month = month + randomGenerator.nextInt(12); day = day + randomGenerator.nextInt(31);
			temp.set(year, month, day);
			
			flag = ((randomGenerator.nextInt(2)) % 2 == 0)?(true):(false);
			Calendar receiveTimeStamp = (flag)?(temp):(null);
			
			year = year + randomGenerator.nextInt(2); month = month + randomGenerator.nextInt(12); day = day + randomGenerator.nextInt(31);
			temp.set(year, month, day);
			Calendar saleTimeStamp = (flag && (randomGenerator.nextInt(2))%2 == 0)?(temp):(null);
			RetailerInventoryDTO newItem = new RetailerInventoryDTO (retailerId, 
					(byte)productCategory, productId, productUID, dispatchTimeStamp, receiveTimeStamp, saleTimeStamp);
			try {
				if (retailerInventoryDao.insertItemInRetailerInventory(newItem)) {
					samplesAdded++;
				}
			} catch (RetailerInventoryException e) {
				System.out.println(e.getMessage());
				samplesRejected++;
			}
		}
		System.out.println("Total Samples: " + numberOfSamples + "; Samples Added: " 
				+ samplesAdded + "; Samples Rejected: " + samplesRejected);
		return;
	}
}
