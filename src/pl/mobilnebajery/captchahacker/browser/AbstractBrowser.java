package pl.mobilnebajery.captchahacker.browser;

import java.util.Map;

public abstract class AbstractBrowser {
	public abstract PageData getPageData(String pageUrl) throws Exception;
	public abstract PageData getPostPageData(String pageUrl, String cookieStoreUri, Map<String, String> params) throws Exception;
}
