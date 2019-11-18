package kr.co.javalevel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlFileRead {
    public static Map<Object, Object> getXmlData(List<String> csvFileList) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        List<Customer> customerList = new 
                ArrayList<Customer>();
        List<Product> productList = new ArrayList<Product>();
        List<Order> orderList = new ArrayList<Order>();
        
        try {
            for (int i = 0; i < csvFileList.size(); i++) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); // 1.문서를 읽기위한 공장을 만들어야 한다.
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); // 2.빌더 생성
                Document doc = dBuilder.parse(new File(csvFileList.get(i))); // 3.생성된 빌더를 통해서 xml문서를 Document객체로 파싱해서 가져온다
                doc.getDocumentElement().normalize();// 문서 구조 안정화
                Element root = doc.getDocumentElement();
                
                if(root.getNodeName().equals("customer")) {
                    NodeList nodeList = root.getElementsByTagName("customerInfo");
                    
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Node nNode = nodeList.item(j);
                        Customer customer = new Customer();
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            customer.setCustomerNumber(Integer.parseInt(getTagValue("customerId", eElement)));
                            customer.setCustomerName(getTagValue("customerName", eElement));
                            customerList.add(customer);
                        }
                    }
                } else if(root.getNodeName().equals("product")) {
                    NodeList nodeList = root.getElementsByTagName("productInfo");
                    
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Node nNode = nodeList.item(j);
                        Product product = new Product();
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            product.setProductNumber(Integer.parseInt(getTagValue("productId", eElement)));
                            product.setProductName(getTagValue("productName", eElement));
                            productList.add(product);
                        }
                    }
                } else if(root.getNodeName().equals("order")) {
                    NodeList nodeList = root.getElementsByTagName("orderInfo");
                    
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Node nNode = nodeList.item(j);
                        Order order = new Order();
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            order.setOrderNumber(Integer.parseInt(getTagValue("orderId", eElement)));
                            for (Customer customer : customerList) {
                                if (customer.getCustomerNumber() == Integer.parseInt(getTagValue("customerId", eElement))) {
                                    order.setCustomerNumber(customer);
                                }
                            }
                            for (Product product : productList) {
                                if (product.getProductNumber() == Integer.parseInt(getTagValue("productId", eElement))) {
                                    order.setProductNumber(product);
                                }
                            }
                            orderList.add(order);
                        }
                    }
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        for (Order order : orderList) {
            map.put(order.getOrderNumber(), order);
        }
        
        return map;
    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList subNodeList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nodeValue = (Node) subNodeList.item(0);
        return nodeValue.getNodeValue();
    }
}
