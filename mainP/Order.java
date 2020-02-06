package mainP;

import java.util.Comparator;
import java.util.Date;

public class Order {
	
	public enum tradeType{
		BUY,
		SELL
	}
	
	double bid;
	int quantity;
	tradeType type;
	String stock;
	Date orderDate;
	
	public Order(tradeType type, String stock, double bid, int quantity) {
		this.type = type;
		this.stock = stock;
		this.bid = bid;
		this.quantity = quantity;
		orderDate = new Date();
	}
	
	public void subQuantity(int sub) {
		this.quantity = this.quantity - sub;
	}
	
	
	public String toString() {
		return(type+" "+stock+": $"+bid+" @ "+quantity+", "+orderDate);
	}
	/*
	public static Comparator<Order> bidCompare = new Comparator<Order>() {
		public double compare(Order o1, Order o2) {
			
		}
	};
	*/
	
}
