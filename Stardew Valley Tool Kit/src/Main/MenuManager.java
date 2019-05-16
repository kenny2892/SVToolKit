package Main;

public class MenuManager 
{
	private UI ui;
	
	public MenuManager(UI ui)
	{
		this.ui = ui;
	}
	
	public void hideAll()
	{
		ui.mainPanel.setVisible(false);
		ui.dialoguePanel.setVisible(false);
		ui.comparePanel.setVisible(false);
	}
	
	public void showDialogue()
	{
		hideAll();
		ui.dialoguePanel.setVisible(true);
	}
	
	public void showMain()
	{
		hideAll();
		ui.mainPanel.setVisible(true);
	}
	
	public void showCompare()
	{
		hideAll();
		ui.comparePanel.setVisible(true);
	}
}
