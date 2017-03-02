package audio;

import java.io.IOException;

import javax.sound.sampled.*;

public class AudioPlayer 
{
	private Clip audioClip;
	
	public AudioPlayer(String stream)
	{
		try
		{
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(stream));
			AudioFormat baseFormat = audioInput.getFormat();
			
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED, 
					baseFormat.getSampleRate(), 
					16, 
					baseFormat.getChannels(), 
					baseFormat.getChannels() * 2, 
					baseFormat.getSampleRate(), 
					false);
			
			AudioInputStream decodedAudio = AudioSystem.getAudioInputStream(decodeFormat, audioInput);
			audioClip = AudioSystem.getClip();
			audioClip.open(decodedAudio);
		} 
		catch(UnsupportedAudioFileException e) 
		{
			System.out.println("Audio file format not supported");
			e.printStackTrace();
		}
		catch(IOException e) 
		{
			System.out.println("Audio IO error");
			e.printStackTrace();
		}
		catch(LineUnavailableException e)
		{
			System.out.println("Line unavailable");
			e.printStackTrace();
		} 
	}
	
	public void play()
	{
		if(audioClip == null) 
		{
			return;
		}
		else
		{
			stop();
			audioClip.setFramePosition(0);
			audioClip.start();
		}
	}
	
	public void stop()
	{
		if(audioClip.isRunning())
		{
			audioClip.stop();
		}
	}
	
	public void close()
	{
		stop();
		audioClip.close();
	}
}
