// Jesse Ross
// April 12, 2019

package Main;

public class SVToolKit 
{
	public static void main(String[] args) 
	{
		UI ui = new UI();
		MenuManager menu = new MenuManager(ui);
		
		ui.start(menu);
	}
}
