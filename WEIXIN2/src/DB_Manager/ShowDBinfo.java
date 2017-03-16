package DB_Manager;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Connection;

public class ShowDBinfo {
	static String URL = "jdbc:mysql://localhost:3306/t1";
	static String user = "root";
	static String password = "748596";
	static Connection connection = null;
	private static List<BookInfo> books;
	
	public static List<BookInfo> getBooks(){
		books = UseDB.getList(connection, books);
		return books;
	}
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection(URL, user, password);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//CheakBook  «ºÏ≤È «∑ÒΩË‘ƒ
	public static boolean CheakBook(String condition,String needColname){
		if(UseDB.CheekDB(connection, "MyBook", condition, needColname)){
			return true;
		}
		else {
			return false;
		}
	}
	public static boolean isReturn(String condition,String needColname){
		if(UseDB.isReturn(connection, "MyBook", condition, needColname)){
			return true;
		}
		else {
			return false;
		}
	}
	public static String getNowUser(String condition,String needColname){
		String username = UseDB.showData(connection, "MyBook", condition, needColname);
		return username;
	}
	public static void ChangeCondition(String FromName,String bookname){
		
		String condition = "book_name = 'xxx';".replace("xxx", bookname);
		//UseDB.changeData(connection, recentuser, "recent_user",condition , "MyBook");
		UseDB.changeData(connection, "1", "ising",condition , "MyBook");
		UseDB.changeData(connection, FromName, "now_user",condition, "MyBook");
	}
	
	public static boolean returnBook(String FromName,String bookname){
		String condition = "book_name = 'xxx';".replace("xxx", bookname);
		String now_name = "";
		now_name = UseDB.showData(connection, "MyBook", condition, "now_user");
		System.out.println("---"+now_name);
		System.out.println("----"+FromName);
		if(now_name.equals(FromName)){
			UseDB.changeData(connection, FromName, "recent_user",condition , "MyBook");
			UseDB.changeData(connection, "2", "ising",condition , "MyBook");
			UseDB.changeData(connection, "null", "now_user",condition, "MyBook");
			return true;
		}
		else {
			return false;
		}	
	}
	public static void ManagerConfirm(String bookname){
		String condition = "book_name = 'xxx';".replace("xxx", bookname);
		UseDB.changeData(connection, "0", "ising",condition , "MyBook");
	}
	
	
}
