package cn.gok.frontstageManagement.service;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.activation.registries.MailcapFile;
import com.sun.org.apache.regexp.internal.recompile;

import cn.gok.frontstageManagement.entity.Goods;
import cn.gok.frontstageManagement.entity.PageBean;
import cn.gok.util.JDBCUtil;
public class GoodsService {
	
	/**
	 * 获取数据库中每种商品按时间排序的前4个
	 * 将拿到的数据根据商品的类别 存入map中，
	 * 其中map的key为类别，value为该类型商品的list集合
	 * @return
	 */
	public Map<String, List<Goods>> getAllGoods(){
		Connection con  = null;
		PreparedStatement ps = null;
		String sql="select * from t_goods order by g_create_date desc";
		Map<String, List<Goods>> map = new HashMap<String, List<Goods>>();
		List<Goods> slist=new ArrayList<Goods>();
		ResultSet rs=JDBCUtil.executeQuery(sql);
		try{
			while(rs.next()){
				Goods good = new Goods();
				good.setId(rs.getInt("id"));
				good.setName(rs.getString("g_name"));
				good.setPrice(rs.getDouble("g_price"));
				good.setNum(rs.getInt("g_num"));
				good.setColor(rs.getString("g_color"));
				good.setCreateDate(rs.getString("g_create_date"));
				good.setDescription(rs.getString("g_description"));
				good.setImgurl(rs.getString("g_imgurl"));
				good.setMemory(rs.getString("g_memory"));
				good.setType(rs.getString("g_type"));
				slist.add(good);
				map.put(rs.getString("g_type"), slist);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			JDBCUtil.close(con, ps, rs);
		}
		return map;
	}
	
	
	public int getTotalCount(String type) {
		Connection con  = null;
		PreparedStatement ps = null;
		String sql = "select * from t_goods where g_type =?";
		int i = JDBCUtil.queryForInt(sql, type);
		int count = 0;
		if(i != 0) {
			ResultSet rs=JDBCUtil.executeQuery(sql, type);
			try{
				while(rs.next()){
					count++;					
				}
				return count;	
			}catch(SQLException e){
				e.printStackTrace();
			}finally {
				JDBCUtil.close(con, ps, rs);
			}
		}
		// TODO 自动生成的方法存根
		return 0;
	}
	
	
	/**
	 * 
	 * @param type        	类别
	 * @param pageSize    	每页显示数据量
	 * @param currentPage 	当前第几页
	 * @return  			返回封装商品列表的分页实体类
	 * 
	 *   分页公式：  limit (currentPage-1)*pageSize,pageSize
	 */
	public PageBean getGoodsByType(String type,int pageSize, int currentPage) {
		Connection con  = null;
		PreparedStatement ps = null;
		String sql = "select * from t_goods where g_type =? limit ?,?";
		List<Goods> slist=new ArrayList<Goods>();
			ResultSet rs=JDBCUtil.executeQuery(sql, type,(currentPage-1)*pageSize,pageSize);
			try{
				while(rs.next()){
					Goods good = new Goods();
					good.setId(rs.getInt("id"));
					good.setName(rs.getString("g_name"));
					good.setPrice(rs.getDouble("g_price"));
					good.setNum(rs.getInt("g_num"));
					good.setColor(rs.getString("g_color"));
					good.setCreateDate(rs.getString("g_create_date"));
					good.setDescription(rs.getString("g_description"));
					good.setImgurl(rs.getString("g_imgurl"));
					good.setMemory(rs.getString("g_memory"));
					good.setType(rs.getString("g_type"));
					slist.add(good);
				}
				PageBean bean = new PageBean(pageSize,getTotalCount(type),currentPage);
				bean.setGoods(slist);
				bean.setCurrentPage(currentPage);
				bean.setPageSize(pageSize);
				bean.setTotalCount(getTotalCount(type));
				int flag = getTotalCount(type) % pageSize;
				if(flag != 0) {
					bean.setTotalPage((getTotalCount(type) / pageSize) + 1);
				}
				bean.setTotalPage(getTotalCount(type) / pageSize);
				return bean;
			}catch(SQLException e){
				e.printStackTrace();
			}finally {
				JDBCUtil.close(con, ps, rs);
			}
		// TODO 自动生成的方法存根
		return null;
	}
	/**
	 * 根据商品id获取某个商品的详细信息
	 * @param id  前端页面传来的id参数
	 * @return
	 */
	public Goods getGoodsInfoById(int id) {
		Connection con  = null;
		PreparedStatement ps = null;
		String sql = "select * from t_goods where id =?";
		int i = JDBCUtil.queryForInt(sql, id);
		if(i != 0) {
			ResultSet rs=JDBCUtil.executeQuery(sql, id);
			try{
				while(rs.next()){
					Goods good = new Goods();
					good.setId(rs.getInt("id"));
					good.setName(rs.getString("g_name"));
					good.setPrice(rs.getDouble("g_price"));
					good.setNum(rs.getInt("g_num"));
					good.setColor(rs.getString("g_color"));
					good.setCreateDate(rs.getString("g_create_date"));
					good.setDescription(rs.getString("g_description"));
					good.setImgurl(rs.getString("g_imgurl"));
					good.setMemory(rs.getString("g_memory"));
					good.setType(rs.getString("g_type"));
					return good;	
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally {
				JDBCUtil.close(con, ps, rs);
			}
		}
		// TODO 自动生成的方法存根
		return null;
	}

	public static void main(String[] args) {
		System.out.println(new GoodsService().getGoodsInfoById(1));
	}
	
}
