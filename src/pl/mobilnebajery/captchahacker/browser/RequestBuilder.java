package pl.mobilnebajery.captchahacker.browser;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class RequestBuilder {

	private static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401";
	private CookieDecorator cookies;
	
	public RequestBuilder(CookieDecorator cookies) {
		this.cookies = cookies;
	}
	
	public InputStream buildGetRequest(String pageUrl) throws IOException, URISyntaxException {
		
		URL url = new URL(pageUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("User-Agent", USER_AGENT);
		
		cookies.storeCookies(conn);
		
		return conn.getInputStream();
	}
	
	public InputStream buildJavaPostRequest(String pageUrl, String cookieStoreUri, Map<String, String> params) throws IOException, URISyntaxException {
		URL url = new URL(pageUrl);
		byte[] postDataBytes = getPostDataFromParams(params);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	     
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401");
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	    conn.setRequestProperty("Connection", "keep-alive");
	    conn.setDoOutput(true);
	    
	    cookies.setCookies(conn, cookieStoreUri);
	    
	    conn.getOutputStream().write(postDataBytes);
	    
	    return conn.getInputStream();
	}
	
	public InputStream buildAndroidPostRequest(String pageUrl, String cookieStoreUri, Map<String, String> params) throws IOException, URISyntaxException {
	    
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(pageUrl);
	    
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        
        for(String key : params.keySet()) {
        	nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
        }

        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        
        String cookie = this.cookies.getCookie(pageUrl);
        
        httppost.addHeader("Cookie", cookie);
        
        HttpResponse response = httpclient.execute(httppost);
        
        return response.getEntity().getContent();
	}
	
	private byte[] getPostDataFromParams(Map<String, String> params) throws UnsupportedEncodingException {
	    StringBuilder postData = new StringBuilder();
	    
	    for (Map.Entry<String, String> param : params.entrySet()) {
	        if (postData.length() != 0) postData.append('&');
	        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	        postData.append('=');
	        postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
	    }
	    
	    return postData.toString().getBytes("UTF-8");
	}
}
