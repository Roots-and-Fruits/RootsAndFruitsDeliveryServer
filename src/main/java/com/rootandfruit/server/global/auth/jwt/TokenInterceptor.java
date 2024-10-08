package com.rootandfruit.server.global.auth.jwt;

import com.rootandfruit.server.global.exception.CustomException;
import com.rootandfruit.server.global.exception.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 요청 헤더에서 Authorization 헤더 가져오기
        String authorizationHeader = request.getHeader("Authorization");

        // 헤더가 없거나 Bearer 토큰이 없는 경우 401 반환
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new CustomException(ErrorType.INVALID_MISSING_HEADER_ERROR);
        }

        String token = authorizationHeader.replace("Bearer ", "");

        // 토큰 유효성 검증
        if (!jwtUtil.isAdmin(token)) {
            throw new CustomException(ErrorType.JWT_AUTHORIZATION_FAILED);
        } else {
            return true;
        }
    }
}
