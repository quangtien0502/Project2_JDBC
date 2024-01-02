package ra.presentation.admin;

import ra.business.AccountBusiness;
import ra.business.EmployeeBusiness;
import ra.entity.Account;
import ra.entity.Employee;
import ra.presentation.PreLogin;
import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountManagement {
    public static void displayAccount(Scanner scanner, Connection conn){
        boolean isExit = false;
        int choice;
        Account account=new Account();
        do {
            System.out.println("""
                    ******************ACCOUNT MANAGEMENT****************
                    1. Display all accounts data.
                    2. Create new accounts.
                    3. Change account status.
                    4. Find account.
                    5. Logout.
                    6.Back
                    """);
            choice = CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1:
                    account.displayData(AccountBusiness.listAccount(conn));
                    break;
                case 2:
                    account.inputData(scanner,conn);
                    AccountBusiness.insertAndUpdateAccount(conn,account,"insert");
                    break;
                case 3:
                    account=AccountBusiness.findAccountById(conn,scanner);
                    if(!account.equals(new Account())){
                        boolean accStatusUpdate=CommonFunction.checkBoolean("account update status",scanner);
                        account.setAccountStatus(accStatusUpdate);
                        AccountBusiness.insertAndUpdateAccount(conn,account,"update");
                        Employee employee= EmployeeBusiness.findEmployeeById(conn,account.getEmpId());
                        if(!employee.equals(new Employee())){
                            if(accStatusUpdate){
                                employee.setEmpStatus((short) 0);
                                EmployeeBusiness.insertAndUpdateEmployee(conn,employee,"update");
                            }else {
                                employee.setEmpStatus((short) 1);
                                EmployeeBusiness.insertAndUpdateEmployee(conn,employee,"update");
                            }
                        }
                    }
                    break;
                case 4:
                    List<Account> listAcc=AccountBusiness.findAccountContainUserNameOrEmpName(conn,CommonFunction.normalString(scanner,"key word"));
                    account.displayData(listAcc);

                    if(!listAcc.isEmpty()){
                        boolean isExitUpdate=false;
                        do {
                            int indexToUpdateAccount=CommonFunction.checkInteger("account index that you want to update. To exit this update state please press the number that beyond the range of the list",scanner)-1;
                            if((indexToUpdateAccount<listAcc.size()) && (indexToUpdateAccount>=0)){
                                Account account1=listAcc.get(indexToUpdateAccount);
                                boolean accStatusUpdate=CommonFunction.checkBoolean("account update status",scanner);
                                account1.setAccountStatus(accStatusUpdate);
                                AccountBusiness.insertAndUpdateAccount(conn,account1,"update");
                            }else{
                                System.err.println("There are no index like that in the list");
                                isExitUpdate=true;
                            }
                        }while (!isExitUpdate);
                    }

                    break;
                case 5:
                    PreLogin.account=new Account();
                    isExit=true;
                    break;
                case 6:
                    isExit = true;
                    break;
                default:
                    System.err.println("Your choice is not valid value, please try again.");
            }
        }while(!isExit);
    }
}
