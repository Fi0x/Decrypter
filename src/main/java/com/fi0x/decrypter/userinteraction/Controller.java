package com.fi0x.decrypter.userinteraction;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller
{
    @FXML
    private TextField input;

    @FXML
    private void start()
    {
        //TODO: Add function
        System.out.println("String to decrypt: " + input.getText());
    }
    @FXML
    private void cancel()
    {
        //TODO: Add function
    }
}
