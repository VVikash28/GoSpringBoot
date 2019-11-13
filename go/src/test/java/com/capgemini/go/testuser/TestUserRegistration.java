package com.capgemini.go.testuser;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.go.dto.UserDTO;
import com.capgemini.go.exception.UserException;
import com.capgemini.go.service.UserService;
import com.capgemini.go.service.UserServiceImpl;
import com.capgemini.go.utility.InfoConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserRegistration {
	UserServiceImpl user = new UserServiceImpl();

	@Autowired
	private UserService userService;

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
	@DisplayName("User Registration With existing USER_MAIL")
	@Rollback(true)
	public void testBUserMailRegistration() throws Exception {
		UserDTO user = new UserDTO("Mayank", "AC1", "amanraj98@gmail.com", "@zacAnd1", 8090683860L, 2, false);
		assertThrows(UserException.class, () -> {
			userService.userRegistration(user);
		});
	}

	@Test
	@DisplayName("User Registration With Existing USER_ID")
	@Rollback(true)
	public void testAExistingIdRegistration() throws Exception {
		UserDTO user = new UserDTO("Mayank", "RT03", "mayan@gmail.com", "@zacAnd1", 8090683860L, 2, false);
		assertThrows(UserException.class, () -> {
			userService.userRegistration(user);
		});
	}

	@Test
	@DisplayName("User Registration With Existing USER_Number")
	@Rollback(true)
	public void testCExistingNumberRegistration() throws Exception {
		UserDTO user = new UserDTO("Mayank", "PU09", "mrtn@gmail.com", "@zacAnd1", 9038670547L, 2, false);
		assertThrows(UserException.class, () -> {
			userService.userRegistration(user);
		});
	}

	@Test
	@DisplayName("Successful User Registration")
	@Rollback(true)
	public void testDSuccessfulRegistration() throws Exception {
		UserDTO user = new UserDTO("Mayank", "AA1", "mayan@gmail.com", "@zacAnd1", 8890683860L, 2, false);
		String actualMessage = null;

		try {

			if (userService.userRegistration(user)) {
				actualMessage = InfoConstants.User_Added_Success;
			}

		} catch (UserException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage = InfoConstants.User_Added_Success;

		assertEquals(expectedMessage, actualMessage);
	}
}
