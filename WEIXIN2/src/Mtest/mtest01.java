package Mtest;

import WeiXinUtil.Access_token;
import WeiXinUtil.WeiXinUtils;

public class mtest01 {

	public static void main(String[] args) {
//		Access_token access_token = WeiXinUtils.geToken();
//		System.out.println("token:"+access_token.getAccess_token()+"expires_in:"+access_token.getExpires_in());
//		//²âÊÔÉÏ´«ËØ²Ä£»
		String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=FA3iXhFqx2UF-LiuxdtwPwCRO3cpIOwfV__BEZfrOrv0Pd9kH5NPzE1_f0rarSpzvakCkMDf0CmpTviQLnTHDkckmIVQmndahZkdvtzMvU_HwWbanZQTc0DOO6aJ9UDxPWJfAFAHOB&type=image";
		String filepath = "f:\\11.jpg";
		String result = WeiXinUtils.upData(url, filepath, "image");
		System.out.println(result);
		//id:u1y2HaZkqWVctf_Ecaoh8E80RVf_wXaqY96rl-P3yefaYaTNk_dEUniySfEIZuoy
		//created_at:1487676615

	}

}
