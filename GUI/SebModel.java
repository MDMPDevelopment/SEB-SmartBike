import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

public class SebModel {

  private HashMap<String, String> state;
  private DataRequester requester;

  public SebModel() {
    this(new ServerRequester());
  }

  public SebModel(DataRequester requester) {
    this.requester = requester;
  }

  public HashMap<String, String> getState() {
    return state;
  }

  public void updateInfo() {
    state = requester.getState();
  }

  public static void main(String args[]) {

    SebModel model = new SebModel(new ServerRequesterStub());

    model.updateInfo();
    HashMap<String, String> currentState = model.getState();

  }
}
