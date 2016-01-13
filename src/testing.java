import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;


public class testing
{
	private static final Comparator<String> CMP = new Comparator<String>()
			{
			    @Override
			    public int compare(final String a, final String b)
			    {
			        final int inta = Integer.parseInt(a.split("\\s+")[0]);
			        final int intb = Integer.parseInt(b.split("\\s+")[0]);
			        return Integer.compare(inta, intb);
			    }
			};
			
	public static void main(String[]args)
	{
		List<Integer> listofFi = new ArrayList<Integer>();//example
		List<Integer> listofLi = new ArrayList<Integer>();//example
		List<Integer> listofAl = new ArrayList<Integer>();//example
		int policyfirstindex;
		int policylastindex;
		int policyaccesslevel;
		int fiCheckResult = 0;
		int liCheckResult = 0;
		int alCheckResult = 0;
		int mousefirstindex = 25;
		int mouselastindex = 40;
		int accesslevel = 2;
		int approve = 0;
		int fiPassed=0;
		int liPassed=0;
		
		File f = new File("./indexes/" + "test.txt" + ".policy");
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
					
					
					
					for(int k = 0; k < listofFi.size(); k++){//example
						
						if (mousefirstindex >= listofFi.get(k))
						{
							fiPassed++;
						}
					}
					
					
					
					if(fiPassed!=0)
					{
						for(int h = 0; h < listofAl.size(); h++)
						{
							
							if(listofAl.get(fiPassed-1)>= accesslevel )
							{
								approve =1;
							}
							else
							{
								approve=0;
							}
						}
					}
					else
					{
						approve=0;
					}
					
					
				}
				
				System.out.println(fiPassed);
				System.out.println(approve);
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}	
			
		}
	}

}