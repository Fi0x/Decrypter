package com.fi0x.decrypter.userinteraction;

import com.fi0x.decrypter.decryption.DecryptionHandler;
import com.fi0x.decrypter.util.enums.CIPHER;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

public class Controller
{
    private final IntegerProperty currentResult = new SimpleIntegerProperty();
    private final DecryptionHandler decrypter = DecryptionHandler.getInstance();

    @FXML
    private TextField input;
    @FXML
    private CheckBox caesar;
    @FXML
    private CheckBox skytale;
    @FXML
    private TextField currentDecryption;
    @FXML
    private TextField possibleDecryptions;
    @FXML
    private TextField result;

    @FXML
    public void initialize()
    {
        currentDecryption.textProperty().bindBidirectional(currentResult, new NumberStringConverter());
        //TODO: Bind field without creating exceptions
//        possibleDecryptions.textProperty().bind(decrypter.getDecryptedVersionsCount().asString());
    }

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
            currentResult.set(0);

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
    private void showNextResult()
    {
        if(decrypter.getDecryptedVersionsCount().get() == 0) result.setText("No solution found yet");
        else
        {
            currentResult.set(currentResult.get() + 1);
            if(currentResult.get() > decrypter.getDecryptedVersionsCount().get() || currentResult.get() < 1) currentResult.set(1);
            if(currentResult.get() != 0) result.setText(decrypter.getDecryptedVersion(currentResult.get() - 1));
        }
    }
}
