/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicadmin2.Controllers;

import clinicadmin2.BDConector;
import static java.lang.Integer.parseInt;
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
public class NuevoPacienteController implements Initializable {

    @FXML
    private Button bt_RegistrarPaciente;
    @FXML
    private TextField txt_Nombre;
    @FXML
    private TextField txt_Apellidos;
    @FXML
    private TextField txt_Edad;
    @FXML
    private TextField txt_Telefono;
    @FXML
    private TextField txt_Email;
    @FXML
    private Label lb_registro;
    
    private BDConector BDConector;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BDConector  = new BDConector();
    }    

    @FXML
    private void registrarPaciente(ActionEvent event) {
         try {
        //Obtenemos los datos introducidos del nuevo paciente
        String nombre = txt_Nombre.getText();
        String apellidos = txt_Apellidos.getText();
        int edad = parseInt(txt_Edad.getText());
        String telefono = txt_Telefono.getText();
        String email = txt_Email.getText();
       
        if (nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty()){
        lb_registro.setText("Introduce al menos nombre, apellidos y email.");
        }
        else {
        boolean registro = BDConector.registrarPacienteBD(nombre, apellidos, edad, telefono, email);
        if (registro){
        lb_registro.setText("Se ha registrado el nuevo paciente");
                }
            }
        }
        catch (Exception e) { 
            System.out.println(e.getMessage());
            lb_registro.setText("Ha ocurrido un error en el registro. Introduce los datos correctamente");
        }
    }
    
}
