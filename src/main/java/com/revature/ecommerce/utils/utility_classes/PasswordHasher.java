package com.revature.ecommerce.utils.utility_classes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.GeneralSecurityException;
import java.security.spec.KeySpec;

@Component
public class PasswordHasher {

    @Value("${saltTwo}")
    private String salt;

    private static String staticSalt;

    public static char[] hash(String password) {

        try {
            byte[] salt = getSalt();
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            char[] arr = new char[hash.length];
            for (int i = 0; i < hash.length; i++)
                arr[i] = (char) hash[i];
            return arr;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] getSalt() {
        return DatatypeConverter.parseBase64Binary(staticSalt);
    }

    @Value("${saltTwo}")
    public void setStaticSalt(String salt) {
        PasswordHasher.staticSalt = salt;
    }
}
