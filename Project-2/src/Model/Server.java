package Model;

import javafx.collections.ObservableArray;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private volatile boolean running;
    public int currentTime;

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public Server(int noTasks) {
        tasks = new LinkedBlockingQueue<>(noTasks);
        waitingPeriod = new AtomicInteger(0);
    }

    public int getTotalTime() {
        int total = 0;
        for(Task task: tasks) {
            total += task.gettProcessing();
        }
        return total;
    }

    public int getNoTasks() {
        return tasks.size();
    }

    public void addTask(Task task, int currentTime) {
        this.currentTime  = currentTime;
        tasks.add(task);
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        running = true;
         while(running) {
             if(tasks.size() > 0) {
                     Task actualTask = tasks.poll();
                     try {
                         assert actualTask != null;
                         Thread.sleep(actualTask.gettProcessing() * 1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                     System.out.println("Finished the client");
                     waitingPeriod.addAndGet(currentTime - actualTask.gettArrival());
                 } else {
                     try {
                         Thread.sleep(1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }


         }
    }
}
