
package repository;

import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserRepository {

    private List<User> users = new ArrayList<>();

    public void save(User user) {
        users.add(user);
    }

    public User findById(int id) {
        for (User user : users) {
            if (user.getUserId() == id) {
                return user;
            }
        }
        return null;
    }

    public User findByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public List<User> findAll() {
        return users;
    }

    public boolean delete(int id) {
        User user = findById(id);

        if (user != null) {
            users.remove(user);
            return true;
        }

        return false;
    }
}
