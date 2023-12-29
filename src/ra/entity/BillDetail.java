package ra.entity;

import java.util.List;

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

    @Override
    public void inputData() {

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
