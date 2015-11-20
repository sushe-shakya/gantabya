package example.com.gantabya;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Prasis on 11/11/2015.
 */
public class package_detail extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static ViewPager viewPager1;
    public static int int_items = 5 ;
    HashMap<String,String> information;
    public package_detail(HashMap<String, String> information) {
        this.information = information;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.package_detail,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        /**
         *Set an Adapter for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));


        viewPager1 = (ViewPager) x.findViewById(R.id.view_pager);

        ImageAdapter adapter = new ImageAdapter(getActivity());
        viewPager1.setAdapter(adapter);

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return x;

    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 :return new packageoverview();
                case 1 :
                {
                    return new packageitinerary(information.get("packageitinerary"));
                }
                case 2 : return new packageoverview();
                case 3 : return new packageoverview();
                case 4 : return new packageoverview();

            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Overview";
                case 1 :
                    return "Brief Itinerary";
                case 2 :
                    return "Detail";
                case 3 :
                    return "Cost Inclusion/Exclusion";
                case 4:
                    return "Company";
            }
            return null;
        }
    }


    public class ImageAdapter extends PagerAdapter {
        Context context;
        ImageAdapter(Context context){
            this.context=context;
        }
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);

            int padding = context.getResources().getDimensionPixelSize(R.dimen.pixel);
            imageView.setPadding(padding, padding, padding, padding);
            //imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            //imageView.setImageResource(GalImages[position]);
            Picasso.with(context).load(information.get("packageimage")).into(imageView);
            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }

}
