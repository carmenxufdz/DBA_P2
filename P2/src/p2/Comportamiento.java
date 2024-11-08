package p2;

import jade.core.behaviours.Behaviour;
import java.util.ArrayList;
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

        Pair<Movimiento, Double> movimiento;
        Pair<Movimiento, Double> optimo = valorarMovimiento(movimientos.get(0));
        for (int i=1; i<movimientos.size() && optimo.getValue()!=0 ; i++) {
            movimiento = valorarMovimiento(movimientos.get(i));
            if (optimo.getValue()<0 || (movimiento.getValue()>=0 && movimiento.getValue()<optimo.getValue()))
                optimo = movimiento;
        }

        agente.mover();
        entorno.moverAgente(optimo.getKey());
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