package dataobject;

import android.net.Uri;

import java.io.FileInputStream;
import java.io.InputStream;

public class ListingImage {

    private FileInputStream imageStream;
    private int id = -1;
    private Uri path;

    public void Image(){}

    public void setId(int i){

        if(i >= 0){

            id = i;
        }
    }

    public void setUri(Uri p){

        path = p;
    }

    public void setImageStream(FileInputStream theImageStream){

        if(theImageStream != null){

            imageStream = theImageStream;
        }
    }

    public int getId(){

        return id;
    }

    public Uri getUri(){

        return path;
    }

    public FileInputStream getImageStream(){

        return imageStream;
    }
}
