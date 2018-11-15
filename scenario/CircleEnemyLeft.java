import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class vertWalkEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CircleEnemyLeft extends ConeEnemy
{

    private int walkDistanceX;
    private int walkDistanceY;
    private int startPointX;
    private int startPointY;
    private boolean movingDown = false;
    private boolean movingUp = false;
    private boolean movingRight = false;
    private boolean movingLeft = true;
    private double test;
    
    public CircleEnemyLeft(int walkX,int walkY, int xLoca, int yLoca){
        walkDistanceX=walkX-300;
        walkDistanceY=walkY-300;
        startPointX= xLoca;
        startPointY= yLoca;

    }
    
    
    public void act() 
    {
        super.act();
        if(movingDown){
            setRotation(0);
            directionMove(getRotation(), 3);
        }else if(movingLeft){
            setRotation(90);
            directionMove(getRotation(), 3);
        }else if(movingUp){
            setRotation(180);
            directionMove(getRotation(), 3);
        }else if(movingRight){
            setRotation(270);
            directionMove(getRotation(), 3);
        }

        if(movingDown&&getScrollingY()+300>startPointY+walkDistanceY){
            movingDown = false;
            movingRight = true;
            
        }else if(movingLeft&&getScrollingX()+400<startPointX-walkDistanceX){
            movingLeft = false;
            movingDown = true;
        
        }else if(movingUp&&getScrollingY()+300<startPointY){
            movingUp =false;
            movingLeft= true;
        
        }else if(movingRight&&getScrollingX()+400>startPointX){
            movingRight =false;
            movingUp= true;
        
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
