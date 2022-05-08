import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class VirtualStore {
    private List<Worker> workerList;
    private List<Client> clientsList;
    private List<Product> productList;
    private List<Product> productListInStock;

    public VirtualStore(List<Client> clientsList, List<Worker> workerList, List<Product> productList) {
        this.workerList = workerList;
        this.clientsList = clientsList;
        this.productList = productList;
        this.productListInStock = new ArrayList<>();
    }

    public void addClient(Client newClient) {
        this.clientsList.add(newClient); }

    public void addWorker(Worker newWorker) {
        this.workerList.add(newWorker);
    }
    public void newUser() {
        String firstName;
        String lastName;
        String accountType;
        String userName;
        String password;
        String memberAns;
        int workerChoice = 0;
        boolean error;
        boolean member = false;
        Rank rank = null;
        Client newClient;

        Scanner scanner = new Scanner(System.in);
        do {  System.out.println("lets open a new account! \n if you are worker enter: W \n if you are client enter: C");
            accountType = scanner.nextLine();
        } while (!accountType.toUpperCase().equals("W") && !accountType.toUpperCase().equals("C"));
        do {
            System.out.println("please enter your first name");
            firstName = scanner.nextLine();
        } while (checkIfContainsNum(firstName));
        do {
            System.out.println("please enter your last name");
            lastName = scanner.nextLine();
        } while (checkIfContainsNum(lastName));
        do {
            System.out.println("please enter your username");
            userName = scanner.nextLine();

            System.out.println("please chose your password");
            password = scanner.nextLine();
        } while ((ifPasswordValid(password) || (checkIfUserNameTaken(userName, accountType))));

        do {
            System.out.println("do you are a member? enter y (for yes) or n (for no)");
            memberAns = scanner.nextLine();
            if (memberAns.toUpperCase().equals("Y")) {
                member = true;
            }
        } while (!memberAns.toUpperCase().equals("Y") && !memberAns.toUpperCase().equals("N"));
        if (accountType.toUpperCase().equals("C")) {
            newClient = new Client(firstName, lastName, userName, password, member);
            addClient(newClient);
        }

        if (accountType.toUpperCase().equals("W")) {
            do {
                do {
                    System.out.println("If you regular worker - 1");
                    System.out.println("If you manager - 2");
                    System.out.println("If you friend in management - 3");
                    try {
                        error = false;
                        workerChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (workerChoice == 1) {
                            rank = Rank.REGULAR_WORKER;
                        } else if (workerChoice == 2) {
                            rank = Rank.MANAGER;
                        } else if (workerChoice == 3) {
                            rank = Rank.MEMBER_IN_MANAGEMENT_TEAM;
                        }

                        Worker newWorker = new Worker(firstName, lastName, userName, password, member, rank);
                        addWorker(newWorker);
                    } catch (Exception e) {
                        System.out.println("INVALID INPUT! \n please try again");
                        scanner.nextLine();
                        error = true;
                    }
                } while (workerChoice > 3 || workerChoice < 1);
            } while (error);
        }
        System.out.println("your user has been successfully received! \n ");
    }

    public Worker workerLoginAccount(String username, String password) {
        Worker found = null;
        for (Worker currentUser : this.workerList) {
            if (currentUser.getUserName().equals(username) && currentUser.getPassword().equals(password)) {
                found = currentUser;
            }
            if (found != null) {
                workerMenu(found);
            } else {
                System.out.println("the user you entered does not exist \n please try again ");
            }
        }
        return found;
    }

    public Client clientLoginAccount(String username, String password) {
        Client found = null;
        for (Client currentUser : this.clientsList) {
            if (currentUser.getUserName().equals(username) && currentUser.getPassword().equals(password)) {
                found = currentUser;
                break;
            }
        }
        if (found != null) {
            System.out.print("Hello " + found.getFirstName() + " " + found.getLastName());
            if (found.isMember())
                System.out.println("(VIP)");

            System.out.println(" ********************************* \n");
            purchase(found);
        } else {
            System.out.println("the user does not exist");
        }
        return found;
    }

    public void purchase(Client client) {
        Scanner scanner = new Scanner(System.in);
        Cart cartShop = new Cart  ();
        int numOfItems;
        double updatedThePrice;
        int sumOfTheProduct;
        boolean invalidOption;
        do {
            do {
                invalidOption = false;
                printProductsThatInStock();
                System.out.println("Please enter the number of the items that you want to add to your cart or if you wants to finish the shop enter:  -1  ");
                numOfItems = scanner.nextInt();
                scanner.nextLine();
                if (numOfItems < -1 || numOfItems >= this.productList.size())
                    invalidOption = true;
            } while (invalidOption);

            if (numOfItems != -1) {
                do {
                    System.out.println("Please enter the amount of the units that you want: ");
                    sumOfTheProduct = scanner.nextInt();
                    if (sumOfTheProduct > 0) {
                        Product chosenProduct = this.productListInStock.get(numOfItems);
                        cartShop.addForProductList(chosenProduct);
                        if (client.isMember()) {
                            updatedThePrice = updatedPriceForMemberClients(client, chosenProduct);
                        } else
                            updatedThePrice = chosenProduct.getPrice();
                        cartShop.addToTheBill((updatedThePrice * sumOfTheProduct));
                        System.out.println("The product added to your cart!");
                        System.out.println(cartShop.toString());
                    }else
                        System.out.println("PLEASE CHOOSE NUMBER UP TO 0");
                } while (sumOfTheProduct <= 0);
            }

        } while (numOfItems != -1);

        if (client instanceof Worker) {
            Worker worker = (Worker) client;
            double finalBillForWorker = worker.updateTheBillOfTheWorker(cartShop.getBill());
            cartShop.setBill(finalBillForWorker);
            this.clientsList.add(client);
        }
        System.out.println("*****************************************");
        System.out.println("YOUR FINAL BILL IS: " + cartShop.getBill());
        System.out.println("***************************************** \n");
        client.updateTotalPaid(cartShop.getBill());
        client.updateTheLastDate();
        client.updateTheSumOfTheItems();
    }
    public boolean ifPasswordValid(String password) {
        if (password.length() >= 6) {
            return false;
        } else {
            System.out.println("The password is not long enough!");
            return true;
        }
    }
    public boolean checkIfContainsNum(String name) {
        for (int i = 0; i < name.length(); i++) {
            char currentChar = name.charAt(i);
            if (currentChar >= '0' && currentChar <= '9') {
                System.out.println("Please enter only letters!!");
                return true;
            }
        }
        return false;
    }
    public boolean checkIfUserNameTaken(String newUserName, String typeAccount) {
        boolean exist = false;
        if (typeAccount.equals("C")) {
            for (Client currentBuyer : this.clientsList) {
                if (currentBuyer != null) {
                    if (currentBuyer.getUserName().equals(newUserName)) {
                        exist = true;
                        break;
                    }
                }
            }
        }
        if (typeAccount.equals("W")) {
            for (Worker currentWorker : this.workerList) {
                if (currentWorker != null) {
                    if (currentWorker.getUserName().equals(newUserName)) {
                        exist = true;
                        break;
                    }
                }
            }
        }
        if (exist) {
            System.out.println("This username is already taken!");
        }
        return exist;
    }

    public void workerMenu(Worker account) {
        Scanner scanner = new Scanner(System.in);
        boolean disconnected = true;
        int chosen;
        System.out.println("Hello " + account.getFirstName() + " " + account.getLastName() + " " + "(" + account.getRank() + ")!");
        System.out.println("*************************************** \n");
        do {
            System.out.println("For print the list of all the costumers enter: 1");
            System.out.println("For print the list of all the costumers that members enter: 2");
            System.out.println("For print the list of all the customers who have made at least one purchase enter: 3");
            System.out.println("For print the customer with the highest bill enter: 4");
            System.out.println("For add a new product enter: 5 ");
            System.out.println("For change inventory status for product enter: 6");
            System.out.println("For make a purchase enter: 7");
            System.out.println("For disconnected enter: 8");
            chosen = scanner.nextInt();

            switch (chosen) {
                case 1:
                    printAllClients();
                    break;
                case 2:
                    printMemberedClients();
                    break;
                case 3:
                    printCustomersWhoBoughtAtLeastOnce();
                    break;
                case 4:
                    printTheCustomerWithTheHighestBill();
                    break;
                case 5:
                    addNewProduct();
                    break;
                case 6:
                    updateStockProduct();
                    break;
                case 7:
                    purchase(account);
                    break;
                case 8:
                    disconnected = false;
                    break;
            }
        } while (disconnected);
    }

    public void updateStockProduct() {
        Scanner scanner = new Scanner(System.in);
        printProducts();
        System.out.println("Enter the num of the product that you want to update: ");
        int numOfProduct = scanner.nextInt();
        scanner.nextLine();
        System.out.println("If this product found in stock enter Y , if is not enter N");
        String update = scanner.nextLine();
        Product chosenProduct = productList.get(numOfProduct - 1);
        if (update.toUpperCase().equals("Y")) {
            if (!this.productListInStock.contains(chosenProduct)) {
                chosenProduct.setInStock(true);
                this.productListInStock.add(chosenProduct);
            }
        } else if (update.toUpperCase().equals("N")) {
            chosenProduct.setInStock(false);
            this.productListInStock.remove(chosenProduct);
        } else
            System.out.println(" INVALID INPUT! Please try again ");
    }

    public void printAllClients() {
        System.out.println("********************************");
        System.out.println("ALL THE CLIENTS ARE: ");
        for (Client currentClient : this.clientsList) {
            System.out.println(currentClient.toString());
            System.out.println("***************************** ");
        }
        System.out.println("-------------------------------");
    }

    public void printMemberedClients() {
        System.out.println("**********************************");
        System.out.println("THE CUSTOMERS WHO ARE MEMBERS: ");
        for (Client currentClient : this.clientsList) {
            if (currentClient.isMember()) {
                System.out.println(currentClient.toString());
                System.out.println("****************************** ");
            }
        }
        System.out.println("------------------------------------");
    }

    public void printCustomersWhoBoughtAtLeastOnce() {
        System.out.println("***************************************");
        System.out.println("THE CUSTOMERS WHO BOUGHT AT LIST ONCE ARE : ");
        for (Client currentClient : this.clientsList) {
            if (currentClient.getTotalItems() != 0) {
                System.out.println(currentClient.toString());
                System.out.println("------------------------------------ \n");
            }
        }
        System.out.println("**************************************** \n");
    }

    public void printTheCustomerWithTheHighestBill() {
        System.out.println("**************************************");
        System.out.println("THE CUSTOMER WITH THE HIGHEST BILL IS: ");
        if (clientsList.stream().max(Comparator.comparing(Client::getTotalPaid)).isPresent()) {
            Client highestBuyClient = clientsList.stream().max(Comparator.comparing(Client::getTotalPaid)).get();
            System.out.println(highestBuyClient.toString());
            System.out.println("************************************");
        }
    }

    public void addNewProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a description for the new product: ");
        String description = scanner.nextLine();
        System.out.println("Please enter the price of the new product: ");
        double price = scanner.nextDouble();
        System.out.println("Please enter the discount for members customers for this product: ");
        int discount = scanner.nextInt();
        Product newProduct = new Product(description, price, discount);
        productList.add(newProduct);
        productListInStock.add(newProduct);
        System.out.println("The product added successfully!!");
        System.out.println("------------------------------------");
    }

    public void printProducts() {
        int count = 1;
        for (Product currentProduct : this.productList) {
            System.out.println(count + ". " + currentProduct.toString());
            count++;
        }
    }

    public void printProductsThatInStock() {
        int count = 0;
        for (Product currentProduct : this.productListInStock) {
            if (currentProduct.isInStock()) {
                System.out.println(count + ". " + currentProduct.toString());
                count++;
            }
        }
    }

    public double updatedPriceForMemberClients(Client client, Product product) {
        double finalPrice = 0;
        double precent = (100.0 - product.getDiscount()) / 100.0;
        if (client.isMember()) {
            finalPrice = product.getPrice() * precent; // CALCULATE THE FINAL PRICE AFTER PRESENTS DISCOUNT
        }
        return finalPrice;
    }


}

