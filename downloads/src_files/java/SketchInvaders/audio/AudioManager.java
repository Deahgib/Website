/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;
import spaceinvaders.Game;

/**
 *
 * @author Louis
 */
public class AudioManager {
    public AudioManager(){}
    
    public static final int NUM_BUFFERS = 6;
    public static final int NUM_SOURCES = 6;
    
    public static final int PEW = 0;
    public static final int DEATH = 1;
    public static final int LOOSE = 2;
    public static final int WIN = 3;
    public static final int BAH = 4;
    public static final int BOH = 5;
    
    private static IntBuffer buffer = BufferUtils.createIntBuffer(NUM_BUFFERS);
    private static IntBuffer source = BufferUtils.createIntBuffer(NUM_SOURCES);
    private static FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3*NUM_BUFFERS).put(new float[]{0.0f, 0.0f, 0.0f});
    private static FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3*NUM_BUFFERS).put(new float[]{0.0f, 0.0f, 0.0f});
    private static FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f});
    private static FloatBuffer listenerVel = BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f});
    private static FloatBuffer listenerOri = BufferUtils.createFloatBuffer(6).put(new float[]{0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f});
    
    public static void setUpALSounds(){
        try {
            AL.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(AudioManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void destroyALSounds(){
        AL10.alDeleteSources(source);
        AL10.alDeleteBuffers(buffer);
        AL.destroy();
    }
    
    public static void initSounds(){
        try {
            if (loadSoundFiles() == AL10.AL_FALSE) {
                System.err.println("Error loading sound data.");
                System.exit(1);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private static int loadSoundFiles() throws FileNotFoundException {
        AL10.alGenBuffers(buffer);
        if(AL10.alGetError() != AL10.AL_NO_ERROR){
            System.err.println("Buffer Error");
            return AL10.AL_FALSE;
        }
        WaveData waveFile = WaveData.create(new BufferedInputStream(new FileInputStream("assets/Sounds/pew.wav")));
        AL10.alBufferData(buffer.get(PEW), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream("assets/Sounds/death.wav")));
        AL10.alBufferData(buffer.get(DEATH), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream("assets/Sounds/loose.wav")));
        AL10.alBufferData(buffer.get(LOOSE), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream("assets/Sounds/win.wav")));
        AL10.alBufferData(buffer.get(WIN), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream("assets/Sounds/BA1.wav")));
        AL10.alBufferData(buffer.get(BAH), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream("assets/Sounds/BA2.wav")));
        AL10.alBufferData(buffer.get(BOH), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        AL10.alGenSources(source);
        if (AL10.alGetError() != AL10.AL_NO_ERROR){
            System.err.println("Sources Error");
            return AL10.AL_FALSE;
        }
        
        AL10.alSourcei(source.get(PEW),   AL10.AL_BUFFER,   buffer.get(PEW));
        AL10.alSourcei(source.get(DEATH), AL10.AL_BUFFER,   buffer.get(DEATH));
        AL10.alSourcei(source.get(LOOSE), AL10.AL_BUFFER,   buffer.get(LOOSE));
        AL10.alSourcei(source.get(WIN),   AL10.AL_BUFFER,   buffer.get(WIN));
        AL10.alSourcei(source.get(BAH),   AL10.AL_BUFFER,   buffer.get(BAH));
        AL10.alSourcei(source.get(BOH),   AL10.AL_BUFFER,   buffer.get(BOH));

        
        if (AL10.alGetError() == AL10.AL_NO_ERROR){
            return AL10.AL_TRUE;
        }
        return AL10.AL_FALSE;
    }
    
    public static void play(int i){
        AL10.alSourcePlay(source.get(i));
    }
}