package SourceCode;

/*** Imports ***/
import Interface.LoginInterface;
import Interface.CustomerInterface;
import Interface.AdminInterface;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;   //Import file not found exception
import java.io.IOException;
import java.io.FileReader;      //Import file writer and reader 
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;     //JFrame import 
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel; //Table Model

/*** Class User has two sub-class named Customer and Admin(Administration) ***/
public class User {
    
    /*** Data Types ***/
    private String name;         //User Name
    public String email;        //User Email
    public String phoneNum;     //User Phone Number
    public String mailingAdd;   //User Mailing Address
    public String username;     //User Username
    public Scanner scan;
    protected String password;      //User Password
    protected String targetFile;    //The text file location to read or write
    static boolean loginIdentifier;
    int rows;
    
    /*** Instance of a class ***/
    DefaultTableModel model;
    JTable table;
    Order order;
    Customer customer;
    Admin admin;
    Product product;
    
    public User(){
    }
    
    public User(String fileName){  
        this.targetFile = fileName;
    }
    
    public User(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public User(DefaultTableModel model, JTable table, String targetFile) {
        this.model = model;
        this.table = table;
        this.targetFile = targetFile;
        this.model = (DefaultTableModel) this.table.getModel();
    }

    public User(String email, String phoneNum, String mailingAdd/*, String username*/) {
        this.email = email;
        this.phoneNum = phoneNum;
        this.mailingAdd = mailingAdd;
        //this.username = username;
    }
    
    @Override
    public String toString() {
        return "User{" + "Username : " + username + 
                ", Phone Num : " + phoneNum + 
                ", Email : " + email + 
                ", Adderess : " + mailingAdd + '}';
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getMailingAdd() {
        return mailingAdd;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }       

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setTargetFile (String targetFile){
        this.targetFile = targetFile;
    }  

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setMailingAdd(String mailingAdd) {
        this.mailingAdd = mailingAdd;
    }
    
    
    
    /*** Method of login
     * 
     * @param Navigator //Directing to read which text file
     *
     ***/
    public void loginFunc(String Navigator){

        CustomerInterface CustomerInterface = new CustomerInterface();
        AdminInterface AdminInterface = new AdminInterface();
        LoginInterface loginInterface = new LoginInterface();

        try {

            Scanner read = new Scanner(new File(targetFile));

            while (read.hasNextLine()){
                String s = read.nextLine();  
                String[] sArray = s.split("/");

                if (password.trim().equals(sArray[1]) && username.trim().equals(sArray[0])){
                    JOptionPane.showMessageDialog(null,
                        "Login Successful", "Success",
                        JOptionPane.INFORMATION_MESSAGE); 

                    if ("Customer".equals(Navigator)){                        
                        CustomerInterface.TextFieldProfileName.setText(sArray[0]);
                        CustomerInterface.TextFieldProfilePhoneNum.setText(sArray[4]);
                        CustomerInterface.TextFieldProfileEmail.setText(sArray[2]);
                        CustomerInterface.TextFieldProfileAddress.setText(sArray[3]); 
                        CustomerInterface.setVisible(true);
                        
                        loginInterface.dispose();
                        
                    }    
                    else if("Admin".equals(Navigator)){                       
                        AdminInterface.AdminName.setText(sArray[0]);
                        AdminInterface.AdminPhone.setText(sArray[4]);
                        AdminInterface.AdminEmail.setText(sArray[2]);
                        AdminInterface.AdminAddress.setText(sArray[3]); 
                        AdminInterface.setVisible(true);

                        loginInterface.dispose();
                        
                    }
                    break;
                }else if(!read.hasNextLine()){
                    JOptionPane.showMessageDialog(null,
                        "UserName/Password Incorrect", "Error",
                        JOptionPane.ERROR_MESSAGE);
                    break; 
                }else {
                    JOptionPane.showMessageDialog(null,
                        "UserName/Password Incorrect", "Error",
                        JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
            read.close();
        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null,
                "File Not Found", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*** Searching Method
     * 
     * @param valueToSearch 
     * 
     ***/
    public void search(String valueToSearch){  
                       
        try{                        
            scan = new Scanner (new File (targetFile));
            
            String line;
            int count = 0;
            while(scan.hasNextLine()){  
                line = scan.nextLine();
                String lines[] = line.split("/");
                
                if(valueToSearch.trim().equals(lines[1])){      //Search with Item name
                    model.addRow(lines);
                    count++;
                }
            }
            if(count == 0){
               JOptionPane.showMessageDialog(null, "No Item Found !"); 
            }
            scan.close();
        }catch(IOException | NullPointerException e){
            System.out.println("File Not Found");
            System.out.println(e);
        }               
    }                

    /*** View all the order Method ***/
    public void viewOrder(){
        //Open File
        int count = 0;
        try(BufferedReader bfr = new BufferedReader(new FileReader(targetFile))){
            
            //Get line from txt file
            Object[] tableLine = bfr.lines().toArray();
            
            //Loop Through the exists line
            for (Object tableLine1 : tableLine) {   //for(int i = 0 ; i < tableLine.length; i++){
                String[] line = tableLine1.toString().split("/");
                model.addRow(line); 
                count++;
            }
            bfr.close();
        }catch (IOException e){
            System.out.println(e);
        }
        
        if(count == 0){
            JOptionPane.showMessageDialog(null, "No Order Found ! \n Please Go Add one.");
        }
    }
    
    /*** Delete the order Item Method
     * @param orderId
     * @param userName 
     ***/
    public void deleteOrderItem(String orderId, String userName){     
        
        //Remember to call constructor of model table
        int rowSelected = table.getSelectedRow();
          
        if(rowSelected == -1){
            JOptionPane.showMessageDialog(null, "Please Select an Item to delete");
        }else{
            
            int dialog = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "Do you sure to delete this item ?", "Delete", dialog);

            if (result == 0){
                model.removeRow(rows);
                
                //Read text file to delete record 
                String tempFile = "tempOrder.txt";
                File oldFile = new File(targetFile);
                File newFile = new File(tempFile);
                
                try{
                    FileWriter fw1 = new FileWriter(tempFile,true);
                    BufferedWriter bw1 = new BufferedWriter(fw1);                                        
                    
                    FileReader fr = new FileReader(targetFile);
                    BufferedReader br = new BufferedReader(fr);
                    
                    String line;
                    while((line = br.readLine()) != null){
                        String data[] = line.split("/");
                        
                        if(!(data[0].equalsIgnoreCase(orderId))){   //Write all the exsit item
                            bw1.write(line + "\n");
                        }
                    }
                    
                    for (int i = 0; i < table.getRowCount(); i++){  //Write all the item after dlted from the table
                        bw1.write(orderId +"/"+ userName +"/");
                        for (int j = 0 ; j < table.getColumnCount(); j++){
                            bw1.write(table.getValueAt(i,j).toString()+"/");
                        }
                        bw1.newLine();
                    }
                    fr.close();
                    br.close();
                    
                    bw1.flush();
                    fw1.flush();
                    bw1.close();
                    fw1.close();

                    oldFile.delete();
                    File replace = new File(targetFile);
                    newFile.renameTo(replace);
                }catch(IOException e){
                    System.out.println(e);
                }
            }
        }
    }
    
    /*** Update the information of the user
     * 
     * @param OldText
     * @param newText
     * 
     ***/
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
            JOptionPane.showMessageDialog(null, "Successfully Updated");
            bfr.close();
            fw.close();
        }catch (IOException e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Update Failed");
        }
    }
    
    /*** Checker of repeating item
     * 
     * @param FileName
     * @param comparedUserName
     * @return  
     * 
     ***/
    public String repeatChecker(String FileName, String comparedUserName){
        // the identifier that notifies the program if something is repeated
        String repeatIdentifier = "false";
        try (Scanner FileReader = new Scanner(new FileReader(FileName))){
            //scans the file 
            while (FileReader.hasNextLine()){
                String s = FileReader.nextLine();
                String[] sArray = s.split("/");
                // change the identifer to true if the compared item exist in the text file
                if (comparedUserName.equals(sArray[0])){
                    JOptionPane.showMessageDialog(null,
                            "repeated", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                    repeatIdentifier = "true";
                    break;
                }
                else{
                }
            }
        FileReader.close();
        }catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return repeatIdentifier;
    }
    
    public void getDetails (){
        System.out.println("Name : " + username);
        System.out.println("Email : " + email);
        System.out.println("Phone No : " + phoneNum);
        System.out.println("Address : " + mailingAdd);
        System.out.println("\nOrder Details" + product);
    }
    
}
    

