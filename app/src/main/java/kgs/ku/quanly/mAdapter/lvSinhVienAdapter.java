package kgs.ku.quanly.mAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import kgs.ku.quanly.R;
import kgs.ku.quanly.mDB.DBChuTro;
import kgs.ku.quanly.mDB.DBSinhVien;

public class lvSinhVienAdapter extends ArrayAdapter<DBSinhVien> {
    private Context context;
    private int resource;
    private List<DBSinhVien> arrSinhvien;
    DatabaseReference mData;

    public lvSinhVienAdapter(Context context, int resource, ArrayList<DBSinhVien> arrSinhvien) {
        super(context, resource, arrSinhvien);
        this.context = context;
        this.resource = resource;
        this.arrSinhvien = arrSinhvien;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        lvSinhVienAdapter.ViewHolder viewHolder;
        mData = FirebaseDatabase.getInstance().getReference();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_sinhvien, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvNameSV);
            viewHolder.tvMasv = (TextView) convertView.findViewById(R.id.tvMasv);
//            viewHolder.imgavatar = (ImageView) convertView.findViewById(R.id.imgAvatarlv);
//        viewHolder.tvAvatar = (TextView) convertView.findViewById(R.id.tvAvatarCT);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (lvSinhVienAdapter.ViewHolder) convertView.getTag();
        }
        DBSinhVien dbsinhvien = arrSinhvien.get(position);
//    viewHolder.tvAvatar.setBackgroundColor(dbChuTro.getColor());
//    viewHolder.tvAvatar.setText(String.valueOf(position+1));
        viewHolder.tvName.setText(dbsinhvien.getName());
        viewHolder.tvMasv.setText(dbsinhvien.getMasv());

        String avatar = dbsinhvien.getAvatar();

//        byte[] decodedString = Base64.decode(avatar, Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        viewHolder.imgavatar.setImageBitmap(decodedByte);



//    viewHolder.btnXoa.setOnClickListener(new View.OnClickListener() {
//
//        @Override
//        public void onClick(View arg0) {
//            // TODO Auto-generated method stub
//            // trong vi du nay de don gian minh chi log data ra logcat
//            // trong truong hop thuc te cac ban co the xu ly tuy theo yeu cau cua app
//        }
//    });


        return convertView;
    }

    public class ViewHolder {

        TextView tvName, tvMasv;


    }


}
