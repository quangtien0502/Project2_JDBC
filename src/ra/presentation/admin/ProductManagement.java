package ra.presentation.admin;

import ra.business.ProductBusiness;
import ra.entity.Account;
import ra.entity.Product;
import ra.presentation.PreLogin;
import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.Scanner;

public class ProductManagement {
    public static void displayProduct(Scanner scanner, Connection conn){
        boolean isExit = false;
        Product product=new Product();
        int choice;
        do {
            System.out.println("""
                    ******************PRODUCT MANAGEMENT****************
                    1. Product list
                    2. Add new product
                    3. Product updates
                    4. Search for products
                    5. Update product status
                    6. Logout
                    7.Back.""");
            choice= CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1:
                    product.displayData(ProductBusiness.listProduct(conn));
                    break;
                case 2:
                    product.inputData(scanner,conn);
                    ProductBusiness.insertProduct(conn,product);
                    break;
                case 3:
                    ProductBusiness.updateProduct(conn,scanner);
                    break;
                case 4:
                    product.displayData(ProductBusiness.findProductByName(conn,scanner));
                    break;
                case 5:
                    ProductBusiness.updateProductStatus(conn,scanner);
                    break;
                case 6:
                    PreLogin.account=new Account();
                    isExit=true;
                case 7:
                    isExit = true;
                    break;
                default:
                    System.err.println("Your choice is not valid value, please try again.");
            }
        }while (!isExit);
    }
}
