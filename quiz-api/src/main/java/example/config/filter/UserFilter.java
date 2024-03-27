package example.config.filter;

import example.config.JwtProvider;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
@WebFilter(urlPatterns = "/user/quiz/*")
@RequiredArgsConstructor
public class UserFilter implements Filter {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String token = req.getHeader("X-AUTH-TOKEN");

        //1. 토큰이 유효한지
        if(!jwtProvider.checkValidToken(token)) {
            throw new RuntimeException("토큰이 만료되었습니다.");
        }
        //2. USER정보값을 가져오는지
        //2-1 userId, userType

        //3. 가져온다면 UserType이 user가 맞는지

        //4. 통과되면 통과
    }
}
