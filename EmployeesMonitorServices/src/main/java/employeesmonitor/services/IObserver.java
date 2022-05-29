package employeesmonitor.services;

public interface IObserver {
    void onTaskAdded() throws ServiceException;
    void onTaskAssigned() throws ServiceException;
    void onTaskRemoved() throws ServiceException;
    void onEmployeeRemoved() throws ServiceException;
}
