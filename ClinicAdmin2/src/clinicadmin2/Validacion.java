
package clinicadmin2;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author María
 */
public class Validacion {
    
    public Validacion() {
    }
   
    //Método para cifrar las contraseñas de los nuevos usuarios
    public String cifrarSha256(String comando){
        StringBuffer ch = new StringBuffer();
      try{
          //Utilizamos la clase MessageDigest diciendole el algoritmo de cifrado que vamos a utilizar
          MessageDigest md = MessageDigest.getInstance("sha-256");
          //Ciframos la contraseña y cogemos la cadena de bytes
          byte[] cifrado = md.digest(comando.getBytes());
          //Recorremos la cadena de bytes para transformarla a String
          for (int i=0; i<cifrado.length; i++){
              ch.append(Integer.toHexString(0xFF + cifrado[i]));
          }  
      } catch (Exception ex){
          System.out.println("Error: " + ex.getMessage());
      }
      return ch.toString();
    }
    
    
    //Método para buscar un usuario en la BD
     public int buscar (String user, String password){
         int resultado = -1;
        try{
            if ("admin".equals(user)){      // Así podemos entrar a la aplicación por primera vez con el usuario admin y contraseña 1234 sin cifrar
            boolean bol = validarAdmin(user, password);
            if (bol){
            resultado = 1;
            }
            } 
            else {                          // Para el resto de usuarios creados, se usarán las contraseñas cifradas para mayor seguridad
            String sha256 = cifrarSha256(password);
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de consulta a la tabla user de la BD
            String sql = "Select * from usuarios where user =? and password =?";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, user);
            sentencia.setString(2, sha256);
            ResultSet rs = sentencia.executeQuery();
            //Recuperamos el rol de ese usuario en la variable resultado para (des)habilitar los botones
            if (rs.next()){
                resultado = rs.getInt(3);
             }
            
            sentencia.close();
            conexion.close();
          }
           
        } catch (Exception ex){
            System.out.println("Error Buscar: " + ex.getMessage());
            resultado = -1;
        }
        return resultado;
    }
     
     
     
      //Método para validar el usuario y contraseña del Login sin cifrar (solo usado para el usuario admin inicial)
    public boolean validarAdmin (String user, String password){
        boolean resultado = false;
        try{
            //Cargamos el driver de MySql
            Class.forName("com.mysql.jdbc.Driver");
            //Creamos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BDProyecto", "administradorBD", "admin");
            //Sentencia SQL de consulta a la tabla user de la BD
            String sql = "Select * from usuarios where user =? and password =?";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, user);
            sentencia.setString(2, password);
            ResultSet rs = sentencia.executeQuery();
            
            //Si se recupera algún usuario, resultado = true
            if (rs.next()){
                resultado = true;
            }
            
            sentencia.close();
            conexion.close();
        } catch (Exception ex){
            System.out.println("Error Buscar: " + ex.getMessage());
            resultado = false;
        }
        return resultado;
    }
    
}
