package ra.presentation.user;

import ra.util.CommonFunction;

import java.util.Scanner;

public class WarehouseManagementForUser {
    public static void displayUserMenu(Scanner scanner){
        boolean isExit = false;
        int choice;
        do {
            System.out.println("""
                    ******************WAREHOUSE MANAGEMENT****************
                    1. List Receipt By Status
                    2. Create Receipt
                    3. Update Receipt
                    4. Find Receipt
                    5. List Export Bills By Status
                    6. Create Export Bills
                    7. Update Export Bills
                    8. Find Export Bills
                    9. Logout
                    10.Exit""");
            choice = CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1: break;
                case 2: break;
                case 3: break;
                case 4: break;
                case 5: break;
                case 6: break;
                case 7: break;
                case 8: break;
                case 9:
                    isExit = true;
                    break;
                case 10:
                    System.exit(0);
                default:
                    System.err.println("Your choice is not valid value, please try again.");
            }
        }while (!isExit);
    }
}
