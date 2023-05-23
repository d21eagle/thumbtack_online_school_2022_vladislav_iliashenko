package net.thumbtack.school.database.multithread.task9.ttschool;

import java.util.*;

public class TraineeQueue {

    private Queue<Trainee> queue;

    public TraineeQueue(){
        queue = new LinkedList<>();
    }

    public void addTrainee(Trainee trainee){
        queue.add(trainee);
    }

    public Trainee removeTrainee() throws TrainingException{
        if (queue.peek() == null) {
            throw new TrainingException(TrainingErrorCode.EMPTY_TRAINEE_QUEUE);
        }
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
