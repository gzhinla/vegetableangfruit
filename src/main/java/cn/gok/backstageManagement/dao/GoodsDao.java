package cn.gok.backstageManagement.dao;

import java.util.List;
import java.util.Map;

import cn.gok.backstageManagement.entity.Goods;

/**
* @author CJY
* @version ����ʱ�䣺2019��7��22�� ����1:00:28
* @ClassName ������
* @Description ������
*/
public interface GoodsDao {
	/**
	 * ��ȡ���е���Ʒ�б�
	 * @return
	 */
	public  List<Goods> getAllGoods();
	
	/**
	 * ������Ʒ������   ���� ����   ��ȡ��Ʒ�б�����ģ����ѯ
	 * @param params��װ�� ��Ʒ�� �� ���͵�map 
	 * @return
	 */
	public List<Goods> getGoodsByName(Map<String, String> params);
	
	/**
	 * ������Ʒid��ȡĳ����Ʒ����ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public Goods getGoodsInfoById(int id);
	
	/**
	 * �����Ʒ�ķ���
	 * @param good ����ӵ���Ʒ
	 * @return ��ӳɹ�����1�����򷵻�0
	 */
	public int addGoods(Goods good);
	
	/**
	 * ����id�޸���Ʒ����Ϣ������id �� ������Ʒ��ʱ�䲻�޸� 
	 * @param good ��װ��Ҫ�޸ĵ���Ϣ��Goods����
	 * @return ����޸ĳɹ�����1���޸�ʧ�ܷ���0
	 */
	public int updateGoodsById(Goods good);
	
	/**
	 * �޸���Ʒ��ͼƬ��
	 * �޸���Ʒʱ������û����޸���ͼƬ���Ƚ�ͼƬ�ϴ��޸�
	 * @param good
	 * @return
	 */
	public int updateGoodsImgById(String img, int id);
	
	/**
	 * ����idɾ��ָ������Ʒ
	 * @param id  ��ɾ����Ʒ��id
	 * @return  ���ɾ���ɹ�����1��ɾ��ʧ�ܷ���0
	 */
	public int deleteGoodsById(int id);

}
