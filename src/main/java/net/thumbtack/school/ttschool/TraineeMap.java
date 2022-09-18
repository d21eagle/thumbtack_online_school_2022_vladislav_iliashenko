package net.thumbtack.school.ttschool;

import java.util.*;

public class TraineeMap {

    private Map<Trainee, String> trainees;

    public TraineeMap() {
        trainees = new HashMap<>();
    }

    public void addTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        if (trainees.putIfAbsent(trainee, institute) != null) {
            throw new TrainingException(TrainingErrorCode.DUPLICATE_TRAINEE);
        }
    }

    public void replaceTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        if (!(trainees.replace(trainee, trainees.get(trainee), institute))) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public void removeTraineeInfo(Trainee trainee) throws TrainingException {
        if (trainees.remove(trainee) == null) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public int getTraineesCount(){
        return trainees.size();
    }

    public String getInstituteByTrainee(Trainee trainee) throws TrainingException {
        if (trainees.get(trainee) == null) {
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
