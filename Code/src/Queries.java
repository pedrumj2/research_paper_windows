/**
 * Created by Pedrum on 1/15/2017.
 */
import java.sql.*;

public class Queries {
    SqlConnect sqlConnect;
    public String type;
    public String duration;
    public Queries(SqlConnect __sqlConnect){
        sqlConnect = __sqlConnect;
    }

    public void createConnectionTable(){
        String _query;
        _query =
                "drop table research_paper.connections";
        sqlConnect.updateQuery(_query);

        _query =
                "CREATE TABLE `connections` (\n" +
                        "  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
                        "  `conn_clean` int(11) NOT NULL,\n" +
                        "  `conn_unfiltered` int(11) NOT NULL,\n" +
                        "  `type` char(20) NOT NULL,\n" +
                        "  `startTime` datetime NOT NULL,\n" +
                        "  PRIMARY KEY (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n";
        sqlConnect.updateQuery(_query);

    }

    public void addConnection(int __countClean, int __countUnfiltered, String __type,
                              String __dateTime){
        String _query;

        _query =
                "insert into  research_paper.connections(conn_clean, conn_unfiltered, type, startTime)\n" +
                        "values(" + __countClean + ",  " + __countUnfiltered +
                        " ,  \"" + __type + "\",   \"" + __dateTime + "\")";

        sqlConnect.updateQuery(_query);

    }

    public ResultSet getConnections()throws SQLException{
        String _query;

        _query =
                "Select * from  research_paper.connections";

        return sqlConnect.getQuery(_query);
    }
    public void execAnomaliesInIntervalCount(String __startTime, String __endTime, int __serverID) throws SQLException{
        String _query;
        _query =
                "select type, duration from research_paper.grouped\n" +
                        "where dstIP = "+__serverID+"\n" +
                        "and CONVERT(DATE_FORMAT(date,'%Y-%m-%d %H:%i:00'),DATETIME) = str_to_date(\""+__startTime+"\", \"%Y-%m-%d %H:%i:00\")\n" +
                        "and anomaly = 1\n" +
                        "limit 1";

        if (sqlConnect.execGetQueryIndex(_query) == SqlConnect.SQLRET.SUCCESS) {
            type = sqlConnect.getQueryIndex("type", 0);
            duration = sqlConnect.getQueryIndex("duration", 0);
        }
        else{
            type = "Clean";
            duration = "";
        }
    }

    public int getConnectionsStartedInIntervalCount(String __startTime, String __endTime, int __serverID) throws SQLException{
        String _query;
        String _anomalyString;
        ResultSet _rs;
        int _output;
        String _strTemp;

        _query =
                "select sum(count) as count2 from research_paper.grouped\n" +
                        "where dstIP = "+__serverID+"\n" +
                        "and CONVERT(DATE_FORMAT(date,'%Y-%m-%d %H:%i:00'),DATETIME) = str_to_date(\""+__startTime+"\", \"%Y-%m-%d %H:%i:00\")\n";

        sqlConnect.execGetQueryIndex(_query);
        _strTemp =sqlConnect.getQueryIndex("count2", 0);
        if (_strTemp == null){
            _output = 0;
        }
        else{
            _output = Integer.parseInt(_strTemp);
        }
        return _output;

    }
    public int getConnectionsStartedInIntervalCount(String __startTime, String __endTime, int __serverID,
                                             boolean __anomaly) throws SQLException{
        String _query;
        String _anomalyString;
        ResultSet _rs;
        int _output;
        String _strTemp;
        if (__anomaly ==true){
            _anomalyString = "1";
        }
        else{
            _anomalyString = "0";
        }

        _query =
                "select sum(count) as count2 from research_paper.grouped\n" +
                        "where anomaly = "+_anomalyString+"\n" +
                        "and dstIP = "+__serverID+"\n" +
                        "and CONVERT(DATE_FORMAT(date,'%Y-%m-%d %H:%i:00'),DATETIME) = str_to_date(\""+__startTime+"\", \"%Y-%m-%d %H:%i:00\")\n";



        sqlConnect.execGetQueryIndex(_query);
        _strTemp =sqlConnect.getQueryIndex("count2", 0);
        if (_strTemp == null){
            _output = 0;
        }
        else{
            _output = Integer.parseInt(_strTemp);
        }
        return _output;

    }


    public int getConnectionsInitCount(String __startTime, String __endTime, String __serverName,
                                             boolean __anomaly) throws SQLException{
        String _query;
        String _anomalyString;
        ResultSet _rs;
        if (__anomaly ==true){
            _anomalyString = "1";
        }
        else{
            _anomalyString = "0";
        }
        _query = "SELECT count(*) as count FROM research_paper.new_table\n" +
                "inner join research_paper.ips\n" +
                "on dstIP = ips.id\n" +
                "where name = \""+__serverName+"\"\n" +
                "and anomaly = "+_anomalyString+"\n" +
                "and date < str_to_date(\""+__endTime+"\", \"%Y-%m-%d %H:%i:%s\")\n" +
                "and date >= str_to_date(\""+__startTime+"\", \"%Y-%m-%d %H:%i:%s\")\n" +
                "order by date asc;";
        sqlConnect.execGetQueryIndex(_query);
        return Integer.parseInt(sqlConnect.getQueryIndex("count", 0));

    }

}
