package hotP2B.WageGainTools.android.utils;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;


import javax.crypto.Cipher;
import android.annotation.SuppressLint;
import android.util.Base64;

@SuppressLint("TrulyRandom")
public class RsaHelper {
	/**
	 * 生成RSA密钥对(默认密钥长度为1024)
	 * 
	 * @return
	 */
	public static KeyPair generateRSAKeyPair() {
		return generateRSAKeyPair(1024);
	}

	/**
	 * 生成RSA密钥对
	 * 
	 * @param keyLength
	 *            密钥长度，范围：512～2048
	 * @return
	 */
	public static KeyPair generateRSAKeyPair(int keyLength) {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(keyLength);
			return kpg.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	/*
	 * java端公钥转换成C#公钥
	 * 
	 * */
	public static String encodePublicKeyToXml(PublicKey key) {
		if (!RSAPublicKey.class.isInstance(key)) {
			return null;
		}
		RSAPublicKey pubKey = (RSAPublicKey) key;
		StringBuilder sb = new StringBuilder();

		sb.append("<RSAKeyValue>");
		sb.append("<Modulus>").append(
				Base64.encode(pubKey.getModulus().toByteArray(),Base64.DEFAULT)).append(
				"</Modulus>");
		sb.append("<Exponent>").append(
				Base64.encode(pubKey.getPublicExponent().toByteArray(),Base64.DEFAULT))
				.append("</Exponent>");
		sb.append("</RSAKeyValue>");
		return sb.toString();
	}

	/*
	 * C#端公钥转换成java公钥
	 * 
	 * */
	public static PublicKey decodePublicKeyFromXml(String xml) {
		xml = xml.replaceAll("\r", "").replaceAll("\n", "");
		BigInteger modulus = new BigInteger(1, Base64.decode(StringUtils
				.getMiddleString(xml, "<Modulus>", "</Modulus>"),Base64.DEFAULT));
		BigInteger publicExponent = new BigInteger(1, Base64
				.decode(StringUtils.getMiddleString(xml, "<Exponent>",
						"</Exponent>"),Base64.DEFAULT));

		RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus,
				publicExponent);

		KeyFactory keyf;
		try {
			keyf = KeyFactory.getInstance("RSA");
			return keyf.generatePublic(rsaPubKey);
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * C#端私钥转换成java私钥
	 * 
	 * */
	public static PrivateKey decodePrivateKeyFromXml(String xml) {
		xml = xml.replaceAll("\r", "").replaceAll("\n", "");
		BigInteger modulus = new BigInteger(1, Base64.decode(StringUtils
				.getMiddleString(xml, "<Modulus>", "</Modulus>"),Base64.DEFAULT));
		BigInteger publicExponent = new BigInteger(1, Base64
				.decode(StringUtils.getMiddleString(xml, "<Exponent>",
						"</Exponent>"),Base64.DEFAULT));
		BigInteger privateExponent = new BigInteger(1, Base64
				.decode(StringUtils.getMiddleString(xml, "<D>", "</D>"),Base64.DEFAULT)); 
		BigInteger primeP = new BigInteger(1, Base64.decode(StringUtils
				.getMiddleString(xml, "<P>", "</P>"),Base64.DEFAULT));
		BigInteger primeQ = new BigInteger(1, Base64.decode(StringUtils
				.getMiddleString(xml, "<Q>", "</Q>"),Base64.DEFAULT));
		BigInteger primeExponentP = new BigInteger(1, Base64
				.decode(StringUtils.getMiddleString(xml, "<DP>", "</DP>"),Base64.DEFAULT));
		BigInteger primeExponentQ = new BigInteger(1, Base64
				.decode(StringUtils.getMiddleString(xml, "<DQ>", "</DQ>"),Base64.DEFAULT));
		BigInteger crtCoefficient = new BigInteger(1, Base64
				.decode(StringUtils.getMiddleString(xml, "<InverseQ>",
						"</InverseQ>"),Base64.DEFAULT));

		RSAPrivateCrtKeySpec rsaPriKey = new RSAPrivateCrtKeySpec(modulus,
				publicExponent, privateExponent, primeP, primeQ,
				primeExponentP, primeExponentQ, crtCoefficient);

		KeyFactory keyf;
		try {
			keyf = KeyFactory.getInstance("RSA");
			return keyf.generatePrivate(rsaPriKey);
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * java端私钥转换成C#私钥
	 * 
	 * */
	public static String encodePrivateKeyToXml(PrivateKey key) {
		if (!RSAPrivateCrtKey.class.isInstance(key)) {
			return null;
		}
		RSAPrivateCrtKey priKey = (RSAPrivateCrtKey) key;
		StringBuilder sb = new StringBuilder();

		sb.append("<RSAKeyValue>");
		sb.append("<Modulus>").append(
				Base64.encode(priKey.getModulus().toByteArray(),Base64.DEFAULT)).append(
				"</Modulus>");
		sb.append("<Exponent>").append(
				Base64.encode(priKey.getPublicExponent().toByteArray(),Base64.DEFAULT))
				.append("</Exponent>");
		sb.append("<P>").append(
				Base64.encode(priKey.getPrimeP().toByteArray(),Base64.DEFAULT)).append(
				"</P>");
		sb.append("<Q>").append(
				Base64.encode(priKey.getPrimeQ().toByteArray(),Base64.DEFAULT)).append(
				"</Q>");
		sb.append("<DP>").append(
				Base64.encode(priKey.getPrimeExponentP().toByteArray(),Base64.DEFAULT))
				.append("</DP>");
		sb.append("<DQ>").append(
				Base64.encode(priKey.getPrimeExponentQ().toByteArray(),Base64.DEFAULT))
				.append("</DQ>");
		sb.append("<InverseQ>").append(
				Base64.encode(priKey.getCrtCoefficient().toByteArray(),Base64.DEFAULT))
				.append("</InverseQ>");
		sb.append("<D>").append(
				Base64.encode(priKey.getPrivateExponent().toByteArray(),Base64.DEFAULT))
				.append("</D>");
		sb.append("</RSAKeyValue>");
		return sb.toString();
	}

	// 用公钥加密
	public static byte[] encryptData(byte[] data, PublicKey pubKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			return null;
		}
	}

	// 用私钥解密
	public static byte[] decryptData(byte[] encryptedData, PrivateKey priKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			return cipher.doFinal(encryptedData);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 根据指定公钥进行明文加密
	 * 
	 * @param plainText
	 *            要加密的明文数据
	 * @param pubKey
	 *            公钥
	 * @return
	 */
	public static String encryptDataFromStr(String plainText, PublicKey pubKey) {
		
		try {
	        byte[] dataByteArray = plainText.getBytes("utf-8");
	        byte[] encryptedDataByteArray = RsaHelper.encryptData(
	                dataByteArray, pubKey);	  
			return new String(Base64.encode(encryptedDataByteArray,Base64.DEFAULT));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 根据指定私钥对数据进行签名(默认签名算法为"SHA1withRSA")
	 * 
	 * @param data
	 *            要签名的数据
	 * @param priKey
	 *            私钥
	 * @return
	 */
	public static byte[] signData(byte[] data, PrivateKey priKey) {
		return signData(data, priKey, "SHA1withRSA");
	}

	/**
	 * 根据指定私钥和算法对数据进行签名
	 * 
	 * @param data
	 *            要签名的数据
	 * @param priKey
	 *            私钥
	 * @param algorithm
	 *            签名算法
	 * @return
	 */
	public static byte[] signData(byte[] data, PrivateKey priKey,
			String algorithm) {
		try {
			Signature signature = Signature.getInstance(algorithm);
			signature.initSign(priKey);
			signature.update(data);
			return signature.sign();
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 用指定的公钥进行签名验证(默认签名算法为"SHA1withRSA")
	 * 
	 * @param data
	 *            数据
	 * @param sign
	 *            签名结果
	 * @param pubKey
	 *            公钥
	 * @return
	 */
	public static boolean verifySign(byte[] data, byte[] sign, PublicKey pubKey) {
		return verifySign(data, sign, pubKey, "SHA1withRSA");
	}

	/**
	 * 
	 * @param data
	 *            数据
	 * @param sign
	 *            签名结果
	 * @param pubKey
	 *            公钥
	 * @param algorithm
	 *            签名算法
	 * @return
	 */
	public static boolean verifySign(byte[] data, byte[] sign,
			PublicKey pubKey, String algorithm) {
		try {
			Signature signature = Signature.getInstance(algorithm);
			signature.initVerify(pubKey);
			signature.update(data);
			return signature.verify(sign);
		} catch (Exception ex) {
			return false;
		}
	}
}
