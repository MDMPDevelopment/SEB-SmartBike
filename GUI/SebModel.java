import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

public class SebModel {

  private boolean turnL, turnR, brake;
  private double speed;

  private RequestStateInterface requester;

  public SebModel() {
    this(new RequestState());
  }

  public SebModel(RequestStateInterface requester) {
    this.requester = requester;
    updateInfo();
  }

  public boolean getTurnL() { return turnL; }
  public boolean getTurnR() { return turnR; }
  public boolean getBrake() { return brake; }
  public double getSpeed() { return speed; }

  public void updateInfo() {
    HashMap<String, String> state = requester.getState();
    turnL = state.get("turnL").equals("1") ? true : false;
    turnR = state.get("turnR").equals("1") ? true : false;
    brake = state.get("brake").equals("1") ? true : false;
    speed = Double.parseDouble(state.get("Speed"));
  }

//  public static void main(String args[]) {
//
//    SebModel model = new SebModel(new RequestStateStub());
//    model.updateInfo();
//  }

}
