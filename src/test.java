import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hdgf.streams.Stream;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class test extends JPanel implements ActionListener {
	JButton go;
	JFileChooser chooser;
	String choosertitle;
	String fileName;
	File selectedFile;
	OpenFile of = new OpenFile();
	static StringBuilder build = new StringBuilder();
	String sys = System.getProperty("user.home");
	String index;
	int accessID;
	


	public test(int accessID) {
		go = new JButton("Do it");
		go.addActionListener(this);
		add(go);

	}

	public void actionPerformed(ActionEvent e) {
		of = new OpenFile();
		try {
			of.ChooseFile();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		char symbols = '\u25E6';

		File f = new File("./indexes/" + of.filename + ".policy");
		if (f.exists()) {
			try {
				
				BufferedReader br = new BufferedReader(new FileReader(f));
				String line;
				StringBuilder sb = new StringBuilder();
			    
				while ((line = br.readLine()) != null) {
					sb.append(line);
					
				
				}
				br.close();
			
				
				int accesslvl = 1;
				int access = 2;
				if (sb.toString().contains("|")) {
					build = new StringBuilder(of.sb.toString());
					String[] high = sb.toString().split(Pattern.quote("|"));
				
					for (int i = 0; i < high.length; i++) {
						String[] details = high[i].split(Pattern.quote(","));
						
						int firstindex = Integer.parseInt(details[0]);
						int lastindex = Integer.parseInt(details[1]);
						int detail = Integer.parseInt(details[2]);
						int length = lastindex - firstindex;
						char[] symbolArr = new char[length];
						Arrays.fill(symbolArr, symbols);
						String longsymbols = new String(symbolArr);
						if (detail <= accesslvl) {
							build.replace(firstindex, lastindex, longsymbols);
						}
						}
						String pdf = "pdf";
						String txt = "txt";
						String docx = "docx";
						String extension = of.filename.substring(of.filename.lastIndexOf(".") + 1, of.filename.length());
						File newfile = new File("./temp file/" + of.filename);
						
							
							
							try {
								if(pdf.equalsIgnoreCase(extension)) {
									Document doc = new Document(PageSize.A4);
									PdfWriter.getInstance(doc, new FileOutputStream("./temp file/" + of.filename));
									doc.open();
									String breaking[] = build.toString().split("\r\n|\r|\r");
									for (int count = 0; count < breaking.length; count++) {
										Paragraph para = new Paragraph();
										para.add(breaking[count]);
										System.out.println(breaking[count]);
										doc.add(para);
									}
									
									doc.close();
									
								} 
								
								else if (docx.equalsIgnoreCase(extension)) {
									 try {
									        InputStream is = new FileInputStream(of.filename); 
									        XWPFDocument doc = new XWPFDocument(is);

									        List<XWPFParagraph> paras = doc.getParagraphs();  
									        
									        XWPFDocument newdoc = new XWPFDocument(); 
									        String text[] = build.toString().split("\r\n|\r|\n");
									        int count =0;
									        for (XWPFParagraph para : paras) {  
									            if (!para.getParagraphText().isEmpty()) {       
									                XWPFParagraph newpara = newdoc.createParagraph();
									                copyAllRunsToAnotherParagraph(para, newpara,text[count]);
									                count++;
									            }

									        }
									        FileOutputStream fos = new FileOutputStream(new File("./temp file/" + of.filename));
									        newdoc.write(fos);
									        fos.flush();
									        fos.close();
									    } catch (FileNotFoundException ex) {
									        ex.printStackTrace();
									    } catch (IOException ex) {
									        ex.printStackTrace();
									    }
										
								}
							
								
								else if (txt.equalsIgnoreCase(extension)) {
									
									Writer writer = new BufferedWriter(
											new OutputStreamWriter(new FileOutputStream("./temp file/" + of.filename), Charset.forName("utf-8"))); {
										
										writer.write(build.toString());
										writer.close();
										if (Desktop.isDesktopSupported()) {
											Desktop.getDesktop().open(newfile);
										}

											}
								}
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						

						}
						
//						if (detail <= access) {
//							build.replace(firstindex, lastindex, longsymbols);
//							//System.out.println(build.toString());
//							
//							try {
//								if(pdf.equalsIgnoreCase(extension)) {
//									Document doc = new Document(PageSize.A4);
//									PdfWriter.getInstance(doc, new FileOutputStream(sys + "/Desktop/temp file 2/" + of.filename));
//									doc.open();
//									Paragraph para = new Paragraph();
//									para.add(build.toString());
//									System.out.println(para);
//									doc.add(para);
//									doc.close();
//								} 
////								if (Desktop.isDesktopSupported()) {
////									Desktop.getDesktop().open(newfile2);
//								//}
//								
//								else if (docx.equalsIgnoreCase(extension)) {
//									XWPFDocument document = new XWPFDocument();
//									XWPFParagraph paragraph = document.createParagraph();
//									XWPFRun run = paragraph.createRun();
//									
//									//run.setText(build.toString());
//									run.setText(build.toString());
//									run.addCarriageReturn();
//									
//									FileOutputStream output = new FileOutputStream(sys + "/Desktop/temp file 2/" + of.filename);
//									document.write(output);
//									output.close();
//									if (Desktop.isDesktopSupported()) {
//										Desktop.getDesktop().open(newfile2);
//									}
//								}
//							
//								
//								else if (txt.equalsIgnoreCase(extension)) {
//									
//									FileOutputStream outputStream = new FileOutputStream(sys + "/Desktop/temp file 2/" + of.filename);
//									Writer writer = new BufferedWriter(new OutputStreamWriter (outputStream));
//									while ((bytesRead = inputStream.read(buf)) > 0) {
//										outputStream.write(buf, 0, bytesRead);
////										writer.write(build.toString());
////										writer.close();
//										outputStream.close();
//										br.close();
//									}
//									/*Writer writer = new BufferedWriter(
//											new OutputStreamWriter(new FileOutputStream(sys + "/Desktop/temp file 2/" + of.filename), "utf-8")); {
//										
//										writer.write(build.toString());
//										writer.close();
//										if (Desktop.isDesktopSupported()) {
//											Desktop.getDesktop().open(newfile2);
//										}
//
//										
//								}*/
//									
//									
//							}
//								
//							
//							} catch (Exception ex) {
//								ex.printStackTrace();
//							}
//						}
						// if (detail <= accesslvl) {
						// build.replace(firstindex, lastindex, symbols);
						// System.out.println(build);
						// }
						//
						// if (detail <=accesslvl) {
						// build.replace(firstindex, lastindex, symbols);
						// System.out.println(build);
						// }
				
					
				}catch (Exception ex) {
					ex.printStackTrace();
				}
		}
				
		}

	private static void copyAllRunsToAnotherParagraph(XWPFParagraph oldPar, XWPFParagraph newPar,String text) {
		for (XWPFRun run : oldPar.getRuns()) {
	        String textInRun = run.getText(0);
	        if (textInRun == null || textInRun.isEmpty()) {
	            continue;
	        }

	        XWPFRun newRun = newPar.createRun();
	       
	        
	        // Copy text
	        newRun.setText(text);


	        // Apply the same style
//	        newRun.setFontSize( run.getFontSize() );    
	        newRun.setFontFamily( run.getFontFamily() );
	        newRun.setBold( run.isBold() );
	        newRun.setItalic( run.isItalic() );
	        newRun.setStrike( run.isStrike() );
	        newRun.setColor( run.getColor() );
	    }
	}

	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}

	public static void main(String s[]) {
		JFrame frame = new JFrame("");
		test panel = new test(2);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.getContentPane().add(panel, "Center");
		frame.setSize(panel.getPreferredSize());
		frame.setVisible(true);
	}
}
