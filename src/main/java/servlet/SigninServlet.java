package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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

@WebServlet("/auth/signin")
public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> signinUser = JsonParseUtil.toMap(request.getInputStream());
		Map<String, String> responseData = new HashMap<>();

		for (User user : UserData.userList) {
			if (Objects.equals(user.getUsername(), signinUser.get("username"))
					&& Objects.equals(user.getPassword(), signinUser.get("password"))) {	//로그인 성공
				
				// 로그인 성공시 새로운 토큰을 생성
				String token = UUID.randomUUID().toString();
				SecurityContextHolder.addAuth(new Authentication(user, token));
				responseData.put("token", token);
				
				break;
			}
		}
		
		//로그인 실패
		ResponseUtil.response(response).of(200).body(JsonParseUtil.toJson(responseData));	//응답할 때 json 형태로 넣어줘야 함
	}

}
