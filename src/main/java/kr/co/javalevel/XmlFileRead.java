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
    private File file;
    
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Map<Object, Object> getXmlData(List<String> xmlFileList) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        List<Customer> customerList = new ArrayList<Customer>();
        List<Product> productList = new ArrayList<Product>();
        List<Order> orderList = new ArrayList<Order>();
        
        try {
            for (int i = 0; i < xmlFileList.size(); i++) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); // 1.문서를 읽기위한 공장을 만들어야 한다.
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); // 2.빌더 생성
                Document doc = dBuilder.parse(new File(xmlFileList.get(i))); // 3.생성된 빌더를 통해서 xml문서를 Document객체로 파싱해서 가져온다
                doc.getDocumentElement().normalize();// 문서 구조 안정화
                Element root = doc.getDocumentElement();

                if(root.getNodeName().equals("customer")) {
                    NodeList nodeList = root.getElementsByTagName("customerInfo");
                    
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Node node = nodeList.item(j);
                        Customer customer = new Customer();
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) node;
                            customer.setCustomerNumber(Integer.parseInt(getTagValue("customerId", element)));
                            customer.setCustomerName(getTagValue("customerName", element));
                            customerList.add(customer);
                        }
                    }
                } else if(root.getNodeName().equals("product")) {
                    NodeList nodeList = root.getElementsByTagName("productInfo");
                    
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Node node = nodeList.item(j);
                        Product product = new Product();
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) node;
                            product.setProductNumber(Integer.parseInt(getTagValue("productId", element)));
                            product.setProductName(getTagValue("productName", element));
                            productList.add(product);
                        }
                    }
                } else if(root.getNodeName().equals("order")) {
                    NodeList nodeList = root.getElementsByTagName("orderInfo");
                    
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Node node = nodeList.item(j);
                        Order order = new Order();
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) node;
                            order.setOrderNumber(Integer.parseInt(getTagValue("orderId", element)));
                            for (Customer customer : customerList) {
                                if (customer.getCustomerNumber() == Integer.parseInt(getTagValue("customerId", element))) {
                                    order.setCustomerNumber(customer);
                                }
                            }
                            for (Product product : productList) {
                                if (product.getProductNumber() == Integer.parseInt(getTagValue("productId", element))) {
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

    private static String getTagValue(String sTag, Element element) {
        NodeList subNodeList = element.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nodeValue = (Node) subNodeList.item(0);
        
        return nodeValue.getNodeValue();
    }
}
