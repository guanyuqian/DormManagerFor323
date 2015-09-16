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
	JLabel status=new JLabel("状态：");
	JTextArea statusText=new JTextArea("");
	JPanel BillPanel=new JPanel();
	JFrame BillFrame=new JFrame();
	resultTable ResultTable;
	String clientId[];
	JPanel datePanel=new JPanel();
	private JFrame frame;
	   
	public void showManager(resultTable table){
		ResultTable=table;
		BillFrame.getContentPane().removeAll();
		BillFrame.getContentPane().repaint();
		BillFrame.getContentPane().validate();
		statusText.setText("");
		BillPanel.removeAll();
		BillPanel.repaint();
		BillPanel.validate();
	    status.setFont(MainInterface.f); 
	    statusText.setFont(MainInterface.f); 
	    BillPanel.add(status);
	    BillPanel.add(statusText);
		BillPanel.setLayout(new GridLayout(0,2,5,5));
		BillPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
//		BillPanel.add();//将按钮集合放到EAST
	    JPanel OkPanel=new JPanel();
		JButton OK = new JButton("确定");
		OkPanel.add(OK);
		OK.setFont(MainInterface.f);
		OK.addActionListener(new OKListner());
		BillFrame.getContentPane().add(BorderLayout.CENTER,BillPanel);////初始化一个容器，添加此JPanel
		BillFrame.getContentPane().add(BorderLayout.SOUTH,OkPanel);////初始化一个容器，添加此JPanel
		BillFrame.setVisible(true);//立即显示改变	
		BillFrame.pack();
	}
	public class  OKListner implements ActionListener{

	public void actionPerformed(ActionEvent arg0) {
			String Key=statusText.getText();
			//searchbill(Key);
			ResultTable.searchTable("WHERE()");
			BillFrame.dispose();
		}//开始按钮监听器，
	}
}
