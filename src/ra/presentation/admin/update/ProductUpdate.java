package ra.presentation.admin.update;

import ra.entity.Product;
import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.Scanner;

public class ProductUpdate {
    public static void productUpdatePresent(Product product, Scanner scanner, Connection conn){
        boolean isExit=false;
        do {
            System.out.println("MENU UPDATE PRODUCT");
            System.out.println("1. Product Name\n2. Manufacturer\n3. Created Date\n4. Batch\n5. Exit");
            int choice= CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1:
                    product.setProductName(Product.inputProductName(scanner,conn));
                    break;
                case 2:
                    product.setManufacturer(Product.inputManufacturer(scanner));
                    break;
                case 3:
                    product.setCreated(Product.inputCreated(scanner));
                    break;
                case 4:
                    product.setBatch(Product.inputBatch(scanner));
                    break;
                case 5:
                    isExit=true;
                    break;
            }
        }while (!isExit);
    }
}
