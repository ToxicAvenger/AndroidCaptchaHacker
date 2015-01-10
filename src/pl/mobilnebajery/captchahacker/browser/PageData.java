package pl.mobilnebajery.captchahacker.browser;

public class PageData {
	
	private byte[] byteData;
	public byte[] getByteData() {
		return byteData;
	}
	
	public String getStringData() {
		return new String(byteData);
	}
	
	public PageData(byte[] byteData) {

		this.byteData = byteData;
	}
}
