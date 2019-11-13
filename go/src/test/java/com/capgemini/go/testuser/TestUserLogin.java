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
public class TestUserLogin {
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
	@DisplayName("User Login Succesfull")
	@Rollback(true)
	public void testAUserLoginSuccess() {
		UserDTO userLogin = new UserDTO(null, "RT03", null, "@manRaj1", 0L, 2, false);
		String actualMessage = null;

		try {

			if (userService.userLogin(userLogin).isUserActiveStatus() == true) {
				actualMessage = InfoConstants.User_Login_Success;
			}

		} catch (UserException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage = InfoConstants.User_Login_Success;

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	@DisplayName("User LogIn Again")
	@Rollback(true)
	public void testBUserRepeatedLogIn() throws UserException {

		UserDTO loggedUser = new UserDTO("", "RT03", "", "@manRaj1", 0L, 2, false);

		assertThrows(UserException.class, () -> {
			userService.userLogin(loggedUser);
		});
		userService.logout("RT03");
	}

	@Test
	@DisplayName("User LogIn Without Registration")
	@Rollback(true)
	public void testCUserUnregisterdLogIn() {
		UserDTO noUser = new UserDTO("", "MN02", "", "m@N123", 0L, 2, false);
		assertThrows(UserException.class, () -> {
			userService.userLogin(noUser);
		});
	}

	@Test
	@DisplayName("User LogIn Without Password")
	@Rollback(true)
	public void testDUserNoPasswordLogIn() {
		UserDTO noPassUser = new UserDTO("", "RT03", "", "", 0L, 2, false);

		assertThrows(UserException.class, () -> {
			userService.userLogin(noPassUser);
		});
	}

	@Test
	@DisplayName("User LogIn With Empty User_Id and Correct Password")
	public void testEUserNoUserIdLogIn() {
		UserDTO noIduser = new UserDTO("", "", "", "@manRaj1", 0L, 2, false);
		assertThrows(UserException.class, () -> {
			userService.userLogin(noIduser);
		});
	}

	@Test
	@DisplayName("User LogIn With Empty User_Id and Empty Password")
	public void testFUserNoCredentialLogIn() {
		UserDTO noCredUser = new UserDTO("", "", "", "", 0L, 2, false);
		assertThrows(UserException.class, () -> {
			userService.userLogin(noCredUser);
		});
	}

	@Test
	@DisplayName("User LogIn With Case Changed Password")
	public void testGUserCaseSensitivePasswordLogIn() {
		UserDTO caseSensPassuser = new UserDTO("", "RT03", "", "@MANrAJ1", 0L, 2, false);
		assertThrows(UserException.class, () -> {
			userService.userLogin(caseSensPassuser);
		});

	}

}
