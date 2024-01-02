package ra.presentation.admin;

import ra.entity.Account;
import ra.presentation.PreLogin;
import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.Scanner;

public class WarehouseManagement {
    public static void displayWarehouseManagement(Scanner scanner, Connection conn) {
        boolean isExit = false;
        int choice;
        do {
            if(PreLogin.account.equals(new Account())){
                isExit=true;
            }else {
                System.out.println("""
                    ******************WAREHOUSE MANAGEMENT****************
                    1. Product management.
                    2. Employee management.
                    3. Account management.
                    4. Receipt management.
                    5. Bill management.
                    6. Report management.
                    7. Logout.
                    8.Exit""");
                choice = CommonFunction.checkInteger("choice",scanner);
                switch (choice) {
                    case 1:
                        ProductManagement.displayProduct(scanner,conn);
                        break;
                    case 2:
                        EmployeeManagement.displayEmployee(scanner,conn);
                        break;
                    case 3:
                        AccountManagement.displayAccount(scanner,conn);
                        break;
                    case 4:
                        ReceiptManagement.displayReceipt(scanner,conn);
                        break;
                    case 5:
                        BillManagement.displayBill(scanner,conn);
                        break;
                    case 6:
                        ReportManagement.displayReportManagement(scanner,conn);
                        break;
                    case 7:
                        isExit = true;
                        break;
                    case 8:
                        System.exit(0);
                    default:
                        System.err.println("Your choice is not valid value, please try again.");
                }
            }

        } while (!isExit);
    }
}
