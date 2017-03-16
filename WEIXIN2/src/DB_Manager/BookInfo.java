package DB_Manager;
//书籍的信息类
public class BookInfo {

	private int id;
	private String book_name;
	private int ising;
	private String recent_user;
	private String now_user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public int getIsing() {
		return ising;
	}
	public void setIsing(int ising) {
		this.ising = ising;
	}
	public String getRecent_user() {
		return recent_user;
	}
	public void setRecent_user(String recent_user) {
		this.recent_user = recent_user;
	}
	public String getNow_user() {
		return now_user;
	}
	public void setNow_user(String now_user) {
		this.now_user = now_user;
	}
}
