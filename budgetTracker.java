 /* 
   *Name: Kaiya Cooper
   * Date: 2/27/2026
   */


import java.util.Scanner;

public class cooper_kaiya_budgettracker {

    // constants
    static final int MAX_TRANSACTIONS = 50;
    static final int NUM_CATEGORIES = 5; // 5 categories
    static final double WARNING_THRESHOLD = 0.80; // warn the user that they are nearing 80% of their budget

    // category arrays
    static String[] categoryNames = new String[NUM_CATEGORIES];
    static double[] budgetAmounts = new double[NUM_CATEGORIES];
    static double[] categorySpending = new double[NUM_CATEGORIES];

    // transaction arrays
    static double[] transactionAmounts = new double[MAX_TRANSACTIONS];
    static int[] transactionCategories = new int[MAX_TRANSACTIONS];
    static String[] transactionDescriptions = new String[MAX_TRANSACTIONS];

    static int transactionCount = 0;

    // global scanner to be used by every method so we are not creating new ones
    static Scanner scanner = new Scanner(System.in);

    // main method
    public static void main(String[] args) {

        initializeBudgets(); // category names setup and user input for budget amounts

        // welcome design for user
        System.out.println("-------------------------------------------------------");
        System.out.println("|         Personal Budget and Expense Tracker         |");
        System.out.println("-------------------------------------------------------");

        boolean running = true; // for main loop, set it to false when the user leaves/exits

        while (running) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer after reading int

            switch (choice) {
                case 1:
                    addTransaction();
                    break;
                case 2:
                    displayBudgetSummary();
                    break;
                case 3:
                    displayAllTransactions();
                    break;
                case 4:
                    displayCategoryReport();
                    break;
                case 5:
                    running = false;
                    System.out.println("Thank you for using the Budget Tracker!");
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("ERROR. Please enter a number between 1 and 5 to select an option.");
            }

            // press enter to continue
            if (running) {
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    // displays category names and asks for the users budget at each category
    public static void initializeBudgets() {

        // assign names to array
        categoryNames[0] = "Housing";
        categoryNames[1] = "Food";
        categoryNames[2] = "Transportation";
        categoryNames[3] = "Entertainment";
        categoryNames[4] = "Utilities";

        // set spending to 0 since nothing has been spent yet
        for (int i = 0; i < NUM_CATEGORIES; i++) {
            categorySpending[i] = 0.0;
        }

        // give user the prompt to enter a monthly budget for each category
        System.out.println("------------------------------------------------");
        System.out.println("|               MONTHLY BUDGETS                |");
        System.out.println("------------------------------------------------");
        System.out.println("Please enter your monthly budget for each category: ");

        for (int i = 0; i < NUM_CATEGORIES; i++) {
            System.out.print(categoryNames[i] + " budget: $");
            budgetAmounts[i] = scanner.nextDouble();
            scanner.nextLine(); 
        }

        // confirm total and display setup
        System.out.println("Your budgets have been input successfully!");
        System.out.printf("Total Monthly Budget: $%.2f%n", calculateTotalBudget());
    }

    // print main menun for user to see 
    public static void displayMenu() {
        System.out.println(); // new line
        System.out.println("------------------------------------------");
        System.out.println("|              MAIN MENU                  |");
        System.out.println("------------------------------------------");
        System.out.println("| 1. Add Transaction                      |");
        System.out.println("| 2. View Budget Summary                  |");
        System.out.println("| 3. View All Transactions                |");
        System.out.println("| 4. View Category Report                 |");
        System.out.println("| 5. Exit                                 |");
        System.out.println("------------------------------------------");
    }

    // new expense set up
    public static void addTransaction() {

        // stop early if max transactions reached
        if (transactionCount >= MAX_TRANSACTIONS) {
            System.out.println("ERROR: Maximum number of transactions reached.");
            return;
        }

        System.out.println();
        System.out.println("--------------------------------");
        System.out.println("|       ADD TRANSACTION        |");
        System.out.println("--------------------------------");

        // display each budget category and the remaining amount for the user
        System.out.println("Select a category: ");
        for (int i = 0; i < NUM_CATEGORIES; i++) {
            double remaining = budgetAmounts[i] - categorySpending[i]; // calculate whats left
            System.out.printf("  %d. %-15s (Remaining: $%.2f)%n",
                    (i + 1), categoryNames[i], remaining);
        }

        System.out.print("Enter Category (1-" + NUM_CATEGORIES + "): ");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine(); // clear buffer

        // confirm category user selects is one of the options, if not then cancel transaction
        if (categoryChoice < 1 || categoryChoice > NUM_CATEGORIES) {
            System.out.println("ERROR: INVALID CATEGORY! Transaction Cancelled.");
            return;
        }

        System.out.print("Enter Amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        // make sure the transaction is a positive number
        if (amount <= 0) {
            System.out.println("ERROR: Amount must be greater than zero. Transaction Cancelled.");
            return;
        }

        System.out.print("Enter Transaction Details: ");
        String description = scanner.nextLine();

        // currently choices 1 through 5 are in an array which will place them in a different value order so this will fix the order so they are in the correct place
        int index = categoryChoice - 1;

        // place the transaction information into the arrays
        transactionAmounts[transactionCount] = amount;
        transactionCategories[transactionCount] = index;
        transactionDescriptions[transactionCount] = description;
        transactionCount++; // move counter forward +1

        categorySpending[index] = categorySpending[index] + amount; // this will update the spending for the category selected

        // confirmation message for user
        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println("|         TRANSACTION DETAILS             |");
        System.out.println("------------------------------------------");
        System.out.printf("| Category:    %-27s|%n", categoryNames[index]);
        System.out.printf("| Amount:      $%-26.2f|%n", amount);
        System.out.printf("| Description: %-27s|%n", description);
        System.out.println("------------------------------------------");

        budgetWarning(index); // check to see if the transaction made the total spent closer to the limit
    }

    // checks the spending percentage (close to 80%) and makes sure its not at its limit and prints a warning if it is
    public static void budgetWarning(int categoryIndex) {
        double percentUsed = categorySpending[categoryIndex] / budgetAmounts[categoryIndex];

        if (percentUsed >= 1.0) {
            System.out.println("\u274C WARNING: " + categoryNames[categoryIndex] + " is over the budget!");
        } else if (percentUsed >= WARNING_THRESHOLD) {
            System.out.println("\u26A0 WARNING: " + categoryNames[categoryIndex] + " is near the limit!");
        }
    }

    // recap display for user to see all categories with their budgets spending and status
    public static void displayBudgetSummary() {
        System.out.println();
        System.out.println("----------------------------------------------------------------------");
        System.out.println("|                        BUDGET SUMMARY                              |");
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("| %-15s %10s %10s %12s        |%n",
                "Category", "Budget", "Spent", "Remaining");
        System.out.println("----------------------------------------------------------------------");

        for (int i = 0; i < NUM_CATEGORIES; i++) {
            double remaining = budgetAmounts[i] - categorySpending[i];
            String status = getBudgetStatus(i);
            System.out.printf("| %-15s $%9.2f $%9.2f $%11.2f  %-8s|%n",
                    categoryNames[i], budgetAmounts[i], categorySpending[i], remaining, status);
        }

        // calculate the total and display for user
        double totalBudget = calculateTotalBudget();
        double totalSpent = calculateTotalSpending();
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("| %-15s $%9.2f $%9.2f $%11.2f          |%n",
                "TOTAL", totalBudget, totalSpent, (totalBudget - totalSpent));
        System.out.println("----------------------------------------------------------------------");
    }

    // displays all transactions for user 
    public static void displayAllTransactions() {
        System.out.println();
        System.out.println("-----------------------------------------------------------------");
        System.out.println("|                    ALL TRANSACTIONS                           |");
        System.out.println("-----------------------------------------------------------------");

        if (transactionCount == 0) {
            System.out.println("| No transactions recorded yet.                                |");
            System.out.println("-----------------------------------------------------------------");
            return;
        }

        System.out.printf("| %-4s | %-15s | %-8s | %-24s|%n",
                "#", "Category", "Amount", "Description");
        System.out.println("-----------------------------------------------------------------");

        double totalSpent = 0;
        for (int i = 0; i < transactionCount; i++) {
            System.out.printf("| %-4d | %-15s | $%7.2f | %-24s|%n",
                    (i + 1),
                    categoryNames[transactionCategories[i]], // looking up category names
                    transactionAmounts[i],
                    transactionDescriptions[i]);
            totalSpent = totalSpent + transactionAmounts[i];
        }

        System.out.println("-----------------------------------------------------------------");
        System.out.printf("| Total Transactions: %-5d   Total Spent: $%-20.2f|%n",
                transactionCount, totalSpent);
        System.out.println("-----------------------------------------------------------------");
    }

    // Full report for the input the user selects
    public static void displayCategoryReport() {
        System.out.println();
        System.out.println("----------------------------------");
        System.out.println("|       Category Report          |");
        System.out.println("----------------------------------");
        System.out.println("Please choose a category for a detailed report: ");

        for (int i = 0; i < NUM_CATEGORIES; i++) {
            System.out.println("  " + (i + 1) + ". " + categoryNames[i]);
        }

        System.out.print("Enter category (1-" + NUM_CATEGORIES + "): ");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();

        if (categoryChoice < 1 || categoryChoice > NUM_CATEGORIES) {
            System.out.println("ERROR: Invalid Category, please select another.");
            return;
        }

        int index = categoryChoice - 1; // Make sure the correct value is being selected in the array 
        double spent = categorySpending[index];
        double remaining = budgetAmounts[index] - spent;
        double percentUsed = (spent / budgetAmounts[index]) * 100;
        String status = getBudgetStatus(index);

        System.out.println();
        System.out.println("----------------------------------------------------------------");
        System.out.printf("|         %s CATEGORY REPORT%n", categoryNames[index].toUpperCase());
        System.out.println("----------------------------------------------------------------");
        System.out.printf("| Budget Amount:    $%-42.2f|%n", budgetAmounts[index]);
        System.out.printf("| Amount Spent:     $%-42.2f|%n", spent);
        System.out.printf("| Amount Remaining: $%-42.2f|%n", remaining);
        System.out.printf("| Percent Used:      %-43.1f|%n", percentUsed);
        System.out.printf("| Status:            %-43s|%n", status);
        System.out.println("----------------------------------------------------------------");
        System.out.println("| TRANSACTIONS IN THIS CATEGORY:                                |");
        System.out.println("----------------------------------------------------------------");

        boolean found = false;
        int count = 1;
        for (int i = 0; i < transactionCount; i++) {
            if (transactionCategories[i] == index) { // only print transactions that match the selected category
                System.out.printf("| %d. $%-8.2f - %-46s|%n",
                        count, transactionAmounts[i], transactionDescriptions[i]);
                count++;
                found = true;
            }
        }

        if (!found) {
            System.out.println("| There are no current transactions in this category.           |");
        }
        System.out.println("----------------------------------------------------------------");
    }

    // adds all budget amounts and displays the total
    public static double calculateTotalBudget() {
        double total = 0.0;
        for (int i = 0; i < NUM_CATEGORIES; i++) {
            total = total + budgetAmounts[i];
        }
        return total;
    }

    // adds up all category spending and returns the total
    public static double calculateTotalSpending() {
        double total = 0.0;
        for (int i = 0; i < NUM_CATEGORIES; i++) {
            total = total + categorySpending[i];
        }
        return total;
    }

    // budget status label for how much the budget has been used
    public static String getBudgetStatus(int categoryIndex) {
        double percentUsed = categorySpending[categoryIndex] / budgetAmounts[categoryIndex];

        if (percentUsed >= 1.0) {
            return "OVER BUDGET";
        } else if (percentUsed >= WARNING_THRESHOLD) {
            return "NEAR LIMIT";
        } else {
            return "ON TRACK";
        }
    }

}