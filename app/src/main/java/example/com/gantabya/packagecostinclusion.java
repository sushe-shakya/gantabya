package example.com.gantabya;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Prasis on 12/2/2015.
 */
public class packagecostinclusion extends Fragment {
    TextView costinclusion;
    String packagecostinclusion;
    public packagecostinclusion(String packagecostinclusion) {
        this.packagecostinclusion=packagecostinclusion;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview;
        myview = inflater.inflate(R.layout.costinclusion,container,false);
        costinclusion= (TextView) myview.findViewById(R.id.costinclusion);
        costinclusion.setText(packagecostinclusion);
        return myview;
    }
}
