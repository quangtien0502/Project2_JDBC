package ra.business;
import ra.entity.Bill;
import ra.entity.BillDetail;
import ra.util.CommonFunction;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class BillDetailBusiness {
    public static void insertAndUpdateBillDetail(Connection conn,String option,BillDetail billDetail){
        try {
            conn.setAutoCommit(false);
            CallableStatement callableStatement ;
            if(option.trim().equalsIgnoreCase("insert")){
                callableStatement=conn.prepareCall("{CALL InsertBillDetail(?,?,?,?,?)}");
            }else {
                callableStatement=conn.prepareCall("{CALL UpdateBillDetailById(?,?,?,?,?)}");
            }
            callableStatement.setLong(1,billDetail.getBillDetailId());
            callableStatement.setLong(2,billDetail.getBillId());
            callableStatement.setString(3,billDetail.getProductId());
            callableStatement.setInt(4,billDetail.getQuantity());
            callableStatement.setFloat(5,billDetail.getPrice());
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

    public static List<BillDetail> findBillDetailByBillId(Scanner scanner,Connection conn,long billId) {
        List<BillDetail> listBillDetail = new ArrayList<>();
        if (CommonFunction.checkDuplicateBillId(conn, billId)) {
            try {
                CallableStatement callableStatement = conn.prepareCall("{CALL findBillDetailByBillId(?)}");
                callableStatement.setLong(1, billId);
                ResultSet rs = callableStatement.executeQuery();
                while (rs.next()) {
                    BillDetail billDetail = new BillDetail(rs.getLong(1), rs.getLong(2), rs.getString(3), rs.getInt(4), rs.getFloat(5));
                    listBillDetail.add(billDetail);
                }
                return listBillDetail;

            } catch (SQLException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        } else {
            System.err.println("The id doesn't exist in the system");
            return new ArrayList<>();
        }

    }

    public static void addListBillDetail(List<Bill> listBill,Scanner scanner,Connection conn,List<BillDetail> listBillDetail){
        for (Bill billTest :
                listBill) {
            List<BillDetail> listData=BillDetailBusiness.findBillDetailByBillId(scanner,conn,billTest.getBillId());
            if(!listData.isEmpty()){
                listBillDetail.addAll(BillDetailBusiness.findBillDetailByBillId(scanner, conn, billTest.getBillId()));
            }
        }
    }

    public static List<BillDetail> filterBillDetailWithMaxQuantity(List<BillDetail> listBillDetail){
        int max=0;
        List<BillDetail> listData=new ArrayList<>();
        for (BillDetail billDetail :
                listBillDetail) {
            if(billDetail.getQuantity()>max){
                max=billDetail.getQuantity();
            }
        }
        for (BillDetail billDetail :
                listBillDetail) {
            if(billDetail.getQuantity()==max){
                listData.add(billDetail);
            }
        }
        return listData;
    }

    public static List<BillDetail> filterBillDetailWithMinQuantity(List<BillDetail> listBillDetail){
        int min=listBillDetail.get(0).getQuantity();
        List<BillDetail> listData=new ArrayList<>();
        for (BillDetail billDetail :
                listBillDetail) {
            if(billDetail.getQuantity()<min){
                min=billDetail.getQuantity();
            }
        }
        for (BillDetail billDetail :
                listBillDetail) {
            if(billDetail.getQuantity()==min){
                listData.add(billDetail);
            }
        }
        return listData;
    }

    public static void displayCostAndRevenue(List<Bill> listBill,List<BillDetail> listBillDetail){
        listBill.sort(Comparator.comparingLong(Bill::getBillId));
        listBillDetail.sort(Comparator.comparingLong(BillDetail::getBillId));
        printTableHeaderWithBoundaryAndAdditionalFields();
        for (Bill bill :
                listBill) {
            for (BillDetail billDetail :
                    listBillDetail) {
                if(bill.getBillId()==billDetail.getBillId()){
                    System.out.printf("| %-10d | %-25s | %-15s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20d | %-15d | %-15s | %-15d | %-20.2f%n |%n",
                            bill.getBillId(),bill.getBillCode(),bill.isBillType()?"Receipt":"Export Bill",bill.getEmpIdCreated(),bill.getCreated(),bill.getEmpIdAuth(),bill.getAuthDate()==null?"null":bill.getAuthDate().toString(),bill.getBillStatus()==0?"Create":bill.getBillStatus()==1?"Canceled":"Approved",billDetail.getBillDetailId(),billDetail.getBillId(),billDetail.getProductId(),billDetail.getQuantity(),billDetail.getPrice() );
                }
            }
        }
        printTableFooterWithBoundary();
    }

    private static void printTableHeaderWithBoundaryAndAdditionalFields() {
        printHorizontalLineWithBoundary();
        System.out.printf("| %-10s | %-25s | %-15s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-15s | %-15s | %-15s | %-20s |%n",
                "Bill Id","Bill Code","Bill Type","Emp Id Created","Created","Emp Id Auth","Auth Date","Bill Status","Bill Detail Id", "Bill Id", "Product Id", "Quantity", "Price");
        printHorizontalLineWithBoundary();
    }

    private static void printTableFooterWithBoundary() {
        printHorizontalLineWithBoundary();
    }

    private static void printHorizontalLineWithBoundary() {
        System.out.println("+------------+---------------------------+-----------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+-----------------+-----------------+-----------------+----------------------+");
    }
}
