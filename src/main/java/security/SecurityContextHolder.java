package security;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SecurityContextHolder {
	public static List<Authentication> authentications = new ArrayList<>();
	
	// 로그인 됐을 때 인증 객체 추가
	public static void addAuth(Authentication authentication) {
		authentications.add(authentication);
	}
	
	// 인증 객체 확인
	public static Boolean isAuthenticated(String token) {
		for(Authentication authentication : authentications) {
			if(Objects.equals(authentication.getToken(), token)) {
				return true;
			}
		}
		return false;
	}
	
	// 토큰으로 인증 객체 가져오는 메소드
	public static Authentication findAuthenticationByToken(String token) {
		for(Authentication authentication : authentications) {
			if(Objects.equals(authentication.getToken(), token)) {
				return authentication;
			}
		}
		return null;
	}
	
	// 로그아웃 됐을 때 인증 객체 삭제
	public static void removeAuth(String token) {
		for(Authentication authentication : authentications) {
			if(Objects.equals(authentication.getToken(), token)) {
				authentications.remove(authentication);
				break;
			}
		}
	}
}
