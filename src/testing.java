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
		int mousefirstindex = 280;
		int mouselastindex = 300;
		int accesslevel = 2;
		int approve = 0;
		int fiIndex = 0;
		int liIndex = 0;
		
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
						fiIndex=findFiNearestNumber(listofFi,mousefirstindex);
					}

					for(int k = 0; k < listofLi.size(); k++)
					{
						liIndex=findLiNearestNumber(listofLi,mouselastindex);
					}
					
					for(int h = 0; h < listofAl.size(); h++)
					{
						if(listofAl.get(fiIndex)>= accesslevel)
						{
							if(listofAl.get(liIndex)>= accesslevel)
							{
								approve = 1;
							}
							else
							{
								approve = 0;
							}
						}
						else
						{
							approve=0;
						}
					}
				}
				
				System.out.println("\n");
				/*System.out.println(fiPassed);*/
				System.out.println(listofAl);
				System.out.println(listofFi.get(fiIndex));
				System.out.println(listofLi.get(liIndex));
				System.out.println(accesslevel);
				System.out.println(approve);
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}	
			
		}
	}
	
	public static int findFiNearestNumber(List<Integer> indexes, int mouseIndex)
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
	
	public static int findLiNearestNumber(List<Integer> indexes, int mouseIndex)
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