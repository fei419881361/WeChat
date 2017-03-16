package WEIXINSERVERLET;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.sun.xml.internal.bind.v2.model.core.ID;

import CHEKUTILS.ChekUtil;
import CHEKUTILS.MessageUtil;
import CHEKUTILS.ScanCodeInfo;
import CHEKUTILS.ScanMessage;
import CHEKUTILS.TextMessage;
import DB_Manager.BookInfo;
import DB_Manager.ShowDBinfo;
import DB_Manager.getUserInfo;
import WeiXinUtil.Access_token;
import WeiXinUtil.WeiXinUtils;
import net.sf.json.JSONObject;


public class WeixinSevlet extends HttpServlet{
	public static String ManagerID = "oPc31vlYsyXGUfgNTRP4n05_1VwA";

	@Override
	public String getInitParameter(String name) {
		// TODO Auto-generated method stub
		return super.getInitParameter(name);
	}
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		//System.out.println(getInitParameter("appid"));
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("------ing-------");
		log(getServletInfo());
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		//System.out.println(signature+"-"+timestamp+"-"+nonce);
		ChekUtil chekUtil = new ChekUtil();
		PrintWriter writer = resp.getWriter();
		if(chekUtil.Cheksignature(signature, timestamp, nonce)){
			writer.print(echostr);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		log(req.getContentType());
		Map<String, String> map;
		
		System.out.println("req��Ϣ��"+req.toString());
			map = MessageUtil.xmlTomap(req);
			String FromUserName = map.get("FromUserName");
			System.out.println("first fromusername ="+FromUserName);
			String ToUserName = map.get("ToUserName");
			String CreateTime = map.get("CreateTime");
			String MsgType = map.get("MsgType");
			String Content = map.get("Content");
			String MsgId = map.get("MsgId");
			
		System.out.println(MsgType);
		
		
		
		if(MessageUtil.MESSAGE_TEXT.equals(MsgType)){
			
			if(Content.equals("1")||Content.equals("2")){
				String msString = MessageUtil.function(FromUserName, ToUserName);
				System.out.println(msString);
				out.print(msString);
			}
			else if(Content.equals("3")){
				//String mString = MessageUtil.initNewsMessage(FromUserName, ToUserName);
				
				String mString = getUserInfo.getNickName(FromUserName);
				String message = MessageUtil.Duihua(FromUserName, ToUserName,"����ǳ��ǣ�"+mString);
				out.print(message);
				
			}
//			else if (Content.equals("99")) {
//				System.out.println("99");
//				String message = MessageUtil.returnPicMessage(FromUserName, ToUserName, "u1y2HaZkqWVctf_Ecaoh8E80RVf_wXaqY96rl-P3yefaYaTNk_dEUniySfEIZuoy");
//				System.out.println(message);
//				out.print(message);
//			} 
				
			
			else {
			
				String mString = null;
				mString = MessageUtil.Duihua(FromUserName, ToUserName, Content);
				System.out.println(mString);
				out.print(mString);
			}
			
		}
		
		else if(MessageUtil.MESSAGE_EVENT.equals(MsgType)){	
			String type = map.get("Event");
			if(type.equals(MessageUtil.MESSAGE_EVENT_SUBSCRIBE)){
				String mString = MessageUtil.menuText(FromUserName, ToUserName);
				out.print(mString);
			}
			else if("scancode_waitmsg".equals(type)){
				String key = map.get("EventKey");
				if("btn_2".equals(key)){
					//������߼�
					System.out.println("click---");
					String result = "--";
					result = map.get("ScanResult");
					System.out.println(result);
					String condition = "WHERE book_name = 'xxx';";
					condition = condition.replace("xxx", result);
					if(ShowDBinfo.CheakBook(condition,"ising")){
						String From = getUserInfo.getNickName(map.get("FromUserName"));
						ShowDBinfo.ChangeCondition(From, result);
						String mString = MessageUtil.Duihua(FromUserName, ToUserName, "��ϲ��ɹ����ĵ��ˣ�"+result);
						out.print(mString);
					}else {
						
						String now_user = "";
						String con = " book_name = 'xxx';";
						con = con.replace("xxx", result);
						now_user = ShowDBinfo.getNowUser(con, "now_user");
					//	System.out.println("--"+now_user);
						String mString = MessageUtil.Duihua(FromUserName,ToUserName,"Sorry~���鲻�ܱ�������");
						out.print(mString);
					}
				}else if("btn_3".equals(key)){
					//������߼�
					String result = map.get("ScanResult");
					String name = getUserInfo.getNickName(map.get("FromUserName"));
					
					System.out.println("����-"+result);//��ӡ����
					//-----�����ȶ�-------
					if(ShowDBinfo.returnBook(name, result)){
						String mString = MessageUtil.Duihua(FromUserName, ToUserName, "���Ѿ��ɹ��Ĺ黹�ˣ�"+result+"��ŵ�ʵ���ҹ̶�λ��Ŷ~�����ù���Աȷ�Ϲ黹��");
						out.print(mString);
					}else {
						String mString = MessageUtil.Duihua(FromUserName, ToUserName, "����ʧ��-��-���������Ա��ϵ");
						out.print(mString);
					}
				}
				//����Աȷ��
				else if("btn_4".equals(key)){
					String user = map.get("FromUserName");
					if(ManagerID.equals(user)){
						String result = map.get("ScanResult"); //�õ�ɨ���鼮����
						String condition = "WHERE book_name = 'xxx';";
						condition = condition.replace("xxx", result);
						//ȷ���鼮�� ȷ�Ϲ黹״̬
						if(ShowDBinfo.isReturn(condition, "ising")){
							ShowDBinfo.ManagerConfirm(result);
							String mString = MessageUtil.Duihua(FromUserName, ToUserName, "����Աȷ�����");
							out.print(mString);
						}
						else {
							String mString = MessageUtil.Duihua(FromUserName, ToUserName, "����Ա����ʧ��");
							out.print(mString);
						}
						
					}else{
						String mString = MessageUtil.Duihua(FromUserName, ToUserName, "�޹���ԱȨ��");
						out.print(mString);
					}
				}
			}
			else if("CLICK".equals(type)){	
				String key = map.get("EventKey");
				if("btn_1".equals(key)){
					//2017-02-27 17:12:23
					String mString="";
					List<BookInfo> books = new ArrayList<BookInfo>();
					books = ShowDBinfo.getBooks();
					for(int i=0;i<books.size();i++){
						BookInfo bookInfo = new BookInfo();
						bookInfo = books.get(i);
						mString = mString+bookInfo.getId()+" "+bookInfo.getBook_name()+" "+bookInfo.getIsing()+" "+bookInfo.getNow_user()+" "+bookInfo.getRecent_user()+"\n";
						
					}
					String mString2 = MessageUtil.Duihua(FromUserName, ToUserName,"\n"+mString);
					out.print(mString2);
				}
			}
		}
		
		
			
	}

}
