package view;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import controller.Dao;
/**
 * 
 * TODO 
 * @Pre:String table（数据库表格名称） ,String condtion（条件）
 * @Post:返回一个Jpanel，里面有某表格
 * @author Sam
 * @time2015年9月2日下午10:57:51
 * @file_name resultTable.java
 * @package_name view
 * @project_name dorm_Manager
 */
public class resultTable extends JPanel{
	Font headFont=new Font("Dialog", 0, 25);
	Font lineFont=new Font("宋体",Font.BOLD,20);
	ArrayList resultData;
	String Table=null;
	String Condition=null;
	JTable resultdata;
	String[] tableHeads;
	public resultTable(String table){
	Table =table.toLowerCase();
	int lengthOfField;
	init();
	}
	public resultTable(String table ,String condtion){
	Table =table;
	Condition=condtion;
	int lengthOfField;
	init();
	}

	public  void init() {
	System.out.println("init");
	removeAll();
	//setFont(new Font("宋体",Font.BOLD,30));
	setLayout(new BorderLayout());
	resultdata=createTable();
	resultdata.setFont(lineFont);
	resultdata.setRowHeight(30);//设置行高
	resultdata.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	JScrollPane scrollPane = new JScrollPane(resultdata); 
	add(scrollPane);
	repaint();
	validate();
	}
	
	public ArrayList<String>  getSelectNameInfo(){
		ArrayList<String> resultInfo=new ArrayList<String>();
		try{
		for(int i=0;i<tableHeads.length;i++){
			resultInfo.add(resultdata.getValueAt(resultdata.getSelectedRow(),i).toString());
		}
		}catch(Exception e){
			return null;
		}
		return resultInfo;
	}
	public String getClientNameByClientId(String id){
		String[] ids=(id).split(",");
		String names="";
		for(int k=0;k<ids.length;k++){
		ResultSet rs;
		String sql="select clientname from client where clientid = "+ids[k];
		rs=Dao.selectSQL(sql);
		try {
				rs.next();
				names+=rs.getString(1)+",";
			} catch (SQLException e) {
			// TODO Auto-generated catch block
				names+="null"+",";
			}
		}
		return names;
	}
	public String getBillNameByBillId(String id){
		String[] ids=(id).split(",");
		String names="";
		for(int k=0;k<ids.length;k++){
		ResultSet rs;
		String sql="select BillName from bill where Billid = "+ids[k];
		rs=Dao.selectSQL(sql);
		try {
				rs.next();
				names+=rs.getString(1);
			} catch (SQLException e) {
			// TODO Auto-generated catch block
				names+="null";
			}
		}
		return names;
	}
	public void getField(DefaultTableModel dtm,String table){
		switch (table.toUpperCase()){
		case "CLIENT":
		for(int i=0;i<resultData.size();i++){
			Vector v = new Vector();
			Map test=(Map)resultData.get(i);
			v.add(test.get("clientId"));
			v.add(test.get("clientName"));
			v.add(test.get("dorm"));
			v.add(test.get("clientBalance"));
			int del=(int) test.get("deleteFlag");
			v.add(del==1?"已删除":"未删除");
			dtm.addRow(v);
			}
		return;
		case "BILL":
			
			for(int i=0;i<resultData.size();i++){
				Vector v = new Vector();
				Map test=(Map)resultData.get(i);
				v.add(test.get("billId"));
				v.add(test.get("billName"));
				String names=getClientNameByClientId((String) test.get("allClentId"));
				v.add(names.substring(0, names.length()-1));
				v.add(test.get("allClientAmount"));
				v.add(test.get("totalMoney"));
				v.add(test.get("notes"));
				v.add(test.get("billDate"));
				
				int del=(int) test.get("deleteFlag");
				v.add(del==1?"已删除":"未删除");
				dtm.addRow(v);
				}
			return;
		case "PERSONALAMOUNT":
			for(int i=0;i<resultData.size();i++){
				Vector v = new Vector();
				Map test=(Map)resultData.get(i);
				v.add(test.get("personalAmountId"));
				String billName=getBillNameByBillId((String) test.get("billId").toString());
				v.add(billName);
				//v.add(test.get("billId"));
				String names=getClientNameByClientId((String) test.get("clientId").toString());
				v.add(names.substring(0, names.length()-1));
				v.add(test.get("money"));
				int del=(int) test.get("deleteFlag");
				v.add(del==1?"已删除":"未删除");
				dtm.addRow(v);
				}
			return;
		}
	}
	public void searchTable(String sql) {//通过传递sql语句选择表格
		System.out.println("searchTable");
		removeAll();
		//setFont(new Font("宋体",Font.BOLD,30));
		setLayout(new BorderLayout());
		Condition=sql;
		resultdata=createTable();
		resultdata.setFont(lineFont);
		resultdata.setRowHeight(30);//设置行高
		resultdata.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(resultdata); 
		add(scrollPane);
		repaint();
		validate();
		
	}
	public JTable createTable() {
	JTable table = new JTable(){
		   public boolean isCellEditable(int row, int column) { 
			    return false;
			   }
	
			  };
			
//			  table.setFont(new Font("宋体", Font.PLAIN,18));
			  table.getTableHeader().setFont(headFont);
	if(Table.toLowerCase().equals("client")||Table.toLowerCase().equals("bill")){
	table.addMouseListener(new MouseAdapter(){
		   public void mouseClicked(MouseEvent e) {
		      if (e.getClickCount() == 2){
		    	  String Id=getSelectNameInfo().get(0);
		    	  String condition;
		    	  if(Table.toLowerCase().equals("client"))
		    		  condition ="where clientid = "+Id;
		    	  else
		    		  condition ="where billid = "+Id;
		    	  resultTable amountTable = new resultTable("personalamount",condition);
		    	  new personalAmountShow().show(amountTable);
		                    //你可以在這里再進行是點擊哪一行的判斷
		                }
		            }
		        });
	}
	System.out.println(Table);
	Dao.connSQL();
	if(Condition==null){
		resultData=Dao.loadData(Table);
	}
	else{
		System.out.println("Condition"+Condition);
		resultData=Dao.loadData(Table,"*",Condition);
	}
	tableHeads = Dao.getFieldNameForTable(Table);
	DefaultTableModel dtm = (DefaultTableModel)table.getModel();
	dtm.setColumnIdentifiers(tableHeads);
	System.out.println(tableHeads);
	/**ArrayList 数据**/

	/******转化成Vector***********/
	getField(dtm,Table);
	/********添加进JTable中********/
	
	/*****设置table的列模型****/
	TableColumnModel tcm = table.getColumnModel();
//	tcm.getColumn(2).setCellEditor(new DefaultCellEditor(new JCheckBox())); 
//	tcm.getColumn(2).setCellRenderer(new TestTableCellRenderer()); 
	for(int i=0;i<tableHeads.length;i++){
		tcm.getColumn(i).setPreferredWidth(50); 
		tcm.getColumn(i).setMinWidth(50); 
		tcm.getColumn(i).setMaxWidth(10000); 
	}
	return table;
	}

	public static void main(String[] args) {
//	JFrame jframe = new JFrame();
//	jframe.setBounds(300,100,300,200);
//	jframe.setTitle("测试");
//	jframe.add(new resultTable("bill"));
//	jframe.setVisible(true);
//	jframe.pack();]
		Dao.connSQL();
	}

	class TestTableCellRenderer extends JCheckBox implements TableCellRenderer{

	@Override
	public Component getTableCellRendererComponent(JTable table,
	Object value, boolean isSelected, boolean hasFocus, int row,
	int column) {
	Boolean b = (Boolean) value; 
	this.setSelected(b.booleanValue()); 
	return this; 

	}

	}



	
	

}
