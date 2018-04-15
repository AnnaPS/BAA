package com.example.pc.bandsnarts.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pc.bandsnarts.Adaptadores.MiPageAdapter;
import com.example.pc.bandsnarts.R;

public class VentanaSliderParteDos extends AppCompatActivity {

    private ViewPager viewPager;
    private TextView[] textView;
    private LinearLayout layaoutLinear;
    private Button btnSiguiente, btnSaltar;
    private MiPageAdapter pageAdapter;
    private int[] layouts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_slider_parte_dos);
        //finds////
        viewPager = (ViewPager) findViewById(R.id.vwPagerVSliderParteDos);
        btnSaltar = findViewById(R.id.btnSaltarVSliderParteDos);
        btnSiguiente = findViewById(R.id.btnAvanzarVSliderParteDos);


        //SE COMPRUEBA SI ES LA PRIMERA VEZ DE ENTRAR EN LA APP PARA SACAR O NO LA VENTANA DE INFORMACION
        if (!saberSiEsLaPrimeraVezDeInicio()){
            //si ya no es la primera vez se lanza la ventana de inicio !!! PONGO ESTA PORQUE LA DE INICIO NO ESTA HECHA AUN
            //AQUI SE LANZARIA LA POR DEFECTO AL ABRIR LA APP YA LOGUEADO
            establecerPrimeraVezInicio(true);
            startActivity(new Intent(this, VentanaInicialApp.class));
            finish();
        }
        layaoutLinear = findViewById(R.id.linearSlider);
        setStatusBarTransparent();
        layouts = new int[]{R.layout.slider_1, R.layout.slider_2, R.layout.slider_3};
        pageAdapter = new MiPageAdapter(layouts, getApplicationContext());
        viewPager.setAdapter(pageAdapter);
        setDotStatus(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==layouts.length-1){
                    btnSiguiente.setText("COMENZAR");
                    btnSaltar.setVisibility(View.GONE);
                }else{
                    btnSiguiente.setText("SIGUIENTE");
                    btnSaltar.setVisibility(View.VISIBLE);
                }
                setDotStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    public void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | viewPager.SYSTEM_UI_FLAG_FULLSCREEN);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void onClickSiguienteVSlider(View view) {
        int paginaActual=viewPager.getCurrentItem()+1;
        if (paginaActual<layouts.length){
            //nos movemos a la siguiente
            viewPager.setCurrentItem(paginaActual);
        }else{
            startActivity(new Intent(this, VentanaInicialApp.class));
            finish();
        }
    }

    //si pulsa saltar se lanza la actividad de bienvenida
    public void onClickSaltarVSlider(View view) {
        establecerPrimeraVezInicio(true);
        startActivity(new Intent(this, VentanaInicialApp.class));
        finish();
    }
    private void setDotStatus(int page){
        layaoutLinear.removeAllViews();
        textView=new TextView[layouts.length];
        for (int i=0;i<textView.length;i++){
            textView[i]=new TextView(this);
            textView[i].setText(Html.fromHtml("&#8226"));
            textView[i].setTextSize(30);
            textView[i].setTextColor(Color.parseColor("#a9b4bb"));
            layaoutLinear.addView(textView[i]);

        }
        if (textView.length>0){
            textView[page].setTextColor(Color.parseColor("#ffffff"));
        }
    }
    private boolean saberSiEsLaPrimeraVezDeInicio(){
        SharedPreferences preferences=getApplicationContext().getSharedPreferences("IntroSlider", Context.MODE_PRIVATE);
        return preferences.getBoolean("infobienvenida",true );
    }
    private void establecerPrimeraVezInicio(boolean stt){
        SharedPreferences preferences=getApplicationContext().getSharedPreferences("IntroSlider", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("infobienvenida",stt);
        editor.commit();

    }
}
