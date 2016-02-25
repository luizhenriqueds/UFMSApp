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

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.db.chart.Tools;
import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.BarSet;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.BarChartView;
import com.db.chart.view.HorizontalBarChartView;
import com.db.chart.view.LineChartView;
import com.db.chart.view.XController;
import com.db.chart.view.YController;
import com.db.chart.view.animation.Animation;

import ufms.br.com.ufmsapp.R;

public class GraficosActivity extends AppCompatActivity {

    private HorizontalBarChartView horizontalBarChartView;
    private LineChartView lineChartView;
    private BarChartView groupBarChart;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notas_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        horizontalBarChartView = (HorizontalBarChartView) findViewById(R.id.barChart);
        lineChartView = (LineChartView) findViewById(R.id.lineChart);
        groupBarChart = (BarChartView) findViewById(R.id.groupBarChart);


        setBarChartMaxGrade();
        setLineChartGradeEvolution();
        setStackBarCompareGrades();
    }

    private void setBarChartMaxGrade() {
        BarSet notasDataset = new BarSet();
        notasDataset.addBar("Nota 1", 6.5f);
        notasDataset.addBar("Nota 2", 7.2f);
        notasDataset.addBar("Nota 3", 8.5f);
        notasDataset.addBar("Nota 4", 9.5f);

        notasDataset.setColor(Color.parseColor("#EF5350"));


        horizontalBarChartView.setBarSpacing(10);

        horizontalBarChartView.setBorderSpacing(0)
                .setXAxis(false)
                .setYAxis(false)
                .setLabelsColor(Color.parseColor("#ffffff"))
                .setXLabels(XController.LabelPosition.OUTSIDE);


        horizontalBarChartView.addData(notasDataset);
        Animation anim = new Animation();

        horizontalBarChartView.show(anim);
    }

    private void setStackBarCompareGrades() {

        final String[] mLabels = {
                "Nota 1", "Nota 2", "Nota 3", "Nota 4"
        };

        final float[][] mValuesOne = {
                {6.5f, 7.2f, 8.5f, 9.5f},
                {7.0f, 3.0f, 7.0f, 7.5f}
        };


        BarSet notasDataset = new BarSet(mLabels, mValuesOne[0]);
        notasDataset.setColor(Color.parseColor("#a1d949"));
        groupBarChart.addData(notasDataset);


        notasDataset = new BarSet(mLabels, mValuesOne[1]);
        notasDataset.setColor(Color.parseColor("#ff7a57"));
        groupBarChart.addData(notasDataset);


        groupBarChart.setBarSpacing(Tools.fromDpToPx(15));
        groupBarChart.setRoundCorners(Tools.fromDpToPx(1));


        groupBarChart.setXAxis(false)
                .setLabelsColor(Color.parseColor("#ffffff"))
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYAxis(false)
                .setYLabels(YController.LabelPosition.OUTSIDE);

        Animation anim = new Animation();

        groupBarChart.show(anim);
    }

    private void setLineChartGradeEvolution() {

        final LineSet notasDatasetLine = new LineSet();
        notasDatasetLine.addPoint("Nota 1", 3.5f);
        notasDatasetLine.addPoint("Nota 2", 7.5f);
        notasDatasetLine.addPoint("Nota 3", 5f);
        notasDatasetLine.addPoint("Nota 4", 8.5f);


        notasDatasetLine.setColor(Color.parseColor("#6a84c3"))
                .setFill(Color.parseColor("#566789"))
                .setDotsColor(Color.parseColor("#ffc755"))
                .setThickness(10);
        lineChartView.addData(notasDatasetLine);

        lineChartView.setBorderSpacing(Tools.fromDpToPx(15))
                .setAxisBorderValues(0, 10)
                .setYLabels(AxisController.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.parseColor("#ffffff"))
                .setXAxis(false)
                .setYAxis(false);

        //lineChartView.addData(notasDatasetLine);

        Animation anim = new Animation();

        lineChartView.setOnEntryClickListener(new OnEntryClickListener() {
            @Override
            public void onClick(int setIndex, int entryIndex, Rect entryRect) {
                Toast.makeText(GraficosActivity.this, "" + notasDatasetLine.getEntry(entryIndex), Toast.LENGTH_LONG).show();
            }
        });

        lineChartView.show(anim);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent parentIntent = NavUtils.getParentActivityIntent(this);
                parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(parentIntent);

                supportFinishAfterTransition();
                break;

            case R.id.action1:
                Toast.makeText(this, "OPCAO1", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);

    }
}
