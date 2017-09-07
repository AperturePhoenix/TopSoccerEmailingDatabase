package core;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Lance Judan on 9/5/2017.
 */
public class FileManager {
    private static byte[] key = {0x01, 0x25, 0x13, 0x71, 0x03};
    private static String algorithm = "Blowfish";
    private static SecretKey key64 = new SecretKeySpec(key, algorithm);

    public static Serializable loadFile(String fileName) {
        String path = getFilePath(fileName);
        Serializable input = null;
        try {
            if (new File(path).exists()) {
                Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
                CipherInputStream cipherInputStream = new CipherInputStream(new FileInputStream(path), cipher);
                ObjectInputStream in = new ObjectInputStream(cipherInputStream);
                SealedObject sealedObject = (SealedObject) in.readObject();
                input = (Serializable) sealedObject.getObject(cipher);
                in.close();
                return input;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Could not load contacts", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return input;
    }

    public static void saveFile(Serializable object, String fileName) {
        File tempFolder = new File(getFolderPath());
        try {
            //Makes sure "Top Soccer Database" folder exists before saving
            if (!tempFolder.exists()) {
                tempFolder.mkdir();
            }
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
            SealedObject sealedObject = new SealedObject(object, cipher);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(new FileOutputStream(getFilePath(fileName)), cipher);
            ObjectOutputStream out = new ObjectOutputStream(cipherOutputStream);
            out.writeObject(sealedObject);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Could not save contacts", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Returns the path of file based on operating system
    private static String getFilePath(String fileName) {
        String osName = System.getProperty("os.name");
        String path = System.getProperty("user.home");
        path += osName.equalsIgnoreCase("Linux") ? "/Documents/Top Soccer Database/" : "\\Documents\\Top Soccer Database\\";
        return path + fileName;
    }

    //Returns the path of the "Top Soccer Database" folder based on operating system
    private static String getFolderPath() {
        String osName = System.getProperty("os.name");
        String path = System.getProperty("user.home");
        path += osName.equalsIgnoreCase("Linux") ? "/Documents/Top Soccer Database/" : "\\Documents\\Top Soccer Database\\";
        return path;
    }

    //Returns cipher initialized to the specified mode
    private static Cipher getCipher(int mode) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(algorithm);
            switch(mode) {
                case Cipher.ENCRYPT_MODE:
                    cipher.init(Cipher.ENCRYPT_MODE, key64);
                    break;
                case Cipher.DECRYPT_MODE:
                    cipher.init(Cipher.DECRYPT_MODE, key64);
                    break;
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return cipher;
    }
}
