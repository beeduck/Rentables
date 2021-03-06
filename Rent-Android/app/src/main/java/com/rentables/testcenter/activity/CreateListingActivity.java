package com.rentables.testcenter.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.rentables.testcenter.R;
import com.rentables.testcenter.dialog.CreatePostDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import dataobject.CreateListing;
import dataobject.ListingImage;
import server.NotifyingThread;
import server.ServerConnection;
import server.ThreadListener;

public class CreateListingActivity extends AppCompatActivity implements ThreadListener {

    private Thread currentThread = null;
    private CreateListing currentListing = new CreateListing();
    private ProgressDialog listingCreationProgress;

    private boolean imageSet = false;
    private String task;
    private static final int REQUEST_CAMERA = 0;
    private static final int SELECT_FILE = 1;
    private ImageView img1, img2, img3;
    private TextView newText1, newText2,newText3;
    private ImageButton btnShow1, btnShow2, btnShow3;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        img1 = (ImageView)findViewById(R.id.target_image1);
        img2 = (ImageView)findViewById(R.id.target_image2);
        img3 = (ImageView)findViewById(R.id.target_image3);
        newText1 = (TextView)findViewById(R.id.image_view_text_overlay1);
        newText2 = (TextView)findViewById(R.id.image_view_text_overlay2);
        newText3 = (TextView)findViewById(R.id.image_view_text_overlay3);
        btnShow1 = (ImageButton)findViewById(R.id.image_delete_button1);
        btnShow2 = (ImageButton)findViewById(R.id.image_delete_button2);
        btnShow3 = (ImageButton)findViewById(R.id.image_delete_button3);

        setupToolbar();
        setTextForSpinners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.overflow_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.overflow_settings_option:
                toSettingsActivity();
                return true;
            case R.id.overflow_account_option:
                System.out.println("NICE");
                return true;
            case R.id.overflow_logout_option:
                userLogout();
                return true;
            default:
                return true;

        }
    }

    @Override
    public void notifyOfThreadCompletion(NotifyingThread notifyThread) {

        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                listingCreationProgress.dismiss();
                showPostCreatedDialog();
            }
        });
    }

    private void toSettingsActivity(){

        Intent settingsIntent = new Intent();
        settingsIntent.setClass(this, SettingsActivity.class);

        startActivity(settingsIntent);
    }

    private void userLogout(){

        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setupToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Rentables");
        setSupportActionBar(toolbar);

    }

    private void setTextForSpinners(){

        Spinner perSpinner = (Spinner) findViewById(R.id.create_listing_per_spinner);

        ArrayAdapter<CharSequence> perAdapter = ArrayAdapter.createFromResource(this, R.array.RentalTime, R.layout.spinner_item);

        perAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        perSpinner.setAdapter(perAdapter);

    }

    public void createListing(View view){

        if(currentThread == null) {

            //Retrieve all data from the form.
            EditText title = (EditText) findViewById(R.id.create_listing_title_edit_text);
            EditText description = (EditText) findViewById(R.id.create_listing_description_edit_text);
            EditText price = (EditText) findViewById(R.id.create_listing_price_edit_text);
            Spinner per = (Spinner) findViewById(R.id.create_listing_per_spinner);

            String titleText = title.getText().toString().trim();
            String descriptionText = description.getText().toString().trim();
            int priceCategoryId = getCorrectPriceCategoryId(per.getSelectedItem().toString());
            String priceText = price.getText().toString().trim();

            if(checkAllInputs(titleText, descriptionText, priceText, priceCategoryId)){

                //If the form checks out proceed with creating the Listing.
                showListingCreationProgressDialog();

                currentListing.setTitle(titleText);
                currentListing.setDescription(descriptionText);
                currentListing.setPriceCategoryId(priceCategoryId);
                currentListing.setPrice(Double.parseDouble(priceText));

                //Create the ServerConnection object and start the thread.
                ServerConnection<CreateListing> connection = new ServerConnection<>(currentListing);
                connection.addListener(this);

                currentThread = new Thread(connection);
                currentThread.start();

            }else{

                hideKeyboard();
            }
        }
    }

    private boolean checkAllInputs(String titleText, String descriptionText, String currentPrice, int priceCategoryId){

        EditText title = (EditText) findViewById(R.id.create_listing_title_edit_text);
        EditText description = (EditText) findViewById(R.id.create_listing_description_edit_text);
        EditText price = (EditText) findViewById(R.id.create_listing_price_edit_text);
        TextView addImage = (TextView) findViewById(R.id.image_view_text_overlay1);
        Spinner per = (Spinner) findViewById(R.id.create_listing_per_spinner);

        boolean pass = true;

        if(titleText.length() == 0){

            title.setError("Please Enter a Title.");
            pass = false;
        }

        if(descriptionText.length() == 0){

            description.setError("Please Enter a Description.");
            pass = false;
        }

        if(currentPrice.length() == 0){

            price.setError("Please Enter a Price.");
            pass = false;
        }

        if(!imageSet){

            addImage.setText("Please Select a Photo.");
            addImage.setTextColor(getResources().getColor(R.color.red));
            pass = false;
        }

        return pass;
    }

    private void showListingCreationProgressDialog(){

        listingCreationProgress = new ProgressDialog(CreateListingActivity.this, ProgressDialog.STYLE_SPINNER);
        listingCreationProgress.setTitle("Creating Listing...");
        listingCreationProgress.setMessage("please wait");
        listingCreationProgress.show();
    }

    private void showPostCreatedDialog(){

        FragmentManager manager = getSupportFragmentManager();
        CreatePostDialog successDialog = new CreatePostDialog();
        successDialog.show(manager, "dialog_create_post_successful");
    }

    public void onPostCreation(View view){

        Intent homeIntent = new Intent();
        homeIntent.setClass(this, HomeActivity.class);

        startActivity(homeIntent);
    }

    public int getCorrectPriceCategoryId(String priceCategoryId){

        switch (priceCategoryId.toLowerCase()){

            case "hour":
                return 1;
            case "day":
                return 2;
            case "week":
                return 3;
            case "month":
                return 4;
            default:
                return 1;
        }
    }

    private void hideKeyboard(){

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if(imm != null){

            imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public void selectImage(View view) {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(CreateListingActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(CreateListingActivity.this);

                if (items[item].equals("Take Photo")) {
                    task="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    task="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(task.equals("Take Photo"))
                        cameraIntent();
                    else if(task.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {

        //Get input stream and then pass it to the output stream

        try {

            FileInputStream fileStream = (FileInputStream) getApplicationContext().getContentResolver().openInputStream(data.getData());
            ListingImage image = currentListing.getListingImage();
            image.setImageStream(fileStream);
            image.setUri(data.getData());
            imageSet = true;


        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }


        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (!hasImage(img1)) {
            img1.setImageBitmap(bm);
            newText1.setText("");
            btnShow1.setVisibility(View.VISIBLE);
        } else if (!hasImage(img2)) {
            img2.setImageBitmap(bm);
            newText2.setText("");
            btnShow2.setVisibility(View.VISIBLE);
        } else {
            img3.setImageBitmap(bm);
            newText3.setText("");
            btnShow3.setVisibility(View.VISIBLE);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (!hasImage(img1)) {
            img1.setImageBitmap(thumbnail);
            newText1.setText("");
            btnShow1.setVisibility(View.VISIBLE);
        } else if (!hasImage(img2)) {
            img2.setImageBitmap(thumbnail);
            newText2.setText("");
            btnShow2.setVisibility(View.VISIBLE);
        } else {
            img3.setImageBitmap(thumbnail);
            newText3.setText("");
            btnShow3.setVisibility(View.VISIBLE);
        }
    }

    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }

        return hasImage;
    }

    public void removeImage(View view) {

        switch(view.getId()) {
            case R.id.image_delete_button1:
                img1.setImageBitmap(null);
                newText1.setText(R.string.create_listing_no_photo);
                btnShow1.setVisibility(View.INVISIBLE);
                break;
            case R.id.image_delete_button2:
                img2.setImageBitmap(null);
                newText2.setText(R.string.create_listing_no_photo);
                btnShow2.setVisibility(View.INVISIBLE);
                break;
            case R.id.image_delete_button3:
                img3.setImageBitmap(null);
                newText3.setText(R.string.create_listing_no_photo);
                btnShow3.setVisibility(View.INVISIBLE);
                break;
            default:
                throw new RuntimeException("Unknown button ID");

        }
    }
}
