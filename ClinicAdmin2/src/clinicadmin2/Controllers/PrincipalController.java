/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicadmin2.Controllers;

import clinicadmin2.BDConector;
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
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author María
 */
public class PrincipalController implements Initializable {

    @FXML
    private VBox vboxprincipal;
    @FXML
    private MenuItem mi_cerrarSesion;
    @FXML
    private Button bt_registrarEmpleado;
    
    private int rolUsuario;

       
    public void inicializar(int rol) {
        rolUsuario = rol;
        if (rolUsuario == 1) {
            // Si el rol es 1 (administrador), habilitar el botón Registrar nuevo empleado
            bt_registrarEmpleado.setDisable(false);
        } else {
            // Si el rol es 2 (empleado), deshabilitar el botón Registrar nuevo empleado
            bt_registrarEmpleado.setDisable(true);
        } 
    }
    
 /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        BDConector  = new BDConector();
    }   
    
    
       
    @FXML
    private void abrirRegistroPaciente(ActionEvent event) throws IOException{
        //Cargamos el archivo FXML de la vista NuevoPaciente.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/NuevoPaciente.fxml"));
        Parent root = loader.load();
        
        //Obtenemos el controlador de la vista
        NuevoPacienteController controlador = loader.getController();
              
        //Creamos una escena y stage
        Scene scene = new Scene(root);
        Stage stage = new Stage();
       
        stage.setScene(scene);
        stage.showAndWait();
         }  

    @FXML
    private void abrirListadoPacientes(ActionEvent event) throws IOException {
        //Cargamos el archivo FXML de la vista ListadoPacientes.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ListadoPacientes.fxml"));
        Parent root = loader.load();
        
        //Obtenemos el controlador de la vista
        ListadoPacientesController controlador = loader.getController();
        
        String listado = BDConector.consultarPacientes();
        controlador.mostrarListado(listado);
        
        //Creamos una escena y stage
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.show();
    }

   
      private BDConector BDConector;  

    @FXML
    private void abrirListadoCitas(ActionEvent event) throws IOException {
        //Cargamos el archivo FXML de la vista ListadoPacientes.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Citas.fxml"));
        Parent root = loader.load();
        
        //Obtenemos el controlador de la vista
        CitasController controlador = loader.getController();
        
        String listado = BDConector.consultarCitas();
        controlador.mostrarListado(listado);
        
        //Creamos una escena y stage
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void abrirListadoServicios(ActionEvent event) throws IOException {
        //Cargamos el archivo FXML de la vista ListadoPacientes.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Servicios.fxml"));
        Parent root = loader.load();
        
        //Obtenemos el controlador de la vista
        ServiciosController controlador = loader.getController();
        
        String listado = BDConector.consultarServicios();
        controlador.mostrarListado(listado);
        
        //Creamos una escena y stage
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void abrirListadoMaterial(ActionEvent event) throws IOException {
         //Cargamos el archivo FXML de la vista ListadoPacientes.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Material.fxml"));
        Parent root = loader.load();
        
        //Obtenemos el controlador de la vista
        MaterialController controlador = loader.getController();
        
        String listado = BDConector.consultarMaterial();
        controlador.mostrarListado(listado);
        
        //Creamos una escena y stage
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void abrirRegistroEmpleado(ActionEvent event) throws IOException {
         //Cargamos el archivo FXML de la vista RegistrarEmpleado.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/RegistrarEmpleado.fxml"));
        Parent root = loader.load();
        
        //Obtenemos el controlador de la vista
        RegistrarEmpleadoController controlador = loader.getController();
        
        //Creamos una escena y stage
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }
    
    
    
    
}
