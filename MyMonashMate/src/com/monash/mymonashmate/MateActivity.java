package com.monash.mymonashmate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mapquest.android.maps.AnnotationView;
import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.ItemizedOverlay;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.OverlayItem;
import com.monash.mymonashmate.entity.MatchMate;
import com.monash.mymonashmate.entity.MatchMateResult;
import com.monash.mymonashmate.entity.Profile;
import com.monash.mymonashmate.entity.StudentSetting;

public class MateActivity extends Activity {

	private AnnotationView annotation;
	private Profile profile;
	private MatchMateResult mateResult;
	private Button searchMateButton;
	protected CharSequence[] searchOptions;
	protected boolean[] searchSelections;
	Location myLocationForDistanceCalc = new Location("");
	Drawable myIcon;
	Drawable mateIcon;
	Drawable closeMateIcon;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mate);
		
		Intent intent = getIntent();
        if (intent != null) {
        	profile = (Profile)intent.getSerializableExtra("profile");
        	mateResult = (MatchMateResult)intent.getSerializableExtra("matchResult");
        	Log.i("profile", profile.toString());
        	Log.i("mateResult", mateResult.toString());
        }
        addListenerOnSearchCriteriaButton();
        
        
        Double myLatitude = Double.valueOf(profile.getStudent().getLatitude());
        Double myLongitude = Double.valueOf(profile.getStudent().getLongitude());
        GeoPoint myLocation = new GeoPoint(myLatitude, myLongitude);
        
        myLocationForDistanceCalc.setLatitude(myLatitude);
        myLocationForDistanceCalc.setLongitude(myLongitude);

		// set the zoom level, center point and enable the default zoom controls
		MapView map = (MapView) findViewById(R.id.map);
		map.getController().setZoom(17);
		map.getController().setCenter(myLocation);
		map.setBuiltInZoomControls(true);

		// use a custom POI marker by referencing the bitmap file directly,
		// using the filename as the resource ID
		myIcon = getResources().getDrawable(R.drawable.my_location_marker);
		mateIcon = getResources().getDrawable(R.drawable.mates_location_marker);
		closeMateIcon = getResources().getDrawable(R.drawable.close_location_marker);

		final DefaultItemizedOverlay poiOverlay = new DefaultItemizedOverlay(
				mateIcon);
		// set My GeoPoints and title/snippet to be used in the annotation view
		OverlayItem myPoi = new OverlayItem(myLocation, String.valueOf(profile.getStudent().getStudentId()),
						"Unit Code:\n" + profile.niceFormatUnitCodes());
		myPoi.setMarker(myIcon);
		myIcon.setBounds( 0 - myIcon.getIntrinsicWidth() / 2, 0 - myIcon.getIntrinsicHeight(), 
				myIcon.getIntrinsicWidth() / 2, 0 );
		poiOverlay.addItem(myPoi);
		
		drawPoiMap(map, poiOverlay);
	}

	private void drawPoiMap(MapView map, final DefaultItemizedOverlay poiOverlay) {
		List<MatchMate> mateList = mateResult.getMatchMateList();
		Map<Float, MatchMate> mateMap = new HashMap<Float, MatchMate>();
		float[] distanceToMy = new float[mateList.size()];
		for (int i=0; i<mateResult.getMatchMateList().size(); i++ ){
			Location mateLocationForDistanceCalc = new Location("");
			mateLocationForDistanceCalc.setLatitude(Double.valueOf(mateList.get(i).getStudent().getLatitude()));
			mateLocationForDistanceCalc.setLongitude(Double.valueOf(mateList.get(i).getStudent().getLongitude()));
			distanceToMy[i] = myLocationForDistanceCalc.distanceTo(mateLocationForDistanceCalc);
			mateMap.put(distanceToMy[i], mateList.get(i));
		}
		Arrays.sort(distanceToMy);
		for (int n=0; n< distanceToMy.length; n++) {
			MatchMate mate = mateMap.get(distanceToMy[n]);
			// set Mate GeoPoints and title/snippet to be used in the annotation view
	        GeoPoint mateLocation = new GeoPoint(Double.valueOf(mate.getStudent().getLatitude()), 
					Double.valueOf(mate.getStudent().getLongitude()));
			OverlayItem matePoi = new OverlayItem(mateLocation, String.valueOf(mate.getStudent().getStudentId()),
							"");
			if (n==0) {
				
				matePoi.setMarker(closeMateIcon);
				closeMateIcon.setBounds( 0 - closeMateIcon.getIntrinsicWidth() / 2, 0 - closeMateIcon.getIntrinsicHeight(), 
						closeMateIcon.getIntrinsicWidth() / 2, 0 );
			}
			poiOverlay.addItem(matePoi);
		}
		
		annotation = new AnnotationView(map);
		// add a tap listener for My POI overlay
		poiOverlay.setTapListener(new ItemizedOverlay.OverlayTapListener() {
			@Override
			public void onTap(GeoPoint pt, MapView mapView) {
				// when tapped, show the annotation for the overlayItem
				int lastTouchedIndex = poiOverlay.getLastFocusedIndex();
				if (lastTouchedIndex > -1) {
					OverlayItem tapped = poiOverlay.getItem(lastTouchedIndex);
					annotation.setInnerView(getCustomLayout(tapped.getTitle()));
					annotation.showAnnotationView(tapped);
				}
			}
		});
		
		map.getOverlays().add(poiOverlay);
	}
	
	private RelativeLayout getCustomLayout(String studentId) {
		List<MatchMate> mateList = mateResult.getMatchMateList();
		
		RelativeLayout outerRelative = new RelativeLayout(this);
		RelativeLayout.LayoutParams outerRelativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		outerRelative.setLayoutParams(outerRelativeLayoutParams);
		
		LinearLayout innerLinearLayout = new LinearLayout(this);
		LinearLayout.LayoutParams innerLinearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
		innerLinearLayout.setPadding(6, 0, 6, 0);
		innerLinearLayout.setLayoutParams(innerLinearLayoutParams);
		TextView detailTextView = new TextView(this);
		
	    TextView titleTextView = new TextView(this);
	    LinearLayout.LayoutParams titleTextViewLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	    titleTextView.setGravity(Gravity.CENTER);
	    titleTextView.setLayoutParams(titleTextViewLayout);
	    
	    titleTextView.setTextColor(Color.LTGRAY);
	    titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
	    titleTextView.setId(99);
	    
	    //tv.setSingleLine();
	    detailTextView.setGravity(Gravity.LEFT);
	    detailTextView.setEllipsize(TruncateAt.MARQUEE);
	    detailTextView.setTextColor(Color.WHITE);
	    detailTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
	    if (studentId.equals(String.valueOf(profile.getStudent().getStudentId()))) {
	    	Log.i("Inside mylocation poi.", "Inside mylocation poi");
	    	titleTextView.setText("You are here.");
	    	StringBuffer stringBuffer = new StringBuffer();
	    	stringBuffer.append("Student ID: " + studentId + "\n");
	    	stringBuffer.append("Name: " + profile.getStudent().getName() + "\n");
	    	stringBuffer.append("Unit: " + profile.niceFormatUnitCodes() + "\n");
	    	stringBuffer.append("Course: " + profile.niceFormatCourseCodes() + "\n");
	    	stringBuffer.append("Nickname: " + profile.getStudent().getNickname() + "\n");
	    	stringBuffer.append("Native Language: " + profile.getStudent().getNativeLanguage() + "\n");
	    	stringBuffer.append("Favourite Food: " + profile.getStudent().getFavouriteFood() + "\n");
	    	stringBuffer.append("Suburb: " + profile.getStudent().getSuburb() + "\n");
	    	detailTextView.setText(stringBuffer.toString());
	    } else {
		    for (MatchMate mate: mateList) {
		    	if (studentId.equals(String.valueOf(mate.getStudent().getStudentId()))) {
		    		StringBuffer stringBuffer = new StringBuffer();
		    		titleTextView.setText("Student ID: " + studentId );
		    		stringBuffer.append("Name: " + mate.getStudent().getName()+"\n");
		    		stringBuffer.append("Unit: " + mate.niceFormatUnitCodes()+"\n");
		    		stringBuffer.append("Course: " + mate.niceFormatCourseCodes()+"\n");
		    		stringBuffer.append("Nickname: " + 
		    				retrictedOrPublic(PermissionActivity.NICKNAME,
		    						mate.getStudent().getNickname(),mate.getStudentSettings()) +"\n");
		    		stringBuffer.append("Native Language: " + 
		    				retrictedOrPublic(PermissionActivity.NATIVE_LANGUAGE,
		    						mate.getStudent().getNativeLanguage(),mate.getStudentSettings()) +"\n");
		    		stringBuffer.append("Favourite Food: " + 
		    				retrictedOrPublic(PermissionActivity.FAVOURITE_FOOD,
		    						mate.getStudent().getFavouriteFood(), mate.getStudentSettings()) +"\n");
		    		stringBuffer.append("Suburb: " + 
		    				retrictedOrPublic(PermissionActivity.SUBURB,		
		    						mate.getStudent().getSuburb(), mate.getStudentSettings()) +"\n");
		    		detailTextView.setText(stringBuffer.toString());
		    	}
		    }
	    }

	    detailTextView.setId(99);
	    //detailTextView.setLayoutParams(innerLinearLayoutParams);
	    innerLinearLayout.addView(titleTextView);
	    innerLinearLayout.addView(detailTextView);
	    outerRelative.addView(innerLinearLayout);
	    return outerRelative;
	}
	
	private String retrictedOrPublic(String attribute, String value, List<StudentSetting> settingList) {
        for (StudentSetting setting : settingList) {
            if (setting.getStudentSettingPK().getAttribute().equals(attribute) && !setting.isRestricted()) {
                return value;
            }
        }
        return "Secret";
	}

	
	private void addListenerOnSearchCriteriaButton() {
		searchMateButton = (Button) findViewById(R.id.mate_search_criteria);
		searchMateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MateActivity.this, SearchCriteriaActivity.class);
				intent.putExtra("profile", profile);
				startActivity(intent);
				finish();
			}
		});
	}
    
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.match_mate, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.mate_back_dashboard:
			Intent intent = new Intent(MateActivity.this, DashboardDesignActivity.class);
			intent.putExtra("studentId", profile.getStudent().getStudentId());
			startActivity(intent);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
