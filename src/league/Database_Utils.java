package league;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database_Utils {

    //The name of the MySQL account to use (or empty for anonymous)
    private final String userName = "root";

    //The password for the MySQL account (or empty for anonymous)
    private final String password = "";

    //The name of the computer running MySQL
    private final String serverName = "localhost";

    /**
     * The port of the MySQL server (default is 3306)
     */
    private final int portNumber = 3306;

    //The name of the database
    private final String dbName = "nba_fantasy";

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
     * Determines if the username/password combination is valid.
     */
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
        } catch (SQLException e) {
            System.out.println("Error could not login");
            e.printStackTrace();
        }
        return isValid;
    }

    /**
     * Determines if the username already exists in the database.
     */
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
        } catch (SQLException e) {

        }
        return exists;
    }

    /**
     * Creates an entry in the user_team table
     */
    public void createUser(String username, String password) {
        String query = String.format("CALL new_user('%1$2s','%2$2s')", username, password);
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error: Could not create new user");
            e.printStackTrace();
        }
    }

    /**
     * Obtains the ResultSet of all the players.
     */
    public ResultSet getAllPlayers() {
        String query = "CALL getAllPlayers()";
        ResultSet rs = null;
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error: Could Not Retrieve Players");
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * Obtains the players on the specified user's team.
     */
    public ResultSet getTeamPlayers(String user) {
        String query = String.format("CALL getTeam('%1$2s')", user);
        ResultSet rs = null;
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error: Could not obtain user players");
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * Queries the database for a player with the given name.
     */
    public ResultSet searchPlayers(String player) {
        String query = String.format("SELECT * FROM PLAYER_SEASON WHERE PLAYER_ID = '%1$2s'", player);
        ResultSet rs = null;
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error: Could not obtain players from search");
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * Obtains every team's cumulative statistics.
     */
    public ResultSet getTeams() {
        String query = "CALL getTeams()";
        ResultSet rs = null;
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error: Could Not Retrieve Teams");
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * Determines if the given player is available.
     */
    public boolean isPlayerAvailable(String player) {
        boolean isValid = false;
        String query = String.format("SELECT COUNT(*) AS REGISTERED FROM PLAYER WHERE PLAYER_NAME = '%1$2s' and USER_TEAM IS NOT NULL", player);
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            int isRegistered = rs.getInt("REGISTERED");
            isValid = (isRegistered == 0);
        } catch (SQLException e) {
            System.out.println("Error could not get player registration status");
            e.printStackTrace();
        }
        return isValid;
    }

    /**
     * Enrolls the specified player to be on the given user's team.
     */
    public void addPlayer(String user, String player) {
        String query = String.format("CALL enroll('%1$2s','%2$2s')", user, player);
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error: Could not add player");
            e.printStackTrace();
        }
    }

    /**
     * Removes the specified player from the given user's team.
     */
    public void removePlayer(String user, String player) {
        String query = String.format("CALL removePlayer('%1$2s','%2$2s')", user, player);
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error: Could not remove player");
            e.printStackTrace();
        }
    }

    /**
     * Removes every player from the given user's team.
     */
    public void removeAllPlayers(String user) {
        String query = String.format("CALL removeAllPlayers('%1$2s')", user);
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error: Could not delete account");
            e.printStackTrace();
        }
    }

    /**
     * Deletes the specified player from the given user's team.
     */
    public void deleteAccount(String user) {
        String query = String.format("DELETE FROM user_team WHERE USERNAME = '%1$2s'", user);
        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error: Could not delete account");
            e.printStackTrace();
        }
    }

    /**
     * For testing.
     */
    public static void main(String args[]) {
        Database_Utils db = new Database_Utils();
        System.out.println(db.isPlayerAvailable("Kyrie Irving"));

    }
}
