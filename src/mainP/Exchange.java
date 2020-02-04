package mainP;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Exchange {
	
	public static Random random = new Random();
	
	public static SharePrice rndShare() {
		//Creates random share with random  price
		String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder(3);
		for(int i=0;i<3;i++) {
			int rndCharIndex = random.nextInt(charSet.length());
			char rndChar  = charSet.charAt(rndCharIndex);
			sb.append(rndChar);
		}
		double sharePrice = 0.01 +(1000 - 0.01) *random.nextDouble();
		sharePrice = Math.round(sharePrice * 100d)/100d;
		String shareName = sb.toString();
		SharePrice newShare = new SharePrice(shareName, sharePrice);
		return newShare;
	}
	
	public static ArrayList<SharePrice> makeShareList(int size) {
		ArrayList<SharePrice> shareList = new ArrayList<SharePrice>();
		HashSet<String> shareNames = new HashSet<String>(size);
		for(int i=0;i<size;i++) {
			SharePrice newShare = rndShare();
			if(shareNames.contains(newShare.shareName)) {
				i--;
				continue;
			}else {
				shareList.add(newShare);
				shareNames.add(newShare.shareName);
			}
			
		}
		return shareList;
	}
	
	public static double aveShareP(ArrayList<SharePrice> shares) {
		double sum = 0;
		for(int i=0;i<shares.size();i++) {
			sum = sum +shares.get(i).sharePrice;
		}
		return Math.round((sum/shares.size())*100d)/100d;
	}
	
	public static double maxShareP(ArrayList<SharePrice> shares) {
		double max = shares.get(0).sharePrice;
		for(int i=0;i<shares.size();i++) {
			if(shares.get(i).sharePrice > max) {
				max = shares.get(i).sharePrice;
			}
		}
		return max;
	}
	
	public static double minShareP(ArrayList<SharePrice> shares) {
		double min = shares.get(0).sharePrice;
		for(int i=0;i<shares.size();i++) {
			if(shares.get(i).sharePrice < min) {
				min = shares.get(i).sharePrice;
			}
		}
		return min;
	}

	public static Order makeOrder(SharePrice share, double spread, Order.tradeType type) {
		Random r = new Random();
		
		double bidDiff = Math.round(spread*r.nextDouble()*100d)/100d;
		int operator = r.nextInt(2); //If 0 add, If 1 sub bidDiff
		
		double bidPrice = share.sharePrice;
		if(operator == 0) {
			bidPrice = bidPrice + bidDiff;
		}else {
			bidPrice = bidPrice - bidDiff;
		}
		bidPrice = Math.round(bidPrice*100d)/100d;
		//Creating random quantity and rounding to nearest 100
		int quantity = r.nextInt((10000 - 100) + 1) + 100;
		quantity = ((quantity + 99)/100)*100;
		
		Order aOrder = new Order(type,share.shareName,bidPrice,quantity);
		return aOrder;
	}
	
	
	public static OrderBook makeOrderBook(SharePrice share, int numOrders, double spread) {
		
		ArrayList<Order> buyOrders = new ArrayList<Order>();
		ArrayList<Order> sellOrders = new ArrayList<Order>();
		
		for(int i=0;i<numOrders;i++) {
			Order buyOrder = makeOrder(share, spread, Order.tradeType.BUY);
			buyOrders.add(buyOrder);
			Order sellOrder = makeOrder(share, spread, Order.tradeType.SELL);
			sellOrders.add(sellOrder);
		}
		
		Collections.sort(buyOrders, new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				return Double.compare(o2.bid, o1.bid);
			}	
		});
		
		Collections.sort(sellOrders, new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				return Double.compare(o1.bid, o2.bid);
			}
		});
		
		OrderBook theBook = new OrderBook(share, buyOrders, sellOrders);
		return theBook;
	}
	
	
	public static void main(String[] args) {
		ArrayList<SharePrice> shares = makeShareList(10);
		System.out.println("Array Size: "+shares.size());
		for(int i=0;i<shares.size();i++) {
			System.out.println(shares.get(i).toString());
		}
		System.out.println("Average Price: $"+aveShareP(shares));
		System.out.println("Max Price: $"+maxShareP(shares));
		System.out.println("Min Price: $"+minShareP(shares));
		/*
		Order testO = new Order(Order.tradeType.BUY,"AZN", 23.3,10);
		System.out.println(testO.toString());
		
		Random r = new Random();
		for(int i=0;i<25;i++) {
			System.out.println(Math.round((0.1*r.nextDouble())*100d)/100d);
		}
		
		Order testMakeOrder = makeOrder(shares.get(0),0.1,Order.tradeType.BUY);
		System.out.println(testMakeOrder.toString());
		*/
		
		OrderBook oBook = makeOrderBook(shares.get(0),10,0.1);
		ArrayList checkB = oBook.buyOrders;
		ArrayList checkS = oBook.sellOrders;
		
		for(int i=0;i<checkB.size();i++) {
			System.out.println(checkB.get(i).toString());
		}
		for(int i=0;i<checkS.size();i++) {
			System.out.println(checkS.get(i).toString());
		}
				
	}

}
