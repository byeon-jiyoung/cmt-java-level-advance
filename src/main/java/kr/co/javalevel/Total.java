package kr.co.javalevel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Total {
    public static void main(String[] args) {
        String dir = System.getProperty("user.dir") + File.separator + "data" + File.separator;
        String[] path = { dir + "customer.csv", dir + "product.csv", dir + "order.csv" };

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
                                     * try { customer.setCustomerNumber(Integer.parseInt(field[i]));
                                     * } catch (Exception e) { System.err.println("숫자가 아닙니다."); }
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
                                if (i % 3 == 0) {
                                    order.setOrderNumber(Integer.parseInt(field[i]));
                                } else if (i % 3 == 1) {
                                    order.setCustomerNumber(Integer.parseInt(field[i]));
                                } else if (i % 3 == 2) {
                                    order.setProductNumber(Integer.parseInt(field[i]));
                                    orderList.add(order);
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

        Iterator<Object> iterator = map.keySet().iterator(); // 반복자를 이용해서 출력
        while (iterator.hasNext()) {
            int key = (Integer) iterator.next(); // 키 얻기
            Order order = (Order) map.get(key);
            System.out.printf("주문식별번호 : %d, ", order.getOrderNumber());
            
            for(Customer customer : customerList) {
                if(order.getCustomerNumber() == customer.getCustomerNumber()) {
                    System.out.printf("고객식별번호 : %d, 고객이름: %s, ", customer.getCustomerNumber(), customer.getCustomerName());
                }
            }
            
            for(Product product : productList) {
                if(order.getProductNumber() == product.getProductNumber()) {
                    System.out.printf("상품식별번호 : %d, 상품명: %s\n", product.getProductNumber(), product.getProductName());
                }
            }
        }
    }
}
