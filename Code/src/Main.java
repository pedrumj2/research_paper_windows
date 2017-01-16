import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    static List<State> _startStates;
    static csvRead _csvRead;
    static  SqlConnect _sqlConnect;
    static  TimeInterval _timeInterval;
    static  Queries _queries;
    static ArrayList<Integer> _arrcount;
    static UnFiltered _unfiltered;
    static int _COUNTROWS = 252;

    public static void main(String[] args) throws Exception {
        List<Double> _probs;
        _unfiltered = new UnFiltered(_COUNTROWS);
        _sqlConnect = new SqlConnect();
        _timeInterval = new TimeInterval();
        _arrcount =   new ArrayList<Integer>(_COUNTROWS);
        _unfiltered =   new UnFiltered(_COUNTROWS);
        _queries = new Queries(_sqlConnect);
        _startStates  = getStates();
        _probs = getProbs(_arrcount);
        //for (int i =0; i < _probs.size();i ++){
          //  System.out.println(_probs.get(i));
        //}
        _timeInterval = new TimeInterval();
        getAnomalyCounts();
        _unfiltered._probs = getProbs(_unfiltered._counts);
        for (int i =0; i < _probs.size();i ++) {
            System.out.println(_unfiltered._probs.get(i));
        }
    }

    private static void getAnomalyCounts()throws SQLException{


        int _count;
        int j =0;

        for (int i =0; i < _COUNTROWS; i++){
            String _startTime;
            String _anomaly;
            String _endTime;
            _startTime =_timeInterval.getStartTime();
            _endTime = _timeInterval.getEndTime();
            _unfiltered.addTime(_startTime);
            _count =  _queries.getConnectionsStartedInIntervalCount(_startTime, _endTime,
                    16393);
            _unfiltered.addCount(_count);
            _anomaly=  _queries.getAnomaliesInIntervalCount(_startTime, _endTime,
                    16393);
            _unfiltered.addAnomalies(_anomaly);

            _timeInterval.increment();
            System.out.println(i);
            j+=1;
            if (j==84){


                j = 0;
            }
        }

    }

    private static ArrayList<Double> getProbs(ArrayList<Integer> __arrcount){
        ArrayList<Double> _output;
        Double _prob;
        _output = new ArrayList<Double>();
        for (int i = 0 ; i < _COUNTROWS -20; i++){
            _prob = getProbe(i, __arrcount);
            _output.add(_prob);
        }
        return _output;

    }

    private static double getProbe(int __index, List<Integer> __arrcount){
        State _startState;
        int _count;
        double _prob;
        _prob = 1;

        _startState = State.getStartState(__arrcount.get(__index), _startStates);
        if (_startState == null){
            return 0;
        }
        for (int i = 0; i< 20; i++){
            _count = __arrcount.get(__index+i+1);
            _prob *= _startState.getProb(_count);
            _startState = _startState.getNexState(_count);
        //    System.out.println(i+__index + " " + _prob);
            if (_startState == null){
                return 0;
            }
        }
        return _prob;
    }


    private static List<State> getStates()throws SQLException{
        State _startState ;
        _startState = new State(-1, -1);
        _startStates = new LinkedList<State>();
        int _count;
        int j =0;

        for (int i =0; i < _COUNTROWS; i++){
            String _startTime;
            String _endTime;
            _startTime =_timeInterval.getStartTime();
            _endTime = _timeInterval.getEndTime();

            _count =  _queries.getConnectionsStartedInIntervalCount(_startTime, _endTime,
                    16393,   false);
            _arrcount.add(_count);
            _startState = _startState.Add(_count, _startStates);

            _timeInterval.increment();
            System.out.println(i);
            j+=1;
            if (j==84){
              //  System.out.println("--" + "," +
          //              "--" + "," +0);
                _startState = _startState.Add(0, _startStates);
                j = 0;
            }
        }
        return _startStates;
    }

}
