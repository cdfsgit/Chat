import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionCliente extends Thread {
    private BufferedReader in;
    private final Socket clienteConectado;
    private PrintWriter salida;
    private int id;
    private EventoConexion ev;
    
    public ConexionCliente(Socket cliente, int id){
        this.clienteConectado = cliente;
        this.id = id;
        try {   
            this.salida = new PrintWriter(clienteConectado.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        } catch (IOException ex) {}
    }
    
    public int getIdentificador(){
        return id;
    }
    
    public void establecerEscuchadorDeEventos(EventoConexion ev){
        this.ev = ev;
    }
    
    @Override
    public void run(){
        recibirMensajes();
    }
    
    private void recibirMensajes() {
        String textoRecibido;
        try {
            while ((textoRecibido = in.readLine()) != null) {
                StringTokenizer tokens = new StringTokenizer(textoRecibido, "<");
                int destino = Integer.parseInt(tokens.nextToken());
                String mensaje = tokens.nextToken();
				if (destino == -1){
					if (mensaje == "quien") enviarMensaje(-1 + "> eres " + id);
				} else {
					ev.mensajeRecibido(mensaje, id, destino);
				}
			}
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviarMensaje(String mensaje){
        salida.println(mensaje);
    }
    
}
