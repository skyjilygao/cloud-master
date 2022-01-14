package cn.skyjilygao.cloud.common;

import cn.skyjilygao.cloud.enums.ErrorCode;
import cn.skyjilygao.cloud.utils.AssertUtil;
import cn.skyjilygao.cloud.utils.JjwtUtil;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author skyjilygao
 * @date 2022-01-14 15:30
 **/
@Getter
public class TokenGenerator {

    private Long userId;
    private String userName;
    private Set<String> permissions;
    private TokenGenerator(){}
    private TokenGenerator(Long userId, String userName, Set<String> permissions){
        this.userId = userId;
        this.userName = userName;
        this.permissions = permissions;
    }

    public String generateToken() throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("userName", userName);
        claims.put("permissions", permissions);
        return JjwtUtil.createJWT(claims, "pw", userName, "generate_token");
    }
    public static String refreshToken(String token) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        return JjwtUtil.refreshJWT(token);
    }

    public static Map<String, Object> parseToken(String token) throws Exception {
        return JjwtUtil.parseJWT(token);
    }
    public static void deleteToken(String token) throws Exception {
        JjwtUtil.deleteJWT(token);
    }



    public static class Builder {
        private Long userId;
        private String userName;
        private Set<String> permissions = new LinkedHashSet<>();

        public Builder withUserId(Long userId){
            this.userId = userId;
            return this;
        }
        public Builder withUserName(String userName){
            this.userName = userName;
            return this;
        }

        public Builder withPermissions(Set<String> permissions){
            if(CollectionUtils.isNotEmpty(permissions)){
                this.permissions = new LinkedHashSet<>(permissions);
            }
            return this;
        }
        public Builder appendPermissions(Set<String> permissions){
            if(CollectionUtils.isNotEmpty(permissions)){
                this.permissions.addAll(permissions);
            }
            return this;
        }
        public Builder appendPermission(String permission){
            if(StringUtils.isNotBlank(permission)){
                this.permissions.add(permission);
            }
            return this;
        }

        public TokenGenerator build(){
            AssertUtil.notNull(userId, ErrorCode.NOT_NULL);
            AssertUtil.notNull(userName, ErrorCode.NOT_NULL);
            return new TokenGenerator(userId, userName, permissions);
        }
    }
}
