package DataManager;

public class DBUser{
    public int uid;
    public String uname;
    public String borrowed_Time;
    public String return_Time;
    public int id;
    
    public DBUser(){
    }
    
    public DBUser(int uid,String uname,String borrowed_Time,String return_Time,int id){
        this.uid =uid;
        this.uname = uname;
        this.borrowed_Time = borrowed_Time;
        this.return_Time = return_Time;
        this.id = id;
    }
}