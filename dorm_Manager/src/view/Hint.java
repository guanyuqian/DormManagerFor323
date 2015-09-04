package view;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.mysql.jdbc.ResultSetMetaData;

public class Hint {
	/**
	  * 判断字符串是否是整数
	  */
	 public static boolean isInteger(String value) {
	  try {
	   Integer.parseInt(value);
	   return true;
	  } catch (NumberFormatException e) {
	   return false;
	  }
	 }

	 /**
	  * 判断字符串是否是浮点数
	  */
	 public static boolean isDouble(String value) {
	  try {
	   Double.parseDouble(value);
	   if (value.contains("."))
	    return true;
	   return false;
	  } catch (NumberFormatException e) {
	   return false;
	  }
	 }

	 /**
	  * 判断字符串是否是数字
	  */
	 public static boolean isNumber(String value) {
	  return isInteger(value) || isDouble(value);
	 }

	 /**
	  * 设置列宽
	  * @param myTable
	  */
	   
	 static public void FitTableColumns(JTable myTable) {               //設置table的列寬隨內容調整
		    JTableHeader header = myTable.getTableHeader();
		    int rowCount = myTable.getRowCount();
		    Enumeration columns = myTable.getColumnModel().getColumns();
		    while (columns.hasMoreElements()) {
		        TableColumn column = (TableColumn) columns.nextElement();
		        int col = header.getColumnModel().getColumnIndex(
		                column.getIdentifier());
		        int width = (int) myTable.getTableHeader().getDefaultRenderer()
		                .getTableCellRendererComponent(myTable,
		                        column.getIdentifier(), false, false, -1, col)
		                .getPreferredSize().getWidth();
		        for (int row = 0; row < rowCount; row++) {
		            int preferedWidth = (int) myTable.getCellRenderer(row, col)
		                    .getTableCellRendererComponent(myTable,
		                            myTable.getValueAt(row, col), false, false,
		                            row, col).getPreferredSize().getWidth();
		            width = Math.max(width, preferedWidth);
		        }
		        header.setResizingColumn(column);
		        column.setWidth(width + myTable.getIntercellSpacing().width);
		    }
		}
}
