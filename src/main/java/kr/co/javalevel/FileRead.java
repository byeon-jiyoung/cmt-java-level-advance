package kr.co.javalevel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileRead {
	public static Map<Object, Object> readFile(String[] path) { //객체생성없이 사용하기 위해 static사용
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Customer> customerList = new ArrayList<Customer>();
		List<Product> productList = new ArrayList<Product>();
		List<Order> orderList = new ArrayList<Order>();

		BufferedReader br = null;
		String line;

		try {
			for (int z = 0; z < path.length; z++) {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(path[z])));
				while ((line = br.readLine()) != null) {
					if (line.indexOf("식별번호") < 0) {
						String[] field = line.split(",");

						if (path[z].indexOf("customer") >= 0) {
							Customer customer = new Customer();
							for (int i = 0; i < field.length; i++) {
								if (i % 2 == 0) {
									/*
									 * try { customer.setCustomerNumber(Integer.parseInt(field[i])); } catch
									 * (Exception e) { System.err.println("숫자가 아닙니다."); }
									 */
									customer.setCustomerNumber(Integer.parseInt(field[i]));
								} else if (i % 2 == 1) {
									customer.setCustomerName(field[i]);
									customerList.add(customer);
								}
							}
						} else if (path[z].indexOf("product") >= 0) {
							Product product = new Product();
							for (int i = 0; i < field.length; i++) {
								if (i % 2 == 0) {
									product.setProductNumber(Integer.parseInt(field[i]));
								} else if (i % 2 == 1) {
									product.setProductName(field[i]);
									productList.add(product);
								}
							}
						} else if (path[z].indexOf("order") >= 0) {
                            Order order = new Order();
                            for (int i = 0; i < field.length; i++) {
                                if (i == 0) {
                                    order.setOrderNumber(Integer.parseInt(field[i]));
                                } else if (i == 1) {
                                    for (Customer customer : customerList) {
                                        if (customer.getCustomerNumber() == Integer.parseInt(field[i])) {
                                            order.setCustomerNumber(customer);
                                        }
                                    }
                                } else if (i == 2) {
                                    for (Product product : productList) {
                                        if (product.getProductNumber() == Integer.parseInt(field[i])) {
                                            order.setProductNumber(product);
                                            orderList.add(order);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		for (Order order : orderList) {
            map.put(order.getOrderNumber(), order);
        }
		
		return map;
	}
}
