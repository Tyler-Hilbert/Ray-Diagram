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
    
    public abstract void drawLens(GraphicsContext gc);
    
    
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
    protected abstract void drawCRay(GraphicsContext gc);
    protected abstract void drawFRay(GraphicsContext gc);
    
    
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
