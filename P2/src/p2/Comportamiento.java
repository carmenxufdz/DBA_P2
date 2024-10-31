package p2;

import jade.core.behaviours.Behaviour;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

public class Comportamiento extends Behaviour {
    private Entorno entorno;
    private Agente agente;
    
    public Comportamiento(Entorno e, Agente a) {
        entorno = e;
        agente = a;
    }

    @Override
    public void action() {
        ArrayList<Movimiento> movimientos = agente.getMovs();
        
        Pair<Movimiento,Double> optimo = valorarMovimiento(movimientos.get(0));
        Pair<Movimiento,Double> movimiento;
        //Mientras que el movimiento no implique llegar a la meta, puede ser mejor
        for (int i=1; i<movimientos.size() && optimo.getValue()!=0; i++) {
            movimiento = valorarMovimiento(movimientos.get(i));
            if (movimiento.getValue() >= 0 && movimiento.getValue()<optimo.getValue())
                optimo = movimiento;
        }
        agente.mover();
        entorno.moverAgente(optimo.getKey());
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Comportamiento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public boolean done() {
        if (entorno.agenteEnMeta())
            agente.doDelete();
        return entorno.agenteEnMeta();
    }
    
    // Devuelve un pair del movimiento y el coste
    private Pair<Movimiento,Double> valorarMovimiento (Movimiento direccion) {
        double valor = entorno.getEstado(direccion);
        return new Pair<> (direccion, valor);
    }
}
