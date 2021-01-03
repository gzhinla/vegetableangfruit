package cn.gok.backstageManagement.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gok.backstageManagement.entity.Goods;
import cn.gok.backstageManagement.entity.Orders;
import cn.gok.util.JDBCUtil;

/**
 * ��Ʒ��ҵ������
 * @author asus
 *
 */
public class GoodsService {
	/**
	 * ��ѯ���е���Ʒ��Ϣ��  
	 * 		��dao�в鵽�����ݷ��أ�
	 *  	���Ʋ����ô˷����������õ����ݣ�����ʾ�������ҳ��
	 * @return  ������Ʒ����
	 */
	public List<Goods> getAllGoods() {
		Connection con  = null;
		PreparedStatement ps = null;
		String sql="select * from t_goods";
		List<Goods> slist=new ArrayList<>();
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
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			JDBCUtil.close(con, ps, rs);
		}
		return slist;
	}
	
	/**
	 * ������������Ʒ���������Ӧ������Ʒ��Ϣ��
	 * @param   parms ��װ�� ������Ʒ����Ϣ 
	 * @return   ������Ʒ����
	 */
	public List<Goods> getGoodsByName(Map<String, String> parms){
		Connection con  = null;
		PreparedStatement ps = null;
		String sql="select * from t_goods where g_name like ?";
		String name = parms.get("name");
		List<Goods> slist=new ArrayList<>();
		String str = "%" + name + "%";
		ResultSet rs=JDBCUtil.executeQuery(sql, str);
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
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			JDBCUtil.close(con, ps, rs);
		}
		return slist;
	}
	
	/**
	 * �����Ʒ�ķ���
	 * @param good ����ӵ���Ʒ
	 * @return ��ӳɹ�����1�����򷵻�0
	 */
	public int addGoods(Goods good) {
		String sql="insert into t_goods(id,g_name,g_price,g_num,g_imgurl,g_description,g_type,g_create_date,g_color,g_memory) values(?,?,?,?,?,?,?,?,?,?)";
		int count=JDBCUtil.update(sql, good.getId(),good.getName(),good.getPrice(),good.getNum(),good.getImgurl(),good.getDescription(),good.getType(),good.getCreateDate(),good.getColor(),good.getMemory());
		if(count != 0) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * ����id�޸���Ʒ����Ϣ������id �� ������Ʒ��ʱ�䲻�޸� 
	 * @param good ��װ��Ҫ�޸ĵ���Ϣ��Goods����
	 * @return ����޸ĳɹ�����1���޸�ʧ�ܷ���0
	 */
	public int updateGoodsById(Goods good) {
		String sql="update t_goods set g_name=?,g_price=?,g_num=?,g_description=?,g_type=?,g_color=?,g_memory=? where id=?";
		int count=JDBCUtil.update(sql, good.getName(),good.getPrice(),good.getNum(),good.getDescription(),good.getType(),good.getColor(),good.getMemory(),good.getId());
		if(count != 0) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * �޸���Ʒ��ͼƬ��
	 * �޸���Ʒʱ������û����޸���ͼƬ���Ƚ�ͼƬ�ϴ��޸�
	 * @param good
	 * @return  ����޸ĳɹ�����1��ɾ��ʧ�ܷ���0
	 */
	public int updateGoodsImgById(String img, int id) {
		String sql="update t_goods set g_imgurl=? where id=?";
		int count=JDBCUtil.update(sql, img, id);
		if(count != 0) {
			return 1;
		}
		return 0;
	}
	
	
	/**
	 * ������Ʒid��ȡ ��Ʒ������
	 * @param id
	 * @return ���ؾ�����Ʒ��Ϣ
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
		// TODO �Զ����ɵķ������
		return null;
	}
	
	
	/**
	 * ����idɾ��ָ������Ʒ
	 * @param id  ��ɾ����Ʒ��id
	 * @return  ���ɾ���ɹ�����1��ɾ��ʧ�ܷ���0
	 */
	public int deleteGoodsById(int id) {
		String sql="delete from t_goods where id=?";
		int count=JDBCUtil.update(sql, id);
		if(count != 0) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * ��Ӷ����ķ���
	 * @param order ����ӵ���Ʒ
	 * @return ��ӳɹ�����1�����򷵻�0
	 */
	public int addOrder(Orders order) {
		String sql="insert into t_order(o_number,o_user,o_time,o_name,o_sex,o_address,o_phone,o_address_label,o_goods_id,o_goods_num,o_goods_status) values(?,?,?,?,?,?,?,?,?,?,?)";
		int count=JDBCUtil.update(sql,order.getNumber(),order.getUser(),order.getTime(),order.getName(),order.getSex(),order.getAddress(),order.getPhone(),order.getAddress_label(),order.getGoods_id(),order.getGoods_num(),order.getGoods_status());
		if(count != 0) {
			return 1;
		}
		return 0;
	}
}
