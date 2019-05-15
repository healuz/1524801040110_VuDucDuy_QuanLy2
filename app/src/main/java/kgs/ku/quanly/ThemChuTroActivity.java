package kgs.ku.quanly;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import kgs.ku.quanly.mDB.DBChuTro;

public class ThemChuTroActivity extends AppCompatActivity {
    Button btnThem;
    ImageButton btnCapture, btnChoose;
    ImageView imgAvatar;
    EditText edtName, edtAdress, edtPhone, edtUser, edtPass, edtNameHome;
    DatabaseReference mData;
    Bitmap selectedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_chu_tro);

        mData = FirebaseDatabase.getInstance().getReference();
        edtName = (EditText) findViewById(R.id.edtName);
        edtAdress = (EditText) findViewById(R.id.edtAddress);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);
        edtNameHome = (EditText) findViewById(R.id.edtTenTro);
        btnCapture = (ImageButton) findViewById(R.id.btnCapture);
        btnChoose = (ImageButton) findViewById(R.id.btnChoosePicture);
        imgAvatar = (ImageView) findViewById(R.id.imgAvatar);

        btnThem = (Button) findViewById(R.id.btnThem);

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePicture();
            }
        });
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = edtName.getText().toString();
                final String adress = edtAdress.getText().toString();
                final String phone = edtPhone.getText().toString();
                final String user = edtUser.getText().toString();
                final String pass = edtPass.getText().toString();
                final String homename = edtNameHome.getText().toString();

                if (name.length() == 0 || adress.length() == 0 || phone.length() == 0 || user.length() == 0|| pass.length() == 0|| homename.length() == 0) {
                    Toast.makeText(ThemChuTroActivity.this, "Vui lòng điền đủ thông tin !", Toast.LENGTH_LONG).show();
                } else if (pass.length() < 6 && pass.length() > 0) {
                    Toast.makeText(ThemChuTroActivity.this, "Mật khẩu phải có ít nhất 6 kí tự !", Toast.LENGTH_LONG).show();
                } else if (user.replaceAll(" ", "").length() != user.length()){
                    Toast.makeText(ThemChuTroActivity.this, "Tên đăng nhập không được có khoảng trắng !", Toast.LENGTH_LONG).show();
                } else {
                    final Query query = mData.child("ChuTro").orderByChild("user").equalTo(user);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(ThemChuTroActivity.this, "Tên đăng nhập đã tồn tại !", Toast.LENGTH_LONG).show();
                            } else {
                                DBChuTro ChuTro = new DBChuTro();
                                ChuTro.setKeyid(mData.child("ChuTro").push().getKey());
                                ChuTro.setName(name);
                                ChuTro.setAddess(adress);
                                ChuTro.setPhone(phone);
                                ChuTro.setUsername(user);
                                ChuTro.setPassword(pass);
                                ChuTro.setTenphongtro(homename);

                                //đưa bitmap về base64string:
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                selectedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                byte[] byteArray = byteArrayOutputStream .toByteArray();
                                String imgeEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                                ChuTro.setAvatar(imgeEncoded);
                                mData.child("ChuTro").child(ChuTro.getKeyid()).setValue(ChuTro);

                                Toast.makeText(ThemChuTroActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();

                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }

    private void choosePicture() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 200);
    }

    private void capturePicture() {
        Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cInt,100);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100&& resultCode == RESULT_OK) {
//xử lý lấy ảnh trực tiếp lúc chụp hình:
            selectedBitmap = (Bitmap) data.getExtras().get("data");
            imgAvatar.setImageBitmap(selectedBitmap);
        }
        else if(requestCode == 200&& resultCode == RESULT_OK) {
            try {
//xử lý lấy ảnh chọn từ điện thoại:
                Uri imageUri = data.getData();
                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imgAvatar.setImageBitmap(selectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
