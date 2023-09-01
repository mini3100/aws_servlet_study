package utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {
	
	public static ResponseUtilBuilder response(HttpServletResponse response) {
		return new ResponseUtilBuilder(response);
	}
	
	public static class ResponseUtilBuilder {
		private static HttpServletResponse response;
		
		public ResponseUtilBuilder(HttpServletResponse response) {
			this.response = response;
		}
		
		public ResponseUtilBuilder of(int statusCode) {
			response.setStatus(statusCode);
			return this;
		}
		
		public void body(Object body) throws IOException {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(body);
		}
	}
}
