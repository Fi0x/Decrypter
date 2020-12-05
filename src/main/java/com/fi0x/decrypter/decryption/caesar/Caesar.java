package com.fi0x.decrypter.decryption.caesar;

import com.fi0x.decrypter.decryption.DecryptionHandler;
import com.fi0x.decrypter.userinteraction.Out;
import com.fi0x.decrypter.util.Variables;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Caesar implements Runnable
{
    private final ArrayList<char[]> alphabetList;

    public Caesar()
    {
        alphabetList = new ArrayList<>();
    }

    @Override
    public void run()
    {
        DecryptionHandler handler = DecryptionHandler.getInstance();
        Out.newBuilder("Loading caesar alphabet").verbose().print();
        loadAlphabet();

        for(char[] alphabet : alphabetList)
        {
            char[] shifted = alphabet.clone();
            do
            {
                handler.addDecryptedVersion(decrypt(alphabet, shifted));
                Out.newBuilder("New caesar decryption added").veryVerbose().print();
                shiftAlphabet(shifted);

                if(Thread.interrupted())
                {
                    Out.newBuilder("Caesar decryption interrupted").verbose().WARNING().print();
                    handler.removeRunning();
                    return;
                }
            } while(shifted[0] != alphabet[0]);
        }

        handler.removeRunning();
        Out.newBuilder("Caesar decryption finished").verbose().ALERT().print();
    }

    private void loadAlphabet()
    {
        URL url = getClass().getClassLoader().getResource(Variables.alphabetFile);
        if(url == null)
        {
            alphabetList.add(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'});
            Out.newBuilder("Failed to load alphabet file. Falling back to default").debug().print();
            return;
        }
        try
        {
            Scanner scanner = new Scanner(new File(url.toURI()));
            while(scanner.hasNextLine())
            {
                alphabetList.add(scanner.nextLine().toCharArray());
            }
        } catch(Exception ignored)
        {
        }
        Out.newBuilder("Alphabet file loaded").verbose().print();
    }

    private void shiftAlphabet(char[] arrayToShift)
    {
        char tmp = arrayToShift[0];
        System.arraycopy(arrayToShift, 1, arrayToShift, 0, arrayToShift.length - 1);
        arrayToShift[arrayToShift.length - 1] = tmp;
    }
    private String decrypt(char[] alphabet, char[] shifted)
    {
        StringBuilder decrypted = new StringBuilder();
        for(char character : DecryptionHandler.getInstance().getEncryptedString().toCharArray())
        {
            for(int i = 0; i < alphabet.length; i++)
            {
                if(character == alphabet[i])
                {
                    decrypted.append(shifted[i]);
                    break;
                }
            }
        }
        return decrypted.toString();
    }
}
