package samdwise.justjava.com.biometricmobile;

/**
 * Created by anochirionye.emilia on 3/9/2016.
 */
public class FingerprintPpty {
    int _fRecordId;
    String _fUserId;
    String _fUniqueId;
    String _fFingerPosition;
    String _fFingerImageUrl1;
    String _fFingerImageUrl2;
    String _fFingerImageUrl3;
    public FingerprintPpty() {

    }
    public FingerprintPpty(int recordId, String fUserId, String fUniqueId, String fFingerPosition,
                           String fImageUrl1, String fImageUrl2, String fImageUrl3){
        this._fRecordId=recordId;
        this._fUserId=fUserId;
        this._fUniqueId=fUniqueId;
        this._fFingerPosition=fFingerPosition;
        this._fFingerImageUrl1=fImageUrl1;
        this._fFingerImageUrl2=fImageUrl2;
        this._fFingerImageUrl3=fImageUrl3;

    }

    public FingerprintPpty(String fUserId, String fUniqueId, String fFingerPosition,
                           String fImageUrl1, String fImageUrl2, String fImageUrl3){
        this._fUserId=fUserId;
        this._fUniqueId=fUniqueId;
        this._fFingerPosition=fFingerPosition;
        this._fFingerImageUrl1=fImageUrl1;
        this._fFingerImageUrl2=fImageUrl2;
        this._fFingerImageUrl3=fImageUrl3;
    }

    public int get_fRecordId() {
        return _fRecordId;
    }

    public void set_fRecordId(int _fRecordId) {
        this._fRecordId = _fRecordId;
    }

    public String get_fUserId() {
        return _fUserId;
    }

    public void set_fUserId(String _fUserId) {
        this._fUserId = _fUserId;
    }

    public String get_fUniqueId() {
        return _fUniqueId;
    }

    public void set_fUniqueId(String _fUniqueId) {
        this._fUniqueId = _fUniqueId;
    }

    public String get_fFingerPosition() {
        return _fFingerPosition;
    }

    public void set_fFingerPosition(String _fFingerPosition) {
        this._fFingerPosition = _fFingerPosition;
    }
    public String get_fFingerImageUrl1() {
        return _fFingerImageUrl1;
    }
    public void set_fFingerImageUrl1(String _fFingerImageUrl1) {
        this._fFingerImageUrl1 = _fFingerImageUrl1;
    }
    public String get_fFingerImageUrl2() {
        return _fFingerImageUrl2;
    }
    public void set_fFingerImageUrl2(String _fFingerImageUrl2) {
        this._fFingerImageUrl2 = _fFingerImageUrl2;
    }
    public String get_fFingerImageUrl3() {
        return _fFingerImageUrl3;
    }
    public void set_fFingerImageUrl3(String _fFingerImageUrl3) {
        this._fFingerImageUrl3 = _fFingerImageUrl3;
    }




}
