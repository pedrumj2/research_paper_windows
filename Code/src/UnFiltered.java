import java.util.ArrayList;
import java.util.*;
/**
 * Created by Pedrum on 1/16/2017.
 */
public class UnFiltered {
    static ArrayList<Integer> _counts;
    static ArrayList<Double> _probs;
    static ArrayList<String> _starts;
    static ArrayList<String> _anomalies;
    public UnFiltered(int __countRows){
        _counts = new ArrayList<Integer>(__countRows);
        _starts = new ArrayList<String>(__countRows);
        _anomalies = new ArrayList<String>(__countRows);
        _probs = new ArrayList<Double>(__countRows);
    }

    public void addTime(String __date){
        _starts.add(__date);
    }

    public void addCount(int __count){
        _counts.add(__count);
    }

    public void addAnomalies(String __anomaly){
        _anomalies.add(__anomaly);
    }
}
