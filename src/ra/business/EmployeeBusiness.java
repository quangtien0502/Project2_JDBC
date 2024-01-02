package ra.business;

import ra.entity.Account;
import ra.entity.Employee;
import ra.entity.Product;
import ra.util.CommonFunction;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeBusiness {
    public static List<Employee> listEmployee(Connection conn) {
        List<Employee> listEmployee = new ArrayList<>();
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL selectEmployee()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee(rs.getString("emp_id"),rs.getString("emp_name"),rs.getDate("birth_of_day"),rs.getString("email"),rs.getString("phone"),rs.getString("address"),rs.getShort("emp_status"));
                listEmployee.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listEmployee;
    }

    public static void insertAndUpdateEmployee(Connection conn, Employee employee, String option) {
        try {
            conn.setAutoCommit(false);
            CallableStatement callableStatement ;
            if(option.trim().equalsIgnoreCase("insert")){
                callableStatement=conn.prepareCall("{CALL InsertEmployee(?,?,?,?,?,?,?)}");
            }else {
                callableStatement=conn.prepareCall("{CALL UpdateEmployeeById(?,?,?,?,?,?,?)}");
            }
            callableStatement.setString(1,employee.getEmpId());
            callableStatement.setString(2,employee.getEmpName());
            callableStatement.setDate(3, CommonFunction.convertToSqlDate(employee.getBirthOfDate()));
            callableStatement.setString(4,employee.getEmail());
            callableStatement.setString(5,employee.getPhone());
            callableStatement.setString(6,employee.getAddress());
            callableStatement.setShort(7,employee.getEmpStatus());
            callableStatement.executeUpdate();
            conn.commit();
            if(option.trim().equalsIgnoreCase("insert")){
                System.out.println("Insert Success");
            } else if (option.trim().equalsIgnoreCase("update")) {
                System.out.println("Update Success");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Employee findEmployeeById(Connection conn, Scanner scanner) {
        System.out.println("Please enter your Employee Id");
        String empId = scanner.nextLine();
        if (CommonFunction.checkDuplicateEmpId(conn, empId)) {
            try {
                CallableStatement callableStatement = conn.prepareCall("{CALL findEmployeeById(?)}");
                callableStatement.setString(1, empId);
                ResultSet rs = callableStatement.executeQuery();
                rs.next();
                return new Employee(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getShort(7));
            } catch (SQLException e) {
                e.printStackTrace();
                return new Employee();
            }
        } else {
            System.err.println("The id doesn't exist in the system");
            return new Employee();
        }

    }

    public static Employee findEmployeeById(Connection conn, String empId) {

        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL findEmployeeById(?)}");
            callableStatement.setString(1, empId);
            ResultSet rs = callableStatement.executeQuery();
            rs.next();
            return new Employee(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getShort(7));
        } catch (SQLException e) {
            e.printStackTrace();
            return new Employee();
        }
    }

    public static List<Employee> findEmployeeByStatus(Connection conn,short empStatus) {
        List<Employee> listEmp=new ArrayList<>();
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL findEmployeeByStatus(?)}");
            callableStatement.setShort(1, empStatus);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                Employee employee=new Employee(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getShort(7));
                listEmp.add(employee);
            }
            return listEmp;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Employee> findEmployeeContainIdOrName(Connection conn,String value){
        List<Employee> listEmployee=new ArrayList<>();
        if(value.trim().isEmpty()){
            value=null;
        }
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL findEmployeeByIdOrName(?,?)}");
            callableStatement.setString(1, value);
            callableStatement.setString(2,value);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                Employee employee=new Employee(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getShort(7));
                listEmployee.add(employee);
            }
            return listEmployee;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
