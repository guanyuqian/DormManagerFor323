package model;
import java.util.HashSet;
import java.util.Set;


public class PersonalAmount 
{
	private int ClientId;												//当前用户ID
	Set<Integer>SubBillIdList=new HashSet<Integer>();					//
	boolean deleteFlag=true;
	public int getClientId() {
		return ClientId;
	}
	public void setClientId(int clientId) {
		ClientId = clientId;
	}
	public Set<Integer> getSubBillIdList() {
		return SubBillIdList;
	}
	public void setSubBillIdList(Set<Integer> subBillIdList) {
		SubBillIdList = subBillIdList;
	}
	public boolean isDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
};