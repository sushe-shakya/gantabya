package example.com.gantabya;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Prasis on 11/10/2015.
 */
public class transportation extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {
    AutoCompleteTextView ticketfrom, ticketdestination;
    TextView line;
    GridView ticketgridView;
    ArrayList<HashMap<String, String>> ticketinformation=null;
    Button ticketsearchbutton;
    ProgressBar busticketloader;
    Vector<String> destination = new Vector<String>();
    Vector<String> from = new Vector<String>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview;
        myview = inflater.inflate(R.layout.transportation,container,false);
        ticketsearchbutton = (Button) myview.findViewById(R.id.ticketsearchbutton);
        ticketgridView = (GridView)myview.findViewById(R.id.gridView);
        busticketloader = (ProgressBar) myview.findViewById(R.id.busticketloader);
        ticketfrom = (AutoCompleteTextView)myview.findViewById(R.id.ticketfrom);
        ticketdestination = (AutoCompleteTextView)myview.findViewById(R.id.ticketdestination);
        line =(TextView)myview.findViewById(R.id.line);
        try{
            ticketlist ticobj = new ticketlist();
            ticobj.execute();}
        catch (Exception e)
        {
            Log.d("gantabya","error while calling ticketlist");
        }
        ArrayAdapter<String> fromadapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,from);
        ticketfrom.setAdapter(fromadapter);
        ArrayAdapter<String> desadapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,destination);
        ticketdestination.setAdapter(desadapter);
        ticketsearchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                ArrayList<HashMap<String, String>> ticketfoundinformation = new ArrayList<>();
                ticketfoundinformation.clear();
                for (int i = 0; i < ticketinformation.size(); i++) {

                    if (ticketfrom.getText().toString().equalsIgnoreCase(ticketinformation.get(i).get("ticketfrom")) && ticketdestination.getText().toString().equalsIgnoreCase(ticketinformation.get(i).get("ticketdestination")))
                        ticketfoundinformation.add(ticketinformation.get(i));
                }
                if (ticketfoundinformation.isEmpty())
                {Toast.makeText(getContext(), "Match not found", Toast.LENGTH_SHORT).show();}
                busticketadapter ticketfoundadapter = new busticketadapter(getContext(), ticketfoundinformation);
                ticketgridView.setAdapter(ticketfoundadapter);
            }
        });
        ticketgridView.setOnItemClickListener(this);
        return myview;
    }

    @Override // called when a bus ad is clicked
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment ticketdetail = new ticket_detail(ticketinformation.get(position));
        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, ticketdetail);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public class ticketlist extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {
        @Override
        protected void onPreExecute() {
            busticketloader.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
            String downloadURL = "http://gantabya.cu.cc/xml/bus/"; //url to download the xml content from
            ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
            try {
                URL url = new URL(downloadURL);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                results = processXML(inputStream);


            } catch (Exception e) {
                results = null;
                Log.d("gantabya", "error at doinbackground");
            }

            return results;
        }

        @Override
        protected void onCancelled() {
            Log.d("gantabya", "cancelled");
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> results) {
            try {
                ticketinformation = results;
                busticketadapter adapter = new busticketadapter(getContext(), ticketinformation);
                ticketgridView.setAdapter(adapter);
                busticketloader.setVisibility(View.GONE); // Progress bar disappears once the listview is ready to be displayed.
                ticketdestination.setVisibility(View.VISIBLE);
                ticketfrom.setVisibility(View.VISIBLE);
                ticketsearchbutton.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
            }
            catch (Exception e)
            {
                Log.d("gantabya","error at onpostexecute");
            }
        }

        public ArrayList<HashMap<String,String>> processXML(InputStream inputstream) throws Exception {

            //ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();

            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document xmlDocument = documentBuilder.parse(inputstream);

                Element rootelement = xmlDocument.getDocumentElement();

                NodeList itemslist = rootelement.getElementsByTagName("BusTicket"); // in our app item = packages

                Node currentitem = null;
                NodeList currentiem_childrenlist = null;
                HashMap<String, String> currentmap = null;
                ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
                Node currentchild = null;
                //String packagetype =null;
                int count = 0;
                for (int i = 0; i <itemslist.getLength(); i++) {
                    currentitem = itemslist.item(i);
                    currentmap = new HashMap<String, String>(); // current map is for package attributes
                    // for attribute of root node
                    currentmap.put("ticketfrom", currentitem.getAttributes().getNamedItem("PlaceFrom").getTextContent());
                    if(!from.contains(currentitem.getAttributes().getNamedItem("PlaceFrom").getTextContent()))
                    {
                        Log.d("gantabya","unique element");
                        from.add(new String(currentitem.getAttributes().getNamedItem("PlaceFrom").getTextContent()));
                    }
                    currentmap.put("ticketdestination",new String(currentitem.getAttributes().getNamedItem("PlaceTo").getTextContent()));
                    if(!destination.contains(currentitem.getAttributes().getNamedItem("PlaceTo").getTextContent()))
                    {
                        destination.add(new String(currentitem.getAttributes().getNamedItem("PlaceTo").getTextContent()));
                    }
                    currentmap.put("ticketdeparturetime",currentitem.getAttributes().getNamedItem("DepartureTime").getTextContent());
                    currentmap.put("ticketarrivaltime",currentitem.getAttributes().getNamedItem("ArrivalTime").getTextContent());
                    currentmap.put("ticketimage",currentitem.getAttributes().getNamedItem("Image").getTextContent());
                    currentmap.put("ticketseatchart",currentitem.getAttributes().getNamedItem("SeatChart").getTextContent());
                    currentmap.put("ticketcost",currentitem.getAttributes().getNamedItem("Cost").getTextContent());
                    currentiem_childrenlist = currentitem.getChildNodes();

                    //for further child nodes
                    for(int j=0;j<currentiem_childrenlist.getLength();j++)
                    {
                        currentchild = currentiem_childrenlist.item(j);
                        if(currentchild.getNodeName().equalsIgnoreCase("detail"))
                        {
                            currentmap.put("ticketbusdetail",currentchild.getTextContent());
                        }
                        if(currentchild.getNodeName().equalsIgnoreCase("agency"))
                        {
                            currentmap.put("ticketagencynumber",currentchild.getAttributes().getNamedItem("Phone").getTextContent());
                        }
                        if(currentchild.getNodeName().equalsIgnoreCase("weekdays"))
                        {
                            String availabledays=currentchild.getChildNodes().item(0).getTextContent();
                            //get further child nodes
                            for(int k=1;k<currentchild.getChildNodes().getLength();k++)
                            {
                                availabledays= availabledays+","+currentchild.getChildNodes().item(k).getTextContent();
                            }
                           currentmap.put("ticketavailabledays",availabledays);
                        }

                    }
                    if (currentmap != null && !currentmap.isEmpty())
                        results.add(currentmap);
                }
                return results;

            } catch (Exception e) {
                Log.d("gantabya", "error at processxml");
                return null;

            }
        }

    }
}
class busticketadapter extends BaseAdapter
{
    ArrayList<HashMap<String,String>> busticketdataSource;
    Context context;
    LayoutInflater layoutInflater;
    busticketholder holder = null;
    public busticketadapter(Context context, ArrayList<HashMap<String, String>> busticketdataSource)
    {
        this.busticketdataSource = busticketdataSource;
        this.context = context;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return busticketdataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return busticketdataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row==null)
        {
            row = layoutInflater.inflate(R.layout.busticketdisplay,parent,false);
            holder = new busticketholder(row);
            row.setTag(holder);
        }
        else
        {
            holder = (busticketholder) row.getTag();
        }
        HashMap<String,String> currentItem = busticketdataSource.get(position);
        holder.ticketprice.setText("Rs."+ currentItem.get("ticketcost"));
        holder.ticketroute.setText(currentItem.get("ticketfrom")+"-"+currentItem.get("ticketdestination"));
        Picasso.with(context).load(currentItem.get("ticketimage")).into(holder.ticketimage);
        return row;
    }
}
class busticketholder {
    TextView ticketprice,ticketroute;
    ImageView ticketimage;
    public busticketholder(View view)
    {
        ticketprice = (TextView) view.findViewById(R.id.ticketprice);
        ticketimage = (ImageView) view.findViewById(R.id.busticketimage);
        ticketroute = (TextView)view.findViewById(R.id.route);
    }
}




