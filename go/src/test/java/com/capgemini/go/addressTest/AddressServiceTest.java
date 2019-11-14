package com.capgemini.go.addressTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.go.dto.AddressDTO;

import com.capgemini.go.exception.AddressException;
import com.capgemini.go.exception.ExceptionConstants;

import com.capgemini.go.service.AddressService;
import com.capgemini.go.utility.InfoConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTest {
	@Autowired
	private AddressService addressService;
	private static Logger logger;

	@BeforeAll
	static void setUpBeforeClass() {
		logger = Logger.getRootLogger();
	}

	@BeforeEach
	void setUp() throws Exception {
		logger.info("Test Case Started");

	}

	@AfterEach
	void tearDown() throws Exception {
		logger.info("Test Case Over");
	}

	@Test
	@DisplayName("Address Added Succesfully")
	@Rollback(true)
	public void testAddAddressSuccess() throws AddressException {

		AddressDTO address = new AddressDTO("SR02aqsw3", "SR02", "Varthur Road, 3rd phase", "Delhi", "Manipur",
				"Indonesia", "201597", true);
		String actualMessage = null;

		try {
			if (addressService.addAddress(address)) {
				actualMessage = InfoConstants.Address_Added_Success;
			}
		} catch (AddressException exp) {
			actualMessage = exp.getMessage();
		}

		String expectedMessage = InfoConstants.Address_Added_Success;

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	@DisplayName("Address Addition Failure")
	@Rollback(true)
	public void testAddAddressFailure1() {
		AddressDTO newAddress = new AddressDTO("Add22015", "SR02", "Tilak Nagar, 2nd phase", "57gdgf",
				"Himachal Pradesh", "Malaysia", "201597", true);
		String actualMessage = null;
		try {
			if (addressService.addAddress(newAddress)) {
				actualMessage = InfoConstants.Address_Added_Success;
			}
		} catch (AddressException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage = ExceptionConstants.ADDRESS_ADD_ERROR + "Enter correct City";

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	@DisplayName("Address Addition Failure")
	@Rollback(true)
	public void testAddAddressFailure2() {
		AddressDTO newAddress = new AddressDTO("Add22015", "SR02", "Tilak Nagar, 2nd phase", "Ahmedabad", "qwerty",
				"Malaysia", "201597", true);
		String actualMessage = null;
		try {
			if (addressService.addAddress(newAddress)) {
				actualMessage = InfoConstants.Address_Added_Success;
			}
		} catch (AddressException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage = ExceptionConstants.ADDRESS_ADD_ERROR + "Enter correct state";

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	@DisplayName("Address Addition Failure")
	@Rollback(true)
	public void testAddAddressFailure3() {
		AddressDTO newAddress = new AddressDTO("Add22015", "SR02", "Tilak Nagar, 2nd phase", "Ahmedabad",
				"Chhattisgarh", "ijetdd", "201597", true);
		String actualMessage = null;
		try {
			if (addressService.addAddress(newAddress)) {
				actualMessage = InfoConstants.Address_Added_Success;
			}
		} catch (AddressException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage = ExceptionConstants.ADDRESS_ADD_ERROR + "Enter correct Country";

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	@DisplayName("Address Updation Successfully")
	@Rollback(true)
	public void testUpdateAddressSuccess() {
		AddressDTO updateAddress = new AddressDTO("ARRol55", "RT55", "35B, Varthur Road", "Vadodara",
				"Gujarat", "India", "201597", true);
		String actualMessage = null;
		try {
			System.out.println("updation");
			if (addressService.updateAddress(updateAddress)) {

				actualMessage = InfoConstants.Address_Update_Success;
			}
		} catch (AddressException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage = InfoConstants.Address_Update_Success;
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	@DisplayName("Address deleted successfully")
	@Rollback(true)
	public void testdeleteProductSuccess() {

		String actualMessage = null;
		try {
			if (addressService.deleteAddress("avikAD8")) {
				actualMessage = InfoConstants.Address_Delete_Success;
			}
		} catch (AddressException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage = InfoConstants.Address_Delete_Success;
		assertEquals(expectedMessage, actualMessage);
	}

}
