import textFiles.csvRead;

import java.util.ArrayList;
import textFiles.*;
public class Main {

    static State _stateClean;
    static State _stateUnfiltered;
    static csvRead _csvRead;
    static  SqlConnect _sqlConnect;
    static  Queries _queries;
    static IntervalData _intervalData;
    public static int _COUNTROWS = 25200;
    static txtWrite cleanWriter;
    static txtWrite unfilteredWriter;
    public static void main(String[] args) throws Exception {
        ArrayList<Double> _temp;
        _sqlConnect = new SqlConnect();
        _queries = new Queries(_sqlConnect);
        cleanWriter = new txtWrite("clean.log");
        unfilteredWriter = new txtWrite("unfiltered.log");
        //_queries.createConnectionTable();
        //IntervalData.updateConnectionsTable(_queries);

        _intervalData = new IntervalData(25200, _queries );
        _stateClean = new State(_intervalData.countsClean);
        _stateUnfiltered = new State(_intervalData.countsUnfiltered);
        for (int i = 19; i < _COUNTROWS ; i++){
            cleanWriter.write(_intervalData.starts.get(i) + ", " +
                    _intervalData.anomalies.get(i) + ", " );
            _temp = _stateClean.probInState(_intervalData.countsClean, i);
            for (int j = 0; j < _temp.size(); j++){
                cleanWriter.write(_temp.get(j) + ", ");
            }
            cleanWriter.write("\n");
        }

        for (int i = 19; i < _COUNTROWS ; i++){
            unfilteredWriter.write(_intervalData.starts.get(i) + ", " +
                    _intervalData.anomalies.get(i) + ", " );
            _temp = _stateUnfiltered.probInState(_intervalData.countsUnfiltered, i);
            for (int j = 0; j < _temp.size(); j++){
                unfilteredWriter.write(_temp.get(j) + ", ");
            }
            unfilteredWriter.write("\n");
        }


    }





}
