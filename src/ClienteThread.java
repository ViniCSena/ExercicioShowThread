import java.rmi.NoSuchObjectException;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeoutException;

public class ClienteThread extends Thread {

    private static int ingressos = 100;

    private Semaphore semaphore;
    private int id;
    private Random random;

    public ClienteThread(int id,Semaphore semaphore) {
        this.semaphore = semaphore;
        this.id = id;
        random = new Random();
    }

    @Override
    public void run() {
        System.out.println("Cliente #"+id+" fazendo login.");
        try {
            login();
        } catch (Exception e) {
            System.out.println("Cliente #"+id+". Erro no login. Tente novamente \n Msg:"+e.getMessage());
            return;
        }
        System.out.println("Cliente #"+id+" comprando...");
        try {
            comprar();
        } catch (Exception e) {
            System.out.println("Cliente #"+id+". Erro na compra. Tente novamente \n Msg:"+e.getMessage());
            return;
        }
        System.out.println("Cliente #"+id+" finalizando a compra");
        try {
            concluirCompra();
        } catch (NoSuchObjectException e) {
            System.out.println("Cliente #"+id+". Erro na hora de concluir a compra. Tente novamente \n Msg:"+e.getMessage());
            return;
        }
    }

    private void login() throws TimeoutException, InterruptedException {
        long tempo = random.nextInt(1951)+50;
        Thread.sleep(tempo);
        if (tempo>1000){
            throw new TimeoutException("Não foi possível fazer login. Tente novamente mais tarde\n");
        }
    }
    private void comprar() throws TimeoutException, InterruptedException {
        long tempo = random.nextInt(2001)+1000;
        Thread.sleep(tempo);
        if (tempo>2500){
            throw new TimeoutException("Não foi possível concluir a compra. Tente novamente mais tarde\n");
        }
    }
    private void concluirCompra() throws NoSuchObjectException {
        int qtd = random.nextInt(4)+1;
        if (ingressos>=qtd){
            try {
                semaphore.acquire();
                ingressos-=qtd;
                System.out.println("Parabéns cliente #"+id+". Comprou "+qtd+" ingressos. \n Ingressos restantes:"+ingressos+"\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {

                semaphore.release();
            }
        }else{
            throw new NoSuchObjectException("Não tem ingressos suficientes para você comprar. Sinto muito :(\n");
        }
    }

}
