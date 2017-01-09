package samdwise.justjava.com.biometricmobile;


public class InvestorPpty {
    int _userId;
    String _fname;
    String _mname;
    String _lname;
    String _dob;
    String _homeAddress;
    String _gender;

    String _contactAddress;
    String _phoneNumber;
    String _email;
    String _blood;
    String _marital;

    String _children;
    String _disablity;
    String _schoolStatus;
    String _education;
    String _houseHoldType;
    String _sizeHousehold;

    String _imageUrl;
    String _dateAdded;
    String _uniqueId;
    String _accountType;
    String _latitude;
    String _longitude;


    // Empty constructor
    public InvestorPpty() {

    }

    public InvestorPpty(int userId, String fname, String mname, String lname, String dob,
                        String homeAddress, String gender,String latitude, String longitude, String contactAddress,
                        String phoneNumber, String email,
                        String bloodgrp, String marital, String children, String disability,
                        String schoolStatus, String education, String householdType,String sizeHousehold, String imageUrl,
                        String dateAdded, String uniqueId, String accountType){
        this._userId=userId;
        this._fname=fname;
        this._mname=mname;
        this._lname=lname;
        this._dob=dob;
        this._homeAddress=homeAddress;
        this._gender=gender;
        this._latitude = latitude;
        this._longitude = longitude;

        this._contactAddress = contactAddress;
        this._phoneNumber=phoneNumber;
        this._email=email;
        this._blood = bloodgrp;
        this._marital = marital;

        this._children=children;
        this._disablity=disability;
        this._schoolStatus=schoolStatus;
        this._education=education;
        this._houseHoldType=householdType;
        this._sizeHousehold=sizeHousehold;

        this._imageUrl=imageUrl;
        this._dateAdded=dateAdded;
        this._uniqueId=uniqueId;
        this._accountType=accountType;
    }

    public InvestorPpty(String fname, String mname, String lname, String dob,
                        String homeAddress, String gender, String latitude, String longitude,String contactAddress,
                        String phoneNumber, String email,
                        String bloodgrp, String marital, String children, String disability,
                        String schoolStatus, String education, String householdType,String sizeHousehold, String imageUrl,
                        String dateAdded, String uniqueId, String accountType){

        this._fname=fname;
        this._mname=mname;
        this._lname=lname;
        this._dob=dob;
        this._homeAddress=homeAddress;
        this._gender=gender;
        this._latitude = latitude;
        this._longitude = longitude;

        this._contactAddress = contactAddress;
        this._phoneNumber=phoneNumber;
        this._email=email;
        this._blood = bloodgrp;
        this._marital = marital;


        this._children=children;
        this._disablity=disability;
        this._schoolStatus=schoolStatus;
        this._education=education;
        this._houseHoldType=householdType;
        this._sizeHousehold=sizeHousehold;

        this._imageUrl=imageUrl;
        this._dateAdded=dateAdded;
        this._uniqueId=uniqueId;
        this._accountType=accountType;

    }

    public int get_userId() {
        return _userId;
    }

    public void set_userId(int _userId) {
        this._userId = _userId;
    }

    public String get_fname() {
        return _fname;
    }

    public void set_fname(String _fname) {
        this._fname = _fname;
    }

    public String get_mname() {
        return _mname;
    }

    public void set_mname(String _mname) {
        this._mname = _mname;
    }

    public String get_lname() {
        return _lname;
    }

    public void set_lname(String _lname) {
        this._lname = _lname;
    }

    public String get_dob() {
        return _dob;
    }

    public void set_dob(String _dob) {
        this._dob = _dob;
    }

    public String get_homeAddress() {
        return _homeAddress;
    }

    public void set_homeAddress(String _homeAddress) {
        this._homeAddress = _homeAddress;
    }

    public String get_gender() {
        return _gender;
    }

    public void set_gender(String _gender) {
        this._gender = _gender;
    }

    public String get_phoneNumber() {
        return _phoneNumber;
    }

    public void set_phoneNumber(String _phoneNumber) {
        this._phoneNumber = _phoneNumber;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_marital() {
        return _marital;
    }

    public void set_marital(String _marital) {
        this._marital = _marital;
    }

    public String get_children() {
        return _children;
    }

    public void set_children(String _children) {
        this._children = _children;
    }

    public String get_disablity() {
        return _disablity;
    }

    public void set_disablity(String _disablity) {
        this._disablity = _disablity;
    }

    public String get_schoolStatus() {
        return _schoolStatus;
    }

    public void set_schoolStatus(String _schoolStatus) {
        this._schoolStatus = _schoolStatus;
    }

    public String get_education() {
        return _education;
    }

    public void set_education(String _education) {
        this._education = _education;
    }

    public String get_houseHoldType() {
        return _houseHoldType;
    }

    public void set_houseHoldType(String _houseHoldType) {
        this._houseHoldType = _houseHoldType;
    }

    public String get_sizeHousehold() {
        return _sizeHousehold;
    }

    public void set_sizeHousehold(String _sizeHousehold) {
        this._sizeHousehold = _sizeHousehold;
    }

    public String get_imageUrl() {
        return _imageUrl;
    }

    public void set_imageUrl(String _imageUrl) {
        this._imageUrl = _imageUrl;
    }

    public String get_dateAdded() {
        return _dateAdded;
    }

    public void set_dateAdded(String _dateAdded) {
        this._dateAdded = _dateAdded;
    }

    public String get_uniqueId() {
        return _uniqueId;
    }

    public void set_uniqueId(String _uniqueId) {
        this._uniqueId = _uniqueId;
    }

    public String get_accountType() {
        return _accountType;
    }

    public void set_accountType(String _accountType) {
        this._accountType = _accountType;
    }

    public String get_latitude() {
        return _latitude;
    }

    public void set_latitude(String _latitude) {
        this._latitude = _latitude;
    }

    public String get_longitude() {
        return _longitude;
    }

    public void set_longitude(String _longitude) {
        this._longitude = _longitude;
    }

    public String get_contactAddress() {
        return _contactAddress;
    }

    public void set_contactAddress(String _contactAddress) {
        this._contactAddress = _contactAddress;
    }

    public String get_blood() {
        return _blood;
    }

    public void set_blood(String _blood) {
        this._blood = _blood;
    }
}
