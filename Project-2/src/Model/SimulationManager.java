package Model;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationManager implements Runnable {
    public int timeLimit = 200;
    public int maxProcessingTime = 9;
    public int minProcessingTime = 3;
    public int maxArrivalTime = 100;
    public int minArrivalTime = 10;
    public int numberOfServers = 20;
    public int numberOfClients = 1000;
    public SelectionPolicy policy = SelectionPolicy.SHORTEST_TIME;

    public Scheduler scheduler;
    public SimulationFrame frame;
    private List<Task> generatedTasks;

    public SimulationManager() {
        // Initialize the scheduler
        scheduler = new Scheduler(numberOfServers, numberOfClients);
        // Initialize selection strategy
        scheduler.changeStrategy(policy);
        // Generate number of clients random tasks
        this.generateRandomTasks();
        // Create and start numberOfServers threads
        for(Server server: scheduler.getServers()) {
                Thread t = new Thread(server);
                t.start();
        }
        frame = new SimulationFrame();
    }

    public SimulationManager(int timeLimit, int numberOfServers, int numberOfClients, int minArrivalTime, int maxArrivalTime, int minProcessingTime, int maxProcessingTime, SelectionPolicy policy) {
        // Initialize the scheduler
        this.timeLimit = timeLimit;
        this.numberOfClients = numberOfClients;
        this.numberOfServers = numberOfServers;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        scheduler = new Scheduler(numberOfServers, numberOfClients);
        // Initialize selection strategy
        scheduler.changeStrategy(policy);
        // Generate number of clients random tasks
        this.generateRandomTasks();
        // Create and start numberOfServers threads
        for(Server server: scheduler.getServers()) {
            Thread t = new Thread(server);
            t.start();
        }

    }

    private void generateRandomTasks() {
        generatedTasks = new ArrayList<>(numberOfClients);
        for(int i = 0; i < numberOfClients; i++) {
            Random random = new Random();
            int tProcessing = random.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime;
            random = new Random();
            int tArrival = random.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime;
            generatedTasks.add(new Task(i, tProcessing, tArrival));
        }
        // sort the list by arrival time
        generatedTasks.sort(Task::compareTo);
    }

    @Override
    public void run() {

        try {
            FileWriter log = new FileWriter("log.txt");
            int currentTime = 0;
            int peakHour = 0;
            int max = 0;
            double averageWaitingTime = 0;
            double averageServiceTime = 0;
            List<Task> toRemove = new ArrayList<>();
            while(currentTime < timeLimit) {
                log.write("Time: " + currentTime + "\n");
                System.out.println("Here");
                for(Task task: generatedTasks) {
                    if(task.gettArrival() == currentTime) {
                        scheduler.currentTime = currentTime;
                        scheduler.dispachTask(task);
                        toRemove.add(task);
                        averageServiceTime += task.gettProcessing();
                    }
                    // Update UI Frame
//                    frame.update(currentTime, scheduler.getServers());
                }
                generatedTasks.removeAll(toRemove);
                log.write("Waiting clients: ");
                for(Task task: generatedTasks) {
                    log.write(task.toString() + " ");
                }
                log.write("\n");
                for(Server server: scheduler.getServers()) {
                    log.write("Queue" + server.toString() + ": ");
                    for(Task task: server.getTasks()) {
                        log.write(task.toString() + " ");
                    }
                    log.write("\n");
                }
                if (max < scheduler.noOfTasks()) {
                    max = scheduler.noOfTasks();
                    peakHour = currentTime;
                }
                log.write("\n");
                currentTime++;
                log.write("------------------------------------------------------------------\n");
                if (currentTime == timeLimit) {
                    for (Server server: scheduler.getServers()) {
                        averageWaitingTime += server.getWaitingPeriod().intValue();
                        server.stop();
                    }
                    log.write("Peak hour: " + peakHour + "\n");
                    log.write("Average waiting time: " + (averageWaitingTime / (numberOfClients*numberOfServers)) + "\n");
                    log.write("Average service time: " + (averageServiceTime/numberOfClients) + "\n");
                    log.close();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SimulationManager gen = new SimulationManager();
        Thread t = new Thread(gen);
        t.start();
    }
}
