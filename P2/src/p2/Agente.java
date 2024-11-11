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
    private double mejor_distancia;
    private int fila_incial = 6;
    private int columna_inicial = 6;
    private int fila_final = 7;
    private int columna_final = 2;
    
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
        
        mapa = new Mapa ("./maps/mapWithVerticalWall.txt");
        
        
        setEnabledO2ACommunication(true, 10);
        
    }
    
    public void setMejorDistancia(double mejor){
        mejor_distancia = mejor;
    }
    
    public double getMejorDistancia(){
        return mejor_distancia;
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
    }
    
    @Override
    public Entorno getEntorno(){
        return entorno;
    }
    
    public Mapa getMapa(){
        return mapa;
    }
    

    @Override
    public void cargarMapa(String path) {
        String new_path = "./maps/" + path;
        mapa = new Mapa(new_path);
    }

    @Override
    public String moverAgente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    // Implementación de la interfaz para iniciar el comportamiento
    @Override
    public void iniciarComportamiento() {
        
        entorno = new Entorno (mapa, fila_incial, columna_inicial, fila_final, columna_final);
        entorno.mostrarEntorno();
        
        mejor_distancia = entorno.calcularDistancia(fila_incial, columna_inicial);
        comportamiento = new Comportamiento(entorno, this);
        addBehaviour(comportamiento);
    }

    @Override
    public void activarMovimiento() {
        comportamiento.activarMovimiento();
    }
    
}