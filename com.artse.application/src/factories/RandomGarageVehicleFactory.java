package factories;

import business_logic.dto.VehicleCategory;

import business_logic.dto.VehicleDTO;
import movement.GarageVehicle;
import util.Identification;
import util.Photo;

import java.security.SecureRandom;

public class RandomGarageVehicleFactory extends GarageVehicleFactory {
    private static int count = 0;
    public static GarageVehicle createVehicle( int level){
       return createVehicleAt(new VehicleDTO(new Identification(generateName(),generateRegistrationNumber(),generateVIN(), Photo.DEFAULT_PHOTO),0,0,generateRandomCategory()),level);
    }

    static SecureRandom rnd = new SecureRandom();
   private static VehicleCategory generateRandomCategory(){
       return  VehicleCategory.values()[rnd.nextInt(8)];
   }
   private static String generateVIN(){
       final String AB = "0123456789ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnoprstuvwxyz";
        StringBuilder sb = new StringBuilder(17);
        for( int i = 0; i < 17; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }
    private static String generateRegistrationNumber(){
       final String A = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
       final String B = "0123456789";
       StringBuilder sb = new StringBuilder(9);
       for(int i = 0 ;i<3;++i)
           sb.append(B.charAt(rnd.nextInt(B.length())));
       sb.append("-");
       sb.append(A.charAt(rnd.nextInt(A.length())));
       sb.append("-");
       for(int i =0;i<3;++i)
           sb.append(B.charAt(rnd.nextInt(B.length())));

       return sb.toString();
    }
    private static String generateName(){
       return "Vehicle"+(count++);
    }
}
