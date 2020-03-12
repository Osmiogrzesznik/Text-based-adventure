package games.tba;

class Scenario{
  private String[] scn;
  private int curInd=0;
  public boolean isFin=false;
  public int length;
  public Scenario(String[] scnarr){
    scn=scnarr;
    length=scn.length;
  }
  
  
  public String next(){
    System.out.println("deb curInd:"+curInd);
    String o=scn[curInd];
    if (curInd<length-1){
      curInd++;
      
    }
    else{
      isFin=true;
    }
    return o;
  }

}
