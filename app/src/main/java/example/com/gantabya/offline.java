package example.com.gantabya;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Prasis on 11/11/2015.
 */
public class offline extends android.support.v4.app.Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview;
        myview = inflater.inflate(R.layout.offline,container,false);
        Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        return myview;
    }
}
