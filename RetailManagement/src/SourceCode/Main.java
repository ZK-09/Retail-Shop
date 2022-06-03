package SourceCode;

import Interface.LoginInterface;
import java.util.Scanner;

/**
 * @ZEKAI
 */
public class Main {
    
    /**
     * main class
     * @param args
     */
    public static void main(String[] args) {
        //Show login form
        LoginInterface login = new LoginInterface();
        login.setVisible(true);
        
        Scanner scan = new Scanner (System.in); 
        
        User user;
        Product product;
        
        System.out.print("Username : " );
        String username = scan.nextLine();

        System.out.print("Amount to buy : ");
        int itemQuantity = scan.nextInt();

        user = new User("tanzekai@gmail.com","016-4175316","Homie");
        product = new Product("AC001", "Mirror", "Fragile", 9.00, "345");

        user.setProduct(product);                       //Setter of the product toString()
        user.setUsername(username);                     //Setter of the username of the user

        product.setOrderItemQuantity(itemQuantity);     //Setter the amount of the user intended to buy
        product.setProductPrice(9.00);                  //Setter price of the product
        
        product.sumOrderItem();                         //Method of calculating the total price with the amount chosen
        user.getDetails();                              //Method of printing the order list of the user with details
        
        
        
    }
    
}
