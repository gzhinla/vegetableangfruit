package cn.gok.backstageManagement.dao;

import cn.gok.backstageManagement.entity.User;

/**
* @author CJY
* @version ����ʱ�䣺2019��7��22�� ����1:04:27
* @ClassName ������
* @Description ������
*/
public interface UserDao {
	/**
	 * �õ�¼ʱʹ��
	 * 	@param userName  �û���
	 * 	@param password  ����
	 * 	@return   �����û����������ѯ�����û���Ϣ�� 
	 * 			    ������ѯ��������Ϣ�������������Ϣ��Ҫ����װ��һ��User�����в����أ�
	 * 			    ���û�в鵽��ֱ�ӷ���null
	 */
	public User getUserByNameAndPassword(String userName, String password);
	
	/**
	 *������Ա��¼ʱ���ã� 
	 * 	�����û�����ȡ�û��Ľ�ɫ��Ϣ
	 * 	@param userName
	 * 	@return  �����û��Ľ�ɫ��Ϣ
	 */
	public String getUserRoleByName(String userName);
	
	/**
	 *�û��޸�����ʹ�ã� �û������ԭʼ���� �����ǶԵģ��ſ��Խ��������޸�
	 * 	@param id �û�id
	 * 	@return  ���ز鵽�Ĵ��û�������
	 */
	public String getPasswordById(int id);
	
	/**
	 *�޸�����ʱʹ�ã� 
	 * 	�����û�id�޸��û�������
	 * 	@param id           	�û�id
	 * 	@param newPassword  	ǰ�� ������������
	 * 	@return  �޸ĳɹ�����1�����򷵻�0
	 */
	public int updatePasswordById(int id, String newPassword);
}
	
