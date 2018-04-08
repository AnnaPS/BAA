package com.example.pc.bandsnarts;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MiPageAdapter extends PagerAdapter {
    //CLASE PARA IMPLEMENTAR LOS SLIDER
    private LayoutInflater inflador;
    private int[]layouts;
    private Context contexto;

    public MiPageAdapter(int[] layouts, Context contexto) {
        this.layouts = layouts;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflador=(LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vista=inflador.inflate(layouts[position],container,false);
        container.addView(vista);
        return vista;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       View vista=(View)object;
       container.removeView(vista);
    }
}
