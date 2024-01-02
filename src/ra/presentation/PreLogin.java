package ra.presentation;

import ra.entity.Account;
import ra.presentation.admin.WarehouseManagement;
import ra.presentation.user.WarehouseManagementForUser;
import ra.util.CommonFunction;
import ra.util.ConnectionDB;

import java.sql.Connection;
import java.util.Scanner;

public class PreLogin {
    public static Account account = CommonFunction.readDataFromFile();

    public static void main(String[] args) {
        Connection conn = ConnectionDB.openConnection();
        Scanner scanner = new Scanner(System.in);

        do {
            if (account.equals(new Account())) {
                CommonFunction.writeDataToFile(account);
                boolean isCorrect = false;
                do {
                    System.out.println("******************Login****************");
                    System.out.println("Please enter your User_Name");
                    String userName = scanner.nextLine();

                    if (CommonFunction.checkDuplicateUserName(conn, userName)) {

                        do {
                            System.out.println("Please enter your password");
                            String password = scanner.nextLine();
                            if (CommonFunction.checkLogin(conn, userName, password)) {
                                System.out.println("Login success");
                                Account account = CommonFunction.loginSuccess(conn, userName, password);
                                CommonFunction.writeDataToFile(account);
                                isCorrect = true;
                            } else {
                                System.err.println("Wrong password");
                            }
                        } while (!isCorrect);
                    } else {
                        System.err.println("UserName doesn't exist in the system");
                    }
                } while (!isCorrect);
            }else {
                if(!account.isPermission()){
                    WarehouseManagement.displayWarehouseManagement(scanner,conn);
                }else {
                    WarehouseManagementForUser.displayUserMenu(scanner,conn);
                }
            }

        } while (true);

    }
}
