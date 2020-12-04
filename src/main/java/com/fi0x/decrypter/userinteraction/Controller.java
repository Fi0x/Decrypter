package com.fi0x.decrypter.userinteraction;

import com.fi0x.decrypter.decryption.DecryptionHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller
{
    @FXML
    private TextField input;

    @FXML
    private void start()
    {
        DecryptionHandler.getInstance().setEncryptedString(input.getText());
        //TODO: Selection of decryption methods
        DecryptionHandler.getInstance().startDecryption();
    }
    @FXML
    private void cancel()
    {
        DecryptionHandler.getInstance().stopDecryption();
    }
}
