import DataManager.*;
import UIManger.UIManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        DBConnection.Instance().OpenConnection();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        
        UIManager.GetMenuUI();
        while(!exit){
            int choice = scanner.nextInt();
            scanner.nextLine(); // 消费换行符
            
            switch (choice){
                case 1:
                    UIManager.GetFreeRBUI();
                    break;
                case 2:
                    UIManager.GetBackRBUI();
                    break;
                case 3:
                    UIManager.GetBusyRBUI();
                    break;
                case 4:
                    UIManager.GetRBI();
                    break;
                case 5:
                    UIManager.GetUsers();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
        scanner.close();
        DBConnection.Instance().CloseDb();
    }
}
