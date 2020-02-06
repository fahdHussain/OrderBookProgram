package mainP;

public class SharePrice {
	
	String shareName;
	double sharePrice;
	
	public SharePrice(String shareName, double sharePrice) {
		this.shareName = shareName;
		this.sharePrice = sharePrice;
	}
	
	public String toString() {
		return (shareName+" : $"+sharePrice);
	}
	
}
