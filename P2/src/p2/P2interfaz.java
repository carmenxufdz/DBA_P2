package p2;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

// java -cp jade.jar jade.Boot -name dba_server -gui

public class P2interfaz {

    private static AgentController agenteController;
    private static Socket clientSocket;
    private static Entorno entorno;

    public static void main(String[] args) throws StaleProxyException, InterruptedException {
        try {
            jade.core.Runtime rt = jade.core.Runtime.instance();
        
            Profile p = new ProfileImpl();
        
            p.setParameter(Profile.MAIN_HOST, "localhost");
            p.setParameter(Profile.CONTAINER_NAME, "lorena");

            ContainerController cc = rt.createAgentContainer(p);
            agenteController = cc.createNewAgent("Agente", "p2.Agente", null);
            agenteController.start();
            // Inicia el servidor de conexión con Godot
            startServer();
            
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor.");
            e.printStackTrace();
        }
    }

    private static void startServer() throws IOException, InterruptedException {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Servidor iniciado en el puerto 5000. Esperando conexiones...");
            while (true) {
                try {
                    clientSocket = serverSocket.accept();  // Esto debería bloquear la ejecución hasta que un cliente se conecte
                    System.out.println("Cliente conectado desde: " + clientSocket.getInetAddress().getHostAddress());

                    try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                        System.out.println("Conexion aceptada, esperando comandos...");

                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            System.out.println("Comando recibido: '" + inputLine.trim() + "'");
                            String response = processCommand(inputLine.trim());
                            
                            out.println(response);  // Enviar respuesta

                            if ("exit".equalsIgnoreCase(inputLine.trim())) {
                                break;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error al leer del cliente");
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    System.out.println("Error al aceptar conexión");
                    e.printStackTrace();
                }
            }
        }
    }


    private static String processCommand(String command) throws InterruptedException {
        switch (command) {
            case "START":
                iniciarAgente();
                return "Agente iniciado";
            case "STEP":
                return next();
            case "OPTION":
                option();
                return "options";
            case "CHANGE":
                setEntorno();
                return "entorno actualizado";
            case "EXIT":
                // crearAgente();
                return "Terminado";
            default:
                return "Comando desconocido";
        }
    }

    private static void iniciarAgente() throws InterruptedException {
        try {
            // Intentar obtener la interfaz O2A
            AgenteInterface agenteInterface = (AgenteInterface) agenteController.getO2AInterface(AgenteInterface.class);

            if (agenteInterface != null) {
                agenteInterface.iniciarComportamiento();
                getEntorno();
            } else {
                System.out.println("Error: La interfaz del agente es null. El agente podría no estar completamente inicializado.");
            }
        } catch (StaleProxyException ex) {
            Logger.getLogger(P2interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    private static String next(){
        try {
            if (agenteController != null) {
                // Llama al método para establecer el mapa en el agente
                agenteController.getO2AInterface(AgenteInterface.class).moverAgente();
                getEntorno();
                String energia = (agenteController.getO2AInterface(AgenteInterface.class).Energia());
                return energia;
            } else {
                return "Agente no iniciado. Usa el comando 'INICIAR' primero.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al cargar el mapa.";
        }
    }
    
    private static void option(){
                try {
            if (agenteController != null) {
                // Llama al método para establecer el mapa en el agente
                getEntorno();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void setEntorno(){
        try {
            if (agenteController != null) {
                // Llama al método para establecer el mapa en el agente
                entorno = Serializador.deserializarEntorno("./json/entorno.json");
                agenteController.getO2AInterface(AgenteInterface.class).setEntorno(entorno);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void getEntorno(){
        try {
            if (agenteController != null) {
                // Llama al método para establecer el mapa en el agente
                entorno = agenteController.getO2AInterface(AgenteInterface.class).getEntorno();
                Serializador.serializarEntorno(entorno, "./json/entorno.json");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void crearAgente(){
        try {
            // Si el agente está detenido o no existe, se crea uno nuevo
            if (agenteController != null) {
                try {
                    // Intentamos acceder a la interfaz del agente. Si falla, el agente está detenido.
                    agenteController.getO2AInterface(AgenteInterface.class);
                    System.out.println("El agente está activo.");
                } catch (StaleProxyException e) {
                    // Si no se puede acceder a la interfaz, significa que el agente está detenido
                    System.out.println("El agente está detenido. Creando un nuevo agente.");
                    // Detenemos el agente si está en un estado inconsistente
                    agenteController.kill();
                }
            }

            // Crear un nuevo agente
            jade.core.Runtime rt = jade.core.Runtime.instance();
            Profile p = new ProfileImpl();
            p.setParameter(Profile.MAIN_HOST, "localhost");
            p.setParameter(Profile.CONTAINER_NAME, "lorena");

            ContainerController cc = rt.createAgentContainer(p);
            agenteController = cc.createNewAgent("Agente", "p2.Agente", null);
            agenteController.start();
        }catch (StaleProxyException ex) {
            Logger.getLogger(P2interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
