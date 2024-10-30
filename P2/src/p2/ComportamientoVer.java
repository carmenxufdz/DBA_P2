package p2;

import jade.core.behaviours.Behaviour;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

public class ComportamientoVer extends Behaviour {
    private Entorno entorno;
    private Agente agente;
    
    public ComportamientoVer(Entorno e, Agente a) {
        entorno = e;
        agente = a;
    }

    @Override
    public void action() {
        if (!agente.getMover()) {
            agente.setVer(true);
            ArrayList<Pair<Movimiento,Double>> movimientos = new ArrayList<>();
        
            if (!agregarMovimiento(movimientos, Movimiento.NORTE))
                if (!agregarMovimiento(movimientos, Movimiento.NORESTE))
                    if (!agregarMovimiento(movimientos, Movimiento.ESTE))
                        if (!agregarMovimiento(movimientos, Movimiento.SURESTE))
                            if (!agregarMovimiento(movimientos, Movimiento.SUR))
                                if (!agregarMovimiento(movimientos, Movimiento.SUROESTE))
                                    if (!agregarMovimiento(movimientos, Movimiento.OESTE))
                                            agregarMovimiento(movimientos, Movimiento.NOROESTE);

            agente.setMovs(movimientos);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ComportamientoVer.class.getName()).log(Level.SEVERE, null, ex);
            }
            agente.setVer(false);
        }
        
    }

    @Override
    public boolean done() {
        if (entorno.agenteEnMeta())
            agente.doDelete();
        return entorno.agenteEnMeta();
    }
    
    // Devuelve true si el valor es 0 (meta)
    private boolean agregarMovimiento (ArrayList<Pair<Movimiento,Double>> movs, Movimiento direccion) {
        boolean meta = false;
        double valor = entorno.getEstado(direccion);
        if (valor == 0) {
            movs.clear();
            meta = true;
        }
        if (valor > -1)
            movs.add(new Pair<> (direccion, valor));
        return meta;
    }
}
