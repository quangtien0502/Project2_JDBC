package ra.business;

import ra.entity.Bill;
import ra.entity.BillDetail;
import ra.entity.Product;
import ra.presentation.admin.update.ProductUpdate;
import ra.util.CommonFunction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductBusiness {
    public static List<Product> listProduct(Connection conn,int offsetValue) {
        List<Product> listProduct = new ArrayList<>();
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL selectProduct(?)}");
            callableStatement.setInt(1,offsetValue);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getString("product_id"), rs.getString("product_name"), rs.getString("manufacturer"), rs.getDate("created"), rs.getShort("batch"), rs.getInt("quantity"), rs.getBoolean("product_status"));
                listProduct.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listProduct;
    }

    public static Product
    findProductById(Connection conn, Scanner scanner) {
        System.out.println("Please enter your product Id");
        String productId = scanner.nextLine();
        if (CommonFunction.checkDuplicateProductId(conn, productId)) {
            try {
                CallableStatement callableStatement = conn.prepareCall("{CALL findProductById(?)}");
                callableStatement.setString(1, productId);
                ResultSet rs = callableStatement.executeQuery();
                rs.next();
                return new Product(rs.getString("product_id"), rs.getString("product_name"),
                        rs.getString("manufacturer"), rs.getDate("created"),
                        rs.getShort("batch"), rs.getInt("quantity"),
                        rs.getBoolean("product_status"));
            } catch (SQLException e) {
                e.printStackTrace();
                return new Product();
            }
        } else {
            System.err.println("The id doesn't exist in the system");
            return new Product();
        }

    }

    public static Product
    findProductById(Connection conn, String productId) {
        if (CommonFunction.checkDuplicateProductId(conn, productId)) {
            try {
                CallableStatement callableStatement = conn.prepareCall("{CALL findProductById(?)}");
                callableStatement.setString(1, productId);
                ResultSet rs = callableStatement.executeQuery();
                rs.next();
                return new Product(rs.getString("product_id"), rs.getString("product_name"),
                        rs.getString("manufacturer"), rs.getDate("created"),
                        rs.getShort("batch"), rs.getInt("quantity"),
                        rs.getBoolean("product_status"));
            } catch (SQLException e) {
                e.printStackTrace();
                return new Product();
            }
        } else {
            System.err.println("The id doesn't exist in the system");
            return new Product();
        }

    }

    public static List<Product> findProductByName(Connection conn, Scanner scanner) {
        System.out.println("Please enter your product Name");
        String productName = scanner.nextLine();
        List<Product> listProduct = new ArrayList<>();
        if (CommonFunction.checkDuplicateProductName(conn, productName)) {
            try {
                CallableStatement callableStatement = conn.prepareCall("{CALL findProductByName(?)}");
                callableStatement.setString(1, productName);
                ResultSet rs = callableStatement.executeQuery();
                while (rs.next()) {
                    Product product = new Product(rs.getString("product_id"), rs.getString("product_name"), rs.getString("manufacturer"), rs.getDate("created"), rs.getShort("batch"), rs.getInt("quantity"), rs.getBoolean("product_status"));
                    listProduct.add(product);
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {
            System.err.println("The Product Name like that doesn't exist in the system");

        }
        return listProduct;

    }

    public static void insertProduct(Connection conn, Product product) {
        try {
            conn.setAutoCommit(false);
            CallableStatement callableStatement = conn.prepareCall("{CALL InsertProduct(?,?,?,?,?,?)}");
            callableStatement.setString(1, product.getProductId());
            callableStatement.setString(2, product.getProductName());
            callableStatement.setString(3, product.getManufacturer());
            callableStatement.setShort(4, product.getBatch());
            callableStatement.setInt(5, 0);
            callableStatement.setBoolean(6, product.isProductStatus());
            callableStatement.executeUpdate();
            System.out.println("Insert Success");
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateProduct(Connection conn, Scanner scanner) {
        Product product = findProductById(conn, scanner);
        if (!product.equals(new Product())) {
            try {
                ProductUpdate.productUpdatePresent(product, scanner, conn);
                conn.setAutoCommit(false);
                CallableStatement callableStatement = conn.prepareCall("{CALL UpdateProductById(?,?,?,?,?,?,?)}");
                callableStatement.setString(1, product.getProductId());
                callableStatement.setString(2, product.getProductName());
                callableStatement.setString(3, product.getManufacturer());
                callableStatement.setDate(4,CommonFunction.convertToSqlDate(product.getCreated()) );
                callableStatement.setShort(5, product.getBatch());
                callableStatement.setInt(6, product.getQuantity());
                callableStatement.setBoolean(7, product.isProductStatus());
                callableStatement.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateProduct(Connection conn, Product product) {
        if (!product.equals(new Product())) {
            try {
                conn.setAutoCommit(false);
                CallableStatement callableStatement = conn.prepareCall("{CALL UpdateProductById(?,?,?,?,?,?,?)}");
                callableStatement.setString(1, product.getProductId());
                callableStatement.setString(2, product.getProductName());
                callableStatement.setString(3, product.getManufacturer());
                callableStatement.setDate(4,CommonFunction.convertToSqlDate(product.getCreated()) );
                callableStatement.setShort(5, product.getBatch());
                callableStatement.setInt(6, product.getQuantity());
                callableStatement.setBoolean(7, product.isProductStatus());
                callableStatement.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateProductStatus(Connection conn, Scanner scanner) {
        Product product = findProductById(conn, scanner);
        if (!product.equals(new Product())) {
            try {
                boolean newStatus = CommonFunction.checkBoolean("new Status", scanner);
                conn.setAutoCommit(false);
                CallableStatement callableStatement = conn.prepareCall("{CALL UpdateProductById(?,?,?,?,?,?,?)}");
                callableStatement.setString(1, product.getProductId());
                callableStatement.setString(2, product.getProductName());
                callableStatement.setString(3, product.getManufacturer());
                callableStatement.setDate(4, (Date) product.getCreated());
                callableStatement.setShort(5, product.getBatch());
                callableStatement.setInt(6, product.getQuantity());
                callableStatement.setBoolean(7, newStatus);
                callableStatement.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Product> findListProductWithListBillDetail(List<BillDetail> listBillDetail,Connection conn){
        List<Product> listProduct=new ArrayList<>();
        for (BillDetail billDetail :
                listBillDetail) {
            Product product=findProductById(conn,billDetail.getProductId());
            if(!product.equals(new Product())){
                listProduct.add(product);
            }
        }
        return listProduct;
    }

    public static void displayProductWithListBill(List<BillDetail> listBillDetail, List<Product> listProduct){
        printTableHeaderWithBoundaryAndAdditionalFields();
        for (BillDetail billDetail :
                listBillDetail) {
            for (Product product :
                    listProduct) {
                if(billDetail.getProductId().equals(product.getProductId())){
                    System.out.printf("| %-7s | %-25s | %-25s | %-25s | %-5d | %-15d | %-15s | %-15d |%n",
                            product.getProductId(),product.getProductName(),product.getManufacturer(),product.getCreated().toString(),product.getBatch(),product.getQuantity(),product.isProductStatus()?"Active":"In Active",billDetail.getQuantity());
                }
            }
        }
        printTableFooterWithBoundary();
    }
    private static void printTableHeaderWithBoundaryAndAdditionalFields() {
        printHorizontalLineWithBoundary();
        System.out.printf("| %-7s | %-25s | %-25s | %-25s | %-5s | %-15s | %-15s | %-15s |%n",
                "ID", "Name", "Manufacturer", "Created Date", "Batch", "Quantity","Product Status","Quantity");
        printHorizontalLineWithBoundary();
    }

    private static void printTableFooterWithBoundary() {
        printHorizontalLineWithBoundary();
    }

    private static void printHorizontalLineWithBoundary() {
        System.out.println("+---------+---------------------------+---------------------------+---------------------------+-------+-----------------+-----------------+-----------------+");
    }
}
