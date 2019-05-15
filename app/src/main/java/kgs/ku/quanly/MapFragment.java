package kgs.ku.quanly;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

    private GoogleMap mMap;

    private ProgressDialog myProgress;
    private static final String MYTAG = "MYTAG";
    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;
    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        // Tạo Progress Bar
//        myProgress = new ProgressDialog(getContext());
//        myProgress.setTitle("Map Loading ...");
//        myProgress.setMessage("Please wait...");
//        myProgress.setCancelable(true);
//
//        // Hiển thị Progress Bar
//        myProgress.show();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {


                // Thiết lập sự kiện đã tải Map thành công
                mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

                    @Override
                    public void onMapLoaded() {

                        // Đã tải thành công thì tắt Dialog Progress đi
                        //myProgress.dismiss();

                        // Hiển thị vị trí người dùng.


                    }
                });
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                //mMap.clear(); //clear old markers
                LatLng latLng = new LatLng(10.980703, 106.674509);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)             // Sets the center of the map to location user
                        .zoom(15)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(10.980703, 106.674509))
                        .title("Trường đại học Thủ Dầu Một")
                        .snippet("Địa chỉ: 06, Trần Văn Ơn.")
                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.baseline_location_on_black_18dp)));

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(10.982119, 106.680223))
                        .title("Cafe Số")
                        .snippet("Tôi ở đây này :v")
                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.cafesaoicon1)));

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(10.977844, 106.677920))
                        .title("Kí túc xá số 5")
                        .snippet("SDT: 0389303212 - 500.000/Phòng"));
            }
        });


        return rootView;
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
