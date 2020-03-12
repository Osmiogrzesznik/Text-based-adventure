/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.tba;

import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author bolo
 * 
 * 
 */
   
   

public interface IAction{
 // public String outcome(Player player);
 // Finds the thing in the player inventory container Hashmap or location member containers
 // returns the outcome String (later can return string Reaction name)
 // but since things have access to player as parameter they can
 // do it directly (should not , there should be generalization of outcomes)
  public String outcome(Actor player, String thingname);
  //public void Act(Actor player);
   // Finds the thing in the player inventory container Hashmap or location member containers
 // returns void  (later can return string Reaction name)
 // but since things have access to player as parameter they can
 // do it directly (should not , there should be generalization of outcomes
  public void Act(Actor player, String thingname);
}

   
   
   
   
class DefaultPlayerActions extends Actions{
  DefaultPlayerActions(){
    super();
    this.put("go",new Go());
    this.put("examine",new Examine());
  }
}



class Actions extends HashMap{
  public Actor actor;
  Actions(Actor actor){
    super();
    this.actor= actor;// WRONG - DOWNGRADE THE MEMBER!!!
  }
  Actions(){
    super();
  }
  
  void setActor(Actor actor){
    this.actor = actor;
  }
  
  String outcome(String cmd,String thingname){
    if (this.containsKey(cmd)){ 
      IAction action = (IAction)this.get(cmd);
      String outcome = action.outcome(this.actor,thingname);
      return String.format("Action %s invoked successfuly."+outcome, cmd);
    }
    else{
      return String.format("no such Action as %s is possible for %s.", cmd, this.actor.name);
    }
    
  }
  
  void Act(String cmd,String thingname){
    if (this.containsKey(cmd)){ 
      IAction action = (IAction)this.get(cmd);
      action.Act(this.actor,thingname);
    }
    else{
      String erry = String.format("no such Action as %s is possible for %s.", cmd, this.actor.name);
      System.out.println("Errory: Silent Act:"+erry);
    }
    
  }
}

class Examine implements IAction{
  public String outcome(Actor actor, String thingname){
    if (!(actor instanceof Player)){
      return String.format("%s cannot use Examine action,)",actor.name);
    }
    Player player = (Player)actor;
    
    Inventory inventory= player.inventory;
    if (inventory.containsKey(thingname)){
      return inventory.getRef(thingname).description;
      //get not use item and examine it
    }
     
 HashMap<String,Exit> exs= player.curLoc.exits;     
 HashMap<String,Item> its= player.curLoc.items;
 //TODO: Connect them all into one HashMap or Search one after another
 //or search in order that seems most probable
 
 //HashMap<String,Item> inv= player.curLoc.items;
 
   // location
   // items,exits
    // inventory
  //HashMap inventory=actor
  return "WORK IN PROGRESS";
  }
  
  public void Act(Actor actor, String thingname){
   // return "Not implemented";
  }
}

class Go implements IGo,IAction{
  //Act is silent

  // what could be done if item sounds like it can be entered
  // for example locker, or trasure chest? HIDING MECHANISM?
  @Override
  public String outcome(Actor actor, String exitname){
    if (!(actor instanceof Player)){
      return String.format("%s cannot use GO action, it has no space (curLoc member)",actor.name);
    }
    Player player = (Player)actor;
   // System.out.println("Debug: outcome meth "+exitname);
    if (player.curLoc.exits.containsKey(exitname)){
   //   System.out.println("Debug: getting exit"+player.curLoc.toString());
      String o=player.curLoc.exits.get(exitname).react(player);
       return o;
    }
    return String.format("%s cannot enter in %s, or entered object is not in  Location %s",player.name,exitname,player.curLoc.name);
  }
  @Override
  public void Act(Actor actor, String exitname){
     if (!(actor instanceof Player)){
      System.out.println(String.format("ERRB: %s cannot use GO action, it has no space (curLoc member)",actor.name));
    }
     Player player = (Player)actor;
     if (player.curLoc.exits.containsKey(exitname)){
      String o=player.curLoc.exits.get(exitname).react(player);
     }
  }
}

interface IGo extends IAction{
  public void Act(Actor player, String exitname);
  public String outcome(Actor player, String exitname);
}


//_________________________________________________________
