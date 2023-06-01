package net.thumbtack.school.multithread;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PingPongLock extends Thread {
    private final Lock lock = new ReentrantLock();
    private boolean pingTurn = true;
    private final Condition ping;
    private final Condition pong;

    public PingPongLock() {
        ping = lock.newCondition();
        pong = lock.newCondition();
    }

    public void ping() {
        try {
            lock.lock();
            if (pingTurn) {
                pingTurn = false;
            }

            System.out.println("ping");
            Thread.sleep(400);

            ping.signalAll();
            pong.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void pong() {
        try {
            lock.lock();
            while (pingTurn) {
                pong.await();
            }

            System.out.println("pong");
            Thread.sleep(400);

            pong.signalAll();
            ping.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class Task11 {
    public static void main(String[] args) {
        PingPongLock pingPongLock = new PingPongLock();

        Thread pingThread = new Thread(() -> {
            while (true) {
                pingPongLock.ping();
            }
        });
        Thread pongThread = new Thread(() -> {
            while (true) {
                pingPongLock.pong();
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

