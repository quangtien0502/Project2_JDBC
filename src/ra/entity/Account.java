package ra.entity;

import ra.util.CommonFunction;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Account implements IEntity<Account>, Serializable {

    private int accountId;
    private String userName;
    private String password;
    private boolean permission;
    private String empId;
    private boolean accountStatus ;

    public Account() {
    }

    public Account(int accountId, String userName, String password, boolean permission, String empId, boolean accountStatus) {
        this.accountId = accountId;
        this.userName = userName;
        this.password = password;
        this.permission = permission;
        this.empId = empId;
        this.accountStatus = accountStatus;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public boolean isAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    public static String inputUserName(Scanner scanner,Connection conn){
        System.out.println("Please enter your User Name");
        do {
            String userName=scanner.nextLine();
            if (!userName.trim().isEmpty()){
                if(!CommonFunction.checkDuplicateEmpName(conn,userName)){
                    return userName;
                }else {
                    System.err.println("The userName already exist in the system, please try again");
                }
            }else {
                System.err.println("You must enter userName");
            }
        }while (true);
    }

    public static String inputEmpId(Scanner scanner,Connection conn){
        System.out.println("Please enter your Employee Id");
        do {
            String empId=scanner.nextLine();
            if(CommonFunction.checkDuplicateEmpId(conn,empId)){
                if(CommonFunction.countNumberEmpInAccount(conn,empId)==0){
                    return empId;
                }else {
                    System.err.println("This Employee Already has an account");
                }
            }else {
                System.err.println("That Id doesn't exists in the system");
            }
        }while (true);
    }

    @Override
    public void inputData(Scanner scanner, Connection conn) {
        this.userName=inputUserName(scanner,conn);
        this.password=CommonFunction.normalStringNotNull(scanner,"password");
        this.permission=CommonFunction.checkBoolean("Permission",scanner,true);
        this.empId=inputEmpId(scanner,conn);
        this.accountStatus=CommonFunction.checkBoolean("Account Status",scanner,true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId && permission == account.permission && accountStatus == account.accountStatus && Objects.equals(userName, account.userName) && Objects.equals(password, account.password) && Objects.equals(empId, account.empId);
    }

    @Override
    public void displayData(List<Account> listData) {
        printTableHeaderWithBoundaryAndAdditionalFields();
        for (Account account : listData) {
            System.out.printf("| %-10d | %-25s | %-25s | %-20s | %-20s | %-20s |%n",
                   account.getAccountId(),account.getUserName(),account.getPassword(),account.isPermission()?"User":"Admin",account.getEmpId(),account.isAccountStatus() ?"active":"block");
        }
        printTableFooterWithBoundary();
    }

    private static void printTableHeaderWithBoundaryAndAdditionalFields() {
        printHorizontalLineWithBoundary();
        System.out.printf("| %-10s | %-25s | %-25s | %-20s | %-20s | %-20s |%n",
                "Account Id", "User Name", "Password", "Permission", "Employee Id", "Account Status");
        printHorizontalLineWithBoundary();
    }

    private static void printTableFooterWithBoundary() {
        printHorizontalLineWithBoundary();
    }

    private static void printHorizontalLineWithBoundary() {
        System.out.println("+------------+---------------------------+---------------------------+----------------------+----------------------+----------------------+");
    }


}
