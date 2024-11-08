package p2;

import jade.core.Agent;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Agente extends Agent {
    private Entorno entorno;
    private int energia;
    private ArrayList<Movimiento> sensores;
    private double mejor_distancia;
    private int fila_incial = 6;
    private int columna_inicial = 6;
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
        
        Mapa mapa = new Mapa ("./maps/mapWithVerticalWall.txt");
        entorno = new Entorno (mapa, fila_incial, columna_inicial, 7, 2);
        
        entorno.mostrarEntorno();
        mejor_distancia = entorno.calcularDistancia(fila_incial, columna_inicial);
        addBehaviour(new Comportamiento(entorno, this));
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
}