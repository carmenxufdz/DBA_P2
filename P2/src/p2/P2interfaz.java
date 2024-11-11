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
            case "CHOOSE_MAP":
                try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    System.out.println("Esperando la ruta...");

                    String inputLine = in.readLine();
                    System.out.println("Ruta recibida: '" + inputLine.trim() + "'");
                    String response = cargarMapa(inputLine.trim());
                    out.println(response);  // Enviar respuesta
                        
                } catch (IOException e) {
                    System.out.println("Error al leer del cliente");
                    e.printStackTrace();
                }
            case "EXIT":
                return "Cliente desconectado";
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
    
    private static String cargarMapa(String path) {
        try {
            if (agenteController != null) {
                // Llama al método para establecer el mapa en el agente
                agenteController.getO2AInterface(AgenteInterface.class).cargarMapa(path);
                return "Mapa cargado desde: " + path;
            } else {
                return "Agente no iniciado. Usa el comando 'INICIAR' primero.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al cargar el mapa.";
        }

    }
    
    private static String next(){
        try {
            if (agenteController != null) {
                // Llama al método para establecer el mapa en el agente
                agenteController.getO2AInterface(AgenteInterface.class).moverAgente();
                getEntorno();
                return "Next";
            } else {
                return "Agente no iniciado. Usa el comando 'INICIAR' primero.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al cargar el mapa.";
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
}
