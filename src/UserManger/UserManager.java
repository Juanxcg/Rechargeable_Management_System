package UserManger;

import DataManager.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;


/**
 * 用户交互的逻辑代码
 */
public class UserManager {
    // 单例实例，使用 volatile 保证可见性
    private static volatile UserManager _instance;

    // 私有构造函数防止外部实例化
    private UserManager() {}

    // 提供公共的访问方法，双重检查锁定
    public static UserManager Instance() {
        if (_instance == null) {
            synchronized (UserManager.class) {
                if (_instance == null) {
                    _instance = new UserManager();
                }
            }
        }
        return _instance;
    }
    
    //空闲充电宝列表
    List<DBInformation> freeRBList;
    
    Scanner scanner = new Scanner(System.in);
    
    //更新未被租借的充电宝，并且如果空闲列表为空就返回false
    private boolean UpdateFreeDB(){
        freeRBList = DBConnection.Instance().GetFreeRB();
        return !freeRBList.isEmpty();
    }
    
    //找到某个id的充电宝是不是在空闲列表中
    private boolean IsFreeDBID(int id){
        UpdateFreeDB();
        for (DBInformation dbInformation : freeRBList) {
            if(dbInformation.id == id) {
                return true;
            }
        }
        return false;
    }
    
    //找出还在租用id充电宝的人
    private int FindBusyDBUid(int id){
        for(DBUser dbUser : DBConnection.Instance().GetUser(id)){
            if(dbUser.return_Time == null) {
                return dbUser.uid;
            }
        }
        return -2;
    }
    
    //添加新的充电宝
    public void AddRB(){
        UpdateFreeDB();
        DBInformation dbInformation = new DBInformation();

        //自动找到具体id
        int id = 0;
        while(true){
            if(!IsFreeDBID(id)&&FindBusyDBUid(id)==-2){
                break;
            }
            id++;
        }
        dbInformation.id = id;
        System.out.println("请输入您要填的充电宝姓名(输入-1退出):");
        
        if((dbInformation.name = scanner.nextLine())=="-1") {
            return;
        }
        dbInformation.acquisition_time = LocalDateTime.now().toString();
        
        System.out.println("请输入您要填的充电宝购入价格:");
        dbInformation.prices = scanner.nextFloat();
        
        dbInformation.state = State.In;
        
        DBConnection.Instance().AddData(dbInformation);
        
        System.out.println("添加成功");
    }
    
    //删除充电宝
    public void RemoveRB(){
        if(!UpdateFreeDB()){
            System.out.println("充电宝已经全部被租用，无法删除。");
            return;
        }
        
        DBConnection.Instance().SeeDb(DBName.information);
        
        int id;
        System.out.println("请输入您要删除的充电宝ID(输入-1退出):");
        while(true){
            id = scanner.nextInt();
            scanner.nextLine();
            if(id ==-1){
                return;
            }
            if(IsFreeDBID(id)){
                break;
            }
            System.out.println("您想要的充电宝ID错误或者已被租用，请重新输入(输入-1退出):");
        }
        
        DBConnection.Instance().DeleteRow(DBName.information,id);
        System.out.println("已删除");
    }
    
    //租借
    public void AddUser(){
        UpdateFreeDB();
        for (DBInformation item : freeRBList) {
            System.out.printf("%-10d %-20s %-20s %-20f%n",item.id , item.name , item.acquisition_time , item.prices);
        }
        //判断是否还有充电宝
        if(!UpdateFreeDB()){
            System.out.println("充电宝已经全部被租用，很抱歉。");
            return;
        }
        int id;
        System.out.println("请输入您要租用的充电宝ID(输入-1退出):");
        while(true){
            id = scanner.nextInt();
            scanner.nextLine();
            if(id==-1){
                return;
            }
            if(IsFreeDBID(id)){
                break;
            }
            System.out.println("您想要的充电宝ID错误或者已被租用，请重新输入(输入-1退出):");
        }
        
        DBUser dbUser = new DBUser();
        
        dbUser.id=id;
        System.out.println("请输入您的学号:");
        dbUser.uid = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("请输入你的名字:");
        dbUser.uname = scanner.nextLine();
        dbUser.borrowed_Time = LocalDateTime.now().toString();
        
        DBConnection.Instance().UpdateState(id, State.Out);
        DBConnection.Instance().AddData(dbUser);
        
        System.out.println("租用成功!");
    }
    
    public void BackRB(){
        //获取正确的id
        int id;
        while(true){
            id = scanner.nextInt();
            scanner.nextLine();
            if(id==-1){
                return;
            }
            if(FindBusyDBUid(id)!=-2){
                break;
            }
            System.out.println("您想要的充电宝ID错误或者已被归还，请重新输入(输入-1退出):");
        }
        
        //通过id找到还没归还的学生
        int uid;
        System.out.println("请输入您的学号(输入-1退出): ");
        while(true){
            uid = scanner.nextInt();
            scanner.nextLine();
            if(uid==-1){
                return;
            }
            if(FindBusyDBUid(id)==uid){
                break;
            }
            System.out.println("学号输入错误，请重新输入(输入-1退出):");
        }
        
        DBConnection.Instance().UpdateTime(uid,LocalDateTime.now().toString());
        DBConnection.Instance().UpdateState(id,State.In);
        
        System.out.println("归还成功");
    }
    
    
    //查询有租借了某个充电宝的学生
    public void GetBusyRB(){
        int id;
        while(true){
            id = scanner.nextInt();
            scanner.nextLine();
            if(id==-1){
                return;
            }
            if(FindBusyDBUid(id)!=-2){
                break;
            }
            System.out.println("您想要查询的充电宝ID错误或者未被租借过，请重新输入(输入-1退出):");
        }
        
        List<DBUser> users = DBConnection.Instance().GetUser(id);

        System.out.printf("%-10s %-20s %-20s %-20s %-10s%n","学号","姓名","租借充电宝日期","归还充电宝日期","充电宝id");
        
        for(DBUser dbUser : users) {
            System.out.printf("%-10d %-20s %-20s %-20s %-10d%n",dbUser.uid,dbUser.uname,dbUser.borrowed_Time,dbUser.return_Time,dbUser.id);
        }
        
        System.out.println("查询成功");
    }
    
    public void GetRBI(){
        DBConnection.Instance().SeeDb(DBName.information);
    }
    
    public void GetUsers(){
        DBConnection.Instance().SeeDb(DBName.users);
    }
}
