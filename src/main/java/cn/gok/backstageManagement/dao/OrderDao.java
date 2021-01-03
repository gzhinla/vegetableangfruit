package cn.gok.backstageManagement.dao;

import java.util.List;
import java.util.Map;


import cn.gok.backstageManagement.entity.Orders;



public interface OrderDao {
	/**1
	 * ��ѯ���е���Ʒ��Ϣ��  
	 * 		��dao�в鵽�����ݷ��أ�
	 *  	���Ʋ����ô˷����������õ����ݣ�����ʾ�������ҳ��
	 * @return  ������Ʒ����
	 */
	public List<Orders> getAllOrders();
	/**2
	 * ������������Ʒ���������Ӧ������Ʒ��Ϣ��
	 * @param   parms ��װ�� ������Ʒ����Ϣ 
	 * @return   ������Ʒ����
	 */
	public List<Orders> getOrdersByName(Map<String, String> parms);
	/**3
	 * ����id�޸Ķ�������Ϣ������id �� ������Ʒ��ʱ�䲻�޸� 
	 * @param good ��װ��Ҫ�޸ĵ���Ϣ��Goods����
	 * @return ����޸ĳɹ�����1���޸�ʧ�ܷ���0
	 */
	public int updateOrdersById(Orders order);
	
	/**4
	 * ���ݶ���id��ȡ ��Ʒ������
	 * @param id
	 * @return ���ؾ�����Ʒ��Ϣ
	 */
	
	public Orders getOrdersInfoById(int id);
	
	/**5
	 * ����idɾ��ָ������Ʒ
	 * @param id  ��ɾ����Ʒ��id
	 * @return  ���ɾ���ɹ�����1��ɾ��ʧ�ܷ���0
	 */
	public int deleteOrdersById(int id);
	/**6
	 * ��Ӷ����ķ���
	 * @param order ����ӵ���Ʒ
	 * @return ��ӳɹ�����1�����򷵻�0
	 */
	public int addOrder(Orders order);

}
