package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import security.SecurityContextHolder;
import utils.ResponseUtil;

@WebFilter("*")
public class SecurityFilter extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String rootPath = "/servlet_study_ga0";
		String[] antMatchers = {"/auth"};	//인증이 필요없는 path들
		String uri = req.getRequestURI();
		
		// 인증이 필요 없는 경우 : /servlet_study_ga0/auth로 시작하는 경우 -> SigninServelet, SignupServelet, DuplicatedUsername
		// 로그아웃인 상태에서도 로그인에 접근할 수 있어야 함.
		// 로그인 하는 순간에 토큰을 발급하고 로그인 하고 나서부터 마이페이지 장바구니 등에 인증이 필요
		for(String antMatcher : antMatchers) {
			if(uri.startsWith(rootPath + antMatcher)) {
				chain.doFilter(request, response);
				return;
			}
		}
		
		String token = req.getHeader("Authorization");
		
		// 해당 토큰이 인증되지 않은 상태일 때(인증 실패) / option과 get 두 가지가 들어와서 option 메소드 무시해줌
		if(!req.getMethod().equalsIgnoreCase("options") && !SecurityContextHolder.isAuthenticated(token)) {
			ResponseUtil.response(resp).of(401).body("인증 실패");
			return;
		}
		// 인증 성공
		chain.doFilter(request, response);
		
	}

}
