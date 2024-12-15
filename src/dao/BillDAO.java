package dao;

import adt.ArrayList;
import adt.ListInterface;
import entity.*;

import java.io.*;

/**
 *
 * @author ZY
 */
public class BillDAO {
    
    Receipt receipt = new Receipt();
    
    public void writeBill(ListInterface<Bill> itemsList){
        try{
            FileOutputStream writeFile = new FileOutputStream("billOrder.txt");
            ObjectOutputStream oos = new ObjectOutputStream(writeFile);
            oos.writeObject(itemsList);
        }
        catch(IOException e){
            System.out.println("Error in writing file!");
        }
    } 
    public ListInterface<Bill> readBill(){  
            ListInterface<Bill> itemsList = new ArrayList<>(); 
        try{
            FileInputStream readFile = new FileInputStream("billOrder.txt");
            ObjectInputStream ois = new ObjectInputStream(readFile);
            return (ListInterface<Bill>) ois.readObject();
        } 
        catch (ClassNotFoundException | IOException e) {
            System.out.println("Error in reading file!");
        }
        return itemsList;
    }
    
    public int readReceiptID(){
        int newReceiptNo = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("receiptNo.txt"))) {
            String line = br.readLine();
            if (line != null) {
                newReceiptNo = Integer.parseInt(line);
            }
        } 
        catch (IOException e) {
        System.out.println("Error in reading file!");
    }
    return newReceiptNo;
    
    }
    public void writeReceiptNo(int newReceiptNo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("receiptNo.txt"))) {
            bw.write(String.valueOf(newReceiptNo));
        } 
        catch(IOException e) {
            System.out.println("Error in writing file!");
        }
    }
}
