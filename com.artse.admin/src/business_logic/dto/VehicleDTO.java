package business_logic.dto;
import util.Identification;
import util.Photo;

import java.io.Serializable;

public class VehicleDTO implements Serializable {

    private final static long serialVersionUID = 28L;

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification( Identification id ) {
        name = id.getName();
        registrationNumber = id.getRegistrationNumber();
        vin = id.getVin();
        photo =id.getPhoto();
        photoPath = photo.getPhotoPath();
        identification = new Identification(name,registrationNumber,vin,photo);
    }

    private Identification identification;


    private String name;
    private String registrationNumber;
    private String vin;
    private String photoPath;
    private Photo photo;
    private Integer doorNo;
    private Integer deadweight;
    private VehicleCategory category;

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath( String photoPath ) {
     this.photoPath = photoPath;
    }

    public String getName() {
        return name;
    }

    public void setName( final String name ) {
        this.name = name;
    }

    public String getVin() {
        return vin;
    }

    public void setVin( final String vin ) {
        this.vin = vin;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber( String registrationNumber ) {
        this.registrationNumber = registrationNumber;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto( final Photo photo ) {
        this.photo = photo;
    }


    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (!(o instanceof VehicleDTO)) return false;
        return vin.equals(((VehicleDTO) o).vin);
    }

    public VehicleDTO( Identification id, Integer doorNo, Integer deadweight, VehicleCategory category){
        setIdentification(id);
        setCategory(category);
        setDeadweight(deadweight);
        setDoorNo(doorNo);
    }
    public VehicleCategory getCategory() {
        return category;
    }

    public void setCategory( VehicleCategory category ) {
        this.category = category;
    }


    public int getDoorNo() {
        return doorNo;
    }

    public void setDoorNo( int doorNo ) {
        this.doorNo = doorNo;
    }

    public int getDeadweight() {
        return deadweight;
    }

    public void setDeadweight( int deadweight ) {
        this.deadweight = deadweight;
    }
}
