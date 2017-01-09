package samdwise.justjava.com.biometricmobile;

/**
 * Created by Akinrinde on 5/24/2016.
 */
public class SaveLocation {



   // private int id;
    String _address;

    String _longitude;
    String _latitude;

    public SaveLocation(){

    }

    public SaveLocation(String getAdd,String getLong, String getLat){

        this._address = getAdd;
        this._longitude = getLong;
        this._latitude = getLat;
    }

    public SaveLocation(String getAdd){

        this._address = getAdd;

    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_longitude() {
        return _longitude;
    }

    public void set_longitude(String _longitude) {
        this._longitude = _longitude;
    }

    public String get_latitude() {
        return _latitude;
    }

    public void set_latitude(String _latitude) {
        this._latitude = _latitude;
    }
}
