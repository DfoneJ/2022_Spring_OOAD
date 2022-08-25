package errorProcess;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UploadFileError extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession(true);

		req.setAttribute("hwid", (String) session.getAttribute("hwId"));
		req.setAttribute("errorTitle", session.getAttribute("errorTitle"));
		req.setAttribute("errorContents", session.getAttribute("errorContents"));
		
		req.getRequestDispatcher("/WEB-INF/student/UploadFileError.jsp").forward(req, resp);
	}

}
