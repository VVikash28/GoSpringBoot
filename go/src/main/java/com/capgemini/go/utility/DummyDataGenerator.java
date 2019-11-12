package com.capgemini.go.utility;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

import com.capgemini.go.dao.RetailerInventoryDao;
import com.capgemini.go.dao.RetailerInventoryDaoImpl;
import com.capgemini.go.dto.RetailerInventoryDTO;
import com.capgemini.go.exception.RetailerInventoryException;

public class DummyDataGenerator {
	
	/**
	 * This function is only to be used to populate the retailer inventory table with dummy data.
	 * It does not check to see if the table exists or not, nor does it delete existing data.
	 * It inserts @numOfSamples number of new entities in the table.
	 */
	public static void initializeRetailerInventory (int numOfSamples) {
		int numberOfSamples = numOfSamples;
		
		RetailerInventoryDao retailer = new RetailerInventoryDaoImpl ();
		Random randomGenerator = new Random();
		Calendar temp = null;
		
		int samplesAdded = 0;
		int samplesRejected = 0;
		boolean flag = false;
		while (samplesAdded != numberOfSamples) {
			String retailerId = "rt" + Integer.toString(randomGenerator.nextInt(10) + 1);
			int productCategory = randomGenerator.nextInt(5) + 1;
			String productUID = "cat" + Integer.toString(productCategory) + "uid" + 
					Integer.toString(randomGenerator.nextInt(9000) + 1000);// + some random 4 digit number
			Calendar dispatchTimeStamp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			temp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			temp.set(randomGenerator.nextInt(100)+1950, randomGenerator.nextInt(12)+1, randomGenerator.nextInt(31)+1);
			flag = ((randomGenerator.nextInt(2)+1) % 2 == 0)?(true):(false);
			Calendar receiveTimeStamp = (flag)?(temp):(null);
			temp.set(Calendar.YEAR, temp.get(Calendar.YEAR)+randomGenerator.nextInt(50));
			temp.set(Calendar.MONTH, temp.get(Calendar.MONTH)+randomGenerator.nextInt(24)+1);
			temp.set(Calendar.DAY_OF_MONTH, temp.get(Calendar.DAY_OF_MONTH)+randomGenerator.nextInt(30)+1);
			Calendar saleTimeStamp = (flag && (randomGenerator.nextInt(2)+1)%2 == 0)?(temp):(null);
			RetailerInventoryDTO newItem = new RetailerInventoryDTO (retailerId, 
					(byte)productCategory, "N/A", productUID, dispatchTimeStamp, receiveTimeStamp, saleTimeStamp);
			try {
				if (retailer.insertItemInRetailerInventory(newItem)) {
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
