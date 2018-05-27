package com.example.pc.bandsnarts.Adaptadores;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.pc.bandsnarts.FragmentsTabLayoutsInicio.FragmentGruposTabInicio;
import com.example.pc.bandsnarts.FragmentsTabLayoutsInicio.FragmentLocalesTabInicio;
import com.example.pc.bandsnarts.FragmentsTabLayoutsInicio.FragmentMusicosTabInicio;
import com.example.pc.bandsnarts.FragmentsTabLayoutsInicio.FragmentSalasTabInicio;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> listaFragment = new ArrayList<>();
    public final String[] tabTitulos=new String[]{"MÚSICOS","GRUPOS","SALAS","LOCALES"};
    public static int tabs;

    public ViewPagerAdapter(FragmentManager fm,int numerotabs) {
        super(fm);
        tabs=numerotabs;
    }

    @Override
    public Fragment getItem(int position) {
        //return listaFragment.get(position);
        switch (position){
            case 0:
                return new FragmentMusicosTabInicio();
            case 1:
                return new FragmentGruposTabInicio();
            case 2:
                return new FragmentSalasTabInicio();
            case 3:
                return new FragmentLocalesTabInicio();
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        System.out.println("---------------------------------"+getItemPosition(object));
        return super.getItemPosition(object);

    }



    @Override
    public int getCount() {
        return tabTitulos.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitulos[position];
    }
    public void añadirFragment(Fragment fragment,String titulo){
        listaFragment.add(fragment);
        //titulosListaFragment.add(titulo);

    }
}
