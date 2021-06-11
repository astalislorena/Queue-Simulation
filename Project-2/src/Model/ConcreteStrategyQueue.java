package Model;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t, int currentTime) {
        Server minimum = servers.get(0);
        for(Server server: servers) {
            if(server.getNoTasks() < minimum.getNoTasks()) {
                server.currentTime = currentTime;
                minimum = server;
            }
        }
        System.out.println("added task " + t.gettProcessing() + " at server " + minimum.toString());
        minimum.addTask(t, currentTime);
    }
}
