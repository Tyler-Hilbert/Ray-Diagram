package lenses;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Lenses extends Application {
    final static int CANVAS_HEIGHT = 800;
    final static int CANVAS_WIDTH = 800;
    
    @Override
    public void start(Stage primaryStage) {
        // Default values
        double ho = 100; // Height of object
        double disO = 300; // Distance of object
        double f = 50; // Focal length
        
        showInput(primaryStage, ho, disO, f);
        
        primaryStage.setTitle("Lenses");
    }
    
    private void showInput(Stage primaryStage, double ho, double disO, double f) {
        // Set up grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, CANVAS_HEIGHT, CANVAS_WIDTH);
        primaryStage.setScene(scene);
        primaryStage.show();

        
        // Create and add gui components 
        Label hoLabel = new Label("Height of object: ");
        Label disOLabel = new Label("Distance of object from the lens: ");
        Label focalLabel = new Label("Focal length: ");
        Label lensLabel = new Label("Lens type: ");
        
        TextField hoText = new TextField(Double.toString(ho));
        TextField disOText = new TextField(Double.toString(disO));
        TextField focalText = new TextField(Double.toString(f));
        
        ChoiceBox lensChoice = new ChoiceBox(FXCollections.observableArrayList(
            "Converging Lens", "Diverging Lens")
        );
        lensChoice.getSelectionModel().selectFirst();

        
        Button submit = new Button("update");
        submit.setOnAction((ActionEvent e) -> {
            try {
                final double inputHo = Double.parseDouble(hoText.getText());
                final double inputDisO = Double.parseDouble(disOText.getText());
                final double inputFocal = Double.parseDouble(focalText.getText());
                String lensType = (String)lensChoice.getValue();
                showLensView(primaryStage, inputHo, inputDisO, inputFocal, lensType);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        grid.add(hoLabel, 0, 0);
        grid.add(disOLabel, 0, 1);
        grid.add(focalLabel, 0, 2);
        grid.add(hoText, 1, 0);
        grid.add(disOText, 1, 1);
        grid.add(focalText, 1, 2);
        grid.add(lensLabel, 0, 3);
        grid.add(lensChoice, 1, 3);
        grid.add(submit, 0, 4);
    }
    
    private void showLensView(Stage primaryStage, double ho, double disO, double f, String lensType) {
        // Set up view for the light
        GraphicsContext gc;
        Group root = new Group();    
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT+100);
        gc = canvas.getGraphicsContext2D();
        
        Button change = new Button("Change properties");
        change.setOnAction((ActionEvent e) -> {
            showInput(primaryStage, ho, disO, f);
        });
        
       
        // Perform calculations and output to the view
        if (lensType.equals("Converging Lens")) {
            ConvergingLens lens = new ConvergingLens(ho, disO, f);
            lens.drawLens(gc);
            lens.drawLight(gc);
        }
        
        

        root.getChildren().add(canvas);  
        root.getChildren().add(change);
        primaryStage.setScene(new Scene(root));   
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
