/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicadmin2.Controllers;

import clinicadmin2.BDConector;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author María
 */
public class MaterialController implements Initializable {
    
    private BDConector BDConector;
    
    @FXML
    private TextArea tArea_Material;
    @FXML
    private TextField txt_Producto;
    @FXML
    private TextField txt_Cantidad;
    @FXML
    private TextField txt_ProductoEliminado;
    @FXML
    private Label lb_Mensaje;
    
     public void mostrarListado(String listado){
     tArea_Material.setText(listado);
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
    private void actualizarMaterial(ActionEvent event) {
        this.lb_Mensaje.setText("");
        try{
        boolean actualizacion = BDConector.actualizarMaterialBD(this.txt_Producto.getText(), Integer.parseInt(this.txt_Cantidad.getText()));
        if (!actualizacion){
        this.lb_Mensaje.setText("Ha ocurrido un error. Introduce los datos correctamente.");
        }
        else {
        this.lb_Mensaje.setText("Actualizado correctamente.");
        }
        String listado = BDConector.consultarMaterial();
        tArea_Material.setText(listado);
        this.txt_Producto.setText("");
        this.txt_Cantidad.setText("");
        }
        catch (Exception ex){
        this.lb_Mensaje.setText("Ha ocurrido un error. Introduce los datos correctamente.");
        }
    }

    @FXML
    private void añadirMaterial(ActionEvent event) {
        try{
        BDConector.registrarMaterialBD(this.txt_Producto.getText(), Integer.parseInt(this.txt_Cantidad.getText()));
        String listado = BDConector.consultarMaterial();
        tArea_Material.setText(listado);
        this.txt_Producto.setText("");
        this.txt_Cantidad.setText("");
        this.lb_Mensaje.setText("Añadido correctamente");
        }
        catch (Exception ex){
        this.lb_Mensaje.setText("Ha ocurrido un error. Introduce los datos correctamente.");
        }
        
    }

    @FXML
    private void borrarMaterial(ActionEvent event) {
        this.lb_Mensaje.setText("");
        boolean borrado = BDConector.eliminarMaterialBD(this.txt_ProductoEliminado.getText());
        if (!borrado){
        this.lb_Mensaje.setText("No se ha eliminado, comprueba los datos.");
        }
        else {
        this.lb_Mensaje.setText("Material eliminado correctamente");
        }
        String listado = BDConector.consultarMaterial();
        tArea_Material.setText(listado);
        this.txt_ProductoEliminado.setText("");
    }
    
}
