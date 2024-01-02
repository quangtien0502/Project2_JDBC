package ra.presentation.admin;

import ra.business.BillBusiness;
import ra.business.BillDetailBusiness;
import ra.business.EmployeeBusiness;
import ra.business.ProductBusiness;
import ra.entity.*;
import ra.presentation.PreLogin;
import ra.util.CommonFunction;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ReportManagement {
    public static void displayReportManagement(Scanner scanner, Connection conn){
        boolean isExit = false;
        Date date1,date2;
        List<Bill> listBill;
        List<BillDetail> listBillDetail;
        List<Product> listProduct;
        Employee employee=new Employee();
        int choice;
        float sumCost,sumRevenue;
        do {
            System.out.println("""
                    ******************REPORT MANAGEMENT****************
                    1. Cost statistics by day, month, year
                    2. Cost statistics by time period
                    3. Revenue statistics by day, month, year
                    4. Revenue statistics by time period
                    5. Statistics on the number of employees by each status
                    6. Statistics on most imported products in a period of time
                    7. Statistics on imported products at least over a period of time
                    8. Statistics of products produced the most in a period of time
                    9. Statistics on products produced at least over a period of time
                    10. Logout
                    11.Back.""");
            choice= CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1:
                    date1=CommonFunction.checkDate(scanner,"Date");
                    listBill= BillBusiness.findBillBeforeGivenDate(conn,date1,"import");
                    listBillDetail=new ArrayList<>();
                    sumCost=0;
                    BillDetailBusiness.addListBillDetail(listBill,scanner,conn,listBillDetail);
                    for (BillDetail billDetail :
                            listBillDetail) {
                        sumCost=sumCost+(billDetail.getPrice()*billDetail.getQuantity());
                    }
                    BillDetailBusiness.displayCostAndRevenue(listBill,listBillDetail);
                    break;
                case 2:
                    date1=CommonFunction.checkDate(scanner,"Start Range Date");
                    date2=CommonFunction.checkDate(scanner,"End Range Date");
                    sumCost=0;
                    listBill=BillBusiness.findBillInRangeOfDate(conn,date1,date2,"import");
                    listBillDetail=new ArrayList<>();
                    BillDetailBusiness.addListBillDetail(listBill,scanner,conn,listBillDetail);
                    for (BillDetail billDetail :
                            listBillDetail) {
                        sumCost=sumCost+(billDetail.getPrice()*billDetail.getQuantity());
                    }
                    BillDetailBusiness.displayCostAndRevenue(listBill,listBillDetail);
                    break;
                case 3:
                    date1=CommonFunction.checkDate(scanner,"Date");
                    listBill= BillBusiness.findBillBeforeGivenDate(conn,date1,"export");
                    sumRevenue=0;
                    listBillDetail=new ArrayList<>();
                    BillDetailBusiness.addListBillDetail(listBill,scanner,conn,listBillDetail);
                    for (BillDetail billDetail :
                            listBillDetail) {
                        sumRevenue=sumRevenue+(billDetail.getPrice()*billDetail.getQuantity());
                    }
                    BillDetailBusiness.displayCostAndRevenue(listBill,listBillDetail);
                    break;
                case 4:
                    date1=CommonFunction.checkDate(scanner,"Start Range Date");
                    date2=CommonFunction.checkDate(scanner,"End Range Date");
                    sumRevenue=0;
                    listBill=BillBusiness.findBillInRangeOfDate(conn,date1,date2,"export");
                    listBillDetail=new ArrayList<>();
                    BillDetailBusiness.addListBillDetail(listBill,scanner,conn,listBillDetail);
                    for (BillDetail billDetail :
                            listBillDetail) {
                        sumRevenue=sumRevenue+(billDetail.getPrice()*billDetail.getQuantity());
                    }
                    BillDetailBusiness.displayCostAndRevenue(listBill,listBillDetail);
                    break;
                case 5:
                    employee.displayData(EmployeeBusiness.findEmployeeByStatus(conn,Employee.inputEmpStatus(scanner)));
                    break;
                case 6:
                    date1=CommonFunction.checkDate(scanner,"Date");
                    listBill= BillBusiness.findBillBeforeGivenDate(conn,date1,"import");
                    listBillDetail=new ArrayList<>();
                    BillDetailBusiness.addListBillDetail(listBill,scanner,conn,listBillDetail);
                    listBillDetail=BillDetailBusiness.filterBillDetailWithMaxQuantity(listBillDetail,conn,"import");
                    listProduct= ProductBusiness.findListProductWithListBillDetail(listBillDetail,conn);
                    ProductBusiness.displayProductWithListBill(listBillDetail,listProduct);
                    break;
                case 7:
                    date1=CommonFunction.checkDate(scanner,"Date");
                    listBill= BillBusiness.findBillBeforeGivenDate(conn,date1,"import");
                    listBillDetail=new ArrayList<>();
                    BillDetailBusiness.addListBillDetail(listBill,scanner,conn,listBillDetail);
                    listBillDetail=BillDetailBusiness.filterBillDetailWithMinQuantity(listBillDetail,conn,"import");
                    listProduct= ProductBusiness.findListProductWithListBillDetail(listBillDetail,conn);
                    ProductBusiness.displayProductWithListBill(listBillDetail,listProduct);
                    break;
                case 8:
                    date1=CommonFunction.checkDate(scanner,"Date");
                    listBill= BillBusiness.findBillBeforeGivenDate(conn,date1,"export");
                    listBillDetail=new ArrayList<>();
                    BillDetailBusiness.addListBillDetail(listBill,scanner,conn,listBillDetail);
                    listBillDetail=BillDetailBusiness.filterBillDetailWithMaxQuantity(listBillDetail,conn,"import");
                    listProduct= ProductBusiness.findListProductWithListBillDetail(listBillDetail,conn);
                    ProductBusiness.displayProductWithListBill(listBillDetail,listProduct);
                    break;
                case 9:
                    date1=CommonFunction.checkDate(scanner,"Date");
                    listBill= BillBusiness.findBillBeforeGivenDate(conn,date1,"export");
                    listBillDetail=new ArrayList<>();
                    BillDetailBusiness.addListBillDetail(listBill,scanner,conn,listBillDetail);
                    listBillDetail=BillDetailBusiness.filterBillDetailWithMinQuantity(listBillDetail,conn,"export");
                    listProduct= ProductBusiness.findListProductWithListBillDetail(listBillDetail,conn);
                    ProductBusiness.displayProductWithListBill(listBillDetail,listProduct);
                    break;
                case 10:
                    PreLogin.account=new Account();
                    isExit=true;
                case 11:
                    isExit = true;
                    break;
                default:
                    System.err.println("Your choice is not valid value, please try again.");
            }
        }while(!isExit);
    }
}
