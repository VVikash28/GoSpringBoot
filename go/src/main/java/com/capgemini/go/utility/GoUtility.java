package com.capgemini.go.utility;

import java.time.Duration;
import java.time.Period;
import java.util.Calendar;

import com.capgemini.go.exception.ExceptionConstants;

public class GoUtility {
	/**
	 * - Function Name : calculatePeriod
	 * - Description : Calculates and returns a Period object containing the period in (years, months, days) <br>
	 * between two Calendar instances. <br>
	 * - Author : Kunal <br>
	 * 
	 * @param timestamp1 - This timestamp must come before <code>timestamp2</code>
	 * @param timestamp2 - This timestamp must come after <code>timestamp1</code>
	 * @return Period - Period of (years, months, )
	 */
	public static Period calculatePeriod (Calendar timestamp1, Calendar timestamp2) throws RuntimeException {
		if (timestamp1.getTime().after(timestamp2.getTime())) {
			GoLog.getLogger(GoUtility.class).error("calculatePeriod - " + ExceptionConstants.INAPPROPRIATE_ARGUMENT_PASSED);
			throw new RuntimeException ("calculatePeriod - " + ExceptionConstants.INAPPROPRIATE_ARGUMENT_PASSED);
		}
		else {
			long days = Duration.between (timestamp1.toInstant(), timestamp2.toInstant()).toDays();
			int yearsElapsed = (int) (days / 365);
			int remainingDays = (int) (days % 365);
			int monthsElapsed = remainingDays / 30;
			int daysElapsed = remainingDays % 30;
			GoLog.getLogger(GoUtility.class).info("calculatePeriod - {Years: " + yearsElapsed + " Months: " + monthsElapsed + " Days: " + daysElapsed + "}");
			return Period.of (yearsElapsed, monthsElapsed, daysElapsed);
		}
	}
	
	public static String getCategoryName (int categoryNumber) {
		switch (categoryNumber) {
		case 1: {return "CAMPING";}
		case 2: {return "GOLF";}
		case 3: {return "MOUNTAINEERING";}
		case 4: {return "OUTDOOR";}
		case 5: {return "PERSONAL";}
		default : {return "OTHER";}
		}
	}
}
