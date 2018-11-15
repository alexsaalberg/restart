import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
* Write a description of class vertWalkEnemy here.
* 
* @author (your name) 
* @version (a version number or a date)
*/
public class Level4Hallway2 extends ConeEnemy
{

    private int walkDistanceX = 1700;
    private int walkDistanceY = 0;
    private int startPointX;
    private int startPointY;
    private boolean movingDown = false;
    private boolean movingUp = false;
    private boolean movingRight = true;
    private boolean movingLeft = false;
    
    
    public void act() 
    {
        super.act();
        if(!isTurning()){
            directionMove(getDirection(), 3);
            //System.out.println(getTargetDirection());
            //System.out.println(getDirection());
        }
        
        if(movingDown&&getScrollingY()>startPointY+walkDistanceY){
            movingDown = false;
            movingLeft = true;
            smoothSetDirection(90);
            
        }else if(movingLeft&&getScrollingX()<startPointX){
            movingLeft = false;
            movingUp = true;
            smoothSetDirection(180);
        
        }else if(movingUp&&getScrollingY()<startPointY){
            movingUp =false;
            movingRight= true;
            smoothSetDirection(270);
        
        }else if(movingRight&&getScrollingX()>startPointX+walkDistanceX){
            movingRight =false;
            movingDown= true;
            smoothSetDirection(0);
        }
        
    }   
    
    private void directionMove(int direction, int speed)
    {
        
        int moveX = -1*(int)Math.sin(Math.toRadians(direction));
        int moveY = (int)Math.cos(Math.toRadians(direction));
        setLocation(getX()+speed*moveX,getY()+speed*moveY);
    }
    
    protected void addedToWorld(World world){
        super.addedToWorld(world);
        setSightLength(250);
        setSightAngle(30);
        startPointY = (int) getScrollingY();
        startPointX = (int) getScrollingX();
        setDirection(270);
    }
}