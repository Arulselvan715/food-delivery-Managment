
package repository;

import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserRepository {

    private List<User> users = new ArrayList<>();

    // Save user
    public void save(User user) {
        users.add(user);
    }

    // Find user by ID
    public User findById(int id) {
        for (User user : users) {
            if (user.getUserId() == id) {
                return user;
            }
        }
        return null;
    }

    // Find user by email
    public User findByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    // Get all users
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    // Update user
    public boolean update(User updatedUser) {
        User user = findById(updatedUser.getUserId());

        if (user == null) {
            return false;
        }

        user.setName(updatedUser.getName());
        user.setPhone(updatedUser.getPhone());
        user.setAddress(updatedUser.getAddress());

        return true;
    }

    // Delete user
    public boolean delete(int id) {
        User user = findById(id);

        if (user == null) {
            return false;
        }

        users.remove(user);
        return true;
    }
}
