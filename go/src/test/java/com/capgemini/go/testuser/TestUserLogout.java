package com.capgemini.go.testuser;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.go.dto.UserDTO;
import com.capgemini.go.exception.UserException;
import com.capgemini.go.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserLogout {

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
	@DisplayName("Successful User LogOut")
	@Rollback(true)
	public void testSuccessfulLogOut() throws Exception {
		String userId = "RT03";
		UserDTO userLogout = new UserDTO("", userId, "", "@manRaj1", 0L, 2, false);
		// DO THE LOG IN first
		userService.userLogin(userLogout);
		// now attempt logout
		boolean result = userService.logout(userId);
		assertEquals(result,true);

	}

	@Test
	@DisplayName("Invalid User_ID LogOut")
	@Rollback(true)
	public void testInvalidLogOut(){
		String userId = "xb1";
		assertThrows(UserException.class, () -> {
			userService.logout(userId);
		});
	}

}
