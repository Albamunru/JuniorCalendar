package com.example.juniorcalendar.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.juniorcalendar.Fragments.EducacionFragment;
import com.example.juniorcalendar.Fragments.HomeFragment;
import com.example.juniorcalendar.Fragments.HorarioFragment;
import com.example.juniorcalendar.Fragments.OcioFragment;
import com.example.juniorcalendar.Fragments.SaludFragment;
import com.example.juniorcalendar.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityPrincipal extends AppCompatActivity {
    FragmentContainerView containerView;
    BottomNavigationView navigationView;

    HomeFragment homeFragment= new HomeFragment();
    EducacionFragment educacionFragment= new EducacionFragment();
    HorarioFragment horarioFragment=new HorarioFragment();
    OcioFragment ocioFragment=new OcioFragment();
    SaludFragment saludFragment= new SaludFragment();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_principal);


        this.containerView = findViewById(R.id.fragmentContainer);

        setNavMenu();


    }



    public void setNavMenu(){
        this.navigationView = findViewById(R.id.btnNavMenu);

        changeFragment(homeFragment);

        this.navigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.itemidHome:
                    changeFragment(homeFragment);
                    break;
                case R.id.itemEducacion:
                    changeFragment(educacionFragment);
                    break;
                case R.id.itemMedico:
                    changeFragment(saludFragment);
                    break;
                case R.id.itemOcio:
                    changeFragment(ocioFragment);
                    break;
                case R.id.itemHorario:
                    changeFragment(horarioFragment);
                    break;
                default:
                    changeFragment(homeFragment);
                    break;

            }

            return true;
        });
    }
    public void changeFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuopciones_main, menu);
        return true;
    }

}