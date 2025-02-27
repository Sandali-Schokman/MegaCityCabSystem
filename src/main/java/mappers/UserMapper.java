package mappers;

import dto.UserDTO;
import models.User;

public class UserMapper {

    // Convert User Entity to UserDTO
    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),  // Added password field
                user.getEmail(),
                user.getFullName(),
                user.getPhone(),
                user.getAddress(),
                user.getRole(),
                user.getCreatedAt()  // Added createdAt field
        );
    }

    // Convert UserDTO to User Entity
    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) return null;
        return new User(
                userDTO.getUsername(),
                userDTO.getPassword(),  // Added password field
                userDTO.getEmail(),
                userDTO.getFullName(),
                userDTO.getPhone(),
                userDTO.getAddress(),
                userDTO.getRole(),
                userDTO.getCreatedAt()  // Added createdAt field
        );
    }
}
