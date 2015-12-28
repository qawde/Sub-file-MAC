import java.awt.Dimension;
import java.io.File;

import javax.swing.JFileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

public class OpenFolder
{
	private File directory;
	private String directoryName = "C://";
	StringBuilder sb = new StringBuilder();

	public String ChooseFolder() throws Exception
	{
		directory = new File(directoryName);
		List<String> nameList = new ArrayList<String>();
		File[] fList = directory.listFiles();

		for (int i = 0; i < fList.length; i++)
		{
			if (fList[i].isFile())
			{
				fList[i].getName();
				nameList.add(fList[i].getName());

			}

		}
		sb.append(nameList);
		System.out.println(sb);
		return sb.toString();
	}

	public void setDirectory(String directoryName)
	{
		this.directoryName = directoryName;
	}

}
//
// public class OpenFolder {
//
// JFileChooser jfc = new JFileChooser();
//
// public void ChooseFolder() throws Exception {
// jfc.setCurrentDirectory(new java.io.File("."));
// jfc.setDialogTitle("choosertitle");
// jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
// jfc.setAcceptAllFileFilterUsed(false);
//
// if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
// File currentDirectory = jfc.getCurrentDirectory();
// File selectedFile = jfc.getSelectedFile();
// }
// else {
// System.out.println("No Selection");
// }
//
// }
//
// public Dimension getPreferredSize() {
// return new Dimension(200,200);
// }
// }
