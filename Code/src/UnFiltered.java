import java.util.ArrayList;
import java.util.*;
/**
 * Created by Pedrum on 1/16/2017.
 */
public class UnFiltered {
     public ArrayList<Integer> _counts;
     public ArrayList<Double> _probs;
     public ArrayList<String> _starts;
    public ArrayList<String> durations;
     public ArrayList<String> _anomalies;
    public UnFiltered(int __countRows){
        _counts = new ArrayList<Integer>(__countRows);
        _starts = new ArrayList<String>(__countRows);
        durations = new ArrayList<String>(__countRows);
        _anomalies = new ArrayList<String>(__countRows);
        _probs = new ArrayList<Double>(__countRows);
    }

    public void addStart(String __startdate){
        _starts.add(__startdate);
    }

    public void addCount(int __count){
        _counts.add(__count);
    }

    public void addDuration(String __duration){
        durations.add(__duration);
    }

    public void addAnomalies(String __anomaly){
        _anomalies.add(__anomaly);
    }
}
