package ra.presentation.admin.update;

import ra.entity.Bill;
import ra.entity.BillDetail;
import ra.entity.Product;
import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.Scanner;

public class BillDetailUpdate {
    public static void billDetailUpdatePresent(BillDetail billDetail, Scanner scanner, Connection conn, String option){
        boolean isExit=false;
        do {
            if(option.trim().equalsIgnoreCase("export")){
                System.out.println("MENU UPDATE EXPORT BILL Detail");
            }else {
                System.out.println("MENU UPDATE RECEIPT Detail");
            }
            System.out.println("1. Product Id\n2. Quantity\n3. Price \n4. Back\n");
            int choice= CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1:
                    billDetail.setProductId(Product.inputProductId(scanner,conn));
                    break;
                case 2:
                    billDetail.setQuantity(CommonFunction.checkInteger("Quantity",scanner));
                    break;
                case 3:
                    billDetail.setPrice(CommonFunction.checkFloat("price",scanner));
                    break;
                case 4:
                    isExit=true;
                    break;
            }
        }while (!isExit);
    }
}
