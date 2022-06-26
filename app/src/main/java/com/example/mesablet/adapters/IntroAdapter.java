package com.example.mesablet.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mesablet.R;
import com.example.mesablet.entities.IntroItem;
import com.example.mesablet.fragments.IntroFragment;

public class IntroAdapter extends FragmentPagerAdapter {

    private final IntroItem[] introItems ={new IntroItem("","Easy sublet in one click",R.drawable.mesublet_logo),
            new IntroItem("Select your destiny","Choose your city, plan your stay. Save your favorite sublets.",R.drawable.second_frag_img),
            new IntroItem("Connect to people","Chat with people to sublet their place.",R.drawable.third_frag_img),
            new IntroItem("Don't forget","Enjoy your sublet!",R.drawable.last_frag_img)};

    public IntroAdapter(FragmentManager fm){ super(fm); }

    @Override
    public Fragment getItem(int position) {
        return IntroFragment.newInstance(introItems[position],position);
    }

    @Override
    public int getCount() { return introItems.length; }
}
