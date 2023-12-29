package ra.entity;

import java.io.Serializable;
import java.util.List;

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



    @Override
    public void inputData() {

    }

    @Override
    public void displayData(List<Account> listData) {
        printTableHeaderWithBoundaryAndAdditionalFields();
        for (Account account : listData) {
            System.out.printf("| %-10d | %-25s | %-25s | %-20s | %-20s | %-20s |%n",
                   account.getAccountId(),account.getUserName(),account.getPassword(),account.isPermission(),account.getEmpId(),account.isAccountStatus() );
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
