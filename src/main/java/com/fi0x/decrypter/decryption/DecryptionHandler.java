package com.fi0x.decrypter.decryption;

import com.fi0x.decrypter.decryption.caesar.Caesar;
import com.fi0x.decrypter.decryption.skytale.Skytale;
import com.fi0x.decrypter.userinteraction.Out;
import com.fi0x.decrypter.util.enums.CIPHER;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DecryptionHandler extends Observable
{
    private static DecryptionHandler instance;

    private String encryptedString;
    private final ArrayList<String> decryptedVersions;
    private int count;

    private final ArrayList<CIPHER> ciphers;
    ArrayList<Thread> threads;
    private int running;

    private DecryptionHandler()
    {
        decryptedVersions = new ArrayList<>();
        count = 0;
        setChanged();
        notifyObservers(count);
        ciphers = new ArrayList<>();
        threads = new ArrayList<>();
        running = 0;
    }
    public static DecryptionHandler getInstance(Observer observer)
    {
        if(instance == null) instance = new DecryptionHandler();
        instance.addObserver(observer);
        return instance;
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
        count = 0;
        setChanged();
        notifyObservers(count);
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
            count++;
            setChanged();
            notifyObservers(count);
        }
    }
    public String getDecryptedVersion(int index)
    {
        if(index >= count) return null;
        return decryptedVersions.get(index);
    }
    public int getDecryptedVersionsCount()
    {
        return count;
    }
}
