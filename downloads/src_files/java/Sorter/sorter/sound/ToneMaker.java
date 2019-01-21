
package sorter.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Taken from:
 * http://stackoverflow.com/questions/2064066/does-java-have-built-in-libraries-for-audio-synthesis/2065693#2065693
 * @author trashgod http://stackoverflow.com/users/230513/trashgod
 */
public class ToneMaker {

    private SourceDataLine line;
    
    public ToneMaker() throws LineUnavailableException {
        
        final AudioFormat af = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
        line = AudioSystem.getSourceDataLine(af);
        line.open(af, Note.SAMPLE_RATE);
        
    }

    public void startSoundLine(){
        line.start();
    }
    
    public void stopSoundLine(){
        line.drain();
        line.close();
    }
    
    public void play(int elementSize, int maxElementSize, int ms){
        ms = ms - 1;
        float tone = ((float)elementSize) * 60f / (float)maxElementSize;
        play(line, new Note(tone), ms);
        play(line, new Note(0), 1);
    }
    
    private static void play(SourceDataLine line, Note note, int ms) {
        ms = Math.min(ms, Note.SECONDS * 1000);
        int length = Note.SAMPLE_RATE * ms / 1000;
        int count = line.write(note.data(), 0, length);
    }

}
