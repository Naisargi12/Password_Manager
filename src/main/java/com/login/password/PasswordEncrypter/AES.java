package com.login.password.PasswordEncrypter;

import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class AES {

    public static String encrypt(String strClearText,String strKey) throws Exception{
        String strData="";

        try {
            SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"AES");
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
            byte[] encrypted=cipher.doFinal(strClearText.getBytes("UTF8"));
            encrypted = Base64.getEncoder().encode(encrypted);

            strData=new String(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return strData;
    }

    public static String decrypt(String strEncrypted,String strKey) throws Exception{
        String strData="";

        try {
            SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"AES");
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeyspec);
            byte[] dec = Base64.getDecoder().decode(strEncrypted.getBytes());
            byte[] decrypted=cipher.doFinal(dec);
            strData=new String(decrypted,"UTF8");

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return strData;
    }
}
