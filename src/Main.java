import DataManager.*;
import UIManger.UIManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        //打开数据库链接
        DBConnection.Instance().OpenConnection();

        //初始化输入
        Scanner scanner = new Scanner(System.in);
        
        //开始执行程序
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
                case 6:
                    UIManager.AddRB();
                    break;
                case 7:
                    UIManager.RemoveRB();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
        
        //关闭输入与数据库
        scanner.close();
        DBConnection.Instance().CloseDb();
    }
}
