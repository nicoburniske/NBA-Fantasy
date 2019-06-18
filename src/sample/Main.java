package sample;

import com.mysql.cj.conf.IntegerProperty;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {
    private Stage window;
    private Scene login, team;
    private String user, pass;
    private Database_Utils database;
    private TableView<Player> allPlayers;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.window = primaryStage;
        database = new Database_Utils();
        this.window.setTitle("NBA Fantasy League");

        initLoginButtons();
        initTeamView();
        this.window.setScene(this.login);
        this.window.show();
    }

    private void initTeamView() {
        TableColumn<Player, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(130);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn<Player, String> teamCol = new TableColumn<>("Team");
        teamCol.setMinWidth(50);
        teamCol.setCellValueFactory(new PropertyValueFactory<>("Team"));

        TableColumn<Player, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setMinWidth(50);
        ageCol.setCellValueFactory(new PropertyValueFactory<>("Age"));

        TableColumn<Player, Integer> gameCol = new TableColumn<>("Games Played");
        gameCol.setMinWidth(50);
        gameCol.setCellValueFactory(new PropertyValueFactory<>("Games_Played"));

        TableColumn<Player, Double> minutesCol = new TableColumn<>("MPG");
        minutesCol.setMinWidth(50);
        minutesCol.setCellValueFactory(new PropertyValueFactory<>("MPG"));

        TableColumn<Player, Double> usageCol = new TableColumn<>("USG%");
        usageCol.setMinWidth(50);
        usageCol.setCellValueFactory(new PropertyValueFactory<>("Usage_Percentage"));

        TableColumn<Player, Double> turnRatioCol = new TableColumn<>("TO%");
        turnRatioCol.setMinWidth(50);
        turnRatioCol.setCellValueFactory(new PropertyValueFactory<>("Turnover_Ratio"));

        TableColumn<Player, Integer> ftaCol = new TableColumn<>("FTA");
        ftaCol.setMinWidth(50);
        ftaCol.setCellValueFactory(new PropertyValueFactory<>("FTA"));

        TableColumn<Player, Double> ftaPerCol = new TableColumn<>("FT%");
        ftaPerCol.setMinWidth(50);
        ftaPerCol.setCellValueFactory(new PropertyValueFactory<>("FT_Percentage"));

        TableColumn<Player, Integer> twoACol = new TableColumn<>("2PA");
        twoACol.setMinWidth(50);
        twoACol.setCellValueFactory(new PropertyValueFactory<>("TwoPoint_Attempts"));

        TableColumn<Player, Double> twoPerCol = new TableColumn<>("2P%");
        twoPerCol.setMinWidth(50);
        twoPerCol.setCellValueFactory(new PropertyValueFactory<>("TwoPoint_Percentage"));

        TableColumn<Player, Integer> threeACol = new TableColumn<>("3PA");
        threeACol.setMinWidth(50);
        threeACol.setCellValueFactory(new PropertyValueFactory<>("ThreePoint_Attempts"));

        TableColumn<Player, Double> threePerCol = new TableColumn<>("2P%");
        threePerCol.setMinWidth(50);
        threePerCol.setCellValueFactory(new PropertyValueFactory<>("ThreePoint_Percentage"));

        TableColumn<Player, Double> efgCol = new TableColumn<>("EFG%");
        efgCol.setMinWidth(50);
        efgCol.setCellValueFactory(new PropertyValueFactory<>("EFG_Percentage"));

        TableColumn<Player, Double> tsCol = new TableColumn<>("TS%");
        tsCol.setMinWidth(50);
        tsCol.setCellValueFactory(new PropertyValueFactory<>("TS_Percentage"));

        TableColumn<Player, Double> ppgCol = new TableColumn<>("PPG");
        ppgCol.setMinWidth(50);
        ppgCol.setCellValueFactory(new PropertyValueFactory<>("PPG"));

        TableColumn<Player, Double> rpgCol = new TableColumn<>("RPG");
        rpgCol.setMinWidth(50);
        rpgCol.setCellValueFactory(new PropertyValueFactory<>("RPG"));

        TableColumn<Player, Double> apgCol = new TableColumn<>("APG");
        apgCol.setMinWidth(50);
        apgCol.setCellValueFactory(new PropertyValueFactory<>("APG"));

        TableColumn<Player, Double> spgCol = new TableColumn<>("SPG");
        spgCol.setMinWidth(50);
        spgCol.setCellValueFactory(new PropertyValueFactory<>("SPG"));

        TableColumn<Player, Double> bpgCol = new TableColumn<>("BPG");
        bpgCol.setMinWidth(50);
        bpgCol.setCellValueFactory(new PropertyValueFactory<>("BPG"));

        TableColumn<Player, Double> topgCol = new TableColumn<>("TOPG");
        topgCol.setMinWidth(50);
        topgCol.setCellValueFactory(new PropertyValueFactory<>("TOPG"));

        TableColumn<Player, Double> ortgCol = new TableColumn<>("ORTG");
        ortgCol.setMinWidth(50);
        ortgCol.setCellValueFactory(new PropertyValueFactory<>("ORTG"));

        TableColumn<Player, Double> drtgCol = new TableColumn<>("DRTG");
        drtgCol.setMinWidth(50);
        drtgCol.setCellValueFactory(new PropertyValueFactory<>("DRTG"));


        allPlayers = new TableView<>();
        allPlayers.setItems(getPlayerList());
        allPlayers.getColumns().addAll(nameCol, teamCol, ageCol, gameCol, minutesCol, usageCol, turnRatioCol, ftaCol, ftaPerCol, twoACol, twoPerCol, threeACol, threePerCol, efgCol, tsCol, ppgCol, rpgCol, apgCol, spgCol, bpgCol, topgCol, ortgCol, drtgCol);

        VBox layout = new VBox();
        layout.getChildren().addAll(allPlayers);
        team = new Scene(layout);
    }

    private void initLoginButtons() {
        TextField enterUsername = new TextField();
        TextField enterPassword = new TextField();
        enterUsername.setPromptText("Username");
        enterPassword.setPromptText("Password");

        Alert userAlreadyExists = new Alert(Alert.AlertType.ERROR);
        userAlreadyExists.setTitle("Error");
        userAlreadyExists.setHeaderText("Invalid Input");
        userAlreadyExists.setContentText("User already exists");

        Alert invalidUserPass = new Alert(Alert.AlertType.ERROR);
        invalidUserPass.setTitle("Error");
        invalidUserPass.setHeaderText("Invalid Input");
        invalidUserPass.setContentText("Invalid Username/Password");

        Button login = new Button("Login");
        login.setOnAction(e -> {
            setUsername(enterUsername.getText(), enterPassword.getText());
            if (database.validUserLogin(this.user, this.pass)) {
                this.window.setScene(this.team);
            } else {
                invalidUserPass.showAndWait();
            }
        });

        Button create_account = new Button("Create Account");
        create_account.setOnAction(e -> {
            setUsername(enterUsername.getText(), enterPassword.getText());
            if (database.userExists(this.user)) {
                userAlreadyExists.showAndWait();
            } else {
                this.database.createUser(this.user, this.pass);
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(enterUsername, enterPassword, login, create_account);

        this.login = new Scene(layout, 300, 300);
    }

    private void setUsername(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    private ObservableList<Player> getPlayerList() {
        ObservableList<Player> playerList = FXCollections.observableArrayList();
        ResultSet set = database.getPlayers();
        Player p;
        try {
            while (set.next()) {
                String name = set.getString("PLAYER_ID");
                String team = set.getString("TEAM_ID");
                int age = set.getInt("AGE");
                int games_played = set.getInt("GAMES_PLAYED");
                double mpg = set.getBigDecimal("MPG").doubleValue();
                double minutesPer = set.getBigDecimal("MINUTES_PERCENTAGE").doubleValue();
                double usage = set.getBigDecimal("USAGE_PERCENTAGE").doubleValue();
                double turnRatio = set.getBigDecimal("TURNOVER_RATIO").doubleValue();
                int fta = set.getInt("FTA");
                double ftPer = set.getBigDecimal("FREETHROW_PERCENTAGE").doubleValue();
                int two = set.getInt("2PA");
                double twoPer = set.getBigDecimal("2PT_PERCENTAGE").doubleValue();
                int three = set.getInt("3PA");
                double threePer = set.getBigDecimal("3PT_PERCENTAGE").doubleValue();
                double efg = set.getBigDecimal("EFG_PERCENTAGE").doubleValue();
                double TSPer = set.getBigDecimal("TRUE_SHOOTING_PERCENTAGE").doubleValue();
                double ppg = set.getBigDecimal("PPG").doubleValue();
                double rpg = set.getBigDecimal("RPG").doubleValue();
                double apg = set.getBigDecimal("APG").doubleValue();
                double spg = set.getBigDecimal("SPG").doubleValue();
                double bpg = set.getBigDecimal("BPG").doubleValue();
                double topg = set.getBigDecimal("TOPG").doubleValue();
                double ortg = set.getBigDecimal("ORTG").doubleValue();
                double drtg = set.getBigDecimal("DRTG").doubleValue();

                p = new Player(name, team, age, games_played, mpg, minutesPer, usage, turnRatio, fta, ftPer, two, twoPer, three, threePer
                        , efg, TSPer, ppg, rpg, apg, spg, bpg, topg, ortg, drtg);

                System.out.println(playerList.size());
                playerList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could no retrieve observable list of players");
        }
        return playerList;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
