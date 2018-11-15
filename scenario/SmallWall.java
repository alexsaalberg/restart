import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
* Write a description of class SmallWall here.
* 
* @author (your name) 
* @version (a version number or a date)
*/
public class SmallWall extends SolidActor
{
    public SmallWall()
    {
        GreenfootImage b = getImage();
        b.scale(b.getWidth()/2, b.getHeight()/2);
        setImage(b);
    }
 
}