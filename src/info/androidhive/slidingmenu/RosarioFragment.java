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

public class RosarioFragment extends Fragment {
	
	private Animation animation;
	private ArrayList<String> Preghiere;
	private ArrayList<ImageView> Immagini;
	private ImageView img;
    
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
        
       LoadRosario();
		img_rosario.setImageBitmap(
    		    decodeSampledBitmapFromResource(getResources(), R.drawable.rosario, 625, 625));
        
        
        f_next.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				
				img.setImageResource(R.drawable.yellow_light);
				
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
	
	public void LoadRosario(){
		
		Calendar c = Calendar.getInstance();
		int giorno = c.get(Calendar.DAY_OF_WEEK);
		
		Preghiere = new ArrayList <String>();
		ListIterator<String> p =Preghiere.listIterator();
		
		Immagini = new ArrayList <ImageView>();
		ListIterator<ImageView> i =Immagini.listIterator();
		
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
		
		
		
		
	}
	
	public void decina() {
		Preghiere.add("Padre Nostro");
		for (int i=0; i<10; i++){
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
