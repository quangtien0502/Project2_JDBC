package ra.entity;

import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    public static String inputEmpId(Scanner scanner,Connection conn,String option){
        System.out.println("Please enter your "+option);
        do {
            String empId=scanner.nextLine();
            if(option.trim().toLowerCase().contains("auth") && empId.isEmpty()){
                return null;
            }
            if(CommonFunction.checkDuplicateEmpId(conn,empId)){
                return empId;
            }else {
                System.err.println("That Id doesn't exists in the system");
            }
        }while (true);
    }
    @Override
    public void inputData(Scanner scanner, Connection conn) {
        this.billCode= CommonFunction.normalStringNotNull(scanner,"bill Code");
        this.empIdCreated=inputEmpId(scanner,conn,"Employee Id Created");
        this.empIdAuth=inputEmpId(scanner,conn,"Employee Id Authenticate");
        this.billStatus= (short) CommonFunction.checkIntegerWithDefaultValue("bill Status",scanner,0);
    }

    public void inputData(Scanner scanner, Connection conn,String empIdCreated,String empIdAuth) {
        this.billCode= CommonFunction.normalStringNotNull(scanner,"bill Code");
        this.empIdCreated=empIdCreated;
        this.empIdAuth=empIdAuth;
        this.billStatus= (short) CommonFunction.checkIntegerWithDefaultValue("bill Status",scanner,0);
    }

    @Override
    public void displayData(List<Bill> listData) {
        printTableHeaderWithBoundaryAndAdditionalFields();
        for (Bill bill : listData) {
            System.out.printf("| %-10d | %-25s | %-15s | %-20s | %-20s | %-20s | %-20s | %-20s |%n",
                    bill.getBillId(),bill.getBillCode(),bill.isBillType()?"Receipt":"Export Bill",bill.getEmpIdCreated(),bill.getCreated(),bill.getEmpIdAuth(),bill.getAuthDate()==null?"null":bill.getAuthDate().toString(),bill.getBillStatus()==0?"Create":bill.getBillStatus()==1?"Canceled":"Approved" );
        }
        printTableFooterWithBoundary();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return billId == bill.billId && billType == bill.billType && billStatus == bill.billStatus && Objects.equals(billCode, bill.billCode) && Objects.equals(empIdCreated, bill.empIdCreated) && Objects.equals(created, bill.created) && Objects.equals(empIdAuth, bill.empIdAuth) && Objects.equals(authDate, bill.authDate);
    }

    private static void printTableHeaderWithBoundaryAndAdditionalFields() {
        printHorizontalLineWithBoundary();
        System.out.printf("| %-10s | %-25s | %-15s | %-20s | %-20s | %-20s | %-20s | %-20s |%n",
                "Bill Id","Bill Code","Bill Type","Emp Id Created","Created","Emp Id Auth","Auth Date","Bill Status");
        printHorizontalLineWithBoundary();
    }
    private static void printTableFooterWithBoundary() {
        printHorizontalLineWithBoundary();
    }

    private static void printHorizontalLineWithBoundary() {
        System.out.println("+------------+---------------------------+-----------------+----------------------+----------------------+----------------------+----------------------+----------------------+");
    }
}
