package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import com.mysql.jdbc.PreparedStatement;

import controller.*;

public class BillController 
{
	public static ArrayList<String> transfer(String str)									//将数据库中的String转为ArrayList（辅助操作）
	{
		
		ArrayList<String> List=new ArrayList<String>();
		String []strList=str.split(",");
		for(int i=0;i<strList.length;i++)
		  {
			  List.add(strList[i]);
		  }
		return List;
	
	
	}
	
	//创建账单 参数为：账单名称（String） 账单备注（String） 各客户金额（ArrayList） 各客户Id（ArrayList）
	public static boolean createBill(String billName,String notes, ArrayList<String>AllClientAmount, ArrayList<String> ClientIdList)
	{
		
		if(notes=="")notes="Empty";											//若无输入，设置为empty
		int i=0;															//计数器
		
		/****************************************************************************************/                          	//将ID导入数据库（转换为String）
		//高圆圆提供有关提高提高
		
		String idList = "";													
		if(ClientIdList.isEmpty()==true) idList="empty";					//若无输入，设置为empty
		else																//转为String输入数据库
		{
			for(i=0;i<ClientIdList.size();i++)
			{
				idList=idList+ClientIdList.get(i)+",";
			}
		}
		idList=idList.substring(0,idList.length()-1);
		
		
		/*****************************************************************************************************/
		
		
		/*******************************************************************************************************/				//将amount导入数据库（转换为String）
		
		
		String amountList = "";
		if(AllClientAmount.isEmpty()==true) {System.out.println("未输入用户！");return false;}
		else
		{
			for(i=0;i<AllClientAmount.size();i++)
			{
				amountList=amountList+AllClientAmount.get(i)+",";
			}
		}
		amountList=amountList.substring(0,amountList.length()-1);
		
		/********************************************************************************************************/
		
		
		/********************************************************************************************/                            //获得总金额
		
		
		double totalMoney1=0;
		double money=0;
		for(i=0;i<AllClientAmount.size();i++)
		{
			money=Double.parseDouble(AllClientAmount.get(i));
			totalMoney1=totalMoney1+money;
		}
		
		
		/***************************************************************************************/
		
	
		
		/******************************************************************************************/							//将以上数据插入数据库的Bill表中
		
		
		String time =Dao.df.format(new Date());
		Dao.connSQL();
		String sql2 = "insert into bill(billName,billDate,notes,allClientAmount,allClentId,totalmoney,deleteFlag) "
				+ "values('"+billName+"','"+time+"','"+notes+"','"+amountList+"','"+idList+"',"+totalMoney1+",'0')";
		System.out.println("执行："+sql2);
		if(!Dao.insertSQL(sql2)){
			return false;
		}
		Dao.print("Bill");//打印检查
		
		
		/************************************************************************************************/


		/********************************************************************************************/                            //将BILL数据插入PersonalAmount表中
		
		
		String billIdSql = "SELECT LAST_INSERT_ID() from bill;";																  //获取LastId
		String billId = null;
		try {
			Dao.statement = (PreparedStatement) Dao.conn.prepareStatement(billIdSql);
			ResultSet rs = Dao.statement.executeQuery(billIdSql);
			rs.next();	
			billId=rs.getString(1);																									//取得Id们
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		String sql3;
		for(i=0;i<AllClientAmount.size();i++)
		{
		sql3 = "insert into personalAmount(billId,clientId,money,deleteFlag) "
				+ "values('"+billId+"','"+ClientIdList.get(i)+"','"+AllClientAmount.get(i)+"','0')";
		
		if(!Dao.insertSQL(sql3)){
			return false;
		};
		}
		Dao.print("PersonalAmount");
		String ClientId;
		String balance;
		for(i=0;i<ClientIdList.size();i++)
		{
			ClientId=ClientIdList.get(i);
			balance=AllClientAmount.get(i);
			ClientController.update_Amount(ClientId, balance);		
		}
		Dao.print("PersonalAmount");
		
		
		/********************************************************************************************/                            //
		
		
		return true;
	}
	
	

	/********************************************************************************************/                            //
	public static boolean createBill(String billId,String billName,String notes, ArrayList<String>AllClientAmount, ArrayList<String> ClientIdList)
	{
		
		
		if(notes=="")notes="Empty";											//若无输入，设置为empty
		int i=0;															//计数器
		
		
		
		/****************************************************************************************/                          	//将ID导入数据库（转换为String）
		
		
		String idList = "";													
		if(ClientIdList.isEmpty()==true) idList="empty";					//若无输入，设置为empty
		else																//转为String输入数据库
		{
			for(i=0;i<ClientIdList.size();i++)
			{
				idList=idList+ClientIdList.get(i)+",";
			}
		}
		idList=idList.substring(0,idList.length()-1);
		
		/*****************************************************************************************************/
		
		
		/*******************************************************************************************************/				//将amount导入数据库（转换为String）
		
		
		String amountList = "";
		if(AllClientAmount.isEmpty()==true) {System.out.println("未输入用户！");return false;}
		else
		{
			for(i=0;i<AllClientAmount.size();i++)
			{
				amountList=amountList+AllClientAmount.get(i)+",";
			}
		}
		amountList=amountList.substring(0,amountList.length()-1);
		
		/********************************************************************************************************/
		
		
		/********************************************************************************************/                            //获得总金额
		
		
		double totalMoney1=0;
		double money=0;
		for(i=0;i<AllClientAmount.size();i++)
		{
			money=Double.parseDouble(AllClientAmount.get(i));
			totalMoney1=totalMoney1+money;
		}
		
		
		/***************************************************************************************/
		
	
		
		/******************************************************************************************/							//将以上数据插入数据库的Bill表中
		
		
		String time =Dao.df.format(new Date());
		Dao.connSQL();
		String sql2 = "insert into bill(billId,billName,billDate,notes,allClientAmount,allClentId,totalmoney,deleteFlag) "
				+ "values('"+billId+"','"+billName+"','"+time+"','"+notes+"','"+amountList+"','"+idList+"',"+totalMoney1+",'0')";
		System.out.println("执行："+sql2);
		Dao.insertSQL(sql2);
		Dao.print("Bill");//打印检查
		
		
		/************************************************************************************************/


		/********************************************************************************************/                            //将BILL数据插入PersonalAmount表中
		
		
		
		
		String sql3;
		for(i=0;i<AllClientAmount.size();i++)
		{
		sql3 = "insert into personalAmount(billId,clientId,money,deleteFlag) "
				+ "values('"+billId+"','"+ClientIdList.get(i)+"','"+AllClientAmount.get(i)+"','0')";
		
		Dao.insertSQL(sql3);
		}
		Dao.print("PersonalAmount");
		String ClientId;
		String balance;
		for(i=0;i<ClientIdList.size();i++)
		{
			ClientId=ClientIdList.get(i);
			balance=AllClientAmount.get(i);
			ClientController.update_Amount(ClientId, balance);		
		}
		Dao.print("PersonalAmount");
		
		
		/********************************************************************************************/                            //
		
		
		return true;
	}
	
	
	//删除账单 账单Id号（String）
	public static boolean deleteBill(String billId)
	{
			
		
//		String select_deleteflag = "Select deleteflag from bill where billId='"+billId+"'";		
//		ResultSet rs;
//		String deleteflag = "";
//		rs=Dao.selectSQL(select_deleteflag);
//		try {
//			rs.next();
//			System.out.println("获取成功");
//			deleteflag=rs.getString(1);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(deleteflag=="1"){System.out.println("该数据已经被删除！");return false;}
//		
//		
		
		int i=0;
		ArrayList<String>AmountList=new ArrayList<String>();															//定义
		ArrayList<String>ClientList=new ArrayList<String>();															//定义
								
		
		/********************************************************************************************/                            //删除
		
		
		String delete_bill="update Bill set deleteFlag='1' where billId='"+billId+"'";
		System.out.println("删除"+delete_bill);
		Dao.deleteSQL(delete_bill);
		
		
		/********************************************************************************************/                            
		
		
		
		/********************************************************************************************/                            //还钱
		
		
		String select_bill_amount = "Select allclientamount from bill where billid='"+billId+"'";		
		ResultSet rss;
		String amount = "";
		rss=Dao.selectSQL(select_bill_amount);
		try {
			rss.next();
			System.out.println("获取成功");
			amount=rss.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		AmountList=transfer(amount);
		
		
		/********************************************************************************************/                            //	
		
		
		String select_bill_clientid = "Select allclentid from bill where billid='"+billId+"'";		
		ResultSet rsss;
		String id = "";
		rsss=Dao.selectSQL(select_bill_clientid);
		try {
			rsss.next();
			System.out.println("获取成功");
			id=rsss.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		ClientList=transfer(id);
		String add_amount="";
		double a_amount=0;
		for(i=0;i<ClientList.size();i++)
		{
			a_amount=-Double.parseDouble(AmountList.get(i));
			add_amount=""+a_amount;
			
			ClientController.update_Amount(ClientList.get(i), add_amount);
		}
		
		
		/********************************************************************************************/                            //
		
		
		/********************************************************************************************/                            //	
		
		
		String delete_personalamount="update personalamount set deleteFlag='1' where billId='"+billId+"'";
		System.out.println("删除"+delete_personalamount);
		Dao.deleteSQL(delete_personalamount);
		
		
		
		return true;
	}


	
	
	
	
	//修改  用来寻找：账单Id（String） 更改的名字（String） 更改的备注（String） Map：（1.用户 与 2.金额）
	
	
	public static boolean update(String billId,String newBillName,String newNotes, ArrayList<String>AllClientAmount, ArrayList<String> ClientIdList)
	{
		
		if(AllClientAmount.size()!=ClientIdList.size()||AllClientAmount.size()==0){
			return false;
		}
		int i=0;
		String clientList="";
		
		String billname="";
		String billnotes="";
		
		
		
		/********************************************************************************************/                            //将ArrayList转换为String导入数据库
		
		
		for(i=0;i<ClientIdList.size();i++)
		{
			if(ClientIdList.get(i)=="") continue;
			clientList=clientList+ClientIdList.get(i)+",";	
		}
		clientList = clientList.substring(0,clientList.length()-1); 
		String amountList="";
		for(i=0;i<AllClientAmount.size();i++)
		{
			if(AllClientAmount.get(i)=="") continue;
			amountList=amountList+AllClientAmount.get(i)+",";	
		}
		amountList = amountList.substring(0,amountList.length()-1); 
		
		
		/*********************************************************************************************/
		
		
																										//update 账单名
			String update_bill_name = "update Bill set billname ='"+newBillName+"' where billid= '"+billId+"'";
			Dao.updateSQL(update_bill_name);

																											//update 账单备注
			String update_bill_notes = "update Bill set notes ='"+newNotes+"' where billid= '"+billId+"'";
			Dao.updateSQL(update_bill_notes);
	
		if(ClientIdList.isEmpty()==false){																						//update 账单Id
			
																																//update 账单金额（并还钱）
			
			String select_bill_name = "Select billname from bill where billid='"+billId+"'";		
			ResultSet rs_billname;
			rs_billname=Dao.selectSQL(select_bill_name);
			try {
				rs_billname.next();
				System.out.println("获取成功");
				billname=rs_billname.getString(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			String select_bill_note = "Select notes from bill where billid='"+billId+"'";		
			ResultSet rs_billnote;
			rs_billnote=Dao.selectSQL(select_bill_note);
			try {
				rs_billnote.next();
				System.out.println("获取成功");
				billnotes=rs_billnote.getString(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			

			
			/********************************************************************************************/                            //还钱
			
			System.out.println("执行到此处");
			deleteBill(billId);
			String sql_delete = "delete from bill where billid = '" + billId + "' ";
			Dao.deleteSQL(sql_delete);
			String update_bill_clientid = "update Bill set allclentId ='"+clientList+"' where billid= '"+billId+"'";
			Dao.updateSQL(update_bill_clientid);
			String sql_delete_personalamount = "delete from personalamount where billid = '" + billId + "' ";
			Dao.deleteSQL(sql_delete_personalamount);
			createBill(billId,billname,billnotes,AllClientAmount,ClientIdList);
			//String billName,String notes, ArrayList<String>AllClientAmount, ArrayList<String> ClientIdList
		}
		return true;
	}
	
	
	
	
	
	
	
	public static void main(String[]args)
	{
//
//		Dao.connSQL();
//		ArrayList<String>ClientList=new ArrayList<String>();
//		ArrayList<String>AmountList=new ArrayList<String>();
//		ClientList.add("45");
//		ClientList.add("45");
//		AmountList.add("200");
//		AmountList.add("100");
//		
////		createBill("paper","",AmountList,ClientList);
////		System.out.println("老");
////		Dao.print("Bill");
////		Dao.print("Client");
//
//		deleteBill("13");
//		deleteBill("13");
////		Dao.print("Bill");
////		Dao.print("Client");
	}
}