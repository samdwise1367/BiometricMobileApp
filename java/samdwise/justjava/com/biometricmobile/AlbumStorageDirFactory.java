package samdwise.justjava.com.biometricmobile;

import java.io.File;


/**
 * Created by anochirionye.emilia on 3/9/2016.
 */
abstract class AlbumStorageDirFactory {
    public abstract File getAlbumStorageDir(String albumName);
}
