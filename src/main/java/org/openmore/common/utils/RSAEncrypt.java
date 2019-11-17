package org.openmore.common.utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.aliyuncs.utils.IOUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAEncrypt {
	/**
	 * @Fields CHARSET :
	 */
	public static final String CHARSET = "UTF-8";
	/**
	 * @Description:RSA ALGORITHM
	 * @Fields RSA_ALGORITHM :
	 */
	public static final String RSA_ALGORITHM = "RSA";

	public  static void main(String args[]){
		createKeys(512);

	}

	/**
	 * @Title: createKeys
	 * @Description: 产生RSA公钥和私钥
	 * @param keySize
	 * @return
	 */
	public static void createKeys(int keySize){
		//为RSA算法创建一个KeyPairGenerator对象
		KeyPairGenerator kpg;
		try{
			kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
		}catch(NoSuchAlgorithmException e){
			throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
		}

		//初始化KeyPairGenerator对象,密钥长度
		kpg.initialize(keySize);
		//生成密匙对
		KeyPair keyPair = kpg.generateKeyPair();
		//得到公钥
		Key publicKey = keyPair.getPublic();
		//String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
		//得到私钥
		Key privateKey = keyPair.getPrivate();
		String privateKeyStr = new String(Base64.getEncoder().encode(publicKey.getEncoded()));
		String privateKeyStr2 = new String(Base64.getEncoder().encode(privateKey.getEncoded()));
		System.out.println("==>privateKeyStr:"+(privateKeyStr));
		System.out.println("==>privateKeyStr2:"+(privateKeyStr2));
		String pu1 = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALK8yhS7hC2D3lsTIY47PhsmTd0UwrinzGZXqLg9vQQdmrV+iKd/V1WPgLbWoO26ruoHfI/+cgU6pum0Ym8wHWMCAwEAAQ==";
		String pr1 = "MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAsrzKFLuELYPeWxMhjjs+GyZN3RTCuKfMZleouD29BB2atX6Ip39XVY+Attag7bqu6gd8j/5yBTqm6bRibzAdYwIDAQABAkBYpx8sbV3AHZoaXkDUhTnmyXbxYyy53jCZynza9XRdJ2QYfyHLpD+gw2+wosQG2PU3mLVNKyvqetgt6eZLKgPhAiEA8etklFOnXLeoh/Ne6h+39cHNTwe/6IH/2dPHT7urmTkCIQC9I/t5WpWwY/I23nIF1IoeKd2uaEgrDpaHhgZeD6R3ewIhAMaylFXPr7LTljSzi824Z5wOpda3gsQxojcDXrz6Y6LhAiEAmMVijr+rHqFr+AOup6TntrtsMj5K5HRRA8AujnUmC9cCIQDpoEPNRWx+vwsuHgph+8M2rmNQGyovjl9AA05gq/DS7A==";
		//System.out.println("==>publicKey:"+Base64.decodeBase64(pu1));
		String pu2 = new BASE64Encoder().encode(pu1.getBytes());
		String pr2 = new BASE64Encoder().encode(pr1.getBytes());
		System.out.println("==>publicKey2:"+(publicKey));
		//System.out.println("==>privateKey:"+Base64.decodeBase64(pr1));
		System.out.println("==>privateKey2:"+new BASE64Encoder().encode(privateKey.getEncoded()));
		try{
			String data="dada";
			String enStr = publicEncrypt(data, getPublicKey(pu1));
			System.out.println("==>加密:"+ enStr);
			String deStr = privateDecrypt(enStr, getPrivateKey(pr1));
			System.out.println("==>解密:"+ deStr);


			System.out.println("==>base64解密:"+ new String(Base64.getDecoder().decode("ZmFsc2U=")));

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @Title: getPublicKey
	 * @Description: 获取RSA公钥
	 * @Author Administrator
	 * @DateTime 2018年11月19日 下午7:48:10
	 * @param publicKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */


	public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		//通过X509编码的Key指令获得公钥对象
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
		RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
		return key;
	}

	/**
	 * @Title: getPrivateKey
	 * @Description: 获取RSA私钥
	 * @DateTime 2018年11月19日 下午7:47:03
	 * @param privateKey 密钥字符串（经过base64编码）
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		//通过PKCS#8编码的Key指令获得私钥对象
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
		RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
		return key;
	}


	/**
	 * @Title: publicEncrypt
	 * @Description: 公钥加密
	 * @DateTime 2018年11月19日 下午7:49:09
	 * @param data
	 * @param publicKey
	 * @return
	 */
	public static String publicEncrypt(String data, RSAPublicKey publicKey){
		try{
			Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] enStr = rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength());
			return new String(Base64.getEncoder().encode(enStr));
		}catch(Exception e){
			throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
		}
	}

	/**
	 * @Title: privateDecrypt
	 * @Description: 私钥解密
	 * @DateTime 2018年11月19日 下午7:49:36
	 * @param data
	 * @param privateKey
	 * @return
	 */
	public static String privateDecrypt(String data, RSAPrivateKey privateKey){
		try{
			Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);

			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] dataBytes = Base64.getDecoder().decode(data);
			return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, dataBytes, privateKey.getModulus().bitLength()), CHARSET);
		}catch(Exception e){
			throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
		}
	}

	/**
	 * @Title: rsaSplitCodec
	 * @Description: RSA分割code
	 * @DateTime 2018年11月19日 下午7:50:11
	 * @param cipher
	 * @param opmode
	 * @param datas
	 * @param keySize
	 * @return
	 */
	private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize){
		int maxBlock = 0;
		if(opmode == Cipher.DECRYPT_MODE){
			maxBlock = keySize / 8;
		}else{
			maxBlock = keySize / 8 - 11;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] buff;
		int i = 0;
		try{
			while(datas.length > offSet){
				if(datas.length-offSet > maxBlock){
					buff = cipher.doFinal(datas, offSet, maxBlock);
				}else{
					buff = cipher.doFinal(datas, offSet, datas.length-offSet);
				}
				out.write(buff, 0, buff.length);
				i++;
				offSet = i * maxBlock;
			}
		}catch(Exception e){
			throw new RuntimeException("加解密阀值为["+maxBlock+"]的数据时发生异常", e);
		}
		byte[] resultDatas = out.toByteArray();
		IOUtils.closeQuietly(out);
		return resultDatas;
	}

}