package dao;

import adt.HashMap;

import java.io.*;
import java.time.LocalDate;

/**
 *
 * @author Choo Zheng Yi
 */
public class SalesReportDAO {
    
    
    public void writeReport(HashMap<LocalDate, Double> salesReports){
        try{
            FileOutputStream writeFile = new FileOutputStream("salesReport.dat");
            ObjectOutputStream oos = new ObjectOutputStream(writeFile);
            oos.writeObject(salesReports);
            oos.close();
        }
        catch(IOException e){
            System.out.println("Error in writing file!!!!!");
        }
    } 
    public HashMap<LocalDate, Double> readReport(){  
        HashMap<LocalDate, Double> salesReports = new HashMap<>();
        try{
            FileInputStream readFile = new FileInputStream("salesReport.dat");
            ObjectInputStream ois = new ObjectInputStream(readFile);
            
            salesReports = (HashMap<LocalDate, Double>) ois.readObject();
            ois.close();
        } 
        catch (ClassNotFoundException | IOException e) {
            System.out.println("No records found!" + e.getMessage());
        }
        return salesReports;
    }
}
