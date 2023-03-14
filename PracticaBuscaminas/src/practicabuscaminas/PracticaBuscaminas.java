/*
Clase JFrame que representa el juego del Buscaminas

BOTÓN IZQUIERDO DEL RATÓN DETAPA CASILLA
BOTÓN DERECHO DEL RATÓN AÑADE/QUITA BANDERAS DE LAS CASILLAS

AUTORES : Damián Gebhard Galeote y Bartomeu Capó Salas
VÍDEO EXPLICATIVO : 
 */
package practicabuscaminas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author Bartomeu y Damián
 */

public class PracticaBuscaminas extends JFrame implements MouseListener {
    //Atributos
    //tablero del Buscaminas
    private Tablero tablero;
    //JPanel que dará información de la partida
    private JPanel panelInfo; 
    //2 etiquetas que darán información dentro del panelInfo
    JLabel et;
    JLabel et1;
    //atributos para llevar a cabo un menu en el JFrame
    JMenu menu;
    JMenuBar menuBar;
    JMenuItem nueva;
    JMenuItem salir;
    JMenuItem guardar;
    JMenuItem cargar;
    //para separar el tablero con el panelInfo
    JSplitPane sep1;
    
    //MÉTODO CONSTRUCTOR
    public PracticaBuscaminas(){
        inicio();   
    }
    
    //PROGRAMA PRINCIPAL
    public static void main(String[] args) {
        new PracticaBuscaminas();
    }

    //Método que inicializa todas las componentes del JFrame
    private void inicio(){
        //inicialización de los JPanels
        tablero = new Tablero();
        panelInfo = new JPanel();
        //Construcción e inicialización del menú del JFrame
        JMenu menu = new JMenu("OPCIONES");
        menuBar= new JMenuBar();
        nueva = new JMenuItem("Nueva Partida");
        nueva.addActionListener(eventoBoton);
        salir = new JMenuItem("Salir");
        salir.addActionListener(eventoBoton);
        guardar = new JMenuItem("Guardar Partida");
        guardar.addActionListener(eventoBoton);
        cargar = new JMenuItem("Cargar Partida");
        cargar.addActionListener(eventoBoton);
        menu.add(nueva);
        menu.add(guardar);
        menu.add(cargar);
        menu.add(salir);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        //inicialización de las etiquetas para su posterior introducción dentro
        // del panelInfo
        et = new JLabel("TIEMPO :");
        et1 = new JLabel("   NÚMERO BANDERAS :"+tablero.getNumBanderas());
        panelInfo.add(et);
        panelInfo.add(et1);
        //hacemos uso del separador
        sep1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,panelInfo,tablero);
        //introducimos el separador en el JFrame
        getContentPane().add(sep1);
        //añadimos un escuchador de ratón al tablero
        tablero.addMouseListener(this);
        //el tamaño del JFrame será dependiente del tablero
        setSize(tablero.getPreferredSize());
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        //inicializamos el contador del tiempo jugado del panelInfo
        tiempo(0,0);
    }
    @Override
    public void mouseClicked(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    //cuando se pulsa el ratón
    public void mousePressed(MouseEvent me) {
        int x = 0, y = 0, i=0, j = 0;
        //cuando se pulsa el botón DERECHO del ratón
        if (me.getButton() == MouseEvent.BUTTON3) {
            x = me.getX();
            y = me.getY();
            boolean trobat = false;
            for (i = 0; i < Tablero.DIMENSION && !trobat; i++) {
                for (j = 0; j < Tablero.DIMENSION && !trobat; j++) {
                    trobat = tablero.dentroCasilla(x, y, i, j);
                }
            }
            i--;
            j--;
            //si se ha encontrado la casilla pulsada y dicha casilla está tapada
            if(trobat && tablero.isTapada(i, j)){
                //si la casilla tiene bandera , se le quita la bandera y se actualiza
                //la información
                if(tablero.isBandera(i, j)){ 
                    tablero.cargarBoton(i, j);
                    et1.setText("   NÚMERO BANDERAS :"+tablero.getNumBanderas());
                }
                else{
                    //si la casilla no tiene bandera y aún no hay 10 casillas con 
                    //bandera , se le añade una bandera a la casilla y se actualiza
                    //la información
                    if(tablero.getNumBanderas() != 10){
                        tablero.cargarBandera(i, j);
                        et1.setText("   NÚMERO BANDERAS :"+tablero.getNumBanderas());
                    }
                }
            }
            //actualizamos el tablero
           tablero.repaint();
        }
    }

    @Override
    //cuando se deja de pulsar el botón
    public void mouseReleased(MouseEvent e) {
        int x = 0, y = 0, i=0, j = 0;
        //si se ha pulsado el botón IZQUIERDO del ratón
        if (e.getButton() == MouseEvent.BUTTON1) {
            x = e.getX();
            y = e.getY();
            boolean trobat = false;
            for (i = 0; i < Tablero.DIMENSION && !trobat; i++) {
                for (j = 0; j < Tablero.DIMENSION && !trobat; j++) {
                    trobat = tablero.dentroCasilla(x, y, i, j);
                }
            }
            i--;
            j--;
            // si se ha encontrado la casilla pulsada y la casilla no tiene
            //bandera y está tapada
            if(trobat && tablero.isTapada(i, j) && !tablero.isBandera(i, j)){
                //destapamos la casilla
                tablero.destapar(i, j);
                //si la casilla destapada es una bomba se destapa el tablero entero
                //y salta una información que indica que has perdido
                if(tablero.isBomba(i, j)){
                    tablero.destaparTablero();
                    JOptionPane.showMessageDialog(null,"            HAS PERDUT!");
                }
                else{
                    //incrementamos contador
                    tablero.incrementarVictoria();
                }
                //si el contador de victoria es igual a la dimension por la dimension
                //del tablero (9x9) menos las 10 bombas que hay , querrá decir que hemos
                //ganada. Por tanto destapamos el tablero entero y salta una información
                //que nos indica que hemos ganada
                if(tablero.getVictoria() == Tablero.DIMENSION*Tablero.DIMENSION - Tablero.getNumBombas()){
                    tablero.destaparTablero();
                    JOptionPane.showMessageDialog(null,"           HAS GUANYAT!");  
                }
            }
            //actualizamos el tablero
           tablero.repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //método que simula el tiempo
    private void tiempo(int minutos,int segundos){
        for(int min=minutos;min<60;min++){
            for(int seg=segundos;seg<60;seg++){
                et.setText("TIEMPO DE JUEGO : "+min+":"+seg);
                delaySegundo();
            }
        }
    }
    //método que hace un delay de un segundo
    private void delaySegundo(){
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}
    }
    //Oyente de los botones
    ActionListener eventoBoton = new ActionListener() { //oyente de evento 
                public void actionPerformed(ActionEvent evento) {  //Tratamiento del evento
                    switch (evento.getActionCommand()) {
                        case "Nueva Partida":
                                              //inicializamos el nuevo tablero
                                              tablero.inicializar();
                                              tablero.repaint();
                                              et1.setText("   BANDERAS :"+tablero.getNumBanderas());
                                               break;
                        case "Guardar Partida":
                                              
                                              TableroFicheroOut escribir;
                                              CasillaPartida [][] casillas = new CasillaPartida[Tablero.DIMENSION][Tablero.DIMENSION];
                                              //guardamos toda la información de las casillas que necesitamos
                                              //para guardar en el fichero
                                              for (int i = 0; i < Tablero.DIMENSION; i++) {
                                                  for (int j = 0; j < Tablero.DIMENSION; j++) {
                                                      casillas[i][j] = new CasillaPartida(!tablero.isTapada(i, j),
                                                                       tablero.isBandera(i, j),tablero.getNumFoto(i, j));
                                                  }
                                              }
                                              //guardamos toda la información necesaria del tablero para guardar dentro
                                              //de un fichero
                                              TableroPartida aux = new TableroPartida(casillas,tablero.getVictoria(),tablero.getNumBanderas()); 
                                              JFileChooser fichero = new JFileChooser();
                                              //abrimas directorio
                                              fichero.showSaveDialog(guardar);
                                              File nombre = fichero.getSelectedFile();
                                              //guardamos el path con el nombre que nos haya puesto el usuario
                                              String path = nombre.toString() + ".dat";
                                              //instanciamos el objeto TableroFicheroOut
                                              escribir = new TableroFicheroOut(path);
                                              //escribimos el objeto
                                              escribir.escritura(aux);
                                              //cerramos enlace lógico
                                              escribir.cierre();
                                              
                                               break;
                        case "Cargar Partida":
                                              TableroFicheroIn leer;
                                              TableroPartida auxs = new TableroPartida();
                                              JFileChooser fichero1 = new JFileChooser();
                                              //abrimos el directorio
                                              fichero1.showOpenDialog(cargar);
                                              File nombres = fichero1.getSelectedFile();
                                              //guardamos el path del fichero que representa la partida guardada
                                              String paths = nombres.toString();
                                              //instanciamos el objeto TableroFicheroIn
                                              leer = new TableroFicheroIn(paths);
                                              //leemos el objeto TableroPartida
                                              auxs = leer.lectura();
                                              //cerramos enlace lógico
                                              leer.cierre();
                                              //extraemos la información de las casillas de la partida leída
                                              //y las introducimos en la casillas de nuestro tablero,
                                              //lo mismo hacemos con el número de banderas y de victorias
                                              tablero.extraerCasillas(auxs.getCasillas());
                                              tablero.setNumBanderas(auxs.getNumBanderas());
                                              tablero.setVictoria(auxs.getVictoria());
                                              //actualizamos tablero y la información
                                              tablero.repaint();
                                              et1.setText("   NÚMERO BANDERAS :"+tablero.getNumBanderas());
                                              
                                               break;
                                               //salir de la aplicación
                        case "Salir":          System.exit(0);
                                               break;
                    }

            }
        };
}
