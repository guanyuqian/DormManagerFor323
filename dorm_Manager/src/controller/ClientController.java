package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.mysql.jdbc.PreparedStatement;

import model.*;

	public class ClientController 
	{
		public ClientController(){
		Dao.connSQL();
		}
		//用户列表
		Set <Client>ClientList=new HashSet<Client>();
		
		//判断 是否重名(此处是private)
		private static boolean isRename(String name)
		{
			String sql="SELECT clientId FROM client where clientName = '"+name+"'";
			System.out.println(sql);
			try {
				Dao.statement = Dao.conn.prepareStatement(sql);
				ResultSet rss = Dao.statement.executeQuery(sql);
				rss.next();
				System.out.println("rss:"+rss.getString(1));
				System.out.println("创建失败"+name+"已经存在！");
				return true;
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
			}
			return false;
		}
		
		
		//增加客户操作   客户名（String） 宿舍（String） 初始充值金额（String）
		public boolean add(String name,String dorm,String balance) 				//??????????
		{
			/*String sql="SELECT clientId FROM client where clientName = '"+name+"'";
			System.out.println(sql);
			try {
				Dao.statement = Dao.getConn().prepareStatement(sql);
				ResultSet rss = Dao.statement.executeQuery(sql);
				rss.next();
				System.out.println("rss:"+rss.getString(1));
				System.out.println("创建失败"+name+"已经存在！");
				return false;
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
			}*/
			if(isRename(name)==true)return false;
			
			//Client client=new Client();
		//	client.setClientName(name);
		//	client.setDorm(dorm);
		//	client.setClientBalance(Integer.parseInt(balance));
		//	ClientList.add(client);
			//client.setClientId(ClientList.size()+1);
		
			
			//在Client表中创建用户
			String sql1 = "insert into Client(clientName,dorm,clientBalance,deleteFlag) "
					+ "values('"+name+"','"+dorm+"','"+balance+"','0')";
			System.out.println("执行："+sql1);
			Dao.insertSQL(sql1);
			Dao.print("Client");
			
			//获得创建用户的Id
			String clientIdSql = "SELECT LAST_INSERT_ID() from client;";
			String ClientId = null;
			try {
				Dao.statement =  (PreparedStatement) Dao.conn.prepareStatement(clientIdSql);
				ResultSet rs = Dao.statement.executeQuery(clientIdSql);
				rs.next();
				
				ClientId=rs.getString(1);	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//创建充值记录
			String time =Dao.df.format(new Date());
			String note="充值";
			String sql2 = "insert into bill(billName,billDate,notes,allClientAmount,allClentId,totalmoney,deleteFlag) "
					+ "values('"+note+"','"+time+"','新建用户初始金额','"+balance+"','"+ClientId+"',"+balance+",'0')";
			
			System.out.println("执行："+sql2);
			Dao.insertSQL(sql2);
			Dao.print("Bill");
			
			String billIdSql = "SELECT LAST_INSERT_ID() from bill;";
			String billId = null;
			try {
				Dao.statement = (PreparedStatement) Dao.conn.prepareStatement(billIdSql);
				ResultSet rs = Dao.statement.executeQuery(billIdSql);
				rs.next();	
				billId=rs.getString(1);	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String sql3 = "insert into personalAmount(billId,clientId,money,deleteFlag) "
					+ "values('"+billId+"','"+ClientId+"','"+balance+"','0')";
			
			System.out.println("执行："+sql3);
			Dao.insertSQL(sql3);
			Dao.print("PersonalAmount");
			return true;
		}
		
	/*	 
	    String insert = "insert into Client(clientName,dorm,clientBalance,deleteFlag) "
				+ "values('sam','323A','100','0')";
	    String update = "update Client set clientBalance ='20000' where id= '4'";
	    String delete = "update Client set deleteFlag ='1' where id= '5'";	*/
		
		//删除用户   用户Id（String）
		public boolean delete(String clientId)
		{
			String delete_client="update Client set deleteFlag='1' where clientid='"+clientId+"'";
			System.out.println("删除"+delete_client);
			return Dao.deleteSQL(delete_client);
			
		}
		
		//修改用户    用户ID（String） 更改的用户名（String）
		public boolean update(String id,String name_to_change)
		{
			if(isRename(name_to_change)==true) return false;
			
			String update_client = "update Client set clientname='"+name_to_change+"' where clientid= '"+id+"'";
			return Dao.updateSQL(update_client);
		}
		
		public static boolean update_Amount(String id,String amount)
		{
			String select_client = "Select ClientBalance from client where clientId='"+id+"'";		
			ResultSet rs;
			String balance = "";
			rs=Dao.selectSQL(select_client);
			try {
				rs.next();
				System.out.println("获取成功");
				balance=rs.getString(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			balance=""+(Double.parseDouble(balance)+Double.parseDouble(amount));
			
			String update_balance = "update Client set clientbalance='"+balance+"' where clientid= '"+id+"'";
			return Dao.updateSQL(update_balance);
			
			
		}
		
		static public ArrayList getLowMoney(){
			ArrayList rs=Dao.loadData("CLIENT"," CLIENTNAME,CLIENTBALANCE ","  WHERE(DELETEFLAG = 0 AND CLIENTBALANCE <= 10)");
		
			return rs;
			
		}
		static public float getAllMoney(){
			String sql="SELECT CLIENTBALANCE FROM CLIENT WHERE DELETEFLAG = 0";
			if(!Dao.connSQL()){
				System.err.println("数据库连接失败");
			};
			ResultSet rs=Dao.selectSQL(sql);
			float totalmoney=0;
			try{
				while(rs.next()){
				totalmoney+=Float.parseFloat(rs.getString(1));
				}
			}catch(Exception ex){
				totalmoney=0;
				System.err.println(sql+"执行失败（找不到此数据）");
				//ex.printStackTrace();
			}
			return totalmoney;
			
		}
		static public void main(String[] args){
			Dao.connSQL();
			/*ClientController cc=new ClientController();
			cc.add("gasJNoduhj", "323A,a","10000000");
//			cc.delete(1);
			Dao.print("Client");
			Dao.print("Client");
			*/
		}
		
		
		
	}

