package DB_Manager;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import WeiXinUtil.Access_token;
import WeiXinUtil.WeiXinUtils;
import net.sf.json.JSONObject;

public class getUserInfo {
	private static String access_token;
	private static String openid;
	private String lang = "zh_CN";
	private static String URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	private static String URL2 = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	//获取用户信息的方法。
	public static JSONObject doGet(){
		System.out.println("\n oppenid+++"+openid);
		URL = URL.replace("ACCESS_TOKEN", access_token);
		URL = URL.replace("OPENID", openid);
		System.out.println("URL=="+URL);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(URL);
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
	public static String getNickName(String FromUserName){
		String mString = "";
		Access_token access_token = new Access_token();
		access_token = WeiXinUtils.geToken();
		mString = access_token.getAccess_token();
		System.out.println(mString);
		getUserInfo getUserInfo = new getUserInfo();
		getUserInfo.setAccess_token(mString);
		//System.out.println("Fromuser=="+FromUserName);
		getUserInfo.setOpenid(FromUserName);
		System.out.println("openid = "+openid);
		JSONObject jsonObject = doGet();
		System.out.println(jsonObject.toString());
		String nickname = jsonObject.get("nickname").toString();
		System.out.println("nickname=="+jsonObject.get("nickname"));
		URL = URL2;
		return nickname; 
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	
}
