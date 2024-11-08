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
        
        Mapa mapa = new Mapa ("./maps/mapWithComplexObstacle3.txt");
        entorno = new Entorno (mapa, 6, 6, 2, 7);
        
        entorno.mostrarEntorno();
        
        addBehaviour(new Comportamiento(entorno, this));
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