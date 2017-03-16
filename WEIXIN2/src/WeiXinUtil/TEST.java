
package WeiXinUtil;

import java.util.ArrayList;
import java.util.List;

import DB_Manager.BookInfo;
import DB_Manager.ShowDBinfo;
import DB_Manager.getUserInfo;
import net.sf.json.JSONObject;

public class TEST {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Access_token access_token = new Access_token();
		access_token = WeiXinUtils.geToken();
		System.out.println("token:"+access_token.getAccess_token()+"expires_in:"+access_token.getExpires_in());
		
		
		String menu = JSONObject.fromObject(WeiXinUtils.initMenu()).toString();
		
		int i = WeiXinUtils.creatMenu(access_token.getAccess_token(), menu);
		System.out.println(menu);
		if(i == 0)
			System.out.println("success");
		else {
			System.out.println(i);
		}
		
//		JSONObject jsonObject =  WeiXinUtils.initErweima(access_token.getAccess_token());
//		String ticket = (String) jsonObject.get("ticket");
//		System.out.println(jsonObject.get("url"));
//		System.out.println(jsonObject.get("ticket"));
//		System.out.println(jsonObject.get("errcode"));
		//url:http://weixin.qq.com/q/02J552NSZdbR-10000M03r
		//ticket:gQH98TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAySjU1Mk5TWmRiUi0xMDAwME0wM3IAAgS9LLJYAwQAAAAA
	

		
		
		
	}

}
