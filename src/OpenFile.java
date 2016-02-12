import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class OpenFile
{
	JFileChooser fc = new JFileChooser();

	StringBuilder sb = new StringBuilder();
	String filename;
	String filePath;
	XWPFDocument document;

	public void ChooseFile() throws Exception
	{
		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Text Documents", "txt"));
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Word Documents", "docx"/*, "xlsx", "pptx"*/));
		fc.addChoosableFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
		//fc.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
		fc.setAcceptAllFileFilterUsed(true);

		int result = fc.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = fc.getSelectedFile();
			filename = selectedFile.getName();
			filePath = selectedFile.getAbsolutePath();
			String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());

			String pdf = "pdf";
			String txt = "txt";
//			String doc = "doc";
			String docx = "docx";
//			String xlsx = "xlsx";

			if (pdf.equalsIgnoreCase(extension))
			{
				PDFManager pdfManager = new PDFManager();
				pdfManager.setFilePath((selectedFile.getAbsolutePath()));
				sb = new StringBuilder(pdfManager.ToText());
			}

			/*else if (xlsx.equalsIgnoreCase(extension))
			{
				ExcelReader excelReader = new ExcelReader();
				excelReader.setInputFile(selectedFile.getAbsolutePath());
				sb = new StringBuilder(excelReader.read());
			} 
			else if (doc.equalsIgnoreCase(extension))
			{
				DocReader docReader = new DocReader();
				docReader.setFileName(selectedFile.getAbsolutePath());
				sb = new StringBuilder(docReader.DocToText());

			}*/ 
			else if (docx.equalsIgnoreCase(extension))
			{
				DocReader docReader = new DocReader();
				docReader.setFilePath(selectedFile.getAbsolutePath());
				sb = new StringBuilder(docReader.DocxToText());
			} 
			else if (txt.equalsIgnoreCase(extension))
			{

				java.io.File file = fc.getSelectedFile();
				Scanner input = new Scanner(file);
				while (input.hasNext())
				{
					sb.append(input.nextLine());
					sb.append("\r\n");
				}
				input.close();
			}
		} else
		{
			sb.append(" ");
		}
	}
}
