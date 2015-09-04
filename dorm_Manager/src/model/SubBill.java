package model;
 
import java.util.Map;
import java.util.HashMap;

public class SubBill 
{
	private int SubBillId;								//���˵�ID
	/*private Set ClientAmount=new HashSet();		//ÿ���û��Ľ���ţ�*/
	Map<Integer,Integer> ClientAmount=new HashMap<Integer,Integer>();//ÿ���û���ÿ���û��˴ν��׽��
	
	boolean deleteFlag=true;
	
	public int getSubBillId() {
		return SubBillId;
	}
	public void setSubBillId(int subBillId) {
		SubBillId = subBillId;
	}
	public Map<Integer, Integer> getClientAmount() {
		return ClientAmount;
	}
	public void setClientAmount(Map<Integer, Integer> clientAmount) {
		ClientAmount = clientAmount;
	}
	
	
};
