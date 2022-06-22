package com.example.rentpe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class HouseImages extends AppCompatActivity {

    private Button addimages,submit,next;
    private TextView alert,back;
    private ProgressDialog pd;
    private int upload_count = 0;
    private Uri imageuri;
    ArrayList<Uri> ImageList = new ArrayList<Uri>();
    ArrayList urlStrings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_images);
        addimages=findViewById(R.id.addimages);
        submit=findViewById(R.id.submitimages);
        next=findViewById(R.id.gonext);
        alert=findViewById(R.id.alert);
        back=findViewById(R.id.goback);
        next.setVisibility(View.GONE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HouseImages.this, HomeActivity.class);
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HouseImages.this, HouseDetails.class);
                startActivity(intent);
            }
        });
        addimages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, 1);
            }
        });
        pd = new ProgressDialog(HouseImages.this);
        pd.setMessage("Uploading Images please Wait.........!!!!!!");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlStrings = new ArrayList<>();
                pd.show();
                StorageReference ImageFolder = FirebaseStorage.getInstance().getReference("Houses").child("ImageFolder");
                for (upload_count = 0; upload_count < ImageList.size(); upload_count++) {
                    Uri IndividualImage = ImageList.get(upload_count);
                    final StorageReference ImageName = ImageFolder.child("Images" + IndividualImage.getLastPathSegment());
                    ImageName.putFile(IndividualImage).addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    ImageName.getDownloadUrl().addOnSuccessListener(
                                            new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    urlStrings.add(String.valueOf(uri));
                                                    if (urlStrings.size() == ImageList.size()){
                                                        storeLink(urlStrings);
                                                    }
                                                }
                                            }
                                    );
                                }
                            }
                    );
                }
            }
            });
        }
    private void storeLink(ArrayList<String> urlStrings) {

        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i <urlStrings.size() ; i++) {
            hashMap.put("ImgLink" + i, urlStrings.get(i));
        }
        for (int i =urlStrings.size(); i < 10 ; i++){
            hashMap.put("ImgLink" + i, "");
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HouseDetails");
        Bundle Incomingmessages= getIntent().getExtras();
        if(Incomingmessages!=null) {
            String addressDetails=Incomingmessages.getString("Address");
            String desc=Incomingmessages.getString("Description");
            String tenantName=Incomingmessages.getString("NameTenant");
            String landlordName = Incomingmessages.getString("NameLandlord");
            String rentDetails=Incomingmessages.getString("Rent");
            String getUIDofL =Incomingmessages.getString("UIDofL");
            String getUIDofT =Incomingmessages.getString("UIDofT");
            String getPhoneTenant=Incomingmessages.getString("PhoneTenant");
            String getPhoneLandlord=Incomingmessages.getString("PhoneLandlord");
            hashMap.put("UserIDofLandlord", getUIDofL);
            hashMap.put("UserIDofTenant", getUIDofT);
            hashMap.put("Address", addressDetails);
            hashMap.put("Description", desc);
            hashMap.put("NameOfTheLandlord", landlordName);
            hashMap.put("NameOfTheTenant", tenantName);
            hashMap.put("Rent", rentDetails);
            hashMap.put("PhoneLandlord",getPhoneLandlord);
            hashMap.put("PhoneTenant",getPhoneTenant);
            hashMap.put("NoOfImages", Integer.toString(ImageList.size()));
            String newUIDL = getUIDofL.substring(20);
            String newUIDT=getUIDofT.substring(20);
            String newBranch=newUIDL+" + "+newUIDT;
            databaseReference.child(newBranch).setValue(hashMap)
                    .addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(HouseImages.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                                        next.setVisibility(View.VISIBLE);
                                        submit.setVisibility(View.GONE);
                                    }
                                }
                            }
                    ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(HouseImages.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            pd.dismiss();
            addimages.setVisibility(View.GONE);
            ImageList.clear();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {


                if (data.getClipData() != null) {

                    int countClipData = data.getClipData().getItemCount();
                    int currentImageSlect = 0;

                    while (currentImageSlect < countClipData) {

                        imageuri = data.getClipData().getItemAt(currentImageSlect).getUri();
                        ImageList.add(imageuri);
                        currentImageSlect = currentImageSlect + 1;
                    }
                    alert.setText("You have selected " + ImageList.size() + " Images");
                    addimages.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(this, "Please Select Multiple Images", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}