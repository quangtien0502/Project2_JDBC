package ra.entity;

import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

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

    public static String inputProductId(Scanner scanner, Connection conn){
        System.out.println("Please enter your product Id");
        do {
            String productId=scanner.nextLine();
            if(!productId.isEmpty()){
                if(productId.length()==5){
                    if(!CommonFunction.checkDuplicateProductId(conn,productId)){
                        return productId;
                    }else {
                        System.err.println("The Id already Exist in the system");
                    }
                }else {
                    System.err.println("You must enter product Id with length 5");
                }
            }else {
                System.err.println("You must enter product name");
            }
        }while (true);
    }

    public static String inputProductName(Scanner scanner,Connection conn){
        System.out.println("Please enter your Product Name");
        do {
            String productName=scanner.nextLine();
            if(!CommonFunction.checkDuplicateProductName(conn,productName)){
                return productName;
            }else {
                System.err.println("The Product Name already exist in the system");
            }
        }while (true);

    }

    public static String inputManufacturer(Scanner scanner){
        System.out.println("Please enter manufacturer");
        do {
            String manufacturer= scanner.nextLine();
            if(!manufacturer.isEmpty()){
                return manufacturer;
            }else {
                System.err.println("You must enter manufacturer");
            }
        }while (true);
    }

    public static Date inputCreated(Scanner scanner){
        return CommonFunction.checkDate(scanner,"created Date");
    }

    public static short inputBatch(Scanner scanner){
        return CommonFunction.checkShort("batch",scanner);
    }

    public static boolean inputProductStatus(Scanner scanner){
        return CommonFunction.checkBoolean("Product Status",scanner,true);
    }



    @Override
    public void inputData(Scanner scanner,Connection conn) {

        this.productId=inputProductId(scanner,conn);
        this.productName=inputProductName(scanner,conn);
        this.manufacturer=inputManufacturer(scanner);
        this.created=inputCreated(scanner);
        this.batch=inputBatch(scanner);
        this.productStatus=inputProductStatus(scanner);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return batch == product.batch && quantity == product.quantity && productStatus == product.productStatus && Objects.equals(productId, product.productId) && Objects.equals(productName, product.productName) && Objects.equals(manufacturer, product.manufacturer) && Objects.equals(created, product.created);
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
