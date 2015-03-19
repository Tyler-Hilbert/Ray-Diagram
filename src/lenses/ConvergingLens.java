package lenses;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class ConvergingLens extends Lens {   
    public ConvergingLens(double ho, double disO, double f) {
        super(ho, disO, f);
    }
    
    /**
     * Calculates the locations of the p ray and then draws them to the lens view
     * @param gc The GraphicsContext to draws the light refractions on
     */
    @Override
    protected void drawPRay(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
                
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
        
        // Draw reflected ray for virtual image
        if (f > disO) {
            gc.setStroke(Color.RED);
            
            // Create triangle ofproportional size
            double xLen = Lenses.CANVAS_WIDTH;
            double reflectionRadians = Math.toRadians(90 - Math.toDegrees(focalAngle));
            double yLen = Math.tan(reflectionRadians) * xLen;
            
            // Move traingle to correct posistion
            double xPos = getXl() - xLen;
            double yPos = getYl() - ho - yLen;
            
            gc.strokeLine(getXl(), getYl() - ho, xPos, yPos);
        }
    }
    
    /**
     * Calculates the locations of the F ray and then draws them to the lens view
     * @param gc The GraphicsContext to draws the light refractions on
     */
    @Override
    protected void drawFRay(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        
        // Calculate the angle to the focal point
        double rayAngle = Math.atan((disO - f) / ho);
        
        // Use imaginary triangle to find where ray goes through lens
        double relativeY = disO / Math.tan(rayAngle);
        double exactY = relativeY + getYl() - ho;
        
        // Draw ray before refraction
        gc.strokeLine(getXl() - disO, getYl() - ho, getXl(), exactY);
        
        // Draw ray after refraction
        gc.strokeLine(getXl(), exactY, Lenses.CANVAS_WIDTH, exactY);
        
        // Draw reflected ray for virtual image
        if (f > disO) {
            gc.setStroke(Color.RED);
            
            gc.strokeLine(getXl(), exactY, 0, exactY);
        }
    }
}
