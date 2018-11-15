import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * 
 * @AlexSaalberg
 */
public class InvisPlayer extends Player
{
    /**
     * Act - do whatever the InvisPlayer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int speed = 0;
    
    public void act() 
    {
      Actor a = getOneIntersectingObject(CollectableActor.class);    
      if (a != null)        
      {        
          CollectableActor b = (CollectableActor) a;
          b.collected();
      }
    }    
    
    public void directionMove(int direction)
    {
        int moveX = (int)Math.sin(Math.toRadians(direction));
        int moveY = (int)Math.cos(Math.toRadians(direction));
        setLocation(getX()+speed*moveX,getY()+speed*moveY);
    }
    
    public void setSpeed(int newSpeed){
        speed=newSpeed;
    }
    
    
    
    private boolean outOfWorld(){
        JobWorld tempWorld = (JobWorld) getWorld();
        int width = tempWorld.getLevelWidth();
        int height = tempWorld.getLevelHeight();
        int minWidth = tempWorld.getLevelMinWidth();
        int minHeight = tempWorld.getLevelMinHeight();
        int tempX = (int) getScrollingX();
        int tempY = (int) getScrollingY();
        //System.out.println(tempX+" "+width);
        //System.out.println(tempY+" "+height);
        if(tempX<minWidth||tempX>width||tempY<minHeight||tempY>height)
        return false;
        
        return false;
    }
    
    public boolean checkCollision(int direction, int newX, int newY, int newRotation)    
    { 
      //setRotation(newRotation);
      setLocation(newX,newY);
      directionMove(direction);
      Actor a = getOneIntersectingObject(SolidActor.class);    
      if (a != null||outOfWorld())       
      {        
          return false; 
      }  
      return true;
    }
}
