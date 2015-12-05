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
public class packagedetail extends Fragment {
    String packagedetail;
    public packagedetail(String packagedetail) {
        this.packagedetail = packagedetail;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview;
        TextView detail;
        myview = inflater.inflate(R.layout.packagedetail,container,false);
        detail = (TextView)myview.findViewById(R.id.detail);
        detail.setText(packagedetail);
        return myview;
    }
}
