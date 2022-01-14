package cn.skyjilygao.cloud;

import cn.skyjilygao.cloud.enums.ErrorCode;
import cn.skyjilygao.cloud.utils.JjwtUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.powerwin.adorado.common.ReturnT;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author skyjilygao
 * @date 2022-01-14 12:29
 **/
@Slf4j
@Component
@Order(0)
public class TokenFilter implements GlobalFilter, Ordered {
    /**
     * Process the Web request and (optionally) delegate to the next {@code WebFilter}
     * through the given {@link GatewayFilterChain}.
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String t = request.getHeaders().getFirst("t");
        log.info("t====={}",t);
        ServerHttpResponse response = exchange.getResponse();
        try {
            Claims claims = JjwtUtil.parseJWT(t);
            Object userId = claims.getOrDefault("userId", 0);
            // todo: 需要根据userId读redis，判断token是否有效（针对主动设置token无效情况，需要验证）。
        } catch (Exception e) {
            log.error("parse token[{}] error={}", t, e.getMessage());
            ReturnT r = new ReturnT(ErrorCode.AUTH_TOKEN_ERR.getCode(), ErrorCode.AUTH_TOKEN_ERR.getMsg());
            response.setRawStatusCode(2001);
            JSONObject o = (JSONObject) JSON.toJSON(r);
            String s = o.toJSONString();
            byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return response.writeWith(Flux.just(buffer));
        }
        return chain.filter(exchange);
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
