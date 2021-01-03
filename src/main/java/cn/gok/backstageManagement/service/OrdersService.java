package cn.gok.backstageManagement.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gok.backstageManagement.entity.Orders;
import cn.gok.util.JDBCUtil;

/**
 * ������ҵ������
 * @author asus
 *
 */
public class OrdersService {
	/**1
	 * ��ѯ���ж�����Ϣ��  
	 * 		��dao�в鵽�����ݷ��أ�
	 *  	���Ʋ����ô˷����������õ����ݣ�����ʾ�������ҳ��
	 * @return  ���ض�������
	 */
	public List<Orders> getAllOrders() {
		Connection con  = null;
		PreparedStatement ps = null;
		String sql="select * from t_order";
		List<Orders> slist=new ArrayList<>();
		ResultSet rs=JDBCUtil.executeQuery(sql);
		try{
			while(rs.next()){
				Orders order = new Orders();
				order.setId(rs.getInt("id"));
				order.setNumber(rs.getString("o_number"));
				order.setUser(rs.getString("o_user"));
				order.setTime(rs.getString("o_time"));
				order.setName(rs.getString("o_name"));
				order.setSex(rs.getString("o_sex"));
				order.setAddress(rs.getString("o_address"));
				order.setPhone(rs.getString("o_phone"));
				order.setAddress_label(rs.getString("o_address_label"));
				order.setGoods_id(rs.getInt("o_goods_id"));
				order.setGoods_num(rs.getInt("o_goods_num"));
				order.setGoods_status(rs.getInt("o_goods_status"));
				slist.add(order);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			JDBCUtil.close(con, ps, rs);
		}
		return slist;
	}
	
	/**2(ע�⣡��ѯ��Χ�޸�)
	 * ���������߶������������Ӧ���Ķ�����Ϣ��
	 * @param   parms ��װ�� ���Ͷ�������Ϣ 
	 * @return   ���ض�������
	 */
	public List<Orders> getOrdersByName(Map<String, String> parms){
		Connection con  = null;
		PreparedStatement ps = null;
		String name = parms.get("name");
		String sql="select * from t_order where o_user like ?";
		List<Orders> slist=new ArrayList<>();
		String str = "%" + name + "%";
		ResultSet rs=JDBCUtil.executeQuery(sql, str);
		try{
			while(rs.next()){
				Orders order = new Orders();
				order.setId(rs.getInt("id"));
				order.setNumber(rs.getString("o_number"));
				order.setUser(rs.getString("o_user"));
				order.setTime(rs.getString("o_time"));
				order.setName(rs.getString("o_name"));
				order.setSex(rs.getString("o_sex"));
				order.setAddress(rs.getString("o_address"));
				order.setPhone(rs.getString("o_phone"));
				order.setAddress_label(rs.getString("o_address_label"));
				order.setGoods_id(rs.getInt("o_goods_id"));
				order.setGoods_num(rs.getInt("o_goods_num"));
				order.setGoods_status(rs.getInt("o_goods_status"));
				slist.add(order);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			JDBCUtil.close(con, ps, rs);
		}
		return slist;
	}
	
	
	/**3
	 * ����id�޸Ķ�������Ϣ������id �� ����������ʱ�䲻�޸� 
	 * @param good ��װ��Ҫ�޸ĵ���Ϣ��order����
	 * @return ����޸ĳɹ�����1���޸�ʧ�ܷ���0
	 */
	public int updateOrdersById(Orders order) {
		String sql="update t_order set o_number=?,o_user=?,o_time=?,o_name=?,o_sex=?,o_address=?,o_phone=?,o_address_label=?,o_goods_id=?,o_goods_num=?,o_goods_status=? where id=?";
		int count=JDBCUtil.update(sql,order.getNumber(),order.getUser(),order.getTime(),order.getName(),order.getSex(),order.getAddress(),order.getPhone(),order.getAddress_label(),order.getGoods_id(),order.getGoods_num(),order.getGoods_status(),order.getId());
		if(count != 0) {
			return 1;
		}
		return 0;
	}
	
	
	/**4
	 * ���ݶ���id��ȡ ����������
	 * @param id
	 * @return ���ؾ��嶩����Ϣ
	 */
	public Orders getOrdersInfoById(int id) {
		Connection con  = null;
		PreparedStatement ps = null;
		String sql = "select * from t_order where id =?";
		int i = JDBCUtil.queryForInt(sql, id);
		if(i != 0) {
			ResultSet rs=JDBCUtil.executeQuery(sql, id);
			try{
				while(rs.next()){
					Orders order = new Orders();
					order.setId(rs.getInt("id"));
					order.setNumber(rs.getString("o_number"));
					order.setUser(rs.getString("o_user"));
					order.setTime(rs.getString("o_time"));
					order.setName(rs.getString("o_name"));
					order.setSex(rs.getString("o_sex"));
					order.setAddress(rs.getString("o_address"));
					order.setPhone(rs.getString("o_phone"));
					order.setAddress_label(rs.getString("o_address_label"));
					order.setGoods_id(rs.getInt("o_goods_id"));
					order.setGoods_num(rs.getInt("o_goods_num"));
					order.setGoods_status(rs.getInt("o_goods_status"));
					return order;	
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
	
	
	/**5
	 * ����idɾ��ָ���Ķ���
	 * @param id  ��ɾ��������id
	 * @return  ���ɾ���ɹ�����1��ɾ��ʧ�ܷ���0
	 */
	public int deleteOrdersById(int id) {
		String sql="delete from t_order where id=?";
		int count=JDBCUtil.update(sql, id);
		if(count != 0) {
			return 1;
		}
		return 0;
	}
	
	/**5
	 * ɾ��ȫ������
	 * @return  ���ɾ���ɹ�����1��ɾ��ʧ�ܷ���0
	 */
	public int deleteAllOrders() {
		String sql="delete from t_order";
		int count=JDBCUtil.update(sql);
		if(count != 0) {
			return 1;
		}
		return 0;
	}
	
	/**6
	 * ��Ӷ����ķ���
	 * @param order ����ӵĶ���
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
