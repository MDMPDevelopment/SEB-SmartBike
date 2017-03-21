import java.util.HashMap;
public interface DataRequester {
  public HashMap<String, String> getState(InetAddress host, String sport);
}
