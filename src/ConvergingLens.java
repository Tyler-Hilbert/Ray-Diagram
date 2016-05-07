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
        double scale = Main.CANVAS_HEIGHT*2; // A large number that makes sure the line goes far enough
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
            double xLen = Main.CANVAS_WIDTH;
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
        gc.strokeLine(getXl(), exactY, Main.CANVAS_WIDTH, exactY);
        
        // Draw reflected ray for virtual image
        if (f > disO) {
            gc.setStroke(Color.RED);
            
            gc.strokeLine(getXl(), exactY, 0, exactY);
        }
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
        double hImg = -disImg * ho / disO;
        // Calculate magnification
        double magnification = -disImg / disO;
        
        gc.fillText("Distance from lens to image: " + disImg, 15, Main.CANVAS_HEIGHT + 15);
        gc.fillText("Height of the image " + hImg, 15, Main.CANVAS_HEIGHT + 30);
        gc.fillText("Magnification: " + magnification, 15, Main.CANVAS_HEIGHT + 45);
        gc.fillText("Image classification: " + classifyImage(magnification), 15, Main.CANVAS_HEIGHT + 60);
    }
    
    /**
     * @return The classified version of the image
     */
    private String classifyImage(double magnification) {
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
}
