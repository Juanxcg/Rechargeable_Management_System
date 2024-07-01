package DataManager;


/**
 * Information数据库的相关实体
 */
public class DBInformation{
    public int id;
    public String name;
    public String acquisition_time;
    public float prices;
    public State state;
    
    public DBInformation(){}
    
    public DBInformation(int id,String name,String acquisition_time,float prices,State state){
        this.id = id;
        this.name = name;
        this.acquisition_time = acquisition_time;
        this.prices = prices;
        this.state = state;
    }
}
