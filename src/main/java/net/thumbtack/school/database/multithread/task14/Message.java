package net.thumbtack.school.database.multithread.task14;
import lombok.*;

@Data
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Message {
    private String emailAddress;
    private String sender;
    private String subject;
    private String body;

    @Override
    public String toString() {
        return "Message {" + "email: '" + emailAddress + '\'' + ", sender: '" + sender + '\'' +
                ", subject: '" + subject + '\'' + ", body: '" + body + '\'' + '}';
    }
}
