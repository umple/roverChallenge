// Author: Michal Pasternak
import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;
import java.awt.event.*;
import java.awt.event.KeyEvent;
// simple rover controller
class RoverController extends JPanel implements KeyListener {
    private char c = 'e';
    private static SocketCommunicator rover = new SocketCommunicator();
    private static SocketCommunicator ref = new SocketCommunicator();
    private static DriveCommands drive;
    public static String IP = "127.0.0.1";
    private int oldKey = 0;

    public RoverController() {
        this.setPreferredSize(new Dimension(500, 500));
        addKeyListener(this);
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawString("Click to capture keyboard", 200, 250);
    }
    @Override
    public void keyPressed(KeyEvent e)
    {
      int key = e.getKeyCode();
      if (oldKey != key){
      c = e.getKeyChar();
      if (c=='s'){
        drive.Brake(true);
      }
      if (c=='p'){
          System.out.println("distance: "+ ref.send("dist"));
      }
      if (c=='r'){
          System.out.println("program: "+ ref.send("ready"));
      }
      if (key == e.VK_LEFT) {
        drive.Left();
      }

      if (key == KeyEvent.VK_RIGHT) {
        drive.Right();
      }

      if (key == KeyEvent.VK_UP) {
        drive.Go();
      }

      if (key == KeyEvent.VK_DOWN) {
        drive.Reverse();
      }
      oldKey=key;
    }
    }
    @Override
    public void keyReleased(KeyEvent e)
    {
      c = e.getKeyChar();
      if (c=='s'){
        drive.Brake(false);
      }
      int key = e.getKeyCode();

      if (key == KeyEvent.VK_LEFT) {
        drive.Stop();
      }

      if (key == KeyEvent.VK_RIGHT) {
        //System.out.println("sent right key");
        drive.Stop();
      }

      if (key == KeyEvent.VK_UP) {
        drive.Stop();
      }

      if (key == KeyEvent.VK_DOWN) {
        drive.Stop();
      }
      oldKey=0;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        c = e.getKeyChar();
        repaint();
        if (c=='q'){
          rover.close();
          System.exit(0);
        }
    }

    public static void main(String[] s) {
        JFrame f = new JFrame();
        int refPort=0;
        int roverPort=0;
        try{
          //------ Load config
          java.util.List<String> conf = readFile("Settings/config.txt");
          Iterator<String> conIt = conf.iterator();
          while (conIt.hasNext()) {
              String a = conIt.next();
              if (a.contains("=") == true) {
                  String b = a.substring(0, a.indexOf('='));
                  if (b.equals("controlPort"))
                      roverPort = Integer.parseInt(a.substring(a.indexOf('=')+1));
                  if (b.equals("observationPort"))
                      refPort = Integer.parseInt(a.substring(a.indexOf('=')+1));
              }
          }
        rover.connectToServer(IP,roverPort);
        ref.connectToServer(IP,refPort);
        drive = new DriveCommands(rover,"Rover");
      }
      catch (Exception e){}

        f.getContentPane().add(new RoverController());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

    // file read
    public static java.util.List<String> readFile(String fileName) {
        int count = 1;
        File file = new File(fileName);
        // this gives you a 2-dimensional array of strings
        java.util.List<String> data = new ArrayList<>();
        Scanner inputStream;
        try {
            inputStream = new Scanner(file);

            while (inputStream.hasNext()) {
                data.add(inputStream.next());
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}
