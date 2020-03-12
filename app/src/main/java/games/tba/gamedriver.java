package games.tba;

//import java.util.HashMap;
//import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.regex.Pattern;

public class gamedriver {

  public static void main(String[] args) {
    boolean isStart = true;

    String[] testarr = new String[]{
      "go north",
      "go south",
      "examine room1",
      "quit game"
    };

    Scanner sc = new Scanner(System.in);
    sc.useDelimiter("\n");
    Scenario test = new Scenario(testarr);
    Inputter in = new Inputter(test, sc);
    HashMap mappy =
      World.CreateNamyMap();
       //World.CreateMap(6);
    Location startloc = (Location) mappy.get("0startroom");
    Player Bilbo = new Player("Bilbo", "Hobbit, not tall guy", startloc);//Default Actions applied but do not point back to player?
    say(Bilbo.actions.toString());
    GameLayer Game = new GameLayer(in);
    Game.addPlayer(Bilbo);
    say(startloc.description);
 
    if (isStart) {
      say(Bilbo.perceive(startloc));
      Game.startLoop();
    }
    //   GameLayer outerGameLayer= new GameLayer(in,interpretOuter);
    say("Bye Bye");
  }

  public static void say(String T) {
    System.out.println(T);
  }

}

// ------------------- Game Layer CLASS 
class GameLayer {
 // private int moves=0;
  private ArrayList<Player> Players= new ArrayList<>();
  private Inputter in;
  public boolean isRunning;
  public boolean hasChild;
  public GameLayer child;
  public String stdqst = "?";
  public final int loopSafetyCap = 10;
  
  public void addPlayer(Player plr){
    boolean ok = Players.add(plr);
    String stat= ok? plr.name+" succesfully": plr.name+" not";
    
    say(String.format("Player %s added",stat));
  }

  public String next() {
    String cmd = ask(stdqst);
    interpret(cmd);
    return cmd;
  }

  public String prepare(String inTxt) {
    String outTxt = inTxt.toLowerCase();
    return outTxt;
  }

  public void startLoop() {
    isRunning = true;

    Game:
    while (isRunning) {
      next();
    }
  }

  public GameLayer(Inputter inptr) {
    in = inptr;
    hasChild = false;

//  myinterpreter=interpretr;
  }

  public GameLayer(Inputter inptr, GameLayer Ichild) {
    in = inptr;
    hasChild = true;
    child = Ichild;
    //cmds=cmdsy;
  }

//Overridable
  public String[] interpret(String cmd) {
    String[] cmds = cmd.split("\\s+"); // CHANGE TO INTERPRETING
    
    if ("quit game".equalsIgnoreCase(cmd)){
        isRunning = false;
        return new String[]{cmd};
    }
    Player plr = Players.get(0);
   String o= plr.actions.outcome(cmds[0],cmds[1]);
   say(o);
   // say("command not recognized");
    return cmds;
  }
  
  
  
  

  public void say(String T) {
    System.out.println(T);
  }

  public String ask(String qst) {
    say(qst);
    return in.next();
  }
}
