/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicadmin2.Controllers;

import clinicadmin2.BDConector;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author María
 */
public class ListadoPacientesController implements Initializable {

    @FXML
    private TextArea tArea_pacientes;
    
    private BDConector BDConector;
    
    @FXML
    private Button btn_Buscar;
    @FXML
    private Button btn_Eliminar;
    @FXML
    private TextField txt_Nombre;
    @FXML
    private TextField txt_Apellidos;
    
    
    public void mostrarListado(String listado){
    
    tArea_pacientes.setText(listado);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BDConector  = new BDConector();
        
        
        
    }    

    
    //Botón que busca el paciente escrito y lo muestra en el cuadro de texto
    @FXML
    private void buscar(ActionEvent event) {
       String paciente = BDConector.buscarPacienteBD(this.txt_Nombre.getText(), this.txt_Apellidos.getText());
       this.tArea_pacientes.setText(paciente);
       if (paciente == ""){
       this.tArea_pacientes.setText("Paciente no encontrado en la base de datos. Introduce nombre y dos apellidos");
       }
    }

    //Botón que elimina el paciente buscado y muestra el listado de pacientes actualizado
    @FXML
    private void eliminar(ActionEvent event) throws SQLException {
        boolean borrado = BDConector.eliminarPaciente(this.txt_Nombre.getText(), this.txt_Apellidos.getText());
        if (!borrado){tArea_pacientes.setText("No se ha eliminado ningún paciente, comprueba los datos introducidos");
        }
        
        else {
        String listado = BDConector.consultarPacientes();
        tArea_pacientes.setText("Se ha eliminado el paciente y sus citas asociadas\n\n" + listado);
        this.txt_Nombre.setText("");
        this.txt_Apellidos.setText("");
    }
   } 
    
}
