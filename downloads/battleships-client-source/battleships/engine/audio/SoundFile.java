package battleships.engine.audio;

/**
 *
 * @author Louis Bennette
 */
public class SoundFile {
    private int id;
    private String fileName;
    private Type soundType;

    // Create enumerato one for music one for general. Have the play method check what the type of sond file it it and apropriately mute the one the ser does not want to hear.
    public enum Type{
        EFFECT, MUSIC
    }
    
    public SoundFile(int id, String name, Type type){
        this.id       = id;
        this.fileName = name;
        this.soundType = type;
    }
    
    public int getID(){
        return this.id;
    }
    
    public String getFileName(){
        return this.fileName;
    }
    /**
     * @return the soundType
     */
    public Type getSoundType() {
        return soundType;
    }
}
