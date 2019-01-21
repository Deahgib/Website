package battleships.scene.components.label;

import battleships.engine.FontManager;
import battleships.engine.OpenGLManager;
import battleships.engine.Point;
import battleships.scene.components.AbstractComponent;
import java.awt.Font;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Louis Bennette
 */
public class Label extends AbstractComponent {
    private String text;
    private boolean title;
    private TextFormat textFormat;
    private float fontSize;
    private int fontStyle;
    private Font coreFont;
    private TrueTypeFont textFont;
    private Color textColour;
    
    private Point drawOrigin;
    
    private boolean fontAutoResisable;
    
    private enum TextFormat{
        CENTERED, LEFT_ALIGN, RIGHT_ALIGN;
    }
    
    
    public Label(double x, double y, double width, double height, String name){
        this.origin = new Point(x, y);
        this.drawOrigin = new Point(x, y);
        this.width = width;
        this.height = height;
        
        this.textFormat = TextFormat.LEFT_ALIGN;
        this.fontSize = 32f;
        this.fontStyle = Font.PLAIN;
        this.fontAutoResisable = true;
        this.textColour = new Color(255, 255, 255, 255);
        
        this.name = name;
        this.text = name;
        this.title = false;
        
        // Create a pointer to the general font.
        this.coreFont = FontManager.getGeneralFont();
        
        // The ttf drawable font is made front the coreFont 
        this.textFont = FontManager.makeTTFont(this.coreFont, this.fontSize, 
                this.fontStyle);
    }

    public void findDrawOrigin() {
        // Find the Y
        int heightMidPoint = (int) this.getHeight() / 2;
        int textHeight = this.textFont.getHeight();
        double drawYPosInLabel = (heightMidPoint - (textHeight / 2));
        double drawY = this.getY() + drawYPosInLabel;

        int textWidth = this.textFont.getWidth(this.text);
        double drawX;
        
        // Find the X pos.
        switch(this.textFormat){
            case LEFT_ALIGN:
                drawX = this.origin.getX();
                this.drawOrigin = new Point(drawX, drawY);
                break;
            case RIGHT_ALIGN:
                drawX = this.getX() + (this.getWidth() - textWidth);
                this.drawOrigin = new Point(drawX, drawY);
                break;
            case CENTERED:
                int labelWidthMidPoint = (int) this.getWidth() / 2;
                double drawXPosInLabel = labelWidthMidPoint - (textWidth / 2);
                drawX = this.getX() + drawXPosInLabel;
                this.drawOrigin = new Point(drawX, drawY);
                break;
        }
    }

    private void autoresizeFont() {
        if (this.isFontAutoResisable()) {
            double textLength = this.textFont.getWidth(this.text);
            if (textLength > this.getWidth()) {
                double newTextLength = this.textFont.getWidth(this.text);
                float mod = this.fontSize;
                while (newTextLength > this.getWidth()) {
                    //System.out.println(this.getName() + ": " + mod);
                    mod = mod - 2;
                    this.textFont = FontManager.makeTTFont(this.coreFont, mod, this.fontStyle);
                    newTextLength = this.textFont.getWidth(this.text);
                }
                this.fontSize = mod;
            }
        }
    }
    
    @Override
    public void draw() {
        if (this.isVisible()) {
            glEnable(GL_BLEND);
                this.textFont.drawString((float) this.drawOrigin.getX(), (float) this.drawOrigin.getY(), this.getText(), this.getTextColour());
            glDisable(GL_BLEND);
            OpenGLManager.setColor(255, 255, 255);
        }
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Don't call update on a Label!");
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.findDrawOrigin();
        this.autoresizeFont();
    }

    public boolean isTitle() {
        return title;
    }

    public void makeTitle(boolean title) {
        if(title){
            this.coreFont = FontManager.getTitleFont();
            
        }
        else{
            this.coreFont = FontManager.getGeneralFont();
        }
        this.textFont = FontManager.makeTTFont(this.coreFont, this.fontSize, this.fontStyle);
        
        if(this.title != title){
            this.title = title;
            this.findDrawOrigin();
        }
    }
    
    public TextFormat getTextFormat() {
        return textFormat;
    }
    
    public float getFontSize() {
        return fontSize;
    }

    /**
     * Only call if the fontSize is the only characteristic of the font you wish
     * to change. If you also need to set the font style consider using the 
     * setFontStyleAndSize() method.
     * @param fontSize the desired font size.
     */
    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
        this.textFont = FontManager.makeTTFont(this.coreFont, this.fontSize, this.fontStyle);
        this.findDrawOrigin();
        this.autoresizeFont();
    }

    public int getFontStyle() {
        return fontStyle;
    }
    /**
     * Only call if the fontStyle is the only characteristic of the font you 
     * wish to change. If you also need to set the font size consider using the 
     * setFontStyleAndSize() method
     * @param fontStyle the desired font style Font.BOLD or Font.PLAIN for 
     * example.
     */
    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
        this.textFont = FontManager.makeTTFont(this.coreFont, this.fontSize, this.fontStyle);
        this.findDrawOrigin();
        this.autoresizeFont();
    }
    
    /**
     * This method should be called if you want to update the font size and the 
     * font style at the same time. Do not call setFontSize() then setFont 
     * style. This
     * @param fontStyle the desired font style Font.BOLD or Font.PLAIN for 
     * example.
     * @param fontSize the desired font size.
     */
    public void setFontStyleAndSize(int fontStyle, float fontSize){
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;
        this.textFont = FontManager.makeTTFont(this.coreFont, this.fontSize, this.fontStyle);
    }
        
    private void setTextFormat(TextFormat textFormat) {
        this.textFormat = textFormat;
        this.findDrawOrigin(); 
    }
    
    public void textAlignCentered(){
        this.setTextFormat(TextFormat.CENTERED);
    }
    public void textAlignLeft(){
        this.setTextFormat(TextFormat.LEFT_ALIGN);
    }
    public void textAlignRight(){
        this.setTextFormat(TextFormat.RIGHT_ALIGN);
    }

    /**
     * @return the fontResisable variable indicating if the font will auto 
     * resize to fit inside the bounds of it's component box.
     */
    public boolean isFontAutoResisable() {
        return fontAutoResisable;
    }

    /**
     * @param fontResisable the fontResisable to set
     */
    public void setFontAutoResisable(boolean fontResisable) {
        this.fontAutoResisable = fontResisable;
    }

    /**
     * @return the textColour
     */
    public Color getTextColour() {
        return textColour;
    }

    /**
     * @param textColour the textColour to set
     */
    public void setTextColour(Color textColour) {
        this.textColour = textColour;
    }
    
    
    public void setTextColour(int r, int g, int b){
        this.setTextColour(new Color(r,g,b));
    }
    
    public void setTextColour(int r, int g, int b, int a) {
        this.setTextColour(new Color(r,g,b,a));
    }
}
