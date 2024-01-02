package ra.entity;

import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Employee implements IEntity<Employee>{

    private String empId;
    private String empName;
    private Date birthOfDate;
    private String email;
    private String phone;
    private String address;
    private short empStatus;

    public Employee() {
    }

    public Employee(String empId, String empName, Date birthOfDate, String email, String phone, String address, short empStatus) {
        this.empId = empId;
        this.empName = empName;
        this.birthOfDate = birthOfDate;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.empStatus = empStatus;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Date getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(Date birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public short getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(short empStatus) {
        this.empStatus = empStatus;
    }

    public static String inputEmpId(Scanner scanner,Connection conn){
        System.out.println("Please enter your Employee Id");
        do {
            String empId=scanner.nextLine();
            if (!empId.isEmpty()){
                if(empId.length()==5){
                    if(!CommonFunction.checkDuplicateEmpId(conn,empId)){
                        return empId;
                    }else {
                        System.err.println("The Id already exist in the system, please try again");
                    }
                }else {
                    System.err.println("Employee Id must has 5 characters");
                }
            }else {
                System.err.println("You must enter Employee Id");
            }
        }while (true);
    }

    public static String inputEmpName(Scanner scanner,Connection conn){
        System.out.println("Please enter your Employee Name");
        do {
            String empName=scanner.nextLine();
            if (!empName.trim().isEmpty()){
                if(!CommonFunction.checkDuplicateEmpName(conn,empName)){
                    return empName;
                }else {
                    System.err.println("The Employee Name already exist in the system, please try again");
                }
            }else {
                System.err.println("You must enter Employee Name");
            }
        }while (true);
    }

    public static short inputEmpStatus(Scanner scanner){
        do {
            short empStatus=CommonFunction.checkShort("Emp Status",scanner);
            if(empStatus==1 || empStatus==0 || empStatus==2){
                return empStatus;
            }else {
                System.err.println("Employee Status only receive value from 0 to 2");
            }

        }while (true);
    }

    @Override
    public void inputData(Scanner scanner, Connection conn) {
        this.empId=inputEmpId(scanner,conn);
        this.empName=inputEmpName(scanner,conn);
        this.birthOfDate=CommonFunction.checkDate(scanner,"day of birth");
        this.email=CommonFunction.normalStringNotNull(scanner,"email");
        this.phone=CommonFunction.normalStringNotNull(scanner,"phone");
        this.address=CommonFunction.normalStringNotNull(scanner,"address");
        this.empStatus=inputEmpStatus(scanner);
    }

    @Override
    public void displayData(List<Employee> listData) {
        printTableHeaderWithBoundaryAndAdditionalFields();
        for (Employee employee : listData) {
            System.out.printf("| %-15s | %-25s | %-20s | %-25s | %-20s | %-25s | %-15s |%n",
                    employee.getEmpId(),employee.getEmpName(),employee.getBirthOfDate(),employee.getEmail(),employee.getPhone(),employee.getAddress(),employee.getEmpStatus()==0?"Active":employee.getEmpStatus()==1?"On leave":"Job's quit");
        }
        printTableFooterWithBoundary();
    }

    private static void printTableHeaderWithBoundaryAndAdditionalFields() {
        printHorizontalLineWithBoundary();
        System.out.printf("| %-15s | %-25s | %-20s | %-25s | %-20s | %-25s | %-15s |%n",
                "Employee Id", "Employee Name", "Birthday", "Email", "Phone", "Address","Employee Status");
        printHorizontalLineWithBoundary();
    }

    private static void printTableFooterWithBoundary() {
        printHorizontalLineWithBoundary();
    }

    private static void printHorizontalLineWithBoundary() {
        System.out.println("+-----------------+---------------------------+----------------------+---------------------------+----------------------+---------------------------+-----------------+");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return empStatus == employee.empStatus && Objects.equals(empId, employee.empId) && Objects.equals(empName, employee.empName) && Objects.equals(birthOfDate, employee.birthOfDate) && Objects.equals(email, employee.email) && Objects.equals(phone, employee.phone) && Objects.equals(address, employee.address);
    }

}
