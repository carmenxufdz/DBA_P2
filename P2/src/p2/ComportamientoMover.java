package p2;

import jade.core.behaviours.Behaviour;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

public class ComportamientoMover extends Behaviour {
    private Entorno entorno;
    private Agente agente;
    
    public ComportamientoMover(Entorno e, Agente a) {
        entorno = e;
        agente = a;
    }

    @Override
    public void action() {
        if (!agente.getVer()) {
            agente.setMover(true);
            ArrayList<Pair<Movimiento,Double>> movimientos = agente.getMovs();
            Pair<Movimiento,Double> optimo = movimientos.get(0);
            for (Pair<Movimiento,Double> mov : movimientos)
                if (mov.getValue()<optimo.getValue())
                    optimo = mov;
            agente.mover();
            entorno.moverAgente(optimo.getKey());
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ComportamientoMover.class.getName()).log(Level.SEVERE, null, ex);
            }
            agente.setMover(false);
        }
    }

    @Override
    public boolean done() {
        return entorno.agenteEnMeta();
    }
}
