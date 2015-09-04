package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import view.addclient.OKListner;
import controller.ClientController;

/**
 * TODO 更改client
 * @Pre: clientManager调用
 * @Post: 更改client
 * @author Sam
 * @time2015年9月2日下午10:50:50
 * @file_name AddBill.java
 * @package_name view
 * @project_name dorm_Manager
 */
public class editClient {
	JPanel ClientPanel=new JPanel();
	JFrame ClientFrame=new JFrame();
	JTextArea nameText=new JTextArea("");
	JTextArea moneyText=new JTextArea("");
	JTextArea roomRemark=new JTextArea("");
	JLabel name=new JLabel("名称*：");
	JLabel money=new JLabel("总金额*：");
	JLabel room=new JLabel("宿舍：");
	resultTable ResultTable;
	ArrayList<String> selectInfo;
	public void showManager(resultTable table,ArrayList<String> info){
		selectInfo=info;
		ClientFrame.getContentPane().removeAll();
		ClientFrame.getContentPane().repaint();
		ClientFrame.getContentPane().validate();
		ClientPanel.removeAll();
		ClientPanel.repaint();
		ClientPanel.validate();
		ResultTable=table;
		nameText.setText(selectInfo.get(1));
		ClientFrame.getContentPane().removeAll();
		ClientFrame.getContentPane().repaint();
		ClientFrame.getContentPane().validate();
		ClientPanel.removeAll();
		ClientPanel.repaint();
		ClientPanel.validate();
		ResultTable=table;
		roomRemark.setText("");
		moneyText.setText("");
		ClientPanel=new JPanel();
		ClientPanel.setFont(MainInterface.f);
		ClientPanel.setLayout(new GridLayout(0,2,5,5));
		ClientPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

		room.setFont(MainInterface.f);
		name.setFont(MainInterface.f);
		money.setFont(MainInterface.f);
		nameText.setFont(MainInterface.f);
		roomRemark.setFont(MainInterface.f);
		moneyText.setFont(MainInterface.f);

		
		ClientPanel.add(name);
		ClientPanel.add(nameText);
//		ClientPanel.add(room);
//		ClientPanel.add(roomRemark);
//		ClientPanel.add(money);
//		ClientPanel.add(moneyText);
	    JPanel OkPanel=new JPanel();
		JButton OK = new JButton("确定");
		OkPanel.add(OK);
		OK.setFont(MainInterface.f);
		OK.addActionListener(new OKListner());
		ClientFrame.setSize(500,250);
		ClientFrame.getContentPane().add(BorderLayout.CENTER,ClientPanel);////初始化一个容器，添加此JPanel
		ClientFrame.getContentPane().add(BorderLayout.SOUTH,OkPanel);////初始化一个容器，添加此JPanel]
		ClientFrame.setVisible(true);//显示
		ClientFrame.pack();
	}
	public class  OKListner implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(nameText.getText().equals("")){
				JOptionPane.showMessageDialog(null, "请输入用户姓名", "输入错误",JOptionPane.ERROR_MESSAGE);  
				return;	
			}
			
			new ClientController().update(selectInfo.get(0),nameText.getText());
			ResultTable.init();
			ClientFrame.dispose();
		}//开始按钮监听器，
}
}
