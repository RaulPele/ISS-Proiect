package employeesmonitor.networking.objectprotocol;

public class ErrorResponse implements Response{
    String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
