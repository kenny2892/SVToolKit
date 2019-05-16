package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class MusicPlayer 
{
	private Clip clip;
	private long clipTmePos;
	
	public void playMusic(String filePath)
	{
		if(filePath.contains("%20"))
			filePath = filePath.replaceAll("%20", " ");
		
		try
		{
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(this.getClass().getResource(filePath)); // You can't use File, for audio. You have to do a Stream
			
			clip = AudioSystem.getClip(); // Use the Clip class to play song
			
			clip.open(audioInput);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			lowerVol();
		}
		
		catch(Exception e)
		{
			//System.out.println("Song Error 2");
		}
	}
	
	public void pause()
	{
		clipTmePos = clip.getMicrosecondPosition();
		clip.stop();
	}
	
	public void resume()
	{
		clip.setMicrosecondPosition(clipTmePos);
		clip.start();
	}
	
	public void lowerVol()
	{
		// Source: http://helpdesk.objects.com.au/java/how-to-control-volume-of-audio-clip
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		double gain = 0.50; // Percent you want the volume to be at. 0 to 1
		float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
		gainControl.setValue(dB);
	}
}
