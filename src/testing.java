import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;


public class testing
{	
	public static void main(String[]args)
	{
		List<Integer> listofFi = new ArrayList<Integer>();//example
		List<Integer> listofLi = new ArrayList<Integer>();//example
		List<Integer> listofAl = new ArrayList<Integer>();//example
		int policyfirstindex;
		int policylastindex;
		int policyaccesslevel;
		int mousefirstindex =328;
		int mouselastindex = 769;
		int accesslevel = 4;
		int approve = 0;
		int indexOne = 0;
		int indexTwo = 0;
		
		File f = new File("./indexes/" + "Interimtest.txt" + ".policy");
		f.setReadOnly();
		
		if (f.exists())
		{
			try
			{
				BufferedReader br = new BufferedReader(new FileReader(f));
				String line;
				StringBuilder sb = new StringBuilder();
			
				while ((line = br.readLine()) != null)
				{
					sb.append(line);
				}
				br.close();
				
				if (sb.toString().contains("|"))
				{
					String[] high = sb.toString().split(Pattern.quote("|"));
					/*System.out.println(Arrays.toString(high));*/
					for (int i = 0; i < high.length; i++)
					{
						String[] details = high[i].split(Pattern.quote(","));
						policyfirstindex = Integer.parseInt(details[0]);//policy indexes
						listofFi.add(policyfirstindex);
						
						policylastindex = Integer.parseInt(details[1]);
						listofLi.add(policylastindex);
						
						policyaccesslevel = Integer.parseInt(details[2]);
						listofAl.add(policyaccesslevel);
						
					}
					/*System.out.println("\n");*/
					System.out.println(listofFi);
					System.out.println(listofLi);
					System.out.println(listofAl);
					
					for(int k = 0; k < listofFi.size(); k++)
					{
						indexOne = findNearestNumber(listofFi,mousefirstindex);
					}

					for(int k = 0; k < listofLi.size(); k++)
					{
						indexTwo = findNearestNumber(listofLi,mouselastindex);
					}
					
					
					if(listofFi.get(indexOne)>= mousefirstindex  && listofLi.get(indexOne) > mousefirstindex)
					{
						if(listofFi.get(indexOne)> mouselastindex)
						{
							approve =1;
						}
						else if(listofFi.get(indexOne)< mouselastindex)
						{
							int checkMiddleAl=middleIndexes(listofFi,listofAl,mousefirstindex,mouselastindex);
							if(checkMiddleAl<accesslevel)
							{
								approve=0;
							}
							else
							{
								approve=1;
							}
						}
						else if(mouselastindex >= listofFi.get(indexOne)&& listofLi.get(indexOne) >= mouselastindex)
						{
							if(listofAl.get(indexOne)>= accesslevel)
							{
								approve=1;
							}
							else
							{
								approve=0;
							}
						}
						else
						{
							if(listofAl.get(indexTwo)>= accesslevel)
							{
								approve=1;
							}
							else
							{
								approve=0;
							}
						}
					}	
					else if(listofFi.get(indexOne)< mousefirstindex && mousefirstindex >= listofLi.get(indexOne))
					{
						if(listofFi.get(indexOne)== listofFi.get(indexTwo) && listofLi.get(indexOne)== listofLi.get(indexTwo))
						{
							if(listofLi.get(indexOne)<mousefirstindex && listofLi.get(indexOne)<mouselastindex)
							{
								approve=1;
							}
						}
						
						else if(listofFi.get(indexTwo)> mouselastindex)
						{
							approve=1;
						}
						
						else
						{
							if(listofAl.get(indexTwo)>= accesslevel)
							{
								approve=1;
							}
							else
							{
								approve=0;
							}
						}
						
					}	
					else
					{
						if(listofAl.get(indexOne)>= accesslevel && listofAl.get(indexTwo)>= accesslevel)
						{
							approve=1;
						}
						else
						{
							approve=0;
						}
					}
				}
				
				/*System.out.println("\n");
				System.out.println(fiPassed);
				System.out.println(listofAl);
				
				System.out.println(listofFi.get(indexOne));
				System.out.println(listofLi.get(indexOne));
				System.out.println(listofFi.get(indexTwo));
				System.out.println(listofLi.get(indexTwo));
				
				System.out.println(accesslevel);
				System.out.println(approve);*/
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}	
			
		}
	}
	public static int middleIndexes(List<Integer> listofFi,List<Integer> listofAl, int mousefirstindex, int mouselastindex)
	{
		List<Integer> listofIndexes = new ArrayList<Integer>();
		int accesslevel=5;
		for(int i=0; i<listofFi.size(); i++)
		{
			if(listofFi.get(i)>mousefirstindex && mouselastindex>listofFi.get(i))
			{
				listofIndexes.add(i);
			}
		}
		for(int j=0; j<listofIndexes.size(); j++)
		{
			if(listofAl.get(listofIndexes.get(j)) < accesslevel)
			{ 
				accesslevel=listofAl.get(listofIndexes.get(j));
			}
		}
		return accesslevel;
	}
	
	public static int findNearestNumber(List<Integer> indexes, int mouseIndex)
	{
	    int min=0,max=0,nearestNumber,index;

	    for(int i=0; i<indexes.size(); i++)
	    {
	        if(indexes.get(i)<mouseIndex)
	        {
	            if(min==0)
	            {
	                min=indexes.get(i);
	            }
	            else if(indexes.get(i)>min)
	            {
	                min=indexes.get(i);
	            }
	        }
	        else if(indexes.get(i)>mouseIndex)
	        {
	            if(max==0)
	            {
	                max=indexes.get(i);
	            }
	            else if(indexes.get(i)<max)
	            {
	                max=indexes.get(i);
	            }
	        }
	        else
	        {
	            return indexes.get(i);
	        }
	    }

	    if(Math.abs(mouseIndex-min)<Math.abs(mouseIndex-max))
	    {
	        nearestNumber=min;
	        index = indexes.indexOf(nearestNumber);
	    }
	    else
	    {
	        nearestNumber=max;
	        index = indexes.indexOf(nearestNumber);
	    }

	    return index;
	}
}