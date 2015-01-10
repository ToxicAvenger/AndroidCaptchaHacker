package pl.mobilnebajery.captchahacker.browser;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class DummyBrowser extends AbstractBrowser {
	
	private RequestBuilder requestBuilder;
	
	public DummyBrowser() {

		requestBuilder = new RequestBuilder(new CookieDecorator());
	}

	public PageData getPageData(String pageUrl) throws Exception {
				
		InputStream stream = requestBuilder.buildGetRequest(pageUrl);

		return readPageDataAndCloseStream(stream);
	}
	
	public PageData getPostPageData(String pageUrl, String cookieStoreUri, Map<String, String> params) throws Exception {
	
		InputStream stream = null;
		
		if(!isAndroid()) {
		    
		    stream = requestBuilder.buildJavaPostRequest(pageUrl, cookieStoreUri, params);
		} else {
        
	    	stream = requestBuilder.buildAndroidPostRequest(pageUrl, cookieStoreUri, params);
		}
		
		return readPageDataAndCloseStream(stream);
	}
	
	private PageData readPageDataAndCloseStream(InputStream stream) throws IOException {
		
		if (stream != null) {
			byte[] data = readInputStreamAsString(stream);
			stream.close();

			return new PageData(data);
		}

		return null;
	}
	
	private boolean isAndroid() {
		
		return System.getProperty("java.vm.name").equalsIgnoreCase("Dalvik");
	}
	
	private byte[] readInputStreamAsString(InputStream in) throws IOException {

		BufferedInputStream bis = new BufferedInputStream(in);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while (result != -1) {
			byte b = (byte) result;
			buf.write(b);
			result = bis.read();
		}
		return buf.toByteArray();
	}
}
