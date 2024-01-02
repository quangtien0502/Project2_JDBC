package ra.presentation.admin;

import ra.business.BillBusiness;
import ra.business.BillDetailBusiness;
import ra.entity.Bill;
import ra.entity.BillDetail;
import ra.presentation.admin.update.BillUpdate;
import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class BillManagement {
    public static void displayBill(Scanner scanner, Connection conn){
        boolean isExit = false;
        int choice;
        Bill bill=new Bill();
        do {
            System.out.println("""
                    ******************BILL MANAGEMENT****************
                    1. List of Export Bills
                    2. Create export Bills
                    3. Update export Bills
                    4. Export Bill Details
                    5. Approve export Bills
                    6. Find export Bills
                    7. Back.""");
            choice = CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1:
                    bill.displayData(BillBusiness.listBill(conn,"export"));
                    break;
                case 2:
                    bill.inputData(scanner,conn);
                    BillBusiness.insertAndUpdateBill(conn,bill,"insert",false);
                    break;
                case 3:
                    bill=BillBusiness.findBillById(conn,scanner,"export");
                    if(!bill.equals(new Bill())){
                        BillUpdate.billUpdatePresent(bill,scanner,conn,"export");
                        BillBusiness.insertAndUpdateBill(conn,bill,"update",false);
                    }
                    break;
                case 4:
                    bill=BillBusiness.findBillById(conn,scanner,"export");
                    if(!bill.equals(new Bill())){
                        List<BillDetail> listBillDetail= BillDetailBusiness.findBillDetailByBillId(scanner,conn,bill.getBillId());
                        BillDetail billDetail=new BillDetail();
                        billDetail.displayData(listBillDetail);
                    }
                    break;
                case 5:
                    bill=BillBusiness.findBillById(conn,scanner,"export");
                    if(!bill.equals(new Bill())){
                        bill.setBillStatus((short) 2);
                        BillBusiness.insertAndUpdateBill(conn,bill,"update",false);
                    }
                    break;
                case 6:
                    bill.displayData(BillBusiness.findBillByBillCode(conn,scanner,"export"));
                    break;
                case 7:
                    isExit = true;
                    break;
                default:
                    System.err.println("Your choice is not valid value, please try again.");
            }
        }while (!isExit);
    }
}
