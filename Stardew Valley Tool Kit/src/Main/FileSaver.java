package Main;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class FileSaver 
{
	public void save(ArrayList<String> fileList, String saveFolder)
	{
		for(int i = 0; i < fileList.size(); i++)
		{
			// Get File to Copy
			File file = new File(fileList.get(i));
			
			// Split the Paths
			String[] fileSplit = file.getPath().split("\\\\"); // It needs to be 4 \'s to split one \\
			String[] saveSplit = saveFolder.split("\\\\");
			
			// Make Copy Path
			int num = 0;
			for(int j = 0; j < fileSplit.length; j++)
			{
				if(saveSplit[saveSplit.length - 1].compareTo(fileSplit[j]) == 0)
					num = j;
			}
			
			String curDir = "\\";
			for(int k = num + 1; k < fileSplit.length - 1; k++)
			{
				curDir += fileSplit[k] + "\\";
				
			}
			
			String copyDir = saveFolder + curDir;
			copyDir = copyDir.substring(0, copyDir.length() - 1);
			File saveDir = new File(copyDir);
			
			if(!saveDir.exists())
			{
				try
				{
					saveDir.mkdirs();
				}
				
				catch(Exception e)
				{
					
				}
			}
			
			File newFile = new File(copyDir + "\\" + file.getName());
			
			try 
			{
				Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} 
			catch (Exception e) 
			{
				
			}
		}
		
		File placeHolder = new File(saveFolder + "\\Placeholder");
		if(placeHolder.exists())
			placeHolder.delete();
	}
}
