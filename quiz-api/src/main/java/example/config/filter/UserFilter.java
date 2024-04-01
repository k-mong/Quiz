package example.config.filter;

import example.config.JwtProvider;
import example.domain.common.UserType;
import example.domain.common.UserVo;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
@WebFilter(urlPatterns = "/user/quiz/*")
@RequiredArgsConstructor
public class UserFilter implements Filter {

    private final JwtProvider jwtProvider;

    /**
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     * 스프링 밖에서 url 패턴을 컨트롤러에 가기 전에 필터가 처리함
     * user/quiz 로들어오는 모든것을 필터처리해줌
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;   // HttpServletRequest = 클라이언트가 보낸 HTTP 요청을 처리할 때 사용
        String token = req.getHeader("X-AUTH-TOKEN");   // token 은 HTTP 의 헤더값을 반환한다.

        //1. 토큰이 유효한지
        if(!jwtProvider.checkValidToken(token)) {
            throw new RuntimeException("토큰이 만료되었습니다.");
        }
        //2. USER정보값을 가져오는지
        //2-1 userId, userType
        UserVo userVo = jwtProvider.getUserVo(token);

        //3. 가져온다면 UserType이 user가 맞는지
        if(!userVo.getUserType().equals(UserType.Answer)) {
            throw new RuntimeException("접근 가능한 USER TYPE 이 아닙니다.");
        }

        //4. 통과되면 통과
        // 전처리
        filterChain.doFilter(servletRequest, servletResponse);
        // 후처리
    }
}
