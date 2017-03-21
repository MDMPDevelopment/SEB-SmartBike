import java.util.HashMap;

public class ServerRequesterStub implements DataRequester {

  private HashMap<String, String> state;

  public ServerRequesterStub() {
    state = new HashMap<String, String>();
    state.put("turnL", "0");
    state.put("turnR", "0");
    state.put("brake", "0");
    state.put("speed", "0");
  }

  public HashMap<String, String> getState(InetAddress host, String sport) {
    return state;
  }

}
