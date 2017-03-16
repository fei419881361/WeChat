package CHEKUTILS;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Parameterizable;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

import WeiXinUtil.WeiXinUtils;



public class MessageUtil {
	/***消息类型*/
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_EVENT_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_EVENT_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_EVENT_CLICK = "CLICK";
	public static final String MESSAGE_EVENT_VIEW = "VIEW";
	
	
	
	

	public static Map<String, String> xmlTomap(HttpServletRequest request) {
		Map<String, String>map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();
		InputStream inputStream;
		try {
			inputStream = request.getInputStream();
			Document doc = reader.read(inputStream);
			
			Element root = doc.getRootElement();
			
			List<Element> list =root.elements();
			
//			for(Element e:list){
//				map.put(e.getName(),e.getText());
//				System.out.println(e.getName()+"\n"+e.getText());//打印出XML
//				
//			}
			getEliments(list, map);
			inputStream.close();
			return map;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		return null;	
	}
	private static void getEliments(List<Element> sonEliment,Map< String, String> map){
		for(Element element :sonEliment){
			if(element.elements().size()!=0){
				getEliments(element.elements(), map);
			}else{
				map.put(element.getName(),element.getText());
			}
		}
	}
	
	public static String textmessageToxml(TextMessage textmessage){
		XStream xStream = new XStream();
		xStream.alias("xml", textmessage.getClass());
		return xStream.toXML(textmessage);
	}
	
	public static String menuText(String FromUserName,String ToUserName){
		StringBuffer text = new StringBuffer();
		TextMessage mm = new TextMessage();
		text.append("欢迎来到菜鸟的世界：\n");
		text.append("1.自我介绍\n");
		text.append("2.世界的组成\n");
		 mm = MessageUtil.Pz(FromUserName, ToUserName, text.toString(), MessageUtil.MESSAGE_TEXT);
		 String mString = MessageUtil.textmessageToxml(mm);
		return mString;	
	}
	
	public static String Duihua(String FromUserName,String ToUserName,String Content){
		//Content = Content;
		TextMessage mm = MessageUtil.Pz(FromUserName, ToUserName, Content, MessageUtil.MESSAGE_TEXT);
		String mString = null;
		mString = MessageUtil.textmessageToxml(mm);
		return mString;
	}
	public static String function(String FromUserName,String ToUserName){
		TextMessage mm = Pz(FromUserName, ToUserName, "123454321", MessageUtil.MESSAGE_TEXT);
		String mString = null;
		mString = MessageUtil.textmessageToxml(mm);
		return mString;
	}
	private static TextMessage Pz(String FromUserName,String ToUserName,String Content,String type){
		TextMessage mm = new TextMessage();
		mm.setFromUserName(ToUserName);
		mm.setToUserName(FromUserName);
		mm.setMsgType(type);
		mm.setCreateTime(Long.toString(new Date().getTime()));
		mm.setContent(Content);
		return mm;
	
	}
	public static String newsmessageToxml(NewsMessage newsmessage){
		XStream xStream = new XStream();
		xStream.alias("xml", newsmessage.getClass());
		xStream.alias("item", new News().getClass());
		return xStream.toXML(newsmessage);
	}
	public static String initNewsMessage(String FromUserName,String ToUserName){
		String message = null;
		
		News news  = new News();
		news.setPicUrl("http://mtest110.tunnel.2bdata.com/WEIXIN2/Image/18.bmp");//图片URL为http：//服务端/WEIXIN2/image/。。。
		news.setTitle("test");
		news.setUrl("www.baidu.com");
		news.setDescription("描述");
		
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setFromUserName(ToUserName);
		newsMessage.setToUserName(FromUserName);
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setCreateTime((new Date().getTime())+"");
		
		List<News> mNews = new ArrayList<News>();
		mNews.add(news);
		newsMessage.setArticles(mNews);
		newsMessage.setArticleCount(mNews.size());
		
		message = newsmessageToxml(newsMessage);
		
		return message;
		
	}
	
	public static String PicMessageToXML(ImageMessage imageMessage){
		XStream xStream = new XStream();
		xStream.alias("xml", imageMessage.getClass());
		xStream.alias("Image", new Image().getClass());
		return xStream.toXML(imageMessage);
	}
	public static String returnPicMessage(String FromUserName,String ToUserName,String mid){
		String message = null;
		
		Image Image = new Image();
		Image.setMediaId(mid);
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setCreateTime(new Date().getTime()+"");
		imageMessage.setFromUserName(ToUserName);
		imageMessage.setmImage(Image);
		imageMessage.setMsgType("Image");
		imageMessage.setToUserName(FromUserName);
		
		message = MessageUtil.PicMessageToXML(imageMessage);
		return message;
	}
	
}
