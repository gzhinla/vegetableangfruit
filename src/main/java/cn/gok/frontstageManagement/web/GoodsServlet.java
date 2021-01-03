package cn.gok.frontstageManagement.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gok.frontstageManagement.entity.Goods;
import cn.gok.frontstageManagement.entity.PageBean;
import cn.gok.frontstageManagement.service.GoodsService;

@WebServlet("/frontstage_goodsServlet")
public class GoodsServlet extends HttpServlet{
	GoodsService service = new GoodsService();
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("findAllGoods".equals(action)) {
			findAllGoods(req, resp);
		} else if ("findGoodsById".equals(action)) {
			findGoodsById(req, resp);
		} else if ("findGoodsByType".equals(action)) {
			findGoodsByType(req, resp);
		}
	}
	
	
	/**
	 * 获取所有商品列表
	 * @param req
	 * @param resp
	 */
	public void findAllGoods(HttpServletRequest req, HttpServletResponse resp) {
		Map<String, List<Goods>> allGoods = service.getAllGoods();
		
		try {
			req.setAttribute("allGoods", allGoods);
			req.getRequestDispatcher("/main.jsp").forward(req, resp);
			return;
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *根据类型获得商品列表 
	 * @param req
	 * @param resp
	 */
	public void findGoodsByType(HttpServletRequest req, HttpServletResponse resp) {
		String type = req.getParameter("type");
		String currentPage = req.getParameter("currentPage");
		
		PageBean pageBean = null;
		
		// 如果当前第几页currentPage 值为null，说明第一次跳转到此页面或者是要跳转到首页，则设定该值currentPage默认为1
		if(currentPage == null) {
			pageBean = service.getGoodsByType(type, 4, 1);
		}else {
			
			pageBean = service.getGoodsByType(type, 4, Integer.parseInt(currentPage));
		}
		
		try {
			req.setAttribute("pageBean", pageBean);
			req.setAttribute("type", type);
			
			req.getRequestDispatcher("/product.jsp").forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取商品的详细信息
	 * @param req
	 * @param resp
	 */
	public void findGoodsById(HttpServletRequest req, HttpServletResponse resp) {
		int id =Integer.parseInt(req.getParameter("id"));
		
		Goods goodsInfo = service.getGoodsInfoById(id);
		
		try {
			req.setAttribute("pro", goodsInfo);
			req.getRequestDispatcher("/productInfo_user.jsp").forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
