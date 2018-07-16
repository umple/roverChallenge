// Author: Michal Pasternak
import java.io.IOException;
import java.util.concurrent.TimeUnit;

// format commands to unity string messages.
public class DriveCommands {
  public static String name;
    private static SocketCommunicator rover;

    public DriveCommands (SocketCommunicator sc,String n){
      rover = sc;
      name = n;
    }

    public static void Left(){
        rover.noReply(name+",setLRPower(-100,100)");
        System.out.println(name+",setLRPower(-100,100)");
    }

    public static void Right(){
        rover.noReply(name+",setLRPower(100,-100)");
        System.out.println(name+",setLRPower(100,-100)");
    }

    public static void Go(){
        rover.noReply(name+",setLRPower(100,100)");
        System.out.println(name+",setLRPower(100,100)");
    }

    public static void Stop(){
        rover.noReply(name+",setLRPower(0,0)");
        System.out.println(name+",setLRPower(0,0)");
    }
    public static void Reverse(){
        rover.noReply(name+",setLRPower(-100,-100)");
        System.out.println(name+",setLRPower(-100,-100)");
    }
    public static void Brake(boolean on){
      if (on == true){
        rover.noReply(name+",brake(100)");
        System.out.println(name+",brake(100)");
      }
      else
        rover.noReply(name+",brake(0)");
        System.out.println(name+",brake(0)");
    }
    public static void Place(){
        rover.noReply(name+",place()");
        System.out.println(name+",place()");
    }
}
