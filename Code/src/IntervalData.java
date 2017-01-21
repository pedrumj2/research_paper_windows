import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.*;
/**
 * Created by Pedrum on 1/16/2017.
 */
public class IntervalData {
    public ArrayList<Integer> countsUnfiltered;
    public ArrayList<Double> cleanProbs;
    public ArrayList<Double> Anomalyprobs;
    public ArrayList<String> starts;
    public ArrayList<String> durations;
    public ArrayList<String> anomalies;
    public ArrayList<Integer> countsClean;
    private int rowCount;

    public IntervalData(int __countRows, Queries __queries) throws SQLException{
        rowCount=__countRows;
        initArrays();
        fillIntervalData(__queries);

    }

    public void Print(){
        for (int i =0; i < rowCount;i++){
            System.out.println(cleanProbs.get(i) + "," +
                                Anomalyprobs.get(i) + "," +
                                starts.get(i) + ","  +
                                anomalies.get(i) );
        }
    }
    private void initArrays(){
        countsUnfiltered = new ArrayList<Integer>(rowCount);
        countsClean = new ArrayList<Integer>(rowCount);
        starts = new ArrayList<String>(rowCount);
        durations = new ArrayList<String>(rowCount);
        anomalies = new ArrayList<String>(rowCount);

    }
    public static void updateConnectionsTable(Queries __queries) throws SQLException{
        TimeInterval _timeInterval;
        _timeInterval = new TimeInterval();
        int _countClean;
        int _countUnfiltered;
        String _type;
        int j =0;

        for (int i =0; i < Main._COUNTROWS; i++){
            String _startTime;
            String _endTime;
            _startTime =_timeInterval.getStartTime();
            _endTime = _timeInterval.getEndTime();

            _countClean =  __queries.getConnectionsStartedInIntervalCount(_startTime, _endTime,
                    16393,   false);
            _countUnfiltered =  __queries.getConnectionsStartedInIntervalCount(_startTime, _endTime,
                    16393);

            __queries.execAnomaliesInIntervalCount(_startTime, _endTime,
                    16393);
            __queries.addConnection(_countClean, _countUnfiltered, __queries.type, _startTime);


            _timeInterval.increment();
            System.out.println(i);

        }

    }
    private void fillIntervalData(Queries __queries) throws SQLException{

        ResultSet _resultSet;
        int _count;
        int _countUnfiltered;
        String __type;
        String __date;
        int j =0;

        _resultSet = __queries.getConnections();
        while(_resultSet.next()){
            _count = _resultSet.getInt("conn_clean");
            _countUnfiltered = _resultSet.getInt("conn_unfiltered");
            __type = _resultSet.getString("type");
            __date = _resultSet.getString("startTime");
            countsClean.add(_count);
            countsUnfiltered.add(_countUnfiltered);
            anomalies.add(__type);
            starts.add(__date);
            j +=1;
            if (j==720){
                countsClean.add(0);
                countsUnfiltered.add(0);
                anomalies.add("Divider");
                starts.add(__date);
            }
         }

    }



}
