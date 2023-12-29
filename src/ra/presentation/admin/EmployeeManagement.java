package ra.presentation.admin;

import ra.util.CommonFunction;

import java.util.Scanner;

public class EmployeeManagement {
    public static void displayEmployee(Scanner scanner){
        boolean isExit = false;
        int choice;
        do {
            System.out.println("""
                    ******************EMPLOYEE MANAGEMENT****************
                    1. List of employees
                    2. Add new employee
                    3. Update employee information
                    4. Update employee status
                    5. Search for employees
                    6. Back.""");
            choice = CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1: break;
                case 2: break;
                case 3: break;
                case 4: break;
                case 5: break;
                case 6:
                    isExit = true;
                    break;
                default:
                    System.err.println("Your choice is not valid value, please try again.");
            }
        }while (!isExit);
    }
}
