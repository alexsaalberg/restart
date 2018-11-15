import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class vertWalkEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CircleEnemy6 extends ConeEnemy
{

    private int walkDistanceX;
    private int walkDistanceY;
    private int startPointX;
    private int startPointY;
    private boolean movingDown = false;
    private boolean movingUp = false;
    private boolean movingRight = true;
    private boolean movingLeft = false;
    private double test;
    
    public CircleEnemy6(int walkX,int walkY, int xLoca, int yLoca){
        walkDistanceX=walkX-300;
        walkDistanceY=walkY-300;
        startPointX= xLoca;
        startPointY= yLoca;

    }
    
    
    public void act() 
    {
        super.act();
        if(movingDown){
            setDirection(0);
            directionMove(getDirection(), 3);
        }else if(movingLeft){
            setDirection(90);
            directionMove(getDirection(), 3);
        }else if(movingUp){
            setDirection(180);
            directionMove(getDirection(), 3);
        }else if(movingRight){
            setDirection(270);
            directionMove(getDirection(), 3);
        }
        test=getScrollingY();
        if(movingDown&&getScrollingY()+300>startPointY+walkDistanceY){
            movingDown = false;
            movingLeft = true;
            
        }else if(movingLeft&&getScrollingX()+400<startPointX){
            movingLeft = false;
            movingUp = true;
        
        }else if(movingUp&&getScrollingY()+300<startPointY){
            movingUp =false;
            movingRight= true;
        
        }else if(movingRight&&getScrollingX()+400>startPointX+walkDistanceX){
            movingRight =false;
            movingDown= true;
        
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
    }
}
