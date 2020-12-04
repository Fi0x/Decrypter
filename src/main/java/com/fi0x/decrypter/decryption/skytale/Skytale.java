package com.fi0x.decrypter.decryption.skytale;

import com.fi0x.decrypter.decryption.DecryptionHandler;

public class Skytale implements Runnable
{
    @Override
    public void run()
    {
        DecryptionHandler handler = DecryptionHandler.getInstance();
        //TODO: Implement
        handler.removeRunning();
    }
}
