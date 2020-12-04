package com.fi0x.decrypter.decryption;

import com.fi0x.decrypter.decryption.caesar.Caesar;

import java.util.ArrayList;

public class DecryptionHandler
{
    private static DecryptionHandler instance;

    private String encryptedString;
    private ArrayList<Thread> caesars;

    private DecryptionHandler()
    {
    }

    public static DecryptionHandler getInstance()
    {
        if(instance == null) instance = new DecryptionHandler();
        return instance;
    }

    public void startDecryption()
    {
        for(int i = 0; i < 26; i++)
        {
            Thread c = new Thread(new Caesar());
            c.start();
            caesars.add(c);
        }
    }
    public void stopDecryption()
    {
        for(Thread c : caesars) c.interrupt();
    }

    public String getEncryptedString()
    {
        return encryptedString;
    }
    public void setEncryptedString(String input)
    {
        this.encryptedString = input;
    }
}
