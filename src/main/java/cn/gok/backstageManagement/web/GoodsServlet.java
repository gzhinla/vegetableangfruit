package cn.gok.backstageManagement.web;

import java.io.File;
import java.io.IOException;
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

import cn.gok.backstageManagement.entity.Goods;
import cn.gok.backstageManagement.entity.Orders;
import cn.gok.backstageManagement.service.GoodsService;
import cn.gok.frontstageManagement.entity.User;
import cn.gok.util.FileUploadUtil;
import cn.gok.util.JSONResult;
import cn.gok.util.JsonUtil;

@WebServlet("/backstage_goodsServlet")
public class GoodsServlet extends HttpServlet{
	
	GoodsService service = new GoodsService();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");		
		switch(action) {
			case "addGoods":addGoods(req,resp);break;
			case "getGoodsList":getGoodsList(req,resp);break;
			case "getGoodsListByName":getGoodsListByName(req,resp);break;
			case "deleteGoods":deleteGoods(req,resp);break;
			case "toGoodsUpdatePage":toGoodsUpdatePage(req,resp);break;
			case "updateGoods":updateGoods(req,resp);break;
			case "upGoodsImage":upGoodsImage(req,resp);break;
			case "fastbuy":sendOrder(req,resp);break;
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
		Orders order = new Orders();
		order.setNumber(order.getRandomString(10));
		order.setTime(df.format(new Date()));
		order.setName(req.getParameter("recipients"));
		order.setAddress(req.getParameter("address"));
		order.setPhone(req.getParameter("phone"));
		order.setAddress_label(req.getParameter("addressLabel"));
		order.setSex(req.getParameter("sex"));
		
		order.setUser("sjb");
		order.setGoods_id(99);
		order.setGoods_num(3);
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
	 * ������Ʒ
	 * @throws IOException 
	 */
	public void addGoods(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		//������������ʽ:
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ			
		resp.setContentType("text/html;charset=utf-8");
		FileUploadUtil upFile = new FileUploadUtil();
		ServletContext servletContext = req.getSession().getServletContext();
		List<FileItem> items= upFile.getRequsetFileItems(req,servletContext);
		
		//���������û��ύ�� ������ ��map
		Map<String, String>  formData = new HashMap<>();
		
		String savePath = new File(getServletContext().getRealPath("/")).getParentFile().getParentFile().getPath()+"/imgs";
		
		
		try {
			//����
			for (FileItem file : items) {
				
				if(!file.isFormField()) {
					String fileSuffix = FileUploadUtil.getFileSuffix(file).toUpperCase();
					
					//������jpg��png  ͼƬ�ļ����ܽ����ϴ�����
					if("JPG".equals(fileSuffix) || "PNG".equals(fileSuffix)) {
						//������ͼƬ��
						String imgName = FileUploadUtil.getImgNewName(fileSuffix);
						
						FileUploadUtil.saveFile(file, savePath, imgName);
						
						formData.put("imgUrl",imgName);
					}
				}else {	
						//���ļ����ݴ���
						formData.put(file.getFieldName(), file.getString("utf-8"));	
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
		Goods goods = new Goods();
		goods.setName(formData.get("goodsName"));
		goods.setNum(Integer.parseInt(formData.get("num")));
		goods.setPrice(Double.parseDouble(formData.get("price")));
		goods.setImgurl(formData.get("imgUrl"));
		goods.setType(formData.get("type"));
		goods.setDescription(formData.get("description"));
		goods.setColor(formData.get("color"));
		goods.setMemory(formData.get("memory"));
		
		goods.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		int d = service.addGoods(goods);
		
		try {
			if(d>0) {
				JSONResult ok = JSONResult.ok();
				resp.getWriter().println(JsonUtil.javaObjectToJson(ok));
			}else {
				JSONResult errorMsg = JSONResult.errorMsg("���ʧ�ܣ�������");
				resp.getWriter().println(JsonUtil.javaObjectToJson(errorMsg));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ��ǰ��ҳ�淵����Ʒ�����б�
	 */
	public void getGoodsList(HttpServletRequest req,HttpServletResponse resp) {
		List<Goods> allGoods = service.getAllGoods();
		req.setAttribute("goodsList", allGoods);
		try {
			req.getRequestDispatcher("/backstage/tgls/goodsManage/goods_list.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	/**
	 * @throws IOException 
	 * ������Ʒ����ѯ��Ʒ
	 * @param req
	 * @param resp
	 * @throws  
	 */
	public void getGoodsListByName(HttpServletRequest req,HttpServletResponse resp) throws IOException  {
		//������������ʽ:
				req.setCharacterEncoding("utf-8");
				//������Ӧ�����ʽ			
				resp.setContentType("text/html;charset=utf-8");
		String name = req.getParameter("goodsName");
		String type = req.getParameter("type");
		
		Map<String,String> parmas = new HashMap<>();
		parmas.put("name", name);
		parmas.put("type", type);
		
		List<Goods> list = service.getGoodsByName(parmas);
	
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
	 * �޸���Ʒʱ����ȡ����Ʒ��ȫ�����ݣ����������޸�ҳ��
	 */
	public void toGoodsUpdatePage(HttpServletRequest req,HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		Goods goods = service.getGoodsInfoById(id);
		req.setAttribute("goods", goods);
		try {
			req.getRequestDispatcher("/backstage/tgls/goodsManage/goods_update.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	
	
	
	/**
	 *�޸���Ʒ��Ϣʱ��������޸�ͼƬ���Ƚ�ͼƬ�����޸�
	 * @throws IOException 
	 */
	public void upGoodsImage(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		//������������ʽ:
				req.setCharacterEncoding("utf-8");
				//������Ӧ�����ʽ			
				resp.setContentType("text/html;charset=utf-8");
		FileUploadUtil upFile = new FileUploadUtil();
		ServletContext servletContext = req.getSession().getServletContext();
		List<FileItem> items= upFile.getRequsetFileItems(req,servletContext);
		String savePath = new File(getServletContext().getRealPath("/")).getParentFile().getParentFile().getPath()+"/imgs";
		
		String imgName = null;
		
		try {
			//����
			for (FileItem file : items) {
				if(!file.isFormField()) {
					String fileSuffix = FileUploadUtil.getFileSuffix(file);
					//������jpg��png  ͼƬ�ļ����ܽ����ϴ�����
					if("jpg".equals(fileSuffix) || "png".equals(fileSuffix)) {
						//������ͼƬ��
						imgName = FileUploadUtil.getImgNewName(fileSuffix);
						FileUploadUtil.saveFile(file, savePath, imgName);					
					}
				}
			}
			
			int d = service.updateGoodsImgById(imgName, Integer.parseInt(req.getParameter("id")));
			if(d>0) {
				JSONResult ok = JSONResult.ok(imgName);
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
	 *  �޸���Ʒ
	 * @throws IOException 
	 */
	public void updateGoods(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		//������������ʽ:
				req.setCharacterEncoding("utf-8");
				//������Ӧ�����ʽ			
				resp.setContentType("text/html;charset=utf-8");
		Goods goods = new Goods();
		goods.setId(Integer.parseInt(req.getParameter("id")));
		goods.setName(req.getParameter("goodsName"));
		goods.setNum(Integer.parseInt(req.getParameter("num")));
		goods.setPrice(Double.parseDouble(req.getParameter("price")));
		goods.setType(req.getParameter("type"));
		goods.setColor(req.getParameter("color"));
		goods.setMemory(req.getParameter("memory"));
		goods.setDescription(req.getParameter("description"));
				
		int d = service.updateGoodsById(goods);
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
	 * ɾ����Ʒ
	 * @throws IOException 
	 */
	public void deleteGoods(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		//������������ʽ:
				req.setCharacterEncoding("utf-8");
				//������Ӧ�����ʽ			
				resp.setContentType("text/html;charset=utf-8");
		int id = Integer.parseInt(req.getParameter("id"));
		
		int d = service.deleteGoodsById(id);
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
