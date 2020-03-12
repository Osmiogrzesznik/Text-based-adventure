package games.tba;

import java.util.HashMap;
import java.util.Iterator;


public class World {
  
  public static HashMap CreateNamyMap(){
   
   HashMap<String,Location> mappy= new HashMap();
   Location startloc= new Location("startroom","this is the entrance to the dungeon");
   mappy.put("0startroom",startloc);
   Location prevloc;
   prevloc = startloc;
   String[] Roomnames=new String[]{
    "1Hall","2Chamber of Souls","3Souls Keep"
   };
   
      for (String RoomName: Roomnames ){
      Location l=
         new Location(RoomName,
           "this is the"+RoomName+" Location");
      System.out.println(RoomName);
      mappy.put(RoomName,l);
     
       Exit exitprevlocTol= new Exit(prevloc,l,"northy");
        Exit exitlToprevloc= new Exit(l,prevloc,"southy");
        prevloc.addExit("north",exitprevlocTol);
        l.addExit("south",exitlToprevloc);
     
      prevloc=l;
    }
    
    for(Location l: mappy.values()){
     String li="\n"+l.toString();
    System.out.println(li);
    }
    
    return mappy;
  }
  
  public static HashMap CreateMap(int roomsAmount) {
    
   HashMap<String,Location> mappy= new HashMap();
   Location startloc= new Location("startroom","this is the entrance to the dungeon");
   mappy.put("startroom",startloc);
   Location prevloc;
   prevloc = startloc;
    for (int id=1;id<roomsAmount;id++){
      Location l= new Location(prevloc,"room"+id,"this is the another room");
      System.out.println(id);
      mappy.put("room"+id,l);
      prevloc=l;
    }
    
    for(Location l: mappy.values()){
    System.out.println(l);
    }
    
    
    /* LAMBDA VERSION
    mappy.values().forEach((l) -> {
      System.out.println(l);
    });
    */
    
    
    
   /*
   /*   Location l= new Location("gffvnj");
   String s= l.examine();
   System.out.println(s) ;
 HashMap v= new HashMap();
 Key reqs= new Key("Skull reqs");
  System.out.println( reqs.examine());
   */
   return mappy;
    }
  
  
}


/* Container extends Thing{
  
}*/





class Location extends Thing{
  
  public String type="Location";
  public int id;
  public HashMap items= new HashMap();
  public HashMap<String,Exit> exits= new HashMap<>();
  
  
  
  
  public Location(String nm){
    super(nm);
  }
  
  public Location(String nm,String ds){
    super(nm,ds);
  }
  
  
  public boolean addExit(String exnm,Exit ex){
    if (ex.start.equals(this)){
      if (exits.containsValue(exnm)){
        System.out.println(String.format("ErrB:Location %s contains %s exit (the same)already",name,exnm));
        return false;
      }
      exits.put(exnm,ex);
      return true;
    }
    else{
         System.out.println(String.format(
           "ErrB:Exit %s just being added to Location %s"+
           "points to %s as its start location."+
           " This may brake back /return system . Not Adding",
           exnm,name,ex.start.name));
           return false;
    }
    
  }
  
//  public boolean addExit(String exnm,Location l){}
    
  public void link(Location l,String exitTolnm){
    Exit exitthis = new Exit(this,l,exitTolnm);
    Exit exitl = new Exit(l,this,exitTolnm);
    if (addExit(exitTolnm,exitthis)){
     if (!l.addExit(exitTolnm,exitl)){
       System.out.println(String.format("ErrB:Failed to link back %s with %s",l.name,name));
   
     }
    }
    else{
      System.out.println(String.format("ErrB:Failed to link %s with %s",name,l.name));
    }
  }
  
  public void link(Location l ){
    Exit exitthis = new Exit(this,l);
    Exit exitl = new Exit(l,this);
    exits.put(l.name,exitthis);
    l.exits.put(this.name,exitl);
  }
  
  public Location (Location l,String nm,String ds){
    super(nm,ds);
    this.link(l);
  }
  
  @Override
  public String getType(){
    return this.type;
  }
  
  @Override
  public String toString(){
    String u=super.toString();
    u+= " \n__EXITS: ( \n";
    for (HashMap.Entry<String,Exit> exentry: exits.entrySet()){
      u+= "External name:"+exentry.getKey();
      u+="____"+exentry.getValue().toString()+"\n";
      }
   // u+= exits.values().stream().map((ext) -> "____"+ext.toString()+", \n").reduce(u, String::concat);
    u+= ")\n";
    return u;
  }
}

class LockedExit extends Exit{
  //implements Lockable, Usable
  public boolean isOpen=true;
  public Requirements reqs;
  
  public LockedExit(Location start, Location end, String nm,String ds,Requirements reqs,boolean isOpen){
    super(start,end,nm,ds);
    this.reqs=reqs;
    this.isOpen=isOpen;
  }
  
  @Override
  public String examine(){
    String d= ""+description;
      // some doors may be allowed to retain mystery how open or what happens
      
      // THIS DOESNT WORK?????????
     // d+="\n"+reqs.getDescription();
    
    return d;
  }
  
  @Override
  public String react(Player player){
    if(isOpen){
      player.setCurrentLocation(end);
      return String.format("Player %s succesfully entered the %s, and found himself in %s",player.name,this.name,end.name);
    }
    else{
      // do damage
      return "You shall not pass.... yet.";
    }
  }
  
  @Override
  public String toString(){
    return super.toString()+
    " REQS:"+reqs.description;
  }
  
}

class Exit{
  
  public String type="Exit";
  public String description;
  public String name;
  public Location start;
  public Location end;
  //public int time=0;
  
// CONSTRUCTORS
  public Exit(Location start, Location end,String nm, String ds){
    this.description=ds;
    this.name=nm;
    this.start=start;
    this.end=end;
    //this.time=traverseTime;
  }
  
  public Exit(Location start, Location end,String nm){
    this(start,
      end,
      nm,
      "typical "+nm+", nothing unusual about it"
      );
  }
  
  public Exit(Location start, Location end){
    this(start,
      end,
      "exit");
  }
    
//METHODS
  
  public String examine(){
    String d= ""+description;
      // some doors may be allowed to retain mystery how open or what happens
    return d;
  }
  
  public String react(Player player){
    player.setCurrentLocation(end);
    return String.format("Player %s succesfully entered the %s, and found himself in %s",player.name,this.name,end.name);
  }
  
// OTHER
  @Override
  public String toString(){
    return 
    "TYPE:"+type+
    " NAME:"+name+
    " DESC:"+description+
    " TO:"+end.name;
  }

}



class Requirements{
  String description=" you cannot pass , however there may be some way to change it";
 HashMap<String,Item> itemsInInventory= new HashMap<>();
  //String[] modes = ""
  /*
    there may be different modes 
    use(item key){
      if (vector itemsRequiredToUse.
    }{233 }
  */
  Requirements(Item Key){
    
  }
    public String getDescription(){
    // may be some checks to leave truth uncovered
    return description;
  }
  
  void check(){
    
  }
  
}

