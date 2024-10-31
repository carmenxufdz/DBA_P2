package p2;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author Usuario
 */
public class Agente extends Agent {
    private Entorno entorno;
    private int energia;
    private ArrayList<Movimiento> movimientos;
    
    @Override
    protected void setup() {
        energia = 0;
        movimientos = new ArrayList<>();
        movimientos.add(Movimiento.NORTE);
        movimientos.add(Movimiento.NORESTE);
        movimientos.add(Movimiento.ESTE);
        movimientos.add(Movimiento.SURESTE);
        movimientos.add(Movimiento.SUR);
        movimientos.add(Movimiento.SUROESTE);
        movimientos.add(Movimiento.OESTE);
        movimientos.add(Movimiento.NOROESTE);
        
        Mapa mapa = new Mapa ("./maps/mapWithComplexObstacle3.txt");
        entorno = new Entorno (mapa, 0, 0, 9, 9);
        
        entorno.mostrarEntorno();
        
        addBehaviour(new Comportamiento(entorno, this));
    }
    
    @Override
    public void takeDown() {
        System.out.println("Terminating agent...");
    }
    
    public ArrayList<Movimiento> getMovs() {
        return movimientos;
    }
    
    public void mover() {
        energia++;
    }
}