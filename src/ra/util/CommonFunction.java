package ra.util;

import ra.entity.Account;

import java.io.*;
import java.sql.*;
import java.util.Scanner;


public class CommonFunction {

    public static int checkInteger(String option, Scanner scanner){
        do {
            try{
                System.out.printf("Please enter your %s \n",option);
                return Integer.parseInt(scanner.nextLine());

            }catch (NumberFormatException nfx){
                System.err.println("Please enter an Integer value");
            }
        }while (true);
    }

    public static int checkIntegerWithDefaultValue(String option, Scanner scanner,int defaultValue){
        System.out.println("Please enter your "+option);
        do {
            String preInt = scanner.nextLine();
            if(preInt.isEmpty()){
                return defaultValue;
            }else {
                try{
                    return Integer.parseInt(preInt);
                }catch (NumberFormatException nfx){
                    System.err.println("Please enter a number , please re enter");
                }
            }
        }while (true);
    }
    public static boolean checkDuplicateProductId(Connection conn,String value){
        boolean isDuplicate;
        try {
            CallableStatement callableStatement= conn.prepareCall("{CALL CheckDuplicateProductID(?, ?)}");
            callableStatement.setString(1, value);
            callableStatement.registerOutParameter(2, Types.BOOLEAN);
            callableStatement.execute();

            isDuplicate = callableStatement.getBoolean(2);
            return isDuplicate;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;

        }
    }

    public static boolean checkDuplicateUserName(Connection conn,String value){
        boolean isDuplicate;
        try {
            CallableStatement callableStatement= conn.prepareCall("{CALL checkDuplicateUserName(?, ?)}");
            callableStatement.setString(1, value);
            callableStatement.registerOutParameter(2, Types.BOOLEAN);
            callableStatement.execute();

            isDuplicate = callableStatement.getBoolean(2);
            return isDuplicate;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }
    }

    public static boolean checkLogin(Connection conn, String userName, String password){
        try {
            CallableStatement callableStatement= conn.prepareCall("{CALL checkPassWord(?, ?,?)}");
            callableStatement.setString(1, userName);
            callableStatement.setString(2, password);
            callableStatement.registerOutParameter(3, Types.BOOLEAN);
            callableStatement.execute();

            return callableStatement.getBoolean(3);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }
    }

    public static Account loginSuccess(Connection conn,String userName,String password){

            try {
                CallableStatement callSt=conn.prepareCall("{call loginAccount(?,?)}");
                callSt.setString(1,userName);
                callSt.setString(2,password);
                Account account = new Account();
                ResultSet rs= callSt.executeQuery();
                while (rs.next()){
                    account.setAccountId(rs.getInt("acc_id"));
                    account.setUserName(rs.getString("user_name"));
                    account.setPassword(rs.getString("password"));
                    account.setPermission(rs.getBoolean("permission"));
                    account.setEmpId(rs.getString("emp_id"));
                    account.setAccountStatus(rs.getBoolean("acc_status"));
                }
                return account;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }


    }

    public static Account readDataFromFile(){
        Account account;
        File file = new File("account.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            account = (Account) ois.readObject();
            return account;
        } catch (FileNotFoundException e) {
            return new Account();
        } catch (Exception ex){
            ex.printStackTrace();
            return new Account();
        } finally {
            if (fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois!=null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void writeDataToFile(Account account){
        File file = new File("account.txt");

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(account);
            oos.flush();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (oos!=null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
