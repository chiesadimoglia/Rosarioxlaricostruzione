package info.androidhive.slidingmenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.ListIterator;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class RosarioFragment extends Fragment {
	
	private Animation animation;
	private ArrayList<String> Preghiere;
	private ArrayList<ImageView> Immagini;
	private String[] mistero;
	private String[] brano;
	private String[] meditation;
	private ImageView img;
	ListIterator<String> p;
	ListIterator<ImageView> i;
    
	public RosarioFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

	
        View rootView = inflater.inflate(R.layout.fragment_rosario, container, false);
         
        return rootView;
    } 
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        final ImageView f_next = (ImageView) getView().findViewById(R.id.imageView_next);
        final ImageView f_back = (ImageView) getView().findViewById(R.id.imageView_back);
        img = (ImageView) getView().findViewById(R.id.imageView_caso);
        final ImageView img_rosario = (ImageView) getView().findViewById(R.id.imageView_rosario);
        final TextView testo = (TextView) getView().findViewById(R.id.textViewPreg);
        
        LoadRosario(0);
		img_rosario.setImageBitmap(
    		    decodeSampledBitmapFromResource(getResources(), R.drawable.rosario, 625, 625));
        
		testo.setText((CharSequence) p.next());
        
        f_next.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				
				img.setImageResource(R.drawable.yellow_light);
				
				testo.setText((CharSequence) p.next());
				//Test Animazione0
				animation= new RotateAnimation(0, 360,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
				animation.setDuration(750);
				animation.setFillAfter(true);
				f_next.startAnimation(animation);
				
			}
		});
        
        f_back.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				img.setImageResource(R.drawable.blue_light);
				
				testo.setText((CharSequence) p.previous());
				//Test Animazione	
				animation= new RotateAnimation(0, -360,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
				animation.setDuration(750);
				animation.setFillAfter(true);
				f_back.startAnimation(animation);
			}
		});
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
}
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	
	public void LoadRosario(Integer m_selected){
		
		Preghiere = new ArrayList <String>();
		p =Preghiere.listIterator();
		
		Immagini = new ArrayList <ImageView>();
		i =Immagini.listIterator();
		
		Preghiere.add("✝ Nel nome del Padre, del Figlio e dello Spirito Santo");
		Preghiere.add("O Dio vieni a salvarmi. Signore vieni presto in mio aiuto");
		Preghiere.add("Gloria al Padre...");
		
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getView().getContext());
		if (prefs.getBoolean("checkbox_01", false)) {
			Preghiere.add("Vieni, Santo Spirito...");
		}
		
		if (prefs.getBoolean("checkbox_1", false)) {
			Preghiere.add("Io credo in Dio...");
		}
		
		if (prefs.getBoolean("checkbox_2", false)) {
			Preghiere.add("Padre Nostro");
			Preghiere.add("Ave Maria per la Fede");
			Preghiere.add("Ave Maria per la Speranza");
			Preghiere.add("Ave Maria per la Carità");
			Preghiere.add("Gloria al Padre...");
		}
		
		//Loading mistero, brano, meditazione
		if (m_selected==0) {
			Calendar c = Calendar.getInstance();
			int giorno = c.get(Calendar.DAY_OF_WEEK);

			switch (giorno) {
			case 0: //Sunday
			case 3: //Wednesday
				mistero = getResources().getStringArray(R.array.gloria_mis);
				if (prefs.getBoolean("checkbox_3", true)) {
					brano = getResources().getStringArray(R.array.gloria_br);
				}
				if (prefs.getBoolean("checkbox_4", false)) {
					brano = getResources().getStringArray(R.array.gloria_med);
				}
				break;
			case 1: //Monday
			case 6: //Saturday
				mistero = getResources().getStringArray(R.array.gioia_mis);
				if (prefs.getBoolean("checkbox_3", true)) {
					brano = getResources().getStringArray(R.array.gioia_br);
				}
				if (prefs.getBoolean("checkbox_4", false)) {
					brano = getResources().getStringArray(R.array.gioia_med);
				}
				break;
			case 2: //Tuesday
			case 5: //Friday
				mistero = getResources().getStringArray(R.array.dolore_mis);
				if (prefs.getBoolean("checkbox_3", true)) {
					brano = getResources().getStringArray(R.array.dolore_br);
				}
				if (prefs.getBoolean("checkbox_4", false)) {
					brano = getResources().getStringArray(R.array.dolore_med);
				}
				break;
			case 4: //Thursday
				mistero = getResources().getStringArray(R.array.luce_mis);
				if (prefs.getBoolean("checkbox_3", true)) {
					brano = getResources().getStringArray(R.array.luce_br);
				}
				if (prefs.getBoolean("checkbox_4", false)) {
					brano = getResources().getStringArray(R.array.luce_med);
				}
				break;
			}	
		}
		else {
			switch (m_selected) {
			case 1: //misteri_gioia
				mistero = getResources().getStringArray(R.array.gioia_mis);
				if (prefs.getBoolean("checkbox_3", true)) {
					brano = getResources().getStringArray(R.array.gioia_br);
				}
				if (prefs.getBoolean("checkbox_4", false)) {
					brano = getResources().getStringArray(R.array.gioia_med);
				}
				break;
			case 2: //misteri_luce
				mistero = getResources().getStringArray(R.array.luce_mis);
				if (prefs.getBoolean("checkbox_3", true)) {
					brano = getResources().getStringArray(R.array.luce_br);
				}
				if (prefs.getBoolean("checkbox_4", false)) {
					brano = getResources().getStringArray(R.array.luce_med);
				}
				break;
			case 3: //misteri_dolore
				mistero = getResources().getStringArray(R.array.dolore_mis);
				if (prefs.getBoolean("checkbox_3", true)) {
					brano = getResources().getStringArray(R.array.dolore_br);
				}
				if (prefs.getBoolean("checkbox_4", false)) {
					brano = getResources().getStringArray(R.array.dolore_med);
				}
				break;
			case 4: //misteri_gloria
				mistero = getResources().getStringArray(R.array.gloria_mis);
				if (prefs.getBoolean("checkbox_3", true)) {
					brano = getResources().getStringArray(R.array.gloria_br);
				}
				if (prefs.getBoolean("checkbox_4", false)) {
					brano = getResources().getStringArray(R.array.gloria_med);
				}
				break;
			}
		}
		
		for (int j=0; j<5; j++){
			Preghiere.add(mistero[j]);
			if (prefs.getBoolean("checkbox_3", true)) {
				Preghiere.add(brano[j]);
			}
			if (prefs.getBoolean("checkbox_4", false)) {
				Preghiere.add(meditation[j]);
			}
			decina();
		}
	}
	
	public void decina() {
		Preghiere.add("Padre Nostro");
		for (int j=0; j<10; j++){
			Preghiere.add("Ave Maria");
		}
		Preghiere.add("Gloria al Padre...");
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getView().getContext());
		if (prefs.getBoolean("checkbox_02", true)) {
			Preghiere.add("L\'Eterno riposo...");
		}
		if (prefs.getBoolean("checkbox_5", true)) {
			Preghiere.add("O Gesù mio...");
		}
		
		if (prefs.getBoolean("checkbox_6", true)) {
			Preghiere.add("Maria, Regina della Pace, prega per noi e per il mondo intero");
		}
	}
}
