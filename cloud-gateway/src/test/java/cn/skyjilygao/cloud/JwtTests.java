package cn.skyjilygao.cloud;

import cn.skyjilygao.cloud.common.TokenGenerator;
import com.powerwin.adorado.util.TxtUtil;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * @author skyjilygao
 * @date 2022-01-13 17:48
 **/
public class JwtTests {
    private static String ALGORITHM_RSA = "RSA";
    /**
     * 私钥路径：用于解密
     */
    private static final String PRIVATE_KEY_PATH = "files/rsa_key/private_key.pem";

    private static final File PUBLIC_KEY_FILE = new File("files/rsa_key/rsa_public_key.pem");
    /**
     * 公钥文件路径
     * <br> 临时测试，正式使用时有正式key
     */
    private final static String PUBLIC_KEY_STR = TxtUtil.txt2String(PUBLIC_KEY_FILE);
    private final static String PRIVATE_KEY_STR = TxtUtil.txt2String(new File(PRIVATE_KEY_PATH));

    public static void main(String[] args) throws Exception {
        TokenGenerator tokenGenerator = new TokenGenerator.Builder().withUserId(1L).withUserName("sky").build();
        String s = tokenGenerator.generateToken();
        System.out.println(s);
        String ss = s;
        TokenGenerator.deleteToken(ss);
        Map<String, Object> map = TokenGenerator.parseToken(ss);
/*
        String algorithm = SignatureAlgorithm.PS384.getFamilyName();
//        PublicKey publicKey = loadPublicKey();

        String jwt = JjwtUtil.createJWT("aa", "wee", "ddd");
        System.out.println(jwt);
        Claims claims = JjwtUtil.parseJWT(jwt);
        System.out.println(claims);
*/
        /*SecretKey privateKey = generalKey();
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.RS384);
        LocalDateTime now = LocalDateTime.now();
        now = now.plusMinutes(10);
        Date expiration = Date.from(now.toInstant(ZoneOffset.UTC));
        DefaultClaims defaultClaims = new DefaultClaims();
        JwtBuilder joe = Jwts.builder().setClaims(defaultClaims);
        String jws = joe.signWith(privateKey, SignatureAlgorithm.HS256).setExpiration(expiration).compact();
        System.out.println(jws);*/

        /*Claims body = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(jws).getBody();
        System.out.println(body.getSubject());*/
    }

    public static PublicKey loadPublicKey() throws Exception {
        System.out.println(PUBLIC_KEY_FILE.getAbsolutePath());
        byte[] buffer = Base64.decodeBase64(PUBLIC_KEY_STR);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey loadPrivateKey() throws Exception {
        byte[] buffer = Base64.decodeBase64(PRIVATE_KEY_STR);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        return keyFactory.generatePrivate(keySpec);
    }

    // 由字符串生成加密key
    public static SecretKey generalKey() {
        // 本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(PRIVATE_KEY_STR);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
}
