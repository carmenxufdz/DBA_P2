/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package p2;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
/**
 *
 * @author carme
 */
public class P2 {
    
    private static AgentController agenteController;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws StaleProxyException {
        // TODO code application logic here
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
