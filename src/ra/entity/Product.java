package ra.entity;

import java.util.Date;
import java.util.List;

public class Product implements IEntity<Product>{

    private String productId;
    private String productName;
    private String manufacturer;
    private Date created;
    private short batch;
    private int quantity;
    private boolean productStatus;

    public Product() {
    }

    public Product(String productId, String productName, String manufacturer, Date created, short batch, int quantity, boolean productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.created = created;
        this.batch = batch;
        this.quantity = quantity;
        this.productStatus = productStatus;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public short getBatch() {
        return batch;
    }

    public void setBatch(short batch) {
        this.batch = batch;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }



    @Override
    public void inputData() {

    }

    @Override
    public void displayData(List<Product> listData) {
        printTableHeaderWithBoundaryAndAdditionalFields();
        for (Product product : listData) {
            System.out.printf("| %-7s | %-25s | %-25s | %-25s | %-5d | %-15d | %-15s |%n",
                    product.getProductId(),product.getProductName(),product.getManufacturer(),product.getCreated().toString(),product.getBatch(),product.getQuantity(),product.isProductStatus()?"Active":"In Active");
        }
        printTableFooterWithBoundary();
    }

    private static void printTableHeaderWithBoundaryAndAdditionalFields() {
        printHorizontalLineWithBoundary();
        System.out.printf("| %-7s | %-25s | %-25s | %-25s | %-5s | %-15s | %-15s |%n",
                "ID", "Name", "Manufacturer", "Created Date", "Batch", "Quantity","Product Status");
        printHorizontalLineWithBoundary();
    }

    private static void printTableFooterWithBoundary() {
        printHorizontalLineWithBoundary();
    }

    private static void printHorizontalLineWithBoundary() {
        System.out.println("+---------+---------------------------+---------------------------+---------------------------+-------+-----------------+-----------------+");
    }


}
