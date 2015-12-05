package example.com.gantabya;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by Prasis on 12/2/2015.
 */
public class seatchart extends Fragment {
    String seatcharturl;
    ImageView seatchartimage;
    public seatchart(String seatchart) {
        seatcharturl=seatchart;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview;
        myview = inflater.inflate(R.layout.seatchartimage, container, false);
        seatchartimage= (ImageView) myview.findViewById(R.id.seatchartimage);
        if(!seatcharturl.isEmpty())
        {Picasso.with(getContext()).load(seatcharturl).into(seatchartimage);}
        else
        {
            Toast.makeText(getContext(),"No seatchart available",Toast.LENGTH_SHORT).show();
        }
        return myview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
