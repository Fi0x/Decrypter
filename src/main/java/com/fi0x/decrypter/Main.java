package com.fi0x.decrypter;

import com.fi0x.decrypter.userinteraction.Out;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        Parent root;
        try
        {
            root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        } catch(IOException ignored)
        {
            Out.newBuilder("Could not load GUI. Shutting down").always().WARNING().print();
            return;
        }

        primaryStage.setTitle("Decryption Program");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        ArrayList<String> arguments = new ArrayList<>(Arrays.asList(args));
        if(arguments.contains("-v")) Out.v = true;
        if(arguments.contains("-vv")) Out.vv = true;
        if(arguments.contains("-d")) Out.d = true;

        Out.newBuilder("Starting Program").veryVerbose().print();
        launch(args);
    }
}
