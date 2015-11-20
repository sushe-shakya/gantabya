package example.com.gantabya;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Prasis on 11/19/2015.
 */
public class packageitinerary extends Fragment {
    String packageitinerary;
    public packageitinerary(String packageitinerary) {
        this.packageitinerary = packageitinerary;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview;
        TextView itinerary;
        myview = inflater.inflate(R.layout.packageitinerary,container,false);
        itinerary = (TextView)myview.findViewById(R.id.itinerary);
        itinerary.setText(packageitinerary);
        return myview;
    }
}
