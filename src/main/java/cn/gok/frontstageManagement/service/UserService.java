package cn.gok.frontstageManagement.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.gok.frontstageManagement.entity.User;
import cn.gok.util.JDBCUtil;

public class UserService {
	
	/**
	 * ע���û�ʱʹ�ã����һ���û�����
	 * @param user
	 * @return  ��ӳɹ�������1�����򷵻�0
	 */
	public int addUser(User user) {
		String sql="insert into t_user(u_name,u_password,u_role,u_phone,u_email) values(?,?,?,?,?)";
		int count=JDBCUtil.update(sql,user.getUserName(),user.getPassword(),user.getRole(),user.getPhone(),user.getEmail());
		if(count != 0) {
			return 1;
		}
		return 0;
	}
	
	
	/**
	 * ����û����Ƿ��ظ���
	 * @param userName
	 * @return  �����û�����÷��ж�Ӧ��id���ڣ�������ڣ�
	 * 			�򷵻���Ӧ��idֵ �����򷵻�0
	 */
	public int checkUserNameRepeat(String userName) {
		String sql = "select id from t_user where u_name =?";
		int i = JDBCUtil.queryForInt(sql, userName);
		if(i != 0) {
			return i;
		}
		// TODO �Զ����ɵķ������
		return 0;
	}
	

	/**
	 * �õ�¼ʱʹ��
	 * @param userName
	 * @param password
	 * @return   �����û����������ѯ�����û���Ϣ�� 
	 * 			  ����ܹ���ѯ�����򽫲�ѯ����Ϣ��װ��һ��User�����У������أ����򷵻�null
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
	 * �޸��û���Ϣ
	 * @param user  ����user�з�װ��ǰ̨ҳ�洫����������Ҫ�޸ĵ�������Ϣ
	 * @return       ����id�޸Ķ�Ӧ���û����ݣ� �ɹ�����1�����򷵻�0
	 */
	public int updateUserById(User user) {
		String sql="update t_user set u_name=?,u_password=?,u_role=?,u_phone=?,u_email=? where id=?";
		int count=JDBCUtil.update(sql, user.getUserName(),user.getPassword(),user.getRole(),user.getPhone(),user.getEmail(),user.getId());
		if(count != 0) {
			return 1;
		}
		return 0;
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
		ResultSet rs=JDBCUtil.executeQuery(sql, id);
			try{
				while(rs.next()){
					String password = rs.getString("u_password");
					System.out.println(password);
					return password;	
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally {
				JDBCUtil.close(con, ps, rs);
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
