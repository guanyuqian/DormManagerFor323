package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;  
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

import controller.BillController;
import controller.ClientController;
import controller.Dao;
/**
 * 
 * TODO 主界面
 * @Pre:
 * @Post:可实现增删改操作，管理用户和账单
 * @author Sam
 * @time2015年9月2日下午10:57:07
 * @file_name MainInterface.java
 * @package_name view
 * @project_name dorm_Manager
 */
public class MainInterface {
	JFrame theFrame=new JFrame("Dorm Manager");
	static Font f=new Font("宋体",Font.BOLD,30);
	static Font F=new Font("宋体",Font.BOLD,50);
	static Font TitleFont=new Font("楷体",Font.BOLD,35);
	static Font nomalFont=new   Font("Dialog",   1,   25);
	static Font warnFont=new   Font("Dialog",   1,   25);
	TestCalc maincalc=new TestCalc();
	JButton calcShow=new JButton("计算器");
	JPanel headMasaage=new JPanel();//JPanel 是 Java图形用户界面(GUI)工具包swing中的面板容器类
	JPanel Manager=new JPanel();//JPanel 是 Java图形用户界面(GUI)工具包swing中的面板容器类
	JPanel returnArea=new  JPanel();	
	JButton returnToMainInterface =new JButton("返回");//声明开始按钮
	

	
	AddBill bill=new AddBill();
	SearchBill searchbill=new SearchBill();
	JLabel ALLmoney=new JLabel();
	JLabel Lowmoney=new JLabel();
	JLabel Conn=new JLabel();
	JButton BuildDatabaseJButton;
	float allmoney=0;
	EditeBill editbill=new EditeBill();
	public static void main(String[] args){
		new MainInterface().buildGUI();
	}
	public void buildGUI() {//建立图形化界面
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//在windows关闭时会把程序结束掉
		double width = Toolkit.getDefaultToolkit().getScreenSize().width; //得到当前屏幕分辨率的高
		double height = Toolkit.getDefaultToolkit().getScreenSize().height;//得到当前屏幕分辨率的宽
		theFrame.setSize((int)width,(int)height);//设置大小
		theFrame.setLocation(0,0); //设置窗体居中显示
	
//		theFrame.setSize(800,800);//设定frame大小
		reviewToMainInterface();
		//theFrame.setBounds(50,50,300,300);
		
	}//关闭方法
	/**
	 * reviewToMainInterface:返回到主界面的函数
	 */
	public void reviewToMainInterface(){	
		returnToMainInterface.setFont(MainInterface.f);
		
		theFrame.getContentPane().removeAll();
		theFrame.getContentPane().repaint();
		theFrame.getContentPane().validate();
		theFrame.setTitle("DM 宿舍资金管理系统");
		theFrame.setFont(TitleFont);
		theFrame.setForeground(Color.red);
		Conn.setText("数据库连接失败");
		if(Dao.connSQL()){
			Conn.setText("");
		};
		allmoney =ClientController.getAllMoney();
		BuildDatabaseJButton=new JButton("创建数据库");
		BuildDatabaseJButton.addActionListener(new BuildDatabaseListner());//监听开始按钮]
		headMasaage.removeAll();
		headMasaage.repaint();
		headMasaage.validate();
		
		try{
			if(!Conn.getText().equals(""))headMasaage.add(BuildDatabaseJButton);
			else headMasaage.remove(BuildDatabaseJButton);
			}catch(Exception ex){}
		ALLmoney.setText("舍费总额："+allmoney);
		
		ArrayList lowMoney=ClientController.getLowMoney();
		String Warn="";
		
		for(int i=0;i<lowMoney.size();i++){
			Map test=(Map) lowMoney.get(i);
			Warn=Warn+test.get("clientName")+" 余："+test.get("clientBalance");	
		}
		Lowmoney.setText(Warn);
		Lowmoney.setForeground(Color.RED);
		Conn.setFont(warnFont);
		Conn.setForeground(Color.red);
	

		headMasaage.add(calcShow);//调出计算器
		calcShow.setFont(f);
		calcShow.addActionListener(new calcShowActionListener());
		headMasaage.add(ALLmoney);
		headMasaage.add(Conn);
		headMasaage.add(Lowmoney);
		
		
		BuildDatabaseJButton.setFont(nomalFont);
		ALLmoney.setForeground(Color.blue);
		Lowmoney.setFont(warnFont);
		ALLmoney.setFont(nomalFont);
		FlowLayout FLOW=new FlowLayout(1);
		headMasaage.setLayout(FLOW);
		FLOW.setHgap(200); 
		headMasaage.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		Manager.removeAll();
		Manager.repaint();
		Manager.validate();
		Manager.setFont(F);
		Manager.setLayout(new GridLayout(0,1,5,10));
		Manager.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		JButton ClientJButton =new JButton("用户管理");//声明开始按钮		
		ClientJButton.addActionListener(new ClientManagerListner());//监听开始按钮	
		ClientJButton.setFont(F);
		JButton BillJButton =new JButton("账单管理");
		BillJButton.setFont(F);
		BillJButton.addActionListener(new BillManagerListner());

		
		Manager.add(ClientJButton);//将按钮集合放到EAST
		Manager.add(BillJButton);//将按钮集合放到EAST
		theFrame.getContentPane().add(BorderLayout.NORTH,headMasaage);////初始化一个容器，添加此JPanel
		theFrame.getContentPane().add(BorderLayout.CENTER,Manager);////初始化一个容器，添加此JPanel
		theFrame.setVisible(true);//立即显示改变		
	}
	public class  calcShowActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		
			maincalc .setVisible(true);
		}
	}
	/**
	 * BillManagerListner
	 * 账单按钮响应，生成账单界面
	 * 内有
	 * EditBillListner
	 * deleteBillListner
	 * AddBillListner
	 * 三个账单增删改的按钮响应
	 * @author Sam
	 *
	 */
	public class  BillManagerListner implements ActionListener{//开始按钮监听器，
		JButton addBill;
		JButton deleteBill;
		JButton editBill;
		JButton searchBill; 
		resultTable BillTable;
		
		public void reviewToBillManager(){
			BillTable=new resultTable("Bill");
			theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//在windows关闭时会把程序结束掉
			Manager.setFont(f);
			Manager.setLayout(new GridLayout(1,4,10,10));
			Manager.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
			headMasaage.removeAll();
			headMasaage.repaint();
			headMasaage.validate();
			theFrame.getContentPane().removeAll();
			theFrame.getContentPane().repaint();
			theFrame.getContentPane().validate();
			addBill =new JButton("添加账单");//声明开始按钮		
			addBill.addActionListener(new AddBillListner());//监听开始按钮
			addBill.setFont(f);
			deleteBill =new JButton("删除账单");//声明开始按钮	
			deleteBill.addActionListener(new deleteBillListner());//监听开始按钮
			deleteBill.setFont(f);
			editBill =new JButton("修改账单");//声明开始按钮		
			editBill.addActionListener(new EditBillListner());//监听开始按钮	
			editBill.setFont(f);
			searchBill =new JButton("查询账单");//声明开始按钮		
			searchBill.addActionListener(new SearchBillListener());//监听开始按钮	
			searchBill.setFont(f);
			
			Manager.removeAll();
			Manager.repaint();
			Manager.validate();
		
			
			JPanel resultArea=new  JPanel();
			resultArea.setBackground(Color.white);
			
			Manager.add(addBill);//将按钮集合放到EAST
			Manager.add(deleteBill);//将按钮集合放到EAST
			Manager.add(editBill);//将按钮集合放到EAST
			Manager.add(searchBill);//将按钮集合放到EAST
		
			theFrame.setTitle("账单管理");
			theFrame.getContentPane().add(BorderLayout.NORTH,Manager);////初始化一个容器，添加此JPanel

			returnToMainInterface.addActionListener(new returnToMainInterfaceListner());
			returnArea.add(returnToMainInterface);
			
			theFrame.getContentPane().add(BorderLayout.CENTER,BillTable);////初始化一个容器，添加此JPanel	
			theFrame.getContentPane().add(BorderLayout.SOUTH,returnArea);////初始化一个容器，添加此JPanel	
			theFrame.setVisible(true);//立即显示改变	
		}
		public void actionPerformed(ActionEvent a) {
			reviewToBillManager();
		}
		
		
		public class  SearchBillListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {		
				searchbill.BillFrame.setTitle("查询账单");
				searchbill.showManager(BillTable);
	           }
			public class  OKListner implements ActionListener{

				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					bill.BillFrame.dispose();
				}
			}
		}
		
		
		public class  EditBillListner implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			editbill.BillFrame.setTitle("修改账单");
			ArrayList<String>info=BillTable.getSelectNameInfo();
			if(info==null){
					JOptionPane.showMessageDialog(null, "未选择选项");
					return;	
			}
			if(info.get(7).equals("已删除")){//已删除的账单不能修改
				JOptionPane.showMessageDialog(null, "不能修改已删除的账单");//显示修改客户frame
				return;	
			}
			editbill.showManager(BillTable,info);//显示修改账单frame
           }//开始按钮监听器，
		
		}
		public class  deleteBillListner implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				ArrayList<String>info=BillTable.getSelectNameInfo();
				if(info==null){
					JOptionPane.showMessageDialog(null, "未选择选项");
					return;	
				}
				if(info.get(7).equals("已删除")){//已删除的账单不能修改
					JOptionPane.showMessageDialog(null, "不能删除已删除的账单");//显示修改客户frame
					return;	
				}
				if(JOptionPane.showConfirmDialog(Manager, "确认删除:"+info.get(1)+"?", "删除账单",JOptionPane.DEFAULT_OPTION)==0) {
					new BillController().deleteBill(info.get(0));
					BillTable.init();
				}
				
	           }//开始按钮监听器，
			
		}
		public class  AddBillListner implements ActionListener{
			public void actionPerformed(ActionEvent e) {		
				bill.BillFrame.setTitle("添加账单");
				bill.showManager(BillTable);//显示添加账单frame
	           }//开始按钮监听器，
			public class  OKListner implements ActionListener{

				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					bill.BillFrame.dispose();
				}//开始按钮监听器，
			}
		}
		
	}
	/**
	 * ClientManagerListner
	 * 客户按钮响应，生成账单界面
	 * 内有
	 * editClientListner
	 * deleteClientListner
	 * addClientListner
	 * 三个账单增删改的按钮响应
	 * @author Sam
	 *
	 */
		public class  ClientManagerListner implements ActionListener{//开始按钮监听器，
			JButton searchClient;
			JButton editClient;
			JButton addClient;
			JButton deleteClient;
			searchClient search=new searchClient();
			addclient add=new addclient();
			editClient edit=new editClient();
			resultTable clientTable=new resultTable("client");
			public void reviewToClentManager(){
				Manager.setFont(f);
				Manager.setLayout(new GridLayout(1,4,10,10));
				Manager.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
				JButton addClient =new JButton("添加用户");//声明开始按钮		
//				JButton ClientJButton =new JButton("查询用户");//声明开始按钮		
				addClient.addActionListener(new addClientListner());//监听开始按钮	
				addClient.setFont(f);
				JButton deleteClient =new JButton("删除用户");//声明开始按钮		
				deleteClient.addActionListener(new deleteClientListner());//监听开始按钮	
				deleteClient.setFont(f);
				JButton editClient =new JButton("修改用户");//声明开始按钮		
				editClient.addActionListener(new editClientListner());//监听开始按钮	
				editClient.setFont(f);
				JButton searchClient =new JButton("查询用户");//声明开始按钮		
				searchClient.addActionListener(new searchClientListner());//监听开始按钮	
				searchClient.setFont(f);
				
				theFrame.getContentPane().removeAll();
				theFrame.getContentPane().repaint();
				theFrame.getContentPane().validate();
				
				Manager.removeAll();
				Manager.repaint();
				Manager.validate();
				Manager.add(addClient);//将按钮集合放到EAST
				Manager.add(deleteClient);//将按钮集合放到EAST
				Manager.add(editClient);//将按钮集合放到EAST
				Manager.add(searchClient);//将按钮集合放到EAST
				returnToMainInterface.addActionListener(new returnToMainInterfaceListner());
				returnArea.add(returnToMainInterface);
				
				theFrame.getContentPane().add(BorderLayout.CENTER,clientTable);////初始化一个容器，添加此JPanel	
				theFrame.getContentPane().add(BorderLayout.SOUTH,returnArea);////初始化一个容器，添加此JPanel			
				theFrame.setTitle("用户管理");
		
				theFrame.getContentPane().add(BorderLayout.NORTH,Manager);////初始化一个容器，添加此JPanel
				theFrame.setVisible(true);//立即显示改变
				
			}
			public void actionPerformed(ActionEvent a) {
				reviewToClentManager();
			}

			public class  searchClientListner implements ActionListener{
				public void actionPerformed(ActionEvent e) {
//					search.ClientFrame.setTitle("查询客户");//显示添加客户frame
//					search.showManager(clientTable);
				}
			}
			public class  editClientListner implements ActionListener{
				public void actionPerformed(ActionEvent e) {
					edit.ClientFrame.setTitle("修改客户");
					ArrayList<String>info=clientTable.getSelectNameInfo();
					if(info==null){
							JOptionPane.showMessageDialog(null, "未选择选项");//显示修改客户frame
							return;	
					}
					if(info.get(4).equals("已删除")){//已删除的不能修改
						JOptionPane.showMessageDialog(null, "不能修改已删除的用户");//显示修改客户frame
						return;	
					}
					edit.showManager(clientTable,info);
				}
			}
			public class  deleteClientListner implements ActionListener{
				public void actionPerformed(ActionEvent e) {
					ArrayList<String>info=clientTable.getSelectNameInfo();
					if(info==null){
						JOptionPane.showMessageDialog(null, "未选择选项");
						return;	
					}
					if(info.get(4).equals("已删除")){//已删除的账单不能修改
						JOptionPane.showMessageDialog(null, "不能删除已删除的用户");//显示修改客户frame
						return;	
					}
					if(JOptionPane.showConfirmDialog(Manager, "确认删除:"+info.get(1)+"?", "删除用户",JOptionPane.DEFAULT_OPTION)==0) {
						new ClientController().delete(info.get(0));
						clientTable.init();
					}
					
				}
				
			}
			public class  addClientListner implements ActionListener{
				public void actionPerformed(ActionEvent e) {
					add.ClientFrame.setTitle("添加客户");//显示添加客户frame
					add.showManager(clientTable);
					//theFrame.getContentPane().add(BorderLayout.CENTER,clientTable);////初始化一个容器，添加此JPanel	
				}
			}
	}
		/**
		 * 返回到主菜单
		 * @author lenovo
		 *
		 */
		public class  returnToMainInterfaceListner implements ActionListener{
			public void actionPerformed(ActionEvent arg0) {
				reviewToMainInterface();
			}//开始按钮监听器，
		}
		public class  BuildDatabaseListner implements ActionListener{
			
			public void actionPerformed(ActionEvent arg0) {
				if(Dao.buildClientTable("DormManager")){
				JOptionPane.showMessageDialog(null, "数据库创建成功", "创建成功",JOptionPane.INFORMATION_MESSAGE);  
				Conn.setText("");
				return;
			}
				else{
					JOptionPane.showMessageDialog(null, "数据库已创建，不能重复创建", "创建失败",JOptionPane.INFORMATION_MESSAGE);  
					return;
				}
			}//开始按钮监听器，
		}
}
