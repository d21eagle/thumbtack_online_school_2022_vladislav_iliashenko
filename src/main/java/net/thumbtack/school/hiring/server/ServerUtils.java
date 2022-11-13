package net.thumbtack.school.hiring.server;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import net.thumbtack.school.hiring.exception.*;

public class ServerUtils {
    private static final Gson GSON = new Gson();
    public static <T> T getClassFromJson(String requestJsonString, Class<T> tempClass) throws ServerException {
        if (Strings.isNullOrEmpty(requestJsonString)) {
            throw new ServerException(ServerErrorCode.WRONG_JSON);
        }
        return GSON.fromJson(requestJsonString, tempClass);
    }
}
