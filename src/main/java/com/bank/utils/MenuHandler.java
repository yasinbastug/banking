import java.util.Scanner;
import java.util.function.Consumer;

public class MenuHandler {
    private static Scanner scanner = new Scanner(System.in);

    private static void displayMenu(String title, String[] options) {
        System.out.println("\n--- " + title + " ---");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    private static int getChoice(int numberOfOptions) {
        while (true) {
            System.out.print("Choose an option: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= numberOfOptions) {
                    return choice;
                } else {
                    System.out.println("Invalid choice, please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number.");
            }
        }
    }

    private static void executeMenu(String title, String[] options, Consumer<Void>[] actions) {
        while (true) {
            displayMenu(title, options);
            int choice = getChoice(options.length);
            if (choice == options.length) { // Last option is "Logout"
                System.out.println("Logging out...");
                return;
            }
            actions[choice - 1].accept(null); // Execute the corresponding action
        }
    }

    public static void managerMenu(Manager manager) { // Made public
        String[] options = {
            "Set Interest Rates",
            "Manage Employees",
            "Logout"
        };
        
        Consumer<Void>[] actions = new Consumer[]{
            v -> manager.setInterestRates(),
            v -> manager.manageEmployees()
        };

        executeMenu("Manager Menu", options, actions);
    }

    public static void employeeMenu(Employee employee) { // Made public
        String[] options = {
            "Open Account",
            "Close Account",
            "List Pending Loan Applications",
            "Process Loan Applications",
            "Logout"
        };
        
        Consumer<Void>[] actions = new Consumer[]{
            v -> employee.openAccount(),
            v -> employee.closeAccount(),
            v -> employee.listPendingLoans(),
            v -> employee.processLoanApplication()
        };

        executeMenu("Employee Menu", options, actions);
    }

    public static void clientMenu(Client client) { // Made public
        String[] options = {
            "View Account Details",
            "Deposit",
            "Withdraw",
            "Transfer",
            "Apply for Loan",
            "Repay Loan",
            "Logout"
        };
        
        Consumer<Void>[] actions = new Consumer[]{
            v -> client.viewAccountDetails(),
            v -> client.deposit(),
            v -> client.withdraw(),
            v -> client.transfer(),
            v -> client.applyForLoan(),
            v -> client.repayLoan()
        };

        executeMenu("Client Menu", options, actions);
    }
}
