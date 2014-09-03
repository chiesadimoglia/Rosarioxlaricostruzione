package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.classi.Preghiera;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class PreghiereFragment extends Fragment {
	
	public PreghiereFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_preghiere, container, false);
         
        return rootView;
    }
	
	@Override
	public void onActivityCreated(Bundle bundle){
		super.onActivityCreated(bundle);
		//TODO: Aggiornare url query
		String serverURL = "http://rosarioricostruzione.altervista.org/";
		//new Connessione().execute(serverURL);
	}
	
	
	private class Connessione extends AsyncTask<String, Void, Void>{
		 
		private ProgressDialog pDialog;
		private InputStream is = null;
		private StringBuilder sb=null;
	    private String result = null;
	    private List<Preghiera> listaPreghiere;
	    private Preghiera preghiera;
	    
	    
		
		protected void onPreExecute() {
			super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getView().getContext());
            pDialog.setMessage("Attendere prego...");
            pDialog.setCancelable(false);
            pDialog.show();
			
	    }
	    protected Void doInBackground(String... urls) {
	    	
	    	listaPreghiere =new ArrayList<Preghiera>();
	 
	    	try {
				URI url = new URI(urls[0]);
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is=httpEntity.getContent();
			}catch(Exception e){
				Log.e("log_tag","Errore nella connessione http"+e.toString());
			}
	    	
	    	
			try{
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
	            sb = new StringBuilder();
	            String line=null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            is.close();
	            result=sb.toString();      
	        }catch(Exception e){
	            Log.e("log_tag", "Error converting result "+e.toString());
	        }
			if(result!=null){
				try {
					JSONObject jsonobj = new JSONObject(result);
					JSONArray preghiere = jsonobj.getJSONArray("preghiere");
					for(int i = 0;i<preghiere.length();i++){
						
						JSONObject c =preghiere.getJSONObject(i);
						String id = c.getString("idp");
						String nome = c.getString("nome");
	                    String testo = c.getString("testo");
	                    String tipo = c.getString("tipo");
	                    String importanza = c.getString("importanza");
	                   
	                    preghiera= new Preghiera(Integer.valueOf(id),nome,testo,tipo,Integer.valueOf(importanza));
	                    

	                    listaPreghiere.add(preghiera);
	                    
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
	    	return null;
	    }
	    
	    protected void onPostExecute(Void unused) {
	    	
	    	if (pDialog.isShowing())
                pDialog.dismiss();
	    	
	    	
	    	ListView mylistview = (ListView) getView().findViewById(R.id.listView_Preghiere);
	    	CustomAdapter adapter = new CustomAdapter(getView().getContext(), listaPreghiere);
	    	mylistview.setAdapter(adapter);
	    	mylistview.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            	//TODO: Gestione click
//	            	Carta c=(Carta) parent.getItemAtPosition(position); 
//	            	
//	            	SharedPreferences prefs = view.getContext().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
//	                SharedPreferences.Editor editor = prefs.edit();
//	                editor.putString("CARTA", c.getID());
//	                editor.commit();
//	                
//	            	Fragment fragment = new Card_information();
//		    		FragmentManager fragmentManager = getFragmentManager();
//		    		fragmentManager.beginTransaction()
//		    				.replace(R.id.frame_container, fragment).addToBackStack("Result").commit();
//		    		
	                }
	        });
	    }
	    
	}
	
	public class CustomAdapter extends BaseAdapter {

		 private Context context;
		 private List<Preghiera> Preghiere;
		 

		 CustomAdapter(Context context, List<Preghiera> p) {
			 
			 this.context = context;
			 this.Preghiere = p;
		 }

		 @Override
		 public int getCount() {
			 return Preghiere.size();
		 }

		 @Override
		 public Object getItem(int position) {
			 return Preghiere.get(position);
		 }

		 @Override
		 public long getItemId(int position) {
			 return Preghiere.indexOf(getItem(position));
		 }

		 /* private view holder class */
		 private class ViewHolder {
		  ImageView tipo;
		  TextView nome;
		 }

		 @Override
		 public View getView(int position, View convertView, ViewGroup parent) {

		  ViewHolder holder = null;

		  LayoutInflater mInflater = (LayoutInflater) context
		    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		  if (convertView == null) {
		   convertView = mInflater.inflate(R.layout.preghiera_list_item, null);
		   holder = new ViewHolder();

		   holder.nome = (TextView) convertView.findViewById(R.id.textView_nome);
		   holder.tipo = (ImageView) convertView.findViewById(R.id.imageView_tipo);

		   Preghiera row_pos = Preghiere.get(position);

		   holder.nome.setText(row_pos.getNome());
		   holder.tipo.setImageResource(row_pos.getTipo());
		   
		   convertView.setTag(holder);
		   Log.i("Posizione: ", Integer.toString(position));
		   Log.i("Carta: ", row_pos.getNome());
		   
		  } else {
		   holder = (ViewHolder) convertView.getTag();
		   Preghiera row_pos = Preghiere.get(position);

		   holder.nome.setText(row_pos.getNome());
		   holder.tipo.setImageResource(row_pos.getTipo());
		   
		   convertView.setTag(holder);
		  }

		  return convertView;
		 }

		}
	
}
