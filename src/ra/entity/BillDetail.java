package ra.entity;

import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class BillDetail implements IEntity<BillDetail>{

    private long billDetailId;
    private long billId;
    private String productId;
    private int quantity;
    private float price;

    public BillDetail() {
    }

    public BillDetail(long billDetailId, long billId, String productId, int quantity, float price) {
        this.billDetailId = billDetailId;
        this.billId = billId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public long getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(long billDetailId) {
        this.billDetailId = billDetailId;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public static long inputBillId(Scanner scanner,Connection conn){
        System.out.println("Please enter your bill Id");
        do {
            try{
                long billId=Long.parseLong(scanner.nextLine());
                if(CommonFunction.checkDuplicateBillId(conn,billId)){
                    return billId;
                }else {
                    System.err.println("this bill id doesn't exist in the system");
                }
            }catch (NumberFormatException nfx){
                System.err.println("Please enter a number");
            }
        }while (true);

    }

    public static String inputProductId(Scanner scanner,Connection conn){
        System.out.println("Please enter your Product Id");
        do {
            String productId= scanner.nextLine();
            if(CommonFunction.checkDuplicateProductId(conn,productId)){
                return productId;
            }else {
                System.err.println("This product Id doesn't exist in the system");
            }
        }while (true);
    }
    @Override
    public void inputData(Scanner scanner, Connection conn) {
        this.billId=inputBillId(scanner,conn);
        this.productId=inputProductId(scanner,conn);
        this.quantity=CommonFunction.checkInteger("quantity",scanner);
        this.price=CommonFunction.checkFloat("price",scanner);
    }


    public void displayData(BillDetail billDetail){
        printTableHeaderWithBoundaryAndAdditionalFields();
            System.out.printf("| %-20d | %-15d | %-15s | %-15d | %-20.2f%n |%n",
                    billDetail.getBillDetailId(),billDetail.getBillId(),billDetail.getProductId(),billDetail.getQuantity(),billDetail.getPrice());

        printTableFooterWithBoundary();
    }
    @Override
    public void displayData(List<BillDetail> listData) {
        printTableHeaderWithBoundaryAndAdditionalFields();
        for (BillDetail billDetail : listData) {
            System.out.printf("| %-20d | %-15d | %-15s | %-15d | %-20.2f%n |%n",
                    billDetail.getBillDetailId(),billDetail.getBillId(),billDetail.getProductId(),billDetail.getQuantity(),billDetail.getPrice());
        }
        printTableFooterWithBoundary();
    }

    private static void printTableHeaderWithBoundaryAndAdditionalFields() {
        printHorizontalLineWithBoundary();
        System.out.printf("| %-20s | %-15s | %-15s | %-15s | %-20s |%n",
                "Bill Detail Id", "Bill Id", "Product Id", "Quantity", "Price");
        printHorizontalLineWithBoundary();
    }

    private static void printTableFooterWithBoundary() {
        printHorizontalLineWithBoundary();
    }

    private static void printHorizontalLineWithBoundary() {
        System.out.println("+----------------------+-----------------+-----------------+-----------------+----------------------+");
    }
}
