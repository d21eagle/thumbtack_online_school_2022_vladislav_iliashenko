package net.thumbtack.school.multithread;
import java.util.concurrent.Semaphore;

class PingPong extends Thread {
    private Semaphore ping = new Semaphore(1);
    private Semaphore pong = new Semaphore(0);

    public void ping() {
        try {
            ping.acquire();
            System.out.println("ping");
            Thread.sleep(400);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            pong.release();
        }
    }

    public void pong() {
        try {
            pong.acquire();
            System.out.println("pong");
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            ping.release();
        }
    }
}

public class Task7 {
    public static void main(String[] args) {
        PingPong pingPong = new PingPong();

        Thread pingThread = new Thread(() -> {
            while(true) {
                pingPong.ping();
            }
        });
        Thread pongThread = new Thread(() -> {
            while(true) {
                pingPong.pong();
            }
        });

        pingThread.start();
        pongThread.start();
        try {
            pingThread.join();
            pongThread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
