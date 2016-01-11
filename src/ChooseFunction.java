import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JLabel;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.SwingConstants;

import java.awt.Font;

public class ChooseFunction extends JFrame
{

	private JPanel contentPane;
	String fileName;
	File selectedFile;
	OpenFile of = new OpenFile();
	static StringBuilder build = new StringBuilder();
	String sys = System.getProperty("user.home");
	String index;
	int accessID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ChooseFunction frame = new ChooseFunction(0);
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChooseFunction(int accessID)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Security Policy Generator");
		btnNewButton.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		if (!Login.username.getText().equals("admin"))
		{
			btnNewButton.setVisible(false);
		} else
		{
			btnNewButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					BrowseFile browsefile = new BrowseFile(0);
					setVisible(false);
					browsefile.setVisible(true);
				}
			});
		}
		setTitle("Sub-File Level MAC");

		btnNewButton.setBounds(100, 72, 234, 23);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("File Viewer");
		btnNewButton_1.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				of = new OpenFile();
				try
				{
					of.ChooseFile();
				} catch (Exception ex)
				{
					ex.printStackTrace();
				}

				char symbols ='*';//'\u25E6';
				
				try
				{
					File f = new File("./indexes/" + of.filename + ".policy");
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
						String pdf = "pdf";
						String txt = "txt";
						String docx = "docx";
						String extension = of.filename.substring(of.filename.lastIndexOf(".") + 1, of.filename.length());
						
						File newfile = new File("./temp file/" + of.filename);
						build = new StringBuilder(of.sb.toString());
						String[] high = sb.toString().split(Pattern.quote("|"));
						for (int i = 0; i < high.length; i++)
						{
							String[] details = high[i].split(Pattern.quote(","));

							int firstindex = Integer.parseInt(details[0]);
							int lastindex = Integer.parseInt(details[1]);
							int detail = Integer.parseInt(details[2]);
							int length = lastindex - firstindex;
							char[] symbolArr = new char[length];
							Arrays.fill(symbolArr, symbols);
							String longsymbols = new String(symbolArr);

							if (detail <= accessID)
							{
								build.replace(firstindex, lastindex, longsymbols);
							}
						}
						
						try
						{
							if (pdf.equalsIgnoreCase(extension))
							{
								Document document = new Document(PageSize.A4);
								PdfWriter.getInstance(document, new FileOutputStream("./temp file/" + of.filename));
								document.open();
								String breaking[] = build.toString().split("\r\n|\r|\r");
								for (int count = 0; count < breaking.length; count++)
								{
									Paragraph para = new Paragraph();
									para.add(breaking[count]);
									document.add(para);
								}
								document.close();
								if (Desktop.isDesktopSupported())
								{
									Desktop.getDesktop().open(newfile);
								}
							}

							else if (docx.equalsIgnoreCase(extension))
							{
								try
								{
									FileInputStream is = new FileInputStream(of.filePath);
									XWPFDocument doc = new XWPFDocument(is);
									List<XWPFParagraph> paras = doc.getParagraphs();
									System.out.println(paras);
									XWPFDocument newdoc = new XWPFDocument();

									String test[] = build.toString().split("\r\n|\r|\n");
									int count = 0;
									for (XWPFParagraph para : paras)
									{
										if (!para.getParagraphText().isEmpty())
										{
											XWPFParagraph newpara = newdoc.createParagraph();
											List<XWPFRun> runs = para.getRuns();
											for (int i = runs.size() - 1; i > 0; i--)
											{
												para.removeRun(i);
											}
											XWPFRun run = runs.get(0);
											
											run.setText(test[count], 0);
											newpara.addRun(run);
											copyAllRunsToAnotherParagraph(para, newpara, test[count]);
											count++;
										}
									}
									FileOutputStream fos = new FileOutputStream(new File("./temp file/" + of.filename));
									newdoc.write(fos);
									fos.flush();
									fos.close();
									if (Desktop.isDesktopSupported())
									{
										Desktop.getDesktop().open(newfile);
									}
								} catch (FileNotFoundException ex)
								{
									ex.printStackTrace();
								} catch (IOException ex)
								{
									ex.printStackTrace();
								}
								
							}

							else if (txt.equalsIgnoreCase(extension))
							{

								Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./temp file/" + of.filename), Charset.forName("utf-8")));
								{
									writer.write(build.toString());
									writer.close();
									if (Desktop.isDesktopSupported())
									{
										Desktop.getDesktop().open(newfile);
									}
								}
							}
							
						} catch (Exception ex)
						{
							ex.printStackTrace();
						}
					}
					
					else if (f.length()==0)
					{
						String pdf = "pdf";
						String txt = "txt";
						String docx = "docx";
						String extension = of.filename.substring(of.filename.lastIndexOf(".") + 1, of.filename.length());
						
						File newfile = new File("./temp file/" + of.filename);
						build = new StringBuilder(of.sb.toString());
						
						try
						{
							if (pdf.equalsIgnoreCase(extension))
							{
								Document document = new Document(PageSize.A4);
								PdfWriter.getInstance(document, new FileOutputStream("./temp file/" + of.filename));
								document.open();
								String breaking[] = build.toString().split("\r\n|\r|\r");
								for (int count = 0; count < breaking.length; count++)
								{
									Paragraph para = new Paragraph();
									para.add(breaking[count]);
									document.add(para);
								}
								document.close();
								if (Desktop.isDesktopSupported())
								{
									Desktop.getDesktop().open(newfile);
								}
							}

							else if (docx.equalsIgnoreCase(extension))
							{
								try
								{
									FileInputStream is = new FileInputStream(of.filePath);
									XWPFDocument doc = new XWPFDocument(is);
									List<XWPFParagraph> paras = doc.getParagraphs();
									XWPFDocument newdoc = new XWPFDocument();

									String test[] = build.toString().split("\r\n|\r|\n");
									int count = 0;
									for (XWPFParagraph para : paras)
									{
										if (!para.getParagraphText().isEmpty())
										{
											XWPFParagraph newpara = newdoc.createParagraph();
											List<XWPFRun> runs = para.getRuns();
											for (int i = runs.size() - 1; i > 0; i--)
											{
												para.removeRun(i);
											}
											XWPFRun run = runs.get(0);
											run.setText(test[count], 0);
											newpara.addRun(run);
											copyAllRunsToAnotherParagraph(para, newpara, test[count]);
											count++;
										}
									}
									FileOutputStream fos = new FileOutputStream(new File("./temp file/" + of.filename));
									newdoc.write(fos);
									fos.flush();
									fos.close();
									if (Desktop.isDesktopSupported())
									{
										Desktop.getDesktop().open(newfile);
									}
								} catch (FileNotFoundException ex)
								{
									ex.printStackTrace();
								} catch (IOException ex)
								{
									ex.printStackTrace();
								}
								
							}

							else if (txt.equalsIgnoreCase(extension))
							{

								Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./temp file/" + of.filename), Charset.forName("utf-8")));
								{
									writer.write(build.toString());
									writer.close();
									if (Desktop.isDesktopSupported())
									{
										Desktop.getDesktop().open(newfile);
									}
								}
							}
							
						} catch (Exception ex)
						{
							ex.printStackTrace();
						}
					}
					
				} catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(100, 119, 234, 23);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Log out");
		btnNewButton_2.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnNewButton_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Login login = new Login();
				setVisible(false);
				login.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(170, 189, 89, 23);
		contentPane.add(btnNewButton_2);

		JLabel lblPleaseChooseWhat = new JLabel("Please choose what do you want to do.");
		lblPleaseChooseWhat.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		lblPleaseChooseWhat.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseChooseWhat.setBounds(100, 22, 234, 14);
		contentPane.add(lblPleaseChooseWhat);
	}

	private static void copyAllRunsToAnotherParagraph(XWPFParagraph oldPar, XWPFParagraph newPar, String text)
	{
		for (XWPFRun run : oldPar.getRuns())
		{
			String textInRun = run.getText(0);
			if (textInRun == null || textInRun.isEmpty())
			{
				continue;
			}

			int fontSize = run.getFontSize();

			XWPFRun newRun = newPar.createRun();

			// Copy text
			newRun.setText(text);

			// Apply the same style
			// newRun.setFontSize( run.getFontSize() );
			newRun.setFontFamily(run.getFontFamily());
			newRun.setBold(run.isBold());
			newRun.setItalic(run.isItalic());
			newRun.setStrike(run.isStrike());
			newRun.setColor(run.getColor());
		}
	}

}
