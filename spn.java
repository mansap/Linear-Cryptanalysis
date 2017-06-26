/* Authors: Abhishek Jaitley, Mansa Pabbaraju */
import java.util.ArrayList;
import java.util.HashMap;
public class spn 
{
	static ArrayList<String> lst = new ArrayList<String>();
	static int[][] result = new int[16][16];
	public static void main(String[] args)
	{
		HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
		String s  = "0000"; 
		spn ss = new spn();
		recurse(s.toCharArray(),0);
		map = ss.createSBlock();
		int[][] result = ss.linearApprox(map);	
		for(int i = 0; i < 16; i++)
		{
			for(int j = 0 ; j< 16;j++)
			{
				System.out.print(result[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public HashMap<Integer,Integer> createSBlock()
	{
		HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
		map.put(0, 13);
		map.put(1, 7);
		map.put(2, 1);
		map.put(3, 11);
		map.put(4, 5);
		map.put(5, 9);
		map.put(6, 0);
		map.put(7, 6);
		map.put(8, 15);
		map.put(9, 4);
		map.put(10, 14);
		map.put(11, 10);
		map.put(12, 8);
		map.put(13, 12);
		map.put(14, 2);
		map.put(15, 3);
		return map;
	}
	
	
	
	public static void recurse(char[] arr,int index) 
	{

		if(index == arr.length) 
		{

			String s = new String(arr);

			//System.out.println(s);

			if(!lst.contains(s)) 
			{

				lst.add(s);

			}

			return;

		}


		String s = new String(arr);

		//System.out.println(s);

		if(!lst.contains(s)) 
		{

			lst.add(s);

		}

		recurse(arr,index+1);

		arr[index]='1';

		recurse(arr,index+1);

		arr[index]='0';


		}
	
	public int[][] linearApprox(HashMap<Integer,Integer> map)
	{
		String x_equation;
		String y_equation;
		int i =0;
		int j = 0;
		int m = 0 ;
		int matches = 0;
		for(i =0; i < 16; i++)
		{
			x_equation = lst.get(i);
			//System.out.println("Hello");
			
			for(m = 0; m < 16; m++)
			{
				y_equation = lst.get(m);
				matches = 0;
			//for each such x equation
			for(j = 0; j < 16;j++)
			{				
				//for every possible x input value
				int bb = j &i;
				int x_res = Integer.bitCount(bb);
				if(x_res%2 == 0)
				{
					x_res = 0;
				}
				else
				{
					x_res = 1;
				}
				//find corresponding y output value
				int y = map.get(j); 
				int cc = y & m;
				int y_res = Integer.bitCount(cc);;
				if(y_res%2 == 0)
				{
					y_res = 0;
				}
				else
				{
					y_res = 1;
				}
				
				
				if(x_res == y_res)
				{
					matches++;
				}				
			}
			result[i][m] = matches -8;
			}		
		}
		return result;
	}
}
