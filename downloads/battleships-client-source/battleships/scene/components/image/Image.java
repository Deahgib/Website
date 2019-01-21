package battleships.scene.components.image;

import battleships.engine.OpenGLManager;
import battleships.engine.Point;
import battleships.engine.TextureManager;
import battleships.scene.components.AbstractComponent;

/**
 *
 * @author Louis Bennette
 */
public class Image extends AbstractComponent {

    public Image(double x, double y, double width, double height, String pathNameOfFile) {
        this.setLocation(new Point(x, y));
        this.setWidth(width);
        this.setHeight(height);

        this.setTexture(TextureManager.loadTexture(pathNameOfFile));
    }

    @Override
    public void draw() {
        if (this.isVisible()) {
            OpenGLManager.drawSquareWithTex((float) this.getX(), (float) this.getY(), (float) this.getWidth(), (float) this.getHeight(), this.getTexture());
        }
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
