package CHEKUTILS;

import java.util.Arrays;

public class ChekUtil {
	
	private static final String token = "199581";
	
	public static boolean Cheksignature(String signature,String timestamp,String nonce){
	
		System.out.println(signature+"-"+timestamp+"-"+nonce);
		String[] arr = new String[]{token, timestamp, nonce};
		
		//ÅÅÐò
		Arrays.sort(arr);
		//Éú³É×Ö·û´®
		StringBuffer buffer = new StringBuffer();
		for(int i =0;i<arr.length;i++){
			buffer.append(arr[i]);
		}
		Sha1 sha1 = new Sha1();
		String awString = sha1.getSha1(buffer.toString());
		boolean flag;
		flag = awString.equals(signature);
		System.out.println("aws=="+awString);
		System.out.println("signature=="+signature);
		return flag;
		
	}

}
