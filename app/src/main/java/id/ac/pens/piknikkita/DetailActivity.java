package id.ac.pens.piknikkita;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.shapeofview.shapes.RoundRectView;
import com.github.florent37.viewanimator.AnimationBuilder;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

public class DetailActivity extends AppCompatActivity {

    AnimationBuilder myAnimation,myReturnAnimation;
    int tinggiAsli, lebarAsli;
    TextView tvJudul, tvDeskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final ConstraintLayout clDetail = findViewById(R.id.cl_detail);
        final ImageView ivGambar = findViewById(R.id.iv_gambar);
        final RoundRectView rrvParent = findViewById(R.id.rrv_parent);
        tvJudul = findViewById(R.id.tv_judul);
        tvJudul.setAlpha(0);
        tvDeskripsi = findViewById(R.id.tv_deskripsi);
        tvDeskripsi.setAlpha(0);

        final Bundle intent = getIntent().getExtras();
        //ambil extra
        final int lebar = intent.getInt("lebar");
        final int tinggi = intent.getInt("tinggi");
        final int posisiX = intent.getInt("posisiX");
        final int posisiY = intent.getInt("posisiY");
        final int imgId = intent.getInt("imgId");
        setResult(intent.getInt("id"));

        myAnimation = ViewAnimator.animate(rrvParent);
        myReturnAnimation = ViewAnimator.animate(rrvParent);

        if(savedInstanceState == null) {
            rrvParent.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    rrvParent.getViewTreeObserver().removeOnPreDrawListener(this);
                    ivGambar.setImageResource(imgId);

                    //hitung delta
                    float deltaLebar = (float) lebar / rrvParent.getWidth();
                    float deltaTinggi = (float) tinggi / rrvParent.getHeight();
                    int[] lokasi = {0, 0};
                    rrvParent.getLocationOnScreen(lokasi);
                    float deltaX = (float) posisiX - lokasi[0];
                    float deltaY = (float) posisiY - lokasi[1];
                    tinggiAsli = rrvParent.getHeight();
                    lebarAsli =rrvParent.getWidth();

                    myAnimation.translationX(deltaX, 0)
                            .translationY(deltaY, 0)
                            .height(tinggi, tinggiAsli)
                            .width(lebar, lebarAsli)
                            .singleInterpolator(new AccelerateDecelerateInterpolator())
                            .custom(new AnimationListener.Update() {
                                @Override
                                public void update(View view, float value) {
                                    rrvParent.setBottomLeftRadiusDp(value);
                                    rrvParent.setBottomRightRadiusDp(value);
                                    rrvParent.setTopLeftRadiusDp(value);
                                    rrvParent.setTopRightRadiusDp(value);
                                }
                            }, 16, 0)
                            .duration(250)
                            .andAnimate(clDetail)
                            .backgroundColor(Color.TRANSPARENT, Color.WHITE)
                            .onStop(new AnimationListener.Stop() {
                                @Override
                                public void onStop() {
                                    ViewAnimator.animate(tvJudul)
                                            .fadeIn()
                                            .duration(150)
                                            .translationY(30, 0)
                                            .thenAnimate(tvDeskripsi)
                                            .fadeIn()
                                            .duration(150)
                                            .translationY(30, 0)
                                            .start();
                                }
                            })
                            .start();
                    myReturnAnimation.translationX(deltaX)
                            .translationY(deltaY)
                            .height(tinggiAsli, tinggi)
                            .width(lebarAsli, lebar)
                            .singleInterpolator(new DecelerateInterpolator())
                            .custom(new AnimationListener.Update() {
                                        @Override
                                        public void update(View view, float value) {
                                            rrvParent.setBottomLeftRadiusDp(value);
                                            rrvParent.setBottomRightRadiusDp(value);
                                            rrvParent.setTopLeftRadiusDp(value);
                                    rrvParent.setTopRightRadiusDp(value);
                                }
                            }, 16)
                            .andAnimate(clDetail)
                            .backgroundColor(Color.WHITE, Color.TRANSPARENT)
                            .duration(250);
                /*rrvParent.setBottomLeftRadiusDp(16);
                rrvParent.setBottomRightRadiusDp(16);
                    rrvParent.setTopLeftRadiusDp(16);
                    rrvParent.setTopRightRadiusDp(16);*/
//                Toast.makeText(DetailActivity.this, ""+deltaLebar+","+deltaTinggi+","+deltaX+","+deltaY, Toast.LENGTH_SHORT).show();

                    //set skala dan translasi
                /*rrvParent.setScaleX(deltaLebar);
                rrvParent.setScaleY(deltaTinggi);
                rrvParent.setTranslationX(deltaX);
                rrvParent.setTranslationY(deltaY);*/
//                    rrvParent.setAlpha(.5f);

                    return true;
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        ViewAnimator.animate(tvDeskripsi)
                .fadeOut()
                .translationY(30)
                .duration(150)
                .thenAnimate(tvJudul)
                .fadeOut()
                .translationY(30)
                .duration(150)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        myReturnAnimation
                                .onStop(new AnimationListener.Stop() {
                                    @Override
                                    public void onStop() {
                                        DetailActivity.super.onBackPressed();
                                        overridePendingTransition(0,0);
                                    }
                                })
                                .start();
                    }
                })
                .start();
    }

    @Override
    public void finish() {
        super.finish();

    }
}
