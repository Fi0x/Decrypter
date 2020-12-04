package com.fi0x.decrypter.decryption.skytale;

import com.fi0x.decrypter.decryption.DecryptionHandler;
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
        }

        handler.removeRunning();
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
