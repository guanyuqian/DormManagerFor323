package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import view.addclient.OKListner;
import controller.ClientController;
/***
 * pre:resultTable table
 * 显示出由table，personalAmount的表格
 * @author lenovo
 *
 */
public class personalAmountShow {
	JPanel ClientPanel=new JPanel();
	JFrame ClientFrame=new JFrame();
	resultTable ResultTable;
	public void show(resultTable table){
		ClientFrame.getContentPane().removeAll();
		ClientFrame.getContentPane().repaint();
		ClientFrame.getContentPane().validate();
		ClientPanel.removeAll();
		ClientPanel.repaint();
		ClientPanel.validate();
		ResultTable=table;

		ClientPanel=new JPanel();
		ClientPanel.add(ResultTable);
		ClientPanel.setFont(MainInterface.f);
//		ClientPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

		

		ClientFrame.setSize(800,800);
		ClientFrame.getContentPane().add(ResultTable);////初始化一个容器，添加此JPanel
		ClientFrame.setVisible(true);//显示
//		ClientFrame.pack();
	}
}
