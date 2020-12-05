package com.fi0x.decrypter.decryption;

import com.fi0x.decrypter.decryption.caesar.Caesar;
import com.fi0x.decrypter.decryption.skytale.Skytale;
import com.fi0x.decrypter.userinteraction.Out;
import com.fi0x.decrypter.util.enums.CIPHER;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

public class DecryptionHandler
{
    private static DecryptionHandler instance;

    private String encryptedString;
    private final ArrayList<String> decryptedVersions;
    private final IntegerProperty count;

    private final ArrayList<CIPHER> ciphers;
    ArrayList<Thread> threads;
    private int running;

    private DecryptionHandler()
    {
        decryptedVersions = new ArrayList<>();
        count = new SimpleIntegerProperty(0);
        ciphers = new ArrayList<>();
        threads = new ArrayList<>();
        running = 0;
    }
    public static DecryptionHandler getInstance()
    {
        if(instance == null) instance = new DecryptionHandler();
        return instance;
    }

    public void startDecryption()
    {
        for(CIPHER c : ciphers)
        {
            switch(c)
            {
                case CAESAR:
                    startCaesar();
                    break;
                case SKYTALE:
                    addSkytales();
                    break;
                default:
                    Out.newBuilder("Unknown encryption selected").origin(this.getClass().getName()).debug().WARNING().print();
                    break;
            }
        }
    }
    public void stopDecryption()
    {
        for(Thread t : threads) t.interrupt();
    }
    public void addCipherToCheck(CIPHER cipher)
    {
        ciphers.add(cipher);
    }
    public void resetHandler()
    {
        ciphers.clear();
        decryptedVersions.clear();
        count.set(0);
        running = 0;
    }

    public void addRunning()
    {
        running++;
        Out.newBuilder("Running decrypters: " + running).verbose().print();
    }
    public void removeRunning()
    {
        running--;
        if(running < 0) running = 0;
        Out.newBuilder("Running decrypters: " + running).verbose().print();
    }

    private void startCaesar()
    {
        Thread t = new Thread(new Caesar());
        t.start();
        addRunning();
        threads.add(t);
        Out.newBuilder("Caesar Thread created").verbose().print();
    }
    private void addSkytales()
    {
        Thread t = new Thread(new Skytale());
        t.start();
        addRunning();
        threads.add(t);
        Out.newBuilder("Skytale Thread created").verbose().print();
    }

    public String getEncryptedString()
    {
        return encryptedString;
    }
    public void setEncryptedString(String input)
    {
        this.encryptedString = input;
    }
    public void addDecryptedVersion(String decryptedText)
    {
        if(decryptedText != null && !decryptedText.isEmpty())
        {
            decryptedVersions.add(decryptedText);
            count.set(count.get() + 1);
        }
    }
    public String getDecryptedVersion(int index)
    {
        if(index >= count.get()) return null;
        return decryptedVersions.get(index);
    }
    public IntegerProperty getDecryptedVersionsCount()
    {
        return count;
    }
}
