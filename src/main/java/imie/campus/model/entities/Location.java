package imie.campus.model.entities;

import imie.campus.core.entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "location")
public class Location extends BaseEntity<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOC_LOCATION_ID")
    private Integer id;

    @Column(name = "LOC_ADDRESS", nullable = false)
    private String address;

    @Column(name = "LOC_ADDRESS_2")
    private String address2;

    @Column(name = "LOC_ADDRESS_3")
    private String addresse3;

    @Column(name ="LOC_CITY")
    private String city;

    @Column(name = "LOC_COUNTRY")
    private String country;

    @Column(name ="LOC_LATITUDE_GPS")
    private Float latitude;

    @Column(name = "LOC_LONGITUDE_GPS")
    private Float longitude;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddresse3() {
        return addresse3;
    }

    public void setAddress3(String addresse3) {
        this.addresse3 = addresse3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer primaryKey() {
        return this.id;
    }

}
