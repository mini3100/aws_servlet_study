package servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.UserData;
import entity.User;
import utils.JsonParseUtil;
import utils.ResponseUtil;

@WebServlet("/auth/signin")
public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> signinUser = JsonParseUtil.toMap(request.getInputStream());
		Boolean responseData = false;

		for (User user : UserData.userList) {
			if (Objects.equals(user.getUsername(), signinUser.get("username"))
					&& Objects.equals(user.getPassword(), signinUser.get("password"))) { // 로그인 할 때 username, password가
																							// 일치하면 true
				responseData = true;
				break;
			}
		}

		ResponseUtil.response(response).of(200).body(responseData);
	}

}
