import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import org.jutils.apps.JExplorer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//http://www.programcreek.com/java-api-examples/java.awt.FileDialog
import java.io.File;
import java.util.regex.Pattern;

public class BrowseFolder extends JFrame
{

	private JPanel contentPane;
	OpenFolder of = new OpenFolder();
	final String directoryWindows = "C://";

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
					BrowseFolder frame = new BrowseFolder();
					JExplorer.createAndShowGui();
					// OpenFolder panel = new OpenFolder();
					// frame.addWindowListener (
					// new WindowAdapter() {
					// public void windowClosing(WindowEvent e) {
					//
					// System.exit(0);
					// }
					// }
					// );
					// frame.getContentPane().add(panel, "Center");
					// frame.setSize(panel.getPreferredSize());
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
/**
 * Create the frame.
 */
// public BrowseFolder() {
// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// setBounds(100, 100, 450, 300);
// contentPane = new JPanel();
// contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
// setContentPane(contentPane);
// contentPane.setLayout(null);
//
// JTextArea textArea = new JTextArea();
// textArea.setText(JExplorer.createAndShowGui().toString());
// // of = new OpenFolder();
// //
// // try {
// // of.ChooseFolder();
// //
// // } catch (Exception ex) {
// // ex.printStackTrace();
// // }
// // try {
// // textArea.setText(of.sb.toString());
// // } catch (Exception ex) {
// // ex.printStackTrace();
// // }
// textArea.setEditable(false);
// textArea.setBounds(10, 11, 374, 212);
// contentPane.add(textArea);
//
// JScrollPane scrollPane = new JScrollPane(textArea);
// scrollPane.setBounds(10, 11, 374, 212);
// contentPane.add(scrollPane);
//
// JButton btnNewButton = new JButton("Browse");
// btnNewButton.addActionListener(new ActionListener() {
// public void actionPerformed(ActionEvent e) {
//
// }
// });
// btnNewButton.setBounds(10, 227, 89, 23);
// contentPane.add(btnNewButton);
// }
//
// }

// class OpenFolder extends JPanel
// implements ActionListener {
// JButton go;
// JFileChooser jfc;
// String choosertitle;
//
// public OpenFolder() {
// go = new JButton("Do it");
// go.addActionListener(this);
// add(go);
// }
//
//
// public void actionPerformed (ActionEvent e){
// int result;
//
// jfc = new JFileChooser();
// jfc.setCurrentDirectory(new java.io.File("."));
// jfc.setDialogTitle("choosertitle");
// jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
// jfc.setAcceptAllFileFilterUsed(false);
//
// if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
// System.out.println(jfc.getCurrentDirectory());
// System.out.println(jfc.getSelectedFile());
// }
// else {
// System.out.println("No Selection");
// }
//
// }
//
// public Dimension getPreferredSize() {
// return new Dimension(450, 300);
// }
// }
// public void ShowFolder() {
// FileDialog fd = new FileDialog(textArea, FileDialog.LOAD);
// fd.setSize(300, 300);
// fd.setVisible(true);
// String str = fd.getDirectory();
//
// if (str != null && !str.trim().equals("")) {
// textArea.setText(str);
// }
// }
