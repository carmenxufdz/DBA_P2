package p2;

import jade.core.Agent;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Agente extends Agent implements AgenteInterface{
    private int energia;
    private ArrayList<Movimiento> sensores;
  
    private int fila_incial = 6;
    private int columna_inicial = 6;
    private int fila_final = 0;
    private int columna_final = 5;
    private double distancia_actual;

    private Entorno entorno;
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
        
        mapa = new Mapa ("./maps/mapWithComplexObstacle2.txt");
        
        entorno = new Entorno (new Mapa ("./maps/mapWithComplexObstacle2.txt"), fila_incial, columna_inicial, fila_final, columna_final);

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

    // Implementación de la interfaz para iniciar el comportamiento
    @Override
    public void iniciarComportamiento() {

        entorno.mostrarEntorno();
        
        comportamiento = new Comportamiento(entorno, this);
        addBehaviour(comportamiento);
    }

    @Override
    public void moverAgente() {
        comportamiento.moverAgente();
    }

    @Override
    public String Energia() {
        return "Energia:" + this.energia;
    }

    @Override
    public void setEntorno(Entorno entorno) {
        this.entorno = entorno;
    }

    
}