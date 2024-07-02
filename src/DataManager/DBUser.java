package DataManager;


/**
 * 用于数据库Users的实体数据存储
 */
public class DBUser{
    public String uid;
    public String uname;
    public String borrowed_Time;
    public String return_Time;
    public int id;
    
    public DBUser(){}
    
    public DBUser(String uid,String uname,String borrowed_Time,String return_Time,int id){
        this.uid =uid;
        this.uname = uname;
        this.borrowed_Time = borrowed_Time;
        this.return_Time = return_Time;
        this.id = id;
    }
}
