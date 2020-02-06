package mainP;

import java.util.ArrayList;

import mainP.Order.tradeType;

public class OrderBook {
	ArrayList<Order> buyOrders = new ArrayList<Order>();
	ArrayList<Order> sellOrders = new ArrayList<Order>();
	SharePrice share;
	
	public OrderBook(SharePrice share, ArrayList<Order> buyOrders, ArrayList<Order> sellOrders) {
		this.share = share;
		this.buyOrders = buyOrders;
		this.sellOrders = sellOrders;
	}
	
	public ArrayList<Order> matchBandS() {
		ArrayList<Order> trades = new ArrayList<Order>();
		boolean tradesRemain = true;
		//int index = 0;
		while(tradesRemain) {
			Order buyOrder = this.buyOrders.get(0);
			Order sellOrder = this.sellOrders.get(0);
			
			if(buyOrder.bid >= sellOrder.bid) {
				if(buyOrder.quantity >= sellOrder.quantity) {
					this.buyOrders.get(0).subQuantity(sellOrder.quantity);
					this.sellOrders.remove(0);
					Order aTrade = new Order(tradeType.BUY, sellOrder.stock, sellOrder.bid, sellOrder.quantity);
					trades.add(aTrade);
				}else if(sellOrder.quantity >= buyOrder.quantity) {
					this.sellOrders.get(0).subQuantity(buyOrder.quantity);
					this.buyOrders.remove(0);
					Order aTrade = new Order(tradeType.BUY, sellOrder.stock, sellOrder.bid, sellOrder.quantity);
					trades.add(aTrade);
				}
			}else {
				tradesRemain = false;
			}
		}
		
		return trades;
	}
	
	public void printBook() {
		
		int size = 0;
		boolean moreBuy = true;
		if(this.buyOrders.size() >= this.sellOrders.size()) {
			size = this.sellOrders.size();
		}else {
			size = this.buyOrders.size();
			moreBuy = false;
		}
		
		for(int i=0; i<size;i++) {
			System.out.println(this.buyOrders.get(i)+" | "+this.sellOrders.get(i));
		}
		if(moreBuy) {
			for(int i=size;i<buyOrders.size();i++) {
				System.out.println(this.buyOrders.get(i)+" | ");
			}
		}else {
			for(int i=size;i< sellOrders.size();i++) {
				System.out.println(String.format("%110s", "| "+this.sellOrders.get(i)));
			}
		}
		
	}

}
