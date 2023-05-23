package net.thumbtack.school.database.multithread.task14;
import java.io.*;
import java.util.*;

public class Task14 {

    private static final int COUNT_OF_MAILS = 1000;

    public static void main(String[] args) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("ящики.txt"));
        LinkedList<String> list = new LinkedList<>();
        fillFile(COUNT_OF_MAILS);
        fillList(list);

        Thread[] threads = new Thread[COUNT_OF_MAILS];
        for (int i = 0; i < threads.length; i++) {
            Message message = new Message(list.remove(), "sender", "subject", "body");
            threads[i] = new Transport(writer, message);
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        writer.close();
    }

    public static void fillFile(int size) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("адреса.txt"))) {
            for (int i = 0; i < size; i++) {
                String email = String.format("email%s@gmail.com%n", i);
                writer.write(email);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void fillList(List<String> emails) {
        try (BufferedReader reader = new BufferedReader(new FileReader("адреса.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                emails.add(line);
                line = reader.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
