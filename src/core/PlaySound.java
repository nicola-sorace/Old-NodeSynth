package core;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class PlaySound implements Runnable{
	
	private Jack in;
	private int sampleRate = 48000;
	
	public PlaySound(Jack in){
		this.in = in;
	}

	@Override
	public void run() {
		final AudioFormat af = new AudioFormat(sampleRate, 16, 1, true, true);
        try {
            SourceDataLine line = AudioSystem.getSourceDataLine(af);
            line.open(af);
            line.start();
            //line.write(new byte[]{Byte.MIN_VALUE,Byte.MAX_VALUE}, 0, 2);
            //play Frequency = 200 Hz for 1 seconds
            play(line, generateSineWavefreq(400,1));
            //line.drain();
            Thread.sleep(1000);
            Thread thread = new Thread(new PlaySound(in), "PlaySound");
    		thread.start();
            line.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private byte[] generateSineWavefreq(int frequencyOfSignal, int seconds) {
        // total samples = (duration in second) * (samples per second)
        byte[] sin = new byte[seconds * sampleRate];
        double samplingInterval = (double) (sampleRate / frequencyOfSignal);
        //System.out.println("Sampling Frequency  : "+sampleRate);
        //System.out.println("Frequency of Signal : "+frequencyOfSignal);
        //System.out.println("Sampling Interval   : "+samplingInterval);
        for (int i = 0; i < sampleRate*seconds; i++) {
            double angle = (2.0 * Math.PI * i) / samplingInterval;
            sin[i] = (byte) (in.getValue(angle) * 127);
            //System.out.println("" + sin[i]);
        }
        return sin;
    }
    private void play(SourceDataLine line, byte[] array) {
        int length = sampleRate * array.length / 1000;
        line.write(array, 0, array.length);
    }

}
