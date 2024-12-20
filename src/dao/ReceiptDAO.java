package dao;

import entity.Receipt;

import java.io.*;

/**
 *
 * @author Choo Zheng Yi
 */
public class ReceiptDAO {
    
    Receipt receipt = new Receipt();
    
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
        e.printStackTrace();
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
