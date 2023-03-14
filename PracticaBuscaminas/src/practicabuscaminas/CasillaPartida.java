/*
    Clase utilizada para el guardado de los atributos necesarios de Casilla
    para poder guardar un tablero en un fichero 
 */
package practicabuscaminas;

/**
 *
 * @author Bartomeu y Damián
 */
public class CasillaPartida implements java.io.Serializable{
    //Atributos
    private Boolean destapada;
    private Boolean bandera;
    private int numFoto;
    
    //Métodos constructores;
    public CasillaPartida(){}
    
    public CasillaPartida(boolean destapada,boolean bandera, int numFoto){
        this.bandera=bandera;
        this.destapada=destapada;
        this.numFoto=numFoto;
    }

    //Métodos GET
    public Boolean getDestapada() {
        return destapada;
    }

    public Boolean getBandera() {
        return bandera;
    }

    public int getNumFoto() {
        return numFoto;
    }
    
    
}
