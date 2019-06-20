package league;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FantasyApplication extends Application {
    private Stage window;
    private Scene login, team, league;
    private String user, pass;
    private Database_Utils database;
    private TableView<Player> allPlayers;
    private TableView<Team> allTeams;

    public void start(Stage primaryStage) {
        this.window = primaryStage;
        database = new Database_Utils();
        this.window.setTitle("NBA Fantasy League");
        this.window.getIcons().add(new Image("/images/fantasylogo.png"));

        initLoginButtons();

        createPlayerColumns();
        createTeamColumns();

        initTeamView();
        initLeagueView();

        this.login.getStylesheets().add("nbaStyle.css");
        this.team.getStylesheets().add("nbaStyle.css");
        this.league.getStylesheets().add("nbaStyle.css");


        this.window.setScene(this.login);
        this.window.centerOnScreen();
        this.window.show();
    }

    private void initLoginButtons() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setAlignment(Pos.CENTER);

        Label title = new Label("NBA FANTASY LEAGUE");
       // title.setStyle("-fx-font-size: 25;");
        GridPane.setConstraints(title, 0, 0);

        ImageView logo = new ImageView(new Image("/images/fantasylogo5.png"));
        GridPane.setConstraints(logo, 1,0);

        Label username = new Label("Username: ");
        GridPane.setConstraints(username, 0, 1);

        TextField enterUsername = new TextField();
        enterUsername.setPromptText("Username");
        GridPane.setConstraints(enterUsername, 1, 1);

        Label password = new Label("Password: ");
        GridPane.setConstraints(password, 0, 2);

        TextField enterPassword = new TextField();
        enterPassword.setPromptText("Password");
        GridPane.setConstraints(enterPassword, 1, 2);

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
                enterUsername.clear();
                enterPassword.clear();
                this.window.setScene(this.team);
                this.window.centerOnScreen();
            } else {
                invalidUserPass.showAndWait();
            }
        });
        GridPane.setConstraints(login, 0, 3);

        Button create_account = new Button("Create Account");
        create_account.setOnAction(e -> {
            setUsername(enterUsername.getText(), enterPassword.getText());
            if (database.userExists(this.user)) {
                userAlreadyExists.showAndWait();
            } else {
                this.database.createUser(this.user, this.pass);
            }
        });
        GridPane.setConstraints(create_account, 1, 3);

        grid.getChildren().addAll(title, logo, username, enterUsername, password, enterPassword, login, create_account);

        this.login = new Scene(grid, 400, 300);
    }

    private void initTeamView() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setAlignment(Pos.CENTER);

        allPlayers.setItems(getPlayerList(this.database.getAllPlayers()));
        GridPane.setConstraints(allPlayers, 0, 1, 4, 7);

        Label title = new Label("All Current Players     ");
        title.setStyle("-fx-font-size: 25;");
        GridPane.setConstraints(title, 0, 0);

        Label search = new Label("Search: ");
        GridPane.setConstraints(search, 1, 0);

        TextField searchBox = new TextField();
        searchBox.setPromptText("Player Name");
        GridPane.setConstraints(searchBox, 2, 0);

        Button searchButton = new Button("League Search");
        searchButton.setOnAction(e ->
                allPlayers.setItems((getPlayerList(this.database.searchPlayers(searchBox.getText()))))
        );
        GridPane.setConstraints(searchButton, 3, 0);


        Button delete_account = new Button("Delete Account");
        delete_account.setOnAction(e -> {
            this.database.deleteAccount(this.user);
            this.window.setScene(login);
            this.window.centerOnScreen();
        });
        GridPane.setConstraints(delete_account, 5, 0);

        Button logOut = new Button("Logout");
        logOut.setOnAction(e -> {
            this.window.setScene(login);
            this.window.centerOnScreen();
        });
        GridPane.setConstraints(logOut, 5, 1);

        Button viewTeam = new Button("View Team Players");
        viewTeam.setOnAction(e -> {
            title.setText("Your Players      ");
            allPlayers.setItems(getPlayerList(this.database.getTeamPlayers(this.user)));
            });

        GridPane.setConstraints(viewTeam, 5, 2);

        Button viewAllPlayers = new Button("View All Players");
        viewAllPlayers.setOnAction(e -> {
            title.setText("All Current Players     ");
            allPlayers.setItems(getPlayerList(this.database.getAllPlayers()));
        });
        GridPane.setConstraints(viewAllPlayers, 5, 3);

        Button addPlayer = new Button("Add selected player");
        addPlayer.setOnAction(e ->
                this.database.addPlayer(this.user, allPlayers.getSelectionModel().getSelectedItem().getName()));
        GridPane.setConstraints(addPlayer, 5, 4);

        Button removePlayer = new Button("Remove selected player");
        removePlayer.setOnAction(e -> {
            this.database.removePlayer(this.user, allPlayers.getSelectionModel().getSelectedItem().getName());
            allPlayers.setItems(getPlayerList(this.database.getTeamPlayers(this.user)));
        });
        GridPane.setConstraints(removePlayer, 5, 5);

        Button viewLeague = new Button("Show League");
        viewLeague.setOnAction(e -> {
            allTeams.setItems(getTeamList(this.database.getTeams()));
            this.window.setScene(this.league);
            this.window.centerOnScreen();
        });
        GridPane.setConstraints(viewLeague, 5, 6);

        ImageView logo = new ImageView(new Image("/images/fantasylogo.png"));
        logo.setStyle("-fx-border-radius: 30; -fx-background-radius: 10 10 0 0;");

        GridPane.setConstraints(logo,5, 7);

        grid.getChildren().addAll(allPlayers, search, searchBox, searchButton, viewTeam, viewAllPlayers, addPlayer, removePlayer, logOut, viewLeague, title, logo, delete_account);
        team = new Scene(grid);
    }

    private void initLeagueView() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(8);

        createTeamColumns();

        allTeams.setItems(getTeamList(this.database.getTeams()));
        GridPane.setConstraints(allTeams, 0, 1, 4, 5);

        Button back = new Button("Back to Team");
        back.setOnAction(e -> {
            window.setScene(this.team);
            window.centerOnScreen();
        });
        GridPane.setConstraints(back, 1, 0);

        grid.getChildren().addAll(allTeams, back);
        grid.setAlignment(Pos.CENTER);
        league = new Scene(grid);
    }

    private void createPlayerColumns() {
        TableColumn<Player, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(130);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn<Player, String> teamCol = new TableColumn<>("Team");
        teamCol.setMinWidth(50);
        teamCol.setCellValueFactory(new PropertyValueFactory<>("Team"));

        TableColumn<Player, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setMinWidth(50);
        ageCol.setCellValueFactory(new PropertyValueFactory<>("Age"));

        TableColumn<Player, Integer> gameCol = new TableColumn<>("GP");
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

        TableColumn<Player, Double> threePerCol = new TableColumn<>("3P%");
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
        allPlayers.getColumns().addAll(nameCol, teamCol, ageCol, gameCol, minutesCol, usageCol, turnRatioCol, ftaCol, ftaPerCol, twoACol, twoPerCol, threeACol, threePerCol, efgCol, tsCol, ppgCol, rpgCol, apgCol, spgCol, bpgCol, topgCol, ortgCol, drtgCol);
        allPlayers.setMaxWidth(1270);
    }

    private void createTeamColumns() {
        TableColumn<Team, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(130);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn<Team, Double> teamAgeCol = new TableColumn<>("Age");
        teamAgeCol.setMinWidth(50);
        teamAgeCol.setCellValueFactory(new PropertyValueFactory<>("Age"));

        TableColumn<Team, Integer> ftaCol = new TableColumn<>("FTA");
        ftaCol.setMinWidth(50);
        ftaCol.setCellValueFactory(new PropertyValueFactory<>("FTA"));

        TableColumn<Team, Double> ftaPerCol = new TableColumn<>("FT%");
        ftaPerCol.setMinWidth(50);
        ftaPerCol.setCellValueFactory(new PropertyValueFactory<>("FT_Percentage"));

        TableColumn<Team, Integer> twoACol = new TableColumn<>("2PA");
        twoACol.setMinWidth(50);
        twoACol.setCellValueFactory(new PropertyValueFactory<>("TwoPoint_Attempts"));

        TableColumn<Team, Double> twoPerCol = new TableColumn<>("2P%");
        twoPerCol.setMinWidth(50);
        twoPerCol.setCellValueFactory(new PropertyValueFactory<>("TwoPoint_Percentage"));

        TableColumn<Team, Integer> threeACol = new TableColumn<>("3PA");
        threeACol.setMinWidth(50);
        threeACol.setCellValueFactory(new PropertyValueFactory<>("ThreePoint_Attempts"));

        TableColumn<Team, Double> threePerCol = new TableColumn<>("2P%");
        threePerCol.setMinWidth(50);
        threePerCol.setCellValueFactory(new PropertyValueFactory<>("ThreePoint_Percentage"));

        TableColumn<Team, Double> efgCol = new TableColumn<>("EFG%");
        efgCol.setMinWidth(50);
        efgCol.setCellValueFactory(new PropertyValueFactory<>("EFG_Percentage"));

        TableColumn<Team, Double> tsCol = new TableColumn<>("TS%");
        tsCol.setMinWidth(50);
        tsCol.setCellValueFactory(new PropertyValueFactory<>("TS_Percentage"));

        TableColumn<Team, Double> ppgCol = new TableColumn<>("PPG");
        ppgCol.setMinWidth(50);
        ppgCol.setCellValueFactory(new PropertyValueFactory<>("PPG"));

        TableColumn<Team, Double> rpgCol = new TableColumn<>("RPG");
        rpgCol.setMinWidth(50);
        rpgCol.setCellValueFactory(new PropertyValueFactory<>("RPG"));

        TableColumn<Team, Double> apgCol = new TableColumn<>("APG");
        apgCol.setMinWidth(50);
        apgCol.setCellValueFactory(new PropertyValueFactory<>("APG"));

        TableColumn<Team, Double> spgCol = new TableColumn<>("SPG");
        spgCol.setMinWidth(50);
        spgCol.setCellValueFactory(new PropertyValueFactory<>("SPG"));

        TableColumn<Team, Double> bpgCol = new TableColumn<>("BPG");
        bpgCol.setMinWidth(50);
        bpgCol.setCellValueFactory(new PropertyValueFactory<>("BPG"));

        TableColumn<Team, Double> topgCol = new TableColumn<>("TOPG");
        topgCol.setMinWidth(50);
        topgCol.setCellValueFactory(new PropertyValueFactory<>("TOPG"));


        allTeams = new TableView<>();
        allTeams.getColumns().addAll(nameCol, teamAgeCol, ftaCol, ftaPerCol, twoACol, twoPerCol, threeACol, threePerCol, efgCol, tsCol, ppgCol, rpgCol, apgCol, spgCol, bpgCol, topgCol);
        allTeams.setMaxWidth(1000);
    }

    private void setUsername(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    private ObservableList<Player> getPlayerList(ResultSet set) {
        ObservableList<Player> playerList = FXCollections.observableArrayList();
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

    private ObservableList<Team> getTeamList(ResultSet set) {
        ObservableList<Team> teamList = FXCollections.observableArrayList();
        Team t;
        try {
            while (set.next()) {
                String name = set.getString("TEAM_NAME");
                double age = set.getBigDecimal("AGE").doubleValue();
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

                t = new Team(name, age, fta, ftPer, two, twoPer, three, threePer
                        , efg, TSPer, ppg, rpg, apg, spg, bpg, topg);
                teamList.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not retrieve observable list of teams");
        }
        return teamList;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
