package league;

import java.sql.*;
import java.util.Properties;

public class Database_Utils {

    //The name of the MySQL account to use (or empty for anonymous) */
    private final String userName = "root";

    //The password for the MySQL account (or empty for anonymous) */
    private final String password = "Theoneandonly5658";

    //The name of the computer running MySQL
    private final String serverName = "localhost";

    /**
     * The port of the MySQL server (default is 3306)
     */
    private final int portNumber = 3306;

    //The name of the database we are testing with (this default is installed with MySQL) */
    private final String dbName = "nba_fantasy";

    /**
     * The name of the table we are testing with
     */
    private final String tableName = "JDBC_TEST";

    /**
     *    * Get a new database connection
     *    *
     *    * @return
     *    * @throws SQLException
     *    
     */
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        conn = DriverManager.getConnection("jdbc:mysql://"
                + this.serverName + ":" + this.portNumber + "/"
                + this.dbName + "?characterEncoding=UTF-8&useSSL=false", connectionProps);

        return conn;
    }

    /**
     *    * Run a SQL command which does not return a recordset:
     *    * CREATE/INSERT/UPDATE/DELETE/DROP/etc.
     *    *
     *    * @throws SQLException If something goes wrong
     *    
     */
    public boolean executeUpdate(Connection conn, String command) throws SQLException {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(command); // This will throw a SQLException if it fails
            return true;
        } finally {

            // This will run whether we throw an exception or not
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     *    * Connect to MySQL and do some stuff.
     *    
     */
    public void run() {

        // Connect to MySQL
        Connection conn = null;
        try {
            conn = this.getConnection();
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("ERROR: Could not connect to the database");
            e.printStackTrace();
            return;
        }

        // Create a table
        try {
            String createString =
                    "CREATE TABLE " + this.tableName + " ( " +
                            "ID INTEGER NOT NULL, " +
                            "NAME varchar(40) NOT NULL, " +
                            "STREET varchar(40) NOT NULL, " +
                            "CITY varchar(20) NOT NULL, " +
                            "STATE char(2) NOT NULL, " +
                            "ZIP char(5), " +
                            "PRIMARY KEY (ID))";
            this.executeUpdate(conn, createString);
            System.out.println("Created a table");
        } catch (SQLException e) {
            System.out.println("ERROR: Could not create the table");
            e.printStackTrace();
            return;
        }
        // Drop the table
        try {
            String dropString = "DROP TABLE " + this.tableName;
            this.executeUpdate(conn, dropString);
            System.out.println("Dropped the table");
        } catch (SQLException e) {
            System.out.println("ERROR: Could not drop the table");
            e.printStackTrace();
            return;
        }
    }

    public boolean validUserLogin(String username, String password) {
        boolean isValid = false;
        String query = String.format("SELECT COUNT(*) AS USERS FROM user_team WHERE USERNAME = '%1$2s' AND USER_PASSWORD = '%2$2s'", username, password);
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            int numberOfUsers = rs.getInt("USERS");
            isValid = (numberOfUsers == 1);
        } catch (SQLException e){
            System.out.println("Error could not login");
            e.printStackTrace();
        }
        return isValid;
    }

    public boolean userExists(String username) {
        boolean exists = false;
        String query = String.format("SELECT COUNT(*) AS USERS FROM user_team WHERE USERNAME = '%1$2s'", username);
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            int numberOfUsers = rs.getInt("USERS");
            exists = (numberOfUsers == 1);
        } catch (SQLException e){

        }
        return exists;
    }

    public void createUser(String username, String password){
        String query = String.format("CALL new_user('%1$2s','%2$2s')", username, password);
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e){
            System.out.println("Error: Could not create new user");
            e.printStackTrace();
        }
    }

    public ResultSet getAllPlayers(){
        String query = "SELECT * FROM player_season";
        ResultSet rs = null;
        try{
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e){
            System.out.println("Error: Could Not Retrieve Players");
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getTeamPlayers(String user){
        String query = String.format("CALL getTeam('%1$2s')", user);
        ResultSet rs = null;
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e){
            System.out.println("Error: Could not obtain user players");
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet searchPlayers(String player){
        String query = String.format("SELECT * FROM PLAYER_SEASON WHERE PLAYER_ID = '%1$2s'", player);
        ResultSet rs = null;
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e){
            System.out.println("Error: Could not obtain players from search");
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getTeams(){
        String query = "CALL getTeams()";
        ResultSet rs = null;
        try{
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e){
            System.out.println("Error: Could Not Retrieve Teams");
            e.printStackTrace();
        }
        return rs;
    }

    public void addPlayer(String user, String player){
        String query = String.format("CALL enroll('%1$2s','%2$2s')", user, player);
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e){
            System.out.println("Error: Could not add player");
            e.printStackTrace();
        }
    }

    public void removePlayer(String user, String player) {
        String query = String.format("CALL removePlayer('%1$2s','%2$2s')", user, player);
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e){
            System.out.println("Error: Could not remove player");
            e.printStackTrace();
        }
    }

    public void deleteAccount(String user) {
        String query = String.format("DELETE FROM user_team WHERE USERNAME = '%1$2s'", user);
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e){
            System.out.println("Error: Could not delete account");
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        Database_Utils db = new Database_Utils();
        System.out.println(db.validUserLogin("nick", "burn"));
    }
}
