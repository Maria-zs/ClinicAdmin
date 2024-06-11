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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author María
 */
public class ServiciosController implements Initializable {
    
    private BDConector BDConector;

    @FXML
    private TextArea tArea_servicios;
    @FXML
    private TextField txt_Nombre;
    @FXML
    private TextField txt_ServicioBorrado;
    @FXML
    private TextField txt_Precio;
    @FXML
    private TextField txt_Descripcion;
    
     public void mostrarListado(String listado){
        tArea_servicios.setText(listado);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BDConector  = new BDConector();
    }    

    @FXML
    private void eliminarServicio(ActionEvent event) {
        boolean borrado = BDConector.eliminarServicioBD(this.txt_ServicioBorrado.getText());
        if (borrado){
        String listado = BDConector.consultarServicios();
        this.tArea_servicios.setText(listado);
        }
        else {this.tArea_servicios.setText("No se ha podido eliminar, comprueba que el nombre es correcto.");
        }
        this.txt_ServicioBorrado.setText("");
    }

    @FXML
    private void añadirServicio(ActionEvent event) {
        try {
        //Obtenemos los datos introducidos del nuevo servicio
        String nombre = txt_Nombre.getText();
        int precio = parseInt(txt_Precio.getText());
        String descripcion = txt_Descripcion.getText();
        
        boolean registro = BDConector.registrarServicioBD(nombre, precio, descripcion);
        if (registro){
        String listado = BDConector.consultarServicios();
        this.tArea_servicios.setText(listado);
        this.txt_Nombre.setText("");
        this.txt_Precio.setText("");
        this.txt_Descripcion.setText("");
        }
        }
        catch (Exception e) { 
            System.out.println(e.getMessage());
            tArea_servicios.setText("Ha ocurrido un error en el registro. Introduce los datos correctamente.");
        }
    }
    
}
