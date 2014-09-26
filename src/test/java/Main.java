import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


public class Main {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
	String a = "user/1/music/14117238874809.mp3";
	System.out.println(URLEncoder.encode(a, "utf-8"));
	String b = "user%2F1%2Fmusic%2F14099105233850.mp3";
	System.out.println(URLDecoder.decode(b, "utf-8"));
	}

}
