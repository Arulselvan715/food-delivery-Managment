
package cli;

import java.util.Scanner;
import model.User;
import repository.UserRepository;
import service.UserService;

public class UserCli {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserRepository repository = new UserRepository();
        UserService service = new UserService(repository);

        User loggedInUser = null;

        while (true) {

            System.out.println("\n===== FOOD DELIVERY MANAGEMENT =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. View Profile");
            System.out.println("4. Update Profile");
            System.out.println("5. Delete Account");
            System.out.println("6. Logout");
            System.out.println("7. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter password: ");
                    String password = sc.nextLine();

                    System.out.print("Enter phone: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter address: ");
                    String address = sc.nextLine();

                    System.out.println(service.register(
                        name, email, password, phone, address));
                    break;

                case 2:
                    if (loggedInUser != null) {
                        System.out.println("Already logged in!");
                        break;
                    }

                    System.out.print("Enter email: ");
                    email = sc.nextLine();

                    System.out.print("Enter password: ");
                    password = sc.nextLine();

                    loggedInUser = service.login(email, password);

                    if (loggedInUser != null) {
                        System.out.println("Login successful!");
                        System.out.println("Welcome " +
                                           loggedInUser.getName());
                    } else {
                        System.out.println("Invalid credentials!");
                    }
                    break;

                case 3:
                    if (loggedInUser == null) {
                        System.out.println("Please login first!");
                        break;
                    }

                    User user = service.viewProfile(
                        loggedInUser.getUserId());

                    System.out.println("\n--- USER PROFILE ---");
                    System.out.println("ID: " + user.getUserId());
                    System.out.println("Name: " + user.getName());
                    System.out.println("Email: " + user.getEmail());
                    System.out.println("Phone: " + user.getPhone());
                    System.out.println("Address: " + user.getAddress());
                    break;

                case 4:
                    if (loggedInUser == null) {
                        System.out.println("Please login first!");
                        break;
                    }

                    System.out.print("Enter new name: ");
                    name = sc.nextLine();

                    System.out.print("Enter new phone: ");
                    phone = sc.nextLine();

                    System.out.print("Enter new address: ");
                    address = sc.nextLine();

                    System.out.println(service.updateProfile(
                        loggedInUser.getUserId(),
                        name, phone, address));
                    break;

                case 5:
                    if (loggedInUser == null) {
                        System.out.println("Please login first!");
                        break;
                    }

                    System.out.println(service.deleteAccount(
                        loggedInUser.getUserId()));

                    loggedInUser = null;
                    break;

                case 6:
                    if (loggedInUser == null) {
                        System.out.println("You are not logged in!");
                    } else {
                        loggedInUser = null;
                        System.out.println("Logged out successfully!");
                    }
                    break;

                case 7:
                    System.out.println("Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
