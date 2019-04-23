package com.pool.main.audio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundClip
{
	private Clip clip = null;
	private FloatControl gain;
	
	public SoundClip(String path) {
		try
		{
			InputStream audio_source = SoundClip.class.getResourceAsStream(path);
			InputStream buffered_input = new BufferedInputStream(audio_source);
			AudioInputStream ais = AudioSystem.getAudioInputStream(buffered_input);
			AudioFormat file_format = ais.getFormat();
			AudioFormat decode_format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
														file_format.getSampleRate(),
														16, file_format.getChannels(),
														file_format.getChannels() * 2,
														file_format.getSampleRate(),
														false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decode_format, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}

	public void play() {
		if(clip == null) {
			return;
		}
		stop();
		clip.setFramePosition(0);
		while(!clip.isRunning()) {
			clip.start();
		}
		
	}
	
	public void stop() {
		if(clip.isRunning()) {
			clip.stop();
		}
	}
	
	public void close() {
		stop();
		clip.drain();
		clip.close();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		play();
	}
	
	public void set_volume(float volume) {
		gain.setValue(volume);
	}
	
	public boolean is_running() {
		return clip.isRunning();
	}
	
}
