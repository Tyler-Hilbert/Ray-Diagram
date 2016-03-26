
package lenses;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DivergingLens extends Lens{
    public DivergingLens(double ho, double disO, double f) {
        super(ho, disO, f);
    }
    
    /**
     * Calculates the locations of the p ray and then draws them to the lens view
     * @param gc The GraphicsContext to draws the light refractions on
     */
    @Override
    protected void drawPRay(GraphicsContext gc) {
        // Calculate angle from where the ray goes through the lens to where it reflects back to the focal point
        double radians = Math.atan(ho/f);
        
        // Draw reflected ray
        double yr = Lenses.CANVAS_HEIGHT; // Large number to ensure large ray drawn
        double xr = yr / Math.tan(radians);
        
        double xe = getXl() - xr;
        double ye = getYl() - ho + yr;
        
        gc.setStroke(Color.RED);
        gc.strokeLine(getXl(), getYl() - ho, xe, ye);
        
        
        // Draw refracted angle
        xr = yr / Math.tan(radians);
        
        xe = getXl() + xr;
        ye = ho - yr;
        
        gc.setStroke(Color.BLACK);
        gc.strokeLine(getXl(), getYl() - ho, xe, ye);
        
        
        // Draw ray from object to lens
        gc.strokeLine(getXl() - disO, getYl() - ho, getXl(), getYl() - ho);
    }

    /**
     * Calculates the locations of the F ray and then draws them to the lens view
     * @param gc The GraphicsContext to draws the light refractions on
     */
    @Override
    protected void drawFRay(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        
        // Calculate angle to the focal point and draw ray from object to lens
        double radians = Math.atan((disO + f) / ho);
        
        double xr = disO;
        double yr = xr / Math.tan(radians);
        
        double ye = getYl() - ho + yr;
        double xe = getXl();
        
        gc.strokeLine(getXl() - disO, getYl() - ho, xe, ye);
        
        // Draw Refracted ray
        gc.strokeLine(getXl(), ye, Lenses.CANVAS_WIDTH, ye);
        
        // Draw reflected ray
        gc.setStroke(Color.RED);
        gc.strokeLine(getXl(), ye, 0, ye);
    }
    
    /**
     * Prints the values of the image to the lens view
     * @param gc The GraphicsContext to draws the light refractions on
     */
    public void outputValues (GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        
        // Calculate the distance between the lens and the image
        double disImg = 1 / (1/f - 1/disO); 
        // Calculate height of the image
        double hImg = disImg * ho / disO;
        // Calculate magnification
        double magnification = disImg / disO;
        
        gc.fillText("Distance from lens to image: " + disImg, 15, Lenses.CANVAS_HEIGHT + 15);
        gc.fillText("Height of the image " + hImg, 15, Lenses.CANVAS_HEIGHT + 30);
        gc.fillText("Magnification: " + magnification, 15, Lenses.CANVAS_HEIGHT + 45);
        gc.fillText("Image classification: Virtual upright reduced", 15, Lenses.CANVAS_HEIGHT + 60);
    }
}
