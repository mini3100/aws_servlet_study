package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.UserData;
import entity.User;
import security.Authentication;
import security.SecurityContextHolder;
import utils.JsonParseUtil;
import utils.ResponseUtil;

@WebServlet("/mypage/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getHeader("Authorization");	// 헤더의 Authorization의 키를 가진 값 : 토큰
		
		User user = SecurityContextHolder.findAuthenticationByToken(token).getUser();	// 해당 토큰의 인증 객체를 찾아와 그 안의 User를 가져옴
		
		ResponseUtil.response(response).of(200).body(JsonParseUtil.toJson(user));	//user 객체를 응답으로 보낸다
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> profileMap = JsonParseUtil.toMap(request.getInputStream());
		
		Authentication authentication = SecurityContextHolder.findAuthenticationByToken(request.getHeader("Authorization"));
		User oldUser = authentication.getUser();
		
		List<User> userList = UserData.userList;
		
		User user = User.builder()
				.userId(oldUser.getUserId())
				.username((String) profileMap.get("username"))
				.password((String) profileMap.get("password"))
				.name((String) profileMap.get("name"))
				.email((String) profileMap.get("email"))
				.build();
		
		for(int i = 0; i < userList.size(); i++) {
			if(userList.get(i).getUserId() == user.getUserId()) {
				userList.set(i, user);			// 로그인된 회원의 정보 수정
				authentication.setUser(user); 	// 회원가입된 회원의 정보 수정
				ResponseUtil.response(response).of(200).body(true);
			}
		}
		
		ResponseUtil.response(response).of(200).body(false);
	}
}








