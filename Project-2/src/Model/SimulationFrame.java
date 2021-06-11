package Model;


import java.util.List;

public class SimulationFrame {
    public String text;

    SimulationFrame() {
        text = "";
    }

    public void update(int currentTime, List<Server> servers) {
        text = "Time: " + currentTime + "\n";
        for(Server server: servers) {
            text += server.toString() + ": ";
            for(Task task: server.getTasks()) {
                text += task.toString() + " ";
            }
            text += "\n";
        }
    }
}
