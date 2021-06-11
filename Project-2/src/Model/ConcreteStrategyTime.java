package Model;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t, int currentTime) {
        Server minimum = servers.get(0);
        for(Server server: servers) {
            if(server.getTotalTime() < minimum.getTotalTime()) {
                server.currentTime = currentTime;
                minimum = server;
            }
        }
        System.out.println("added task " + t.gettArrival() + " at server " + minimum.toString());
        minimum.addTask(t, currentTime);
    }
}
