package ra.presentation.admin.update;

import ra.entity.Bill;
import ra.entity.Employee;
import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.Scanner;

public class BillUpdate {
    public static void billUpdatePresent(Bill bill, Scanner scanner, Connection conn,String option){
        boolean isExit=false;
        do {
            if(option.trim().equalsIgnoreCase("export")){
                System.out.println("MENU UPDATE BILL");
            }else {
                System.out.println("MENU UPDATE RECEIPT");
            }
            System.out.println("1. Receipt Code\n2. Employee Id Created\n3. Employee Id Auth \n4. Back\n");
            int choice= CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1:
                    bill.setBillCode(CommonFunction.normalStringNotNull(scanner,"bill Code"));
                    break;
                case 2:
                    bill.setEmpIdCreated(Bill.inputEmpId(scanner,conn,"create"));
                    break;
                case 3:
                    bill.setEmpIdAuth(Bill.inputEmpId(scanner,conn,"auth"));
                    break;
                case 4:
                    isExit=true;
                    break;
            }
        }while (!isExit);
    }
}
