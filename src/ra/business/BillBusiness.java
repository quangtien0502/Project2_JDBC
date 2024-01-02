package ra.business;

import ra.entity.Account;
import ra.entity.Bill;
import ra.entity.Employee;
import ra.util.CommonFunction;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BillBusiness {
    public static List<Bill> listBill(Connection conn, String option) {
        List<Bill> listBill = new ArrayList<>();
        boolean isImport= option.trim().equalsIgnoreCase("import");
        try {
            CallableStatement callableStatement;
            if(isImport){
                callableStatement=conn.prepareCall("{CALL selectReceipt()}");
            }else {
                callableStatement=conn.prepareCall("{CALL selectExportBill()}");
            }
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Bill bill  = new Bill(rs.getLong("bill_id"),rs.getString("bill_code"),rs.getBoolean("bill_type"),rs.getString("emp_id_created"),rs.getDate("created"),rs.getString("emp_id_auth"),rs.getDate("auth_date"),rs.getShort("bill_status"));
                listBill.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listBill;
    }

    public static void insertAndUpdateBill(Connection conn, Bill bill,String option,boolean billType) {
        try {
            conn.setAutoCommit(false);
            CallableStatement callableStatement ;
            if(option.trim().equalsIgnoreCase("insert")){
                callableStatement=conn.prepareCall("{CALL InsertBill(?,?,?,?,?,?,?,?)}");
            }else {
                callableStatement=conn.prepareCall("{CALL UpdateBillById(?,?,?,?,?,?,?,?)}");
            }
            callableStatement.setLong(1,bill.getBillId());
            callableStatement.setString(2,bill.getBillCode());
            callableStatement.setBoolean(3,billType);
            callableStatement.setString(4,bill.getEmpIdCreated());
            callableStatement.setDate(5, CommonFunction.convertToSqlDate(new Date()));
            callableStatement.setString(6,bill.getEmpIdAuth());
            callableStatement.setDate(7,CommonFunction.convertToSqlDate(bill.getAuthDate()));
            callableStatement.setShort(8, bill.getBillStatus());
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

    public static Bill findBillById(Connection conn, Scanner scanner,String option) {
        System.out.println("Please enter your Bill Export Id");
        long billId=CommonFunction.checkLong(scanner,"export Bill");
        if (CommonFunction.checkDuplicateBillId(conn, billId)) {
            try {
                CallableStatement callableStatement = conn.prepareCall("{CALL findBillById(?)}");
                callableStatement.setLong(1, billId);
                ResultSet rs = callableStatement.executeQuery();
                rs.next();
                Bill bill= new Bill(rs.getLong(1),rs.getString(2),rs.getBoolean(3),rs.getString(4),rs.getDate(5),rs.getString(6),rs.getDate(7),rs.getShort(8));
                if(!bill.isBillType() && option.trim().equalsIgnoreCase("export")){
                    return bill;
                }else if (bill.isBillType() && option.trim().equalsIgnoreCase("import")){
                    return bill;
                }else {
                    System.err.println("This Id doesn't exist in the system");
                    return new Bill();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return new Bill();
            }
        } else {
            System.err.println("The id doesn't exist in the system");
            return new Bill();
        }

    }

    public static Bill findBillById(Connection conn, long billId){
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL findBillById(?)}");
            callableStatement.setLong(1, billId);
            ResultSet rs = callableStatement.executeQuery();
            rs.next();
            return new Bill(rs.getLong(1),rs.getString(2),rs.getBoolean(3),rs.getString(4),rs.getDate(5),rs.getString(6),rs.getDate(7),rs.getShort(8));
        } catch (SQLException e) {
            e.printStackTrace();
            return new Bill();
        }
    }

    public static List<Bill> findBillByBillStatusForUser(Connection conn, Scanner scanner,String option,String empIdCreated) {
        short billStatus=CommonFunction.checkShort("bill Status",scanner);
        List<Bill> listBill=new ArrayList<>();
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL selectBillByBillStatus(?)}");
            callableStatement.setShort(1, billStatus);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                Bill bill=new Bill(rs.getLong(1),rs.getString(2),rs.getBoolean(3),rs.getString(4),rs.getDate(5),rs.getString(6),rs.getDate(7),rs.getShort(8));
                if(bill.getEmpIdCreated().equals(empIdCreated)){
                    if(option.trim().equalsIgnoreCase("import")&&bill.isBillType()){
                        listBill.add(bill);
                    } else if (option.trim().equalsIgnoreCase("export") && (!bill.isBillType())) {
                        listBill.add(bill);
                    }
                }
            }
            return listBill;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Bill> findBillByEmployeeCreated(Connection conn, String empId,String option) {
        List<Bill> listBill=new ArrayList<>();
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL selectBillByBillStatus(?)}");
            callableStatement.setString(1, empId);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                Bill bill=new Bill(rs.getLong(1),rs.getString(2),rs.getBoolean(3),rs.getString(4),rs.getDate(5),rs.getString(6),rs.getDate(7),rs.getShort(8));
                if(option.trim().equalsIgnoreCase("import")&&bill.isBillType()){
                    listBill.add(bill);
                } else if (option.trim().equalsIgnoreCase("export") && (!bill.isBillType())) {
                    listBill.add(bill);
                }
            }
            return listBill;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Bill> findBillByBillCode(Connection conn, Scanner scanner,String option){
        String billCode=CommonFunction.normalString(scanner,"bill Code");
        List<Bill> listBill=new ArrayList<>();
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL findBillByBillCode(?)}");
            callableStatement.setString(1, billCode);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                Bill bill=new Bill(rs.getLong(1),rs.getString(2),rs.getBoolean(3),rs.getString(4),rs.getDate(5),rs.getString(6),rs.getDate(7),rs.getShort(8));
                if(option.trim().equalsIgnoreCase("export")&&(!bill.isBillType())){
                    listBill.add(bill);
                } else if (option.trim().equalsIgnoreCase("import") && (bill.isBillType())) {
                    listBill.add(bill);
                }
            }
            return listBill;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Bill> findBillBeforeGivenDate(Connection conn,Date date,String option){
        List<Bill> listBill=new ArrayList<>();
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL selectBillByBeforeDate(?)}");
            callableStatement.setDate(1, CommonFunction.convertToSqlDate(date));
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                Bill bill=new Bill(rs.getLong(1),rs.getString(2),rs.getBoolean(3),rs.getString(4),rs.getDate(5),rs.getString(6),rs.getDate(7),rs.getShort(8));
                if(option.trim().equalsIgnoreCase("export")&&(!bill.isBillType())){
                    listBill.add(bill);
                } else if (option.trim().equalsIgnoreCase("import") && (bill.isBillType())) {
                    listBill.add(bill);
                }
            }
            return listBill;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Bill> findBillInRangeOfDate(Connection conn,Date date1,Date date2,String option){
        List<Bill> listBill=new ArrayList<>();
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL selectBillByRangeOfDate(?,?)}");
            if(date1.before(date2)){
                callableStatement.setDate(1, CommonFunction.convertToSqlDate(date1));
                callableStatement.setDate(2,CommonFunction.convertToSqlDate(date2));
            }else {
                callableStatement.setDate(2, CommonFunction.convertToSqlDate(date1));
                callableStatement.setDate(1,CommonFunction.convertToSqlDate(date2));
            }
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                Bill bill=new Bill(rs.getLong(1),rs.getString(2),rs.getBoolean(3),rs.getString(4),rs.getDate(5),rs.getString(6),rs.getDate(7),rs.getShort(8));
                if(option.trim().equalsIgnoreCase("export")&&(!bill.isBillType())){
                    listBill.add(bill);
                } else if (option.trim().equalsIgnoreCase("import") && (bill.isBillType())) {
                    listBill.add(bill);
                }
            }
            return listBill;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
