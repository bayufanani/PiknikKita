package id.ac.pens.piknikkita;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.shapeofview.shapes.RoundRectView;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<Place> tempat = new ArrayList<>();
    MyAdapter myAdapter;
    RecyclerView rvPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempat.add(new Place(1, "Alun-alun", "Alun-alun kota merupakan ...", String.valueOf(R.drawable.satu)));
        tempat.add(new Place(2, "Ngebel", "Ngebel merupakan ...", String.valueOf(R.drawable.dua)));
        tempat.add(new Place(3, "Bukit Bintang", "Bukit bintang merupakan ...", String.valueOf(R.drawable.tiga)));
        tempat.add(new Place(4, "Pringgitan", "Pringgitan merupakan ...", String.valueOf(R.drawable.dua)));
        tempat.add(new Place(5, "Pudak", "Pudak merupakan ...", String.valueOf(R.drawable.tiga)));
        myAdapter = new MyAdapter(tempat, this);
        rvPlaces= findViewById(R.id.rv_places);
        rvPlaces.setHasFixedSize(true);
        rvPlaces.setLayoutManager(new LinearLayoutManager(this));
        rvPlaces.setAdapter(myAdapter);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        Toast.makeText(this, ""+resultCode, Toast.LENGTH_SHORT).show();
        super.onActivityReenter(resultCode, data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        Toast.makeText(this, ""+resultCode, Toast.LENGTH_SHORT).show();
//        View v = rvPlaces.getChildAt(resultCode);
        View v = rvPlaces.getLayoutManager().findViewByPosition(resultCode);
        TextView tvJudul = v.findViewById(R.id.tv_judul);
        TextView tvDeskripsi = v.findViewById(R.id.tv_deskripsi);
        ViewAnimator.animate(tvJudul)
                .fadeIn()
                .translationY(0)
                .duration(150)
                .thenAnimate(tvDeskripsi)
                .fadeIn()
                .translationY(0)
                .duration(150)
                .start();
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showImageAgain(int index) {
        View v = rvPlaces.getChildAt(index);
        RoundRectView rrv = v.findViewById(R.id.rrv_parent);
        rrv.setVisibility(View.VISIBLE);
    }
}
