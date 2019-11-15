package kr.co.javalevel;

public class Product { //상품
    private int productNumber;
    private String productName;

    public Product() {}

    public Product(int productNumber) {
		super();
		this.productNumber = productNumber;
	}

	public Product(int productNumber, String productName) {
        this.productNumber = productNumber;
        this.productName = productName;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
