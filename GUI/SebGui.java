import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SebGui extends javax.swing.JFrame{

  private SebModel model;
  private JLabel parentLabel;
  private int delay;

  JLabel labelTurnL, labelTurnR, labelBrake, labelSpeed;

  public SebGui() {

    super("SEB - Safe Electronic Bike");

    //TODO: ***** REPLACE WITH REGULAR REQUEST STATE CLASS *****
    model = new SebModel(new RequestStateStub());

    //Add bike image
    JPanel panel = new JPanel();
    panel.setLayout(null);
  //  panel.setSize(500, 500);
    panel.setBackground(Color.WHITE);
    ImageIcon icon = new ImageIcon("bike.png");
    parentLabel = new JLabel();
    parentLabel.setIcon(icon);
    panel.add(parentLabel);
    parentLabel.setSize(1000, 600);
    parentLabel.setLocation(100, 0);


    labelTurnL = newBikeLabel("Turning Left!", 600, 25);
    labelTurnR = newBikeLabel("Turning Right!", 600, 75);
    labelBrake = newBikeLabel("Braking", 100, 150);
    labelSpeed = newBikeLabel("Speed: 0 km/h", 0, 0);

    updateGUI();

    this.getContentPane().add(panel);

    delay = 750; //ms
    ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            updateGUI();
        }
    };
    new javax.swing.Timer(delay, taskPerformer).start();


    this.setSize(1000, 600);
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

  private void updateGUI() {
    model.updateInfo();

    labelTurnL.setVisible(model.getTurnL());
    labelTurnR.setVisible(model.getTurnR());

    //Flash indicators
    if (model.getTurnL()) {
      if (labelTurnL.getForeground() == Color.GRAY)
        labelTurnL.setForeground(Color.ORANGE);
      else
        labelTurnL.setForeground(Color.GRAY);
    }

    if (model.getTurnR()) {
      if (labelTurnR.getForeground() == Color.GRAY)
        labelTurnR.setForeground(Color.ORANGE);
      else
        labelTurnR.setForeground(Color.GRAY);
    }

    labelBrake.setVisible(model.getBrake());
    labelSpeed.setText("Speed: " + String.format("%.2f", model.getSpeed()) + " km/h");
  }

  private JLabel newBikeLabel(String text, int width, int height) {
    JLabel bikeLabel = new JLabel(text, JLabel.LEFT);
    bikeLabel.setLocation(width, height);
    bikeLabel.setSize(250, 50);
    bikeLabel.setFont(new Font("Serif", Font.BOLD, 30));
    parentLabel.add(bikeLabel);
    return bikeLabel;
  }


  public static void main(String[] args) {
    new SebGui();
  }

}
