package UIManger;

import UserManger.UserManager;

/**
 * 对于UI的管理
 */
public class UIManager {
    //获取主界面ui
    public static void GetMenuUI(){
        System.out.println("*********************");
        System.out.println("菜单:");
        System.out.println("1. 租借充电宝");
        System.out.println("2. 归还充电宝");
        System.out.println("3. 查询已租借充电宝");
        System.out.println("4. 查询充电宝列表");
        System.out.println("5. 查询租借充电宝列表");
        System.out.println("6. 添加新的充电宝");
        System.out.println("7. 删除一个充电宝");
        System.out.println("0. 退出");
        System.out.println("*********************");
    }
    
    //租用充电宝ui
    public static void GetFreeRBUI(){
        
        System.out.printf("%-10s %-20s %-20s %-20s%n","充电宝ID","充电宝名称","充电宝购入日期","充电宝购入价格");
        
        UserManager.Instance().AddUser();
        
        GetMenuUI();
    }
    
    //归还充电宝ui
    public static void GetBackRBUI(){
        System.out.println("请输入您要归还的充电宝ID(按-1退出): ");
        
        UserManager.Instance().BackRB();
        
        GetMenuUI();
    }
    
    //查询租用充电宝的ui
    public static void GetBusyRBUI(){
        System.out.println("请输入您要查询的充电宝ID(按-1退出): ");
        
        UserManager.Instance().GetBusyRB();
        
        GetMenuUI();
    }
    
    //查询所有充电宝的ui
    public static void GetRBI(){
        UserManager.Instance().GetRBI();
        
        GetMenuUI();
    }
    
    //查询所有租用历史的ui
    public static void GetUsers(){
        UserManager.Instance().GetUsers();

        GetMenuUI();
    }
    
    //添加新充电宝的ui
    public static void AddRB(){
        UserManager.Instance().AddRB();
        
        GetMenuUI();
    }
    
    //删除某个充电宝的ui
    public static void RemoveRB(){
        UserManager.Instance().RemoveRB();
        
        GetMenuUI();
    }
}
