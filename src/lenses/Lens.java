package lenses;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public abstract class Lens {
    protected double ho; // Height of object
    protected double disO; // Distance of object
    protected double f; // Focal length
    
    public Lens(double ho, double disO, double f) {
        this.ho = ho;
        this.disO = disO;
        this.f = f;
    }
    
    /**
     * Draws the lens lines on the lens view
     * @param gc The GraphicsContext to draws the lens on
     */
    public final void drawLens(GraphicsContext gc) {
        gc.setStroke(Color.BLUE);

        for (int x=0; x<=Lenses.CANVAS_WIDTH; x+=50) {
            gc.strokeLine(x, getYl(), x+25, getYl());
        }
        for (int y=0; y<=Lenses.CANVAS_HEIGHT; y+=50) {
            gc.strokeLine(getXl(), y, getXl(), y+25);
        }
    }
    
    
    /**
     * Calculates where the light rays will be and draws them on the lens view
     * @param gc The GraphicsContext to draws the light refractions on
     */
    public final void drawLight(GraphicsContext gc){          
        drawPRay(gc);
        drawCRay(gc);
        drawFRay(gc);
    }
    
    protected abstract void drawPRay(GraphicsContext gc);
    protected abstract void drawFRay(GraphicsContext gc);
    
    /**
     * Calculates the locations of the C ray and then draws them to the lens view
     * @param gc The GraphicsContext to draws the light refractions on
     */
    private void drawCRay(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        
        // Calulate the angle the light ray will travel to the center
        double rayAngle = Math.atan(disO / ho);
        
        // Create triangle to find x and y sizes
        double relativeX = Lenses.CANVAS_WIDTH; // A large x value to draw the line to
        double relativeY = relativeX / Math.tan(rayAngle);
        
        // Move x and y posistions to start at the object
        double exactX = relativeX + getXl() - disO;
        double exactY = relativeY + getYl() - ho;
        
        gc.strokeLine(getXl() - disO, getYl() - ho, exactX, exactY);
        
        // Draw reflected ray for virtual image
        if (f > disO || this instanceof DivergingLens) {
            gc.setStroke(Color.RED);
            
            // Create imaginary triangle to find x and y values
            double xRelative = Lenses.CANVAS_WIDTH; // large number to ensure ray is long enough
            // Find the complementary angle (the angle that the triangle will use) by doing 90 - the ray angle
            double reflectedAngle = Math.toRadians(90 - Math.toDegrees(rayAngle));
            double yRelative = Math.tan(reflectedAngle) * xRelative;
            
            // Move the points to the correct posistion 
            double xExact = getXl() - disO - xRelative;
            double yExact = getYl() - ho - yRelative;
            
            gc.strokeLine(getXl() - disO, getYl() - ho, xExact, yExact);
        }
    }
    
    
    /**
     * Prints the values of the image to the lens view
     * @param gc The GraphicsContext to draws the light refractions on
     */
    public final void outputValues (GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        
        // Calculate the distance between the lens and the image
        double disImg = 1 / (1/f - 1/disO); 
        // Calculate height of the image
        double hImg = -disImg * ho / disO;
        // Calculate magnification
        double magnification = -disImg / disO;
        
        gc.fillText("Distance from lens to image: " + disImg, 15, Lenses.CANVAS_HEIGHT + 15);
        gc.fillText("Height of the image " + hImg, 15, Lenses.CANVAS_HEIGHT + 30);
        gc.fillText("Magnification: " + magnification, 15, Lenses.CANVAS_HEIGHT + 45);
        gc.fillText("Image classification: " + classifyImage(magnification), 15, Lenses.CANVAS_HEIGHT + 60);
    }
    
        
    /**
     * @return The classified version of the image
     */
    private final String classifyImage(double magnification) {
        String classification = "";
        if (f > disO) 
            classification += "Real ";
        else if (f < disO) 
            classification += "Virtual ";
        
        if (magnification > 0) 
            classification += "upright ";
        else if (magnification < 0) 
            classification += "inverted ";
        
        if (Math.abs(magnification) > 1) 
            classification += "englarged";
        else if (Math.abs(magnification) < 1) 
            classification += "reduced";
        
        return classification;
    }
    
    /**
     * @return The center x position of the lense
     */
    protected final double getXl() {
        return Lenses.CANVAS_WIDTH / 2;
    }
    
    /**
     * @return The center y position of the lense
     */
    protected final double getYl() {
        return Lenses.CANVAS_HEIGHT / 2;
    }
}
