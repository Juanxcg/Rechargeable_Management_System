package DataManager;

import Entities.DBInformation;
import Entities.DBUser;
import Enums.DBName;
import Enums.State;

import java.util.List;

public interface DataC {
    //数据库链接的函数
    public void OpenConnection();
    public void CloseDb();
    
    //增
    public void AddData(DBInformation item);
    public void AddData(DBUser item);
    
    //删
    public void DeleteRow(DBName tableName, int id);
    public void ClearTable(DBName dbName);
    
    //改
    public void UpdateState(int id, State state);
    public void UpdateTime(int id,String return_Time);
    
    //查
    public void SeeDb(DBName dbName);
    public List<Integer> GetRBID();
    public List<DBInformation> GetFreeRB();
    public List<DBUser> GetUser(int id);
    
    //自定义方法
    public void RunFunction(String sql);
}
