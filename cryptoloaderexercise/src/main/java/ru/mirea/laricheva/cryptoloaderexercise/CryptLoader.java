package ru.mirea.laricheva.cryptoloaderexercise;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptLoader extends AsyncTaskLoader<String> {
    private String decryptText;
    private String crypt;
    public static final String ARG_WORD = "text";
    public CryptLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null) {
            byte[] cryptText = args.getByteArray(ARG_WORD);
            byte[] key = args.getByteArray("key");
            Log.d("key + crypt: ", cryptText.toString() + ' ' + key.toString());
            SecretKey originalKey = new SecretKeySpec(key, 0, key.length, "AES");
            String decryptText = decryptMsg(cryptText, originalKey);
            Log.d("Decrypted text 2: ", decryptText);
        }
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Override
    public String loadInBackground() {
        // emulate long-running operation
        SystemClock.sleep(3000);
        return decryptText;
    }

    public static String decryptMsg(byte[] cipherText, SecretKey secret){
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return new String(cipher.doFinal(cipherText));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                 | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
