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
    private ArrayList<Pair<Movimiento, Double>> movimientos;
    
    private boolean viendo, moviendo;
    
    @Override
    protected void setup() {
        energia = 0;
        movimientos = new ArrayList<>();
        
        viendo = true;
        moviendo = false;
        
        Mapa mapa = new Mapa ("./maps/mapWithoutObstacle.txt");
        entorno = new Entorno (mapa, 0, 0, 5, 5);
        
        entorno.mostrarEntorno();
        
        addBehaviour(new ComportamientoVer(entorno, this));
        addBehaviour(new ComportamientoMover(entorno, this));
    }
    
    @Override
    public void takeDown() {
        System.out.println("Terminating agent...");
    }
    
    public void setMovs (ArrayList<Pair<Movimiento, Double>> movs) {
        movimientos = movs;
    }
    
    public ArrayList<Pair<Movimiento, Double>> getMovs() {
        return movimientos;
    }
    
    public void mover() {
        energia++;
    }
    
    public void setVer (boolean ver) {
        viendo = ver;
    }
    
    public void setMover (boolean mover) {
        moviendo = mover;
    }
    
    public boolean getVer() {
        return viendo;
    }
    
    public boolean getMover() {
        return moviendo;
    }
}
