package services;

import dto.UserDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService();
    }

    @After
    public void tearDown() {
        userService = null;
    }

    @Test
    public void testAuthenticateUser_ValidCredentials() {
        String username = "admin";
        String password = "Admin@123";

        UserDTO user = userService.authenticateUser(username, password);
        assertNotNull("User should be authenticated", user);
        assertEquals(username, user.getUsername());
    }

    @Test
    public void testAuthenticateUser_InvalidCredentials() {
        String username = "testuser";
        String password = "wrongpass";

        UserDTO user = userService.authenticateUser(username, password);
        assertNull("Invalid credentials should return null", user);
    }

    @Test
    public void testResetPassword_EmailExists() {
        String email = "admin@gmail.com";

        String tempPassword = userService.resetPassword(email);
        assertNotNull("Temporary password should be generated", tempPassword);
    }

    @Test
    public void testResetPassword_EmailNotExists() {
        String email = "admin.com";

        String tempPassword = userService.resetPassword(email);
        assertNull("Reset should fail if email not found", tempPassword);
    }

    @Test
    public void testSavePasswordResetToken() {
        String email = "admin@gmail.com";
        String token = "test-token-123";

        boolean result = userService.savePasswordResetToken(email, token);
        assertTrue("Token should be saved successfully", result);
    }

    @Test
    public void testIsValidResetToken_Valid() {
        String token = "test-token-123";

        boolean isValid = userService.isValidResetToken(token);
        assertTrue("Token should be valid", isValid);
    }

    @Test
    public void testIsValidResetToken_Invalid() {
        String token = "invalid-token";

        boolean isValid = userService.isValidResetToken(token);
        assertFalse("Invalid token should return false", isValid);
    }

    @Test
    public void testResetPasswordWithToken() {
        String token = "test-token-123"; // Must exist in DB for a user
        String newPassword = "Admin@1234";
        String hashedPassword = utils.HashUtil.hashPassword(newPassword);

        boolean result = userService.resetPasswordWithToken(token, hashedPassword);
        assertTrue("Password reset with token should succeed", result);
    }

    @Test
    public void testRegisterManager() {
        dto.UserDTO newUser = new UserDTO();
        newUser.setUsername("newmanagername1");
        newUser.setPassword(utils.HashUtil.hashPassword("manager@123"));
        newUser.setEmail("manager1@gmail.com");
        newUser.setFullName("New Manager1");
        newUser.setPhone("0771234523");
        newUser.setAddress("Colombo");
        newUser.setRole("Manager1");

        boolean result = userService.registerManager(newUser);
        assertTrue("Manager should be registered successfully", result);
    }

    @Test
    public void testGetAllManagers() {
        List<UserDTO> managers = userService.getAllManagers();
        assertNotNull("List of managers should not be null", managers);
        assertTrue("There should be at least one manager", managers.size() > 0);
    }

}
