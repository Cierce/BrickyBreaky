package audio;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * AudioPlayer is not my own, I have just included its .jar in the build path as it provides the level of support I otherwise 
 * could not find in Java's standard library for audio. 
 * <br>The author can be found here setting up the object:
 * https://www.youtube.com/watch?v=ar0hTsb9sxM 
 * <br><br>Provides audio support for audio files.
 * @author ForeignGuyMike
 * @version 1.0
 * @since 1.0
 */
public class AudioPlayer 
{
	private Clip audioClip;                //Clip audioClip stores the decoded audio file
	private AudioInputStream audioInput;   //AudioInputStream audioInput stores an audio file and its location 
	private AudioInputStream decodedAudio; //AudioInputStream decodedAudio stores the decode format and the audioInput 
	private AudioFormat baseFormat;        //AudioFormat baseFormat stores the format for the audioInput e.g. .mp3
	private AudioFormat decodeFormat;      //AudioFormat decodeFormat stores the decoded audio for the baseFormat

	/**
	 * This constructor expects an audio file location to be passed through as the argument, it will then decode the audio file passed though.
	 * @param fileToStream String An absolute path location to the audio file
	 * @throws UnsupportedAudioFileException this is thrown when an audio file format is used that the decodeFormat does not support e.g. mp4
	 * @throws IOException this is thrown when the audio file absolute path cannot be found
	 * @throws LineUnavailableException this is thrown when the audio line is not free
	 */
	public AudioPlayer(String fileToStream)
	{
		try
		{
			audioInput = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(fileToStream));
			baseFormat = audioInput.getFormat();

			decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED, 
					baseFormat.getSampleRate(), 
					16, 
					baseFormat.getChannels(), 
					baseFormat.getChannels() * 2, 
					baseFormat.getSampleRate(), 
					false);

			decodedAudio = AudioSystem.getAudioInputStream(decodeFormat, audioInput);
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

	/**
	 * Plays the audio clip decoded by this objects constructor if it's not null.
	 */
	public void play()
	{
		if(audioClip != null)
		{
			stop();
			audioClip.setFramePosition(0);
			audioClip.start();
		}
	}

	/**
	 * Stops the audio clip decoded by this objects constructor.
	 */
	public void stop()
	{
		if(audioClip.isRunning())
		{
			audioClip.stop();
		}
	}

	/**
	 * Closes the audio clip passed through this objects constructor.
	 */
	public void close()
	{
		stop();
		audioClip.close();
	}
}
