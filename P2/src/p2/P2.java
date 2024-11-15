package p2;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

// java -cp jade.jar jade.Boot -name dba_server -gui

public class P2 {
    
    private static AgentController agenteController;

    public static void main(String[] args) throws StaleProxyException {
        jade.core.Runtime rt = jade.core.Runtime.instance();
        
        Profile p = new ProfileImpl();
        
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.CONTAINER_NAME, "lorena");
        
        ContainerController cc = rt.createAgentContainer(p);
        agenteController = cc.createNewAgent("Agente", "p2.Agente", null);
        agenteController.start();
        AgenteInterface agenteInterface = (AgenteInterface) agenteController.getO2AInterface(AgenteInterface.class);
        agenteInterface.iniciarComportamiento();
        
    }
    
}
