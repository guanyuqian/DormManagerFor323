package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.BillController;
import controller.ClientController;
import controller.Dao;

/**
 * TODO 添加Billdialog
 * @Pre: billManager调用
 * @Post: 添加Bill，相应更改client和personalAmount两张表数据
 * @author Sam
 * @time2015年9月2日下午10:50:50
 * @file_name AddBill.java
 * @package_name view
 * @project_name dorm_Manager
 */
public class AddBill {
	JLabel billName=new JLabel("名称*：");
	JLabel note=new JLabel("备注：");
	JLabel[] clientName;
	JTextArea[] clientMoney;
	JLabel client1=new JLabel("id：");
	JLabel client2=new JLabel("money：");
	JTextArea billNameText=new JTextArea("");
	JTextArea totalMoney=new JTextArea("");
	JTextArea notesText=new JTextArea("");
	JPanel BillPanel=new JPanel();
	JFrame BillFrame=new JFrame();
	resultTable ResultTable;
	String clientId[];
	public void showManager(resultTable table){
		BillFrame.getContentPane().removeAll();
		BillFrame.getContentPane().repaint();
		BillFrame.getContentPane().validate();
		BillPanel.removeAll();
		BillPanel.repaint();
		BillPanel.validate();
		totalMoney.setText("");
		notesText.setText("");
		billNameText.setText("");
		Dao.connSQL();
	    BillPanel.add(billName);
	    BillPanel.add(billNameText);
	    BillPanel.add(note);
	    BillPanel.add(notesText);
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
		clientMoney=new JTextArea[clientNames.size()];
		
		for(int i=0;i<clientNames.size();i++){
			Map id=(Map)clientIds.get(i);
			Map name=(Map)clientNames.get(i);
			clientName[i]=new JLabel((String) name.get("clientName")+":");
			
			clientName[i].setFont(MainInterface.f);
			clientMoney[i]=new JTextArea("0");
			
			clientMoney[i].setFont(MainInterface.f);
			clientId[i]=id.get("clientId").toString();
			BillPanel.add(clientName[i]);
			BillPanel.add(clientMoney[i]);
		}

		BillPanel.setLayout(new GridLayout(0,2,5,5));
		BillPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

		JScrollPane scrollPane = new JScrollPane(BillPanel);
		scrollPane.setBounds(100, 100, 100, 300);
		//BillPanel.setPreferredSize(new Dimension(scrollPane.getWidth() - 50, scrollPane.getHeight()*2));
		
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
		BillFrame.  setSize(800,500);
	}
	public class  OKListner implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			ArrayList<String> AllClientAmount=new ArrayList();
			ArrayList<String> ClientIdList=new ArrayList();
			// TODO Auto-generated method stub
			if(billNameText.getText().equals("")){
				JOptionPane.showMessageDialog(null, "请输入名称", "输入错误",JOptionPane.ERROR_MESSAGE);  
				return;
			}
			for(int i=0;i<clientMoney.length;i++){
				if(!clientMoney[i].getText().equals("0")){//金额不为零时，把用户加到AllClientAmount中
					if(clientMoney[i].getText().equals("")){
						JOptionPane.showMessageDialog(null, "金额不可为空，插入失败", "输入错误",JOptionPane.ERROR_MESSAGE);  
						return;
					}
					if(!Hint.isNumber(clientMoney[i].getText())){
						JOptionPane.showMessageDialog(null, "请输入8位以内的数字", "输入错误",JOptionPane.ERROR_MESSAGE);  
						return;
					}
					String newStr = clientMoney[i].getText().replaceFirst("^0*", "");
					newStr = String .format("%.2f",Float.parseFloat(newStr));
					AllClientAmount.add(newStr);
					ClientIdList.add(clientId[i]);
				}
			}
			if(!new BillController().createBill(billNameText.getText(), notesText.getText(),AllClientAmount,  ClientIdList)){
				JOptionPane.showMessageDialog(null, "插入失败", "输入错误",JOptionPane.ERROR_MESSAGE);  
				return;
			}
			
			ResultTable.init();
			BillFrame.dispose();
		}//开始按钮监听器，
	}
}
