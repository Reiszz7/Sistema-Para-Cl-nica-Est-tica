package br.com.essenciaviva.security;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordUtil {
    private PasswordUtil() { }
    public static String hash(String password) {
        try { byte[] salt=new byte[16]; new SecureRandom().nextBytes(salt); return Base64.getEncoder().encodeToString(salt)+":"+Base64.getEncoder().encodeToString(derive(password.toCharArray(),salt)); }
        catch(Exception e){throw new IllegalStateException(e);}
    }
    public static boolean matches(String password, String stored) {
        try { String[] p=stored.split(":"); return java.security.MessageDigest.isEqual(Base64.getDecoder().decode(p[1]), derive(password.toCharArray(),Base64.getDecoder().decode(p[0]))); }
        catch(Exception e){return false;}
    }
    private static byte[] derive(char[] p, byte[] salt) throws Exception { KeySpec spec=new PBEKeySpec(p,salt,120000,256); return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded(); }
}
