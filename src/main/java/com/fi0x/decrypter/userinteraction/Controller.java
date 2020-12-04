package com.fi0x.decrypter.userinteraction;

import com.fi0x.decrypter.decryption.DecryptionHandler;
import com.fi0x.decrypter.util.enums.CIPHER;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller
{
    private int currentResult;
    DecryptionHandler decrypter = DecryptionHandler.getInstance();

    @FXML
    private TextField input;
    @FXML
    private CheckBox caesar;
    @FXML
    private CheckBox skytale;
    @FXML
    public Label caesarRunning;
    @FXML
    public Label skytaleRunning;
    @FXML
    private TextField result;

    @FXML
    private void start()
    {
        if(input.getText().isEmpty())
        {
            Out.newBuilder("No input to decrypt").always().WARNING().print();
        } else
        {
            Out.newBuilder("Resetting decrypter").verbose().print();
            decrypter.resetHandler();

            Out.newBuilder("Starting decryption").veryVerbose().print();
            decrypter.setEncryptedString(input.getText());
            if(caesar.isSelected()) decrypter.addCipherToCheck(CIPHER.CAESAR);
            if(skytale.isSelected()) decrypter.addCipherToCheck(CIPHER.SKYTALE);

            decrypter.startDecryption();
            currentResult = -1;

            input.clear();
        }
    }
    @FXML
    private void cancel()
    {
        DecryptionHandler.getInstance().stopDecryption();
    }
    @FXML
    private void showResult()
    {
        if(decrypter.getDecryptedVersionCount() == 0) result.setText("No solution found yet");
        else
        {
            currentResult++;
            if(currentResult >= decrypter.getDecryptedVersionCount() || currentResult < 0) currentResult = 0;
            result.setText((currentResult + 1) + ")" + decrypter.getDecryptedVersion(currentResult));
        }
    }
}
