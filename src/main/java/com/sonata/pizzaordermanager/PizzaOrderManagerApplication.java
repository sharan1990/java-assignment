package com.sonata.pizzaordermanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sonata.pizzaordermanager.util.ApplicationConstants;
import com.sonata.pizzaordermanger.model.Order;

@SpringBootApplication
public class PizzaOrderManagerApplication {

	Logger logger= LoggerFactory.getLogger(PizzaOrderManagerApplication.class);
	static String inputFile;
	static String outputFile;
	public static void main(String[] args) {
		SpringApplication.run(PizzaOrderManagerApplication.class, args);
		System.out.println("Welcome to Pizza store");
		inputFile=args[0];
		outputFile=args[1];
		List<Order> sortedOrders=new PizzaOrderManagerApplication().loadOrders();
		new PizzaOrderManagerApplication().saveOrdersToFile(sortedOrders);
	}

	/**
	 * method to read orders from text file	
	 * @return
	 */
	private List<Order> loadOrders(){
		 List<String> orders=new ArrayList<String>();
        File file = new File(inputFile);
        try (BufferedReader br = new BufferedReader(new FileReader(file)))  {
            String line;
            boolean flag = true; 
            List<String> columns = null; 
            while ((line = br.readLine()) != null) {
               if (flag) {
                   flag = false; 
                   //process header 
                   columns = Arrays.asList(line.split(","));
               } else {
            	  List<String> list = Arrays.asList(line.split(","));
            	  orders.addAll(list);
               } 
            }
        } catch(FileNotFoundException fnfe) {
        	logger.error("File not found=="+fnfe);
        } catch(IOException io) {
        	logger.error("Cannot read file."+io);
        }
       return sortOrdersByTime(orders);
	}
	
	/**
	 * method to sort orders by time
	 * @param orderList
	 * @return
	 */
	private List<Order> sortOrdersByTime(List<String> orderList) {
		String[] stringArray=null;
		List<Order> orders=new ArrayList<Order>();
		System.out.println("inside=="+orderList.size());
			for(String str:orderList) {
				String st=new String(str.trim().replaceAll("\\s+", " "));
				stringArray=st.split(" ");
				if(stringArray.length==2) {
					Order order=new Order(stringArray[0], Long.valueOf(stringArray[1]));
					orders.add(order);
				}
			}
			System.out.println("before sorting===");
			orders.forEach(item->System.out.println(item.getOrderType()+" "+item.getTime()));
			Collections.sort(orders);
			System.out.println("after sorting===");
			orders.forEach(item->System.out.println(item.getOrderType()+" "+item.getTime()));
			return orders;
		}
	
	private void saveOrdersToFile(List<Order> list) {
		try {
		    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		    writer.write("Orders"+"		"+"Time");
		    writer.newLine();
		    for (Order order : list) {
                 writer.write(order.toString());
                 writer.newLine();
                }
            writer.close();
            System.out.println("saved to file find in the path "+outputFile);
		} catch (FileNotFoundException e) {
			logger.error("File not found=="+e);
		} catch (IOException e) {
			logger.error("Cannot read file."+e);
		}
	}
}
