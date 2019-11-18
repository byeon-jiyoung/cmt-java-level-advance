package kr.co.javalevel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileRead {
    private File file;
    
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Map<Object, Object> readFile(List<String> csvFileList) { //객체생성없이 사용하기 위해 static사용
        Map<Object, Object> map = new HashMap<Object, Object>();
        List<Customer> customerList = new ArrayList<Customer>();
        List<Product> productList = new ArrayList<Product>();
        List<Order> orderList = new ArrayList<Order>();

        BufferedReader br = null;
        String line;
        

        try {
            for (int i = 0; i < csvFileList.size(); i++) {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFileList.get(i))));
                while ((line = br.readLine()) != null) {
                    if (line.indexOf("식별번호") < 0) {
                        String[] field = line.split(",");
                        String fileName = csvFileList.get(i).substring(csvFileList.get(i).lastIndexOf(File.separator)+1, csvFileList.get(i).lastIndexOf("."));
                        
                        if (fileName.equals("customer")) {
                            Customer customer = new Customer();
                            for (int j = 0; j < field.length; j++) {
                                if (j == 0) {
                                    /*
                                     * try { customer.setCustomerNumber(Integer.parseInt(field[i])); } catch
                                     * (Exception e) { System.err.println("숫자가 아닙니다."); }
                                     */
                                    customer.setCustomerNumber(Integer.parseInt(field[j]));
                                } else if (j == 1) {
                                    customer.setCustomerName(field[j]);
                                    customerList.add(customer);
                                }
                            }
                        } else if (fileName.equals("product")) {
                            Product product = new Product();
                            for (int j = 0; j < field.length; j++) {
                                if (j == 0) {
                                    product.setProductNumber(Integer.parseInt(field[j]));
                                } else if (j == 1) {
                                    product.setProductName(field[j]);
                                    productList.add(product);
                                }
                            }
                        } else if (fileName.equals("order")) {
                            Order order = new Order();
                            for (int j = 0; j < field.length; j++) {
                                if (j == 0) {
                                    order.setOrderNumber(Integer.parseInt(field[j]));
                                } else if (j == 1) {
                                    for (Customer customer : customerList) {
                                        if (customer.getCustomerNumber() == Integer.parseInt(field[j])) {
                                            order.setCustomerNumber(customer);
                                        }
                                    }
                                } else if (j == 2) {
                                    for (Product product : productList) {
                                        if (product.getProductNumber() == Integer.parseInt(field[j])) {
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
        } catch (Exception e) {
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
