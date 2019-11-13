package com.capgemini.go.utility;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtil {
	private static List<String> cityList;
	private static List<String> stateList;
	private static List<String> countryList;

	public static void initialiseCityList() {
		cityList = new ArrayList<>();
		cityList.add("Gwalior");
		cityList.add("Kolkata");
		cityList.add("Mumbai");
		cityList.add("Delhi");
		cityList.add("Bangalore");
		cityList.add("Hyderabad");
		cityList.add("Ahmedabad");
		cityList.add("Chennai");
		cityList.add("Surat");
		cityList.add("Pune");
		cityList.add("Jaipur");
		cityList.add("Lucknow");
		cityList.add("Kanpur");
		cityList.add("Nagpur");
		cityList.add("Vadodara");
		cityList.add("Ghaziabad");
		cityList.add("Agra");
		cityList.add("Varanasi");
	}

	public static List<String> getCityList() {
		return cityList;
	}

	public static void initialiseStateList() {
		stateList = new ArrayList<>();
		stateList.add("Andhra Pradesh");
		stateList.add("Arunachal Pradesh");
		stateList.add("Assam");
		stateList.add("Bihar");
		stateList.add("Chhattisgarh");
		stateList.add("Goa");
		stateList.add("Gujarat");
		stateList.add("Haryana");
		stateList.add("Himachal Pradesh");
		stateList.add("Jharkhand");
		stateList.add("Karnataka");
		stateList.add("Kerala");
		stateList.add("Maharashtra");
		stateList.add("Manipur");
		stateList.add("Meghalaya");
		stateList.add("Mizoram");
		stateList.add("Nagaland");
		stateList.add("Odisha");
		stateList.add("Punjab");
		stateList.add("Rajasthan");
		stateList.add("Sikkim");
		stateList.add("Tamil Nadu");
		stateList.add("Telangana");
		stateList.add("Tripura");
		stateList.add("Uttar Pradesh");
		stateList.add("Uttarakhand");
		stateList.add("West Bengal");

	}

	public static List<String> getStateList() {
		return stateList;
	}
	
	public static void initialiseCountryList() {
		countryList = new ArrayList<>();
		countryList.add("Afghanistan");
		countryList.add("Armenia");
		countryList.add("Azerbaijan");
		countryList.add("Bahrain");
		countryList.add("Bangladesh");
		countryList.add("Bhutan");
		countryList.add("Brunei");
		countryList.add("Cambodia");
		countryList.add("China");
		countryList.add("Cyprus");
		countryList.add("Georgia");
		countryList.add("India");
		countryList.add("Indonesia");
		countryList.add("Iran");
		countryList.add("Iraq");
		countryList.add("Israel");
		countryList.add("Japan");
		countryList.add("Jordan");
		countryList.add("Kazakhstan");
		countryList.add("Kuwait");
		countryList.add("Kyrgyzstan");
		countryList.add("Laos");
		countryList.add("Lebanon");
		countryList.add("Malaysia");
		countryList.add("Maldives");
		countryList.add("Mongolia");
		countryList.add("Myanmar");
		countryList.add("Nepal");
		countryList.add("Oman");
		countryList.add("Palestine");
		countryList.add("Philippines");
			
	}
	public static List<String> getCountryList() {
		return countryList;
	}
	
}
