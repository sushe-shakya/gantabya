package example.com.gantabya;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Prasis on 11/10/2015.
 */
public class transportation extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview;
        myview = inflater.inflate(R.layout.transportation,container,false);
        ImageView transimage, transimage2;
        transimage = (ImageView) myview.findViewById(R.id.transimage);
        transimage2 = (ImageView) myview.findViewById(R.id.transimage2);

        Picasso.with(getActivity()).load("http://www.nextyatra.com/images/product/4c5279f647403.jpg").into(transimage2);
        return myview;
    }
}
