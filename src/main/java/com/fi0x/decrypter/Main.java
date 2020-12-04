package com.fi0x.decrypter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        GridPane root = new GridPane();
        primaryStage.setTitle("Decryption Program");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        TextField txtInput = new TextField();
        root.add(txtInput, 0, 0);

        Button btnStart = new Button("Start");
        root.add(btnStart, 0, 1);

        Button btnCancel = new Button("Cancel");
        root.add(btnCancel, 1, 1);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
