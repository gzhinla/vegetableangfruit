package cn.gok.backstageManagement.entity;

import java.util.Random;

public class Orders {
	private int id;//������
	private String number;//��������
	private String user;//�µ��û�
	private String time;//�µ�ʱ��
	private String name;//�ռ���
	private String sex;//�Ա�
	private String address;//�ջ���ַ
	private String phone;//�绰����
	private String address_label;//��ַ��ǩ
	private int goods_id;//��Ʒ���
	private int goods_num;//��������
	private int goods_status;//����״̬
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress_label() {
		return address_label;
	}
	public void setAddress_label(String address_label) {
		this.address_label = address_label;
	}
	public int getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}
	public int getGoods_num() {
		return goods_num;
	}
	public void setGoods_num(int goods_num) {
		this.goods_num = goods_num;
	}
	public int getGoods_status() {
		return goods_status;
	}
	public void setGoods_status(int goods_status) {
		this.goods_status = goods_status;
	}
	
	public String getRandomString(int length) { // length �ַ�������
		StringBuffer buffer = new StringBuffer("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i ++) {
		sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}
}
