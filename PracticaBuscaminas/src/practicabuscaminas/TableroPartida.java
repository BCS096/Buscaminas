/*
    Clase utilizada para el guardado de los atributos necesarios de Tablero
    para poder guardar un tablero en un fichero 
 */
package practicabuscaminas;

/**
 *
 * @author Bartomeu y Damián
 */
public class TableroPartida implements java.io.Serializable {
    //Atributos
    private CasillaPartida casillas[][];
    private int victoria;
    private int numBanderas;
    
    //MÉTODOS CONSTRUCTORES
    public TableroPartida(){}
    
    public TableroPartida( CasillaPartida[][] casillas , int victoria , int numBanderas){
        this.casillas = casillas;
        this.victoria = victoria;
        this.numBanderas = numBanderas;
    }
    //MÉTODOS GET
    public CasillaPartida[][] getCasillas() {
        return casillas;
    }
    public int getVictoria() {
        return victoria;
    }
    public int getNumBanderas() {
        return numBanderas;
    }
}
