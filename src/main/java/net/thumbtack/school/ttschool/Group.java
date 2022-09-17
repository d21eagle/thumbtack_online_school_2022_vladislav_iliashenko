package net.thumbtack.school.ttschool;

import java.util.*;

public class Group {

    private String name;
    private String room;
    private List<Trainee> trainees;

    public Group(String name, String room) throws TrainingException {
        nullCheckerForName(name);
        this.name = name;
        nullCheckerForRoom(room);
        this.room = room;
        this.trainees = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public void setName(String name) throws TrainingException {
        nullCheckerForName(name);
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) throws TrainingException {
        nullCheckerForRoom(room);
        this.room = room;
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }

    public void addTrainee(Trainee trainee) {
        trainees.add(trainee);
    }

    public void removeTrainee(Trainee trainee) throws TrainingException {
        if (!trainees.remove(trainee)) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public void removeTrainee(int index) throws TrainingException {
        if (index >= trainees.size() || trainees.remove(index) == null) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public Trainee getTraineeByFirstName(String firstName) throws TrainingException{
        for (Trainee trainee: trainees) {
            if (trainee.getFirstName().equals(firstName)) {
                return trainee;
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public Trainee getTraineeByFullName(String fullName) throws TrainingException{
        for (Trainee trainee: trainees) {
            if (trainee.getFullName().equals(fullName)) {
                return trainee;
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public void sortTraineeListByFirstNameAscendant(){
        trainees.sort(Comparator.comparing(Trainee::getFirstName));
    }

    public void sortTraineeListByRatingDescendant(){
        trainees.sort(Comparator.comparing(Trainee::getRating).reversed());
    }

    public void reverseTraineeList(){
        Collections.reverse(trainees);
    }

    public void rotateTraineeList(int positions){
        Collections.rotate(trainees, positions);
    }

    public List<Trainee> getTraineesWithMaxRating() throws TrainingException{
        if (trainees.size() == 0) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }

        int max = 0;
        List<Trainee> traineeList = new ArrayList<>();

        for (Trainee trainee: trainees) {
            if (trainee.getRating() > max) {
                max = trainee.getRating();
            }
        }

        for (Trainee trainee: trainees) {
            if (max == trainee.getRating()) {
                traineeList.add(trainee);
            }
        }
        return traineeList;
    }

    public boolean hasDuplicates(){
        for (int i = 0; i < trainees.size(); i++) {
            for (int j = 0; j < trainees.size(); j++) {
                if (trainees.get(i).equals(trainees.get(j)) && i != j) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void nullCheckerForName(String name) throws TrainingException{
        if(name == null || name.equals(""))
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_NAME);
    }

    public static void nullCheckerForRoom(String room) throws TrainingException{
        if(room == null || room.equals(""))
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_ROOM);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name) && Objects.equals(room, group.room) && Objects.equals(trainees, group.trainees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, room, trainees);
    }
}
