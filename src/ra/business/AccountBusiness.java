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

public class AccountBusiness {
    public static List<Account> listAccount(Connection conn) {
        List<Account> listAccount = new ArrayList<>();
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL selectAccount()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Account account  = new Account(rs.getInt("acc_id"),rs.getString("user_name"),rs.getString("password"),rs.getBoolean("permission"),rs.getString("emp_id"),rs.getBoolean("acc_status"));
                listAccount.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listAccount;
    }

    public static void insertAndUpdateAccount(Connection conn, Account account,String option) {
        try {
            conn.setAutoCommit(false);
            CallableStatement callableStatement ;
            if(option.trim().equalsIgnoreCase("insert")){
                callableStatement=conn.prepareCall("{CALL InsertAccount(?,?,?,?,?,?)}");
            }else {
                callableStatement=conn.prepareCall("{CALL UpdateAccountById(?,?,?,?,?,?)}");
            }
            callableStatement.setInt(1,account.getAccountId());
            callableStatement.setString(2,account.getUserName());
            callableStatement.setString(3,account.getPassword());
            callableStatement.setBoolean(4,account.isPermission());
            callableStatement.setString(5,account.getEmpId());
            callableStatement.setBoolean(6,account.isAccountStatus());
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

    public static Account findAccountById(Connection conn, Scanner scanner) {
        int acc_id = CommonFunction.checkInteger("Account Id",scanner);
        if (CommonFunction.checkDuplicateAccountId(conn,acc_id)) {
            try {
                CallableStatement callableStatement = conn.prepareCall("{CALL findAccountById(?)}");
                callableStatement.setInt(1, acc_id);
                ResultSet rs = callableStatement.executeQuery();
                rs.next();
                return new Account(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getBoolean(4),rs.getString(5),rs.getBoolean(6));
            } catch (SQLException e) {
                e.printStackTrace();
                return new Account();
            }
        } else {
            System.err.println("The id doesn't exist in the system");
            return new Account();
        }

    }

    public static Account findAccountById(Connection conn,int accountId,String empId){
        try {
            CallableStatement callableStatement;
            if(empId.trim().isEmpty()){
                callableStatement = conn.prepareCall("{CALL findAccountById(?)}");
                callableStatement.setInt(1, accountId);
            }else {
                //Todo: Add procedure findAccountByEmpId
                callableStatement=conn.prepareCall("{CALL findAccountByEmpId(?)}");
                callableStatement.setString(1,empId);
            }


            ResultSet rs = callableStatement.executeQuery();
            rs.next();
            return new Account(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getBoolean(4),rs.getString(5),rs.getBoolean(6));
        } catch (SQLException e) {
            e.printStackTrace();
            return new Account();
        }
    }

    public static List<Account> findAccountContainUserNameOrEmpName(Connection conn,String value){
        List<Account> listAccount=new ArrayList<>();
        if(value.trim().isEmpty()){
            value=null;
        }
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL findAccountByUserNameOrEmployeeName(?,?)}");
            callableStatement.setString(1, value);
            callableStatement.setString(2,value);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                Account account=new Account(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getBoolean(4),rs.getString(5),rs.getBoolean(6));
                listAccount.add(account);
            }
            return listAccount;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
