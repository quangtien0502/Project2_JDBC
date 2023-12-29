package ra.entity;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
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

    @Override
    public void inputData(Scanner scanner, Connection conn) {

    }

    @Override
    public void displayData(List<Employee> listData) {
        printTableHeaderWithBoundaryAndAdditionalFields();
        for (Employee employee : listData) {
            System.out.printf("| %-15s | %-25s | %-20s | %-25s | %-20s | %-25s | %-15s |%n",
                    employee.getEmpId(),employee.getEmpName(),employee.getBirthOfDate(),employee.getEmail(),employee.getPhone(),employee.getAddress(),employee.getEmpStatus());
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
}
