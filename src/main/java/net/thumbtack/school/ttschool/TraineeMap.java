package net.thumbtack.school.ttschool;

import java.util.*;

public class TraineeMap {

    private Map<Trainee, String> trainees;

    public TraineeMap() {
        trainees = new HashMap<>();
    }

    public void addTraineeInfo(Trainee trainee, String institute) throws TrainingException{
        // REVU не надо containsKey, putIfAbsent сама скажет
        if (trainees.containsKey(trainee)) {
            throw new TrainingException(TrainingErrorCode.DUPLICATE_TRAINEE);
        }
        trainees.put(trainee, institute);
    }

    public void replaceTraineeInfo(Trainee trainee, String institute) throws TrainingException{
        // REVU не надо containsKey, replace сама скажет
        if (trainees.containsKey(trainee)) {
            trainees.replace(trainee, institute);
        }
        else {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public void removeTraineeInfo(Trainee trainee) throws TrainingException{
        // REVU не надо containsKey, remove сама скажет
        if (!trainees.containsKey(trainee)) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        trainees.remove(trainee);
    }

    public int getTraineesCount(){
        return trainees.size();
    }

    public String getInstituteByTrainee(Trainee trainee) throws TrainingException{
        // REVU не надо containsKey, get сама скажет
        if (!trainees.containsKey(trainee)) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        return trainees.get(trainee);
    }

    public Set<Trainee> getAllTrainees() {
        return new HashSet<>(trainees.keySet());
    }

    public Set<String> getAllInstitutes() {
        return new HashSet<>(trainees.values());
    }

    public boolean isAnyFromInstitute(String institute) {
        return trainees.containsValue(institute);
    }
}
