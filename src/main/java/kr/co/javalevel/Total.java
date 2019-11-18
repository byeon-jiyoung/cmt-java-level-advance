package kr.co.javalevel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Total {
    public static void main(String[] args) {
        String dir = System.getProperty("user.dir") + File.separator + "data" + File.separator;
        String[] files = {"customer.csv","customer.xml","product.csv","order.csv","product.xml","order.xml"};
        
        List<String> csvFileList = new ArrayList<String>();
        List<String> xmlFileList = new ArrayList<String>();
        
        for(int i=0; i<files.length; i++) {
            if(files[i].substring(files[i].lastIndexOf(".")+1).equals("csv")) {
                csvFileList.add(dir+files[i]);
                
            }else if(files[i].substring(files[i].lastIndexOf(".")+1).equals("xml")) {
                xmlFileList.add(dir+files[i]);
            }
        }
        
        Map<Object, Object> fileMap = FileRead.readFile(csvFileList);
        Map<Object, Object> xmlFileMap = XmlFileRead.getXmlData(xmlFileList);
        printMap(fileMap);
        printMap(xmlFileMap);
    }

    private static void printMap(Map<Object, Object> map) {
        System.out.println("----------------------------------");
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
