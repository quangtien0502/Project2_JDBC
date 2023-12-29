package ra.presentation.admin;

import ra.util.CommonFunction;

import java.util.Scanner;

public class BillManagement {
    public static void displayBill(Scanner scanner){
        boolean isExit = false;
        int choice;
        do {
            System.out.println("""
                    ******************BILL MANAGEMENT****************
                    1. List of Export Bills
                    2. Create export Bills
                    3. Update export Bills
                    4. Export Bill Details
                    5. Approve export Bills
                    6. Find export Bills
                    7. Back.""");
            choice = CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1: break;
                case 2: break;
                case 3: break;
                case 4: break;
                case 5: break;
                case 6: break;
                case 7:
                    isExit = true;
                    break;
                default:
                    System.err.println("Your choice is not valid value, please try again.");
            }
        }while (!isExit);
    }
}
