package lenses;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class ConvergingLens {
    private double ho; // Height of object
    private double disO; // Distance of object
    private double f; // Focal length
    
    public ConvergingLens(double ho, double disO, double f) {
        this.ho = ho;
        this.disO = disO;
        this.f = f;
    }
    
    /**
     * Draws the lens lines on the lens view
     * @param gc The GraphicsContext to draws the lens on
     */
    public void drawLens(GraphicsContext gc) {
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
    public void drawLight(GraphicsContext gc){
        gc.setStroke(Color.BLACK);
          
        drawPRay(gc);
        drawCRay(gc);
        drawFRay(gc);
    }
    
    /**
     * Calculates the locations of the p ray and then draws them to the lens view
     * @param gc The GraphicsContext to draws the light refractions on
     */
    private void drawPRay(GraphicsContext gc) {
        // Draws P ray going up to the lens
        gc.strokeLine(getXl() - disO, getYl() - ho, getXl(), getYl() - ho);
        
        // Draw P Ray after refractionby.
        // The angle that the light ray goes from the lens to the focal point
        double focalAngle = Math.atan(f/ho); 
        
        // Extend the triangle so the line goes past the focal point
        double scale = Lenses.CANVAS_HEIGHT*2; // A large number that makes sure the line goes far enough
        double relativeX = Math.sin(focalAngle) * scale;
                
        double relativeY = Math.cos(focalAngle) * scale;
        
        // Move the points so the ray starts at the lens
        double exactX = getXl() + relativeX;
        double exactY = getYl() - ho + relativeY;
        
        gc.strokeLine(getXl(), getYl() - ho, exactX, exactY);
    }
    
    /**
     * Calculates the locations of the C ray and then draws them to the lens view
     * @param gc The GraphicsContext to draws the light refractions on
     */
    private void drawCRay(GraphicsContext gc) {
        // Calulate the angle the light ray will travel to the center
        double rayAngle = Math.atan(disO / ho);
        System.out.println(rayAngle);
        
        // Create triangle to find x and y sizes
        double relativeX = Lenses.CANVAS_WIDTH; // A large x value to draw the line to
        double relativeY = relativeX / Math.tan(rayAngle);
        
        // Move x and y posistions to start at the object
        double exactX = relativeX + getXl() - disO;
        double exactY = relativeY + getYl() - ho;
        
        gc.strokeLine(getXl() - disO, getYl() - ho, exactX, exactY);
    }
    
    /**
     * Calculates the locations of the F ray and then draws them to the lens view
     * @param gc The GraphicsContext to draws the light refractions on
     */
    private void drawFRay(GraphicsContext gc) {
        // Calculate the angle to the focal point
        double rayAngle = Math.atan((disO - f) / ho);
        
        // Use imaginary triangle to find where ray goes through lens
        double relativeY = disO / Math.tan(rayAngle);
        double exactY = relativeY + getYl() - ho;
        
        // Draw ray before refraction
        gc.strokeLine(getXl() - disO, getYl() - ho, getXl(), exactY);
        
        // Draw ray after refraction
        gc.strokeLine(getXl(), exactY, Lenses.CANVAS_WIDTH, exactY);
        
    }
    
    /**
     * @return The center x position of the lense
     */
    private double getXl() {
        return Lenses.CANVAS_WIDTH / 2;
    }
    
    /**
     * @return The center y position of the lense
     */
    private double getYl() {
        return Lenses.CANVAS_HEIGHT / 2;
    }
}
