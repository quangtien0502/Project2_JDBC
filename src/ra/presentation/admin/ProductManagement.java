package ra.presentation.admin;

import ra.business.ProductBusiness;
import ra.entity.Product;
import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.Scanner;

public class ProductManagement {
    public static void displayProduct(Scanner scanner, Connection conn){
        boolean isExit = false;
        int choice;
        do {
            System.out.println("""
                    ******************PRODUCT MANAGEMENT****************
                    1. Product list
                    2. Add new product
                    3. Product updates
                    4. Search for products
                    5. Update product status
                    6. Back.""");
            choice= CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1:
                    Product product=new Product();
                    product.displayData(ProductBusiness.listProduct(conn));
                    break;
                case 2: break;
                case 3: break;
                case 4: break;
                case 5: break;
                case 6:
                    isExit = true;
                    break;
                default:
                    System.err.println("Your choice is not valid value, please try again.");
            }
        }while (!isExit);
    }
}
