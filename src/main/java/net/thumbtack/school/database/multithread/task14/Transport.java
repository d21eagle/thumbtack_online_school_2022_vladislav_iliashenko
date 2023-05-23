package net.thumbtack.school.database.multithread.task14;
import lombok.*;
import java.io.*;

@AllArgsConstructor
public class Transport extends Thread{
    private final BufferedWriter bufferedWriter;
    private final Message message;

    public void run() {
        send(message);
    }

    public void send(Message message) {
        try {
            Thread.sleep(100);
            bufferedWriter.write(message.toString() + "; Отправитель: " + Thread.currentThread().getId() + "\n");
            bufferedWriter.flush();
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
