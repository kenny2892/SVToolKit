package Main;

import java.io.File;
import java.util.ArrayList;

public class CompareCount 
{
	private int count = 0;
	private static ArrayList<String> full1 = new ArrayList<String>();
	private static ArrayList<String> full2 = new ArrayList<String>();
	
	public String compare(String folder1, String folder2)
	{
		File dir1 = new File(folder1);
		File dir2 = new File(folder2);
		
		if(!dir1.exists() || !dir2.exists())
			return "Folder input invalid";
		
		ArrayList<String> files1 = new ArrayList<String>();
		files1 = filesAra(dir1, files1, 1);
		
		ArrayList<String> files2 = new ArrayList<String>();
		files2 = filesAra(dir2, files2, 2);
		
		ArrayList<String> extras = files2;
		extras.removeAll(files1);
		
		compareFullLists(extras);
		
		String missingFiles = "";
		
		for(int i = 0; i < extras.size(); i++)
			missingFiles += extras.get(i) + "\n";
		
		if(missingFiles == "")
			missingFiles = "No Files Missing";
		
		count = extras.size();
		
		return missingFiles;
	}
	
	private static ArrayList<String> filesAra(File item, ArrayList<String> total, int mode)
	{
		if(item.isDirectory() == true) // Is a directory
		{
			File[] names = item.listFiles();
			if(names != null)
			{
				for(File name:names)
				{
					total = filesAra(name, total, mode); // Jump Into It
				}
			}
		}
		
		else // Is a File
		{
			//int posOfExt = item.getName().lastIndexOf("."); 
			//total.add(item.getName().substring(0, posOfExt));
			total.add(item.getName());
			
			if(mode == 1)
				full1.add(item.getPath());
			
			else
				full2.add(item.getPath());
		}

		return total;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public ArrayList<String> getExtras()
	{
		return full2;
	}
	
	private static void compareFullLists(ArrayList<String> extras)
	{
		for(int i = 0; i < full2.size(); i++)
		{
			boolean check = false;
			for(int j = 0; j < extras.size(); j++)
			{
				if(full2.get(i).contains(extras.get(j)))
				{
					check = true;
				}
			}
			
			if(check == false)
			{
				full2.remove(i);
				i--;
			}
		}
	}
}
