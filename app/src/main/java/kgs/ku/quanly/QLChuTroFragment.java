package kgs.ku.quanly;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import kgs.ku.quanly.mDB.DBChuTro;
import kgs.ku.quanly.mAdapter.lvChuTroAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class QLChuTroFragment extends Fragment {

    Button btnThem;
    ListView lvChuTro;
    ArrayList<DBChuTro> arrChuTro = new ArrayList<>();
    DatabaseReference mData;
    private ProgressDialog myProgress;


    public QLChuTroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Tạo Progress Bar
        myProgress = new ProgressDialog(getContext());
        myProgress.setTitle("Đang tải dữ liệu ...");
        myProgress.setMessage("Xin vui lòng chờ...");
        myProgress.setCancelable(true);

        // Hiển thị Progress Bar
        myProgress.show();
        mData = FirebaseDatabase.getInstance().getReference();
        final View v = inflater.inflate(R.layout.fragment_qlchu_tro, container, false);
        btnThem = (Button) v.findViewById(R.id.btnThemChu);
        lvChuTro = (ListView) v.findViewById(R.id.lv_ChuTro);

        final lvChuTroAdapter customAdapter = new lvChuTroAdapter(getContext(),R.layout.lv_chutro,arrChuTro);
arrChuTro.clear();
        mData.child("ChuTro").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                DBChuTro ChuTro = dataSnapshot.getValue(DBChuTro.class);
                ChuTro.setKeyid(dataSnapshot.getKey());
//                keysList.add(dataSnapshot.getKey());
                arrChuTro.add(ChuTro);
                lvChuTro.setAdapter(customAdapter);
                myProgress.dismiss();
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentThemChuTro = new Intent(getActivity(), ThemChuTroActivity.class);
                startActivity(intentThemChuTro);
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

}
