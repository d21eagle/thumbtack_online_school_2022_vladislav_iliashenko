package net.thumbtack.school.multithread.task9.ttschool;

import java.util.*;

public class Group {

    private String name;
    private String room;
    private List<Trainee> trainees;

    public Group(String name, String room) throws TrainingException {
        checkName(name);
        this.name = name;
        checkRoom(room);
        this.room = room;
        this.trainees = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public void setName(String name) throws TrainingException {
        checkName(name);
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) throws TrainingException {
        checkRoom(room);
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

    public Trainee getTraineeByFirstName(String firstName) throws TrainingException {
        for (Trainee trainee: trainees) {
            if (trainee.getFirstName().equals(firstName)) {
                return trainee;
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public Trainee getTraineeByFullName(String fullName) throws TrainingException {
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

    public List<Trainee> getTraineesWithMaxRating() throws TrainingException {
        List<Trainee> list = new ArrayList<>();
        if (trainees.size() != 0) {
            sortTraineeListByRatingDescendant();
            for (int i = 0; i < trainees.size(); i++) {
                if (trainees.listIterator(i).next().getRating() == trainees.listIterator(0).next().getRating()) {
                    list.add(trainees.listIterator(i).next());
                }
            }
            return list;
        }
        else {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public boolean hasDuplicates() {
        HashSet<Trainee> hashSet = new HashSet(trainees);
        return hashSet.size() != trainees.size();
    }

    public static void checkName(String name) throws TrainingException {
        if(name == null || name.equals(""))
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_NAME);
    }

    public static void checkRoom(String room) throws TrainingException {
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
