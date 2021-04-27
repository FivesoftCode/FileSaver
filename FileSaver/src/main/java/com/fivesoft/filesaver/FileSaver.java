package com.fivesoft.filesaver;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;


@SuppressWarnings("unused")
public class FileSaver {

    private final Activity activity;

    private static String name;
    private static String type = "*/*";
    private static byte[] file;
    private static OnResultsListener listener;

    private static final int SAVE_REQUEST_CODE = 42;

    public static final String ALL = "*/*";
    public static final String IMAGE = "image/*";
    public static final String AUDIO = "audio/*";
    public static final String VIDEO = "video/*";
    public static final String TEXT = "text/*";


    private FileSaver(Activity activity){
        this.activity = activity;
    }

    /**
     * Creates new FileSaver instance.
     * @param activity The running activity.
     * @return A new FileSaver instance.
     */

    public static FileSaver from(Activity activity){
        return new FileSaver(activity);
    }

    /**
     * Sets the file.
     * @param bytes File bytes.
     * @return Current FileSaver instance.
     */

    public FileSaver setFile(@NonNull byte[] bytes){
        FileSaver.file = bytes;
        return this;
    }

    /**
     * Sets the type of the file.
     * <br>
     * For more info about file types see <a href="http://www.iana.org/assignments/media-types/media-types.xhtml">this</a>.
     *
     * @param type The type of the file.
     * @see #ALL
     * @see #IMAGE
     * @see #VIDEO
     * @see #AUDIO
     * @see #TEXT
     * @return Current FileSaver instance.
     */

    public FileSaver setType(@NonNull String type){
        FileSaver.type = type;
        return this;
    }

    /**
     * Sets the interface called when saving file is finished.
     * (with success or failure)
     * @param listener The listener.
     * @return Current FileSaver instance.
     */

    public FileSaver setListener(OnResultsListener listener){
        FileSaver.listener = listener;
        return this;
    }

    /**
     * Sets the file name. This field is required.
     * @param name File name without extension. (ex.: "helloWorld")
     * @param extension File extension without dot. (ex.: "txt")
     * @return Current FileSaver instance.
     * @see FileSaver#setName(String)
     */

    public FileSaver setName(@NonNull String name, @NonNull String extension){
        FileSaver.name = name.concat(".").concat(extension);
        return this;
    }

    /**
     * Sets the file name. This field is required.
     * @param name File name with extension. (ex.: "helloWorld.txt")
     * @return Current FileSaver instance.
     * @see FileSaver#setName(String, String)
     */

    public FileSaver setName(@NonNull String name){
        FileSaver.name = name;
        return this;
    }

    /**
     * Opens android Files api and lets user save the file
     * in a given location.
     */

    public void save(){
        activity.startActivity(new Intent(activity, FileSaverActivity.class));
    }

    public static class FileSaverActivity extends Activity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(type);
            intent.putExtra(Intent.EXTRA_TITLE, name);
            startActivityForResult(intent, SAVE_REQUEST_CODE);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
            Uri currentUri;
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SAVE_REQUEST_CODE) {
                    if (resultData != null) {
                        currentUri = resultData.getData();
                        writeFileContent(this, file, currentUri);
                    } else {
                        if(listener != null)
                            listener.onResults(null, OnResultsListener.CANCELED_BY_USER);
                    }
                }
            } else {
                if(listener != null)
                    listener.onResults(null, OnResultsListener.CANCELED_BY_USER);
            }
            finish();
        }

        @Override
        protected void onResume() {
            super.onResume();
        }

        private void writeFileContent(Activity activity, byte[] bytes, Uri uri) {

            if(bytes == null){
                if(listener != null)
                    listener.onResults(uri, OnResultsListener.ERROR_OCCURRED);
                return;
            }

            try{
                ParcelFileDescriptor pfd =
                        activity.getContentResolver().
                                openFileDescriptor(uri, "w");

                FileOutputStream fileOutputStream =
                        new FileOutputStream(
                                pfd.getFileDescriptor());

                fileOutputStream.write(bytes);

                fileOutputStream.close();
                pfd.close();
                if(listener != null)
                    listener.onResults(uri, OnResultsListener.OK);
            } catch (IOException e) {
                e.printStackTrace();
                if(listener != null)
                    listener.onResults(uri, OnResultsListener.ERROR_OCCURRED);
            }
        }
    }

    public interface OnResultsListener {

        /**
         * User saves file with success.
         */
        int OK = 0;
        /**
         * User exits android Files api and cancels saving file.
         */
        int CANCELED_BY_USER = -1;
        /**
         * Error occurs.
         */
        int ERROR_OCCURRED = -2;

        /**
         * Interface called when saving file is finished.
         * @param fileLocation Location which user choose to save file at.
         * @param resCode Result code.
         * @see #OK
         * @see #CANCELED_BY_USER
         * @see #ERROR_OCCURRED
         */

        void onResults(Uri fileLocation, int resCode);
    }

}
