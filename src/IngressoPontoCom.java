import java.util.concurrent.Semaphore;

public class IngressoPontoCom {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        Thread cliente;
        for (int i=1; i<=300; i++){
            cliente = new ClienteThread(i,semaphore);
            cliente.start();
        }
    }
}
