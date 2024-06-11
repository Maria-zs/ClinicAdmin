/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicadmin2.Controllers;

import clinicadmin2.BDConector;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author María
 */
public class CitasController implements Initializable {
    
     private BDConector BDConector;

        @FXML
    private TextArea tArea_Citas;
    @FXML
    private TextField txt_Nombre;
    @FXML
    private TextField txt_ApellidosNueva;
    @FXML
    private DatePicker fecha_citaBuscar;
    @FXML
    private DatePicker fecha_citaNueva;
    @FXML
    private TextField text_Hora;
    @FXML
    private TextField txt_ApellidosBuscar;
    @FXML
    private Label txt_Mensaje;
    
    public void mostrarListado(String listado){
     tArea_Citas.setText(listado);
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
    private void buscarPorFecha(ActionEvent event) {
        LocalDate fecha = fecha_citaBuscar.getValue();
        Date date = Date.valueOf(fecha);
       String listado = BDConector.buscarCitaFecha(date);
       mostrarListado(listado);
    }

    @FXML
    private void buscarPorApellidos(ActionEvent event) {
        String listado = BDConector.buscarCitaApellidos(this.txt_ApellidosBuscar.getText());
        mostrarListado(listado);
    }

    @FXML
    private void añadirCita(ActionEvent event) {
        LocalDate fecha = fecha_citaNueva.getValue();
        if (fecha == null){
            txt_Mensaje.setText("Ha ocurrido un error. Comprueba los datos introducidos");}
        else {
        Date date = Date.valueOf(fecha);
        int id_paciente = BDConector.obtenerIDpaciente(txt_Nombre.getText(), txt_ApellidosNueva.getText());
        boolean registro = BDConector.registrarCitaBD(date, text_Hora.getText(), id_paciente);
        String listado = BDConector.consultarCitas();
        tArea_Citas.setText(listado);
        this.txt_Nombre.setText("");
        this.txt_ApellidosNueva.setText("");
        this.text_Hora.setText("");
        if (registro) {txt_Mensaje.setText("Cita añadida correctamente");}
        else {txt_Mensaje.setText("Ha ocurrido un error. Comprueba los datos introducidos");}
    }
    }

    @FXML
    private void eliminarCita(ActionEvent event) {
        String nombre = txt_Nombre.getText();
        String apellidos = txt_ApellidosNueva.getText();
        LocalDate fecha = fecha_citaNueva.getValue();
        if (nombre.isEmpty() || apellidos.isEmpty()  || fecha == null){
            txt_Mensaje.setText("Ha ocurrido un error");}
        else {
        Date date = Date.valueOf(fecha);
        boolean borrado = BDConector.eliminarCitaBD(nombre, apellidos, date);
        if (borrado) { txt_Mensaje.setText("Cita eliminada correctamente."); }
        else {txt_Mensaje.setText("Ha ocurrido un error");}
        String listado = BDConector.consultarCitas();
        tArea_Citas.setText(listado);
        this.txt_Nombre.setText("");
        this.txt_ApellidosNueva.setText("");
      }        
    }

    @FXML
    private void mostrarTodo(ActionEvent event) {
        String listado = BDConector.consultarCitas();
        tArea_Citas.setText(listado);
    }

 

    
}
