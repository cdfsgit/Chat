public class Chat {
    public static void main(String[] args) {
        int puertoEscucha;
        puertoEscucha = Integer.parseInt(args[0]);
		Servidor s = new Servidor(puertoEscucha);
		s.iniciarEscucha();
    }
}
