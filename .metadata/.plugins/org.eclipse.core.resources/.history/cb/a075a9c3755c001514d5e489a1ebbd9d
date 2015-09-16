package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import view.AddBill.OKListner;
import controller.BillController;
import controller.Dao;
/**
 * TODO 更改Billdialog
 * @Pre: billManager调用
 * @Post: 更改Bill，相应更改client和personalAmount两张表数据
 * @author Sam
 * @time2015年9月2日下午10:50:50
 * @file_name AddBill.java
 * @package_name view
 * @project_name dorm_Manager
 */
public class EditeBill {
	JLabel billName=new JLabel("名称*：");
	JLabel note=new JLabel("备注：");
	JButton[] imp;
	JLabel[] clientName;
	JPanel[] clientaddPanel;
	JTextArea[] clientMoney;
	JTextArea billNameText=new JTextArea("");
	JTextArea notesText=new JTextArea("");
	JPanel BillPanel=new JPanel();
	JPanel calcPanel=new JPanel();
	JFrame BillFrame=new JFrame();
	resultTable ResultTable;
	String clientId[];
	ArrayList<String> selectInfo;
	TestCalc calc=new TestCalc();
	JButton calcShow=new JButton("计算器");
	JLabel result=new JLabel();
	public void showManager(resultTable table,ArrayList<String> info){
		selectInfo=info;
		BillFrame.getContentPane().removeAll();
		BillFrame.getContentPane().repaint();
		BillFrame.getContentPane().validate();
		BillPanel.removeAll();
		BillPanel.repaint();
		BillPanel.validate();
		notesText.setText(info.get(5));
		billNameText.setText(info.get(1));
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
		
		String[] oldClientId=selectInfo.get(2).split(",");
		String[] oldClientMoney=selectInfo.get(3).split(",");
		
		imp=new  JButton[clientNames.size()];
		clientaddPanel=new JPanel[clientNames.size()];
		
		for(int i=0;i<clientNames.size();i++){
			Map id=(Map)clientIds.get(i);
			Map name=(Map)clientNames.get(i);
			clientId[i]=id.get("clientId").toString();
			clientName[i]=new JLabel((String) name.get("clientName")+":");
			String sql="select money from personalamount where billId = "+info.get(0)+
					" and clientId = "+clientId[i];
			ResultSet res=Dao.selectSQL(sql);
			try {
				res.next();
				clientMoney[i]=new JTextArea(res.getString(1));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				clientMoney[i]=new JTextArea("0");
		
			}
			clientName[i].setFont(MainInterface.f);
			clientMoney[i].setFont(MainInterface.f);
			
			clientaddPanel[i]=new JPanel();
			imp[i]=new JButton("导入");
			imp[i].addActionListener(new impListner(i));
			clientaddPanel[i].setLayout(new GridLayout(0,2,50,50));
			clientaddPanel[i].add(clientMoney[i]);
			clientaddPanel[i].add(imp[i]);

			BillPanel.add(clientName[i]);
			BillPanel.add(clientaddPanel[i]);
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
		OK.setFont(MainInterface.f);
		OK.addActionListener(new OKListner());

//计算器调用
		calc.setOthersLabel(result);
		result.setFont(MainInterface.f);
		calcPanel.add(calcShow);
		calcPanel.add(result);
		calcShow.addActionListener(new calcShowActionListener());
		BillFrame.getContentPane().add(BorderLayout.NORTH,calcPanel);////初始化一个容器，添加此JPanel
		calcShow.setFont(MainInterface.f);

		BillFrame.getContentPane().add(BorderLayout.CENTER,scrollPane);////初始化一个容器，添加此JPanel
		BillFrame.getContentPane().add(BorderLayout.SOUTH,OkPanel);////初始化一个容器，添加此JPanel
		BillFrame.setVisible(true);//立即显示改变	
		BillFrame.  setSize(800,600);
		
	}
	public class  calcShowActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			calc .setVisible(true);
			result.setText(calc.result);
		}
	}
	public class  impListner implements ActionListener{
		private int NO;
		public impListner(int i) {
			// TODO Auto-generated constructor stub
			NO=i;
		}

		public void actionPerformed(ActionEvent e) {
			clientMoney[NO].setText(result.getText());
		}
	
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
				if(!clientMoney[i].getText().equals("0")) {
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
			if(!new BillController().update(selectInfo.get(0),
					billNameText.getText(), notesText.getText(),AllClientAmount,  ClientIdList)){
				JOptionPane.showMessageDialog(null, "不能创建空账单", "输入错误",JOptionPane.ERROR_MESSAGE);  
				return;
			}
			ResultTable.init();
			BillFrame.dispose();
		}//开始按钮监听器，
	}
}
