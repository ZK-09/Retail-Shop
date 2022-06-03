package SourceCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/*** Class Administration is a sub-class of user ***/
public class Admin extends User {    
   
    /*** Data Type ***/
    public String aUserName;
    public String aPhoneNumber;
    public String aEmail;
    public String aMailingAdd;
    private DefaultTableModel model;

    /*** Constructor ***/
    public Admin(){
    
    }
    
    public Admin(String aUserName, String aPhoneNumber, String aEmail, String aMailingAdd) {
        this.aUserName = aUserName;
        this.aPhoneNumber = aPhoneNumber;
        this.aEmail = aEmail;
        this.aMailingAdd = aMailingAdd;
    }
    
    /*** Method ***/
    public void viewTable(JTable ViewProdTable, String txtFileName){
    try (BufferedReader bfr = new BufferedReader(new FileReader(txtFileName))){
        model = (DefaultTableModel)ViewProdTable.getModel();
        //Get line from txt file
        Object[] tableLine = bfr.lines().toArray();
                                    
        for (Object tableLine1 : tableLine) {     //int i = 0 ; i < tableLine.length; i++
            String[] line = tableLine1.toString().split("/");
            if (line.equals("")){
                continue;
            }else{
                // add the line to the tables
                model.addRow(line); 
            }
        }
        bfr.close();
    }catch (IOException e){
            System.out.println(e);
        }
        
    }
    
    public void addProd(String ProdId, String ProdName,String FragileCheck, String ProdPrice, String ProdQuan){
        String repeatProd = repeatChecker("Product.txt", ProdId);
        if ("false".equals(repeatProd)){
            try {
               BufferedWriter bw = new BufferedWriter(new FileWriter("Product.txt",true));
              bw.write(ProdId +"/"+ProdName +"/"+FragileCheck +"/"+ ProdPrice +"/"+ProdQuan +"\n");
              JOptionPane.showMessageDialog(null,
                        "Added Successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                bw.flush();
               bw.close();
           } catch (IOException ex) {
              Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else{
        JOptionPane.showMessageDialog(null,
                        "Record Exist", "Error",
                        JOptionPane.INFORMATION_MESSAGE);
        }
    
    }
    

        
    public void addCus(String name,String password,String email,String phoneNum,String mailingAdd){
        // calling the repeatchecker to check if the previous customer exist
        String repeatCus = repeatChecker("Customer.txt", name);
        //code goes here if the customer name does not exist
        if ("false".equals(repeatCus)){
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter("Customer.txt",true));
            fw.write( name +"/"+ password  +"/"+ email +"/"+  mailingAdd +"/"+ phoneNum +"\n");
            fw.close();
            JOptionPane.showMessageDialog(null,
                        "Added Successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else{
        // do nothing if the customer name is repeated
        }
    }

    
    
public void exportChanges(JTable table, String OldFile){
    String filePath = "Temporary.txt";
    File oldFile = new File(OldFile);
    File file = new File(filePath);
    //writes all the item in the table into another textfile called Temporary.txt
    try{
        
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i<table.getRowCount();i++){
            for (int j = 0;j<table.getColumnCount();j++){
                if (j==4){
                    bw.write(table.getValueAt(i,j).toString());
                }else{
                    bw.write(table.getValueAt(i,j).toString()+"/");
                }
            }
        bw.newLine();
    
        }
        bw.close();
        fw.close();
        File dump = new File(OldFile);
        // deletes the old file and rename the new written file to the old file
        oldFile.delete();
        file.renameTo(dump);
            
    }catch (IOException ex){
        System.out.println(ex);
    }
    
    
    }
    
   
    public void deleteItems(JTable orderListTable, String FileName){
        model = (DefaultTableModel)orderListTable.getModel();
                
        //Get selected row index
        int rowSelected = orderListTable.getSelectedRow();
        //Check if a row is selected
        if(rowSelected == -1){ //return -1 if no row is selected
            
            JOptionPane.showMessageDialog(null, "Please Select an Item to delete");
        }else{
        
            model.removeRow(orderListTable.getSelectedRow());
            
            JOptionPane.showMessageDialog(null, "Item deleted");
        
            String tempFile = "tempOder.txt";
            File oldFile = new File (FileName);
            File newFile = new File (tempFile);

            //Read text file to delete record as well

            try{
                FileWriter fw = new FileWriter(tempFile,true);

                BufferedWriter bw = new BufferedWriter(fw);

                for(int i = 0 ; i < orderListTable.getRowCount(); i ++){
                    for(int j = 0 ; j < orderListTable.getColumnCount(); j ++){ 

                        bw.write(orderListTable.getValueAt(i, j).toString()+ "/");
                    }
                    bw.newLine();
                }
                bw.flush();
                bw.close();
                fw.close();
                oldFile.delete();
                File replace = new File(FileName);
                newFile.renameTo(replace);
                
            }catch(IOException e ){
                System.out.println(e);
            }
        }
    }
    public void filter(JTable table, String query){
        //gets the table structure
        model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
        table.setRowSorter(tr);
        // shows the rows that contains the item in the query
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
    
}
