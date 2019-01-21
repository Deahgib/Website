package battleships.engine.audio;


import battleships.engine.GameGlobals;
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

/**
 *
 * @author Louis Bennette
 */
public class AudioManager {
    public AudioManager(){}
    
    public static final int NUM_BUFFERS = 20;
    public static final int NUM_SOURCES = 20;
    
    public static final SoundFile MENU_MUSIC = new SoundFile(0, "main-menu-music", SoundFile.Type.MUSIC);
    public static final SoundFile CLICK = new SoundFile(1, "click", SoundFile.Type.EFFECT);
    public static final SoundFile MISSILE_FLY = new SoundFile(2, "fly", SoundFile.Type.EFFECT);
    public static final SoundFile E_HIT = new SoundFile(3, "e-hit", SoundFile.Type.EFFECT);
    public static final SoundFile E_SINK = new SoundFile(4, "e-sink", SoundFile.Type.EFFECT);
    public static final SoundFile E_MISS = new SoundFile(5, "e-miss", SoundFile.Type.EFFECT);
    public static final SoundFile GAME_OVER_WIN = new SoundFile(6, "win", SoundFile.Type.EFFECT);
    public static final SoundFile GAME_OVER_LOOSE = new SoundFile(7, "loose", SoundFile.Type.EFFECT);
    public static final SoundFile PLACE_SHIP = new SoundFile(8, "place-ship", SoundFile.Type.EFFECT);
    public static final SoundFile GAME_MUSIC = new SoundFile(9, "game-music", SoundFile.Type.MUSIC);
    public static final SoundFile CREDITS = new SoundFile(10, "credits-music", SoundFile.Type.MUSIC);
    public static final SoundFile P_HIT = new SoundFile(11, "p-hit", SoundFile.Type.EFFECT);
    public static final SoundFile P_SINK = new SoundFile(12, "p-sink", SoundFile.Type.EFFECT);
    public static final SoundFile P_MISS = new SoundFile(13, "p-miss", SoundFile.Type.EFFECT);
    public static final SoundFile SECRET = new SoundFile(14, "secret", SoundFile.Type.EFFECT);
    public static final SoundFile EXPLOSION = new SoundFile(15, "explosion", SoundFile.Type.EFFECT);
    public static final SoundFile AMBIENCE = new SoundFile(16, "ambience", SoundFile.Type.MUSIC);
    
    private static final String path =  "assets/sounds/";
    private static final String extention = ".wav";
    
    private static IntBuffer buffer = BufferUtils.createIntBuffer(NUM_BUFFERS);
    private static IntBuffer source = BufferUtils.createIntBuffer(NUM_SOURCES);
    private static FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3*NUM_BUFFERS).put(new float[]{0.0f, 0.0f, 0.0f});
    private static FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3*NUM_BUFFERS).put(new float[]{0.0f, 0.0f, 0.0f});
    private static FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f});
    private static FloatBuffer listenerVel = BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f});
    private static FloatBuffer listenerOri = BufferUtils.createFloatBuffer(6).put(new float[]{0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f});
    
    public static void initOpenAL(){
        try {
            AL.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(AudioManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void destroyOpenAL(){
        AL10.alDeleteSources(source);
        AL10.alDeleteBuffers(buffer);
        AL.destroy();
    }
    
    public static void createSounds(){
        try {
            if (loadSoundFiles() == AL10.AL_FALSE) {
                System.err.println("Error loading sound data.");
                System.exit(1);
            }
        } catch (FileNotFoundException ex) {
        }
        
    }

    private static int loadSoundFiles() throws FileNotFoundException {
        AL10.alGenBuffers(buffer);
        if(AL10.alGetError() != AL10.AL_NO_ERROR){
            System.err.println("Buffer Error");
            return AL10.AL_FALSE;
        }
        WaveData waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + MENU_MUSIC.getFileName() + extention)));
        AL10.alBufferData(buffer.get(MENU_MUSIC.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + CLICK.getFileName() + extention)));
        AL10.alBufferData(buffer.get(CLICK.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + MISSILE_FLY.getFileName() + extention)));
        AL10.alBufferData(buffer.get(MISSILE_FLY.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + E_HIT.getFileName() + extention)));
        AL10.alBufferData(buffer.get(E_HIT.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + E_SINK.getFileName() + extention)));
        AL10.alBufferData(buffer.get(E_SINK.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + E_MISS.getFileName() + extention)));
        AL10.alBufferData(buffer.get(E_MISS.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + GAME_OVER_WIN.getFileName() + extention)));
        AL10.alBufferData(buffer.get(GAME_OVER_WIN.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + GAME_OVER_LOOSE.getFileName() + extention)));
        AL10.alBufferData(buffer.get(GAME_OVER_LOOSE.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + PLACE_SHIP.getFileName() + extention)));
        AL10.alBufferData(buffer.get(PLACE_SHIP.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + GAME_MUSIC.getFileName() + extention)));
        AL10.alBufferData(buffer.get(GAME_MUSIC.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + CREDITS.getFileName() + extention)));
        AL10.alBufferData(buffer.get(CREDITS.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + P_HIT.getFileName() + extention)));
        AL10.alBufferData(buffer.get(P_HIT.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + P_SINK.getFileName() + extention)));
        AL10.alBufferData(buffer.get(P_SINK.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + P_MISS.getFileName() + extention)));
        AL10.alBufferData(buffer.get(P_MISS.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + SECRET.getFileName() + extention)));
        AL10.alBufferData(buffer.get(SECRET.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + EXPLOSION.getFileName() + extention)));
        AL10.alBufferData(buffer.get(EXPLOSION.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(path + AMBIENCE.getFileName() + extention)));
        AL10.alBufferData(buffer.get(AMBIENCE.getID()), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        
        AL10.alGenSources(source);
        if (AL10.alGetError() != AL10.AL_NO_ERROR){
            System.err.println("Sources Error");
            return AL10.AL_FALSE;
        }
        
        AL10.alSourcei(source.get(MENU_MUSIC.getID()),      AL10.AL_BUFFER,   buffer.get(MENU_MUSIC.getID()));
        AL10.alSourcei(source.get(CLICK.getID()),           AL10.AL_BUFFER,   buffer.get(CLICK.getID()));
        AL10.alSourcei(source.get(MISSILE_FLY.getID()),     AL10.AL_BUFFER,   buffer.get(MISSILE_FLY.getID()));
        AL10.alSourcei(source.get(E_HIT.getID()),           AL10.AL_BUFFER,   buffer.get(E_HIT.getID()));
        AL10.alSourcei(source.get(E_SINK.getID()),          AL10.AL_BUFFER,   buffer.get(E_SINK.getID()));
        AL10.alSourcei(source.get(E_MISS.getID()),          AL10.AL_BUFFER,   buffer.get(E_MISS.getID()));
        AL10.alSourcei(source.get(GAME_OVER_WIN.getID()),   AL10.AL_BUFFER,   buffer.get(GAME_OVER_WIN.getID()));
        AL10.alSourcei(source.get(GAME_OVER_LOOSE.getID()), AL10.AL_BUFFER,   buffer.get(GAME_OVER_LOOSE.getID()));
        AL10.alSourcei(source.get(PLACE_SHIP.getID()),      AL10.AL_BUFFER,   buffer.get(PLACE_SHIP.getID()));
        AL10.alSourcei(source.get(GAME_MUSIC.getID()),      AL10.AL_BUFFER,   buffer.get(GAME_MUSIC.getID()));
        AL10.alSourcei(source.get(CREDITS.getID()),         AL10.AL_BUFFER,   buffer.get(CREDITS.getID()));
        AL10.alSourcei(source.get(P_HIT.getID()),           AL10.AL_BUFFER,   buffer.get(P_HIT.getID()));
        AL10.alSourcei(source.get(P_SINK.getID()),          AL10.AL_BUFFER,   buffer.get(P_SINK.getID()));
        AL10.alSourcei(source.get(P_MISS.getID()),          AL10.AL_BUFFER,   buffer.get(P_MISS.getID()));
        AL10.alSourcei(source.get(SECRET.getID()),          AL10.AL_BUFFER,   buffer.get(SECRET.getID()));
        AL10.alSourcei(source.get(EXPLOSION.getID()),       AL10.AL_BUFFER,   buffer.get(EXPLOSION.getID()));
        AL10.alSourcei(source.get(AMBIENCE.getID()),        AL10.AL_BUFFER,   buffer.get(AMBIENCE.getID()));

        
        if (AL10.alGetError() == AL10.AL_NO_ERROR){
            return AL10.AL_TRUE;
        }
        return AL10.AL_FALSE;
    }
    
    public static void play(SoundFile soundFile){
        if(GameGlobals.gameMusicOn && soundFile.getSoundType() == SoundFile.Type.MUSIC){
            AL10.alSourcePlay(source.get(soundFile.getID()));
        }
        else if(GameGlobals.gameEffectSoundsOn && soundFile.getSoundType() == SoundFile.Type.EFFECT){
            AL10.alSourcePlay(source.get(soundFile.getID()));
        }
    }
        
    public static void stop(SoundFile soudFile) {
        AL10.alSourceStop(source.get(soudFile.getID()));
    }
    
    public static void pause(SoundFile soudFile) {
        AL10.alSourcePause(source.get(soudFile.getID()));
    }
}