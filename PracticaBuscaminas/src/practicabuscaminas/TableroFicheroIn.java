/*
 Clase que utiliza ObjectOInputStream para el guardado de una partida en un 
 fichero
 */
package practicabuscaminas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Bartomeu y Damián
 */
public class TableroFicheroIn {
    private ObjectInputStream fichero =null;
    //MÉTODOS CONSTRUCTORES
    public TableroFicheroIn(String nom)  {       
        try {
           fichero = new ObjectInputStream(new FileInputStream(nom));
        }catch (FileNotFoundException error) {
            System.err.println("Ja s'ha actualitzat la partida actual o no s'ha jugat"
                    + "cap partida");
           System.err.println("ERROR: "+error.toString());
        }
        catch (IOException error) {
           System.out.println("ERROR: "+error.toString());
        }       
    }
    //MÉTODOS FUNCIONALES
    //método que lee un objeto TableroPartida del fichero
    public TableroPartida lectura() {
        TableroPartida objeto=new TableroPartida();
        try {
            objeto= (TableroPartida) fichero.readObject();
        }catch (ClassNotFoundException error) {
            System.out.println("ERROR: "+error.toString());
        }
        catch (IOException error) {
            System.out.println("ERROR: "+error.toString());
        }
        finally {
            return objeto;
        }     
    }
    //método que cierra el enlace lógico con el fichero
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
