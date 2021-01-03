package cn.gok.backstageManagement.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.gok.backstageManagement.entity.User;
import cn.gok.backstageManagement.service.UserService;
import cn.gok.util.JSONResult;
import cn.gok.util.JsonUtil;

@WebServlet("/backstage_userServlet")
public class UserServlet extends HttpServlet{
	UserService service = new UserService();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("login".equals(action)) {
			login(req, resp);
		} else if ("modifyPassword".equals(action)) {
			modifyPassword(req, resp);
		} else if ("checkOldPassword".equals(action)) {
			checkOldPassword(req, resp);
		} else if ("afterModifyPassword".equals(action)) {
			afterModifyPassword(req, resp);
		} else if ("logout".equals(action)) {
			logout(req, resp);
		}
	}
	
	/**
	 * �û���¼
	 * @throws IOException 
	 */
	public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//������������ʽ:
				req.setCharacterEncoding("utf-8");
				//������Ӧ�����ʽ			
				resp.setContentType("text/html;charset=utf-8");
		//��ȡǰ��ҳ�洫�����û���
		String userName = req.getParameter("userName");
		//��ȡǰ��ҳ�洫��������
		String password = req.getParameter("password");
		// 1������֤���û��Ľ�ɫ�Ƿ�Ϊ����Ա
		String userRole = service.getUserRoleByName(userName);
		try {
			if(!"admin".equals(userRole)) {
				JSONResult errorMsg = JSONResult.errorMsg("���޵�¼Ȩ��");
				resp.getWriter().println(JsonUtil.javaObjectToJson(errorMsg));
			}else {
				//2����ȡ�û����� �����û�����������֤
				User user = service.getUserByNameAndPassword(userName, password);
				if(user!=null) {
					//���û����ݱ��浽session �������У������ں����Ĳ�����ʹ���û�����
					req.getSession().setAttribute("user", user);
					JSONResult ok = JSONResult.ok();
					resp.getWriter().println(JsonUtil.javaObjectToJson(ok));
				}else {
					// ��������Ϣ��װ�ڽ������
					JSONResult result = JSONResult.errorMsg("�û������������������");
					//��json����ʽ���ظ�ǰ��
					resp.getWriter().println(JsonUtil.javaObjectToJson(result));
				}
			}
		} catch (Exception e) {
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
		User user =(User) req.getSession().getAttribute("user");
		String oldPassword = service.getPasswordById(user.getId()); 
		String password = req.getParameter("password");
		try {
			if(!oldPassword.equals(password)) {
				JSONResult errorMsg = JSONResult.errorMsg("ԭʼ�����������������");
				resp.getWriter().println(JsonUtil.javaObjectToJson(errorMsg));
			}else {
				JSONResult ok = JSONResult.ok();
				resp.getWriter().println(JsonUtil.javaObjectToJson(ok));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 *  �޸�����
	 * @throws IOException 
	 */
	public void modifyPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//������������ʽ:
				req.setCharacterEncoding("utf-8");
				//������Ӧ�����ʽ			
				resp.setContentType("text/html;charset=utf-8");
		
		String password = req.getParameter("newPassword");
		User user =(User) req.getSession().getAttribute("user");
		int id = user.getId();
		
		int d = service.updatePasswordById(id, password);
		
		try {
			if(d>0) {
				JSONResult ok = JSONResult.ok();
				resp.getWriter().println(JsonUtil.javaObjectToJson(ok));
			}else {
				JSONResult errorMsg = JSONResult.errorMsg("�޸�ʧ�ܣ�������");
				resp.getWriter().println(JsonUtil.javaObjectToJson(errorMsg));
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �ɹ��޸���������session�е����ݣ�����������ת����¼ҳ
	 * @param req
	 * @param resp
	 */
    public void afterModifyPassword(HttpServletRequest req, HttpServletResponse resp) {
    	req.getSession().removeAttribute("user");
		try {
			//ʹ��iframeʱ��Ҫ��������ֱ����ת�ķ�ʽ
			// ��ʱֻ������Ƕ��iframe��ת����¼ҳ����������ҳ��
//			resp.sendRedirect("backstage/login.jsp");
			
			PrintWriter writer = resp.getWriter();
			writer.print("<html>");
			writer.print("<script>");
			writer.print("window.open('"+ req.getContextPath()+"/backstage/login.jsp','_top')");
			writer.print("</script>");
			writer.print("</html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	

	/**
	 * �˳���¼
	 */
    public void logout(HttpServletRequest req, HttpServletResponse resp) {
		req.getSession().removeAttribute("user");
		try {
			resp.sendRedirect("backstage/login.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
