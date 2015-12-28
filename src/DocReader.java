import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFHeaderFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class DocReader
{

	private String Text;
	private String filePath;
	private File file;
	private String fileName;
	private String paragraph;
	private String head;
	private String foot;
	private static String Texts;

	public String DocxToText() throws IOException
	{

		file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);
		XWPFDocument doc = new XWPFDocument(inputStream);
		XWPFWordExtractor ext = new XWPFWordExtractor(doc);

		String paraText = ext.getText();

		return paraText;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	private static void copyAllRunsToAnotherParagraph(XWPFParagraph oldPar)
	{
		for (XWPFRun run : oldPar.getRuns())
		{
			Texts = run.getText(0);
			if (Texts == null || Texts.isEmpty())
			{
				continue;
			}
			// run.setText(Texts);
			run.getFontSize();
			run.getUnderline();
			run.getFontFamily();
			run.getColor();
			run.getFontSize();
			run.isBold();
			run.isItalic();
			run.isStrike();

		}
	}

	public String DocToText() throws IOException
	{

		file = new File(fileName);
		FileInputStream inputStream = new FileInputStream(file);

		HWPFDocument doc = new HWPFDocument(inputStream);
		WordExtractor extract = new WordExtractor(doc);

		Text = extract.getText();
		return Text;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

}
