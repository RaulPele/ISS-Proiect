package employeesmonitor.networking.objectprotocol;

import employeesmonitor.services.IObserver;

public class AddObserverRequest implements Request{
    private IObserver client;

    public AddObserverRequest(IObserver client) {
        this.client = client;
    }

    public IObserver getClient() {
        return client;
    }
}
