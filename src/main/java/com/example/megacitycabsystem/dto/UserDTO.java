package dto;

import java.sql.Timestamp;

public class UserDTO {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private String role;
    private Timestamp createdAt;

    public UserDTO(int userId, String username, String password, String email,
                   String fullName, String phone, String address, String role, Timestamp createdAt) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.createdAt = createdAt;
    }

    // Getters
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getRole() { return role; }
    public Timestamp getCreatedAt() { return createdAt; }
}

