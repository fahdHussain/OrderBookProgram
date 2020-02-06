package mainP;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Exchange {
	
	public static Random random = new Random();
	
//Q1 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static SharePrice rndShare() {
		//Creates random share with random  price
		String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder(3);
		for(int i=0;i<3;i++) {
			int rndCharIndex = random.nextInt(charSet.length());
			char rndChar  = charSet.charAt(rndCharIndex);
			sb.append(rndChar);
		}
		//share price limited between 0.01 and 1000 for simplicity
		double sharePrice = 0.01 +(1000 - 0.01) *random.nextDouble();
		sharePrice = Math.round(sharePrice * 100d)/100d;
		String shareName = sb.toString();
		SharePrice newShare = new SharePrice(shareName, sharePrice);
		return newShare;
	}
	
	public static ArrayList<SharePrice> makeShareList(int size) {
		//Generates list of shares with given size
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

//Q2 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static double aveShareP(ArrayList<SharePrice> shares) {
		//find average share price
		double sum = 0;
		for(int i=0;i<shares.size();i++) {
			sum = sum +shares.get(i).sharePrice;
		}
		return Math.round((sum/shares.size())*100d)/100d;
	}
	
	public static double maxShareP(ArrayList<SharePrice> shares) {
		//find max share price
		double max = shares.get(0).sharePrice;
		for(int i=0;i<shares.size();i++) {
			if(shares.get(i).sharePrice > max) {
				max = shares.get(i).sharePrice;
			}
		}
		return max;
	}
	
	public static double minShareP(ArrayList<SharePrice> shares) {
		//find min share price
		double min = shares.get(0).sharePrice;
		for(int i=0;i<shares.size();i++) {
			if(shares.get(i).sharePrice < min) {
				min = shares.get(i).sharePrice;
			}
		}
		return min;
	}

//Q3 a)
	public static Order makeOrder(SharePrice share, double spread, Order.tradeType type) {
		//Create an order for a share with a random spread
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
		//Create an orderbook
		
		ArrayList<Order> buyOrders = new ArrayList<Order>();
		ArrayList<Order> sellOrders = new ArrayList<Order>();
		
		for(int i=0;i<numOrders;i++) {
			Order buyOrder = makeOrder(share, spread, Order.tradeType.BUY);
			buyOrders.add(buyOrder);
			Order sellOrder = makeOrder(share, spread, Order.tradeType.SELL);
			sellOrders.add(sellOrder);
		}
		//Sort buy and sells
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
	
	//Find min,max, ave price per stock
	public static double avePperStock(ArrayList<Order> orders) {
		//find average share price
		double sum = 0;
		for(int i=0;i<orders.size();i++) {
			sum = sum +orders.get(i).bid;
		}
		return Math.round((sum/orders.size())*100d)/100d;
	}
	
	public static double maxPperStock(ArrayList<Order> orders) {
		//find max share price
		double max = orders.get(0).bid;
		for(int i=0;i<orders.size();i++) {
			if(orders.get(i).bid > max) {
				max = orders.get(i).bid;
			}
		}
		return max;
	}
	
	public static double minPperStock(ArrayList<Order> orders) {
		//find min share price
		double min = orders.get(0).bid;
		for(int i=0;i<orders.size();i++) {
			if(orders.get(i).bid < min) {
				min = orders.get(i).bid;
			}
		}
		return min;
	}
	
	public static void closestOrders(ArrayList<Order> trades) {
		long minDiff = 100000000000l;
		Order trade1 = trades.get(0);
		Order trade2 = trades.get(1);
		for(int i=0;i<trades.size();i++) {
			Date fDate = trades.get(i).orderDate;
			for(int j=0;j<trades.size();j++) {
				if(trades.get(i) == trades.get(j)) {
					continue;
				}else if((fDate.getTime() - trades.get(j).orderDate.getTime())<minDiff) {
					minDiff = fDate.getTime() - trades.get(j).orderDate.getTime();
					trade1 = trades.get(i);
					trade2 = trades.get(j);
				}
			}
		}
		System.out.println("Closest orders: ");
		System.out.println(trade1.toString());
		System.out.println(trade2.toString());
	}
	
	
	
	public static void main(String[] args) {
		//Creating 100 random shares
		
		ArrayList<SharePrice> shares = makeShareList(100);
		//Uncomment below to print out all stocks
		/*
		System.out.println("Array Size: "+shares.size());
		for(int i=0;i<shares.size();i++) {
			System.out.println(shares.get(i).toString());
		}
		*/
		System.out.println("Share stats");
		System.out.println("Average Price: $"+aveShareP(shares));
		System.out.println("Max Price: $"+maxShareP(shares));
		System.out.println("Min Price: $"+minShareP(shares));
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
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
		
		//Creating 30 order books with 50 buy and sell orders
		ArrayList<OrderBook> myBooks = new ArrayList<OrderBook>();
		for(int i=0;i<30;i++) {
			myBooks.add(makeOrderBook(shares.get(i),50,0.1));
		}
		
		//OrderBook oBook = makeOrderBook(shares.get(0),10,0.1);
		//Printing an order book
		myBooks.get(0).printBook();
		
		//Mathcing buyers and sellers for an orderbook
		ArrayList newTrades = myBooks.get(0).matchBandS();
		//ArrayList checkB = oBook.buyOrders;
		//ArrayList checkS = oBook.sellOrders;
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		/*
		for(int i=0;i<checkB.size();i++) {
			System.out.println(checkB.get(i).toString());
		}
		for(int i=0;i<checkS.size();i++) {
			System.out.println(checkS.get(i).toString());
		}
		for(int i=0;i<newTrades.size();i++) {
			System.out.println(newTrades.get(i).toString());
		}
		*/
		//Print orderbook post matching and dispay stats
		myBooks.get(0).printBook();
		System.out.println("Average Price Per Stock: $"+avePperStock(newTrades));
		System.out.println("Max Price Per Stock: $"+maxPperStock(newTrades));
		System.out.println("Min Price Per Stock: $"+minPperStock(newTrades));
		
		closestOrders(newTrades);
	}

}
