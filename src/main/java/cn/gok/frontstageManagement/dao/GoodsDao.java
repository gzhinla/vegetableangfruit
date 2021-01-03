package cn.gok.frontstageManagement.dao;

import java.util.List;
import java.util.Map;

import cn.gok.frontstageManagement.entity.Goods;
import cn.gok.frontstageManagement.entity.PageBean;

/**
* @author CJY
* @version ����ʱ�䣺2019��7��22�� ����1:05:48
* @ClassName ������
* @Description ������
*/
public interface GoodsDao {
	/**
	 * ��ȡ���ݿ���ÿ����Ʒ��ʱ�������ǰ4��
	 * ���õ������ݸ�����Ʒ����� ����map�У�
	 * ����map��keyΪ���valueΪ��������Ʒ��list����
	 * @return
	 */
	public Map<String, List<Goods>> getAllGoods();
	
	/**
	 * 
	 * @param type        	���
	 * @param pageSize    	ÿҳ��ʾ������
	 * @param currentPage 	��ǰ�ڼ�ҳ
	 * @return
	 * 
	 *   ��ҳ��ʽ��  limit (currentPage-1)*pageSize,pageSize
	 */
	public PageBean getGoodsByType(String type, int pageSize, int currentPage);
	
	/**
	 * ���������в�ѯʱ��  ��ҳʱʹ��
	 * 	��ȡ��ǰ�����Ʒ��������
	 * @return
	 */
	public int getTotalCount(String type);
	
	/**
	 * ������Ʒid��ȡĳ����Ʒ����ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public Goods getGoodsInfoById(int id);
}
