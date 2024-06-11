/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicadmin2;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author María
 */
public class BDConector {

    public BDConector() {
    }
    
    
    
// -- FUNCIONALIDAD REGISTRAR NUEVO PACIENTE --
    
    //Método para registrar un nuevo paciente en la BD
    public boolean registrarPacienteBD(String nombre, String apellidos, int edad, String telefono, String email){
        boolean registro = false;
        try{  
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de insercion en la tabla pacientes de la BD
            String sql = "INSERT INTO pacientes (nombre, apellidos, edad, telefono, email) VALUES (?,?,?,?,?)";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, apellidos);
            sentencia.setInt(3, edad);
            sentencia.setString(4, telefono);
            sentencia.setString(5, email);
            sentencia.executeUpdate();
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
            
            registro = true;
        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            registro = false;
        }       
    return registro;
    }
    
 
    
 // -- FUNCIONALIDADES PACIENTES --
    
    //Método para consultar todos los pacientes de la BD
    public String consultarPacientes(){
        String linea = "";
        try{
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de consulta a la tabla pacientes de la BD
            String sql = "SELECT * FROM pacientes";
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(sql);
            //Mientras haya registros, recuperamos los datos
            while (resultado.next()){
            linea = linea + String.format("%-10d %-20s %-23s %-15d %-20s %-30s%n", resultado.getInt(1), resultado.getString(2), resultado.getString(3), resultado.getInt(4), resultado.getString(5), resultado.getString(6));
            }
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
          }    
        return linea;
    }
    
    
    //Método para buscar un paciente de la BD
    public String buscarPacienteBD(String nombre, String apellidos){
        String linea = "";
        try{
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de consulta a la tabla pacientes de la BD
            String sql = "SELECT * FROM pacientes WHERE nombre = '"+nombre+"' AND apellidos = '"+apellidos+"'";
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(sql);
            //Si se encuentra el registro, recuperamos los datos
            while (resultado.next()){
            linea = linea + String.format("%-10d %-20s %-23s %-15d %-20s %-30s%n", resultado.getInt(1), resultado.getString(2), resultado.getString(3), resultado.getInt(4), resultado.getString(5), resultado.getString(6));
            }
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
          }    
        return linea;
    }
    

    //Método para eliminar un paciente de la BD
    public boolean eliminarPaciente(String nombre, String apellidos) throws SQLException{
        boolean borrado = false;
        try {
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Primero comprobamos si existe el paciente en la BD
            String sql1 = "SELECT * FROM pacientes WHERE nombre = '"+nombre+"' AND apellidos = '"+apellidos+"'";
            Statement sentencia1 = conexion.createStatement();
            ResultSet resultado1 = sentencia1.executeQuery(sql1); 
            //Si el paciente no existe ponemos borrado a false
            if (!resultado1.next()){
            borrado = false;
            }
            //Si el paciente si existe...
            else {
            //Primero eliminamos las citas asociadas al paciente para poder borrar luego el paciente
            String sql2 = "DELETE FROM citas WHERE id_paciente = (SELECT id_paciente FROM pacientes WHERE nombre = '"+nombre+"' AND apellidos = '"+apellidos+"')";
            Statement sentencia2 = conexion.createStatement();
            sentencia2.executeUpdate(sql2);            
            //Después eliminamos al paciente que ya no tiene citas asociadas
            String sql3 = "DELETE FROM pacientes WHERE nombre = '"+nombre+"' AND apellidos = '"+apellidos+"'";
            Statement sentencia3 = conexion.createStatement();
            sentencia3.executeUpdate(sql3);
            borrado = true;
          }
         } catch (ClassNotFoundException ex) {
            Logger.getLogger(BDConector.class.getName()).log(Level.SEVERE, null, ex);
            borrado = false;
        }
        return borrado;
    }
    

// -- FUNCIONALIDADES SERVICIOS --
    
    //Método para consultar todos los servicios de la BD
    public String consultarServicios(){
        String linea = "";
        try{
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de consulta a la tabla servicios de la BD
            String sql = "SELECT * FROM servicios";
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(sql);
            //Mientras haya registros, recuperamos los datos
            while (resultado.next()){
            linea = linea + String.format("%-25s %-15d %-100s%n", resultado.getString(1), resultado.getInt(2), resultado.getString(3));
            }
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
          }    
        return linea;
    }
    
    
     //Método para registrar nuevo servicio en la BD
    public boolean registrarServicioBD(String nombre, int precio, String descripcion){
        boolean registro = false;
        try{  
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de insercion a la tabla servicios de la BD
            String sql = "INSERT INTO servicios (nombre, precio, descripcion) VALUES (?,?,?)";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nombre);
            sentencia.setInt(2, precio);
            sentencia.setString(3, descripcion);
            sentencia.executeUpdate();
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
            
            registro = true;
        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            registro = false;
        }       
    return registro;
    }
    
    
    //Método para eliminar un servicio de la BD
    public boolean eliminarServicioBD(String nombre){
        boolean borrado = false;
        try{  
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de eliminación en la tabla servicios de la BD
            String sql = "DELETE FROM servicios WHERE nombre = '" + nombre + "'";
            Statement sentencia = conexion.createStatement();
            int resultado = sentencia.executeUpdate(sql);
            if(resultado != 0){
            borrado = true;
            }
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            borrado = false;
        }
        return borrado;
    }
    
 
// -- FUNCIONALIDADES SERVICIOS -- 
    
    //Método para consultar el material de la base de datos
    public String consultarMaterial(){
        String linea = "";
        try{
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de consulta a la tabla material de la BD
            String sql = "SELECT * FROM material";
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(sql);
            //Mientras aya registros, recuperamos los datos
            while (resultado.next()){
            linea = linea + String.format("%-35s %-15d%n", resultado.getString(1), resultado.getInt(2));
            }
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
          }    
        return linea;
    }
    
    
    //Método para actualizar material de la BD
    public boolean actualizarMaterialBD(String nombre, int cantidad){
        boolean actualizacion = false;
        try{  
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de actualización de la tabla material de la BD
            String sql = "UPDATE material SET stock = " + cantidad + " WHERE nombre = '" + nombre + "';";
            Statement sentencia = conexion.createStatement();
            int resultado = sentencia.executeUpdate(sql);
            if (resultado != 0){
            actualizacion = true;
            }
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            actualizacion = false;
        } 
        return actualizacion;
    }

    
    //Método para registrar nuevo material en la BD
    public void registrarMaterialBD(String nombre, int stock){
        
        try{  
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de insercion en la tabla material de la BD
            String sql = "INSERT INTO material (nombre, stock) VALUES (?,?)";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nombre);
            sentencia.setInt(2, stock);
            sentencia.executeUpdate();
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }  
    }
    
    
    //Método para eliminar un material de la BD
    public boolean eliminarMaterialBD(String nombre){
        boolean borrado = false;
        try{  
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de eliminación en la tabla material de la BD
            String sql = "DELETE FROM material WHERE nombre = '" + nombre + "'";
            Statement sentencia = conexion.createStatement();
            int resultado = sentencia.executeUpdate(sql);
            if (resultado !=0){
            borrado = true;
            }
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            borrado = false;
        }       
        return borrado;
    }
    
 
// -- FUNCIONALIDADES CITAS --
    
    //Método para consultar todas las citas de la BD
    public String consultarCitas(){
        String linea = "";
        try{
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de consulta a la unión de las tablas citas y pacientes, ordenadando el resultado por fecha y hora
            String sql = "SELECT c.*, p.nombre, p.apellidos FROM citas c JOIN pacientes p ON c.id_paciente = p.id_paciente ORDER BY c.fecha, LENGTH(hora), CAST(SUBSTRING_INDEX(hora, ':', 1) AS UNSIGNED), CAST(SUBSTRING_INDEX(hora, ':', -1) AS UNSIGNED);";
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(sql);
            //Mientras haya registros, recuperamos los datos
            while (resultado.next()){
            Date fecha = resultado.getDate(2);
            String fechaFormateada = (fecha != null) ? String.format("%tF", fecha) : "N/A";
            linea = linea + String.format("%-12d %-15s %-12s %-10d %-18s %-20s%n", resultado.getInt(1), fechaFormateada, resultado.getString(3), resultado.getInt(4), resultado.getString(5), resultado.getString(6));
            }
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
          }    
        return linea;
    }
    
    
     //Método para registrar nueva cita en la BD
    public boolean registrarCitaBD(Date fecha, String hora, int id_paciente){
        boolean registro = false;
        try{  
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de insercion en la tabla citas de la BD
            String sql = "INSERT INTO citas (fecha, hora, id_paciente) VALUES (?,?,?)";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setDate(1, fecha);
            sentencia.setString(2, hora);
            sentencia.setInt(3, id_paciente);
            sentencia.executeUpdate();
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
            
            registro = true;
        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            registro = false;
        }       
    return registro;
    }
    
    
    //Método para eliminar una cita de la BD
    public boolean eliminarCitaBD(String nombre, String apellidos, Date fecha){
        boolean borrado = false;
        try{
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de eliminación en la tabla citas de la BD
            String sql = "DELETE FROM citas WHERE fecha = ? AND id_paciente = (SELECT id_paciente FROM pacientes WHERE nombre = ? AND apellidos = ?);";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setDate(1, fecha);
            sentencia.setString(2, nombre);
            sentencia.setString(3, apellidos);
            int resultado = sentencia.executeUpdate();
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();   
          
            if (resultado !=0){
            borrado = true;}
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            borrado = false;
          
        }  
        return borrado;
   }
    
    //Método para obtener el ID de un paciente
     public int obtenerIDpaciente(String nombre, String apellidos){
        int id_paciente = 0;
        try{  
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de consulta en la tabla pacientes de la BD
            String sql = "SELECT id_paciente FROM pacientes WHERE nombre = '" + nombre + "' AND apellidos = '" + apellidos + "';";
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(sql);
            while (resultado.next()){
            id_paciente = resultado.getInt(1);
            
            }
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
                   
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            
        }       
    return id_paciente;
    }

    
     //Método para buscar una cita por fecha de la BD
    public String buscarCitaFecha(Date fecha){
        String linea = "";
        try{
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de consulta a la tabla citas de la BD
            String sql = "SELECT c.*, p.nombre, p.apellidos FROM citas c JOIN pacientes p ON c.id_paciente = p.id_paciente WHERE c.fecha = ? ORDER BY c.fecha, LENGTH(hora), CAST(SUBSTRING_INDEX(hora, ':', 1) AS UNSIGNED), CAST(SUBSTRING_INDEX(hora, ':', -1) AS UNSIGNED);";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setDate(1, fecha);
            ResultSet resultado = sentencia.executeQuery();
            //Mientras haya registros, recuperamos los datos
            while (resultado.next()){
            String fechaFormateada = (fecha != null) ? String.format("%tF", fecha) : "N/A";
            linea = linea + String.format("%-12d %-15s %-12s %-10d %-18s %-20s%n", resultado.getInt(1), fechaFormateada, resultado.getString(3), resultado.getInt(4), resultado.getString(5), resultado.getString(6));
            }
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
          }    
        return linea;
    }
     
     //Método para buscar citas por apellido de la BD
    public String buscarCitaApellidos(String apellidos){
        String linea = "";
        try{
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de consulta a la tabla citas de la BD
            String sql = "SELECT c.*, p.nombre, p.apellidos FROM citas c JOIN pacientes p ON c.id_paciente = p.id_paciente WHERE p.apellidos = ? ORDER BY c.fecha, LENGTH(hora), CAST(SUBSTRING_INDEX(hora, ':', 1) AS UNSIGNED), CAST(SUBSTRING_INDEX(hora, ':', -1) AS UNSIGNED);";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, apellidos);
            ResultSet resultado = sentencia.executeQuery();
            //Mientras haya registros, recuperamos los datos
            while (resultado.next()){
    //        String fechaFormateada = (fecha != null) ? String.format("%tF", fecha) : "N/A";
            linea = linea + String.format("%-12d %-15s %-12s %-10d %-18s %-20s%n", resultado.getInt(1), resultado.getDate(2), resultado.getString(3), resultado.getInt(4), resultado.getString(5), resultado.getString(6));
            }
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
          }    
        return linea;
    }
    
    
 // -- FUNCIONALIDADES REGISTRAR NUEVO EMPLEADO --
    
    //Método para registrar un nuevo usuario en la BD
    public void registrarUsuarioBD(String user, String password, int idRol){
         try{  
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de insercion en la tabla usuarios de la BD
            String sql = "INSERT INTO usuarios (user, password, idRol) VALUES (?,?,?)";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, user);
            sentencia.setString(2, password);
            sentencia.setInt(3, idRol);
            sentencia.executeUpdate();
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
     //Método para eliminar un usuario de la BD
    public boolean eliminarUsuarioBD(String nombre){
        boolean borrado = false;
        try{  
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de eliminación usuario de la BD
            String sql = "DELETE FROM usuarios WHERE user = '" + nombre + "'";
            Statement sentencia = conexion.createStatement();
            int resultado = sentencia.executeUpdate(sql);
            if (resultado != 0){
            borrado = true;
            }
            //Cerramos sentencia y conexión          
            sentencia.close();
            conexion.close();
        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            borrado = false;
        }       
        return borrado;
    }
     
}
                
            

