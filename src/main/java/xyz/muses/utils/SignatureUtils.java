/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.utils;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.geronimo.mail.util.Base64;

/**
 * @author jervis
 * @date 2020/12/3.
 */
public class SignatureUtils {

    /** 换行 */
    private static final String NEW_LINE = "\n";

    /** 算法 */
    private static final String ALGORITHM = "SHA256WITHRSA";

    /**
     * 加签
     *
     * @param text 待加签文本
     * @param privateKey 加签私钥
     * @return
     */
    @SuppressWarnings("restriction")
    public static String sign(String text, String privateKey) {
        try {
            byte[] signBytes = sign(Base64.encode(text.getBytes()), Base64.decode(privateKey.getBytes()), ALGORITHM);
            return new String(Base64.encode(signBytes)).replace(NEW_LINE, "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加签
     *
     * @param text
     * @param privateKeyData
     * @param algorithm
     * @return
     * @throws GeneralSecurityException
     */
    private static byte[] sign(final byte[] text, final byte[] privateKeyData, final String algorithm)
        throws GeneralSecurityException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyData);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        Signature signatureChecker = Signature.getInstance(algorithm);
        signatureChecker.initSign(privateKey);
        signatureChecker.update(text);
        return signatureChecker.sign();
    }

    /**
     * 验签
     *
     * @param text 待验签字符串
     * @param signedText 加签串
     * @param publicKey 验签公钥
     * @return
     */
    @SuppressWarnings("restriction")
    public static boolean verify(String text, String signedText, String publicKey) {
        try {
            return verify(Base64.encode(text.getBytes()), Base64.decode(signedText.getBytes()),
                Base64.decode(publicKey.getBytes()), ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验签
     *
     * @param text
     * @param signedText
     * @param publicKeyData
     * @param algorithm
     * @return
     * @throws GeneralSecurityException
     */
    private static boolean verify(final byte[] text, final byte[] signedText, final byte[] publicKeyData,
        final String algorithm) throws GeneralSecurityException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyData);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        Signature signatureChecker = Signature.getInstance(algorithm);
        signatureChecker.initVerify(publicKey);
        signatureChecker.update(text);

        return signatureChecker.verify(signedText);
    }
}
