package example.com.gantabya;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Prasis on 12/3/2015.
 */
public class packagecostexclusion extends Fragment {
    TextView packagecostexclusion;
    String costexclusion;
    public packagecostexclusion(String packagecostexclusion) {
        costexclusion=packagecostexclusion;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview;
        myview = inflater.inflate(R.layout.costexclusion,container,false);
        packagecostexclusion = (TextView) myview.findViewById(R.id.costexclusion);
        packagecostexclusion.setText(costexclusion);
        return myview;
    }
}
