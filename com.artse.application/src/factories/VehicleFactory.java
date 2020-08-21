package factories;

import business_logic.dto.VehicleCategory;
import business_logic.dto.VehicleDTO;
import util.Identification;
import vehicles.*;

public class VehicleFactory {
    public static Wheelable createVehicle( VehicleDTO dto ){
        Identification identification = dto.getIdentification();
        VehicleCategory category  = dto.getCategory();
        switch (category){
            case FV: return new FiremenVan(identification,dto.getDeadweight());
            case V:return new Van(identification,dto.getDeadweight());
            case PM:return new PoliceMotorcycle(identification);
            case PC:return  new PoliceCar(identification,dto.getDoorNo());
            case C:return new Car(identification,dto.getDoorNo());
            case AC:return new AmbulanceCar(identification,dto.getDoorNo());
            case AV:return new AmbulanceVan(identification,dto.getDeadweight());
            case M:return new Motorcycle(identification);

        }
        return  null;
    }
}
