

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class cryptanalysis {

	
static HashMap<Integer, Integer> map;

	

public static void cryptAnalysis(ArrayList<Integer> cipherList,ArrayList<Integer> plain){
	map = new HashMap<Integer,Integer>();
	map.put(13, 0);
	map.put(7, 1);
	map.put(1, 2);
	map.put(11, 3);
	map.put(5, 4);
	map.put(9, 5);
	map.put(0, 6);
	map.put(6, 7);
	map.put(15, 8);
	map.put(4, 9);
	
	map.put(14, 10);
	map.put(10, 11);
	map.put(8, 12);
	map.put(12, 13);
	map.put(2, 14);
	map.put(3, 15);
	
	double maxVal = 0 ;
	int maxKey1=0;
	int maxKey2 = 0;
	
	
	for(int i=0;i<=15;i++) {
		for(int j=0;j<=15;j++){
			int count=0;
			for(int k=0;k<cipherList.size();k++) {
				
				int currCipher =  cipherList.get(k);
				int currPlain = plain.get(k);
				//System.out.println(currCipher+"  "+currPlain);
				int firstBlock = (int) ((currCipher>>12) & 15);
				int thirdBlock = (int) ((currCipher>>4) & 15);
				
				int xorFirstBlock = (int) (i ^ firstBlock);
				int xorSecondtBlock = (int) (j ^ thirdBlock);
				
				int sBoxFirstBlock = map.get(xorFirstBlock);
				int sBoxSecondBlock = map.get(xorSecondtBlock);
				
				int sBoxFirstBlockSecondBit =  sBoxFirstBlock>>1 & 1;
				int sBoxFirstBlockFourthBit =  sBoxFirstBlock>>3 & 1;
				
				int sBoxSecondBlockSecondBit =  sBoxSecondBlock>>1 & 1;
				int sBoxSecondBlockFourthBit =  sBoxSecondBlock>>3 & 1;
				
				int plainTextSixthBit = currPlain >> 10 &1 ;
				int plainTextEigth = currPlain >> 8 &1 ;
				
				
				int result = sBoxFirstBlockSecondBit ^ sBoxFirstBlockFourthBit ^ sBoxSecondBlockSecondBit ^ sBoxSecondBlockFourthBit ^ plainTextSixthBit ^ plainTextEigth;
				if(result == 0){
					count++;
				}
				
			}
			// System.out.println(i+" "+j+"  "+count);
			//System.out.println(count);
			double bias = Math.abs(  (double) count/65536  - 0.5  );
			 System.out.println(i+" "+j+"  "+count+"  "+bias);
			//double bias = Math.abs(count  - 65536/2  )/65536;
			if(bias>maxVal) {
				maxKey1 = i;
				maxKey2 = j;
				maxVal=bias;
				
			}
		}
		
	}
	System.out.println(maxKey1+" "+maxKey2+"  maxBias "+maxVal);
}
	
	
	
public static String readFile(String filename) {
    String result = "";
    try {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }
        result = sb.toString();
    } catch(Exception e) {
        e.printStackTrace();
    }
    return result;
}
	
	public static void main(String[] args) {
	
		String jsonData = readFile("/Users/abhishekjaitley/documents/cryptanalysis.json");
		//System.out.println(jsonData);
		ArrayList<Integer> cipherList = new ArrayList<Integer>();
		ArrayList<Integer> plainList = new ArrayList<Integer>();
		try {
			JSONObject jobj = new JSONObject(jsonData);
			  JSONArray jarr = new JSONArray(jobj.getJSONArray("texts").toString());
			  for(int i =0;i<jarr.length();i++) {
				  JSONObject currObj  = (JSONObject) jarr.get(i);
				  int ciphertext = (Integer) currObj.get("ciphertext");
				  int plaintext = (Integer) currObj.get("plaintext");
				  cipherList.add(ciphertext);
				  plainList.add(plaintext);
				 // System.out.println(ciphertext + "   "+plaintext);
				  
				  
			  }
			  
			  
			 /* cipherList = new ArrayList<Integer>();
				plainList = new ArrayList<Integer>();
				cipherList.add(22095);
				plainList.add(46);*/
			  System.out.println(cipherList.size());
			  System.out.println(plainList.size());
			  cryptAnalysis(cipherList,plainList);
 		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
	
	
}
