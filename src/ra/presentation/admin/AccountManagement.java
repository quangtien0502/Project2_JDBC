package ra.presentation.admin;

import ra.util.CommonFunction;

import java.util.Scanner;

public class AccountManagement {
    public static void displayAccount(Scanner scanner){
        boolean isExit = false;
        int choice;
        do {
            System.out.println("""
                    ******************ACCOUNT MANAGEMENT****************
                    1. Display all accounts data.
                    2. Create new accounts.
                    3. Change account status.
                    4. Find account.
                    5. Logout.
                    """);
            choice = CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1: break;
                case 2: break;
                case 3: break;
                case 4: break;
                case 5:
                    isExit = true;
                    break;
                default:
                    System.err.println("Your choice is not valid value, please try again.");
            }
        }while(!isExit);
    }
}
