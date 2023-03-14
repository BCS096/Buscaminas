/*
Clase que representa cada casilla del tablero del juego Buscaminas
 */
package practicabuscaminas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Bartomeu y Damián
 */
public class Casilla implements java.io.Serializable{
    //Atributos
    //objeto Rectangle2d.Float para guardar el tamaño y las coordenadas en el 
    //tablero de la Casilla
    private Rectangle2D.Float rec;
    //booleana para verificar si una casilla está destapada
    private Boolean destapada;
    //booleana que indica si la casilla tiene bandera
    private Boolean bandera=false;
    //objeto Image que representará visualmente la Casilla en el tablero
    private Image imagen;
    //variable que indica que foto se cargará en el atributo imagen con la ayuda
    //del método cogerImagen();
    private int numFoto = 0;
    
    //MÉTODOS CONSTRUCTORES
    public Casilla(Rectangle2D.Float rec,Boolean destapada){
        this.rec = rec;
        this.destapada = destapada;
        ImageIcon tmp=redimensionarImagen(new ImageIcon("imagenes/boton.png"));
        imagen = tmp.getImage(); 
    }
    public Casilla(Rectangle2D.Float rec,Boolean destapada,boolean bandera,int numFoto){
        this.rec = rec;
        this.destapada = destapada;
        ImageIcon tmp=redimensionarImagen(new ImageIcon("imagenes/boton.png"));
        imagen = tmp.getImage();
        this.destapada=destapada;
        this.bandera=bandera;
        this.numFoto=numFoto;
    }
    //Método que redimensiona al tamaño que debe tener la imagen pasada por parámetro
    public ImageIcon redimensionarImagen(ImageIcon imagen) {
        Image imgEscalada = imagen.getImage().getScaledInstance(Tablero.LADO,
            Tablero.LADO, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(imgEscalada); 
    }
    //Método que realiza la visualización gráfica de la Casilla
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        if(!destapada){
            if(bandera){
                setImagen("imagenes/bandera.png");   
            }
            else{
                setImagen("imagenes/boton.png");
            }
           g2d.drawImage(imagen,(int) this.rec.x , (int) this.rec.y , null);
        }
        else{
            cogerImagen(numFoto);
            g2d.drawImage(imagen,(int) this.rec.x , (int) this.rec.y , null);
        }
    }
    //Método que carga la imagen de la casilla según el número de proximidad
    //de bombas que tenga , pasado por parámetro , o si se pasa un 9 es una bomba
    private void cogerImagen(int tipo) {
        ImageIcon aux = null;
        switch(tipo){
            case 0: aux = redimensionarImagen(new ImageIcon("imagenes/0.png"));
                    break;
            case 1: aux = redimensionarImagen(new ImageIcon("imagenes/1.png"));
                    break;
            case 2: aux = redimensionarImagen(new ImageIcon("imagenes/2.png"));
                    break;
            case 3: aux = redimensionarImagen(new ImageIcon("imagenes/3.png"));
                    break;
            case 4: aux = redimensionarImagen(new ImageIcon("imagenes/4.png"));
                    break;
            case 5: aux = redimensionarImagen(new ImageIcon("imagenes/5.png"));
                    break;
            case 6: aux = redimensionarImagen(new ImageIcon("imagenes/6.png"));
                    break;
            case 7: aux = redimensionarImagen(new ImageIcon("imagenes/7.png"));
                    break;
            case 8: aux = redimensionarImagen(new ImageIcon("imagenes/8.png"));
                    break;
            case 9: aux = redimensionarImagen(new ImageIcon("imagenes/bomba.jpg"));
                    break;       
        }
        imagen = aux.getImage();
    } 
    //Método que en caso de que la casilla tenga una bomba , incrementa el número
    //pasado por parámetro y lo devuelve
    public int hayBomba(int cont){
        if(numFoto == 9){
            cont ++;
        }
        return cont;
    } 
    //Métodos GET
    public Rectangle2D.Float getRec() {
        return rec;
    }
    public Boolean getDestapada() {
        return destapada;
    }
    public Image getImagen() {
        return imagen;
    }
    public int getNumFoto() {
        return numFoto;
    }
    public Boolean getBandera() {
        return bandera;
    }
    
    //Métodos SET
    public void setRec(Rectangle2D.Float rec) {
        this.rec = rec;
    }
    public void setDestapada(Boolean destapada) {
        this.destapada = destapada;
    }
    public void setImagen(String imagen) {
        ImageIcon tmp=redimensionarImagen(new ImageIcon(imagen));
        this.imagen = tmp.getImage();
    }
    public void setNumFoto(int numFoto) {
        this.numFoto = numFoto;
    }
    public void setBandera(Boolean bandera) {
        this.bandera = bandera;
    }
    
}

