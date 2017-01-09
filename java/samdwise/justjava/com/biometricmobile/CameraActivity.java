package samdwise.justjava.com.biometricmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by anochirionye.emilia on 3/9/2016.
 */
public class CameraActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.cscsnigeriaplc.cscsbiometrics.MESSAGE";
    public final static String USER_ID = "com.cscsnigeriaplc.cscsbiometrics.USERID";
    private static final int ACTION_TAKE_PHOTO_B = 1;

    // Button b;
    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    private ImageView mImageView;
    private TextView mTextView;
    // private TextView mTextView2;
    // private TextView mTextView3;
    private Bitmap mImageBitmap;
    private Button finish;

    private String mCurrentPhotoPath;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    // ProgressBar pg;
    private int _id;
    private DatabaseHandler _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_camera);
        mImageView = (ImageView) findViewById(R.id.imageView1);
        // pg = (ProgressBar) findViewById(R.id.progressBar1);
        mTextView = (TextView) findViewById(R.id.textView1);
        // mTextView2 = (TextView) findViewById(R.id.textView2);
        // mTextView3 = (TextView) findViewById(R.id.textView3);

        mImageBitmap = null;

        Button picBtn = (Button) findViewById(R.id.captureButton);
        setBtnListenerOrDisable(picBtn, mTakePicOnClickListener,
                MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }

        finish = (Button) findViewById(R.id.ButtonFinish);
        finish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NextPage(v);
            }
        });

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            _id = (Integer) b.get("com.cscsnigeriaplc.cscsbiometrics.USERID");

        }
        _db = new DatabaseHandler(this);

    }

    String _imageUrl;

    private void NextPage(View v) {
        if (_imageUrl != null && !_imageUrl.isEmpty()) {
            InvestorPpty pp = _db.getInvestor(_id);


            InvestorPpty ppNew = new InvestorPpty(pp.get_userId(),
                    pp.get_fname(), pp.get_mname(), pp.get_lname(),
                    pp.get_dob(), pp.get_homeAddress(), pp.get_gender(),pp.get_latitude(),pp.get_longitude(),pp.get_contactAddress(),
                    pp.get_phoneNumber(), pp.get_email(), pp.get_blood(),
                    pp.get_marital(),pp.get_children(),pp.get_disablity(),pp.get_schoolStatus(),pp.get_education(),
                    pp.get_houseHoldType(),pp.get_sizeHousehold(),_imageUrl, pp.get_dateAdded(),
                    pp.get_uniqueId(), pp.get_accountType());
            int rows = _db.updateContact(ppNew);


            Intent intent =new Intent(this, ViewUserDetails.class);
            //String message = "From Login";

            //intent.putExtra(EXTRA_MESSAGE, message);
            intent.putExtra(USER_ID,pp.get_userId());
            startActivity(intent);
            //Intent myIntent = new Intent(v.getContext(), homeActivity.class);

            //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // clear
            // back
            // stack
            //startActivity(myIntent);
            finish();
        } else {
            showAlert("You must capture Investor Photograph");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    private String getAlbumName() {
        return getString(R.string.album_name);
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory
                    .getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name),
                    "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX,
                albumF);
        return imageF;
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
        mImageView.setImageBitmap(bitmap);
        mTextView.setText(mCurrentPhotoPath);

        // SendImage(mCurrentPhotoPath);

        // AsyncCallWS task = new AsyncCallWS();
        // Call execute
        // task.execute();
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(
                "android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        switch (actionCode) {
            case ACTION_TAKE_PHOTO_B:
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(f));
                    _imageUrl = mCurrentPhotoPath;
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        } // switch

        startActivityForResult(takePictureIntent, actionCode);
    }

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
            mCurrentPhotoPath = null;
        }

    }

    OnClickListener mTakePicOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTION_TAKE_PHOTO_B: {
                if (resultCode == RESULT_OK) {
                    handleBigCameraPhoto();
                }
                break;
            } // ACTION_TAKE_PHOTO_B

            // ACTION_TAKE_VIDEO
        } // switch
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);

        outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY,
                (mImageBitmap != null));

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);

        mImageView.setImageBitmap(mImageBitmap);
        mImageView
                .setVisibility(savedInstanceState
                        .getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ? View.VISIBLE
                        : View.INVISIBLE);

    }

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void setBtnListenerOrDisable(Button btn,
                                         OnClickListener onClickListener, String intentName) {
        if (isIntentAvailable(this, intentName)) {
            btn.setOnClickListener(onClickListener);
        } else {
            btn.setText(getText(R.string.cannot).toString() + " "
                    + btn.getText());
            btn.setClickable(false);
        }
    }

    private void showAlert(String messg) {
        //
        Toast.makeText(getApplicationContext(), messg, Toast.LENGTH_LONG)
                .show();
    }

}
