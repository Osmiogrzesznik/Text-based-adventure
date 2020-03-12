package games.tba;

import java.util.Map;

public class Thing 
{
  public String type="Thing";
  public String description;
  public String name;
  
  public Thing(String nm){
    description=nm+", nothing special about it";
    name=nm;
  }
  
  public Thing(String nm, String ds){
    name=nm;
    description=ds;
  }
  
  public String examine(){
    return description;
  }
  
  public String examine(Player player){
    //?player.perceive(this)
    return description;
  }
  
  public String getType(){
    return this.type;
  }
  
  @Override
  public String toString(){
    return "TYPE:"+
      this.getType()+
         " NAME:"+name+
      " DESC:"+description;
  }
}

class Item extends Thing{
  boolean useDestroys=false;
  int amount=1;
  
  Item(){
    super("item");
  }
  Item(String nm){
    super(nm);
  }
  Item(String nm,String ds,boolean dstr,int amt){
    super(nm,ds);
    useDestroys=dstr;
    amount=amt;
  }
  Item(String nm,String ds,boolean dstr){
    super(nm,ds);
    useDestroys=dstr;
  }
  void add(int amt){
    if(amount+amt<=0){
      amount=0;
    }
    else{
      amount+=amt;
    }  
  }
  void remove(int amt){
    if(amount-amt<=0){
      amount=0;
    }
    else{
      amount-=amt;
    }  
  }
  String react(Actor actor){
    String reactionDesc=actor.name+" ";
    reactionDesc+=String.format("used %s and something happened ",this.name);
    return reactionDesc;
  }
//  Reaction use(Actor actor){
//    return this.
//  }
}

//class Reaction{
//  
//}

class Actor extends Thing{
  public Actions actions;
   public int health;
  public int maxHealth;
  public int timeSpent;

  
  Actor(Actions actions){
    this("Actor","Actor, can act on other things",actions);
  }
  
  Actor(String nm,String ds,Actions actions){
    super(nm,ds);
    this.actions = actions;
    this.actions.setActor(this);
  } 
  
  Actor(String nm,String ds){
    super(nm,ds);
    actions = new Actions();
    actions.setActor(this);
  } 
  
  void setActions(Actions actions){
    this.actions=actions;
    actions.setActor(this);
  }
}

class Key extends Item{
  Key(){
    super("reqs");
  }
  Key(String nm){
    super(nm);
  }
  
}

