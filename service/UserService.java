
package service;

import model.User;
import repository.UserRepository;

public class UserService {

    private UserRepository userRepository;
    private int nextId = 1;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // User Registration
    public String register(String name, String email,
                           String password, String phone,
                           String address) {

        if (name.isBlank() || email.isBlank() ||
            password.isBlank() || phone.isBlank() ||
            address.isBlank()) {
            return "All fields are required!";
        }

        if (!email.contains("@") || !email.contains(".")) {
            return "Invalid email address!";
        }

        if (password.length() < 6) {
            return "Password must contain at least 6 characters!";
        }

        if (userRepository.findByEmail(email) != null) {
            return "Email already registered!";
        }

        User user = new User(nextId++, name, email,
                             password, phone, address);

        userRepository.save(user);

        return "Registration successful!";
    }

    // User Login
    public User login(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    // View Profile
    public User viewProfile(int id) {
        return userRepository.findById(id);
    }

    // Update Profile
    public String updateProfile(int id, String name,
                                String phone, String address) {

        User user = userRepository.findById(id);

        if (user == null) {
            return "User not found!";
        }

        if (name.isBlank() || phone.isBlank() || address.isBlank()) {
            return "Fields cannot be empty!";
        }

        User updatedUser = new User(
            id, name, user.getEmail(),
            user.getPassword(), phone, address
        );

        userRepository.update(updatedUser);

        return "Profile updated successfully!";
    }

    // Delete Account
    public String deleteAccount(int id) {

        if (userRepository.delete(id)) {
            return "Account deleted successfully!";
        }

        return "User not found!";
    }
}
