package net.thumbtack.school.multithread.task14;
import java.io.*;
import java.util.*;

public class Task14 {
    public static void main(String[] args) {
        List<Message> messages = loadMessagesFromFile();
        Transport transport = new Transport();
        transport.sendBatch(messages);
    }

    private static List<Message> loadMessagesFromFile() {
        List<Message> messages = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("email_addresses.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String emailAddress = line.trim();
                Message message = new Message(emailAddress, "sender@example.com", "Hello", "This is a test email");
                messages.add(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
