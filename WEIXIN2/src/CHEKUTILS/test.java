package CHEKUTILS;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		StringBuffer AD = new StringBuffer();
		AD.append("nihao");
		Sha1 sha1 = new Sha1();
		String d ;
		d = Sha1.getSha1(AD.toString());
		System.out.println(d);
	}

}
