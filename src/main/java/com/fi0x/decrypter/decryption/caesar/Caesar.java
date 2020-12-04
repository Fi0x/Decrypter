package com.fi0x.decrypter.decryption.caesar;

import com.fi0x.decrypter.decryption.DecryptionHandler;
import com.fi0x.decrypter.userinteraction.Out;
import com.fi0x.decrypter.util.enums.CIPHER;

public class Caesar implements Runnable
{
    private final char[] alphabet;
    private final char[] shifted;

    public Caesar()//TODO: Implement alphabet from file
    {
        alphabet = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        shifted = alphabet.clone();
    }

    @Override
    public void run()
    {
        DecryptionHandler handler = DecryptionHandler.getInstance();
        do
        {
            StringBuilder decrypted1 = new StringBuilder();
            for(char character : DecryptionHandler.getInstance().getEncryptedString().toCharArray())
            {
                for(int i = 0; i < alphabet.length; i++)
                {
                    if(character == alphabet[i])
                    {
                        decrypted1.append(shifted[i]);
                        break;
                    }
                }
                if(Thread.interrupted())
                {
                    Out.newBuilder("Caesar decryption interrupted").verbose().WARNING().print();
                    return;
                }
            }

            handler.addDecryptedVersion(CIPHER.CAESAR, decrypted1.toString());
            shiftAlphabet();
            Out.newBuilder("New caesar variant added").veryVerbose().print();
        } while(shifted[0] != alphabet[0]);
        Out.newBuilder("Caesar decryption finished").verbose().ALERT().print();
    }

    private void shiftAlphabet()
    {
        char tmp = shifted[0];
        System.arraycopy(shifted, 1, shifted, 0, shifted.length - 1);
        shifted[shifted.length - 1] = tmp;
    }
}
