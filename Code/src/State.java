/**
 * Created by Pedrum on 1/15/2017.
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
public class State {

    private boolean probSet;
    private int countMaxConnection;

    public State(ArrayList<Integer> __arrCount){

        countMaxConnection = getMaxConnection(__arrCount);
    }

    public ArrayList<Double> probInState(ArrayList<Integer> __arrCount, int __index){
        ArrayList<Double> _output;
        int _count;
        _output = new ArrayList<Double>(countMaxConnection+1);
        for (int i =0; i < countMaxConnection+1; i ++){
            _output.add(0.0);
        }
        for (int i =0; i< 20; i++){
            _count = __arrCount.get(__index - 19 +i);
            if (_count > 200){
                _count = 200;
            }
            _output.set(_count, _output.get(_count)+(double)1/20);
        }
        return _output;
    }


    private int getMaxConnection(ArrayList<Integer> __arrCount){
        int _max;
        _max =0;
        for (int i = 0; i< __arrCount.size(); i++){
            if (__arrCount.get(i) > _max){
                _max = __arrCount.get(i);
            }
        }
        if (_max > 200){
            _max = 200;
        }
        return _max;
    }




}
