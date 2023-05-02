package net.thumbtack.school.database.func;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonWithoutOptional {
    private PersonWithoutOptional father;
    private PersonWithoutOptional mother;

    public PersonWithoutOptional getMothersMotherFather(){
        if(mother == null || mother.getMother() == null){
            return null;
        } else {
            return mother.getMother().getFather();
        }
    }
}
