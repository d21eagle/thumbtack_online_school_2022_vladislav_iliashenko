package net.thumbtack.school.multithread.task14;
import java.io.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Transport {
    private static final int THREAD_POOL_SIZE = 10;
    private static final String OUTPUT_FILE_PATH = "email_output.txt";

    public void send(Message message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_PATH, true))) {
            writer.write(message.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendBatch(List<Message> messages) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        for (Message message : messages) {
            executor.execute(() -> send(message));
        }
        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
