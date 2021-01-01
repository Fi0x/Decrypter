package com.fi0x.decrypter.decryption.skytale;

import com.fi0x.decrypter.decryption.DecryptionHandler;
import com.fi0x.decrypter.userinteraction.Out;
import com.fi0x.decrypter.util.enums.CIPHER;

public class Skytale implements Runnable
{
    private char[] encrypted;

    @Override
    public void run()
    {
        DecryptionHandler handler = DecryptionHandler.getInstance();
        encrypted = handler.getEncryptedString().toCharArray();
        for(int i = 1; i < encrypted.length; i++)
        {
            handler.addDecryptedVersion(CIPHER.SKYTALE, decrypt(i));
            Out.newBuilder("New skytale decryption added").veryVerbose().print();

            if(Thread.interrupted())
            {
                Out.newBuilder("Skytale decryption interrupted").verbose().WARNING().print();
                handler.removeRunning();
                return;
            }
        }

        handler.removeRunning();
        Out.newBuilder("Skytale decryption finished").verbose().ALERT().print();
    }

    private String decrypt(int rows)
    {
        String decrypted = "";

        for(int row = 0; row < rows; row++)
        {
            for(int i = 0; i < encrypted.length; i++)
            {
                if(i % rows == row) decrypted += encrypted[i];
            }
        }

        return decrypted;
    }
}
