package movement.thread_safe;

import garage.structure.model.PlatformField;
import movement.GarageVehicle;

public class SynchronizedPlatformField {
    private GarageVehicle vehicle;
    public final PlaformFieldMonitor fieldMonitor ;

  public SynchronizedPlatformField(){
      fieldMonitor = new PlaformFieldMonitor();
  }
    public void setVehicle( GarageVehicle vehicle ) {
        this.vehicle = vehicle;
    }
    public GarageVehicle getVehicle(){
        return this.vehicle;
    }

}
