import greenfoot.*;  
import java.util.List;


public class CameraButton extends CollectableActor
{
    /**
     * Act - do whatever the CameraButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
    
    
    public void collected(){
        World tempWorld = getWorld();
        tempWorld.removeObjects(tempWorld.getObjects(SecurityCamera.class));
        List<Actor> tempList = tempWorld.getObjects(GuardSight.class);
        for (Actor temp : tempList) {
            temp.setLocation(10000,10000);
        }
        tempWorld.removeObject(this);
    }
}
