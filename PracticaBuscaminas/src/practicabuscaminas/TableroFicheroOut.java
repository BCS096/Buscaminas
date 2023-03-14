/*
 Clase que utiliza ObjectOutputStream para el guardado de una partida en un 
 fichero
 */
package practicabuscaminas;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author Bartomeu y Damián
 */
public class TableroFicheroOut {
    //ATRIBUTOS 
    private ObjectOutputStream fichero=null;
    
    //MÉTODOS CONSTRUCTORES
    public TableroFicheroOut(String nom) { 
        try {
           fichero = new ObjectOutputStream(new FileOutputStream(nom));
        }
        catch (IOException error) {
           System.out.println("ERROR: "+error.toString());
        } 
    }         
    //MÉTODOS FUNCIONALES
    //método que escribe en el fichero el objeto TableroPartida
    public void escritura(TableroPartida objeto)  {
        try {
            fichero.writeObject(objeto);
        }
        catch (IOException error) {
            System.out.println("ERROR: "+error.toString());
        }       
    }
    //método que cierra enlace lógico con el fichero
    public void cierre() {      
        try {
            if (fichero!=null) {
                fichero.close();                
            }
        }catch (IOException error) {
           System.out.println("ERROR: "+error.toString()); 
        } 
    }
}
