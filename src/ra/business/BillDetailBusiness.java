package ra.business;

import ra.entity.Bill;
import ra.entity.BillDetail;
import ra.presentation.admin.update.BillDetailUpdate;
import ra.util.CommonFunction;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


public class BillDetailBusiness {
    public static void insertAndUpdateBillDetail(Connection conn, String option, BillDetail billDetail) {
        try {
            conn.setAutoCommit(false);
            CallableStatement callableStatement;
            if (option.trim().equalsIgnoreCase("insert")) {
                callableStatement = conn.prepareCall("{CALL InsertBillDetail(?,?,?,?,?)}");
            } else {
                callableStatement = conn.prepareCall("{CALL UpdateBillDetailById(?,?,?,?,?)}");
            }
            callableStatement.setLong(1, billDetail.getBillDetailId());
            callableStatement.setLong(2, billDetail.getBillId());
            callableStatement.setString(3, billDetail.getProductId());
            callableStatement.setInt(4, billDetail.getQuantity());
            callableStatement.setFloat(5, billDetail.getPrice());
            callableStatement.executeUpdate();
            conn.commit();
            if (option.trim().equalsIgnoreCase("insert")) {
                System.out.println("Insert Success");
            } else if (option.trim().equalsIgnoreCase("update")) {
                System.out.println("Update Success");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateListBillDetail(List<BillDetail> listBillDetail, Connection conn, Scanner scanner,String option) {
        System.out.println("Do you want to update Bill Detail ");
        System.out.println("Yes/No");
        String answer;
        int indexToUpdate;
        boolean isExit = false;
        boolean isExitExpand=false;
        do {
            answer = scanner.nextLine();
            if (answer.trim().equalsIgnoreCase("yes") || answer.trim().equalsIgnoreCase("no")) {
                isExit = true;
            } else {
                System.err.println("Please enter only yes or no");
            }
        } while (!isExit);
        if(answer.trim().equalsIgnoreCase("yes")){
            BillDetail billDetailTest=new BillDetail();
            billDetailTest.displayData(listBillDetail);
            do {
                indexToUpdate=CommonFunction.checkInteger("Index that you want to update. To Exit press 0",scanner)-1;
                if(indexToUpdate>=0 && indexToUpdate< listBillDetail.size()){
                    BillDetailUpdate.billDetailUpdatePresent(listBillDetail.get(indexToUpdate),scanner,conn,option);
                } else if (indexToUpdate==0) {
                    isExitExpand=true;
                }else {
                    System.err.println("The range of you is beyond the given range");
                }
            }while (!isExitExpand);

        }
    }

    public static List<BillDetail> findBillDetailByBillId(Scanner scanner, Connection conn, long billId) {
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

    public static void addListBillDetail(List<Bill> listBill, Scanner scanner, Connection conn, List<BillDetail> listBillDetail) {
        for (Bill billTest :
                listBill) {
            List<BillDetail> listData = BillDetailBusiness.findBillDetailByBillId(scanner, conn, billTest.getBillId());
            if (!listData.isEmpty()) {
                listBillDetail.addAll(BillDetailBusiness.findBillDetailByBillId(scanner, conn, billTest.getBillId()));
            }
        }
    }

    public static List<BillDetail> getSumQuantityForProduct(Connection conn,String option){
        List<BillDetail> listBillDetail=new ArrayList<>();
        boolean isImport;
        isImport= option.trim().equalsIgnoreCase("import");
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL GetSumQuantityForProduct(?)}");
            callableStatement.setBoolean(1,isImport);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                BillDetail billDetail=new BillDetail();
                billDetail.setProductId(rs.getString(1));
                billDetail.setQuantity(rs.getInt(2));
                listBillDetail.add(billDetail);
            }
            return listBillDetail;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<BillDetail> filterBillDetailWithMaxQuantity(List<BillDetail> listBillDetail,Connection conn,String option) {
        int max=0;
        List<BillDetail> listData=getSumQuantityForProduct(conn,option);
        List<BillDetail> listDataFinal=new ArrayList<>();
        List<String> productIdList=new ArrayList<>();
        for (BillDetail billDetail :
                listData) {
            if(billDetail.getQuantity()>max){
                max=billDetail.getQuantity();
            }
        }
        for (BillDetail billDetail :
                listData) {
            if(billDetail.getQuantity()==max){
                productIdList.add(billDetail.getProductId());
            }
        }
        for (BillDetail billDetail :
                listBillDetail) {
            for (String productIdSample :
                    productIdList) {
                if(billDetail.getProductId().equals(productIdSample)){
                    listDataFinal.add(billDetail);
                }
            }
        }
        return listDataFinal;
    }

    public static List<BillDetail> filterBillDetailWithMinQuantity(List<BillDetail> listBillDetail,Connection conn,String option) {
        int min=listBillDetail.get(0).getQuantity();
        List<BillDetail> listData=getSumQuantityForProduct(conn,option);
        List<BillDetail> listDataFinal=new ArrayList<>();
        List<String> productIdList=new ArrayList<>();
        for (BillDetail billDetail :
                listData) {
            if(billDetail.getQuantity()<min){
                min=billDetail.getQuantity();
            }
        }
        for (BillDetail billDetail :
                listData) {
            if(billDetail.getQuantity()==min){
                productIdList.add(billDetail.getProductId());
            }
        }
        for (BillDetail billDetail :
                listBillDetail) {
            for (String productIdSample :
                    productIdList) {
                if(billDetail.getProductId().equals(productIdSample)){
                    listDataFinal.add(billDetail);
                }
            }
        }
        return listDataFinal;
    }

    public static void displayCostAndRevenue(List<Bill> listBill, List<BillDetail> listBillDetail) {
        listBill.sort(Comparator.comparingLong(Bill::getBillId));
        listBillDetail.sort(Comparator.comparingLong(BillDetail::getBillId));
        printTableHeaderWithBoundaryAndAdditionalFields();
        for (Bill bill :
                listBill) {
            for (BillDetail billDetail :
                    listBillDetail) {
                if (bill.getBillId() == billDetail.getBillId()) {
                    System.out.printf("| %-10d | %-25s | %-15s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20d | %-15d | %-15s | %-15d | %-20.2f%n |%n",
                            bill.getBillId(), bill.getBillCode(), bill.isBillType() ? "Receipt" : "Export Bill", bill.getEmpIdCreated(), bill.getCreated(), bill.getEmpIdAuth(), bill.getAuthDate() == null ? "null" : bill.getAuthDate().toString(), bill.getBillStatus() == 0 ? "Create" : bill.getBillStatus() == 1 ? "Canceled" : "Approved", billDetail.getBillDetailId(), billDetail.getBillId(), billDetail.getProductId(), billDetail.getQuantity(), billDetail.getPrice());
                }
            }
        }
        printTableFooterWithBoundary();
    }

    private static void printTableHeaderWithBoundaryAndAdditionalFields() {
        printHorizontalLineWithBoundary();
        System.out.printf("| %-10s | %-25s | %-15s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-15s | %-15s | %-15s | %-20s |%n",
                "Bill Id", "Bill Code", "Bill Type", "Emp Id Created", "Created", "Emp Id Auth", "Auth Date", "Bill Status", "Bill Detail Id", "Bill Id", "Product Id", "Quantity", "Price");
        printHorizontalLineWithBoundary();
    }

    private static void printTableFooterWithBoundary() {
        printHorizontalLineWithBoundary();
    }

    private static void printHorizontalLineWithBoundary() {
        System.out.println("+------------+---------------------------+-----------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+-----------------+-----------------+-----------------+----------------------+");
    }
}
