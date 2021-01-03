package cn.gok.backstageManagement.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;


import cn.gok.backstageManagement.entity.Orders;
import cn.gok.backstageManagement.service.GoodsService;
import cn.gok.backstageManagement.service.OrdersService;
import cn.gok.frontstageManagement.entity.User;
import cn.gok.util.FileUploadUtil;
import cn.gok.util.JSONResult;
import cn.gok.util.JsonUtil;

@WebServlet("/backstage_ordersServlet")
public class OrdersServlet extends HttpServlet{
	
	OrdersService service = new OrdersService();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		System.out.println(action);
		if ("getOrdersList".equals(action)) {
			getOrdersList(req, resp);
		} else if ("getOrdersListByName".equals(action)) {
			getOrdersListByName(req, resp);
		} else if ("deleteOrders".equals(action)) {
			deleteOrders(req, resp);
		} else if ("deleteAllOrders".equals(action)) {
			deleteAllOrders(req, resp);
		} else if ("toOrdersUpdatePage".equals(action)) {
			toOrdersUpdatePage(req, resp);
		} else if ("updateOrders".equals(action)) {
			updateOrders(req, resp);
		} else if ("fastbuy".equals(action)) {
			sendOrder(req, resp);
		}
	}

	/* 
	 * ɾ��ȫ������ 
	 */
	private void deleteAllOrders(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//������������ʽ:
				req.setCharacterEncoding("utf-8");
				//������Ӧ�����ʽ			
				resp.setContentType("text/html;charset=utf-8");
		// TODO �Զ����ɵķ������
		int d = service.deleteAllOrders();
		try {
			if(d>0) {
				JSONResult ok = JSONResult.ok();
				resp.getWriter().println(JsonUtil.javaObjectToJson(ok));
			}else {
				JSONResult errorMsg = JSONResult.errorMsg("ɾ��ʧ�ܣ�������");
				resp.getWriter().println(JsonUtil.javaObjectToJson(errorMsg));
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ύ����
	 * @throws IOException 
	 */
	public void sendOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//������������ʽ:
				req.setCharacterEncoding("utf-8");
				//������Ӧ�����ʽ			
				resp.setContentType("text/html;charset=utf-8");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
			User user =(User) req.getSession().getAttribute("user");
			Orders order = new Orders();
			order.setNumber(order.getRandomString(10));
			order.setTime(df.format(new Date()));
			order.setName(req.getParameter("recipients"));
			order.setAddress(req.getParameter("address"));
			order.setPhone(req.getParameter("phone"));
			order.setAddress_label(req.getParameter("addressLabel"));
			order.setSex(req.getParameter("sex"));
			order.setUser(user.getUserName());
			order.setGoods_id(Integer.parseInt(req.getParameter("id")));
			order.setGoods_num(Integer.parseInt(req.getParameter("num")));
			order.setGoods_status(1);
			int d = service.addOrder(order);
				try {
					if(d>0) {
						JSONResult ok = JSONResult.ok();
						resp.getWriter().println(JsonUtil.javaObjectToJson(ok));
					}else {
						JSONResult error = JSONResult.errorMsg("�����ύʧ��!");
						resp.getWriter().println(JsonUtil.javaObjectToJson(error));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}	
		
	}

	
	/**
	 * ��ǰ��ҳ�淵�ض��������б�
	 */
	public void getOrdersList(HttpServletRequest req,HttpServletResponse resp) {
		List<Orders> allOrders = service.getAllOrders();
		req.setAttribute("ordersList",allOrders);
		try {
			req.getRequestDispatcher("/backstage/tgls/ordersManage/orders_list.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	/**
	 * ���ݶ�������ѯ��Ʒ(ע�⣡���û��)
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	public void getOrdersListByName(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		//������������ʽ:
				req.setCharacterEncoding("utf-8");
				//������Ӧ�����ʽ			
				resp.setContentType("text/html;charset=utf-8");
		String name = req.getParameter("OrdersUser");
		//String type = req.getParameter("type");
		
		Map<String,String> parmas = new HashMap<String, String>();
		parmas.put("name", name);
		//parmas.put("type", type);
		
		List<Orders> list = service.getOrdersByName(parmas);
	
		try {
			if(list != null) {
				JSONResult ok = JSONResult.ok(list);
				resp.getWriter().println(JsonUtil.javaObjectToJson(ok));
			}else {
				JSONResult errorMsg = JSONResult.errorMsg("δ��ȡ���κ����ݣ�������");
				resp.getWriter().println(JsonUtil.javaObjectToJson(errorMsg));
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * �޸Ķ���ʱ����ȡ�˶�����ȫ�����ݣ����������޸�ҳ��
	 */
	public void toOrdersUpdatePage(HttpServletRequest req,HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		Orders orders = service.getOrdersInfoById(id);
		req.setAttribute("orders", orders);
		try {
			req.getRequestDispatcher("/backstage/tgls/ordersManage/orders_update.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	
	/**
	 *  �޸Ķ���
	 * @throws IOException 
	 */
	
	public void updateOrders(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		//������������ʽ:
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ			
		resp.setContentType("text/html;charset=utf-8");
		Orders orders = new Orders();
		orders.setId(Integer.parseInt(req.getParameter("id")));
		orders.setNumber(req.getParameter("number"));
		orders.setUser(req.getParameter("user"));
		orders.setTime(req.getParameter("time"));
		orders.setName(req.getParameter("name"));
		orders.setSex(req.getParameter("sex"));
		orders.setAddress(req.getParameter("address"));
		orders.setPhone(req.getParameter("phone"));
		orders.setAddress_label(req.getParameter("address_label"));
		orders.setGoods_id(Integer.parseInt(req.getParameter("goods_id")));
		orders.setGoods_num(Integer.parseInt(req.getParameter("goods_num")));
		orders.setGoods_status(Integer.parseInt(req.getParameter("goods_status")));
		
		System.out.println(orders);
		int d = service.updateOrdersById(orders);
		System.out.println(d);
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
	 * ɾ������
	 * @throws UnsupportedEncodingException 
	 */
	public void deleteOrders(HttpServletRequest req,HttpServletResponse resp) throws UnsupportedEncodingException {
		//������������ʽ:
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ			
		resp.setContentType("text/html;charset=utf-8");
		int id = Integer.parseInt(req.getParameter("id"));
		
		int d = service.deleteOrdersById(id);
		try {
			if(d>0) {
				JSONResult ok = JSONResult.ok();
				resp.getWriter().println(JsonUtil.javaObjectToJson(ok));
			}else {
				JSONResult errorMsg = JSONResult.errorMsg("ɾ��ʧ�ܣ�������");
				resp.getWriter().println(JsonUtil.javaObjectToJson(errorMsg));
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
