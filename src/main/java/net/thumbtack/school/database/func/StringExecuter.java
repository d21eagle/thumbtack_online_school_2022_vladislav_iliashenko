package net.thumbtack.school.database.func;
import java.util.List;

public class StringExecuter {
    public static List<String> split (String str){
        return List.of(str.split(" "));
    }
}