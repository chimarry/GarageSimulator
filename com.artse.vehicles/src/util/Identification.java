package util;

import vehicles.Vehicle;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Objects;

public class Identification implements Serializable, Cloneable{

    private String name;
    private String registrationNumber;
    private String vin;
    private Photo photo;

    public Identification( String name, String registrationNumber, String vin, Photo photo){
        setName(name);
        setPhoto(photo);
        setRegistrationNumber(registrationNumber);
        setVin(vin);
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

    public FileInputStream getPhotoStream() {
        return photo.getPhotoStream();
    }

    @Override
    public Identification clone(){
        return new Identification(name,registrationNumber,vin,photo);
    }

    @Override
    public String toString() {
        return "Identification:"+"\n"+
                " registration number:"+registrationNumber+" ,"+"vin: "+vin+
                " ,"+"name: "+name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin);
    }

    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (!(o instanceof Identification)) return false;
        Identification id = (Identification) o;
        return (Objects.equals(vin,id.vin));
    }
}
