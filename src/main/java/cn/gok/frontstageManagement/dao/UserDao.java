package cn.gok.frontstageManagement.dao;

import cn.gok.frontstageManagement.entity.User;

/**
* @author CJY
* @version ����ʱ�䣺2019��7��22�� ����1:08:11
* @ClassName ������
* @Description ������
*/
public interface UserDao {
	/**
	 * ע���û�ʱʹ�ã����һ���û�����
	 * @param user
	 * @return  ��ӳɹ�������1�����򷵻�0
	 */
	public int addUser(User user);
	
	/**
	 * ����û����Ƿ��ظ���
	 * @param userName
	 * @return  �����û�����÷��ж�Ӧ��id���ڣ�������ڣ�
	 * 			�򷵻���Ӧ��idֵ �����򷵻�0
	 */
	public int checkUserNameRepeat(String userName);
	
	/**
	 * �õ�¼ʱʹ��
	 * @param userName
	 * @param password
	 * @return   �����û����������ѯ�����û���Ϣ�� 
	 * 			  ����ܹ���ѯ�����򽫲�ѯ����Ϣ��װ��һ��User�����У������أ����򷵻�null
	 */
	public User getUserByNameAndPassword(String userName, String password);
	
	/**
	 * �޸��û���Ϣ
	 * @param user  ����user�з�װ��ǰ̨ҳ�洫����������Ҫ�޸ĵ�������Ϣ
	 * @return  ����id�޸Ķ�Ӧ���û����ݣ� �ɹ�����1�����򷵻�0
	 */
	public int updateUserById(User user);
	
	/**
	 * �����û�id�޸��û�������
	 * @param id
	 * @param password
	 * @return  �޸ĳɹ�����1�����򷵻�0
	 */
	public int updatePasswordById(int id, String password);
}
