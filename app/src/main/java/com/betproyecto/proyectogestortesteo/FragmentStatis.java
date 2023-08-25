package com.betproyecto.proyectogestortesteo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.betproyecto.proyectogestortesteo.Data.FirebaseDatabaseHelper;
import com.betproyecto.proyectogestortesteo.Items.MyObject;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentStatis extends Fragment {

    private FirebaseDatabaseHelper databaseHelper;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statis, container, false);
        databaseHelper = new FirebaseDatabaseHelper();

        configurarPieChart();
        configurarPieChartNegativos();
        return view;
    }

    private void configurarPieChart() {
        databaseHelper.obtenerIngresosPorDescripcion(new FirebaseDatabaseHelper.OnIngresosPorDescripcionListener() {
            @Override
            public void onIngresosPorDescripcionObtenidos(HashMap<String, Double> ingresosPorDescripcion) {
                PieChart pieChart = view.findViewById(R.id.pieChart);

                List<PieEntry> entries = new ArrayList<>();

                // Crear las entradas de datos para el PieChart
                for (String descripcion : ingresosPorDescripcion.keySet()) {
                    double montoTotalDescripcion = ingresosPorDescripcion.get(descripcion);
                    entries.add(new PieEntry((float) montoTotalDescripcion, descripcion));
                }

                PieDataSet dataSet = new PieDataSet(entries, "");
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setValueLinePart1OffsetPercentage(80f);
                dataSet.setValueLinePart1Length(0.2f);
                dataSet.setValueLinePart2Length(0.4f);
                dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE); // Mostrar los valores dentro del segmento
                dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
                dataSet.setValueFormatter(new PercentValueFormatter(dataSet));

                PieData data = new PieData(dataSet);
                data.setValueFormatter(new PercentValueFormatter(dataSet));
                data.setValueTextSize(12f);
                data.setValueTextColor(Color.BLACK);

                pieChart.setData(data);
                pieChart.getLegend().setEnabled(false);
                pieChart.invalidate(); // Actualizar el gráfico
                pieChart.setData(data);
            }
        });
    }

    private void configurarPieChartNegativos() {
        databaseHelper.obtenerIngresosNegativosPorDescripcion(new FirebaseDatabaseHelper.OnIngresosPorDescripcionListener() {
            @Override
            public void onIngresosPorDescripcionObtenidos(HashMap<String, Double> ingresosPorDescripcion) {
                PieChart pieChart = view.findViewById(R.id.pieChart2);

                List<PieEntry> entries = new ArrayList<>();

                // Crear las entradas de datos para el PieChart de ingresos negativos
                for (String descripcion : ingresosPorDescripcion.keySet()) {
                    double montoTotalDescripcion = ingresosPorDescripcion.get(descripcion);
                    entries.add(new PieEntry((float) Math.abs(montoTotalDescripcion), descripcion));
                }

                PieDataSet dataSet = new PieDataSet(entries, "");
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setValueLinePart1OffsetPercentage(80f);
                dataSet.setValueLinePart1Length(0.2f);
                dataSet.setValueLinePart2Length(0.4f);
                dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE); // Mostrar los valores dentro del segmento
                dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
                dataSet.setValueFormatter(new PercentValueFormatter(dataSet));

                PieData data = new PieData(dataSet);
                data.setValueFormatter(new PercentValueFormatter(dataSet));
                data.setValueTextSize(12f);
                data.setValueTextColor(Color.BLACK);

                pieChart.setData(data);
                pieChart.getDescription().setEnabled(false); // Deshabilitar la descripción en el piechart
                pieChart.getLegend().setEnabled(false);
                pieChart.invalidate(); // Actualizar el gráfico
            }
        });
    }

    private static class PercentValueFormatter extends ValueFormatter {
        private final IPieDataSet dataSet;

        public PercentValueFormatter(IPieDataSet dataSet) {
            this.dataSet = dataSet;
        }

        @Override
        public String getFormattedValue(float value) {
            // Calculate the total sum of values in the IPieDataSet
            float total = 0f;
            for (int i = 0; i < dataSet.getEntryCount(); i++) {
                total += dataSet.getEntryForIndex(i).getY();
            }

            // Calculate the percentage
            float percent = (value / total) * 100f;

            // Return the percentage as a string with one decimal place
            return String.format("%.1f%%", percent);
        }
    }


}

