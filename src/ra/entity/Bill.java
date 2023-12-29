package ra.entity;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Bill implements IEntity<Bill>{

    private long billId;
    private String billCode;
    private boolean billType;
    private String empIdCreated;
    private Date created;
    private String empIdAuth;
    private Date authDate;
    private short billStatus;

    public Bill() {
    }

    public Bill(long billId, String billCode, boolean billType, String empIdCreated, Date created, String empIdAuth, Date authDate, short billStatus) {
        this.billId = billId;
        this.billCode = billCode;
        this.billType = billType;
        this.empIdCreated = empIdCreated;
        this.created = created;
        this.empIdAuth = empIdAuth;
        this.authDate = authDate;
        this.billStatus = billStatus;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public boolean isBillType() {
        return billType;
    }

    public void setBillType(boolean billType) {
        this.billType = billType;
    }

    public String getEmpIdCreated() {
        return empIdCreated;
    }

    public void setEmpIdCreated(String empIdCreated) {
        this.empIdCreated = empIdCreated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getEmpIdAuth() {
        return empIdAuth;
    }

    public void setEmpIdAuth(String empIdAuth) {
        this.empIdAuth = empIdAuth;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public short getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(short billStatus) {
        this.billStatus = billStatus;
    }


    @Override
    public void inputData(Scanner scanner, Connection conn) {

    }

    @Override
    public void displayData(List<Bill> listData) {
        printTableHeaderWithBoundaryAndAdditionalFields();
        for (Bill bill : listData) {
            System.out.printf("| %-10d | %-25s | %-15s | %-20s | %-20s | %-20s | %-20s | %-20s |%n",
                    bill.getBillId(),bill.getBillCode(),bill.isBillType(),bill.getEmpIdCreated(),bill.getCreated(),bill.getEmpIdAuth(),bill.getAuthDate().toString(),bill.getBillStatus() );
        }
        printTableFooterWithBoundary();
    }

    private static void printTableHeaderWithBoundaryAndAdditionalFields() {
        printHorizontalLineWithBoundary();
        System.out.printf("| %-10s | %-25s | %-15s | %-20s | %-20s | %-20s | %-20s | %-20s |%n",
                "Account Id", "User Name", "Password", "Permission", "Employee Id", "Account Status");
        printHorizontalLineWithBoundary();
    }
    private static void printTableFooterWithBoundary() {
        printHorizontalLineWithBoundary();
    }

    private static void printHorizontalLineWithBoundary() {
        System.out.println("+------------+---------------------------+-----------------+----------------------+----------------------+----------------------+----------------------+----------------------+");
    }
}
