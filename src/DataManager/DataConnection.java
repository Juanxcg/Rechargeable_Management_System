package DataManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 单例模式下的数据库管理类，所有方法都放在这个类中
 */
public class DataConnection {
    // 单例实例，使用 volatile 保证可见性
    private static volatile DataConnection _instance;

    // 私有构造函数防止外部实例化
    private DataConnection() {
        OpenConnection();
    }
    
    // 提供公共的访问方法，双重检查锁定
    public static DataConnection Instance() {
        if (_instance == null) {
            synchronized (DataConnection.class) {
                if (_instance == null) {
                    _instance = new DataConnection();
                }
            }
        }
        return _instance;
    }

    // 设置连接信息
    private static final String url = "jdbc:mysql://localhost:3306/rechargeable_management";
    private static final String user = "root"; // 用户名
    private static final String password = "123456"; // 密码

    // 数据库连接对象
    private Connection conn = null;

    // 开启连接
    public void OpenConnection() {
        try {
            // 注册JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 打开连接
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            // 处理JDBC错误
            e.printStackTrace();
        }
    }
    
    // 添加数据到信息表
    public void AddData(DBInformation item) {
        String sql = "INSERT INTO information (Id, Name, Acquisition_time, Prices, State) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item.id);
            pstmt.setString(2, item.name);
            pstmt.setString(3, item.acquisition_time);
            pstmt.setFloat(4, item.prices);
            pstmt.setString(5, item.state.toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据到用户表
    public void AddData(DBUser item) {
        String sql = "INSERT INTO users (School_Id, Username, Borrowed_Time, Return_Time, Id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.uid);
            pstmt.setString(2, item.uname);
            pstmt.setString(3, item.borrowed_Time);
            pstmt.setString(4, item.return_Time);
            pstmt.setInt(5, item.id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除某一行
    public void DeleteRow(DBName tableName, int id) {
        String sql = "DELETE FROM " + tableName.toString() + " WHERE Id = "+ id ;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 清空指定表的数据
    public void ClearTable(DBName dbName) {
        String sql = "TRUNCATE TABLE " + dbName.toString();

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //更新状态
    public void UpdateState(int id,State state){
        String sql = "UPDATE information SET State = '" + state + "' WHERE ID = " + id;
        
        try(Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    //更新归还时间
    public void UpdateTime(String uid,String return_Time){
        String sql = "UPDATE users SET Return_Time = '" + return_Time + "' WHERE School_Id = " + uid;

        try(Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // 查看数据库表
    public void SeeDb(DBName dbName) {
        String sql = "SELECT * FROM " + dbName.toString();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            switch (dbName) {
                case information:
                    while (rs.next()) {
                        int id = rs.getInt("Id");
                        String name = rs.getString("Name");
                        String buy_time = rs.getString("Acquisition_time");
                        float prices = rs.getFloat("Prices");
                        String state = rs.getString("State");
                        System.out.print("ID: " + id);
                        System.out.print(", 名称: " + name);
                        System.out.print(", 购买时间: " + buy_time);
                        System.out.print(", 购买价格: " + prices);
                        System.out.print(", 目前状态: " + state);
                        System.out.print("\n");
                    }
                    break;
                case users:
                    while (rs.next()) {
                        int sid = rs.getInt("School_Id");
                        String uname = rs.getString("Username");
                        String borrowed_time = rs.getString("Borrowed_Time");
                        String return_time = rs.getString("Return_Time");
                        int id = rs.getInt("Id");
                        System.out.print("学号: " + sid);
                        System.out.print(", 名称: " + uname);
                        System.out.print(", 租借时间: " + borrowed_time);
                        System.out.print(", 归还时间: " + return_time);
                        System.out.print(", 充电宝Id: " + id);
                        System.out.print("\n");
                    }
                    break;
                default:
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //获取全部的充电宝ID列表
    public List<Integer> GetRBID(){
        String sql = "SELECT * FROM information";

        List<Integer> DBid = new ArrayList<>();

        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("Id");
                DBid.add(id);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return DBid;
    }
    
    //获取未被租用的全部充电宝
    public List<DBInformation> GetFreeRB(){
        String sql = "SELECT * FROM information where State = 'In'";
        
        List<DBInformation> freeDB = new ArrayList<>();
            
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String buy_time = rs.getString("Acquisition_time");
                float prices = rs.getFloat("Prices");
                freeDB.add(new DBInformation(id,name,buy_time,prices,State.In));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return freeDB;
    }
    
    //获取某一个充电宝的租用人
    public List<DBUser> GetUser(int id){
        String sql = "SELECT * FROM users WHERE Id = ?";

        List<DBUser> dbUsers = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String sid = rs.getString("School_Id");
                    String uname = rs.getString("Username");
                    String borrowed_time = rs.getString("Borrowed_Time");
                    String return_time = rs.getString("Return_Time");
                    dbUsers.add(new DBUser(sid, uname, borrowed_time, return_time, id));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbUsers;
    }
    
    //自定义sql语句
    public void RunFunction(String sql){
        try(Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 关闭数据库连接
    public void CloseDb() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}