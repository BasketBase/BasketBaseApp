package com.base.basket.basketbase1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.base.basket.basketbase1.noticias.NoticiasFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        NoticiasFragment nf=new NoticiasFragment();
        PartidosFragment np=new PartidosFragment();

        switch (position) {
            case 0:
                return nf;
            case 1:
                return np;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Noticias";
            case 1:
            default:
                return "Marcadores";
        }
    }
}