package com.example.registrarse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.UploadTask;
import com.providers.AuthProvider;
import com.providers.ImageProvider;
import com.providers.PostProvider;
import com.utils.FileUtil;
import com.utils.ViewedMessageHelper;

import java.io.File;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class PostActivity extends AppCompatActivity {
    ImageView mImageViewPost1;
    ImageView mImageViewPost2;
    File mImageFile;
    File mImageFile2;
    Button mButtonPost;
    TextInputEditText mTextInputTitle;
    TextInputEditText mTextInputDescription;
    ImageView mImageViewPC;
    ImageView mImageViewPS4;
    ImageView mImageViewXBOX;
    ImageView mImageViewNitendo;
    CircleImageView mCircleImageBack;
    ImageProvider mImageProvider;
    PostProvider mPostProvider;
    com.providers.AuthProvider mAuthProvider;
    TextView mTextViewCategory;
    private final int GALLERY_REQUEST_CODE = 1;
    private final int GALLERY_REQUEST_CODE_2 = 2;
    private final int PHOTO_REQUEST_CODE = 3;
    private final int PHOTO_REQUEST_CODE_2 = 4;
    String mCategory = "";
    String mTittle= "";
    String mDescription= "";
    AlertDialog mDialog;
    // FOTO 1
    String mAbsolutePhotoPath;
    String mPhotoPath;
    File mPhotoFile;

    // FOTO 2
    String mAbsolutePhotoPath2;
    String mPhotoPath2;
    File mPhotoFile2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mButtonPost = findViewById(R.id.btnPost);
        mImageViewPost1 = findViewById(R.id.imageViewPost1);
        mImageViewPost2 = findViewById(R.id.imageViewPost2);
        mImageProvider = new ImageProvider();
        mTextInputTitle = findViewById(R.id.textInputVideoGame);
        mTextInputDescription = findViewById(R.id.textInputDescription);
        mImageViewPC = findViewById(R.id.imageViewPc);
        mImageViewPS4 = findViewById(R.id.imageViewPS4);
        mImageViewXBOX = findViewById(R.id.imageViewXbox);
        mImageViewNitendo = findViewById(R.id.imageViewNintendo);
        mPostProvider = new PostProvider();
        mTextViewCategory = findViewById(R.id.textViewCategory);
        mCircleImageBack = findViewById(R.id.circleImageBack);
        mAuthProvider = new AuthProvider();

        mCircleImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento")
                .setCancelable(false).build();

        mImageViewPost1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(GALLERY_REQUEST_CODE);
            }
        });

        mImageViewPost2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(GALLERY_REQUEST_CODE_2);
            }
        });

        mImageViewPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory = "ADOPTAR";
                mTextViewCategory.setText(mCategory);
            }
        });

        mImageViewPS4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory = "LIMPIEZA";
                mTextViewCategory.setText(mCategory);
            }
        });

        mImageViewXBOX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory = "jUEGOS";
                mTextViewCategory.setText(mCategory);
            }
        });

        mImageViewNitendo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory = "ENTRETENER";
                mTextViewCategory.setText(mCategory);
            }
        });

        mButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPost();
            }
        });

    }
    private void clickPost(){
        mTittle = mTextInputTitle.getText().toString();
        mDescription = mTextInputDescription.getText().toString();
        if(!mTittle.isEmpty() && !mDescription.isEmpty() && !mCategory.isEmpty()){
            if(mTittle.length()>=2 && mTittle.length()<=45){
                if(mDescription.length()>=10 && mDescription.length()<=100){
                    if(mImageFile != null){
                        saveImage();
                    }
                    else{
                        Toast.makeText(this,"Debes seleccionar dos imagenes para crear la publicación",Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(this,"La descripción debe tener entre 10-100 caracteres",Toast.LENGTH_LONG).show();
                }
            } else{
                Toast.makeText(this,"El nombre debe tener entre 2-45 caracteres",Toast.LENGTH_LONG).show();
            }
        } else if (mTittle.isEmpty() && !mDescription.isEmpty() && !mCategory.isEmpty()){
            Toast.makeText(this,"Ingresar el nonbre de la mascota",Toast.LENGTH_LONG).show();
        } else if (!mTittle.isEmpty() && mDescription.isEmpty() && !mCategory.isEmpty()){
            Toast.makeText(this,"Debe rellenar la descripcion antes de publicar",Toast.LENGTH_LONG).show();
        } else if (!mTittle.isEmpty() && !mDescription.isEmpty() && mCategory.isEmpty()){
            Toast.makeText(this,"Elija la categoria de la publicación",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Completa los campos para publicar",Toast.LENGTH_LONG).show();
        }

    }
    public void openGallery(int requestCode) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, requestCode);
    }

    public void saveImage(){
        mDialog.show();
        mImageProvider.save(PostActivity.this,mImageFile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String url = uri.toString();

                            mImageProvider.save(PostActivity.this,mImageFile2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> taskImage2) {
                                    if(taskImage2.isSuccessful()){
                                        mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri2) {
                                                String url2 = uri2.toString();
                                                Post post = new Post();
                                                post.setImage1(url);
                                                post.setImage2(url2);
                                                post.setTittle(mTittle.toLowerCase());
                                                post.setDescription(mDescription);
                                                post.setCategory(mCategory);
                                                post.setIdUser(mAuthProvider.getUid());
                                                post.setTimestamp(new Date().getTime());
                                                mPostProvider.save(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> taskSave) {
                                                        mDialog.dismiss();
                                                        if(taskSave.isSuccessful()){
                                                            clearForm();
                                                            Toast.makeText(PostActivity.this,"La informacion se almaceno correctamente",Toast.LENGTH_SHORT).show();
                                                        }
                                                        else{
                                                            Toast.makeText(PostActivity.this,"No se pudo almacenar la informacion",Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                    else{
                                        mDialog.dismiss();
                                        Toast.makeText(PostActivity.this,"El peso de la 2° foto debe estar entre 5 KB - 4MB",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }
                    });
                    Toast.makeText(PostActivity.this,"La imagen se subio correctamente",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(PostActivity.this,"El peso de la foto debe estar entre 5 KB - 4MB",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void clearForm(){
        mTextInputTitle.setText("");
        mTextInputDescription.setText("");
        mTextViewCategory.setText("");
        mImageViewPost1.setImageResource(R.drawable.ic_camera);
        mImageViewPost2.setImageResource(R.drawable.ic_baseline_camera_24);
        mTittle = "";
        mDescription = "";
        mCategory = "";
        mImageFile = null;
        mImageFile2 = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){
                try {
                    mImageFile = FileUtil.from(this,data.getData());
                    mImageViewPost1.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));

                } catch (Exception e){
                    Log.d("ERROR","Se produjo un error"+e.getMessage());
                    Toast.makeText(this,"Se produjo un error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            if (requestCode == GALLERY_REQUEST_CODE_2 && resultCode == RESULT_OK) {
                try {
                    //mPhotoFile2 = null;
                    mImageFile2 = FileUtil.from(this, data.getData());
                    mImageViewPost2.setImageBitmap(BitmapFactory.decodeFile(mImageFile2.getAbsolutePath()));
                } catch(Exception e) {
                    Log.d("ERROR", "Se produjo un error " + e.getMessage());
                    Toast.makeText(this, "Se produjo un error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ViewedMessageHelper.updateOnline(true, PostActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ViewedMessageHelper.updateOnline(false, PostActivity.this);
    }
}