package SourceCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**** Class orderItem is a sub-class of order ****/
public class OrderItem extends Order{
    
    /*** Data Type ***/
        
    public int orderItemQuantity;   //Quantity choosen
    public double totalprice;       //Total price of choosen by the quantity insert
    private String targetFile;      //The location of the text file
    public JTable table;
    public DefaultTableModel model;
    public JLabel label;
    
    /** Create an instances of class **/
    Order order;
    User users;
    Product product;
    
    /** Constructor **/
    public OrderItem(){
    }
    
    public OrderItem(String fileName, String userName, DefaultTableModel model, JTable table){
        this.targetFile = fileName;
        this.orderId = userName;
        
        this.model = model;
        this.table = table;
        this.model = (DefaultTableModel) this.table.getModel();
    }
    
    public OrderItem(DefaultTableModel model, JTable table, JLabel label){
        this.label = label;
        this.model = model;
        this.table = table;
        this.model = (DefaultTableModel) this.table.getModel();
    }

    public OrderItem(String orderId) {
        super(orderId);
    }

    public OrderItem(int orderItemQuantity, String orderId) {
        super(orderId);
        this.orderItemQuantity = orderItemQuantity;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }
    
    public int getOrderItemQuantity() {
        return orderItemQuantity;
    }

    public double getTotalprice() {
        return totalprice;
    }
    
    public void setOrderItemQuantity(int orderItemQuantity) {
        this.orderItemQuantity = orderItemQuantity;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    @Override
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }
    
    public void setUsers(User user) {
        this.users = user;
    }
  
    
    /*** Method of calculateTotal from the interface table ***/
    @Override
    public void calculate() {
        model = (DefaultTableModel) table.getModel();
        table.setModel(model);
        
        double total = 0.0;
        
        for (int i = 0; i < table.getRowCount(); i ++){
            for (int j = 0; j < table.getColumnCount(); j++){
                if(table.getValueAt(i, j).toString() != null){
                    totalprice = Double.parseDouble(table.getValueAt(i, 5).toString());
                    total += totalprice;
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Please add an item in order to purchase.");
                }
            }
            label.setText(String.valueOf(String.format("%.2f", total)));
        }
    }
    
    
    /*** Item can to be added to the shopping cart
     * @param pId
     * @param pName
     * @param pType
     * @param pPrice
     * @param itemQuantity
     * @param username
     * @param existQua 
     ***/
    public void addOrderItem(String pId, String pName, String pType, double pPrice, int itemQuantity, String username, JLabel existQua){
        
        model = (DefaultTableModel) table.getModel();
        table.setModel(model);
        ArrayList<Product> item = new ArrayList<>();       
        
        try{
            item.add(new Product(pId, pName, pType, pPrice, itemQuantity, orderId));
            
            check(targetFile, pId, username, orderId);            
            
            for(int i = 0; i < item.size(); i++){
                
                /**Object[] row = {};**/ //Alternative to write
                row[6] = orderId;
                row[7] = username;
                row[0] = item.get(i).productId;        //Product Id
                row[1] = item.get(i).productName;     //Product Name
                row[2] = item.get(i).productType;     
                row[3] = item.get(i).productPrice;     //Multiple Price of the total amount chose and price's product
                row[4] = item.get(i).orderItemQuantity;  //Quantity that user choose
                
                //Convert existQuan to int
                int qua = Integer.parseInt(existQua.getText());
                //Calculate the total price with the selected amount (price * amount)
                if(itemQuantity <= 0){
                    JOptionPane.showMessageDialog(null, "Please Enter at lease one amount !");
                }else if (qua >= itemQuantity){
                    if (identifier == false){
                        if (row[2].equals("Fragile")){
                            String totalAmount1 = String.valueOf(String.valueOf(String.format("%.2f",((itemQuantity * pPrice) + 5.0))));
                            row[5] = totalAmount1;
                        }else if(row[2].equals("NonFragile")){      
                            String totalAmount = String.valueOf(String.valueOf(String.format("%.2f",((itemQuantity * pPrice)))));
                            row[5] = totalAmount;
                        }
                        //Set existQua to minus the enter unit
                        int newQua = qua - itemQuantity;
                        existQua.setText(String.valueOf(newQua));
                        
                        edit(String.valueOf(qua), String.valueOf(newQua));    //Update text file with new Quantity                        
                                                   
                        model.addRow(row);
                        
                        FileWriter fw = new FileWriter(targetFile,true);
                        fw.write(row[6] + "/" + row[7] + "/" + row[0] + "/" + row[1] + "/" + row[2] + "/" + row[3] + "/" + row[4]+ "/" + row[5] + "\n");
                        fw.close();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Not enough Sufficient !");
                }  
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    public boolean check(String file, String id, String name, String orderId) {
        
        try (Scanner scanFile = new Scanner(new FileReader(file))) {
            while (scanFile.hasNextLine()) {
                String line = scanFile.nextLine();
                String[] words = line.split("/");
                
                if(name.equals(words[1]) && orderId.equals(words[0])){
                    if (id.equals(words[2])) {      //txt file index is [2]
                        JOptionPane.showMessageDialog(null, "You have entered this product");
                        identifier = true;                   
                        break;
                    } 
                }else{
                        identifier = false;
                    } 
            }
            scanFile.close();

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return identifier;
    }
    
    /*** Order Item Can be edited ***/
    @Override
    public void edit(String OldText,String newText){
        
        File f1 = new File(targetFile);
        
        //Give oldText a null string to keep word
        String oldWord = "";        
        BufferedReader bfr;        
        FileWriter fw;
        
        try{
            bfr = new BufferedReader(new FileReader(f1));            
            //Read all line in the file
            String line = bfr.readLine();
            
            //If name is updated
            while(line != null){
                oldWord = oldWord + line + System.lineSeparator();                
                line = bfr.readLine();
            }
            
            //Replace oldText into newText
            String newWord = oldWord.replaceAll(OldText,newText);

            //Rewrite the text file with new data
            fw = new FileWriter(f1);
            fw.write(newWord);
            bfr.close();
            fw.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }
    
    /*** Get the total price with chosen amount
     * 
     * @the multiple of Product Price and the chosen Amount of product  
     * 
     ***/
    public void sumOrderItem(){
        product.price(orderItemQuantity * product.productPrice);
    }
}
