package SourceCode;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/*** Order has a sub-class named OrderItem ***/
/*** Extend abstract Payment class ***/
public class Order extends User implements Payment {

    /*** Data Type ***/   
    public String orderId;      //Order Id
    public double totalPrice;   //Total Price Ordered
    boolean identifier;         //Boolean
    boolean areEqual = false; 
    public Object[] row = new Object[8];
    public JLabel label;
    public DefaultTableModel model;
    private String fileName;
    
    /*** Instance of classes ***/
    Admin ad = new Admin();
    User user = new User();
    OrderItem oItem;
    
    

    /*** Constructor ***/
    public Order(){
    }

    public Order(DefaultTableModel model, JTable table, JLabel label, String file){
        this.fileName = file;
        this.label = label;
        this.model = model;
        this.table = table;
        this.model = (DefaultTableModel) this.table.getModel();
    }
    
    
    
    public Order(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
    
    /*** Generate random number for order Id
     * @param textField 
     ***/
    public void randomId(JTextField textField) {
        Random random = new Random();
        int n = random.nextInt(1000) + 1;
        String value = String.valueOf(n);
        textField.setText(value);
        
    }
    
    /*** Method ***/
    @Override
    public void calculateTxtFile(){
        try{           
            scan = new Scanner (new File (fileName));
            
            double total = 0.0;
            while (scan.hasNextLine()){
                String lines = scan.nextLine();
                String [] price = lines.split("/");
                
                totalPrice = Double.parseDouble(price[2]);     //Price of the total Amount           
                total += totalPrice;                
            }
            
            label.setText(String.valueOf(String.format("%.2f", total)));
            scan.close();
        }catch(IOException | NumberFormatException e){
            System.out.println(e);
        }
    }

    @Override
    public void calculate(){};
}