package p2;

import jade.core.Agent;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Agente extends Agent implements AgenteInterface{
    private Mapa mapa;
    private Entorno entorno;
    private int energia;
    private ArrayList<Movimiento> sensores;
<<<<<<< Updated upstream
    private int fila_incial = 6;
    private int columna_inicial = 6;
    private int fila_final = 0;
    private int columna_final = 5;
    private double distancia_actual;
=======
    private double distancia_actual;
    private int fila_incial = 3;
    private int columna_inicial = 0;
    private int fila_final = 5;
    private int columna_final = 5;
>>>>>>> Stashed changes

    
    private Comportamiento comportamiento;
    
    public Agente(){
        registerO2AInterface(AgenteInterface.class, this);
    }
    
    @Override
    protected void setup() {
        energia = 0;
        sensores = new ArrayList<>();
        sensores.add(Movimiento.NORTE);
        sensores.add(Movimiento.NORESTE);
        sensores.add(Movimiento.ESTE);
        sensores.add(Movimiento.SURESTE);
        sensores.add(Movimiento.SUR);
        sensores.add(Movimiento.SUROESTE);
        sensores.add(Movimiento.OESTE);
        sensores.add(Movimiento.NOROESTE);
<<<<<<< Updated upstream
        
        mapa = new Mapa ("./maps/mapWithComplexObstacle2.txt");
        
        
=======
        entorno = new Entorno (new Mapa ("./maps/mapWithComplexObstacle2.txt"), fila_incial, columna_inicial, fila_final, columna_final);
>>>>>>> Stashed changes
        setEnabledO2ACommunication(true, 10);
    }
    
    public void setDistanciaActual(){
        distancia_actual = entorno.getDistanciaActual();
    }
    
    public double getDistanciaActual(){
        return distancia_actual;
    }
   
    
    @Override
    public void takeDown() {
        System.out.println("Terminating agent...");
    }
    
    public ArrayList<Movimiento> getMovs() {
        return sensores;
    }
    
    public void mover() {
        energia++;
        System.out.println("Energia consumida: " + energia);
    }
    
    @Override
    public Entorno getEntorno(){
        return entorno;
    }
    

    @Override
    public void cargarMapa(String path) {
        String new_path = "./maps/" + path;
        mapa = new Mapa(new_path);
    }

 
    // Implementación de la interfaz para iniciar el comportamiento
    @Override
    public void iniciarComportamiento() {
<<<<<<< Updated upstream
        
        entorno = new Entorno (mapa, fila_incial, columna_inicial, fila_final, columna_final);
=======
        energia = 0;
>>>>>>> Stashed changes
        entorno.mostrarEntorno();
        
        comportamiento = new Comportamiento(entorno, this);
        addBehaviour(comportamiento);
    }

    @Override
    public void moverAgente() {
        comportamiento.moverAgente();
    }

    @Override
    public int Energia() {
        return this.energia;
    }

    @Override
    public void setEntorno(Entorno entorno) {
        this.entorno = entorno;
    }

    
}