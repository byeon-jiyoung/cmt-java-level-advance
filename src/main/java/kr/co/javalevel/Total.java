package kr.co.javalevel;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

public class Total {
    public static void main(String[] args) {
    	String dir = System.getProperty("user.dir") + File.separator + "data" + File.separator;
		String[] path = { dir + "customer.csv", dir + "product.csv", dir + "order.csv" };
		
        Map<Object, Object> map = FileRead.readFile(path);

        Iterator<Object> iterator = map.keySet().iterator(); // 반복자를 이용해서 출력
        while (iterator.hasNext()) {
            int key = (Integer) iterator.next(); // 키 얻기
            Order order = (Order) map.get(key);
            System.out.printf("주문식별번호 : %d, 고객식별번호 : %d, 고객이름: %s, 상품식별번호 : %d, 상품명 : %s\n",
                                order.getOrderNumber(), order.getCustomerNumber().getCustomerNumber(),order.getCustomerNumber().getCustomerName(),
                                order.getProductNumber().getProductNumber(),order.getProductNumber().getProductName());
        }
        
    }
}
