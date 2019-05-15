package kgs.ku.quanly;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import kgs.ku.quanly.mAdapter.lvChuTroAdapter;
import kgs.ku.quanly.mAdapter.lvSinhVienAdapter;
import kgs.ku.quanly.mDB.DBChuTro;
import kgs.ku.quanly.mDB.DBSinhVien;


/**
 * A simple {@link Fragment} subclass.
 */
public class QLSVFragment extends Fragment {
    ListView lvsinhvien;
    ArrayList<DBSinhVien> arrsinhvien = new ArrayList<>();
    DatabaseReference mData;

    public QLSVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mData = FirebaseDatabase.getInstance().getReference();
        final View v = inflater.inflate(R.layout.fragment_qlsv, container, false);
        lvsinhvien = (ListView) v.findViewById(R.id.lv_Sinhvien);

        final lvSinhVienAdapter customAdapter = new lvSinhVienAdapter(getContext(),R.layout.lv_sinhvien,arrsinhvien);
        arrsinhvien.clear();
        mData.child("NguoiTro").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                DBSinhVien SinhVien = dataSnapshot.getValue(DBSinhVien.class);
                SinhVien.setKeyid(dataSnapshot.getKey());
//                keysList.add(dataSnapshot.getKey());
                arrsinhvien.add(SinhVien);
                lvsinhvien.setAdapter(customAdapter);
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


lvsinhvien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ChiTietSV.class);
        Bundle bundle = new Bundle();
        bundle.putString("ten", arrsinhvien.get(position).getName());
        bundle.putString("diachi", arrsinhvien.get(position).getAddess());
        bundle.putString("dt", arrsinhvien.get(position).getPhone());
        bundle.putString("masv", arrsinhvien.get(position).getMasv());
        intent.putExtra("chitietsinhvien", bundle);
        startActivity(intent);
    }
});
        // Inflate the layout for this fragment
        return v;
    }

}
