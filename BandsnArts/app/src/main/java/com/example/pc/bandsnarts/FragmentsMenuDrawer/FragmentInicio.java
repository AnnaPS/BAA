package com.example.pc.bandsnarts.FragmentsMenuDrawer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.bandsnarts.Adaptadores.ViewPagerAdapter;
import com.example.pc.bandsnarts.FragmentsTabLayoutsInicio.FragmentGruposTabInicio;
import com.example.pc.bandsnarts.FragmentsTabLayoutsInicio.FragmentLocalesTabInicio;
import com.example.pc.bandsnarts.FragmentsTabLayoutsInicio.FragmentMusicosTabInicio;
import com.example.pc.bandsnarts.FragmentsTabLayoutsInicio.FragmentSalasTabInicio;
import com.example.pc.bandsnarts.R;

public class FragmentInicio extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_inicio, container, false);


        //finds
        tabLayout = (TabLayout) vista.findViewById(R.id.tabLayoutInicio);
        viewPager = (ViewPager) vista.findViewById(R.id.viewPagerInicio);

        //uso del adaptador para montar los tabs

        LinearLayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
        mLayoutManger.setOrientation(LinearLayoutManager.VERTICAL);
        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);



        return vista;
    }


}
