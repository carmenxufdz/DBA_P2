/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pr1;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
/**
 *
 * @author carmenxufdz
 */
public class Ej1Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            // Obtenemos la instancia del entorno de ejecución
            Runtime rt = Runtime.instance();

            // Creamos un contenedor de agentes
            Profile p = new ProfileImpl();
            p.setParameter(Profile.MAIN_HOST, "localhost");
            p.setParameter(Profile.CONTAINER_NAME, "Ejercicio1Container");
            ContainerController cc = rt.createAgentContainer(p);

            // Creamos un nuevo agente y lo ejecutamos
            AgentController ac = cc.createNewAgent("Ejercicio1Agent", Ejercicio1.class.getCanonicalName(), null);
            ac.start();

        } catch (Exception e) {
            System.out.println("Error al ejecutar el agente: " + e.getMessage());
        }
    }
    
}

