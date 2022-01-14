package cn.skyjilygao.cloud.utils;
 
import com.powerwin.adorado.util.TxtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 利用JWT生成
 * @author skyjilygao
 * @date 20220114
 */
public class JjwtUtil {
	private static final String PRIVATE_KEY_PATH = "files/rsa_key/private_key.pem";

	private static final File PUBLIC_KEY_FILE = new File("files/rsa_key/rsa_public_key.pem");
	/**
	 * 公钥文件路径
	 * <br> 临时测试，正式使用时有正式key
	 */
	private final static String PUBLIC_KEY_STR = TxtUtil.txt2String(PUBLIC_KEY_FILE);
	private final static String PRIVATE_KEY_STR = TxtUtil.txt2String(new File(PRIVATE_KEY_PATH));


	/**
	 * 私钥
	 */
	public static final String JWT_SECRET = PRIVATE_KEY_STR;
 
	// 过期时间，单位毫秒
	public static final int EXPIRE_TIME = 60 * 60 * 1000; // 一个小时
//	public static final long EXPIRE_TIME = 7 * 24 * 3600 * 1000; // 一个星期

	/**
	 * 对称加密：SecretKey
	 * @return SecretKey
	 */
	public static SecretKey generalKey() {
		// 本地的密码解码
		byte[] encodedKey = Base64.decodeBase64(JWT_SECRET);
		// 根据给定的字节数组使用AES加密算法构造一个密钥
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, SignatureAlgorithm.HS512.getJcaName());
		return key;
	}

	/**
	 * 创建token
	 * @param issuer
	 * @param audience
	 * @param subject
	 * @return
	 * @throws Exception
	 */
	public static String createJWT(Map<String, Object> claims, String issuer, String audience, String subject) throws Exception {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

		String jwtId = UUID.randomUUID().toString();;
		LocalDateTime now = LocalDateTime.now();
		// 签发时间
		Date issuedAt = Date.from(now.toInstant(ZoneOffset.UTC));
		// 第一次签发，过期时间30分钟
		LocalDateTime expirate = now.plusMinutes(30);
		Date expirad = Date.from(expirate.toInstant(ZoneOffset.UTC));
		// 密钥
		SecretKey key = generalKey();
		// 为payload添加各种标准声明和私有声明
		JwtBuilder builder = Jwts.builder()
				.setClaims(claims)
				.setId(jwtId)
				// jwt的签发时间
				.setIssuedAt(issuedAt)
				// 签发者
				.setIssuer(issuer)
				.setAudience(StringUtils.isBlank(audience) ? "unknow" : audience)
				// sub(subject)：jwt所面向的用户，放登录的用户名，一个json格式的字符串，可存放userid，roldid之类，作为用户的唯一标志
				.setSubject(subject)
				.setExpiration(expirad)
				.signWith(key, signatureAlgorithm);

		return builder.compact();
	}

	/**
	 * 解密
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception {
		SecretKey key = generalKey();
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(jwt).getBody();

		return claims;
	}

	/**
	 * 刷新token，获取长期token
	 * @param jwt 密文
	 * @return 长期token
	 */
	public static String refreshJWT(String jwt) {
		SecretKey key = generalKey();

		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(jwt)
				.getBody();
		LocalDateTime now = LocalDateTime.now();
		// 签发时间
		Date issuedAt = Date.from(now.toInstant(ZoneOffset.UTC));
		// 续签，60天
		LocalDateTime expirate = now.plusDays(60);
		Date expirad = Date.from(expirate.toInstant(ZoneOffset.UTC));
		return Jwts.builder().setClaims(claims).setExpiration(expirad).setIssuedAt(issuedAt).compact();
	}
	public static String deleteJWT(String jwt) {
		SecretKey key = generalKey();

		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(jwt)
				.getBody();
		LocalDateTime now = LocalDateTime.now();
		// 签发时间
		Date issuedAt = Date.from(now.toInstant(ZoneOffset.UTC));
		// 设置过期
		LocalDateTime expirate = now.minusSeconds(1);
		Date expirad = Date.from(expirate.toInstant(ZoneOffset.UTC));
		return Jwts.builder().setClaims(claims).setExpiration(expirad).setIssuedAt(issuedAt).compact();
	}
 
}
