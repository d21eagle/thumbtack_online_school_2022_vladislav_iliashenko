package net.thumbtack.school.file;

import com.google.gson.Gson;
import net.thumbtack.school.figures.v3.Rectangle;
import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorException;
import net.thumbtack.school.ttschool.Trainee;
import net.thumbtack.school.ttschool.TrainingException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

public class FileService {

    public static void writeByteArrayToBinaryFile(String fileName, byte[] array) throws IOException {
        writeByteArrayToBinaryFile(new File(fileName), array);
    }

    public static void writeByteArrayToBinaryFile(File file, byte[] array) throws IOException {
        try (FileOutputStream fout = new FileOutputStream(file)) {
            fout.write(array);
        }
    }

    public static byte[] readByteArrayFromBinaryFile(String fileName) throws IOException {
        return readByteArrayFromBinaryFile(new File(fileName));
    }

    public static byte[] readByteArrayFromBinaryFile(File file) throws IOException {
        try(FileInputStream fin = new FileInputStream(file)) {
            return fin.readAllBytes();
        }
    }

    public static byte[] writeAndReadByteArrayUsingByteStream(byte[] array) throws IOException {
        byte[] byteArr;
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
            bout.writeBytes(array);
            byteArr = bout.toByteArray();
        }

        byte[] arrInput = new byte[array.length / 2];
        try (ByteArrayInputStream bin = new ByteArrayInputStream(byteArr)) {
            for (int i = 0, n = 0; i < array.length; i++) {
                // REVU усложнили. Просто skip
                if (i % 2 == 0) {
                    arrInput[n] = (byte)bin.read();
                    n++;
                }
                else bin.read();
            }
        }
        return arrInput;
    }

    public static void writeByteArrayToBinaryFileBuffered(String fileName, byte[] array) throws IOException {
        writeByteArrayToBinaryFile(new File(fileName), array);
    }

    public static void writeByteArrayToBinaryFileBuffered(File file, byte[] array) throws IOException {
        try (BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(file))) {
            bout.write(array);
        }
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(String fileName) throws IOException {
        return readByteArrayFromBinaryFile(new File(fileName));
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(File file) throws IOException {
        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file))) {
            return bin.readAllBytes();
        }
    }

    public static void writeRectangleToBinaryFile(File file, Rectangle rect) throws IOException {
        try (DataOutputStream rectData = new DataOutputStream(new FileOutputStream(file))) {
            rectData.writeInt(rect.getTopLeft().getX());
            rectData.writeInt(rect.getTopLeft().getY());
            rectData.writeInt(rect.getBottomRight().getX());
            rectData.writeInt(rect.getBottomRight().getY());
        }
    }

    public static Rectangle readRectangleFromBinaryFile(File file) throws IOException, ColorException {
        try (FileInputStream fin = new FileInputStream(file);
             DataInputStream rectData = new DataInputStream(fin)) {
            return new Rectangle(
                    rectData.readInt(),
                    rectData.readInt(),
                    rectData.readInt(),
                    rectData.readInt(),
                    Color.RED);
        }
    }

    public static void writeRectangleArrayToBinaryFile(File file, Rectangle[] rects) throws IOException {
        try (DataOutputStream rectData = new DataOutputStream(new FileOutputStream(file))) {
            for (Rectangle rect: rects) {
                rectData.writeInt(rect.getTopLeft().getX());
                rectData.writeInt(rect.getTopLeft().getY());
                rectData.writeInt(rect.getBottomRight().getX());
                rectData.writeInt(rect.getBottomRight().getY());
            }
        }
    }

    public static Rectangle[] readRectangleArrayFromBinaryFileReverse(File file) throws IOException, ColorException {
        Rectangle[] rect = new Rectangle[(int) (file.length() / 16)];
        try (FileInputStream fin = new FileInputStream(file);
             // REVU не заносить надо в обратном порядке, а читать
             // RandomAccessFile и его метод seek
             DataInputStream rectData = new DataInputStream(fin)) {
            for (int i = (int)(file.length() / 16) - 1; i >= 0; i--) {
                rect[i] = new Rectangle(
                        rectData.readInt(),
                        rectData.readInt(),
                        rectData.readInt(),
                        rectData.readInt(),
                        Color.RED);
            }
        }
        return rect;
    }

    public static void writeRectangleToTextFileOneLine(File file, Rectangle rect) throws IOException {
        // REVU текстовый вывод. FileWriter или OutputStreeamWriter
        try (FileOutputStream fout = new FileOutputStream(file);
             DataOutputStream rectData = new DataOutputStream(fout)) {
            rectData.writeChars(rect.getTopLeft().getX() + " "
                    + rect.getTopLeft().getY() + " " + rect.getBottomRight().getX() + " "
                    + rect.getBottomRight().getY());
        }
    }

    public static Rectangle readRectangleFromTextFileOneLine(File file) throws IOException, ColorException {
        // REVU верно, но обычно пишут так
        // try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))
        // REVU и укажите кодировку UTF8, здесь и везде в текстовом вводе-выводе
        // да, сейчас тут только числа, но кто знает, что будет потом
        try (FileInputStream fin = new FileInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fin))) {
            String[] rectData = reader.readLine().split(" ");
            return new Rectangle(
                    Integer.parseInt(rectData[0].replaceAll("\\W", "")),
                    Integer.parseInt(rectData[1].replaceAll("\\W", "")),
                    Integer.parseInt(rectData[2].replaceAll("\\W", "")),
                    Integer.parseInt(rectData[3].replaceAll("\\W", "")),
                    Color.RED);
        }
    }

    public static void writeRectangleToTextFileFourLines(File file, Rectangle rect) throws IOException {
        // REVU текстовый вывод. FileWriter или OutputStreeamWriter
        try (FileOutputStream fin = new FileOutputStream(file);
             DataOutputStream rectData = new DataOutputStream(fin)) {
            rectData.writeChars(rect.getTopLeft().getX() + "\n");
            rectData.writeChars(rect.getTopLeft().getY() + "\n");
            rectData.writeChars(rect.getBottomRight().getX() + "\n");
            rectData.writeChars(String.valueOf(rect.getBottomRight().getY()));
        }
    }

    public static Rectangle readRectangleFromTextFileFourLines(File file) throws IOException, ColorException {
        try (FileInputStream fin = new FileInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fin))) {
            String[] rectData = new String[Files.readAllLines(file.toPath()).size()];
            for (int i = 0; i < rectData.length; i++) {
                rectData[i] = Arrays.toString(reader.readLine().split(" "));
            }
            return new Rectangle(
                    Integer.parseInt(rectData[0].replaceAll("\\W", "")),
                    Integer.parseInt(rectData[1].replaceAll("\\W", "")),
                    Integer.parseInt(rectData[2].replaceAll("\\W", "")),
                    Integer.parseInt(rectData[3].replaceAll("\\W", "")),
                    Color.RED);
        }
    }

    public static void writeTraineeToTextFileOneLine(File file, Trainee trainee) throws IOException {
        try (FileWriter traineeData = new FileWriter(file, StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            gson.toJson(trainee, traineeData);
        }
    }

    public static Trainee readTraineeFromTextFileOneLine(File file) throws IOException, TrainingException {
        try (FileReader traineeData = new FileReader(file, StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            return gson.fromJson(traineeData, Trainee.class);
        }
    }

    public static void writeTraineeToTextFileThreeLines(File file, Trainee trainee) throws IOException {
        try (BufferedWriter traineeData = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            traineeData.write(trainee.getFirstName());
            traineeData.newLine();
            traineeData.write(trainee.getLastName());
            traineeData.newLine();
            traineeData.write(String.valueOf(trainee.getRating()));
            traineeData.newLine();
        }
    }

    public static Trainee readTraineeFromTextFileThreeLines(File file) throws IOException, TrainingException {
        try (BufferedReader traineeData = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            return new Trainee(traineeData.readLine(), traineeData.readLine(), Integer.parseInt(traineeData.readLine()));
        }
    }

    public static void serializeTraineeToBinaryFile(File file, Trainee trainee) throws IOException {
        // REVU тут не запись в текстовом виде, а сераилизация
        // ObjectOutputStream.writeObject
        writeTraineeToTextFileThreeLines(file, trainee);
    }

    public static Trainee deserializeTraineeFromBinaryFile(File file) throws IOException, TrainingException {
        // ObjectInputStream.readObject
        return readTraineeFromTextFileThreeLines(file);
    }

    public static String serializeTraineeToJsonString(Trainee trainee) {
        return new Gson().toJson(trainee);
    }

    public static Trainee deserializeTraineeFromJsonString(String json) {
        return new Gson().fromJson(json, Trainee.class);
    }

    public static void serializeTraineeToJsonFile(File file, Trainee trainee) throws IOException {
        try (FileOutputStream fout = new FileOutputStream(file);
             BufferedWriter traineeData = new BufferedWriter(new OutputStreamWriter(fout))) {
            traineeData.write(serializeTraineeToJsonString(trainee));
        }
    }

    public static Trainee deserializeTraineeFromJsonFile(File file) throws IOException {
        try (FileInputStream fin = new FileInputStream(file);
             BufferedReader traineeData = new BufferedReader(new InputStreamReader(fin))) {
            return deserializeTraineeFromJsonString(traineeData.readLine());
        }
    }
}
