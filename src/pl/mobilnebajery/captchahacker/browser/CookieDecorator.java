package pl.mobilnebajery.captchahacker.browser;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CookieDecorator extends CookieManager {
	
	static final String COOKIES_HEADER = "Set-Cookie";
	
	public CookieDecorator() {
		
		super(null, CookiePolicy.ACCEPT_ALL);
		
		CookieHandler.setDefault(this);
	}
	
	public void setCookies(HttpURLConnection connection, String cookieStoreUri) throws MalformedURLException, URISyntaxException {
		
		if(getCookieStore().getCookies().size() > 0)
		{
			List<HttpCookie> cookies = getCookieStore().get(new URL(cookieStoreUri).toURI());
		    connection.setRequestProperty("Cookie", cookies.get(0).toString());   
		}
	}
	
	public String getCookie(String cookieStoreUri) throws MalformedURLException, URISyntaxException {
		
		if(getCookieStore().getCookies().size() > 0)
		{
			List<HttpCookie> cookies = getCookieStore().get(new URL(cookieStoreUri).toURI());
		    return cookies.get(0).toString();   
		}
		
		return null;
	}
	
	public void storeCookies(HttpURLConnection connection) throws URISyntaxException {
		Map<String, List<String>> headerFields = connection.getHeaderFields();
		List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

		if (cookiesHeader != null) {
			for (String cookie : cookiesHeader) {
				
				List<HttpCookie> cookies = new ArrayList<HttpCookie>();
				
				if(parseCookies(cookie, cookies) && cookies.size() > 0) {
					getCookieStore().add(connection.getURL().toURI(), cookies.get(0));
				}
			}
		}
	}
	
	private boolean parseCookies(String cookie, List<HttpCookie> cookies) {

		try {
			cookies.addAll(HttpCookie.parse(cookie));
		} catch (IllegalArgumentException ex) {
			return false;
		}

		return true;
	}
}
