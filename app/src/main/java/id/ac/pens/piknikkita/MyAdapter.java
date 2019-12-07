package id.ac.pens.piknikkita;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.github.florent37.shapeofview.shapes.RoundRectView;
import com.github.florent37.viewanimator.AnimationBuilder;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Place> tempat;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ConstraintLayout layout;
        public MyViewHolder(ConstraintLayout v) {
            super(v);
            layout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Place> myDataset, Context c) {
        this.tempat = myDataset;
        this.context = c;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.textView.setText(mDataset[position]);
        final Place tmp = tempat.get(position);
        final RoundRectView rrvParent = holder.layout.findViewById(R.id.rrv_parent);
        final TextView tvJudul = holder.layout.findViewById(R.id.tv_judul);
        final TextView tvDeskripsi = holder.layout.findViewById(R.id.tv_deskripsi);
        final ImageView ivGambar = holder.layout.findViewById(R.id.iv_gambar);
        CardView cvCard = holder.layout.findViewById(R.id.cv_holder);

        tvJudul.setText(tmp.getNama());
        tvDeskripsi.setText(tmp.getDeskripsi());
        ivGambar.setImageResource(Integer.valueOf(tmp.getGambar_utama()));
        ivGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] lokasi = {0,0};
                rrvParent.getLocationOnScreen(lokasi);
                final float rounded = rrvParent.getBottomLeftRadius();
                final int lebar = rrvParent.getWidth();
                final int tinggi = rrvParent.getHeight();
                final int imgId = Integer.valueOf(tmp.getGambar_utama());
                ViewAnimator.animate(tvJudul)
                        .fadeOut()
                        .translationY(-50)
                        .duration(200)
                        .thenAnimate(tvDeskripsi)
                        .fadeOut()
                        .translationY(-50)
                        .duration(200)
                        .onStop(new AnimationListener.Stop() {
                            @Override
                            public void onStop() {
                                openDetail(lebar, tinggi, lokasi[0], lokasi[1], rounded, position, imgId);
                            }
                        })
                        .start();
                //rrvParent.setVisibility(View.INVISIBLE);
            }
        });
        cvCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] lokasi = {0,0};
                rrvParent.getLocationOnScreen(lokasi);
                final float rounded = rrvParent.getBottomLeftRadius();
                final int lebar = rrvParent.getWidth();
                final int tinggi = rrvParent.getHeight();
                final int imgId = Integer.valueOf(tmp.getGambar_utama());
                ViewAnimator.animate(tvJudul)
                        .fadeOut()
                        .translationY(-40)
                        .duration(150)
                        .thenAnimate(tvDeskripsi)
                        .fadeOut()
                        .translationY(-40)
                        .duration(150)
                        .onStop(new AnimationListener.Stop() {
                            @Override
                            public void onStop() {
                                openDetail(lebar, tinggi, lokasi[0], lokasi[1], rounded, position, imgId);
                            }
                        })
                        .start();
                //rrvParent.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void openDetail(int lebar, int tinggi, int posisiX, int posisiY, float rounded, int posisi, int imgId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("lebar", lebar);
        intent.putExtra("tinggi", tinggi);
        intent.putExtra("posisiX", posisiX);
        intent.putExtra("posisiY", posisiY);
        intent.putExtra("rounded", rounded);
        intent.putExtra("id", posisi);
        intent.putExtra("imgId", imgId);
        Activity ini = (Activity) context;
        ini.startActivityForResult(intent, 1);
//        Toast.makeText(ini, ""+lebar+","+tinggi+","+posisiX+","+posisiY, Toast.LENGTH_SHORT).show();
        ini.overridePendingTransition(0,0);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tempat.size();
    }

    public void showAgain(MyViewHolder a) {

    }
}
