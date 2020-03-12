/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.tba;

import java.util.HashMap;



class Player extends Actor{
  public Inventory inventory= new Inventory();
  
  public Actions actions = new DefaultPlayerActions();
  public int health;
  public int maxHealth;
  public int timeSpent;
  public Location curLoc;
  public Location lastLoc;
  
  Player(Location loc){
    this("Player","Traveler, typical Everyman",loc);
    
     actions.setActor(this);
  }
  
   Player(String nm,String ds,Location loc){
    super(nm,ds);
    actions.setActor(this);
    curLoc=loc;
  }
  
  Player(String nm,String ds,Location loc, Actions actions){
    super(nm,ds,actions);
    actions.setActor(this);
    curLoc=loc;
  }
  
  void setCurrentLocation(Location nloc){
    lastLoc=curLoc;
    curLoc=nloc;
  }
  
  String perceive(Thing thing){
    return thing.description;
  }
 
}


class Inventory{
  private HashMap<String,Item> inv=new HashMap();
 // private HashMap amounts= new HashMap();
 //) /if item is consumable , useadestroys i mean, the item should be stackable
  
  public boolean containsKey(String name){
    return inv.containsKey(name);
  }
 
 public void put(String iname,Item i){
     if (!inv.containsKey(iname)){
   
       inv.put(iname,i);
     }
     else{
      inv.get(iname).add(1);
     }
   
   } 
  public void put(Item i){
    this.put(i.name,i);
  }
  
  public Item takeOut(String iname){
    if (inv.containsKey(iname)){
    Item item= inv.get(iname);
        if (item.useDestroys){
      item.amount-=1;
     
    }   
    return item;
    }
    else{
      System .out.println("ErrB:Item not found you"+
       " should check check for existence furst first. Returned dummy error item");
     return new Item("ErrorItemNotFound");
    }
  }
  public Item getRef(String iname){
    if (inv.containsKey(iname)){
    Item item= inv.get(iname);
    return item;
   }
   else{
     System.out.println("ErrB:Item not found you"+
       " should check check for existence furst firsto");
     return new Item("ErrB:ErrorItemNotFound");
   }
       //item is reusable; just return reference,
      // do not decrease amount 
    // to not make container dispose of it;
  }
 
 }