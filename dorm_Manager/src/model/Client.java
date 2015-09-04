package model;


public class Client 
{
	String clientName;						//�û����
	int clientId;							//�û�ID
	double clientBalance;						//�û�����
	boolean deleteFlag=true;				//�ж��û��Ƿ���ɾ��
	String Dorm;							//323A��323B
	void Cilent()
	{
		clientName="";
		clientId=0;
		clientBalance=0;
		Dorm="";
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public double getClientBalance() {
		return clientBalance;
	}
	public void setClientBalance(double clientBalance) {
		this.clientBalance = clientBalance;
	}
	public boolean isDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getDorm() {
		return Dorm;
	}
	public void setDorm(String dorm) {
		Dorm = dorm;
	}
};
