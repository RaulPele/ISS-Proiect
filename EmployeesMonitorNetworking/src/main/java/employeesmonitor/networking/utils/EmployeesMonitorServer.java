package employeesmonitor.networking.utils;

import employeesmonitor.networking.objectprotocol.ClientWorker;
import employeesmonitor.services.IController;

import java.net.Socket;

public class EmployeesMonitorServer extends AbstractConcurrentServer {
    private IController server;

    public EmployeesMonitorServer(int port, IController server) {
        super(port);
        this.server = server;
        System.out.println("EmployeesMonitor server created.");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientWorker worker = new ClientWorker(client, server);
        return new Thread(worker);
    }
}
