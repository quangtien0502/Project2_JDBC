package ra.presentation.admin;

import ra.util.CommonFunction;

import java.util.Scanner;

public class ReceiptManagement {
    public static void displayReceipt(Scanner scanner){
        boolean isExit = false;
        int choice;
        do {
            System.out.println("""
                    ******************RECEIPT MANAGEMENT****************
                    1. List of Receipt
                    2. Insert Receipt
                    3. Update Receipt Information
                    4. Receipt Detail
                    5. Approve Receipt
                    6. Find Receipt
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
