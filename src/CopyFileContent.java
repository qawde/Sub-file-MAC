import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class CopyFileContent
{
	public static void main(String[] args)
	{
		try
		{
			InputStream is = new FileInputStream("testing.docx");
			XWPFDocument doc = new XWPFDocument(is);

			List<XWPFParagraph> paras = doc.getParagraphs();

			XWPFDocument newdoc = new XWPFDocument();
			for (XWPFParagraph para : paras)
			{

				if (!para.getParagraphText().isEmpty())
				{
					XWPFParagraph newpara = newdoc.createParagraph();
					copyAllRunsToAnotherParagraph(para, newpara);
				}

			}

			FileOutputStream fos = new FileOutputStream(new File("newfile3.docx"));
			newdoc.write(fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// Copy all runs from one paragraph to another, keeping the style unchanged
	private static void copyAllRunsToAnotherParagraph(XWPFParagraph oldPar, XWPFParagraph newPar)
	{
		final int DEFAULT_FONT_SIZE = 10;

		for (XWPFRun run : oldPar.getRuns())
		{
			String textInRun = run.getText(0);
			if (textInRun == null || textInRun.isEmpty())
			{
				continue;
			}

			int fontSize = run.getFontSize();
			System.out.println("run text = '" + textInRun + "' , fontSize = " + fontSize);

			XWPFRun newRun = newPar.createRun();

			// Copy text
			newRun.setText(textInRun);

			// Apply the same style
			newRun.setFontSize(run.getFontSize());
			newRun.setFontFamily(run.getFontFamily());
			newRun.setBold(run.isBold());
			newRun.setItalic(run.isItalic());
			newRun.setStrike(run.isStrike());
			newRun.setColor(run.getColor());
		}
	}
}
// public static void main(String[] args) {
//
// /* Source file, from which content will be copied */
// File sourceFile = new File("C:\\Users\\L31209\\Desktop\\Currently.docx");
//
// /* destination file, where the content to be pasted */
// File destFile = new File("file2.docx");
//
// /* if file not exist then create one */
// if (!destFile.exists()) {
// try {
// destFile.createNewFile();
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
//
// InputStream input = null;
// OutputStream output = null;
//
// try {
//
// /* FileInputStream to read streams */
// input = new FileInputStream(sourceFile);
//
// /* FileOutputStream to write streams */
// output = new FileOutputStream(destFile);
//
// byte[] buf = new byte[1024];
// int bytesRead;
//
// while ((bytesRead = input.read(buf)) > 0) {
// output.write(buf, 0, bytesRead);
// }
//
// } catch (FileNotFoundException e) {
// e.printStackTrace();
// } catch (IOException e) {
// e.printStackTrace();
// }
//
// finally {
// try {
//
// if (null != input) {
// input.close();
// }
//
// if (null != output) {
// output.close();
// }
//
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
// }

