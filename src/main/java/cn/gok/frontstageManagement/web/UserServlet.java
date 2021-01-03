package cn.gok.frontstageManagement.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.gok.frontstageManagement.entity.User;
import cn.gok.frontstageManagement.service.UserService;
import cn.gok.util.JSONResult;
import cn.gok.util.JsonUtil;

@WebServlet("/frontstage_userServlet")
public class UserServlet extends HttpServlet {
	UserService service = new UserService();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");

		if ("login".equals(action)) {
			login(req, resp);
		} else if ("checkUserNameRepeat".equals(action)) {
			checkUserNameRepeat(req, resp);
		} else if ("register".equals(action)) {
			register(req, resp);
		} else if ("logout".equals(action)) {
			logout(req, resp);
		} else if ("checkOldPassword".equals(action)) {
			checkOldPassword(req, resp);
		} else if ("modifyPassword".equals(action)) {
			modifyPassword(req, resp);
		}
	}

	public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//������������ʽ:
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ			
		resp.setContentType("text/html;charset=utf-8");
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String captcha = req.getParameter("captcha");
		if (captcha != null) {
			captcha = captcha.toUpperCase();
		}

		// �Ƚ�����֤����֤
		String checkcode = (String) req.getSession().getAttribute("checkcode_session");

		try {
			// ���û��������֤��� ϵͳ��֤�Ա�
			if (checkcode.equals(captcha)) {
				User user = service.getUserByNameAndPassword(userName, password);
				if (user != null) {
					HttpSession session = req.getSession();
					session.setAttribute("user", user);
					JSONResult ok = JSONResult.ok();
					resp.getWriter().println(JsonUtil.javaObjectToJson(ok));
				} else {
					// ��������Ϣ��װ�ڽ������
					JSONResult result = JSONResult.errorMsg("�û������������������");
					// ��json����ʽ���ظ�ǰ��
					resp.getWriter().println(JsonUtil.javaObjectToJson(result));
				}
			} else {
				// ��������Ϣ��װ�ڽ������
				JSONResult result = JSONResult.errorMsg("��֤���������������");
				// ��json����ʽ���ظ�ǰ��
				resp.getWriter().println(JsonUtil.javaObjectToJson(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �û����������
	 * 
	 * @param req
	 * @param resp
	 */
	public void checkUserNameRepeat(HttpServletRequest req, HttpServletResponse resp) {
		String userName = req.getParameter("userName");

		if (userName != null) {
			int d = service.checkUserNameRepeat(userName);
			try {
				if (d > 0) {
					JSONResult error = JSONResult.errorMsg("");
					resp.getWriter().println(JsonUtil.javaObjectToJson(error));
				} else {
					JSONResult ok = JSONResult.ok();
					resp.getWriter().println(JsonUtil.javaObjectToJson(ok));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �û�ע��
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//������������ʽ:
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ			
		resp.setContentType("text/html;charset=utf-8");
		User user = new User();
		user.setUserName(req.getParameter("userName"));
		user.setPassword(req.getParameter("password"));
		user.setEmail(req.getParameter("email"));
		user.setPhone(req.getParameter("phoneNum"));
		user.setRole("ordinaryUser");
		int d = service.addUser(user);
		try {
			if (d > 0) {
				JSONResult ok = JSONResult.ok();
				resp.getWriter().println(JsonUtil.javaObjectToJson(ok));
			} else {
				JSONResult error = JSONResult.errorMsg("ע��ʧ��");
				resp.getWriter().println(JsonUtil.javaObjectToJson(error));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �˳���¼
	 */
	public void logout(HttpServletRequest req, HttpServletResponse resp) {
		req.getSession().removeAttribute("user");
		try {
			resp.sendRedirect("index.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���������Ƿ���ȷ �����޸��������ʱʹ��
	 * @throws IOException 
	 */
	public void checkOldPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//������������ʽ:
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ			
		resp.setContentType("text/html;charset=utf-8");
		User user = (User) req.getSession().getAttribute("user");		
		String oldPassword = service.getPasswordById(user.getId());
		System.out.println("�����룺"+oldPassword);
		String password = req.getParameter("password");
		System.out.println("��������֤��"+password);
		try {
			if (!oldPassword.equals(password)) {
				JSONResult errorMsg = JSONResult.errorMsg("ԭʼ�����������������");
				resp.getWriter().println(JsonUtil.javaObjectToJson(errorMsg));
			} else {
				JSONResult ok = JSONResult.ok();
				resp.getWriter().println(JsonUtil.javaObjectToJson(ok));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * �޸�����
	 * @throws IOException 
	 */
	public void modifyPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//������������ʽ:
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ			
		resp.setContentType("text/html;charset=utf-8");

		String password = req.getParameter("newPassword");
		System.out.println("�����룺"+password);
		User user = (User) req.getSession().getAttribute("user");
		int id = user.getId();
		System.out.println("������id"+id);
		int d = service.updatePasswordById(id, password);
		try {
			if (d > 0) {
				JSONResult ok = JSONResult.ok();
				resp.getWriter().println(JsonUtil.javaObjectToJson(ok));
			} else {
			  JSONResult errorMsg = JSONResult.errorMsg("�޸�ʧ�ܣ�������");
			  resp.getWriter().println(JsonUtil.javaObjectToJson(errorMsg));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
