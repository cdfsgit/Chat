import java.util.EventListener;
public interface EventoConexion extends EventListener {
    public void mensajeRecibido(String mensaje, int remitente, int destino);
}
