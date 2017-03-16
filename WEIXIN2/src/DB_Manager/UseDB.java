package DB_Manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class UseDB {

	public static void addData(Connection con,String addSTR,String mtab_name){
		try {
			Statement statement = (Statement) con.createStatement();
			String sqldata = "INSERT INTO tab_name VALUES('mvalues')";
			sqldata = sqldata.replace("mvalues", addSTR).replace("tab_name", mtab_name);
			
			int i = statement.executeUpdate(sqldata);
			System.out.println("i = "+i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleData(Connection con,String conditon,String mtab_name){
		try {
			Statement statement = (Statement) con.createStatement();
			String sqldata = "DELETE FROM tab_name WHERE "+conditon;
			sqldata = sqldata.replace("tab_name", mtab_name);
			System.out.println(sqldata);
			int i = statement.executeUpdate(sqldata);
			System.out.println("i = "+i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//changename = 改变后的值; changerowname = 改变列的列名
	public static void changeData(Connection con,String changename,String changeRowName,String condition,String mtab_name){
		try {
			Statement statement = (Statement) con.createStatement();
			String sqldata = "UPDATE tab_name SET rowname = 'changename' WHERE "+condition;
			sqldata = sqldata.replace("rowname",changeRowName ).replace("changename", changename).replace("tab_name", mtab_name);
			System.out.println(sqldata);
			int i = statement.executeUpdate(sqldata);
			
			System.out.println("i = "+i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String showData(Connection con,String tab_name,String Condition,String col_name){
		Statement statement;
		try {
			statement = (Statement) con.createStatement();
			String sqldata = "SELECT XX FROM tab_name WHERE "+Condition;
			sqldata = sqldata.replace("XX", col_name).replace("tab_name", tab_name);
			System.out.println(sqldata);
			ResultSet resultSet = (ResultSet) statement.executeQuery(sqldata);
			String buf = "";
			while(resultSet.next()){
				buf = buf+resultSet.getString(col_name);
			}
			return buf;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";
		
	}
	
	public static List<BookInfo> getList(Connection connection,List<BookInfo> books){
		books = new ArrayList<BookInfo>();
		try {
			Statement statement = (Statement) connection.createStatement();
			ResultSet resultSet = (ResultSet) statement.executeQuery("SELECT * FROM MyBook;");
			while(resultSet.next()){
				BookInfo bookInfo = new BookInfo();
				bookInfo.setId(resultSet.getInt("id"));
				bookInfo.setBook_name(resultSet.getString("book_name"));
				bookInfo.setIsing(resultSet.getInt("ising"));
				bookInfo.setNow_user(resultSet.getString("now_user"));
				bookInfo.setRecent_user(resultSet.getString("recent_user"));
				books.add(bookInfo);
				
			}
			return books;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static Boolean CheekDB(Connection con,String tab_name,String Condition,String col_name){
		try {
			Statement statement = (Statement) con.createStatement();
			String sqldata = "SELECT XX FROM tab_name "+Condition;
			sqldata = sqldata.replace("XX", col_name).replace("tab_name", tab_name);
			System.out.println(sqldata);
			ResultSet resultSet = (ResultSet) statement.executeQuery(sqldata);
			String result = "0";
			while(resultSet.next()){
				result = resultSet.getString(col_name);
				System.out.println("当前书ising:"+result);
			}
			if("0".equals(result)){
				return true;//可借阅状态
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static Boolean isReturn(Connection con,String tab_name,String Condition,String col_name){
		try {
			Statement statement = (Statement) con.createStatement();
			String sqldata = "SELECT XX FROM tab_name "+Condition;
			sqldata = sqldata.replace("XX", col_name).replace("tab_name", tab_name);
			System.out.println(sqldata);
			ResultSet resultSet = (ResultSet) statement.executeQuery(sqldata);
			String result = "2";
			while(resultSet.next()){
				result = resultSet.getString(col_name);
				System.out.println("当前书ising:"+result);
			}
			if("2".equals(result)){
				return true;//可借阅状态
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
}
