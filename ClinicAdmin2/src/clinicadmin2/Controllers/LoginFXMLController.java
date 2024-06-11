/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicadmin2.Controllers;

import clinicadmin2.BDConector;
import clinicadmin2.Validacion;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author María
 */
public class LoginFXMLController implements Initializable {

    @FXML
    private TextField txt_User;
    @FXML
    private TextField txt_Password;
    @FXML
    private Button bt_IniciarSesion;
    
    private BDConector BDConector;
    private Validacion Validacion;
    
    
     @FXML
    private void iniciarSesion(ActionEvent event) throws IOException {
        try{
        //Obtenemos el uduario y contraseña introducido
        String usuario = txt_User.getText();
        String contraseña = txt_Password.getText();
            
        int rol;
        rol = Validacion.buscar(usuario, contraseña);
                   
        if (rol != -1){
            //Obtenemos la ventana actual a través de la Scene del botón y la cerramos
            Stage stageActual = (Stage) bt_IniciarSesion.getScene().getWindow();
            stageActual.close();
            
            //Cargamos el archivo FXML de la vista Principal.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Principal.fxml"));
            Parent root = loader.load();
            
            //Obtenemos el controlador de la vista
            PrincipalController controlador = loader.getController();
            
            controlador.inicializar(rol);
            
            //Creamos una escena y stage
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setScene(scene);
            stage.showAndWait();
        }
        else {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña no existen");
        }    
     } catch (IOException e) {
    e.printStackTrace();
   }     
  }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
     BDConector  = new BDConector();
     Validacion = new Validacion();
    }    
    
}
