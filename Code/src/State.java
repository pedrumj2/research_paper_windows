/**
 * Created by Pedrum on 1/15/2017.
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
public class State {
    public int countConnections;
    public int repeat;
    public List<Action> actions;
    private boolean probSet;
    private int countTotal;
    public State(int __countConnections, int __repeat){
        actions = new LinkedList<Action>();
        countConnections = __countConnections;
        repeat = __repeat;
        probSet = false;
    }
    public double getProb(int __countConnections){
        if (probSet == false){
            setProb();
            probSet = true;
        }
        for (int i =0;i< actions.size();i++){
            if (actions.get(i).state.countConnections ==__countConnections){
                return actions.get(i).prob;
            }
        }
        return 0;
    }

    private void setProb(){
        setTotal();
        for (int  i = 0; i< actions.size();i++){
            actions.get(i).prob = actions.get(i).count/(double)countTotal;
        }
    }

    private void setTotal(){
        int _sum;
        _sum =0;
        for (int i =0; i < actions.size();i++){
            _sum += actions.get(i).count;
        }
        countTotal  = _sum;
    }

    public State incrementTransition(int __countConnections, List<State> __startStates){
        State _output;
        if (__countConnections == countConnections) {
            _output = getRepeatedState(__countConnections);
            if (_output == null){
                _output = createNewState(__countConnections);
            }
        }
        else{
            _output = getNonRepeatedState(__countConnections);
            if (_output == null){
                _output = getStartState(__countConnections, __startStates);
                if (_output != null){
                    Action _newAction = new Action(_output);
                    _newAction.count +=1;
                    actions.add(_newAction);
                }
                else{
                    _output = createNewStartState(__countConnections, __startStates);
                }
            }
        }

        return _output;
    }

    public State getNexState(int __countConnections){
        for (int i =0; i< actions.size(); i++){
            if (actions.get(i).state.countConnections == __countConnections){
                return actions.get(i).state;
            }
        }
        return null;
    }
    private State getNonRepeatedState(int __countConnections){
        for (int i =0; i < actions.size();i++){
            if (actions.get(i).state.countConnections == __countConnections){
                actions.get(i).count +=1;
                return actions.get(i).state;
            }
        }
        return null;
    }
    private State getRepeatedState(int __countConnections){
        for (int i =0; i < actions.size();i++){
            if (actions.get(i).state.countConnections == __countConnections
                    && actions.get(i).state.repeat == repeat + 1){
                actions.get(i).count +=1;
                return actions.get(i).state;
            }
        }
        return null;
    }

    public static State getStartState(int __countConnections, List<State> __startStates){
        for (int i =0; i < __startStates.size();i++){
            if (__startStates.get(i).countConnections == __countConnections ){

                return __startStates.get(i);
            }
        }
        return null;
    }
    private State createNewState(int __countConnections){
        State _newState = new State(__countConnections, repeat+1);
        Action _newAction = new Action(_newState);
        _newAction.count +=1;
        actions.add(_newAction);

        return _newState;

    }
    private State createNewStartState(int __countConnections, List<State> __startStates){
        State _newState = new State(__countConnections, 0);
        Action _newAction = new Action(_newState);
        _newAction.count +=1;
        actions.add(_newAction);
        __startStates.add(_newState);
        return _newState;
    }

    public static List<State> generateStates(Queries __queries)throws SQLException {
        State _startState ;
        List<State> _startStates;
        _startState = new State(-1, -1);
        _startStates = new LinkedList<State>();
        ResultSet _resultSet;
        int _count;
        int j =0;

        _resultSet = __queries.getConnections();
        while(_resultSet.next()){
            _count = _resultSet.getInt("conn_clean");
            _startState = _startState.incrementTransition(_count, _startStates) ;
            j+=1;
            if (j==84){
                _startState = _startState.incrementTransition(0, _startStates);
                j = 0;
            }
        }
        return _startStates;
    }


}
