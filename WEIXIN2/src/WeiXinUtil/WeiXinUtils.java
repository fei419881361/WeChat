package WeiXinUtil;





import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import com.sun.org.apache.regexp.internal.recompile;

import WeiXinMenu.Button;
import WeiXinMenu.Button_Click;
import WeiXinMenu.Menu;
import WeiXinerweima.Action_info;
import WeiXinerweima.Myerweima;
import WeiXinerweima.Scene;
import net.sf.json.JSONObject;

public class WeiXinUtils {
	public static String appid = "wx157109197bc8e05e";
	public static String secret = "691e04b0906f163bead24a4064df70d2";
	public static String URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public static String Erweima="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
	public static final String  Menu_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	public static JSONObject doGet(String url){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String result = EntityUtils.toString(entity,"UTF-8");
				jsonObject =JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
		
	}
	public static void getPic(String url){
		File file = new File("f:\\test1.jpg");
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			response.setHeader("content-disposition", "attachment;fileName="+URLEncoder.encode("erweima", "UTF-8"));
			InputStream reader = null;
	        //OutputStream out = null;
	        reader = entity.getContent();
	        int len =0;
	        byte[] data = new byte[1024];
	        while((len = reader.read(data))!=-1){
	        	fileOutputStream .write(data, 0, len);
	        }
	        
	        
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static JSONObject doPost(String url,String outStr){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		
		try {
			httpPost.setEntity(new StringEntity(outStr,"utf-8"));
			CloseableHttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String result = EntityUtils.toString(entity,"UTF-8");
				jsonObject =JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
		
	}
	/***
	 * 获取token
	 * */
	public static Access_token geToken(){
		Access_token access_token = new Access_token();
		
		String url = URL.replace("APPID", appid).replace("APPSECRET", secret);
		JSONObject jsonObject = null;
		jsonObject = WeiXinUtils.doGet(url);
		if(jsonObject!=null){
			access_token.setAccess_token(jsonObject.getString("access_token"));
			access_token.setExpires_in(jsonObject.getInt("expires_in"));
		}
		
		return access_token;
	}
	/***
	 * 上传临时素材
	 * */
	public static String upData(String url,String filepath,String type){
		File file =  new File(filepath);
		
		
		
		try {
			java.net.URL mUrl = new java.net.URL(url);
			HttpURLConnection con = (HttpURLConnection) mUrl.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			
			//设置请求头信息
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");

			//设置边界
			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");

			byte[] head = sb.toString().getBytes("utf-8");

			//获得输出流
			OutputStream out = new DataOutputStream(con.getOutputStream());
			//输出表头
			out.write(head);

			//文件正文部分
			//把文件已流文件的方式 推入到url中
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();

			//结尾部分
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

			out.write(foot);

			out.flush();
			out.close();

			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = null;
			String result = null;
			try {
				//定义BufferedReader输入流来读取URL的响应
				reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String line = null;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				if (result == null) {
					result = buffer.toString();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					reader.close();
				}
			}

			JSONObject jsonObj = JSONObject.fromObject(result);
			System.out.println(jsonObj);
			String typeName = "media_id";
			if(!"image".equals(type)){
				typeName = type + "_media_id";
			}
			String mediaId = jsonObj.getString(typeName);
			return mediaId;
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 初始化菜单
	 * */
	public static Menu initMenu(){
		Menu menu = new Menu();
		
		Button_Click button_Click = new Button_Click();
		button_Click.setName("查看所有图书");
		button_Click.setKey("btn_1");
		button_Click.setType("click");
		
		Button_Click button_Click2 = new Button_Click();
		button_Click2.setName("我要借书");
		button_Click2.setKey("btn_2");
		button_Click2.setType("scancode_waitmsg");
		
		Button_Click button_Click3 = new Button_Click();
		button_Click3.setName("我要还书");
		button_Click3.setKey("btn_3");
		button_Click3.setType("scancode_waitmsg");
		
		Button_Click button_Click4 = new Button_Click();
		button_Click4.setName("管理员确认");
		button_Click4.setKey("btn_4");
		button_Click4.setType("scancode_waitmsg");
		Button[] suButtons = new Button[4];
		suButtons[0] = button_Click;
		suButtons[1] = button_Click2;
		suButtons[2] = button_Click3;
		suButtons[3] = button_Click4;
		Button_Click button_Click5 = new Button_Click();
		button_Click5.setName("实验室图书");
		button_Click5.setSub_button(suButtons);//将二级菜单装入一级菜单
		//装载 按钮的数组
		Button[] buttons = new Button[1];
		
		buttons[0] = button_Click5;
		
		
		menu.setButtons(buttons);
		return menu;
			
	}
	
	public static int creatMenu(String token, String menu){
		int i = 0;
		String url = Menu_URL;
		url = url.replace("ACCESS_TOKEN", token);
		JSONObject  jsonObject = doPost(url, menu);
		if(jsonObject!=null){
			i = jsonObject.getInt("errcode");
			
		}
		return i;
	}
	//创建带参数的二维码
	public static JSONObject initErweima(String token){
		Myerweima myerweima = new Myerweima();
		
		myerweima.setAction_name("nihao");
		Action_info action_info = new Action_info();
		Scene scene = new Scene();
		scene.setScene_id("1000");
		action_info.setScene(scene);
		myerweima.setAction_info(action_info);
		JSONObject jsonObject = new JSONObject();
		jsonObject = JSONObject.fromObject(myerweima);
		System.out.println(jsonObject.toString());
		String url = Erweima.replace("TOKEN", token);
		//此处注意JSON格式。。后续修改。。
		JSONObject jsonObject2 = doPost(url,"{\"action_name\":\"QR_LIMIT_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":12}}}");
		
		return jsonObject2;
		
	}
	public static void creatErweima(String ticket,String url){
		String mticket = java.net.URLEncoder.encode(ticket);
		
		url = url.replace("TICKET", mticket);
		getPic(url);
		
		
		
	}
	
	
}
