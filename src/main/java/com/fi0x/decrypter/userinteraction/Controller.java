package com.fi0x.decrypter.userinteraction;

import com.fi0x.decrypter.decryption.DecryptionHandler;
import com.fi0x.decrypter.util.enums.CIPHER;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer
{
    private int currentResult;
    private final DecryptionHandler decrypter = DecryptionHandler.getInstance(this);

    @FXML
    private TextField input;
    @FXML
    private CheckBox caesar;
    @FXML
    private CheckBox skytale;
    @FXML
    private TextField possibleDecryptions;
    @FXML
    private Label running;
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
            result.clear();
        }
    }
    @FXML
    private void cancel()
    {
        decrypter.stopDecryption();
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
    @Override
    public void update(Observable o, Object running)
    {
        this.running.setVisible((int) running > 0);
        this.running.setText("running");

        possibleDecryptions.setText("" + decrypter.getDecryptedVersionCount());//TODO: Fix occasional error
    }
}
