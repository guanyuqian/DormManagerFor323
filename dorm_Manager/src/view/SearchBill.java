package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import view.AddBill.OKListner;
import controller.BillController;
import controller.Dao;
/**
 * 
 * TODO 查询账单
 * @Pre:Billmanager，
 * @Post:可根据名称，时间，总金额，备注	查询账单
 * @author Sam
 * @time2015年9月2日下午11:21:05
 * @file_name SearchBill.java
 * @package_name view
 * @project_name dorm_Manager
 */
public class SearchBill {

	JLabel billName=new JLabel("名称：");
	JLabel note=new JLabel("备注：");
	JLabel[] clientName;
	JCheckBox[] clientCheck;
	JLabel totalMoney=new JLabel("总金额：");
	JLabel date=new JLabel("创建时间：");
	JLabel status=new JLabel("状态：");
	Date longago=new Date(0,0,0);
	DateChooser beginDateText = new DateChooser(longago,"yyyy-MM-dd");  
	DateChooser endDateText = new DateChooser("yyyy-MM-dd");  
	JTextArea billNameText=new JTextArea("");
	JTextArea totalMoneyText=new JTextArea("");
	JTextArea notesText=new JTextArea("");
	JPanel BillPanel=new JPanel();
	JFrame BillFrame=new JFrame();
	resultTable ResultTable;
	String clientId[];
	   JPanel datePanel=new JPanel();
	private JComboBox statuscomboBox;
	private String[] names;
	private JFrame frame;
	   
	public void showManager(resultTable table){
		names = new String[3];
		names[0] = "全部";
		names[1] = "未删除";
		names[2] = "已删除";
		statuscomboBox = new JComboBox(names);
		statuscomboBox.setEditable(false);
		BillFrame.getContentPane().removeAll();
		BillFrame.getContentPane().repaint();
		BillFrame.getContentPane().validate();
		BillPanel.removeAll();
		BillPanel.repaint();
		BillPanel.validate();
		totalMoneyText.setText("");
		notesText.setText("");
		billNameText.setText("");
		Dao.connSQL();
	   
	
	  
	    status.setFont(MainInterface.f);
	    statuscomboBox.setFont(MainInterface.f);
	    
	   
	 
	    
	    BillPanel.add(billName);
	    BillPanel.add(billNameText);
	    BillPanel.add(note);
	    BillPanel.add(notesText);
	    BillPanel.add(totalMoney);
	    BillPanel.add(totalMoneyText);
	    BillPanel.add(date);
	    JLabel log=new JLabel("~");
	    datePanel.add(beginDateText);
	    datePanel.add(log);
	    datePanel.add(endDateText);
	    datePanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	    BillPanel.add(datePanel);
	    BillPanel.add(status);
	    BillPanel.add(statuscomboBox);
	    date.setFont(MainInterface.f);
	    totalMoneyText.setFont(MainInterface.f);
	    totalMoney.setFont(MainInterface.f);
		BillPanel.setFont(MainInterface.f);
	    notesText.setFont(MainInterface.f);
	    note.setFont(MainInterface.f);
	    billNameText.setFont(MainInterface.f);
	    billName.setFont(MainInterface.f);
	    String getNoDeleteSql="where deleteFlag=0";
		ArrayList clientNames=Dao.loadData("Client", "ClientName",getNoDeleteSql);//拿到client中的clientname字段
		ArrayList clientIds=Dao.loadData("Client", "clientId",getNoDeleteSql);//拿到client中的clientname字段
	
		clientId=new String[clientIds.size()];
		clientName=new JLabel[clientNames.size()];
		clientCheck=new JCheckBox[clientNames.size()];
		
		for(int i=0;i<clientNames.size();i++){
			Map id=(Map)clientIds.get(i);
			Map name=(Map)clientNames.get(i);
			clientName[i]=new JLabel((String) name.get("clientName")+":");
			clientCheck[i]=new JCheckBox();
			clientName[i].setFont(MainInterface.f);
		
			
			clientCheck[i].setFont(MainInterface.f);
			clientId[i]=id.get("clientId").toString();
			BillPanel.add(clientCheck[i]);
			BillPanel.add(clientName[i]);
		}

		BillPanel.setLayout(new GridLayout(0,2,5,5));
		BillPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

		JScrollPane scrollPane = new JScrollPane(BillPanel);
		scrollPane.setBounds(100, 100, 100, 300);
		BillPanel.setPreferredSize(new Dimension(scrollPane.getWidth() - 50, scrollPane.getHeight()*2));
		
		ResultTable=table;
//		BillPanel.add();//将按钮集合放到EAST
	    JPanel OkPanel=new JPanel();
		JButton OK = new JButton("确定");
		OkPanel.add(OK);
		OK.setFont(MainInterface.f);
		OK.addActionListener(new OKListner());
		BillFrame.getContentPane().add(BorderLayout.CENTER,scrollPane);////初始化一个容器，添加此JPanel
		BillFrame.getContentPane().add(BorderLayout.SOUTH,OkPanel);////初始化一个容器，添加此JPanel
		BillFrame.setVisible(true);//立即显示改变	
		BillFrame.setSize(600,600);
	}
	public class  OKListner implements ActionListener{

	public void actionPerformed(ActionEvent arg0) {
//			ArrayList<String> AllClientAmount=new ArrayList();
//			ArrayList<String> ClientIdList=new ArrayList();
//			// TODO Auto-generated method stub
//			for(int i=0;i<clientMoney.length;i++){
//				if(!clientMoney[i].equals(0)) {
//					String newStr = clientMoney[i].getText().replaceFirst("^0*", "");
//					AllClientAmount.add(newStr);
//					ClientIdList.add(clientId[i]);
//				}
//			}
//			new BillController().createBill(billNameText.getText(), notesText.getText(),AllClientAmount,  ClientIdList);
			ResultTable.init();
			BillFrame.dispose();
		}//开始按钮监听器，
	}
}
