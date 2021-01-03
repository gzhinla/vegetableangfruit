package cn.gok.backstageManagement.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.gok.backstageManagement.entity.User;
import cn.gok.util.JDBCUtil;

public class UserService {
	
	
	
	
	/**
	 * �õ�¼ʱʹ��
	 * 	@param userName  �û���
	 * 	@param password  ����
	 * 	@return   �����û����������ѯ�����û���Ϣ�� 
	 * 			    ������ѯ��������Ϣ�������������Ϣ��Ҫ����װ��һ��User�����в����أ�
	 * 			    ���û�в鵽��ֱ�ӷ���null
	 */
	public User getUserByNameAndPassword(String userName, String password) {
		Connection con  = null;
		PreparedStatement ps = null;
		String sql = "select * from t_user where u_name =? and u_password=?";
		int i = JDBCUtil.queryForInt(sql, userName, password);
		if(i != 0) {
			ResultSet rs=JDBCUtil.executeQuery(sql, userName, password);
			try{
				while(rs.next()){
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setUserName(rs.getString("u_name"));
					user.setPassword(rs.getString("u_password"));
					user.setRole(rs.getString("u_role"));
					user.setPhone(rs.getString("u_phone"));
					user.setEmail(rs.getString("u_email"));
					System.out.println(rs.getString("u_role"));
					return user;	
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
	 *������Ա��¼ʱ���ã� 
	 * 	�����û�����ȡ�û��Ľ�ɫ��Ϣ
	 * 	@param userName
	 * 	@return  �����û��Ľ�ɫ��Ϣ
	 */
	public String getUserRoleByName(String userName) {
		Connection con  = null;
		PreparedStatement ps = null;
		String sql = "select * from t_user where u_name =?";
		int i = JDBCUtil.queryForInt(sql, userName);
		if(i != 0) {
			ResultSet rs=JDBCUtil.executeQuery(sql, userName);
			try{
				while(rs.next()){
					String role= rs.getString("u_role");
					return role;	
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
	 *�û��޸�����ʹ�ã� �û������ԭʼ���� �����ǶԵģ�
	 *�ſ��Խ��������޸�
	 * 	@param id �û�id
	 * 	@return  ���ز鵽�Ĵ��û�������
	 */
	public String getPasswordById(int id) {
		Connection con  = null;
		PreparedStatement ps = null;
		String sql = "select * from t_user where id =?";
		int i = JDBCUtil.queryForInt(sql, id);
		
		if(i != 0) {
			ResultSet rs=JDBCUtil.executeQuery(sql, id);
			try{
				while(rs.next()){
					String password = rs.getString("u_password");
					return password;	
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
	 *�޸�����ʱʹ�ã� 
	 * 	�����û�id�޸��û�������
	 * 	@param id           	�û�id
	 * 	@param newPassword  	ǰ�� ������������
	 * 	@return  				�޸ĳɹ�����1�����򷵻�0
	 */
	public int updatePasswordById(int id, String newPassword) {
		String sql = "update t_user set u_password=? where id=?";
		int i = JDBCUtil.update(sql, newPassword, id);
		if(i != 0) {
			return 1;
		}
	// TODO �Զ����ɵķ������
		return 0;
	}
	
	
	
	
	
}
