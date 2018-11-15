import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * 
 * @AlexSaalberg
 */
public class LaserEnemy extends Enemy
{
    /**
     * Act - do whatever the LaserEnemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int X1;
    private int Y1;
    private int X2;
    private int Y2;
    private int laserHeight = 12;
    private int height;
    private int laserWidth;
    private int laserGap = 30;
    private int counter = 0;
    private int laserStartY = 0;
    private GuardSight guardSight;
    
    public void act() 
    {
        X1 = getX();
        Y1 = getY();
        X2 = getX()+laserWidth;
        Y2 = getY()+height;
        
        if(laserStartY==0)
        counter += 30;
        else
        counter += 3;
        
        
        if((counter)%(laserHeight+laserGap)<=laserHeight){
            if(laserStartY==0)
            laserStartY = counter-((counter)%(laserHeight+laserGap));
            
            drawLaser();
            
            if(testLaser()){
                JobWorld tempWorld = (JobWorld) getWorld();
                GreenfootSound sound = new GreenfootSound("zap.wav");
                sound.play();
                tempWorld.playerIsCaught();
            }
        }
        else
        {
            GreenfootImage img = new GreenfootImage(laserWidth,height);
            guardSight.setImage(img);
            laserStartY=0;
        }
        
        if(counter>=Math.abs(Y2-Y1)){
            counter = 0;
        }
    }    
    
    private void drawLaser(){
        
        GreenfootImage img = new GreenfootImage(Math.abs(X2-X1),Math.abs(Y2-Y1));
        img.setColor(Color.RED);
        img.setTransparency(120);
        
        if(laserHeight+laserStartY<Math.abs(Y2-Y1))
        img.fillRect(0,laserStartY,laserWidth,laserHeight);
        
        guardSight.setImage(img);
        
    }
    
    private boolean testLaser(){
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];
        xPoints[0] = X1;
        xPoints[1] = X2;
        xPoints[2] = X2;
        xPoints[3] = X1;
        
        yPoints[0] = laserStartY + Y1;
        yPoints[1] = laserStartY + Y1;
        yPoints[2] = laserStartY + laserHeight + Y1;
        yPoints[3] = laserStartY + laserHeight + Y1;
        
        JobWorld tempWorld = (JobWorld) getWorld();
        
        return(tempWorld.isPlayerBoxInPolygon(xPoints,yPoints,4));
        
    }
    
    public LaserEnemy(int newX1, int newY1, int newX2, int newY2){
        X1 = newX1;
        Y1 = newY1;
        X2 = newX2;
        Y2 = newY2;
        
        
        if((Y2<Y1)||(X2<X1))
        System.out.println("LaserSight - First point is not smaller than second.");
        
        laserWidth = (Math.abs(X2-X1));
        height = Math.abs(Y2-Y1);
        
    }
    
    protected void addedToWorld(World world){
        guardSight = new GuardSight();
        getWorld().addObject(guardSight,getX()+(X2-X1)/2,getY()+(Y2-Y1)/2);
        
        GreenfootImage img = new GreenfootImage(1,1);
        setImage(img); //Set up dummy image so it doesn't have a greenfoot.
        
        GreenfootImage img2 = new GreenfootImage(Math.abs(X2-X1),Math.abs(Y2-Y1));
        guardSight.setImage(img2);
    }
    
    
    
}
