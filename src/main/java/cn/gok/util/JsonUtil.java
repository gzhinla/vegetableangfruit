package cn.gok.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 *���ڰ����fastjson��װ��jsonת�������� jsonת���Ĺ�����
 */
public class JsonUtil {
	
	/**
	 * ��json����ת�ɶ�Ӧ��java���� 
	 * @param jsonData  ǰ��ҳ�淢�͹�����json����
	 * @param clazz     Ҫת��������
	 * @return
	 */
	public static <T> T jsonToBean(String jsonData, Class<T> clazz) {
        return JSON.parseObject(jsonData, clazz);
    }
	

	/**
     * ��ǰ�˴�������JSON����ת����ָ����java ��List����:
     * 		һ����˵��ת�ɣ�   
     * 				 List<String>
     * 				 List<javaBean�࣬��User>
     * 		         List<Map<String,Object>>
     *      �����ּ���
     * @param jsonData ǰ��ҳ�淢�͹�����json����
     * @param clazz    Ҫת��������
     * @return List<T>
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> clazz) {
        return JSON.parseArray(jsonData, clazz);
    }
    
    /**
     * ��json�ַ���ת��Ϊmap
     * @param json�ַ���
     * @return  map
     */
    public static <K, V> Map<K, V>  jsonToMap(String jsonData) {
    	Map<K, V> m = (Map<K, V>) JSONObject.parseObject(jsonData);
        return m;
    }

    
    /**
     * json���� ת��Ϊjava����
     * @param text
     * @param clazz
     * @return
     */
    public static <T> Object[] jsonToArray(String jsonData, Class<T> clazz) {
        return JSON.parseArray(jsonData, clazz).toArray();
    }
    
	/**
     * ��java����ת����JSON�ַ���
     * @param object    Ҫ��ת����java����
     * @return JSON����
     */
    public static String javaObjectToJson(Object object) {
        return JSON.toJSONString(object);
    }
}
