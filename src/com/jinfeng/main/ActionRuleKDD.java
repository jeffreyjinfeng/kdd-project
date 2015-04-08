package com.jinfeng.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;



/**
 * This class read file from the User Interface and Implement the ActionRule Algorithm on the selected file
 * @author Jinfeng Du  Gaoshan Zhen
 *
 */
public class ActionRuleKDD {
	
	
	/**
	 * This method read from the uploaded file and convert it to a two - dimensional ArrayList
	 * 
	 * 
	 */
	public static ArrayList<ArrayList<String>> parseCSV(File newFile){
		ArrayList<ArrayList<String>> datasets = new ArrayList<ArrayList<String>>();
		
		
    	//Input file which needs to be parsed
       // String fileToParse = fileRoute;
        BufferedReader fileReader = null;
         
        //Delimiter used in CSV file
        final String DELIMITER = ",";
        try
        {
            String line = "";
            //Create the file reader
           // fileReader = new BufferedReader(new FileReader(fileToParse));
            fileReader = new BufferedReader(new FileReader(newFile));
             
            //Read the file line by line
            while ((line = fileReader.readLine()) != null) 
            {
            	ArrayList<String> temp = new ArrayList<String>();
                //Get all tokens available in line
                String[] tokens = line.split(DELIMITER);
                for(String token : tokens)
                {
                    temp.add(token);
                }
                datasets.add(temp);
               
            }
           
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally
        {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return datasets;
    }
	
	
	/**
	 * This method implements the several steps of ActionRule Algorithm
	 * 
	 */
	public static void actionRule(ArrayList<ArrayList<String>> dataset) {
		//Split the dataset based on the decision 
				//d1 -> dataset1, d2 -> dataset2
				int lengthOfElement;
				int numberOfAttribute;
				int d1Count = 0;
				int d2Count = 0;
				
				lengthOfElement = dataset.get(0).size();
				numberOfAttribute = lengthOfElement - 1;
				
				ArrayList<ArrayList<String>> dataset1 = new ArrayList<ArrayList<String>>();//store items with D1
				ArrayList<ArrayList<String>> dataset2 = new ArrayList<ArrayList<String>>();//store items with D2
				String special  = dataset.get(0).get(lengthOfElement - 1);
				special = special.substring(0, 1);
				for (int i = 0; i < dataset.size(); i++){
					if (dataset.get(i).get(lengthOfElement - 1).equals(special.concat("1"))){
						ArrayList<String> temp = dataset.get(i);
						temp.remove(lengthOfElement - 1);//remove decision
						dataset1.add(temp);
						d1Count++;
					}else {//==d2
						ArrayList<String> temp = dataset.get(i);
						temp.remove(lengthOfElement - 1);//remove decision
						dataset2.add(temp);
						d2Count++;
					}
				}
				
				
				//build Discernable Table based on dataset1 and dataset2
				String str;
				String dTable[][]=new String[d1Count][d2Count]; // Discernable Table
				 for(int i=0;i<d1Count;i++)
		         {
		            for(int j=0;j<d2Count;j++)
		             {
		                 str=""; 
		                 for(int k=0;k<numberOfAttribute;k++)
		                 {
		                	 if(!dataset1.get(i).get(k).trim().equalsIgnoreCase(dataset2.get(j).get(k).trim()))
		                     {
		                		 if(str.trim().length()==0)
		                         {
		                			 str=dataset2.get(j).get(k);       
		                         }
		                		 else{
		                			 str=str+"+" + dataset2.get(j).get(k);
		                		 }
		                                   
		                     }
		                
		                 }
		                 if(str.length()==0)
		                     str=null;
		                 dTable[i][j]=str;
		             }
		         }
				
				 
				 //print dTable
				 //test
				 System.out.println("dTable");
				 for (int i = 0; i < dTable.length; i++){
					 System.out.println();
					 for (int j = 0; j < dTable[0].length; j++){
						 System.out.print(dTable[i][j]+" ");
					 }
				 }
				
				 System.out.println();
				
                 
				 
		         HashSet<String> hs = Rcnf.ruleSets(dTable);
		         HashSet<String> hss = Rcnf.sortSets(hs);
		         HashSet<String> hsss = Rcnf.setsInOrder(hss);
		         Rcnf.printRule(hsss, special);
		         
		         
		         
		         
		         
		         
		         
	}
	


/**
 * This method diplays the file content in the User Interface
 * 
 */
public static void displayDataset(ArrayList<ArrayList<String>> dataset){
	if (dataset == null){
		Main.log.append("\n     Cannot detect content of the file. Please Upload another file");
	}else{
		for (int i = 0; i < dataset.size(); i++){
			for (int j = 0; j < dataset.get(i).size(); j++){
				Main.log.append(dataset.get(i).get(j) + ", ");
			}
			Main.log.append("\n");
			
		}
		
	}
	
}


}


