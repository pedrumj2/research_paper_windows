import java.sql.*;

/**
 * Created by Pedrum on 1/14/2017.
 */
public class SqlConnect {
    public Statement stmt;
    private Connection connection;
    private String password = "fafdRE$3";
    private String dbIP = "141.117.233.232";
    private String dbName = "research_paper";
    private ResultSet rs;
    public enum SQLRET{
        SUCCESS,
        FAIL
    }


    public SqlConnect() throws SQLException {

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + dbIP + ":3306/" +dbName+"?useSSL=false", "root", password);
            stmt = connection.createStatement();
            stmt.setQueryTimeout(360000);
        }
        catch(java.sql.SQLException ex) {
            System.out.println("Database not connected!");
            throw ex;
        }
        catch(ClassNotFoundException ex){
            System.out.println(ex);

        }
    }

    public String getQueryIndex(String __column, int __row) throws SQLException{
        String _output;
      //  if (rs.next()==false){
        //    return null;
        //}
        for (int i = 0 ; i < __row; i ++){
            rs.next();
        }
        _output= rs.getString(__column);
        return _output;

    }

    public ResultSet getQuery(String __query) throws SQLException{
        ResultSet _output;
        _output =  stmt.executeQuery(__query);
        return _output;
    }

    public SQLRET execGetQueryIndex(String __query){

        try{
            rs =  stmt.executeQuery(__query);
            if(rs.next()){
                return SQLRET.SUCCESS;
            }
            else{
                return SQLRET.FAIL;
            }

        }
        catch(java.sql.SQLException ex) {
            System.out.println(ex);
            return SQLRET.FAIL;
        }
    }

    public SQLRET updateQuery(String __string){

            Boolean _res;
            try{
                _res = stmt.execute(__string);
                return SQLRET.SUCCESS;
            }
            catch(java.sql.SQLException ex) {
                System.out.println(ex);
                return SQLRET.FAIL;
            }

    }
    public void close() throws SQLException {

        stmt.close();
        connection.close();


    }
}
