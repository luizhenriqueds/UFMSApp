/*
 * Copyright [2016] [UFMS]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ufms.br.com.ufmsapp.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.utils.VersionUtils;

public class CursoActivity extends AppCompatActivity {

    private AppBarLayout appBar;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private CoordinatorLayout coordinatorLayout;
    private ImageView courseCover;
    Target mPicassoTarget;
    private TextView courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        courseCover = (ImageView) findViewById(R.id.img_course_detalhes);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        courseName = (TextView) findViewById(R.id.course_name_text);

        appBar = (AppBarLayout) findViewById(R.id.app_bar_layout);
        setToolbar(getString(R.string.txt_course_name_detalhe));

        loadCover();

    }

    private void loadCover() {
        if (mPicassoTarget == null) {
            mPicassoTarget = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    courseCover.setImageBitmap(bitmap);
                    setColor(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            };
        }
        Picasso.with(this)
                .load(R.drawable.background_image)
                .into(mPicassoTarget);
    }

    private void setToolbar(String titulo) {
        setSupportActionBar(toolbar);

        if (appBar != null) {
            if (appBar.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBar.getLayoutParams();
                lp.height = getResources().getDisplayMetrics().widthPixels;
            }
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (collapsingToolbarLayout != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                collapsingToolbarLayout.setTitle(titulo);
            } else {
                courseName.setText(titulo);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }

    }

    public void setColor(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

                int defaultColor = 0x000000;

                int vibrant = palette.getVibrantColor(defaultColor);
                int vibrantDark = palette.getDarkVibrantColor(defaultColor);

                if (VersionUtils.isGraterThanLollipop()) {
                    getWindow().setStatusBarColor(vibrantDark);
                }

                if (appBar != null) {
                    appBar.setBackgroundColor(vibrant);
                } else {
                    toolbar.setBackgroundColor(Color.TRANSPARENT);
                }

                if (collapsingToolbarLayout != null) {
                    collapsingToolbarLayout.setContentScrimColor(vibrant);
                }
                //coordinatorLayout.setBackgroundColor(vibrant);
            }
        });
    }

}
