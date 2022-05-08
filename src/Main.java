import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int userChoice = 0;
        List<Client> clients = new ArrayList<>();
        List<Worker> workers = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        VirtualStore virtualStore = new VirtualStore(clients, workers, products);
        do {
            System.out.println("Welcome!");
            System.out.println("Please chose an option:");
            System.out.println("For create a new account enter: 1 ");
            System.out.println("For log into existing account enter: 2 ");
            System.out.println("For exit enter: 3 ");
            try {
                userChoice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("PLEASE SELECT ONLY ONE OF THE OPTIONS (1/2/3) ");
                scanner.nextLine();
            }
            switch (userChoice) {
                case 1:
                    virtualStore.newUser();
                    break;
                case 2:
                    String accountType;
                    boolean found = false;
                    boolean inValidInput = true;
                    do {
                        do {
                            System.out.println("Do you want to enter a Client account or a Worker account?");
                            accountType = scanner.nextLine();
                            if (!accountType.toUpperCase().equals("W") && !accountType.toUpperCase().equals("C")) {
                                System.out.println("PLEASE SELECT ONLY ONE OF THE OPTIONS! (W/C) ");
                            } else
                                inValidInput = false;
                        } while (inValidInput);
                        System.out.println("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.println("Enter password: ");
                        String password = scanner.nextLine();

                        if (accountType.toUpperCase().equals("W")) {
                            Worker worker = virtualStore.workerLoginAccount(username, password);
                            if (worker != null) {
                                found = true;
                            }
                        }
                        if (accountType.toUpperCase().equals("C")) {
                            Client client = virtualStore.clientLoginAccount(username, password);
                            if (client != null) {
                                found = true;
                            }
                        }
                    } while (!found);
            }
        } while (userChoice != 3);
    }
}