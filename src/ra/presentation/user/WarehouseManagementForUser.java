package ra.presentation.user;

import ra.business.BillBusiness;
import ra.entity.Bill;
import ra.presentation.PreLogin;
import ra.presentation.admin.update.BillUpdate;
import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class WarehouseManagementForUser {
    public static void displayUserMenu(Scanner scanner, Connection conn){
        boolean isExit = false;
        int choice;
        List<Bill> listBill;
        Bill bill=new Bill();
        do {
            System.out.println("""
                    ******************WAREHOUSE MANAGEMENT****************
                    1. List Receipt By Status
                    2. Create Receipt
                    3. Update Receipt
                    4. Find Receipt
                    5. List Export Bills By Status
                    6. Create Export Bills
                    7. Update Export Bills
                    8. Find Export Bills
                    9. Logout
                    10.Exit""");
            choice = CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1:
                    listBill= BillBusiness.findBillByBillStatusForUser(conn,scanner,"import",PreLogin.account.getEmpId());
                    bill.displayData(listBill);
                    break;
                case 2:
                    bill=new Bill();
                    bill.inputData(scanner,conn, PreLogin.account.getEmpId(), null);
                    BillBusiness.insertAndUpdateBill(conn,bill,"insert",true);
                    break;
                case 3:
                    bill=new Bill();
                    listBill=BillBusiness.findBillByEmployeeCreated(conn,PreLogin.account.getEmpId(),"import");
                    bill.displayData(listBill);
                    do {
                        int indexToUpdate=CommonFunction.checkInteger("Bill's index that you want to update. To exit update, please press number 0",scanner)-1;
                        if(indexToUpdate<listBill.size() && indexToUpdate>=0){
                            BillUpdate.billUpdatePresent(listBill.get(indexToUpdate),scanner,conn,"import");
                            BillBusiness.insertAndUpdateBill(conn,listBill.get(indexToUpdate),"update",true);
                        }else if(indexToUpdate>=listBill.size()){
                            System.err.println("The index you provide beyond the range");
                        }else {
                            System.err.println("Exiting");
                            break;
                        }
                    }while (true);
                    break;
                case 4: break;
                case 5:
                    listBill= BillBusiness.findBillByBillStatusForUser(conn,scanner,"export",PreLogin.account.getEmpId());
                    bill.displayData(listBill);
                    break;
                case 6:
                    bill=new Bill();
                    bill.inputData(scanner,conn, PreLogin.account.getEmpId(), null);
                    BillBusiness.insertAndUpdateBill(conn,bill,"insert",false);
                    break;
                case 7:
                    bill=new Bill();
                    listBill=BillBusiness.findBillByEmployeeCreated(conn,PreLogin.account.getEmpId(),"export");
                    bill.displayData(listBill);
                    do {
                        int indexToUpdate=CommonFunction.checkInteger("Bill's index that you want to update. To exit update, please press number 0",scanner)-1;
                        if(indexToUpdate<listBill.size() && indexToUpdate>=0){
                            BillUpdate.billUpdatePresent(listBill.get(indexToUpdate),scanner,conn,"export");
                            BillBusiness.insertAndUpdateBill(conn,listBill.get(indexToUpdate),"update",false);
                        }else if(indexToUpdate>=listBill.size()){
                            System.err.println("The index you provide beyond the range");
                        }else {
                            System.err.println("Exiting");
                            break;
                        }
                    }while (true);
                    break;
                case 8: break;
                case 9:
                    isExit = true;
                    break;
                case 10:
                    System.exit(0);
                default:
                    System.err.println("Your choice is not valid value, please try again.");
            }
        }while (!isExit);
    }
}
