package employeesmonitor.networking.objectprotocol;

import employeesmonitor.model.Employer;

public class GetAllTasksRequest implements Request {
    private Employer tasksCreator;

    public GetAllTasksRequest(Employer tasksCreator) {
        this.tasksCreator = tasksCreator;
    }

    public Employer getTasksCreator() {
        return tasksCreator;
    }
}
