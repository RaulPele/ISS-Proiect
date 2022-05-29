package employeesmonitor.networking.utils;

import java.net.Socket;

public abstract class AbstractConcurrentServer extends AbstractServer{

    public AbstractConcurrentServer(int port) {
        super(port);
        System.out.println("Concurrent server created.");
    }

    @Override
    public void processRequests(Socket client) {
        Thread threadWorker = createWorker(client);
        threadWorker.start();
    }

    protected abstract Thread createWorker(Socket client);
}