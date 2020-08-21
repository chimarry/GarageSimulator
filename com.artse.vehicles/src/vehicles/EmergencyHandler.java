package vehicles;
public interface EmergencyHandler {
    enum Rotation{OFF,ON}
    long time = 7000;
    EmergencyHandler.Rotation getRotation();
    void setRotation( EmergencyHandler.Rotation aRotation );
    default  void handleEmergency(){
        try{
           setRotation(Rotation.ON);
            Thread.sleep(time);
           setRotation(Rotation.OFF);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }
}
