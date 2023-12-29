package ra.util;

import ra.entity.*;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class CommonFunction {

    public static int checkInteger(String option, Scanner scanner) {
        do {
            try {
                System.out.printf("Please enter your %s \n", option);
                return Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException nfx) {
                System.err.println("Please enter an Integer value");
            }
        } while (true);
    }

    public static short checkShort(String option, Scanner scanner) {
        do {
            try {
                System.out.printf("Please enter your %s \n", option);
                return Short.parseShort(scanner.nextLine());

            } catch (NumberFormatException nfx) {
                System.err.println("Please enter an short value which has value smaller than 127");
            }
        } while (true);
    }

    public static boolean checkBoolean(String option,Scanner scanner){
        System.out.println("Please enter your "+option);
        do {
            String preBoolean=scanner.nextLine();
            if(preBoolean.equals("true")||preBoolean.equals("false")){
                return Boolean.parseBoolean(preBoolean);
            }else {
                System.err.println("You must enter value true or false");
            }
        }while (true);
    }

    public static boolean checkBoolean(String option,Scanner scanner,boolean defaultValue){
        System.out.println("Please enter your "+option);
        do {
            String preBoolean=scanner.nextLine();
            if(preBoolean.equals("true")||preBoolean.equals("false")){
                return Boolean.parseBoolean(preBoolean);
            } else if (preBoolean.isEmpty()) {
                return defaultValue;
            } else {
                System.err.println("You must enter value true or false");
            }
        }while (true);
    }

    public static Date checkDate(Scanner scanner,String option){
        System.out.println("Please enter your "+option);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        do {
            try {
                return sdf.parse(scanner.nextLine());
            } catch (ParseException e) {
                System.err.println("day format yyyy/MM/dd, please re enter");
            }
        } while (true);
    }


    public static int checkIntegerWithDefaultValue(String option, Scanner scanner, int defaultValue) {
        System.out.println("Please enter your " + option);
        do {
            String preInt = scanner.nextLine();
            if (preInt.isEmpty()) {
                return defaultValue;
            } else {
                try {
                    return Integer.parseInt(preInt);
                } catch (NumberFormatException nfx) {
                    System.err.println("Please enter a number , please re enter");
                }
            }
        } while (true);
    }

    //---------------------------------Check Duplicate Start--------------------------------
    public static boolean checkDuplicateProductId(Connection conn, String value) {
        boolean isDuplicate;
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL CheckDuplicateProductID(?, ?)}");
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

    public static boolean checkDuplicateUserName(Connection conn, String value) {
        boolean isDuplicate;
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL checkDuplicateUserName(?, ?)}");
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

    public static boolean checkDuplicateProductName(Connection conn, String value) {
        boolean isDuplicate;
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL checkDuplicateProductName(?, ?)}");
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

    public static boolean checkDuplicateEmpName(Connection conn, String value) {
        boolean isDuplicate;
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL checkDuplicateEmpName(?, ?)}");
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

    public static boolean checkDuplicateEmpId(Connection conn, String value) {
        boolean isDuplicate;
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL checkDuplicateEmpId(?, ?)}");
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

    public static boolean checkLogin(Connection conn, String userName, String password) {
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL checkPassWord(?, ?,?)}");
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

    public static Account loginSuccess(Connection conn, String userName, String password) {

        try {
            CallableStatement callSt = conn.prepareCall("{call loginAccount(?,?)}");
            callSt.setString(1, userName);
            callSt.setString(2, password);
            Account account = new Account();
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
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

    //---------------------------------Check Duplicate End--------------------------------

    //--------------------------------Add Data into Entity Start---------------------------------
    public static Account addDataToAccount(ResultSet rs) {
        try {
            return new Account(rs.getInt("acc_id"), rs.getString("user_name"), rs.getString("password"), rs.getBoolean("permission"), rs.getString("emp_id"), rs.getBoolean("acc_status"));
        } catch (SQLException e) {
            e.printStackTrace();
            return new Account();
        }
    }

    public static Bill addDataToBill(ResultSet rs) {
        try {
            return new Bill(rs.getLong("bill_id"),rs.getString("bill_code"),rs.getBoolean("bill_type"),rs.getString("emp_id_created"),rs.getDate("created"),rs.getString("emp_id_auth"),rs.getDate("auth_date"),rs.getShort("bill_status"));
        } catch (SQLException e) {
            e.printStackTrace();
            return new Bill();
        }
    }

    public static BillDetail addDataToBillDetail(ResultSet rs) {
        try {
            return new BillDetail(rs.getLong("bill_detail_id"),rs.getLong("bill_id"),rs.getString("product_id"),rs.getInt("quantity"),rs.getFloat("price"));
        } catch (SQLException e) {
            e.printStackTrace();
            return new BillDetail();
        }
    }

    public static Employee addDataToEmployee(ResultSet rs) {
        try {
            return new Employee(rs.getString("emp_id"),rs.getString("emp_name"),rs.getDate("birth_of_day"),rs.getString("email"),rs.getString("phone"),rs.getString("address"),rs.getShort("emp_status"));
        } catch (SQLException e) {
            e.printStackTrace();
            return new Employee();
        }
    }

    public static Product addDataToProduct(ResultSet rs) {
        try {
            return new Product(rs.getString("product_id"),rs.getString("product_name"),rs.getString("manufacturer"),rs.getDate("created"),rs.getShort("batch"),rs.getInt("quantity"),rs.getBoolean("product_status"));
        } catch (SQLException e) {
            e.printStackTrace();
            return new Product();
        }
    }

    //--------------------------------Add Data into Entity End---------------------------------

    public static Account readDataFromFile() {
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
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Account();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void writeDataToFile(Account account) {
        File file = new File("account.txt");

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(account);
            oos.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static java.sql.Date convertToSqlDate(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }
}
