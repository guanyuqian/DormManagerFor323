package controller;
import java.security.Timestamp;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
 
public class Dao {
	static public DateFormat   df   =   new   SimpleDateFormat( "yyyy-MM-dd   HH:mm:ss ");  
    public static Connection conn = null;
    static PreparedStatement statement = null;
    static String mysqlDriver = "com.mysql.jdbc.Driver";
    static String newUrl = "jdbc:mysql://localhost:3306/";
    static String username = "root";
    static String password = "root";
	static String url = "jdbc:mysql://localhost:3306/dormmanager?characterEncoding=UTF-8"; // 数据库地址，端口，数据库名称，字符集
	static public String buildClientTable(){
		return "create table Client ( "+
			      "clientId INT(20) not null AUTO_INCREMENT," + 
			      "clientName varchar(50) not null ,"+ 
			      "dorm varchar(50) not null ,"+ 
			      "clientBalance float(10, 2)  Not null ,"+
			      "deleteFlag TINYINT  NOT NULL ,"+
			      "primary key (clientId)) utf8 collate utf8_general_ci;";
	}
	static public String buildPersonalAmountTable(){
		return "create table personalAmount( "+
	      		  "personalAmountId INT(20) not null AUTO_INCREMENT," + 
	    	      "billId INT(20) not null ,"+ 
	    	      "clientId INT(20) not null ,"+ 
	    	      "money float(10, 2) not null ,"+ 
	    	      "deleteFlag TINYINT  NOT NULL ,"+
	    	      "primary key (personalAmountId)) utf8 collate utf8_general_ci;";
	}
	static public String buildBillTable(){
		return "create table bill( "+
	      		  "billId INT(20) not null AUTO_INCREMENT," + 
	      		  "billName varchar(50) not null ,"+ 
	      		 "billDate datetime not null ,"+
	      		 "notes varchar(50)  null ,"+ 
	      		 "allClentId varchar(50) not null ,"+ 
	      		 "allClientAmount varchar(50) not null ,"+ 
	      		 "totalMoney float(10, 2) not null ,"+ 
	    	      "deleteFlag TINYINT  NOT NULL ,"+
	    	      "primary key (billId)) utf8 collate utf8_general_ci;";
	}
	static public boolean buildClientTable(String database) {	
    Connection newConn = null;
    try {
    Class.forName(mysqlDriver);
    } catch (ClassNotFoundException e) {
  	  e.printStackTrace();
    	  return false;
    // TODO Auto-generated catch block
    
    }
      try {
      String tableSql = buildClientTable();
      String personalAmountTable = buildPersonalAmountTable();
      String billTable = buildBillTable();
      String databaseSql = "create database " + database;
     
      conn = DriverManager.getConnection(newUrl, username, password);
      Statement smt = conn.createStatement();
      if (conn != null) {
   	   System.out.println("数据库连接成功!");
   	  smt.executeUpdate(databaseSql);
   	 newConn = DriverManager.getConnection(newUrl + database,
   	  username, password);
     System.out.println("newConn" + newConn);
     System.out.println(newUrl + database);
     
     System.out.println(tableSql);
     System.out.println(personalAmountTable);
   	  if (newConn != null) {
   	  System.out.println("已经连接到新创建的数据库：" + database);
   	  Statement newSmt = newConn.createStatement();
    	 int i = newSmt.executeUpdate(tableSql)*newSmt.executeUpdate(personalAmountTable)
    			 *newSmt.executeUpdate(billTable);//DDL语句返回值为0;
    	 System.out.println(i);
      	  if (i == 0) {
      	  System.out.println(tableSql + "表已经创建成功!");
      	  }
         }
         }
        
         } catch (SQLException e1) {
         // TODO Auto-generated catch block
       	  e1.printStackTrace();
       	  return false;
       
         }
      
      return true;
      }
    
    
    public static boolean connSQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); // 加载驱动，必须导入包mysql-connector-java-5.1.6-bin.jar
            conn = DriverManager.getConnection(url, username, password);
        }
        // 捕获加载驱动程序异常
        catch (ClassNotFoundException cnfex) {
            System.err.println("装载 JDBC/ODBC 驱动程序失败。");
            cnfex.printStackTrace();
            return false;
        }
        // 捕获连接数据库异常
        catch (SQLException sqlex) {
            System.err.println("无法连接数据库");
            sqlex.printStackTrace();
            return false;
        }
        return true;
    }
 
    // 关闭数据库
    void deconnSQL() {
        try {
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            System.out.println("关闭数据库异常：");
            e.printStackTrace();
        }
    }
 
    /**
     * 执行查询sql语句
     *
     * @param sql
     * @return
     */
    public static ResultSet selectSQL(String sql) {
        ResultSet rs = null;
        try {
        	System.out.println("执行selectSQL:"+sql);
            statement = conn.prepareStatement(sql);
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
        	System.err.println(sql+"执行失败（数据库连接失败）");
            //e.printStackTrace();
            return null;
        }
        catch (Exception e) {
        	System.err.println(sql+"执行失败（数据库连接失败）");
            //e.printStackTrace();
            return null;
        }
        return rs;
    }
 
    /**
     * 执行插入sql语句
     *
     * @param String clientName,String dorm,String (int)clientBalance,String (int)deleteFlag
     * 
     * @return
     */
    static boolean insertSQL(String sql) {
        System.out.println("执行insertSQL:"+sql);
        try {
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("插入数据库时出错：");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("插入时出错：");
            e.printStackTrace();
        }
        return false;
    }
 
    /**
     * 执行删除sql语句
     *
     * @param sql
     * @return
     */
    static boolean deleteSQL(String sql) {
        try {
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("删除数据库时出错：");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("删除时出错：");
            e.printStackTrace();
        }
        return false;
    }
 
    /**
     * 执行插入sql语句
     *
     * 
     * 
     * @return
     */
    static boolean updateSQL(String sql) {
        System.out.println("执行:"+sql);
        try {
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("更新数据库时出错：");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("更新时出错：");
            e.printStackTrace();
        }
        return false;
    }
 
    /**
     * 打印结果集
     *
     * 具体列根据自己的数据库表结构更改
     *
     * @param rs
     */
    static void print(String Table) {
    	String sql="select * from "+Table;
        ResultSet resultSet = selectSQL(sql);
        System.out.println("-----------------");
        System.out.println("查询结果:");
        System.out.println("-----------------");
        String name="SELECT column_name from information_schema.columns WHERE  table_name = '"+Table+"'";
        try {
            statement = conn.prepareStatement(name);
			ResultSet rs = statement.executeQuery(name);
			while (rs.next()) {
                System.out.print( rs.getString(1)+"|");
            }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
        	System.out.println("");
            while (resultSet.next()) {
            	String data;
            	int i=0;
            	data= resultSet.getString(++i);
            	do{
            		System.out.print( data+"|");
            		try{
            		data= resultSet.getString(++i);
            		}catch(Exception e){
            			data=null;
            		}
            	}while(data!=null);
            	System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("显示时数据库出错。");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("显示出错。");
            e.printStackTrace();
        }
    }
 
    public static  String[] getFieldName(String tableName){
    	ArrayList<String> Fields = new ArrayList<String> ();
    	int i=0;
    	String name="SELECT column_name from information_schema.columns WHERE  table_name = '"+tableName+"'";
        try {
        	System.out.println(name);
            statement = conn.prepareStatement(name);
			ResultSet rs = statement.executeQuery(name);
			while (rs.next()) {
                System.out.print( rs.getString(1)+"|");
                Fields.add(rs.getString(1));
            }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        String[] result=(String [])Fields.toArray(new String[Fields.size()]);
    	return result;
    }
    public static String[] getFieldNameForTable(String tableName){
    	
		switch(tableName.toUpperCase()){
		case "CLIENT":String[] clientresult= {"ID","名称","宿舍","余额","状态"};
		return clientresult;
		case "BILL":String[] billresult= {"ID","名称","消费用户","消费分金额","消费总金额","备注","录入时间","状态"};
		return billresult;
		case "PERSONALAMOUNT":String[] result= {"ID","表单","消费用户","消费金额","状态"};
		return result;
		}
    	return null;
    	
    }
    public static void main(String args[]) {
 
       Dao dao = new Dao();
//        Dao.buildClientTable("DormManager");
        Dao.connSQL();
       
       /* String personalAmountTable = "create table personalAmount( "+
        		  "personalAmountId INT(20) not null AUTO_INCREMENT," + 
      	      "billId INT(20) not null ,"+ 
      	      "clientId INT(20) not null ,"+ 
      	      "money INT(20) not null ,"+ 
      	      "deleteFlag TINYINT  NOT NULL ,"+
      	      "primary key (personalAmountId));";
        String billTable = "create table bill( "+
        		  "billId INT(20) not null AUTO_INCREMENT," + 
        		  "billName varchar(50) not null ,"+ 
        		 "billDate datetime not null ,"+
        		 "notes varchar(50)  null ,"+ 
        		 "allClentId varchar(50) not null ,"+ 
        		 "allClientAmount varchar(50) not null ,"+ 
      	      "deleteFlag TINYINT  NOT NULL ,"+
      	      "primary key (billId));";*/
      
//        System.out.println(df.format(new Date()));   
//        String time =df.format(new Date());    
        
        
        String s = "select * from Client";
        
//        String insert = "insert into personalAmount(billId,clientId,money,deleteFlag) "
//				+ "values('1','2','-10','0'),('1','3','-20','0'),('1','4','-30','0');";
//        
//        String insert = "insert into bill(billName,billDate,allClientAmount,allClentId,deleteFlag) "
//				+ "values('扣钱','"+time+"','-10,-20,-30','2,3,4','0')";
//        
//        String insert = "insert into Client(clientName,dorm,clientBalance,deleteFlag) "
//				+ "values('傅振寰','323A','0','0')";
//       String update = "update  bill set allClientAmount ='100,100,100,100'";
//        String update = "update Client set clientBalance =clientBalance-30 where clientid= '4'";
//        String insert ="alter table bill add totalMoney INT  Null after allClientAmount;";
//        String insert ="ALTER TABLE bill MODIFY  totalMoney INT NOT NULL;  ";
    /*  if (dao.insertSQL(insert) == true) {
            System.out.println("插入成功");
            ResultSet resultSet = dao.selectSQL(s);
            dao.print(resultSet);
        }*/
    /*  if (dao.updateSQL(update) == true) {
            System.out.println("更新成功");
            ResultSet resultSet = dao.selectSQL(s);
            dao.print(resultSet);
        }*/
//        dao.print("client");
    /* 	if (dao.deleteSQL(delete) == true) {
            System.out.println("删除成功");
            ResultSet resultSet = dao.selectSQL(s);
            dao.print(resultSet);
        }*/
 
        dao.deconnSQL(); // 关闭数据库连接
    }
    static public ArrayList loadData(String table){
    	 System.out.println(" loadData(String table);");
    	String sql = "select * from "+table+" order by deleteFlag";
    	if(table.toUpperCase().equals("BILL")){
    		sql=sql+" ,BILLDATE   DESC";
    	}
    	if(table.toUpperCase().equals("CLIENT")){
    		sql=sql+" ,CLIENTID   DESC";
    	}
    	if(table.toUpperCase().equals("PERSONALAMOUNT")){
    		sql=sql+" ,personalAmountId   DESC";
    	}
    
    	ResultSet rs=selectSQL(sql);
    	ArrayList list = new ArrayList();

    	ResultSetMetaData md;
    	if(rs==null){
      		System.err.println(sql+"执行失败（找不到此数据）");
      		return list;
      	}
		try {
			md = rs.getMetaData();
	

    	int columnCount = md.getColumnCount(); //Map rowData;

    	while (rs.next()) { //rowData = new HashMap(columnCount);

    	Map rowData = new HashMap();

    	for (int i = 1; i <= columnCount; i++) {

    	rowData.put(md.getColumnName(i), rs.getObject(i));

    	}

    	list.add(rowData);

    	} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           System.out.println(list);
		return list;
    }
	
    static public ArrayList loadData(String table,String field){
   	 System.out.println(" loadData(String table);");
   	String s = "select "+field+" from "+table+" order by deleteFlag;";
   	ResultSet rs=selectSQL(s);
   	ArrayList list = new ArrayList();

   	ResultSetMetaData md;
		try {
			md = rs.getMetaData();
	

   	int columnCount = md.getColumnCount(); //Map rowData;

   	while (rs.next()) { //rowData = new HashMap(columnCount);

   	Map rowData = new HashMap();

   	for (int i = 1; i <= columnCount; i++) {

   	rowData.put(md.getColumnName(i), rs.getObject(i));

   	}

   	list.add(rowData);

   	} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          System.out.println(list);
		return list;
   }
    static public ArrayList loadData(String table,String field,String Condition){
      	 System.out.println(" loadData(String table);");
      	String sql = "select "+field+" from "+table+" "+Condition+" order by "+table+".deleteFlag;";
      	ResultSet rs=selectSQL(sql);
      	ArrayList list = new ArrayList();

      	ResultSetMetaData md;
      	if(rs==null){
      		System.err.println(sql+"执行失败（找不到此数据）");
      		return list;
      	}
   		try {
   			md = rs.getMetaData();
      	int columnCount = md.getColumnCount(); //Map rowData;

      	while (rs.next()) { //rowData = new HashMap(columnCount);

      	Map rowData = new HashMap();

      	for (int i = 1; i <= columnCount; i++) {

      	rowData.put(md.getColumnName(i), rs.getObject(i));

      	}

      	list.add(rowData);

      	} 
   		} catch (SQLException e) {
   			// TODO Auto-generated catch block
   			System.err.println(sql+"执行失败（找不到此数据）");
   			//e.printStackTrace();
   		}
   		return list;
      }
}

