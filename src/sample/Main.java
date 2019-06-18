package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage window;
    private Scene login, team;
    private String user, pass;
    private Database_Utils database;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.window = primaryStage;
        database = new Database_Utils();
        this.window .setTitle("NBA Fantasy League");

        initLoginButtons();
        initTeamView();
        this.window.setScene(this.login);
        this.window.show();
    }

    private void initTeamView() {
        VBox layout = new VBox(10);
        this.team = new Scene(layout, 50, 50);
    }

    private void initLoginButtons() {
        TextField enterUsername = new TextField();
        TextField enterPassword = new TextField();
        enterUsername.setText("Username");
        enterPassword.setText("Password");

        Alert userAlreadyExists = new Alert(Alert.AlertType.ERROR);
        userAlreadyExists.setTitle("Error");
        userAlreadyExists.setHeaderText("Invalid Input");
        userAlreadyExists.setContentText("User already exists");

        Alert invalidUserPass = new Alert(Alert.AlertType.ERROR);
        invalidUserPass.setTitle("Error");
        invalidUserPass.setHeaderText("Invalid Input");
        invalidUserPass.setContentText("Invalid Username/Password");

        Button login = new Button("Login");
        login.setOnAction( e -> {
            setUsername(enterUsername.getText(), enterPassword.getText());
            if(database.ValidUserLogin(this.user, this.pass)){
                this.window.setScene(this.team);
            } else {
                invalidUserPass.showAndWait();
            }
        });

        Button create_account = new Button("Create Account");
        create_account.setOnAction(e -> {
            setUsername(enterUsername.getText(), enterPassword.getText());
            if(database.userExists(this.user)){
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

    public static void main(String[] args) {
        launch(args);
    }
}
