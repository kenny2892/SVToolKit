package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DialogueMerger 
{
	private static boolean overlap;
	private static int numResults = 0;
	private static String resultsText = "";
	
	public String mergeFiles(String ogDir, String newDir, String revDir, boolean overlap2)
	{
		overlap = overlap2;
		
		ArrayList<String> fileList = new ArrayList<String>(0);
	
		File ogFiles = new File(ogDir);
		fileList = search(ogFiles, fileList);
	
		boolean folderCheck = checkFolders(fileList, newDir);
		
		if(folderCheck)
		{
			mergeFiles(fileList, ogDir, newDir, revDir);
		}
		
		else
		{
			resultsText += "The two folders do not have matching files.\nPlease check the folders and try again.";
		}
		
		return resultsText;
	}
	
	private static ArrayList<String> search(File item, ArrayList<String> total)
	{
		if(item.isDirectory() == true) // Is a directory
		{
			File[] names = item.listFiles();
			if(names != null)
			{
				for(File name:names)
				{
					total = search(name, total); // Jump Into It
				}
			}
		}
		
		else // Is a File
		{
			int posOfExt = item.getName().lastIndexOf("."); 
			total.add(item.getName().substring(0, posOfExt));
			numResults++; // Increase total of results
		}

		return total;
	}
	
	private static boolean checkFolders(ArrayList<String> ogList, String newDir)
	{		
		int ogNum = numResults;
		numResults = 0; // Reset the counter
		
		File newFiles = new File(newDir);
		ArrayList<String> newList = new ArrayList<String>(0);
		
		newList= search(newFiles,newList);
		
		int newNum = numResults;
		
		if(newNum != ogNum)
		{
			return false;
		}
		
		else
		{
			for(int i = 0; i < ogList.size(); i++)
			{
				if(newList.get(i).equalsIgnoreCase(ogList.get(i)) == false)
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	private static void mergeFiles(ArrayList<String> nameList, String ogDir, String newDir, String revDir)
	{
		for(int i = 0; i < nameList.size(); i++)
		{
			resultsText += "File for: " + nameList.get(i) + " ... ";
			
			File newFile = new File(newDir + "\\" + nameList.get(i) + ".yaml");
			Scanner newScan = null;
			try 
			{
				newScan = new Scanner(newFile);
			} 
			
			catch (FileNotFoundException e) 
			{
				resultsText += "\nWelp... the scanner fucked up.";
			}
			
			for(int p = 0; p < 18; p++)
			{
				newScan.nextLine();
			}
			
			// Getting number of Lines here: https://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
			FileReader input = null;
			try 
			{
				input = new FileReader(newDir + "\\" + nameList.get(i) + ".yaml");
			} 
			
			catch (FileNotFoundException e) 
			{
				resultsText += "\nWell shit... the reader fucked up.";
			}
			
			LineNumberReader newCount = new LineNumberReader(input);
			
			try 
			{
				while (newCount.skip(Long.MAX_VALUE) > 0)
				{
					// Loop just in case the file is > Long.MAX_VALUE or skip() decides to not read the entire file
				}
			} 
			catch (Exception e) 
			{
				resultsText += "Counting messed up.";
			}
			
			int newLineCount = newCount.getLineNumber() - 1; // The last 2 lines are blank, and the counter starts at 0, so I just minus 1
			String newText = "";
			
			// Scanning through the dialogue
			for(int j = 19; j < newLineCount; j++)
			{
				String dialogue = newScan.nextLine();
				
				if(checkLine(dialogue, nameList, i, ogDir) == false)
				{					
					String tempString = dialogue + "\n";
					
					int spaceEnd = 0;
					int index = 0;
					while(spaceEnd == 0)
					{
						if(tempString.charAt(index) != ' ')
						{
							spaceEnd = index;
						}
						
						else
							index++;
					}
					
					String spaces = "";
					for(int h = 0; h < spaceEnd; h++)
					{
						spaces = spaces + " ";
					}
					
					int posOfDD = tempString.indexOf(":");
					
					tempString = spaces + "\"" + tempString.substring(4);
					tempString = tempString.substring(0, posOfDD + 1) + "\"" + tempString.substring(posOfDD + 1);
					
					newText = newText + tempString;
				}
			}
			
			File revFile = new File(revDir + "\\" + nameList.get(i) + ".yaml");
			
			if(revFile.exists())
			{
	    		revFile.delete(); // Delete Old File
			}
			
			// Writing the new save file
			try
			{
				PrintWriter printFile = new PrintWriter(revFile);
				
				printFile.println(newText);
				printFile.close();
				
				resultsText += "Completed\n";
			}
			
			catch(Exception e)
			{
				resultsText += "Something went wrong with saving the file.\n";
			}
		}
	}
	
	private static boolean checkLine(String dialogue, ArrayList<String> nameList, int nameNum, String ogDir)
	{
		File ogFile = new File(ogDir + "\\" + nameList.get(nameNum) + ".yaml");
		Scanner ogScan = null;
		try 
		{
			ogScan = new Scanner(ogFile);
		} 
		
		catch (FileNotFoundException e) 
		{
			resultsText += "\nWelp... the scanner fucked up.";
		}
		
		for(int p = 0; p < 18; p++)
		{
			ogScan.nextLine();
		}
		
		// Getting number of Lines
		FileReader input = null;
		try 
		{
			input = new FileReader(ogDir + "\\" + nameList.get(nameNum) + ".yaml");
		} 

		catch (FileNotFoundException e) 
		{
			resultsText += "\nWell shit... the reader fucked up.";
		}

		LineNumberReader ogCount = new LineNumberReader(input);

		try 
		{
			while (ogCount.skip(Long.MAX_VALUE) > 0)
			{
				// Loop just in case the file is > Long.MAX_VALUE or skip() decides to not read the entire file
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Counting messed up.");
		}

		int ogLineCount = ogCount.getLineNumber() - 1; // The last 2 lines are blank, and the counter starts at 0, so I just minus 1
		
		for(int j = 19; j < ogLineCount; j++)
		{
			String dialogueNew = ogScan.nextLine();
			
			if(overlap == true)
			{
				if(dialogueNew.equalsIgnoreCase(dialogue))
				{
					return true;
				}
			}
			
			else
			{
				int posOfDD = dialogueNew.indexOf(":");
				
				if(dialogueNew.substring(0, posOfDD).equalsIgnoreCase(dialogue.substring(0, posOfDD)))
				{
					return true;
				}
				
			}
		}
		
		return false;
	}
}
