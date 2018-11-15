import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math;
/**
 * 
 * @AlexSaalberg
 */
public class Character extends ScrollingActor
{
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private final int speed = 8;
    private InvisPlayer colSlave;
    private boolean onTeleporter;
    
    private final int playerWidth = 50; //Penguin image width is 60
    private final int playerHeight = 60; //Penguin image height is 71
    
    public void act() 
    {
        int direction = 0;
        if (Greenfoot.isKeyDown("a"))
        {
            directionMove(270);
        }
        if (Greenfoot.isKeyDown("d"))
        {
            directionMove(90);
        }  
        if (Greenfoot.isKeyDown("w"))
        {
            directionMove(180);
        }  
        if (Greenfoot.isKeyDown("s"))
        {
            directionMove(0);
        }
        
        /*MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse!=null)
        turnTowards(mouse.getX(),mouse.getY());
        */
        Actor a = getOneIntersectingObject(Teleporter.class);    
          if (a != null)        
          {        
              if(!onTeleporter){
                  
              Teleporter b = (Teleporter) a;
              JobWorld tempWorld = (JobWorld) getWorld();
              
              
              
              if(b instanceof Teleporter1){
              for(Object c : tempWorld.getObjects(Teleporter1.class)){
                  Teleporter d = (Teleporter) c;
                  if(!(b==d)){
                  setLocation(d.getX(),d.getY());
                  }
                }}
                
              if(b instanceof Teleporter2){
              for(Object c : tempWorld.getObjects(Teleporter2.class)){
                  Teleporter d = (Teleporter) c;
                  if(!(b==d)){
                  setLocation(d.getX(),d.getY());
                  }
                }}
                
              onTeleporter = true;
              }
          }
          else
          {
              onTeleporter=false;
          }
       
    }
    
    private boolean directionMove(int direction)
    {
        if(colSlave.checkCollision(direction,getX(),getY(), getRotation()))
        {
        int moveX = (int)Math.sin(Math.toRadians(direction));
        int moveY = (int)Math.cos(Math.toRadians(direction));
        //System.out.println(moveX+" TEST "+moveY); //Used for testing to see if sin & cos use radians or degrees.
        
        setLocation(getX()+speed*moveX,getY()+speed*moveY);
        return true;
        }
        return false;
    }
    
    protected void addedToWorld(World world){
         setUpScroll(world);
         colSlave = new InvisPlayer();
         colSlave.setSpeed(speed);
         GreenfootImage colSlaveImage = new GreenfootImage("tux.png");
         colSlaveImage.setTransparency(1);
         colSlave.setImage(colSlaveImage);
         JobWorld tempWorld = (JobWorld) getWorld();
         tempWorld.addObject(colSlave,getX(),getY());
    }
    
    public int getWidth(){
        return playerWidth;
    }
    
    public int getHeight(){
        return playerHeight;
    }
}
