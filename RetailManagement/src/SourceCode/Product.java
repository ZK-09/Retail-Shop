package SourceCode;

/*** A class to maintain the product of the retail shop ***/
public class Product extends OrderItem {
    
    /*** Data Type ***/
    public String productId;        //Product ID
    public String productName;      //Product Name
    public String productType;      //Product Type (Fragile/NonFragile)
    public double productPrice;     //The price of the product
    public int productQuantity;     //Exsit product quantity

    OrderItem orItem;
    
    public Product(){
    }

    public Product(String productId, String productName, String productType, double productPrice, int orderItemQuantity, String orderId) {
        super(orderItemQuantity, orderId);
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productPrice = productPrice;
    }
    
    public Product(String productId, String productName, String productType, double productPrice, String orderId) {
        super(orderId);
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "\n" + "Order ID : " + orderId+ 
                "\nProduct Id : " + productId + 
                "\nProduct Name : " + productName + 
                "\nProduct Type : " + productType + 
                "\nProduct Price : " + productPrice + 
                "\nOrder Amount : " + orderItemQuantity + 
                "\nTotal : " + totalPrice;
    }
    
    
    
    //Getter
    public int getProductQuantity() {
        return productQuantity;
    }
    
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductType() {
        return productType;
    }

    public double getProductPrice() {
        return productPrice;
    }

    //Setter
    public void setProductAmount(int productAmount) {
        this.productQuantity = productAmount;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
    
    
    
    /*** Method of Product ***/
    
    /*** Method of maintaining the product amount
     *   Product Amount Decreased by the chosen Amount Item
     * @
     ***/
    public void decreaseAmount(){
        if(productQuantity > 0 && !(productQuantity <= 0)){
            int newAmount = productQuantity - orderItemQuantity;
            System.out.println(newAmount);
        }else{
            System.out.println("Amount Insufficient");
        } 
    }
    
    /*** Method to increase the product Amount
     *   This is where user delete their choice 
     * @
     */
    public void increaseAmount(){
        int newAmount = productQuantity + orderItemQuantity;
        System.out.println(newAmount);
    }
    
    /*** Method of managing the price based on the type
     * @param price 
     * @return  
     * 
     ***/
    public double price(double price){
        if(productType.equals("Fragile")){
            totalPrice = price + 5.00;
            
        }else if(productType.equals("NonFragile")){
            totalprice = price;
            
        }
        return totalPrice;
    }
    
    @Override
    public void sumOrderItem(){
        price(orderItemQuantity * productPrice);
    }
}
