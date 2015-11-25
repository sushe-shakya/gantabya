package example.com.gantabya;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Prasis on 11/10/2015.
 */
public class search_packages extends android.support.v4.app.Fragment {
    Spinner packagetypes, packageprice;
    ListView foundpackages;
    Button packagesearchbutton;
    ArrayList<HashMap<String,String>> foundresults=null;
    String pricesetbyuser, typesetbyuser = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview;
        myview = inflater.inflate(R.layout.search_packages, container, false);
        packagetypes = (Spinner) myview.findViewById(R.id.spinner);
        packageprice = (Spinner) myview.findViewById(R.id.spinner2);
        foundpackages= (ListView) myview.findViewById(R.id.listView2);
        packagesearchbutton = (Button) myview.findViewById(R.id.packagesearchbutton);
        foundresults = new ArrayList<HashMap<String, String>>();

        ArrayAdapter priceadapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.pricelist));
        Log.d("gantabya", "Ok upto here");
        packageprice.setAdapter(priceadapter);
        ArrayAdapter typeadapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.packagetypelist));
        packagetypes.setAdapter(typeadapter);
        packagetypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typesetbyuser = parent.getSelectedItem().toString();
                Log.d("gantabya", parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        packageprice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pricesetbyuser = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        packagesearchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foundresults.clear();// to clear the previous results
                String currentprice, currenttype = null;
                Integer price = 0;
                for (int i = 0; i <= 5; i++) {
                    try {
                        price = Integer.parseInt(packages.information.get(i).get("packageprice").replaceAll("[\\D]",""))/100;
                        Log.d("gantabya",price.toString());
                    } catch (Exception e) {
                        Log.d("gantabya", "error while parsing");
                    }
                    if (price < 10000) {
                        currentprice = "Below Rs.10,000";
                    } else if (price > 10000 && price < 30000) {
                        currentprice = "Rs.10,000-Rs.30,000";
                    } else if (price > 30000 && price < 50000) {
                        currentprice = "Rs.30,000-Rs.50,000";
                    } else if (price > 50000 && price < 70000) {
                        currentprice = "Rs.50,000-Rs.70,000";
                    } else {
                        currentprice = "Above Rs.70,000";
                    }
                    currenttype = packages.information.get(i).get("packagetype");
                    if (currenttype.equalsIgnoreCase(typesetbyuser) && currentprice.equalsIgnoreCase(pricesetbyuser)) {
                        foundresults.add(packages.information.get(i));
                    } else {
                        Log.d("gantabya", "No results found");
                        Log.d("gantabya", currenttype);
                    }
                }

                packageadapter packadapter = new packageadapter(getContext(), foundresults);
                foundpackages.setAdapter(packadapter);

                if (foundresults.isEmpty()) {
                    Toast.makeText(getContext(), "No Package Found", Toast.LENGTH_SHORT).show();
                }
                foundpackages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Fragment packagedetail = new package_detail((HashMap<String, String>) parent.getItemAtPosition(position));
                        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, packagedetail);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
            }
        });

        return myview;
    }
}

