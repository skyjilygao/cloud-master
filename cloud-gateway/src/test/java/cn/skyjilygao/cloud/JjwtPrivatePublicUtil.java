package cn.skyjilygao.cloud;

import com.powerwin.adorado.util.TxtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// 使用io.jsonwebtoken包
public class JjwtPrivatePublicUtil {
	private static final File PRIVATE_KEY_FILE = new File("files/rsa_key/private_key.pem");

	private static final File PUBLIC_KEY_FILE = new File("files/rsa_key/rsa_public_key.pem");
	/**
	 * 公钥文件路径
	 * <br> 临时测试，正式使用时有正式key
	 */
	private final static String PUBLIC_KEY_STR = TxtUtil.txt2String(PUBLIC_KEY_FILE);
	private final static String PRIVATE_KEY_STR = TxtUtil.txt2String(PRIVATE_KEY_FILE);
	private static String ALGORITHM_RSA = "RSA";
 
	// jti：jwt的唯一身份标识
	public static final String JWT_ID = UUID.randomUUID().toString();
 
	// 加密密文，私钥
	public static final String JWT_SECRET = PRIVATE_KEY_STR;
 
	// 过期时间，单位毫秒
	public static final int EXPIRE_TIME = 60 * 60 * 1000; // 一个小时
//	public static final long EXPIRE_TIME = 7 * 24 * 3600 * 1000; // 一个星期
 
	// 由字符串生成加密key
	public static SecretKey generalKey() {
		// 本地的密码解码
		byte[] encodedKey = Base64.decodeBase64(JWT_SECRET);
		// 根据给定的字节数组使用AES加密算法构造一个密钥
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, SignatureAlgorithm.RS256.getJcaName());
		return key;
	}
	public static PrivateKey generalPrivateKey() throws IOException, NoSuchAlgorithmException, ClassNotFoundException, InvalidKeySpecException {
		byte[] buffer = Base64.decodeBase64(PRIVATE_KEY_STR);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		return keyFactory.generatePrivate(keySpec);

		/*KeyFactory factory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey;
		try(ObjectInputStream in =
					new ObjectInputStream(Files.newInputStream(PRIVATE_KEY_FILE.toPath())))
		{
			privateKey = factory.generatePrivate(new RSAPrivateKeySpec(
					(BigInteger)in.readObject(), (BigInteger)in.readObject()));
		}
		return privateKey;*/

		/*// 本地的密码解码
		byte[] encodedKey = Base64.decodeBase64(PRIVATE_KEY_STR);
		// 根据给定的字节数组使用AES加密算法构造一个密钥
		RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec();
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, SignatureAlgorithm.HS512.getJcaName());
		KeyFactory.getInstance()
		return privateKeySpec;*/
	}
	public static PublicKey generalPubliceKey() throws IOException, NoSuchAlgorithmException, ClassNotFoundException, InvalidKeySpecException {
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PublicKey publicKey;
		try(ObjectInputStream in =
					new ObjectInputStream(Files.newInputStream(PUBLIC_KEY_FILE.toPath())))
		{
			publicKey = factory.generatePublic(new RSAPublicKeySpec((BigInteger) in.readObject(), (BigInteger) in.readObject()));
		}
		return publicKey;

		/*// 本地的密码解码
		byte[] encodedKey = Base64.decodeBase64(PRIVATE_KEY_STR);
		// 根据给定的字节数组使用AES加密算法构造一个密钥
		RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec();
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, SignatureAlgorithm.HS512.getJcaName());
		KeyFactory.getInstance()
		return privateKeySpec;*/
	}


 
	// 创建jwt
	public static String createJWT(String issuer, String audience, String subject) throws Exception {
		// 设置头部信息
//		Map<String, Object> header = new HashMap<String, Object>();
//		header.put("typ", "JWT");
//		header.put("alg", "HS256");
		// 或
		// 指定header那部分签名的时候使用的签名算法，jjwt已经将这部分内容封装好了，只有{"alg":"HS256"}
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;
		// 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证的方式）
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", "admin");
		claims.put("password", "010203");
		// jti用户id，例如：20da39f8-b74e-4a9b-9a0f-a39f1f73fe64
		String jwtId = JWT_ID;
		// 生成JWT的时间
		long nowTime = System.currentTimeMillis();
		Date issuedAt = new Date(nowTime);
		// 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露，是你服务端的私钥，在任何场景都不应该流露出去，一旦客户端得知这个secret，那就意味着客户端是可以自我签发jwt的
		PrivateKey key = generalPrivateKey();
		// 为payload添加各种标准声明和私有声明
		JwtBuilder builder = Jwts.builder() // 表示new一个JwtBuilder，设置jwt的body
//				.setHeader(header) // 设置头部信息
				.setClaims(claims) // 如果有私有声明，一定要先设置自己创建的这个私有声明，这是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明
				.setId(jwtId) // jti(JWT ID)：jwt的唯一身份标识，根据业务需要，可以设置为一个不重复的值，主要用来作为一次性token，从而回避重放攻击
				.setIssuedAt(issuedAt) // iat(issuedAt)：jwt的签发时间
				.setIssuer(issuer) // iss(issuer)：jwt签发者
				.setSubject(subject) // sub(subject)：jwt所面向的用户，放登录的用户名，一个json格式的字符串，可存放userid，roldid之类，作为用户的唯一标志
				.signWith(key, signatureAlgorithm);
		// 设置过期时间
		long expTime = EXPIRE_TIME;
		if (expTime >= 0) {
			long exp = nowTime + expTime;
			builder.setExpiration(new Date(exp));
		}
		// 设置jwt接收者
		if (audience == null || "".equals(audience)) {
			builder.setAudience("Tom");
		} else {
			builder.setAudience(audience);
		}
		return builder.compact();
	}
 
	// 解密jwt
	public static Claims parseJWT(String jwt) throws Exception {
		PublicKey key = generalPubliceKey(); // 签名秘钥，和生成的签名的秘钥一模一样
		Claims claims = Jwts.parser() // 得到DefaultJwtParser
				.setSigningKey(key) // 设置签名的秘钥
				.parseClaimsJws(jwt).getBody(); // 设置需要解析的jwt
		return claims;
	}
 
}
