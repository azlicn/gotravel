package my.edu.unikl.gotravel;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.Random;

import my.edu.unikl.android.common.activity.SampleActivityBase;
import my.edu.unikl.android.common.logger.Log;


public class SearchActivity extends SampleActivityBase implements GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = "SearchActivity";

    /**
     * GoogleApiClient wraps our service connection to Google Play Services and provides access
     * to the user's sign in state as well as the Google's APIs.
     */
    protected GoogleApiClient mGoogleApiClient;

    private PlaceAutocompleteAdapter mAdapter;

    private AutoCompleteTextView originAutocompleteView;

    private AutoCompleteTextView destinationAutocompleteView;

    private TextView mPlaceDetailsText;

    private TextView mPlaceDetailsAttribution;

    private EditText originLatitudeView;

    private EditText originLongitudeView;

    private EditText destinationLatitudeView;

    private EditText destinationLongitudeView;

    private String origin;

    private String destination;

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

    public final static String JSON_ROUTE = "my.edu.unikl.gotravel.JSON_ROUTE";
    public final static String ROUTE = "my.edu.unikl.gotravel.ROUTE";
    public final static String STOP = "my.edu.unikl.gotravel.STOP";
    public final static String SEGMENT = "my.edu.unikl.gotravel.SEGMENT";
    public final static String PLACE_ORIGIN = "my.edu.unikl.gotravel.PLACE_ORIGIN";
    public final static String PLACE_DESTINATION = "my.edu.unikl.gotravel.PLACE_DESTINATION";
    public final static String ORIGIN_NAME = "my.edu.unikl.gotravel.ORIGINNAME";
    public final static String DESTINATION_NAME = "my.edu.unikl.gotravel.DESTINATIONNAME";
    public final static String ORIGIN_LATITUDE = "my.edu.unikl.gotravel.ORIGINLATITUDE";
    public final static String ORIGIN_LONGITUDE = "my.edu.unikl.gotravel.ORIGINLONGITUDE";
    public final static String DESTINATION_LATITUDE = "my.edu.unikl.gotravel.DESTINATIONLATITUDE";
    public final static String DESTINATION_LONGITUDE = "my.edu.unikl.gotravel.DESTINATIONLONGITUDE";

    CoordinatorLayout rootView;
    int images[] = {R.drawable.bg, R.drawable.woman, R.drawable.gopro, R.drawable.heart, R.drawable.beach, R.drawable.eiffel, R.drawable.swing, R.drawable.greece, R.drawable.bigben, R.drawable.japan, R.drawable.sail, R.drawable.summer, R.drawable.london, R.drawable.ballon, R.drawable.young};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

         rootView = (CoordinatorLayout) findViewById(R.id.main_layout);

        // Construct a GoogleApiClient for the {@link Places#GEO_DATA_API} using AutoManage
        // functionality, which automatically sets up the API client to handle Activity lifecycle
        // events. If your activity does not extend FragmentActivity, make sure to call connect()
        // and disconnect() explicitly.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();


        // Retrieve the AutoCompleteTextView that will display Place suggestions.
        originAutocompleteView = (AutoCompleteTextView)
                findViewById(R.id.autocomplete_origin);

        originAutocompleteView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= originAutocompleteView.getRight() - originAutocompleteView.getTotalPaddingRight()) {
                        // your action for drawable click event
                        originAutocompleteView.setText("");
                        originAutocompleteView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        return true;
                    }
                }

                return false;
            }
        });

        destinationAutocompleteView = (AutoCompleteTextView)
                findViewById(R.id.autocomplete_destination);

        destinationAutocompleteView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= destinationAutocompleteView.getRight() - destinationAutocompleteView.getTotalPaddingRight()) {
                        // your action for drawable click event
                        destinationAutocompleteView.setText("");
                        destinationAutocompleteView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        return true;
                    }
                }

                return false;
            }
        });

        // Register a listener that receives callbacks when a suggestion has been selected
        originAutocompleteView.setOnItemClickListener(mOriginAutocompleteClickListener);

        destinationAutocompleteView.setOnItemClickListener(mDestinationAutocompleteClickListener);

        originLatitudeView = (EditText) findViewById(R.id.origin_latitude);
        originLongitudeView = (EditText) findViewById(R.id.origin_longitude);

        destinationLatitudeView = (EditText) findViewById(R.id.destination_latitude);
        destinationLongitudeView = (EditText) findViewById(R.id.destination_longitude);

        // Retrieve the TextViews that will display details and attributions of the selected place.
        mPlaceDetailsText = (TextView) findViewById(R.id.place_details);
        mPlaceDetailsAttribution = (TextView) findViewById(R.id.place_attribution);

        // Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
        // the entire world.
        mAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, BOUNDS_GREATER_SYDNEY,
                null);
        originAutocompleteView.setAdapter(mAdapter);
        destinationAutocompleteView.setAdapter(mAdapter);

        final Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);


        rootView.postDelayed(new Runnable() {
            int i = 0;

            public void run() {


                rootView.setBackgroundResource(images[getRandomNumber()]);
                rootView.postDelayed(this, 15000);

            }
        }, 15000);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.info_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplication().getBaseContext(), AboutActivity.class);

                startActivity(intent);

            }
        });



    }



    private int getRandomNumber() {
        //Note that general syntax is Random().nextInt(n)
        //It results in range 0-4
        //So it should be equal to number of images in images[] array
        return new Random().nextInt(14);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();


        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data API
     * to retrieve more details about the place.
     *
     * @see com.google.android.gms.location.places.GeoDataApi#getPlaceById(com.google.android.gms.common.api.GoogleApiClient,
     * String...)
     */
    private AdapterView.OnItemClickListener mOriginAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            originAutocompleteView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.close,0);
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mOriginUpdatePlaceDetailsCallback);


            Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
        }
    };

    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data API
     * to retrieve more details about the place.
     *
     * @see com.google.android.gms.location.places.GeoDataApi#getPlaceById(com.google.android.gms.common.api.GoogleApiClient,
     * String...)
     */
    private AdapterView.OnItemClickListener mDestinationAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            destinationAutocompleteView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.close,0);
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mDestinationUpdatePlaceDetailsCallback);


            Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mOriginUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);

            origin = place.getName().toString();

            // Format details of the place for display and show it in a TextView.
            mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                    place.getId(), place.getAddress(), place.getPhoneNumber(),
                    place.getWebsiteUri(), place.getLatLng()));

            originLatitudeView.setText(String.valueOf(place.getLatLng().latitude));
            originLongitudeView.setText(String.valueOf(place.getLatLng().longitude));

            // Display the third party attributions if set.
            final CharSequence thirdPartyAttribution = places.getAttributions();
            if (thirdPartyAttribution == null) {
                mPlaceDetailsAttribution.setVisibility(View.GONE);
            } else {
                mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
                mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
            }

            Log.i(TAG, "Place details received: " + place.getName());

            places.release();
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mDestinationUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);
            destination = place.getName().toString();
            System.out.println("DESTINATION 888 -> " + place.getName() + " ");
            // Format details of the place for display and show it in a TextView.
            mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                    place.getId(), place.getAddress(), place.getPhoneNumber(),
                    place.getWebsiteUri(), place.getLatLng()));

            destinationLatitudeView.setText(String.valueOf(place.getLatLng().latitude));
            destinationLongitudeView.setText(String.valueOf(place.getLatLng().longitude));

            // Display the third party attributions if set.
            final CharSequence thirdPartyAttribution = places.getAttributions();
            if (thirdPartyAttribution == null) {
                mPlaceDetailsAttribution.setVisibility(View.GONE);
            } else {
                mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
                mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
            }

            Log.i(TAG, "Place details received: " + place.getName());

            places.release();
        }
    };



    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri, LatLng latLng) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri, latLng.latitude, latLng.longitude));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri,latLng.latitude, latLng.longitude));

    }

    /**
     * Called when the Activity could not connect to Google Play services and the auto manager
     * could resolve the error automatically.
     * In this case the API is not available and notify the user.
     *
     * @param connectionResult can be inspected to determine the cause of the failure
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }


    public void searchResultActivity(View view) {

        Snackbar snackbar = Snackbar.make(rootView, "", Snackbar.LENGTH_LONG);

        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);


        if (originAutocompleteView.length() == 0 && destinationAutocompleteView.length() == 0){

            snackbar.setText("Origin and Destination cannot be empty");

            snackbar.show();


        } else if (originAutocompleteView.length() == 0 || destinationAutocompleteView.length() == 0){

            if (originAutocompleteView.length() == 0) {


                snackbar.setText("Origin cannot be empty");

                snackbar.show();


            } else if (destinationAutocompleteView.length() == 0) {


                snackbar.setText("Destination cannot be empty");

                snackbar.show();


            }

        } else {

            Intent intent = new Intent(this, SearchResultActivity.class);
            EditText originLatitudeText = (EditText) findViewById(R.id.origin_latitude);
            String originLatitude = originLatitudeText.getText().toString();
            EditText originLongitudeText = (EditText) findViewById(R.id.origin_longitude);
            String originLongitude = originLongitudeText.getText().toString();

            EditText destinationLatitudeText = (EditText) findViewById(R.id.destination_latitude);
            String destinationLatitude = destinationLatitudeText.getText().toString();
            EditText destinationLongitudeText = (EditText) findViewById(R.id.destination_longitude);
            String destinationLongitude = destinationLongitudeText.getText().toString();

            intent.putExtra(ORIGIN_LATITUDE, originLatitude);
            intent.putExtra(ORIGIN_LONGITUDE, originLongitude);
            intent.putExtra(DESTINATION_LATITUDE, destinationLatitude);
            intent.putExtra(DESTINATION_LONGITUDE, destinationLongitude);
            intent.putExtra(ORIGIN_NAME, origin.replaceAll(",", "").replaceAll(" ", "+"));
            intent.putExtra(DESTINATION_NAME, destination.replaceAll(",","").replaceAll(" ","+"));

            startActivity(intent);
        }

    }

}