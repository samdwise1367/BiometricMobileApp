package samdwise.justjava.com.biometricmobile;

import java.io.File;

import android.os.Environment;


/**
 * Created by anochirionye.emilia on 3/9/2016.
 */
public final class BaseAlbumDirFactory extends AlbumStorageDirFactory {

    // Standard storage location for digital camera files
    private static final String CAMERA_DIR = "/dcim/";

    @Override
    public File getAlbumStorageDir(String albumName) {
        return new File (
                Environment.getExternalStorageDirectory()
                        + CAMERA_DIR
                        + albumName
        );
    }
}
