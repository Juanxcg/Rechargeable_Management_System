package UIManger;

import UserManger.UserManager;

public class UIManager {
    public static void GetMenuUI(){
        System.out.println("\n菜单:");
        System.out.println("1. 租借充电宝");
        System.out.println("2. 归还充电宝");
        System.out.println("3. 查询已租借充电宝");
        System.out.println("4. 查询充电宝列表");
        System.out.println("5. 查询租借充电宝列表");
        System.out.println("0. 退出");
    }
    
    public static void GetFreeRBUI(){
        
        System.out.printf("%-10s %-20s %-20s %-20s%n","充电宝ID","充电宝名称","充电宝购入日期","充电宝购入价格");
        
        UserManager.Instance().AddUser();
        
        GetMenuUI();
    }
    
    public static void GetBackRBUI(){
        System.out.println("请输入您要归还的充电宝ID(按-1退出): ");
        
        UserManager.Instance().BackRB();
        
        GetMenuUI();
    }
    
    public static void GetBusyRBUI(){
        System.out.println("请输入您要查询的充电宝ID(按-1退出): ");
        
        UserManager.Instance().GetBusyRB();
        
        GetMenuUI();
    }
    
    public static void GetRBI(){
        UserManager.Instance().GetRBI();
        
        GetMenuUI();
    }
    
    public static void GetUsers(){
        UserManager.Instance().GetUsers();

        GetMenuUI();
    }
}
