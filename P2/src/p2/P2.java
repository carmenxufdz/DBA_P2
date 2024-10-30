package p2;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.util.Scanner;

// java -cp jade.jar jade.Boot -name dba_server -gui

public class P2 {

    public static void main(String[] args) throws StaleProxyException {
        Scanner scan = new Scanner(System.in);
        int opcion;
        /*
        do {
            System.out.println("1. Ejercicio 1");
            System.out.println("2. Ejercicio 2");
            System.out.println("3. Ejercicio 3");
            System.out.println("4. Ejercicio 4 usando for");
            System.out.println("5. Ejercicio 4 sin usar for");
            System.out.print("Selecciona una opción: ");
            opcion = scan.nextInt();
        } while (opcion<1 || opcion>5);
         */
        
        jade.core.Runtime rt = jade.core.Runtime.instance();
        
        Profile p = new ProfileImpl();
        
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.CONTAINER_NAME, "lorena");
        
        String agentName = "Reactivo";
        String className = "p2.Agente";
        /*
        switch (opcion) {
            case 1:
                break;
        }
        */
        ContainerController cc = rt.createAgentContainer(p);
        AgentController ac = cc.createNewAgent(agentName, className, null);
        
        ac.start();
    }
    
}
