package example.com.gantabya;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by Prasis on 11/29/2015.
 */
public class ticket_detail extends Fragment {
    FloatingActionButton bookbusticketbutton;
    Button viewseatchartbutton;
    TextView busticketdestination, busticketcost, busticketdeparture, busticketarrival, busticketextradetail,busticketworkingdays;
    ImageView busimage;
    HashMap<String,String> ticketdetail;
    public ticket_detail(HashMap<String, String> ticketdetail) {
        this.ticketdetail = ticketdetail;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview;
        String departuretime;
        int departurehour,departureminute;
        myview = inflater.inflate(R.layout.ticketdetail,container,false);
        bookbusticketbutton= (FloatingActionButton) myview.findViewById(R.id.fab);
        viewseatchartbutton= (Button) myview.findViewById(R.id.viewseatchart_button);
        busticketdestination= (TextView) myview.findViewById(R.id.detail_destination);
        busticketcost= (TextView) myview.findViewById(R.id.detail_cost);
        busticketdeparture= (TextView) myview.findViewById(R.id.detail_departure);
        busticketextradetail= (TextView) myview.findViewById(R.id.detail_extradetail);
        busticketworkingdays=(TextView)myview.findViewById(R.id.detail_workingdays);
        busimage=(ImageView)myview.findViewById(R.id.detail_busimage);
        busticketdestination.setText(ticketdetail.get("ticketdestination"));
        busticketcost.setText("Rs."+ticketdetail.get("ticketcost"));
        busticketextradetail.setText(ticketdetail.get("ticketbusdetail"));
        //busticketworkingdays.setText(ticketdetail.get("ticketavailabledays"));
        departuretime=ticketdetail.get("ticketdeparturetime");
        String[]timeparts=departuretime.split("[:]");
        departurehour=Integer.parseInt(timeparts[0]);
        departureminute =Integer.parseInt(timeparts[1]);
        if(departurehour>12){
            if(departureminute<10)
            busticketdeparture.setText((departurehour-12)+":0"+departureminute+" PM"); // less than 10 huda agadi ko zero lidaina
            else
                busticketdeparture.setText((departurehour-12)+":"+departureminute+" PM");
        }
        else{
            if(departureminute<10)
            busticketdeparture.setText((departurehour)+":0"+departureminute+" AM");
            else
                busticketdeparture.setText((departurehour)+":"+departureminute+" AM");
        }

        Picasso.with(getContext()).load(ticketdetail.get("ticketimage")).into(busimage);
        viewseatchartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seatcharturl = ticketdetail.get("ticketseatchart");
                try {
                    Fragment ticketseatchart = new seatchart(seatcharturl);
                    android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, ticketseatchart);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } catch (Exception e) {
                    Log.d("gantabya", "calling error");
                }

            }
        });
        bookbusticketbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence bookingoptions[] = new CharSequence[] {"Book by Call","Book by mail"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Booking Options");
                builder.setItems(bookingoptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0:
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"+ticketdetail.get("ticketagencynumber")));
                                startActivity(intent);
                            case 1:

                        }
                    }
                });
                builder.show();

            }
        });
        return  myview;
    }
}
