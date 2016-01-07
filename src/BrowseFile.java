import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.JTextArea;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.Window.Type;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.JLabel;

import java.awt.Font;

public class BrowseFile extends JFrame
{

	private JPanel contentPane;
	int firstIndex;
	int lastIndex;
	int policyfirstindex;
	int policylastindex;
	int mousefirstindex;
	int mouselastindex;
	int policyaccesslevel;
	String str;
	String allText;
	Highlighter hilit;
	static StringBuilder build = new StringBuilder();
	StringBuilder stringBuilder = new StringBuilder();
	OpenFile of = new OpenFile();
	Highlighter hl;
	JTextArea tfFile = new JTextArea();
	int remove = 0;
	String path;
	

	Highlighter.HighlightPainter redPainter = new MyHighlightPainter(Color.RED);
	Highlighter.HighlightPainter yellowPainter = new MyHighlightPainter(Color.YELLOW);
	Highlighter.HighlightPainter pinkPainter = new MyHighlightPainter(Color.PINK);
	Highlighter.HighlightPainter bluePainter = new MyHighlightPainter(Color.CYAN);
	Highlighter.HighlightPainter grayPainter = new MyHighlightPainter(Color. GRAY);

	/*
	 * String fileName = of.filename; File f= new File("./indexes/" + fileName +
	 * ".xml");
	 */
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
					BrowseFile frame = new BrowseFile(0);
					frame.setVisible(true);
					frame.setResizable(false);
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
	public BrowseFile(int accessID)
	{
		setTitle("Welcome to Sub-File Level MAC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1001, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfFile.setWrapStyleWord(true);
		tfFile.setLineWrap(true);

		tfFile.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				//tfFile.setCaretPosition(tfFile.viewToModel(e.getPoint()));
				firstIndex = tfFile.getCaretPosition();
				//System.out.println(firstIndex);
			}

			public void mouseReleased(MouseEvent e)
			{
				//tfFile.setCaretPosition(tfFile.viewToModel(e.getPoint()));
				lastIndex = tfFile.getCaretPosition();
				//System.out.println(lastIndex);
				/*JTextArea s = (JTextArea) e.getSource();
				System.out.println(s);
				str = s.getSelectedText();
				if (tfFile.getSelectedText() != null)
				{
					str = tfFile.getSelectedText();
					//System.out.println(str);
				}*/
			}
		});

		tfFile.setEditable(false);
		tfFile.setBounds(40, 115, 761, 430);
		tfFile.setVisible(true);
		
		contentPane.add(tfFile);
		
		JLabel lblPath = new JLabel();
		lblPath.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPath.setBounds(200, 57, 440, 50);
		contentPane.add(lblPath);
		lblPath.setVisible(false);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnBrowse.addActionListener(new ActionListener()
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
				tfFile.setText(of.sb.toString());
				choose();
				path = of.filePath;
				
				if(path!=null)
				{
					lblPath.setText("File opened: " + path);
					lblPath.setVisible(true);
				}
				else
				{
					lblPath.setText(" ");
					lblPath.setVisible(true);
				}
			}
			
		});
		btnBrowse.setBounds(40, 70, 150, 23);
		contentPane.add(btnBrowse);

		JButton btnSave = new JButton("Generate MAC Policy");
		btnSave.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnSave.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				File f = new File("./indexes/" + of.filename + ".policy");
				
				if (remove != 0)
				{
					f.delete();
					try
					{
						f.createNewFile();
					} catch (IOException e1)
					{
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(contentPane, "Save Successful");
					remove = 0;
				} 
				
				else
				{
					Highlighter.Highlight[] hL = hilit.getHighlights();
					for (int i = 0; i < hL.length; i++)
					{
						stringBuilder.append(hL[i].getStartOffset());
						stringBuilder.append(",");
						stringBuilder.append(hL[i].getEndOffset());
						stringBuilder.append(",");
						if (hL[i].getPainter() == redPainter)
						{
							stringBuilder.append("1");
						} else if (hL[i].getPainter() == yellowPainter)
						{
							stringBuilder.append("2");
						} else if (hL[i].getPainter() == pinkPainter)
						{
							stringBuilder.append("3");
						}

						else if (hL[i].getPainter() == bluePainter)
						{
							stringBuilder.append("4");
						}
						stringBuilder.append("|");
						mousefirstindex=hL[i].getStartOffset();
						mouselastindex=hL[i].getEndOffset();
						//System.out.println(mousefirstindex);
						//System.out.println(mouselastindex);
					}
					
					BufferedWriter writer = null;
					try
					{
						if (!f.exists())
							f.createNewFile();
						else
						f.setReadOnly();							
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
							//build = new StringBuilder(of.sb.toString());
							String[] high = sb.toString().split(Pattern.quote("|"));
							for (int i = 0; i < high.length; i++)
							{
								String[] details = high[i].split(Pattern.quote(","));
								
								policyfirstindex = Integer.parseInt(details[0]);//policy indexes
								List<Integer> listofFi = new ArrayList<Integer>();//example
								listofFi.add(policyfirstindex);
								
								policylastindex = Integer.parseInt(details[1]);
								List<Integer> listofLi = new ArrayList<Integer>();//example
								listofLi.add(policylastindex);
								
								policyaccesslevel = Integer.parseInt(details[2]);
								List<Integer> listofAl = new ArrayList<Integer>();//example
								listofAl.add(policyaccesslevel);
								
								
								/*System.out.println("First index: "+ policyfirstindex);
								System.out.println("Second index: "+ policylastindex);
								System.out.println("Access level: "+ policyaccesslevel);
								System.out.println("=======================");*/
								
								for(int k = 0; k < listofFi.size(); k++){//example
									System.out.println("First index: "+ listofFi.get(k));
									System.out.println("Second index: "+ listofLi.get(k));
									System.out.println("Access level: "+ listofAl.get(k));
									System.out.println();
								}
							}
							
							
						}
							
						if (f.length()==0) 
						{
							f.delete();
							writer = new BufferedWriter(new FileWriter(f.getAbsolutePath(), false));
							//System.out.print(f.getAbsolutePath());
							writer.write(stringBuilder.toString());
							//System.out.print(stringBuilder);
							stringBuilder.setLength(0);
							remove = 0;
							choose();
						}
						/*else if(policylastindex < mousefirstindex)
						{
							if (policyfirstindex < mousefirstindex)
							{
								f.delete();
								writer = new BufferedWriter(new FileWriter(f.getAbsolutePath(), false));
								//System.out.print(f.getAbsolutePath());
								writer.write(stringBuilder.toString());
								//System.out.print(stringBuilder);
								stringBuilder.setLength(0);
								remove = 0;
							}
							else
							{
								JOptionPane.showMessageDialog(contentPane, "double highlight");
								//tfFile.setText(of.sb.toString());
								stringBuilder.setLength(0);
								removeHighlights(tfFile);
								choose();
							}
						} 
						else if (policyfirstindex > mousefirstindex)
						{
							if (policyfirstindex > mouselastindex)
							{
								f.delete();
								writer = new BufferedWriter(new FileWriter(f.getAbsolutePath(), false));
								//System.out.print(f.getAbsolutePath());
								writer.write(stringBuilder.toString());
								//System.out.print(stringBuilder);
								stringBuilder.setLength(0);
								remove = 0;
							}
							else
							{
								JOptionPane.showMessageDialog(contentPane, "double highlight");
								//tfFile.setText(of.sb.toString());
								stringBuilder.setLength(0);
								removeHighlights(tfFile);
								choose();
							}
						}*/ 
						else
						{
							/*JOptionPane.showMessageDialog(contentPane, "double highlight");
							//tfFile.setText(of.sb.toString());
							stringBuilder.setLength(0);
							removeHighlights(tfFile);
							choose();*/
							
							f.delete();
							writer = new BufferedWriter(new FileWriter(f.getAbsolutePath(), false));
							//System.out.print(f.getAbsolutePath());
							writer.write(stringBuilder.toString());
							//System.out.print(stringBuilder);
							stringBuilder.setLength(0);
							remove = 0;
							choose();
						}
					} catch (Exception ex)
					{
						ex.printStackTrace();
					} finally
					{
						try
						{
							if (writer != null)
							{
								writer.close();
								JOptionPane.showMessageDialog(contentPane, "Save Successful");
								//tfFile.setText(of.sb.toString());
								stringBuilder.setLength(0);
								removeHighlights(tfFile);
								remove = 0;
								choose();
							}
						} catch (IOException ex)
						{
							ex.printStackTrace();
						}
					}
				}

			}
		});
		btnSave.setBounds(811, 462, 162, 23);
		contentPane.add(btnSave);

		JScrollPane scrollPane = new JScrollPane(tfFile);

		scrollPane.setBounds(40, 115, 761, 430);
		scrollPane.setVisible(true);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPane);

		JButton btnCategoryA = new JButton("Security Level 1");
		btnCategoryA.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnCategoryA.setBackground(Color.RED);
		btnCategoryA.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/* Highlighter highlighter = File.getHighlighter(); */
				hilit = tfFile.getHighlighter();
				if (hilit.getHighlights() != null)
				{
					Highlighter.Highlight[] hL = hilit.getHighlights();
					for (int i = 0; i < hL.length; i++)
					{
						if ((hL[i].getStartOffset() == firstIndex) && (hL[i].getEndOffset() == lastIndex))
						{
							hilit.removeHighlight(hL[i]);
						}
					}
					redHighlight(tfFile);
				}

			}
		});
		btnCategoryA.setBounds(811, 115, 162, 23);
		contentPane.add(btnCategoryA);

		JButton btnCategoryB = new JButton("Security Level 2");
		btnCategoryB.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnCategoryB.setBackground(Color.YELLOW);
		btnCategoryB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/* Highlighter highlighter = File.getHighlighter(); */
				hilit = tfFile.getHighlighter();
				if (hilit.getHighlights() != null)
				{
					Highlighter.Highlight[] hL = hilit.getHighlights();
					// redHighlight(File);
					for (int i = 0; i < hL.length; i++)
					{
						if ((hL[i].getStartOffset() == firstIndex) && (hL[i].getEndOffset() == lastIndex))
						{
							hilit.removeHighlight(hL[i]);
						}
					}
					yellowHighlight(tfFile);
				}

			}
		});
		btnCategoryB.setBounds(811, 185, 162, 23);
		contentPane.add(btnCategoryB);

		JButton btnCategoryC = new JButton("Security Level 3");
		btnCategoryC.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnCategoryC.setBackground(Color.PINK);
		btnCategoryC.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/* Highlighter highlighter = File.getHighlighter(); */
				hilit = tfFile.getHighlighter();
				if (hilit.getHighlights() != null)
				{
					Highlighter.Highlight[] hL = hilit.getHighlights();
					// redHighlight(File);
					for (int i = 0; i < hL.length; i++)
					{
						if ((hL[i].getStartOffset() == firstIndex) && (hL[i].getEndOffset() == lastIndex))
						{
							hilit.removeHighlight(hL[i]);
						}
					}
					pinkHighlight(tfFile);

				}

			}
		});
		btnCategoryC.setBounds(811, 255, 162, 23);
		contentPane.add(btnCategoryC);

		JButton btnCategoryD = new JButton("Security Level 4");
		btnCategoryD.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnCategoryD.setBackground(Color.CYAN);
		btnCategoryD.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/* Highlighter highlighter = File.getHighlighter(); */
				hilit = tfFile.getHighlighter();
				if (hilit.getHighlights() != null)
				{
					Highlighter.Highlight[] hL = hilit.getHighlights();
					// redHighlight(File);
					for (int i = 0; i < hL.length; i++)
					{
						if ((hL[i].getStartOffset() == firstIndex) && (hL[i].getEndOffset() == lastIndex))
						{
							hilit.removeHighlight(hL[i]);
						}
					}
					blueHighlight(tfFile);

				}

			}
		});
		btnCategoryD.setBounds(811, 325, 162, 23);
		contentPane.add(btnCategoryD);

		JLabel lblSecurityPolicyGenerator = new JLabel("Security Policy Generator");
		lblSecurityPolicyGenerator.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblSecurityPolicyGenerator.setBounds(296, 11, 339, 49);
		contentPane.add(lblSecurityPolicyGenerator);

		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnBack.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ChooseFunction choosefunction = new ChooseFunction(0);
				choosefunction.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(811, 518, 162, 23);
		contentPane.add(btnBack);

		JButton btnRemove = new JButton("Remove All Highlights");
		btnRemove.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnRemove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				removeHighlights(tfFile);
				remove = 1;
			}
		});
		btnRemove.setBounds(811, 395, 162, 23);
		contentPane.add(btnRemove);
	}
 
	public void choose()
	{
		File f = new File("./indexes/" + of.filename + ".policy");
		f.setReadOnly();
		if (f.exists())
		{
			try
			{

				/*
				 * FileInputStream inputStream = new FileInputStream(f);
				 * InputStreamReader reader = new InputStreamReader(inputStream,
				 * Charset.forName("utf-8")); BufferedReader br = new
				 * BufferedReader(reader);
				 */
				BufferedReader br = new BufferedReader(new FileReader(f));
				String line;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null)
				{
					sb.append(line);
				}
				br.close();
				// String plainText=Encrypt.decrypt(sb.toString());
				if (sb.toString().contains("|"))
				{
					String[] high = sb.toString().split(Pattern.quote("|"));
					hilit = tfFile.getHighlighter();
					for (int i = 0; i < high.length; i++)
					{
						String[] details = high[i].split(Pattern.quote(","));
						if (details[2].equalsIgnoreCase("1"))
						{
							hilit.addHighlight(Integer.parseInt(details[0]), Integer.parseInt(details[1]), redPainter);
						}
						else if (details[2].equalsIgnoreCase("2"))
						{
							hilit.addHighlight(Integer.parseInt(details[0]), Integer.parseInt(details[1]), yellowPainter);
						} 
						else if (details[2].equalsIgnoreCase("3"))
						{
							hilit.addHighlight(Integer.parseInt(details[0]), Integer.parseInt(details[1]), pinkPainter);
						} 
						else if (details[2].equalsIgnoreCase("4"))
						{
							hilit.addHighlight(Integer.parseInt(details[0]), Integer.parseInt(details[1]), bluePainter);
						}
					}
				}
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}

		}
	}

	class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter
	{
		public MyHighlightPainter(Color color)
		{
			super(color);

		}
	}
	
	class HighlightPainter extends DefaultHighlighter.DefaultHighlightPainter
	{
		public HighlightPainter(Color c)
		{
			super(c);
		}
	}
	
	public void redHighlight(JTextComponent textComp)
	{

		try
		{
			hilit = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());

			/*
			 * hilite.addHighlight(allText.indexOf(str), allText.indexOf(str) +
			 * str.length(), myHighlightPainter);
			 */
			if (firstIndex < lastIndex)
			{
				hilit.addHighlight(firstIndex, lastIndex, redPainter);
			} else
				hilit.addHighlight(lastIndex, firstIndex, redPainter);

		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	public void pinkHighlight(JTextComponent textComp)
	{

		try
		{
			hilit = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());

			/*
			 * hilite.addHighlight(allText.indexOf(str), allText.indexOf(str) +
			 * str.length(), myHighlightPainter);
			 */
			if (firstIndex < lastIndex)
			{
				hilit.addHighlight(firstIndex, lastIndex, pinkPainter);
			} else
				hilit.addHighlight(lastIndex, firstIndex, pinkPainter);

		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	public void yellowHighlight(JTextComponent textComp)
	{

		try
		{
			hilit = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());

			/*
			 * hilite.addHighlight(allText.indexOf(str), allText.indexOf(str) +
			 * str.length(), myHighlightPainter);
			 */
			if (firstIndex < lastIndex)
			{
				hilit.addHighlight(firstIndex, lastIndex, yellowPainter);
			} else
				hilit.addHighlight(lastIndex, firstIndex, yellowPainter);

		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	public void blueHighlight(JTextComponent textComp)
	{

		try
		{
			hilit = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());

			/*
			 * hilite.addHighlight(allText.indexOf(str), allText.indexOf(str) +
			 * str.length(), myHighlightPainter);
			 */
			if (firstIndex < lastIndex)
			{
				hilit.addHighlight(firstIndex, lastIndex, bluePainter);
			} else
				hilit.addHighlight(lastIndex, firstIndex, bluePainter);

		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	public void removeHighlights(JTextComponent textComp)
	{

		Highlighter hilite = textComp.getHighlighter();
		Highlighter.Highlight[] hilites = hilite.getHighlights();

		for (int i = 0; i < hilites.length; i++)
		{
			if (hilites[i].getPainter() instanceof MyHighlightPainter)
			{
				hilite.removeHighlight(hilites[i]);
			}
		}
	}

	public void hilite(JTextComponent textComp)
	{
		try
		{
			hl = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());
		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}
}