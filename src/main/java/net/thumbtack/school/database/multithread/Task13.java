package net.thumbtack.school.database.multithread;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

class Formatter {
    private final ThreadLocal<SimpleDateFormat> formatDate = new ThreadLocal<>();

    public void setFormatDate(SimpleDateFormat format) {
        this.formatDate.set(format);
    }

    public String format(Date date) {
        return formatDate.get().format(date);
    }
}

class FormatterThread extends Thread {
    private final Formatter formatter;

    public FormatterThread(Formatter formatter) {
        this.formatter = formatter;
    }

    public void run() {
        formatter.setFormatDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
        Date date = new Date(100_000_000L + new Random().nextInt(1_000_000));

        String currentFormat = String.format("Поток %s. Дата: %s", currentThread().getName(), date);
        String newFormat = formatter.format(date);

        System.out.println(currentFormat);
        System.out.println(newFormat);
    }
}

public class Task13 {
    public static void main(String[] args) {
        Formatter formatter = new Formatter();
        Thread thread1 = new FormatterThread(formatter);
        Thread thread2 = new FormatterThread(formatter);
        Thread thread3 = new FormatterThread(formatter);
        Thread thread4 = new FormatterThread(formatter);
        Thread thread5 = new FormatterThread(formatter);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}