package ra.presentation.admin.update;

import ra.entity.Employee;
import ra.entity.Product;
import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.Scanner;

public class EmployeeUpdate {
    public static void employeeUpdatePresent(Employee employee, Scanner scanner, Connection conn){
        boolean isExit=false;
        do {
            System.out.println("MENU UPDATE Employee");
            System.out.println("1. Employee Name\n2. Birth of Day\n3. Email\n4. Phone\n5. Address\n6. Back\n");
            int choice= CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1:
                    employee.setEmpName(Employee.inputEmpName(scanner,conn));
                    break;
                case 2:
                    employee.setBirthOfDate(CommonFunction.checkDate(scanner,"Day of Birth"));
                    break;
                case 3:
                    employee.setEmail(CommonFunction.normalStringNotNull(scanner,"Email"));
                    break;
                case 4:
                    employee.setPhone(CommonFunction.normalStringNotNull(scanner,"phone"));
                    break;
                case 5:
                    employee.setAddress(CommonFunction.normalStringNotNull(scanner,"address"));
                    break;
                case 6:
                    isExit=true;
                    break;
            }
        }while (!isExit);
    }
}
