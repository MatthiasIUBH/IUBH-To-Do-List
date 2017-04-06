package com.example.m21219.logintest;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.Locale;

/*Diese Klasse bzw. Activity wird aufgerufen, wenn der User einen gespeicherten Eintrag anklickt. Anschließend hat man die Möglichkeit
alle gespeicherten Werte zu ändern und zu speichern. Wir implentieren ebenfalls wie in der "TodoCreate-Activity" die Schnitstellen "TextWatcher",
da wir Text eingeben und bearbeiten, "DatePickerDialog.OnDateSetListener" und "TimePickerDialog.OnTimeSetListener,
damit werden die Dialogfenster mit Kalender und Uhr aufgerufen.*/
public class ToDoDetailActivity extends AppCompatActivity implements TextWatcher, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, OnMapReadyCallback, LocationListener{


    public static final String TODO_ID_KEY = "TODO";

    private EditText name;
    private TextView completiondate;
    private TextView completiontime;
    private EditText description;
    private CheckBox favorite;
    private CheckBox completionstatus;
    private Button update_button;

    public Button currentPosition1;
    public GoogleMap map;
    private Marker marker;
    private LocationManager locationManager;

    private ToDo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_detail);

        long id = getIntent().getLongExtra(TODO_ID_KEY, 0);
        this.todo = TodoDatabase.getInstance(this).readToDo(id);

        this.currentPosition1 = (Button) findViewById(R.id.currentPosition1);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        update_button = (Button) findViewById(R.id.update_button);
        name = (EditText) findViewById(R.id.name);
        completiondate = (TextView) findViewById(R.id.completiondate);
        completiontime = (TextView) findViewById(R.id.completiontime);
        description = (EditText) findViewById(R.id.description);
        favorite = (CheckBox) findViewById(R.id.favorite);
        completionstatus = (CheckBox) findViewById(R.id.completionstatus);

        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (this.currentPosition1 != null) {
            this.currentPosition1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    searchPosition();
                }
            });
        }

        name.setText(todo.getName());
        completiondate.setText(todo.getCompletiondate() == null ? "-" : getDateInString(todo.getCompletiondate()));
        completiontime.setText(todo.getCompletiontime() == null ? "-" : getTimeInString(todo.getCompletiontime()));
        description.setText(todo.getDescription() == null ? "-" : todo.getDescription());
        favorite.setChecked(todo.isFavorite());
        completionstatus.setChecked(todo.isCompletionstatus());

        //OnClickListener für jeweils Kalender- und Uhranzeige.
        this.completiondate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        this.completiontime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        //Listener für Texteingabe
        this.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                todo.setName(editable.toString().length() == 0 ? null : editable.toString());
            }
        });

        this.description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                todo.setDescription(editable.toString().length() == 0 ? null : editable.toString());
            }
        });

        this.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                todo.setFavorite(b);
            }
        });
        this.completionstatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                todo.setCompletionstatus(b);
            }
        });



        this.update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (todo.getName() == null) { //Die Prüfung, ob der Name des Eintrags "null" ist oder nicht.
                    Toast.makeText(ToDoDetailActivity.this, "Fehler beim Speichern, bitte noch einen Namen eingeben.", Toast.LENGTH_SHORT).show();
                    return;
                }

                TodoDatabase.getInstance(ToDoDetailActivity.this).updateToDo(todo);
                finish();
            }
        });

    }

    private void searchPosition() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermission(5);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        if (this.todo != null && this.todo.getLocation() != null) {
            googleMap.addMarker(new MarkerOptions().position(todo.getLocation()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(todo.getLocation(), 15));
       }else {
        this.map = googleMap;
        }
    }


    @Override
    public void onLocationChanged(final Location location) {
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
        todo.setLocation(position);

       if (this.map != null) {
           if (this.marker != null) {
               this.marker.remove();

            }

           this.marker = map.addMarker(new MarkerOptions().position(position));
           this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
        }

        removeListener();
    }

    private void removeListener() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermission(4);
        }
        this.locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(final String s, final int i, final Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(final String s) {

    }

    @Override
    public void onProviderDisabled(final String s) {

    }

    private void requestPermission(final int resultCode) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, resultCode);
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        switch (requestCode) {
            case 5:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    searchPosition();
                }
                break;
            case 4:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    removeListener();
                }
                break;
        }
    }


    @Override
    public void onDateSet(final DatePicker datePicker, final int i, final int i1, final int i2) {
        this.completiondate.setText(String.format(Locale.GERMANY, "%02d.%02d.%d", i2, i1 + 1, i)); //Datumsformat festlegen

        Calendar c = Calendar.getInstance();
        c.set(i, i1, i2);

        todo.setCompletiondate(c); //Das Systemdatum wird als Defaultwert genommen.
    }

    @Override
    public void onTimeSet(final TimePicker timePicker, final int hourOfDay, final int minute){
        this.completiontime.setText(String.format(Locale.GERMANY, "%02d:%02d", hourOfDay, minute)); //Zeitformatfestlegen

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        todo.setCompletiontime(c); //Die Systemzeit wird als Defaultwert genommen.
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    //Hier wird das Datum in String umgewandelt.
    private String getDateInString(Calendar calendar) {
        return String.format(Locale.GERMANY, "%02d.%02d.%d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
    }

    //Hier wird die Uhrzeit in String umgewandelt.
    private String getTimeInString(Calendar time){
        return String.format(Locale.GERMANY, "%02d:%02d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE));
    }

}
