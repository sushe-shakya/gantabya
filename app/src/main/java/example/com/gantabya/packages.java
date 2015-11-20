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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Prasis on 11/10/2015.
 */
public class packages extends Fragment implements AdapterView.OnItemClickListener {

    ListView listView;
    ProgressBar loader;
    ArrayList<HashMap<String, String>> information;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview;
        packageslist obj = new packageslist();
        obj.execute();
        myview = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) myview.findViewById(R.id.listView);
        loader = (ProgressBar) myview.findViewById(R.id.loader);
        listView.setOnItemClickListener(this);
        return myview;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString("packagename", information.get(position).get("packagename"));
        bundle.putString("packageitinerary", information.get(position).get("packageitinerary"));
        bundle.putString("packageimage",information.get(position).get("packageimage"));
        Fragment packagedetail = new package_detail(information.get(position));
        packagedetail.setArguments(bundle);
        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, packagedetail);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // funciton to get and manage the xml content
    public class packageslist extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
            String downloadURL = "http://gantabya.cu.cc/get_package_xml.php"; //url to download the xml content from
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
                myadapter adapter = new myadapter(getContext(), results);
                listView.setAdapter(adapter);
                information = new ArrayList<HashMap<String, String>>();
                information = results;
                loader.setVisibility(View.GONE); // Progress bar disappears once the listview is ready to be displayed.
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

                NodeList itemslist = rootelement.getElementsByTagName("Package"); // in our app item = packages

                Node currentitem = null;
                NodeList currentiem_childrenlist = null;
                HashMap<String, String> currentmap = null;
                ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
                Node currentchild = null;
                int count = 0;
                for (int i = 0; i <itemslist.getLength(); i++) {
                    currentitem = itemslist.item(i);
                    currentmap = new HashMap<String, String>(); // current map is for package attributes
                    currentmap.put("packagename",currentitem.getAttributes().getNamedItem("Name").getTextContent());
                    //currentmap.put("packageplace",currentitem.getAttributes().getNamedItem("Place").getTextContent());
                    //currentmap.put("packageduration",currentitem.getAttributes().getNamedItem("Duration").getTextContent());
                    currentmap.put("packageprice",currentitem.getAttributes().getNamedItem("Cost").getTextContent());
                    //currentmap.put("packagedes",currentitem.getAttributes().getNamedItem("Description").getTextContent());
                    currentmap.put("packageimage",currentitem.getAttributes().getNamedItem("Image").getTextContent());
                    currentiem_childrenlist = currentitem.getChildNodes();
                    Log.d("gantabya",currentiem_childrenlist.item(1).getTextContent());
                    currentmap.put("packageitinerary",currentiem_childrenlist.item(1).getTextContent());
                  /*for(int j=0;j<currentiem_childrenlist.getLength();j++)
                    {
                        if(currentiem_childrenlist.item(j).equals("Itinerary"))
                        {
                            currentmap.put("packageitinerary",currentiem_childrenlist.item(j).getTextContent());
                        }
                        Log.d("gantabya",currentmap.get("packageitinerary"));
                    }*/

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
class myadapter extends BaseAdapter
{
    ArrayList<HashMap<String,String>> dataSource;
    Context context;
    LayoutInflater layoutInflater;
    Myholder holder = null;
    public myadapter(Context context,ArrayList<HashMap<String,String>> dataSource)
    {
        this.dataSource = dataSource;
        this.context = context;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
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
            row = layoutInflater.inflate(R.layout.packages,parent,false);
            holder = new Myholder(row);
            row.setTag(holder);
        }
        else
        {
            holder = (Myholder) row.getTag();
        }
        HashMap<String,String> currentItem = dataSource.get(position);
        holder.title.setText(currentItem.get("packagename"));
        holder.pubdate.setText("Rs."+currentItem.get("packageprice"));
        Picasso.with(context).load(currentItem.get("packageimage")).into(holder.image);
            return row;
    }
}
class Myholder{
    TextView title, pubdate;
    ImageView image;
    public Myholder(View view)
    {
        title = (TextView) view.findViewById(R.id.title);
        pubdate= (TextView) view.findViewById(R.id.pubdate);
        image = (ImageView) view.findViewById(R.id.packageimage);
    }
}


