package br.com.essenciaviva.security;

import br.com.essenciaviva.config.Config;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public final class Crypto {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static byte[] key(String name) {
        String value = Config.get(name, "chave-de-desenvolvimento-troque-no-computador");
        byte[] raw = value.getBytes(StandardCharsets.UTF_8);
        byte[] out = new byte[32];
        System.arraycopy(raw, 0, out, 0, Math.min(raw.length, out.length));
        return out;
    }
    public static String encrypt(String text) {
        try {
            byte[] iv = new byte[12]; RANDOM.nextBytes(iv);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key("APP_KEY"), "AES"), new GCMParameterSpec(128, iv));
            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            byte[] result = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, result, 0, iv.length); System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) { throw new IllegalStateException("Não foi possível proteger o dado", e); }
    }
    public static String decrypt(String value) {
        try {
            byte[] all = Base64.getDecoder().decode(value); byte[] iv = new byte[12];
            byte[] encrypted = new byte[all.length - 12]; System.arraycopy(all, 0, iv, 0, 12); System.arraycopy(all, 12, encrypted, 0, encrypted.length);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key("APP_KEY"), "AES"), new GCMParameterSpec(128, iv));
            return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
        } catch (Exception e) { throw new IllegalStateException("Não foi possível ler o dado protegido", e); }
    }
    public static String hmac(String normalized) {
        try { Mac mac = Mac.getInstance("HmacSHA256"); mac.init(new SecretKeySpec(key("HMAC_KEY"), "HmacSHA256")); return Hex.format(mac.doFinal(normalized.getBytes(StandardCharsets.UTF_8))); }
        catch (Exception e) { throw new IllegalStateException(e); }
    }
    private static final class Hex { static String format(byte[] b) { StringBuilder s=new StringBuilder(); for(byte x:b)s.append(String.format("%02x",x)); return s.toString(); } }
}
