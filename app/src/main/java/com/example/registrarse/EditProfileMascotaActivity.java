package com.example.registrarse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.registrarse.User;
import com.providers.AuthProvider;
import com.providers.ImageProvider;
import com.providers.UsersProvider;
import com.utils.FileUtil;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.UploadTask;
import com.utils.ViewedMessageHelper;
import com.squareup.picasso.Picasso;
import com.example.registrarse.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class EditProfileMascotaActivity extends AppCompatActivity {

    CircleImageView mCircleImageViewBack;
    ImageView mImageViewCover;
    TextInputEditText mTextInputUsername;
    TextInputEditText mTextInputPhone;
    Button mButtonEditProfile;

    AlertDialog.Builder mBuilderSelector;
    CharSequence options[];
    private final int GALLERY_REQUEST_CODE_PROFILE = 1;
    private final int GALLERY_REQUEST_CODE_COVER = 2;
    private final int PHOTO_REQUEST_CODE_PROFILE = 3;
    private final int PHOTO_REQUEST_CODE_COVER = 4;

    Spinner spinner2;

    // FOTO 1
    String mAbsolutePhotoPath;
    String mPhotoPath;
    File mPhotoFile;

    // FOTO 2
    String mAbsolutePhotoPath2;
    String mPhotoPath2;
    File mPhotoFile2;

    File mImageFile;
    File mImageFile2;
    public int bandera = 0;

    String mUsername = "";
    String mPhone = "";
    String nuevo = "";

    AlertDialog mDialog;

    ImageProvider mImageProvider;
    UsersProvider mUsersProvider;
    AuthProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_mascota);

        mCircleImageViewBack = findViewById(R.id.circleImageBack);
        mImageViewCover = findViewById(R.id.imageViewCover);
        mTextInputUsername = findViewById(R.id.textInputMascota);
        mTextInputPhone = findViewById(R.id.textInputDescriptionMascota);
        mButtonEditProfile = findViewById(R.id.btnEditProfile);

        mBuilderSelector = new AlertDialog.Builder(this);
        mBuilderSelector.setTitle("Selecciona una opcion");
        options = new CharSequence[] {"Imagen de galeria", "Tomar foto"};

        mImageProvider = new ImageProvider();
        mUsersProvider = new UsersProvider();
        mAuthProvider = new AuthProvider();

        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento")
                .setCancelable(false).build();

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> staticAdapters = ArrayAdapter.createFromResource(this, R.array.edadd, android.R.layout.simple_spinner_item);
        staticAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(staticAdapters);

        mButtonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEditProfile();
            }
        });


        mImageViewCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectOptionImage(2);
            }
        });

        mCircleImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void clickEditProfile() {
        nuevo = spinner2.getSelectedItem().toString();
        mUsername = mTextInputUsername.getText().toString();
        mPhone = mTextInputPhone.getText().toString();
        if (!mUsername.isEmpty() && !mPhone.isEmpty() && !nuevo.isEmpty()) {
            if (mUsername.length()>=2 && mUsername.length()<=45){
                if (mPhone.length()>=2 && mPhone.length()<=200){
                    if (mImageFile2 != null) {
                        saveImage(mImageFile2);
                    }
                    // TOMO LAS DOS FOTOS DE LA CAMARA
                    else if (mPhotoFile2 != null) {
                        saveImage(mPhotoFile2);
                    } else if (bandera == 2 || bandera == 0){
                        editarperfil();
                    }
                    else {
                        Toast.makeText(this, "Debes seleccionar una imagen entre 5KB - 6MB", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "La descripcion debe tener entre 2-200 caracteres", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "El nombre de la mascota debe tener entre 2-45 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else if(mUsername.equals("") && !mPhone.isEmpty()){
            if (mPhone.length()>=2 && mPhone.length()<=200){
                if (mImageFile != null) {
                    saveImagee(mImageFile);
                }
                // TOMO LAS DOS FOTOS DE LA CAMARA
                else if (mPhotoFile != null) {
                    saveImagee(mPhotoFile);
                } else if (bandera == 2 || bandera == 0){
                    editarperfill();
                }
                else {
                    Toast.makeText(this, "Debes seleccionar una imagen entre 5KB - 6MB", Toast.LENGTH_SHORT).show();
                }
            } else{
                Toast.makeText(this, "La descripcion debe tener entre 2-200 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingresar la descripción de la mascota", Toast.LENGTH_SHORT).show();
        }
    }

    private void editarperfil(){
        if(nuevo.equals("-")){
            User user = new User();
            user.setMascota(mUsername);
            user.setDescripcionmascota(mPhone);
            user.setId(mAuthProvider.getUid());
            mUsersProvider.updatessssssssss(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    mDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(EditProfileMascotaActivity.this, "La informacion se actualizo correctamente", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(EditProfileMascotaActivity.this, "La informacion no se pudo actualizar", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            User user = new User();
            user.setMascota(mUsername);
            user.setDescripcionmascota(mPhone);
            user.setEdadmascota(nuevo);
            user.setId(mAuthProvider.getUid());
            mUsersProvider.updatessssssss(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    mDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(EditProfileMascotaActivity.this, "La informacion se actualizo correctamente", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(EditProfileMascotaActivity.this, "La informacion no se pudo actualizar", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


    private void editarperfill(){
        if(nuevo.equals("-")){
        User user = new User();
        user.setDescripcionmascota(mPhone);
        user.setId(mAuthProvider.getUid());
        mUsersProvider.updatessssssssssss(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(EditProfileMascotaActivity.this, "La informacion se actualizo correctamente", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(EditProfileMascotaActivity.this, "La informacion no se pudo actualizar", Toast.LENGTH_SHORT).show();
                }
            }
        });
        } else {
            User user = new User();
            user.setDescripcionmascota(mPhone);
            user.setEdadmascota(nuevo);
            user.setId(mAuthProvider.getUid());
            mUsersProvider.updatesssssssssssss(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    mDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(EditProfileMascotaActivity.this, "La informacion se actualizo correctamente", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(EditProfileMascotaActivity.this, "La informacion no se pudo actualizar", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void saveImage(final File imageFile2) {
        mDialog.show();
                            mImageProvider.save(EditProfileMascotaActivity.this, imageFile2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> taskImage2) {
                                    if (taskImage2.isSuccessful()) {
                                        mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri2) {
                                                final String urlCover = uri2.toString();
                                                if(nuevo.equals("-")){
                                                    User user = new User();
                                                    user.setMascota(mUsername);
                                                    user.setDescripcionmascota(mPhone);
                                                    user.setImageCover(urlCover);
                                                    user.setId(mAuthProvider.getUid());
                                                    mUsersProvider.updatesssssssss(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            mDialog.dismiss();
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(EditProfileMascotaActivity.this, "La informacion se actualizo correctamente", Toast.LENGTH_SHORT).show();
                                                            }
                                                            else {
                                                                Toast.makeText(EditProfileMascotaActivity.this, "La informacion no se pudo actualizar", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                } else{
                                                    User user = new User();
                                                    user.setMascota(mUsername);
                                                    user.setDescripcionmascota(mPhone);
                                                    user.setEdadmascota(nuevo);
                                                    user.setImageCover(urlCover);
                                                    user.setId(mAuthProvider.getUid());
                                                    mUsersProvider.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            mDialog.dismiss();
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(EditProfileMascotaActivity.this, "La informacion se actualizo correctamente", Toast.LENGTH_SHORT).show();
                                                            }
                                                            else {
                                                                Toast.makeText(EditProfileMascotaActivity.this, "La informacion no se pudo actualizar", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }

                                            }
                                        });
                                    }
                                    else {
                                        mDialog.dismiss();
                                        Toast.makeText(EditProfileMascotaActivity.this, "La imagen numero 2 no se pudo guardar", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }


    private void saveImagee(final File imageFile2) {
        mDialog.show();
        mImageProvider.save(EditProfileMascotaActivity.this, imageFile2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> taskImage2) {
                if (taskImage2.isSuccessful()) {
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri2) {
                            if(nuevo.equals("-")){
                                final String urlCover = uri2.toString();
                                User user = new User();
                                user.setDescripcionmascota(mPhone);
                                user.setImageCover(urlCover);
                                user.setId(mAuthProvider.getUid());
                                mUsersProvider.updatessssssssssssss(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        mDialog.dismiss();
                                        if (task.isSuccessful()) {
                                            Toast.makeText(EditProfileMascotaActivity.this, "La informacion se actualizo correctamente", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(EditProfileMascotaActivity.this, "La informacion no se pudo actualizar", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                final String urlCover = uri2.toString();
                                User user = new User();
                                user.setDescripcionmascota(mPhone);
                                user.setImageCover(urlCover);
                                user.setEdadmascota(nuevo);
                                user.setId(mAuthProvider.getUid());
                                mUsersProvider.updatesssssssssss(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        mDialog.dismiss();
                                        if (task.isSuccessful()) {
                                            Toast.makeText(EditProfileMascotaActivity.this, "La informacion se actualizo correctamente", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(EditProfileMascotaActivity.this, "La informacion no se pudo actualizar", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
                else {
                    mDialog.dismiss();
                    Toast.makeText(EditProfileMascotaActivity.this, "La imagen numero 2 no se pudo guardar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectOptionImage(final int numberImage) {

        mBuilderSelector.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    if (numberImage == 1) {
                        openGallery(GALLERY_REQUEST_CODE_PROFILE);
                    }
                    else if (numberImage == 2) {
                        openGallery(GALLERY_REQUEST_CODE_COVER);
                    }
                }
                else if (i == 1){
                    if (numberImage == 1) {
                        takePhoto(PHOTO_REQUEST_CODE_PROFILE);
                    }
                    else if (numberImage == 2) {
                        takePhoto(PHOTO_REQUEST_CODE_COVER);
                    }
                }
            }
        });

        mBuilderSelector.show();

    }

    private void takePhoto(int requestCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createPhotoFile(requestCode);
            } catch(Exception e) {
                Toast.makeText(this, "Hubo un error con el archivo " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(EditProfileMascotaActivity.this, "com.optic.socialmediagamer", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }

    private File createPhotoFile(int requestCode) throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photoFile = File.createTempFile(
                new Date() + "_photo",
                ".jpg",
                storageDir
        );
        if (requestCode == PHOTO_REQUEST_CODE_PROFILE) {
            mPhotoPath = "file:" + photoFile.getAbsolutePath();
            mAbsolutePhotoPath = photoFile.getAbsolutePath();
        }
        else if (requestCode == PHOTO_REQUEST_CODE_COVER) {
            mPhotoPath2 = "file:" + photoFile.getAbsolutePath();
            mAbsolutePhotoPath2 = photoFile.getAbsolutePath();
        }
        return photoFile;
    }

    private void openGallery(int requestCode) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * SELECCION DE IMAGEN DESDE LA GALERIA
         */
        if(data != null){
            if (requestCode == GALLERY_REQUEST_CODE_PROFILE && resultCode == RESULT_OK) {
                try {
                    bandera = 1;
                    mPhotoFile = null;
                    mImageFile = FileUtil.from(this, data.getData());
                    //mCircleImageViewProfile.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));
                } catch(Exception e) {
                    Log.d("ERROR", "Se produjo un error " + e.getMessage());
                    Toast.makeText(this, "Se produjo un error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            if (requestCode == GALLERY_REQUEST_CODE_COVER && resultCode == RESULT_OK) {
                try {
                    bandera = 1;
                    mPhotoFile2 = null;
                    mImageFile2 = FileUtil.from(this, data.getData());
                    mImageViewCover.setImageBitmap(BitmapFactory.decodeFile(mImageFile2.getAbsolutePath()));
                } catch(Exception e) {
                    Log.d("ERROR", "Se produjo un error " + e.getMessage());
                    Toast.makeText(this, "Se produjo un error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        /**
         * SELECCION DE FOTOGRAFIA
         */
        if (requestCode == PHOTO_REQUEST_CODE_PROFILE && resultCode == RESULT_OK) {
            bandera = 1;
            mImageFile = null;
            mPhotoFile = new File(mAbsolutePhotoPath);
            //Picasso.with(EditProfileMascotaActivity.this).load(mPhotoPath).into(mCircleImageViewProfile);
        }

        /**
         * SELECCION DE FOTOGRAFIA
         */
        if (requestCode == PHOTO_REQUEST_CODE_COVER && resultCode == RESULT_OK) {
            bandera = 1;
            mImageFile2 = null;
            mPhotoFile2 = new File(mAbsolutePhotoPath2);
            Picasso.with(EditProfileMascotaActivity.this).load(mPhotoPath2).into(mImageViewCover);
        }
        } else{
            bandera = 2;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ViewedMessageHelper.updateOnline(true, EditProfileMascotaActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ViewedMessageHelper.updateOnline(false, EditProfileMascotaActivity.this);
    }

}