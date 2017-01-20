import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<State> _startStates;
    static csvRead _csvRead;
    static  SqlConnect _sqlConnect;
    static  Queries _queries;
    static IntervalData _intervalData;
    public static int _COUNTROWS = 25200;
    public static void main(String[] args) throws Exception {
        _sqlConnect = new SqlConnect();
        _queries = new Queries(_sqlConnect);
        //_queries.createConnectionTable();
        //IntervalData.updateConnectionsTable(_queries);
        _startStates = State.generateStates(_queries);
        _intervalData = new IntervalData(25200, _queries,_startStates );
        _intervalData.Print();

    }





}
