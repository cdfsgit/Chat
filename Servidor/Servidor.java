import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor implements EventoConexion{
    private final int portNumber;
    private ServerSocket serverSocket;
    
    private final ArrayList<ConexionCliente> misClientes;
    
    public Servidor(int puerto){
        portNumber = puerto;
        misClientes = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void iniciarEscucha(){
        try{
            while (2 > 1){
                ConexionCliente cc = new ConexionCliente(serverSocket.accept(), misClientes.size());
				cc.establecerEscuchadorDeEventos(this);
                misClientes.add(cc);
                System.out.println("Número de clientes: " + misClientes.size());
                cc.start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void mensajeRecibido(String mensaje, int remitente, int destino) {
        misClientes.get(destino).enviarMensaje(remitente + ">" + mensaje);
    }
}
