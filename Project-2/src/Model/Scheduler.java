package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    public int currentTime;
    // ?
//    private int maxNoServers;
//    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        // create server object
        // create thread with the object
        servers = new ArrayList<>(maxNoServers);
        for(int i = 0; i < maxNoServers; i++) {
            servers.add(new Server(maxTasksPerServer));
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if(policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispachTask(Task t) {
        strategy.addTask(servers, t, currentTime);
    }

    public List<Server> getServers() {
        return servers;
    }

    public int noOfTasks() {
        int noTasks = 0;
        for(Server server: servers) {
            noTasks += server.getNoTasks();
        }
        return noTasks;
    }
}
