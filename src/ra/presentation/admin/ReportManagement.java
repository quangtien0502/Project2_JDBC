package ra.presentation.admin;

import ra.util.CommonFunction;

import java.util.Scanner;

public class ReportManagement {
    public static void displayReportManagement(Scanner scanner){
        boolean isExit = false;
        int choice;
        do {
            System.out.println("******************REPORT MANAGEMENT****************\n" +
                    "1. Cost statistics by day, month, year\n" +
                    "2. Cost statistics by time period\n" +
                    "3. Revenue statistics by day, month, year\n" +
                    "4. Revenue statistics by time period\n" +
                    "5. Statistics on the number of employees by each status\n" +
                    "6. Statistics on most imported products in a period of time\n" +
                    "7. Statistics on imported products at least over a period of time\n" +
                    "8. Statistics of products produced the most in a period of time\n" +
                    "9. Statistics on products produced at least over a period of time\n" +
                    "10. Back.");
            choice= CommonFunction.checkInteger("choice",scanner);
            switch (choice){
                case 1: break;
                case 2: break;
                case 3: break;
                case 4: break;
                case 5: break;
                case 6: break;
                case 7: break;
                case 8: break;
                case 9: break;
                case 10:
                    isExit = true;
                    break;
                default:
                    System.err.println("Your choice is not valid value, please try again.");
            }
        }while(!isExit);
    }
}
