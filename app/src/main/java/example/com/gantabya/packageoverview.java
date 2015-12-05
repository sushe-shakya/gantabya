package example.com.gantabya;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Prasis on 11/19/2015.
 */
public class packageoverview extends Fragment {
String overview;
    TextView package_overview;
    public packageoverview(String packageoverview) {
        overview=packageoverview;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.packageoverview,container,false);
        package_overview= (TextView) myview.findViewById(R.id.overview);
        package_overview.setText(overview);
        return myview;
    }
}
