/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicadmin2;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author María
 */
public class ClinicAdmin2 extends Application {
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vistas/LoginFXML.fxml"));
                
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("ClinicAdmin 1.0 - Login");
        stage.getIcons().add(new Image("/Imagenes/ImagenLogo.png"));
        stage.show();  
        
    }
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
}
