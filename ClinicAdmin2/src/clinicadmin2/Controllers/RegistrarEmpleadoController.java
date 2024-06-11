/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicadmin2.Controllers;

import clinicadmin2.BDConector;
import clinicadmin2.Validacion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author María
 */
public class RegistrarEmpleadoController implements Initializable {

    @FXML
    private TextField txt_User;
    @FXML
    private TextField txt_Contraseña;
    @FXML
    private TextField txt_Rol;
    @FXML
    private Button bt_Registrar;
    @FXML
    private Button bt_Eliminar;
    
    private BDConector BDConector;
    private Validacion Validacion;
    @FXML
    private Label lb_Mensaje;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BDConector  = new BDConector();
        Validacion = new Validacion();
    }    

    @FXML
    private void registrarUsuario(ActionEvent event) {
        try{
        String user = this.txt_User.getText();
        String password = this.txt_Contraseña.getText();
        int idRol = Integer.parseInt(this.txt_Rol.getText());
        String cifrada = Validacion.cifrarSha256(password);
        BDConector.registrarUsuarioBD(user, cifrada, idRol);
        this.lb_Mensaje.setText("Usuario añadido.");
        }
        catch (Exception ex){
        this.lb_Mensaje.setText("Ha ocurrido un error.");
        }
        
        
    }

    @FXML
    private void eliminarUsuario(ActionEvent event) {
        try{
        String user = this.txt_User.getText();
        boolean borrado = BDConector.eliminarUsuarioBD(user);
        if (borrado){
        this.lb_Mensaje.setText("Usuario eliminado.");
        }
        else {
        this.lb_Mensaje.setText("Ha ocurrido un error.");
        }
        }
        catch (Exception ex){
        this.lb_Mensaje.setText("Ha ocurrido un error.");
        }
    }
    
}
