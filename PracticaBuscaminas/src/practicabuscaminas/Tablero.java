/*
Clase de tipo JPanel que representa el tablero del Buscaminas
 */
package practicabuscaminas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Bartomeu y Damián
 */
public class Tablero extends JPanel {
    //ATRIBUTOS CONSTANTES
    public static final int DIMENSION =9;   
    private static final int MAXIMO = 315;
    public static final int LADO = MAXIMO / DIMENSION;
    private static final Color BLANCO = Color.LIGHT_GRAY;
    private static final Color NEGRO = Color.DARK_GRAY;
    private static final int numBombas = 10;
    //ATRIBUTOS DE OBJETO
    //matriz de objetos Casilla que representa todas las casillas correspondientes
    //del tablero
    private Casilla casillas[][];
    //variable que cada vez que pulsamos una casilla tapada y no tiene bomba,
    //se incrementa dicha variable
    private int victoria = 0;
    //variable que cada vez que se da click derecho a una casilla tapada , 
    //dicha variable se incrementará hasta un máximo de 10.Si se clickea una 
    //casilla con una bandera , en dicha variable se decrementará hasta 0
    private int numBanderas = 0;
    
    //MÉTODO CONSTRUCTOR
    public Tablero(){
        //inicializamos la matriz
        casillas = new Casilla[DIMENSION][DIMENSION];
        int y = 0;
        for (int i = 0; i < DIMENSION; i++) {
            int x = 0;
            for (int j = 0; j < DIMENSION; j++) {
                Rectangle2D.Float r =
                        new Rectangle2D.Float(x, y, LADO, LADO);
                //inicializamos cada casilla
                casillas[i][j] = new Casilla(r,false);
                x += LADO;
            }
            y += LADO;
        }
        inicializarBombas();
        inicializarCasillas();
    }
    //Método que crea un nuevo tablero para comenzar una nueva partida
    public void inicializar(){
        casillas = new Casilla[DIMENSION][DIMENSION];
        int y = 0;
        for (int i = 0; i < DIMENSION; i++) {
            int x = 0;
            for (int j = 0; j < DIMENSION; j++) {
                Rectangle2D.Float r =
                        new Rectangle2D.Float(x, y, LADO, LADO);
                casillas[i][j] = new Casilla(r,false);
                x += LADO;
            }
            y += LADO;
        }
        inicializarBombas();
        inicializarCasillas();
        numBanderas=0;
        victoria=0;
    }
    //Método que pinta el tablero
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {   
                //pinta la casilla correspondiente
                casillas[i][j].paint(g);
                //pinta las líneas separadoras del tablero
                g2d.setColor(NEGRO);
                g2d.drawLine((j * LADO), (i * LADO), (j * LADO), (i * LADO) + LADO);
                g2d.drawLine((j * LADO), (i * LADO), (j * LADO) + LADO, (i * LADO));
            }
        }
    }
    //Método que devuelve la dimensión del tablero
    public Dimension getPreferredSize() {
        return new Dimension(MAXIMO-10, MAXIMO-10);
    }
    //Método que verifica si en la casilla correspondiente a la posición i,j
    //de la matriz , existe la coordenada x,y
    public boolean dentroCasilla(int x,int y,int i,int j){
        return casillas[i][j].getRec().contains(x,y);
    }
    //Método que verifica si la casilla correspondiente a la posición i,j
    //de la matriz está tapada
    public boolean isTapada(int i, int j){
        return !(casillas[i][j].getDestapada());
    }
    //Método que destapa una casilla correspondiente a la posición i,j de la matriz
    public void destapar(int i, int j){
        casillas[i][j].setDestapada(true);
    }
    //Método que inicializa las bombas en casillas aleatorias sin repetir ningún lugar
    private void inicializarBombas(){
        Random ran = new Random();
        for(int i = 0;i < numBombas;i++){
            boolean asig = false;
            while(!asig){
                //obtenemos dos números aleatorios de [0,DIMENSION) que representará
                //la fila y la columna de la casilla que tendrá una bomba
                int fila = ran.nextInt(DIMENSION);
                int columna = ran.nextInt(DIMENSION);
                //si esa casilla ya tiene bomba no entra en el if
                //(si el número de la foto es 9 significa que tiene bomba)
                if(casillas[fila][columna].getNumFoto() != 9){
                    casillas[fila][columna].setNumFoto(9);
                    asig = true;
                }
            }
        }   
    }
    //Método que pone en cada casilla del tablero su número correspondiente
    //a la proximidad de las bombas que tenga dicha casilla
    private void inicializarCasillas(){
        for(int i = 0;i<DIMENSION;i++){
            for(int j=0;j<DIMENSION;j++){
                //si la casilla i,j tiene bomba no se analiza
                if(casillas[i][j].getNumFoto() != 9){
                    int cont = 0;
                    //obtenemos las bombas próximas a la casilla i,j
                    cont = proximidadBomba(i,j);
                    //el número de bombas cercanas determina el número
                    //de foto de dicha casilla
                    casillas[i][j].setNumFoto(cont);
                }
            }
        }
    }
    //Método que pasada una posición de la matriz calcula cuantas bombas adyacentes
    //tiene la casilla correspondiente a la posición anteriormente mencionada
    private int proximidadBomba(int i,int j){
        int res = 0;
        //CASO QUE SEA UNA CASILLA QUE ES ESQUINA
        if((i==0 && j==0) || (i==DIMENSION-1 && j==DIMENSION-1) || 
            (i==0 && j==DIMENSION-1) || (i==DIMENSION-1 && j==0)){
            //ESQUINA SUPERIOR IZQUIERDA
            if(i==0 && j==0){
                res=casillas[i][j+1].hayBomba(res);
                res=casillas[i+1][j+1].hayBomba(res);
                res=casillas[i+1][j].hayBomba(res);
            }
            //ESQUINA INFERIOR DERECHA
            if(i==DIMENSION-1 && j==DIMENSION-1 ){
                res=casillas[i][j-1].hayBomba(res);
                res=casillas[i-1][j].hayBomba(res);
                res=casillas[i-1][j-1].hayBomba(res);   
            }
            //ESQUINA SUPERIOR DERECHA
            if(i==0 && j==DIMENSION-1){
                res=casillas[i][j-1].hayBomba(res);
                res=casillas[i+1][j-1].hayBomba(res);
                res=casillas[i+1][j].hayBomba(res);
            }
            //ESQUINA INFERIOR IZQUIERDA
            if(i==DIMENSION-1 && j==0){
                res=casillas[i-1][j].hayBomba(res);
                res=casillas[i-1][j+1].hayBomba(res);
                res=casillas[i][j+1].hayBomba(res);
            }
        }
        else{ //no es esquina
            //CASO LATERALES
            if((i==0)||(i==DIMENSION-1)||(j==0)||(j==DIMENSION-1)){
                //LATERAL NORTE
                if(i==0){
                    res=casillas[i][j-1].hayBomba(res);
                    res=casillas[i][j+1].hayBomba(res);
                    res=casillas[i+1][j].hayBomba(res);
                    res=casillas[i+1][j-1].hayBomba(res);
                    res=casillas[i+1][j+1].hayBomba(res);   
                }
                //LATERAL SUR
                if(i==DIMENSION-1){
                    res=casillas[i][j-1].hayBomba(res);
                    res=casillas[i][j+1].hayBomba(res);
                    res=casillas[i-1][j].hayBomba(res);
                    res=casillas[i-1][j-1].hayBomba(res);
                    res=casillas[i-1][j+1].hayBomba(res);
                }
                //LATERAL OESTE
                if(j==0){
                    res=casillas[i-1][j].hayBomba(res);
                    res=casillas[i+1][j].hayBomba(res);
                    res=casillas[i][j+1].hayBomba(res);
                    res=casillas[i+1][j+1].hayBomba(res);
                    res=casillas[i-1][j+1].hayBomba(res);
                }
                //LATERAL ESTE
                if(j==DIMENSION-1){
                    res=casillas[i-1][j].hayBomba(res);
                    res=casillas[i+1][j].hayBomba(res);
                    res=casillas[i+1][j-1].hayBomba(res);
                    res=casillas[i][j-1].hayBomba(res);
                    res=casillas[i-1][j-1].hayBomba(res);
                }
            }
            else{ //casillas intermedias
                res=casillas[i+1][j+1].hayBomba(res);
                res=casillas[i-1][j-1].hayBomba(res);
                res=casillas[i+1][j-1].hayBomba(res);
                res=casillas[i-1][j+1].hayBomba(res);
                res=casillas[i][j-1].hayBomba(res);
                res=casillas[i][j+1].hayBomba(res);
                res=casillas[i+1][j].hayBomba(res);
                res=casillas[i-1][j].hayBomba(res);
            }    
        }
        return res;
    }
    //Método que incrementa la variable victoria
    public void incrementarVictoria(){
        victoria++;
    }
    //Método que verifica si en la casilla correspondiente a la posición i,j
    //de la matriz de casillas , hay una bomba
    public boolean isBomba (int i,int j){
        return casillas[i][j].getNumFoto()==9;
    }
    //Método que verifica si en la casilla correspondiente a la posición i,j
    //de la matriz de casillas , hay una bandera
    public boolean isBandera(int i,int j){
        return casillas[i][j].getBandera();
    }
    //Método que destapa todas las casillas del tablero
    public void destaparTablero(){
        for(int i=0;i<DIMENSION;i++){
            for(int j=0;j<DIMENSION;j++){
                casillas[i][j].setDestapada(true);
            }
        }
        this.repaint();
    } 
    //Método que carga en la casilla correspondiente a la posición i,j de
    //la matriz de casilla, la foto de un botón con una bandera encima
    public void cargarBandera(int i,int j){
        casillas[i][j].setImagen("imagenes/bandera.png");
        casillas[i][j].setBandera(true);
        numBanderas++;
    }
    //Método que carga en la casilla correspondiente a la posición i,j de
    //la matriz de casillas, la foto de un botón
    public void cargarBoton(int i,int j){
        casillas[i][j].setImagen("imagenes/boton.png");
        casillas[i][j].setBandera(false);
        numBanderas--;
    }
    //Método que devuelve el número de foto de la casilla correspondiente a la 
    //posición i,j de la matriz de casillas
    public int getNumFoto(int i,int j){
        return casillas[i][j].getNumFoto();
    }
    //Método que extrae la información de la matriz de objetos CasillaPartida 
    //para incicializar la matriz de casillas de este clase
    public void extraerCasillas(CasillaPartida [][] partida){
        casillas = new Casilla[DIMENSION][DIMENSION];
        int y = 0;
        for (int i = 0; i < DIMENSION; i++) {
            int x = 0;
            for (int j = 0; j < DIMENSION; j++) {
                Rectangle2D.Float r =
                        new Rectangle2D.Float(x, y, LADO, LADO);
                casillas[i][j] = new Casilla(r,partida[i][j].getDestapada(),
                                 partida[i][j].getBandera(),partida[i][j].getNumFoto());
                x += LADO;
            }
            y += LADO;
        }
    }
    
    //Métodos GET
    public int getVictoria() {
        return victoria;
    }
    
    public Casilla[][] getCasillas() {
        return casillas;
    }
    
    public int getNumBanderas() {
        return numBanderas;
    }

    public static int getNumBombas() {
        return numBombas;
    }
    
    //Métodos SET
    public void setVictoria(int victoria) {
        this.victoria = victoria;
    }

    public void setNumBanderas(int numBanderas) {
        this.numBanderas = numBanderas;
    }
    
}
