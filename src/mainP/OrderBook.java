package mainP;

import java.util.ArrayList;

public class OrderBook {
	ArrayList<Order> buyOrders = new ArrayList<Order>();
	ArrayList<Order> sellOrders = new ArrayList<Order>();
	SharePrice share;
	
	public OrderBook(SharePrice share, ArrayList<Order> buyOrders, ArrayList<Order> sellOrders) {
		this.share = share;
		this.buyOrders = buyOrders;
		this.sellOrders = sellOrders;
	}
	
	public String toString() {
		return "";
	}

}
