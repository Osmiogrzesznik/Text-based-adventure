package games.tba;

import java.util.Scanner;

class Inputter{
  public int mode=0;
  static final int SCENARIO = 1;
  public static final int USERINPUT = 0;
  public Scenario scenario1=new Scenario(new String[]{"a","b"});
  private Scanner scanny;
  
  public Inputter(int modeint,Scanner s){
    mode=modeint;
    scanny = s;
  }
  
  public Inputter(Scenario scn,Scanner s){
    scenario1= scn;
    mode=SCENARIO;
    scanny = s;
    
  }
  
  public Inputter(Scanner s){
    mode=USERINPUT;
    scanny= s;
  }
  
  
  
  public void say(String T){
   System.out.println(T);
   
  }
  
  public String next(){
    String o=nextSilent();
    say("next input:"+o);
    return o;
  }
  
  public String nextSilent(){
    if (mode==SCENARIO){
      return fromArray();
    }
    else{
      return scanny.next();
    }
    
  }
  
  public String fromArray(){
    if (scenario1.isFin){
     return scanny.next();
     
     } 
      else{
       return scenario1.next();
    }
   
  }


}


