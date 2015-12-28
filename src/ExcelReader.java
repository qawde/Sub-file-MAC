import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelReader
{
	private String inputFile;

	public void setInputFile(String inputFile)
	{
		this.inputFile = inputFile;
	}

	public String read() throws IOException
	{
		File inputWorkbook = new File(inputFile);
		Workbook w;

		try
		{
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);

			for (int j = 0; j < sheet.getColumns(); j++)
			{
				for (int i = 0; i < sheet.getRows(); i++)
				{
					Cell cell = sheet.getCell(j, i);
					CellType type = cell.getType();
					if (type == CellType.LABEL)
					{
						System.out.println("I got a label" + cell.getContents());
					}

					if (type == CellType.NUMBER)
					{
						System.out.println("I got a number" + cell.getContents());
					}
				}
			}
		} catch (BiffException e)
		{
			e.printStackTrace();
		}
		return inputFile;
	}
}
