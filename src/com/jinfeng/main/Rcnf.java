package com.jinfeng.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * This class includes several key methods in ActionRule Algorithm
 * @author Jinfeng Du  Shanzhen Gao
 *
 */
public class Rcnf {

	

	/**
	 * This method takes in the Dscernable Table. And stores the reduct in a hashset.
	 * 
	 */
	public static HashSet<String> ruleSets(String[][] cnf) {
         ArrayList<List<String>> acnf = new ArrayList<List<String>>();
         
		//cnf - dtable to acnf: if have null remove
         for (int i = 0; i < cnf[0].length; i++){
        	 ArrayList<String> tempList = new ArrayList<String>();
        	 for (int j = 0; j < cnf.length; j++){
        		 if (cnf[j][i] != null){
        			 tempList.add(cnf[j][i]);
        		 }
        	 }
        	 acnf.add(tempList);
         }
		
		// remove duplicates again and store them in a new arraylist
		ArrayList<List<String>> finalcnf = new ArrayList<List<String>>();
		
		for (int i = 0; i < acnf.size(); i++){
			HashSet<String> hs = new HashSet<String>();
			
			for (int j = 0; j < acnf.get(i).size(); j++){
				hs.add(acnf.get(i).get(j));
			}
			
			ArrayList<String> tList = new ArrayList<String>();
			for (String s: hs){
				tList.add(s);
			}
			
			finalcnf.add(tList);
			
		}
		
		
		
		
		
		//PS. now finalcnf is with different sizes of arraylists without null
		
		//take care of finalcnf. Store the result in the hashset resultHs.
		HashSet<String> resultHs = new HashSet<String>();
		
		for (int i = 0; i < finalcnf.size(); i++){
			
			//take care of each element of finalcnf
			HashSet<String> each = new HashSet<String>();
			String[] tempArray = null;
			for (int j = 0; j < finalcnf.get(i).size(); j++){
				
				
				
				if (tempArray == null) {
				    tempArray = finalcnf.get(i).get(j).replace('+', 'กล').split("กล");
				    
					continue;
				}
				each = new HashSet<String>();
				
				String target = finalcnf.get(i).get(j);
				String[] targetArray = target.replace('+', 'กล').split("กล");
				
				for (int h = 0; h < tempArray.length; h++){
					for (int k = 0; k < targetArray.length; k++){
						if (tempArray[h].contains(targetArray[k])){
							each.add(tempArray[h]);
						}else {
							 each.add(new StringBuilder(tempArray[h]).append(targetArray[k]).toString());
						}
					}
					each = Rcnf.sortSets(each);//test
				}
				
				//tempArray = (String[])each.toArray();
				
				tempArray = new String[each.size()];
				int tempzero = 0;
				
				
				
				for (String s: each){
					tempArray[tempzero] = s;
					tempzero++;
				}
				
				
				
				
			}
			// add each to the result hashset
			HashSet<String> tempeach = Rcnf.sortSets(each);
            for(String s: tempeach){
            	resultHs.add(s);
            	
            }
			
		}
		//PS. Now hashset resultHs is finished.
		
		return resultHs;
		
	}



	/**
	 * This method sort the action reduct, removing duplicates, removing non-needed element.
	 * For example, a1b1 compared to b1, we remove b1 and keep a1b1.
	 * @param hs
	 * @return 
	 */
	public static HashSet<String> sortSets(HashSet<String> hs){
		int size = hs.size();
		
		//String[] arr = (String[])hs.toArray();
		String[] arr = new String[size];
		int count = 0;
		for (String s: hs){
			arr[count] = s;
			count++;
		}
		for (int i = 0; i < arr.length; i++){
			
			for (int j = 0; j < arr.length; j++){
				// compare arr[i] and array[j]
				// if array[j] includes all element of array[i], array[j] = array[i]
				 String[] tempArrI = new String[arr[i].length()/2];
				 for (int k = 0; k < arr[i].length(); k+=2){
	        		   int kk = k;
	        		   tempArrI[kk/2] = arr[i].substring(k,k+2);
	        	 }
				 int flag = 0;
				for (int m = 0; m < tempArrI.length; m++){
					if (!arr[j].contains(tempArrI[m])){
						flag = 1;
					}
				}
				
				if (flag == 1){// not equal or include
					
				}else{
					arr[j] = arr[i];
				}
	        	  
				
				
			}
		}
		
	// move the new arr to the return hashset
		HashSet<String> hSet = new HashSet<String>();
		for(String s: arr){
			hSet.add(s);
		}
		return hSet;
	}
	
	
	public static HashSet<String> setsInOrder(HashSet<String> hs){
		 //sort hs according to the letters
        // set a new hashset hs2
        
        HashSet<String> hs2 = new HashSet<String>();
        for (String s: hs){
     	   
     	   String[] temp = new String[s.length()/2];
     	
     	   for (int i = 0; i < s.length(); i+=2){
     		   int ii = i;
     		   temp[ii/2] = s.substring(i,i+2);
     	   }
     	   Arrays.sort(temp);
     	   String resultString = "";
     	   for (int i = 0; i < temp.length;i++){
     		   resultString = resultString.concat(temp[i]);
     	   }
     	  hs2.add(resultString);
        }
        return hs2;
	}
	
	
	/**
	 * Print the ActionRules
	 * @param hs
	 * @param special
	 */
	public static void printRule(HashSet<String> hs, String special){
		System.out.println("\n Action Rules");
		Main.dTableText.append("\n Action Rules\n");
        for(String s:hs)
        {
        	  String[] temp = new String[s.length()/2];
           	
        	   for (int i = 0; i < s.length(); i+=2){
        		   int ii = i;
        		   temp[ii/2] = s.substring(i,i+2);
        		   System.out.print("("+s.substring(i,i+1)+", --> " + temp[ii/2]+")");
        		   Main.dTableText.append("("+s.substring(i,i+1)+", --> " + temp[ii/2]+")");
        	   }
        	   
               System.out.println(" => ("+special.toUpperCase()+", "+special+"1 --> "+special+"2)");
               Main.dTableText.append(" => ("+special.toUpperCase()+", "+special+"1 --> "+special+"2)\n");
        }
	}
	
}
