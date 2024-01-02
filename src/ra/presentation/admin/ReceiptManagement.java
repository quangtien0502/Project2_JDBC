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

public class ReceiptManagement {
    public static void displayReceipt(Scanner scanner, Connection conn){
        boolean isExit = false;
        int choice;
        Bill bill=new Bill();
        do {
            System.out.println("""
                    ******************RECEIPT MANAGEMENT****************
                    1. List of Receipt
                    2. Insert Receipt
                    3. Update Receipt Information
                    4. Receipt Detail
                    5. Approve Receipt
                    6. Find Receipt
                    7. Back.""");
            choice = CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1:
                    bill.displayData(BillBusiness.listBill(conn,"import"));
                    break;
                case 2:
                    bill.inputData(scanner,conn);
                    BillBusiness.insertAndUpdateBill(conn,bill,"insert",true);
                    break;
                case 3:
                    bill=BillBusiness.findBillById(conn,scanner,"import");
                    if(!bill.equals(new Bill())){
                        BillUpdate.billUpdatePresent(bill,scanner,conn,"import");
                        BillBusiness.insertAndUpdateBill(conn,bill,"update",true);
                    }
                    break;
                case 4:
                    bill=BillBusiness.findBillById(conn,scanner,"import");
                    if(!bill.equals(new Bill())){
                        List<BillDetail> listBillDetail= BillDetailBusiness.findBillDetailByBillId(scanner,conn,bill.getBillId());
                        BillDetail billDetail=new BillDetail();
                        billDetail.displayData(listBillDetail);
                    }
                    break;
                case 5:
                    bill=BillBusiness.findBillById(conn,scanner,"import");
                    if(!bill.equals(new Bill())){
                        bill.setBillStatus((short) 2);
                        BillBusiness.insertAndUpdateBill(conn,bill,"update",true);
                    }
                    break;
                case 6:
                    bill.displayData(BillBusiness.findBillByBillCode(conn,scanner,"import"));
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
