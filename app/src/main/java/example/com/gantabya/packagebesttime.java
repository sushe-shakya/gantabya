package example.com.gantabya;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Prasis on 11/28/2015.
 */
public class packagebesttime extends Fragment {
    String packagebesttime;
    public packagebesttime(String packagebesttime) {
        this.packagebesttime=packagebesttime;
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
        myview = inflater.inflate(R.layout.packagebesttime,container,false);
        itinerary = (TextView)myview.findViewById(R.id.besttime);
        itinerary.setText(packagebesttime);
        return myview;
    }
}
