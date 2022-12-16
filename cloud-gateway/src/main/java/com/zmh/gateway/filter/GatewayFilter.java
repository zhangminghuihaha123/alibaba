package com.zmh.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.zmh.gateway.filter.off.OffFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Configuration
public class GatewayFilter implements GlobalFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取token信息，如果没有响应失败
        String token = exchange.getRequest().getHeaders().getFirst("token");

        //获取url
        String URL=exchange.getRequest().getURI().toString();

        /*如果是特殊请求放行*/
        for(String url : OffFilter.ListURL()){
            if (URL.equals(url)){
                return chain.filter(exchange);
            }
        }

        if(StrUtil.isBlank(token)){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.BAD_GATEWAY);
            String message= "token is null";
            DataBuffer buffer = response.bufferFactory().wrap(message.getBytes());
            return response.writeWith(Mono.just(buffer));
        }

        //token不为空放行
        return chain.filter(exchange);
    }
}
