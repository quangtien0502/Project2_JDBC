package ra.presentation.admin;

import ra.business.AccountBusiness;
import ra.business.EmployeeBusiness;
import ra.entity.Account;
import ra.entity.Employee;
import ra.presentation.PreLogin;
import ra.presentation.admin.update.EmployeeUpdate;
import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagement {
    public static void displayEmployee(Scanner scanner, Connection conn){
        boolean isExit = false;
        Employee employee=new Employee();
        int choice;
        do {
            System.out.println("""
                    ******************EMPLOYEE MANAGEMENT****************
                    1. List of employees
                    2. Add new employee
                    3. Update employee information
                    4. Update employee status
                    5. Search for employees
                    6. Logout
                    7. Back.""");
            choice = CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1:
                    employee.displayData(EmployeeBusiness.listEmployee(conn));
                    break;
                case 2:
                    employee.inputData(scanner,conn);
                    EmployeeBusiness.insertAndUpdateEmployee(conn,employee,"insert");
                    break;
                case 3:
                    employee=EmployeeBusiness.findEmployeeById(conn,scanner);
                    if(!employee.equals(new Employee())){
                        EmployeeUpdate.employeeUpdatePresent(employee,scanner,conn);
                        EmployeeBusiness.insertAndUpdateEmployee(conn,employee,"update");
                    }
                    break;
                case 4:
                    employee=EmployeeBusiness.findEmployeeById(conn,scanner);
                    if(!employee.equals(new Employee())){
                        short empStatusUpdate=Employee.inputEmpStatus(scanner);
                        employee.setEmpStatus(empStatusUpdate);
                        EmployeeBusiness.insertAndUpdateEmployee(conn,employee,"update");
                        Account account=AccountBusiness.findAccountById(conn,0,employee.getEmpId());
                        if(empStatusUpdate==1||empStatusUpdate==2){
                            account.setAccountStatus(false);
                        } else if (empStatusUpdate==0) {
                            account.setAccountStatus(true);
                        }
                        AccountBusiness.insertAndUpdateAccount(conn,account,"update");
                    }
                    break;
                case 5:
                    System.out.println("Please enter your Keyword");
                    List<Employee> listEmp=EmployeeBusiness.findEmployeeContainIdOrName(conn,scanner.nextLine());
                    employee.displayData(listEmp);
                    break;
                case 6:
                    PreLogin.account=new Account();
                    isExit = true;
                    break;
                case 7:
                    isExit = true;
                    break;
                default:
                    System.err.println("Your choice is not valid value, please try again.");
            }
        }while (!isExit);
    }
}
