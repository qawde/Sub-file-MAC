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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
	int mousefirstindex;
	int mouselastindex;	
	int remove = 0;
	int accesslevel=0;
	String str;
	String path;
	String allText;
	Highlighter hl;
	Highlighter hilit;
	static StringBuilder build = new StringBuilder();
	StringBuilder stringBuilder = new StringBuilder();
	OpenFile of = new OpenFile();
	JTextArea tfFile = new JTextArea();

	Highlighter.HighlightPainter redPainter = new MyHighlightPainter(Color.RED);
	Highlighter.HighlightPainter yellowPainter = new MyHighlightPainter(Color.YELLOW);
	Highlighter.HighlightPainter pinkPainter = new MyHighlightPainter(Color.PINK);
	Highlighter.HighlightPainter bluePainter = new MyHighlightPainter(Color.CYAN);
	Highlighter.HighlightPainter grayPainter = new MyHighlightPainter(Color. GRAY);
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
		contentPane.setBackground(new Color(240, 250, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfFile.setWrapStyleWord(true);
		tfFile.setLineWrap(true);

		tfFile.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				firstIndex = tfFile.getCaretPosition();
			}

			public void mouseReleased(MouseEvent e)
			{
				lastIndex = tfFile.getCaretPosition();
			}
		});

		tfFile.setEditable(false);
		tfFile.setBounds(40, 115, 760, 430);
		tfFile.setVisible(true);
		
		contentPane.add(tfFile);
		
		JLabel lblSecurityPolicyGenerator = new JLabel("Security Policy Generator");
		lblSecurityPolicyGenerator.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblSecurityPolicyGenerator.setBounds(296, 11, 339, 49);
		contentPane.add(lblSecurityPolicyGenerator);
		
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
				accesslevel=1;
				hilit = tfFile.getHighlighter();
				if (hilit.getHighlights() != null)
				{
					Highlighter.Highlight[] hL = hilit.getHighlights();
					for (int i = 0; i < hL.length; i++)
					{
						if (hL[i].getStartOffset() >= firstIndex && lastIndex >= hL[i].getEndOffset())
						{
							hilit.removeHighlight(hL[i]);
						}
						else if (firstIndex>lastIndex)
						{
							if(hL[i].getStartOffset() >= lastIndex && firstIndex >= hL[i].getEndOffset())
							{
								hilit.removeHighlight(hL[i]);
							}
						}
					}
					redHighlight(tfFile);
				}
				saveHighlight(0,0);
			}
		});
		btnCategoryA.setBounds(810, 120, 162, 23);
		contentPane.add(btnCategoryA);

		JButton btnCategoryB = new JButton("Security Level 2");
		btnCategoryB.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnCategoryB.setBackground(Color.YELLOW);
		btnCategoryB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				accesslevel=2;
				hilit = tfFile.getHighlighter();
				if (hilit.getHighlights() != null)
				{
					Highlighter.Highlight[] hL = hilit.getHighlights();
					for (int i = 0; i < hL.length; i++)
					{
						if (hL[i].getStartOffset() >= firstIndex && lastIndex >= hL[i].getEndOffset())
						{
							hilit.removeHighlight(hL[i]);
						}
						else if (firstIndex>lastIndex)
						{
							if(hL[i].getStartOffset() >= lastIndex && firstIndex >= hL[i].getEndOffset())
							{
								hilit.removeHighlight(hL[i]);
							}
						}
					}
					yellowHighlight(tfFile);
				}
				saveHighlight(0,0);
			}
		});
		btnCategoryB.setBounds(810, 180, 162, 23);
		contentPane.add(btnCategoryB);

		JButton btnCategoryC = new JButton("Security Level 3");
		btnCategoryC.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnCategoryC.setBackground(Color.PINK);
		btnCategoryC.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				accesslevel=3;
				hilit = tfFile.getHighlighter();
				if (hilit.getHighlights() != null)
				{
					Highlighter.Highlight[] hL = hilit.getHighlights();
					for (int i = 0; i < hL.length; i++)
					{
						if (hL[i].getStartOffset() >= firstIndex && lastIndex >= hL[i].getEndOffset())
						{
							hilit.removeHighlight(hL[i]);
						}
						else if (firstIndex>lastIndex)
						{
							if(hL[i].getStartOffset() >= lastIndex && firstIndex >= hL[i].getEndOffset())
							{
								hilit.removeHighlight(hL[i]);
							}
						}
					}
					pinkHighlight(tfFile);
				}
				saveHighlight(0,0);
			}
		});
		btnCategoryC.setBounds(810, 240, 162, 23);
		contentPane.add(btnCategoryC);

		JButton btnCategoryD = new JButton("Security Level 4");
		btnCategoryD.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnCategoryD.setBackground(Color.CYAN);
		btnCategoryD.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				accesslevel=4;
				hilit = tfFile.getHighlighter();
				if (hilit.getHighlights() != null)
				{
					Highlighter.Highlight[] hL = hilit.getHighlights();
					for (int i = 0; i < hL.length; i++)
					{
						if (hL[i].getStartOffset() >= firstIndex && lastIndex >= hL[i].getEndOffset())
						{
							hilit.removeHighlight(hL[i]);
						}
						else if (firstIndex>lastIndex)
						{
							if(hL[i].getStartOffset() >= lastIndex && firstIndex >= hL[i].getEndOffset())
							{
								hilit.removeHighlight(hL[i]);
							}
						}
					}
					blueHighlight(tfFile);
				}
				saveHighlight(0,0);
			}
		});
		btnCategoryD.setBounds(810, 300, 162, 23);
		contentPane.add(btnCategoryD);
		
		JButton btnRemoveSingle = new JButton("Remove Policy");
		btnRemoveSingle.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnRemoveSingle.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				hilit = tfFile.getHighlighter();
				if (hilit.getHighlights() != null)
				{
					Highlighter.Highlight[] hL = hilit.getHighlights();
					for (int i = 0; i < hL.length; i++)
					{
						if (hL[i].getStartOffset() >= firstIndex && lastIndex >= hL[i].getEndOffset())
						{
							hilit.removeHighlight(hL[i]);
						}
						else if (firstIndex>lastIndex)
						{
							if(hL[i].getStartOffset() >= lastIndex && firstIndex >= hL[i].getEndOffset())
							{
								hilit.removeHighlight(hL[i]);
							}
						}
					}
					saveHighlight(0,1);
				}
			}
		});
		btnRemoveSingle.setBounds(810, 420, 162, 23);
		contentPane.add(btnRemoveSingle);
		
		JButton btnRemove = new JButton("Remove All Policy");
		btnRemove.setFont(new Font("FrankRuehl", Font.PLAIN, 13));
		btnRemove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (hilit.getHighlights() != null)
				{
					String message = "Are you sure you want to remove ALL the policies?";
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(contentPane, message, "Warning", dialogButton);
					
					if(dialogResult == 0) 
					{
					  removeHighlights(tfFile);
					  saveHighlight(1,0);
					} 
				}
			}
		});
		btnRemove.setBounds(810, 470, 162, 23);
		contentPane.add(btnRemove);
		
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
		btnBack.setBounds(810, 520, 162, 23);
		contentPane.add(btnBack);
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
	
	public void choose()
	{
		File f = new File("./indexes/" + of.filename + ".policy");
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
					Arrays.sort(high, new lastchar());
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
	
	public void redHighlight(JTextComponent textComp)
	{
		try
		{
			hilit = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());
			
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

	public void saveHighlight(int remove,int singleremove)
	{
		File f = new File("./indexes/" + of.filename + ".policy");
		
		Highlighter.Highlight[] hL = hilit.getHighlights();
		if (remove == 1 && of.filename != null)
		{
			f.delete();
			try
			{
				f.createNewFile();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		} 
		else if(of.filename != null)
		{
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
			}
			
			BufferedWriter writer = null;	
			try{	
				
				if (f.length()==0) 
				{
					f.delete();
					writer = new BufferedWriter(new FileWriter(f.getAbsolutePath(), false));
					writer.write(stringBuilder.toString());
					stringBuilder.setLength(0);
					choose();
				}
				else
				{
					int approve= checkAbleHilite(mousefirstindex,mouselastindex,accesslevel);
	
					if(approve==1||singleremove==1)
					{
						f.delete();
						writer = new BufferedWriter(new FileWriter(f.getAbsolutePath(), false));
						writer.write(stringBuilder.toString());
						stringBuilder.setLength(0);
						choose();
					}
					else
					{
						JOptionPane.showMessageDialog(contentPane, "Lower access level cannot overwrite higher access level.");
						stringBuilder.setLength(0);
						removeHighlights(tfFile);
						choose();
					}
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
						stringBuilder.setLength(0);
						removeHighlights(tfFile);
						choose();
					}
				} catch (IOException ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}
	
	public int checkAbleHilite(int mousefirstindex, int mouselastindex, int accesslevel)
	{
		List<Integer> listofFi = new ArrayList<Integer>();
		List<Integer> listofLi = new ArrayList<Integer>();
		List<Integer> listofAl = new ArrayList<Integer>();
		int policyfirstindex, policylastindex, policyaccesslevel;
		int approve = 0;
		int nearestFiIndex = 0;
		int nearestLiIndex = 0;
			
		File f = new File("./indexes/" + of.filename + ".policy");
		f.setReadOnly();
			
		if (f.exists())
		{
			try
			{
				String line;
				BufferedReader br = new BufferedReader(new FileReader(f));
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
						policyfirstindex = Integer.parseInt(details[0]);
						listofFi.add(policyfirstindex);
							
						policylastindex = Integer.parseInt(details[1]);
						listofLi.add(policylastindex);
							
						policyaccesslevel = Integer.parseInt(details[2]);
						listofAl.add(policyaccesslevel);
					}
					
					for(int k = 0; k < listofFi.size(); k++)
					{
						nearestFiIndex = findNearestNumber(listofFi,mousefirstindex); 
					}

					for(int k = 0; k < listofLi.size(); k++)
					{
						nearestLiIndex = findNearestNumber(listofLi,mouselastindex);
					}
					
					System.out.println(mousefirstindex);
					System.out.println(mouselastindex);
					System.out.println("index 1 fi = " + listofFi.get(nearestFiIndex));
					System.out.println("index 1 li = " + listofLi.get(nearestFiIndex));
					System.out.println("index 2 fi = " + listofFi.get(nearestLiIndex));
					System.out.println("index 2 li = " + listofLi.get(nearestLiIndex));
					System.out.println(listofAl);
					System.out.println(accesslevel);
					System.out.println("=======================");
					
					//first
					if((listofFi.get(nearestFiIndex) >= mousefirstindex) && (listofLi.get(nearestLiIndex) <= mouselastindex))
					{
						List<Integer>indexOfLiAl = new ArrayList<Integer>();
						int indexLi=0;
						for(int i=0; i<listofLi.size(); i++)
						{
							if(listofFi.get(i)<mouselastindex)
							{	
								indexOfLiAl.add(i);
							}
						}
						for(int i=0; i<indexOfLiAl.size(); i++)
						{
							if(listofLi.get(indexOfLiAl.get(i))>mouselastindex)
							{
								indexLi=indexOfLiAl.get(i);
							}
						}
						boolean middle = isMiddlehighlight(listofFi, listofLi, mousefirstindex, mouselastindex);
						if (middle== true)
						{
							int outerAl=outerhighlight(listofFi, listofLi, listofAl, mousefirstindex, mouselastindex);
							if(accesslevel<=outerAl)
							{
								int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
								if (accesslevel<=chkMiddleAl)
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
								approve=0;
							}
						}
						else if ((listofFi.get(indexLi)<mouselastindex && mouselastindex<listofLi.get(indexLi)))
						{
							int al=0;
							for(int j=0; j<indexOfLiAl.size(); j++)
							{
								if(listofAl.get(indexLi) == 1)
								{ 
									al=1;
									break;
								}
								else if(listofAl.get(indexLi) == 2)
								{ 
									al=2;
									break;
								}
								else if(listofAl.get(indexLi) == 3)
								{ 
									al=3;
									break;
								}
								else if(listofAl.get(indexLi) == 4)
								{ 
									al=4;
									break;
								}
								else
								{
									al=5;
								}
							}

							int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
							if(accesslevel <= al)
							{
								if(accesslevel <= chkMiddleAl)
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
								approve=0;
							}
						}
						else if(listofFi.get(nearestFiIndex)<=listofFi.get(nearestLiIndex))//other than this
						{
							int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
							System.out.println("chkMiddleAl "+chkMiddleAl);
							if(accesslevel <= chkMiddleAl)
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
							
							if(listofFi.get(nearestLiIndex) <= listofFi.get(nearestFiIndex))//here
							{	
								
								if((mousefirstindex > listofFi.get(nearestLiIndex)) && (mousefirstindex < listofLi.get(nearestLiIndex)))
								{
									if((listofAl.get(nearestLiIndex)) >= accesslevel)
									{
										int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
										if (accesslevel<=chkMiddleAl)
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
										approve=0;
									}
								}
								else if((mouselastindex > listofFi.get(nearestLiIndex)) && (mouselastindex < listofLi.get(nearestLiIndex)))
								{
									if((listofAl.get(nearestLiIndex)) >= accesslevel)
									{
										int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
										if (accesslevel<=chkMiddleAl)
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
										approve=0;
									}
								}
								else if((mousefirstindex < listofFi.get(nearestLiIndex)) && (mouselastindex < listofFi.get(nearestLiIndex)))
								{
									int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
									if (accesslevel<=chkMiddleAl)
									{
										approve=1;
									}
									else
									{
										approve=0;
									}
								}
								else if((mousefirstindex < listofFi.get(nearestLiIndex)) && (mouselastindex == listofFi.get(nearestLiIndex)))
								{
									int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
									if (accesslevel<=chkMiddleAl)
									{
										approve=1;
									}
									else
									{
										approve=0;
									}
								}
								else if((mousefirstindex > listofFi.get(nearestLiIndex)) && mouselastindex > listofLi.get(nearestLiIndex))
								{
									int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
									if (accesslevel<=chkMiddleAl)
									{
										approve=1;
									}
									else
									{
										approve=0;
									}
								}
								else if(mousefirstindex<listofFi.get(nearestLiIndex) && mouselastindex>listofLi.get(nearestLiIndex))//here
								{
									int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
									if (accesslevel<=chkMiddleAl)
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
									System.out.println("hi");
									approve=0;
								}
							}
							else if(listofFi.get(nearestLiIndex) >= listofFi.get(nearestFiIndex))//here
							{	
								if((mousefirstindex > listofFi.get(nearestFiIndex)) && (mousefirstindex < listofLi.get(nearestFiIndex)))
								{
									if((listofAl.get(nearestFiIndex)) > accesslevel)
									{
										int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
										if(accesslevel <= chkMiddleAl)
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
										approve=0;
									}
								}
								else if((mouselastindex > listofFi.get(nearestFiIndex)) && (mouselastindex < listofLi.get(nearestFiIndex)))
								{
									if((listofAl.get(nearestFiIndex)) > accesslevel)
									{
										int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
										if(accesslevel <= chkMiddleAl)
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
										approve=0;
									}
								}
								else if((mousefirstindex < listofFi.get(nearestFiIndex)) && (mouselastindex < listofFi.get(nearestFiIndex)))
								{
									int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
									if(accesslevel <= chkMiddleAl)
									{
										approve=1;
									}
									else
									{
										approve=0;
									}
								}
								else if((mousefirstindex < listofFi.get(nearestFiIndex)) && (mouselastindex == listofFi.get(nearestFiIndex)))
								{
									int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
									if(accesslevel <= chkMiddleAl)
									{
										approve=1;
									}
									else
									{
										approve=0;
									}
								}
								else if((mousefirstindex > listofFi.get(nearestFiIndex)) && mouselastindex > listofLi.get(nearestFiIndex))
								{
									int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
									if(accesslevel <= chkMiddleAl)
									{
										approve=1;
									}
									else
									{
										approve=0;
									}
								}
								else if(mouselastindex>listofFi.get(nearestLiIndex) && mouselastindex<listofLi.get(nearestLiIndex))
								{
									int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
									if(accesslevel <= chkMiddleAl)
									{
										approve=1;
									}
									else
									{
										approve=0;
									}
								}
							}
						}
					}
					//second
					else if(listofFi.get(nearestLiIndex) <= listofFi.get(nearestFiIndex))
					{
						List<Integer>indexOfFiAl = new ArrayList<Integer>();
						List<Integer>indexOfLiAl = new ArrayList<Integer>();
						int indexFi=0;
						int indexLi=0;
						for(int i=0; i<listofFi.size(); i++)
						{
							if(listofFi.get(i)<mousefirstindex)
							{	
								indexOfFiAl.add(i);
							}
						}
						for(int i=0; i<indexOfFiAl.size(); i++)
						{
							if(listofLi.get(indexOfFiAl.get(i))>mousefirstindex)
							{
								indexFi=indexOfFiAl.get(i);
							}
						}
						
						for(int i=0; i<listofLi.size(); i++)
						{
							if(listofFi.get(i)<mouselastindex)
							{	
								indexOfLiAl.add(i);
							}
						}
						for(int i=0; i<indexOfLiAl.size(); i++)
						{
							if(listofLi.get(indexOfLiAl.get(i))>mouselastindex)
							{
								indexLi=indexOfLiAl.get(i);
							}
						}
						boolean middle = isMiddlehighlight(listofFi, listofLi, mousefirstindex, mouselastindex);
						if (middle== true)
						{
							int outerAl=outerhighlight(listofFi, listofLi, listofAl, mousefirstindex, mouselastindex);
							if(accesslevel<=outerAl)
							{
								int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
								if (accesslevel<=chkMiddleAl)
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
								approve=0;
							}
						}
						else if ((listofFi.get(indexFi)<mousefirstindex && mousefirstindex<listofLi.get(indexFi)) && (mouselastindex > listofFi.get(nearestFiIndex) && mouselastindex<listofLi.get(nearestFiIndex)))
						{
							int al=0;
							for(int j=0; j<indexOfFiAl.size(); j++)
							{
								if(listofAl.get(indexFi) == 1)
								{ 
									al=1;
									break;
								}
								else if(listofAl.get(indexFi) == 2)
								{ 
									al=2;
									break;
								}
								else if(listofAl.get(indexFi) == 3)
								{ 
									al=3;
									break;
								}
								else if(listofAl.get(indexFi) == 4)
								{ 
									al=4;
									break;
								}
								else
								{
									al=5;
								}
							}

							int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
							if(accesslevel <= al)
							{
								if(accesslevel <= chkMiddleAl)
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
								approve=0;
							}						
						}
						else if ((listofFi.get(indexLi)<mouselastindex && mouselastindex<listofLi.get(indexLi)))
						{
							int al=0;
							for(int j=0; j<indexOfLiAl.size(); j++)
							{
								if(listofAl.get(indexLi) == 1)
								{ 
									al=1;
									break;
								}
								else if(listofAl.get(indexLi) == 2)
								{ 
									al=2;
									break;
								}
								else if(listofAl.get(indexLi) == 3)
								{ 
									al=3;
									break;
								}
								else if(listofAl.get(indexLi) == 4)
								{ 
									al=4;
									break;
								}
								else
								{
									al=5;
								}
							}

							int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
							if(accesslevel <= al)
							{
								if(accesslevel <= chkMiddleAl)
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
								approve=0;
							}
						}
						else if((mousefirstindex > listofFi.get(nearestLiIndex)) && (mousefirstindex < listofLi.get(nearestLiIndex)))
						{
							if((listofAl.get(nearestLiIndex)) >= accesslevel)
							{
								int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
								if (accesslevel<=chkMiddleAl)
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
								approve=0;
							}
						}
						else if((mouselastindex > listofFi.get(nearestLiIndex)) && (mouselastindex < listofLi.get(nearestLiIndex)))
						{
							if((listofAl.get(nearestLiIndex)) >= accesslevel)
							{
								int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
								if (accesslevel<=chkMiddleAl)
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
								approve=0;
							}
						}
						else if((mousefirstindex < listofFi.get(nearestLiIndex)) && (mouselastindex < listofFi.get(nearestLiIndex)))
						{
							int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
							if (accesslevel<=chkMiddleAl)
							{
								approve=1;
							}
							else
							{
								approve=0;
							}
						}
						else if((mousefirstindex < listofFi.get(nearestLiIndex)) && (mouselastindex == listofFi.get(nearestLiIndex)))
						{
							int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
							if (accesslevel<=chkMiddleAl)
							{
								approve=1;
							}
							else
							{
								approve=0;
							}
						}
						else if((mousefirstindex > listofFi.get(nearestLiIndex)) && mouselastindex > listofLi.get(nearestLiIndex))
						{
							int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
							if (accesslevel<=chkMiddleAl)
							{
								approve=1;
							}
							else
							{
								approve=0;
							}
						}
						else if(mousefirstindex<listofFi.get(nearestLiIndex) && mouselastindex>listofLi.get(nearestLiIndex))
						{
							int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
							if (accesslevel<=chkMiddleAl)
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
							approve=0;
						}
					}
					//third
					else if(listofFi.get(nearestLiIndex)>= listofFi.get(nearestFiIndex) )
					{
						List<Integer>indexOfFiAl = new ArrayList<Integer>();
						List<Integer>indexOfLiAl = new ArrayList<Integer>();
						int indexFi=0;
						int indexLi=0;
						for(int i=0; i<listofFi.size(); i++)
						{
							if(listofFi.get(i)<mousefirstindex)
							{	
								indexOfFiAl.add(i);
							}
						}
						for(int i=0; i<indexOfFiAl.size(); i++)
						{
							if(listofLi.get(indexOfFiAl.get(i))>mousefirstindex)
							{
								indexFi=indexOfFiAl.get(i);
							}
						}
						
						for(int i=0; i<listofLi.size(); i++)
						{
							if(listofFi.get(i)<mouselastindex)
							{	
								indexOfLiAl.add(i);
							}
						}
						for(int i=0; i<indexOfLiAl.size(); i++)
						{
							if(listofLi.get(indexOfLiAl.get(i))>mouselastindex)
							{
								indexLi=indexOfLiAl.get(i);
							}
						}
						System.out.println("indexLi "+indexLi);
						boolean middle = isMiddlehighlight(listofFi, listofLi, mousefirstindex, mouselastindex);
						if (middle== true)
						{
							int outerAl=outerhighlight(listofFi, listofLi, listofAl, mousefirstindex, mouselastindex);
							if(accesslevel<=outerAl)
							{
								int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
								if (accesslevel<=chkMiddleAl)
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
								approve=0;
							}
						}
						else if ((listofFi.get(indexFi)<mousefirstindex && mousefirstindex<listofLi.get(indexFi)) && (mouselastindex > listofFi.get(nearestFiIndex) && mouselastindex<listofLi.get(nearestFiIndex)))
						{
							int al=0;
							for(int j=0; j<indexOfFiAl.size(); j++)
							{
								if(listofAl.get(indexFi) == 1)
								{ 
									al=1;
									break;
								}
								else if(listofAl.get(indexFi) == 2)
								{ 
									al=2;
									break;
								}
								else if(listofAl.get(indexFi) == 3)
								{ 
									al=3;
									break;
								}
								else if(listofAl.get(indexFi) == 4)
								{ 
									al=4;
									break;
								}
								else
								{
									al=5;
								}
							}

							int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
							if(accesslevel <= al)
							{
								if(accesslevel <= chkMiddleAl)
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
								approve=0;
							}							
						}
						else if ((listofFi.get(indexLi)<mouselastindex && mouselastindex<listofLi.get(indexLi)))
						{
							int al=0;
							for(int j=0; j<indexOfLiAl.size(); j++)
							{
								if(listofAl.get(indexLi) == 1)
								{ 
									al=1;
									break;
								}
								else if(listofAl.get(indexLi) == 2)
								{ 
									al=2;
									break;
								}
								else if(listofAl.get(indexLi) == 3)
								{ 
									al=3;
									break;
								}
								else if(listofAl.get(indexLi) == 4)
								{ 
									al=4;
									break;
								}
								else
								{
									al=5;
								}
							}

							int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
							if(accesslevel <= al)
							{
								if(accesslevel <= chkMiddleAl)
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
								approve=0;
							}
						}
						else if((mousefirstindex > listofFi.get(nearestFiIndex)) && (mousefirstindex < listofLi.get(nearestFiIndex)))
						{
							if((listofAl.get(nearestFiIndex)) > accesslevel)
							{
								int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
								if(accesslevel <= chkMiddleAl)
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
								approve=0;
							}
						}
						else if((mouselastindex > listofFi.get(nearestFiIndex)) && (mouselastindex < listofLi.get(nearestFiIndex)))
						{
							if((listofAl.get(nearestFiIndex)) > accesslevel)
							{
								int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
								if(accesslevel <= chkMiddleAl)
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
								approve=0;
							}
						}
						else if((mousefirstindex < listofFi.get(nearestFiIndex)) && (mouselastindex < listofFi.get(nearestFiIndex)))
						{
							int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
							if(accesslevel <= chkMiddleAl)
							{
								approve=1;
							}
							else
							{
								approve=0;
							}
						}
						else if((mousefirstindex < listofFi.get(nearestFiIndex)) && (mouselastindex == listofFi.get(nearestFiIndex)))
						{
							int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
							if(accesslevel <= chkMiddleAl)
							{
								approve=1;
							}
							else
							{
								approve=0;
							}
						}
						else if((mousefirstindex > listofFi.get(nearestFiIndex)) && mouselastindex > listofLi.get(nearestFiIndex))
						{
							int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
							if(accesslevel <= chkMiddleAl)
							{
								approve=1;
							}
							else
							{
								approve=0;
							}
						}
						else if(mouselastindex>listofFi.get(nearestLiIndex) && mouselastindex<listofLi.get(nearestLiIndex))
						{
							int chkMiddleAl = middleIndexes(listofFi, listofAl, mousefirstindex, mouselastindex);
							if(accesslevel <= chkMiddleAl)
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
							approve=0;
						}
					}
					//last
					else
					{
						approve=0;
					}
				}
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}	
		}
		accesslevel=0;
		return approve;		
	}
	
	public static int findNearestNumber(List<Integer> listofFi, int mouseIndex)
	{
	    int min=-1,max=-1,nearestNumber,index;

	    for(int i=0; i<listofFi.size(); i++)
	    {
	        if(listofFi.get(i)<mouseIndex)
	        {
	            if(min==-1)
	            {
	                min=listofFi.get(i);
	            }
	            else if(listofFi.get(i)>min)
	            {
	                min=listofFi.get(i);
	            }
	        }
	        else if(listofFi.get(i)>mouseIndex)
	        {
	            if(max==-1)
	            {
	                max=listofFi.get(i);
	            }
	            else if(listofFi.get(i)<max)
	            {
	                max=listofFi.get(i);
	            }
	        }
	        else
	        {
	            return i;
	        }
	    }

	    if(Math.abs(mouseIndex-min)<Math.abs(mouseIndex-max))
	    {
	        nearestNumber=min;
	        index = listofFi.indexOf(nearestNumber);
	    }
	    else
	    {
	        nearestNumber=max;
	        index = listofFi.indexOf(nearestNumber);
	    }

	    if(index<0)
	    {
	    	index = 0;
	    }
	    return index;
	}
	
	public static int middleIndexes(List<Integer> listofFi,List<Integer> listofAl, int mousefirstindex, int mouselastindex)
	{
		List<Integer> listofIndexes = new ArrayList<Integer>();
		int accesslevel=5;
		for(int i=0; i<listofFi.size(); i++)
		{
			if(listofFi.get(i)>=mousefirstindex && mouselastindex>listofFi.get(i))
			{
				listofIndexes.add(i);
			}
		}
		for(int j=0; j<listofIndexes.size(); j++)
		{
			if(listofAl.get(listofIndexes.get(j)) <= accesslevel)
			{ 
				accesslevel=listofAl.get(listofIndexes.get(j));
			}
		}
		return accesslevel;
	}
	
	public static boolean isMiddlehighlight(List<Integer> listofFi, List<Integer> listofLi,int mousefirstindex, int mouselastindex)
	{
		boolean middle = false;
		for(int i=0; i<listofFi.size(); i++)
		{
			if(mousefirstindex >listofFi.get(i) && mouselastindex <listofLi.get(i))
			{
				middle=true;
			}
		}
		return middle;
	}  
	
	public static int outerhighlight(List<Integer> listofFi, List<Integer> listofLi,List<Integer> listofAl,int mousefirstindex, int mouselastindex)
	{
		int outerAl = 5;
		List<Integer> listofOuter = new ArrayList<Integer>();
		for(int i=0; i<listofFi.size(); i++)
		{
			if(mousefirstindex >listofFi.get(i) && mouselastindex <listofLi.get(i))
			{
				listofOuter.add(i);
			}
		}
		outerAl=listofAl.get(listofOuter.get(0));
		return outerAl;
	}
	
	private static class lastchar implements Comparator<String>{
        public int compare(String s1, String s2) {
            return (s1.charAt(s1.length()-1) - s2.charAt(s2.length()-1));
        }
    }	
}