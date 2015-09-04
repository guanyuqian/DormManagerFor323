package model;

public class Bill 
{
	private String BillName;			//֧����Ʒ���¼������
	private int BillAmount;				//֧�����
	private int SubBillId;				//���˵�ID
	private int MainBillId;				//���˵���ID��
	private String Notes;				//��ע
	boolean deleteFlag=true;
	public String getBillName() {
		return BillName;
	}
	public void setBillName(String billName) {
		BillName = billName;
	}
	public int getMainBillId() {
		return MainBillId;
	}
	public void setMainBillId(int mainBillId) {
		MainBillId = mainBillId;
	}
	public int getBillAmount() {
		return BillAmount;
	}
	public void setBillAmount(int billAmount) {
		BillAmount = billAmount;
	}
	public int getSubBillId() {
		return SubBillId;
	}
	public void setSubBillId(int subBillId) {
		SubBillId = subBillId;
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}
};
