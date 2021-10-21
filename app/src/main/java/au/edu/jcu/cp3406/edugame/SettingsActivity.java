package au.edu.jcu.cp3406.edugame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    public static String set_seconds = "25";
    private MediaPlayer sound_background;
    public static Boolean sound_on = false;
    public static String player_name = "Player 1";
    private float accelerometer;
    private float accelerometer_latest_value;
    private float accelerometer_last_value;
    private String error_message;
    public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        preferences = getSharedPreferences("config", MODE_PRIVATE);

        AppCompatDelegate.setDefaultNightMode(preferences.getInt("default night mode",
                AppCompatDelegate.MODE_NIGHT_UNSPECIFIED));

        sound_background = MediaPlayer.create(this, android.provider.Settings.System.DEFAULT_RINGTONE_URI);

        EditText text = findViewById(R.id.seconds_textbox);

        final SwitchCompat switchMusic = findViewById(R.id.switch_music);
        text.setText(set_seconds);
        switchMusic.setChecked(sound_on);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(sensorManager).registerListener(
                sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        accelerometer_latest_value = SensorManager.GRAVITY_EARTH;
        accelerometer_last_value = SensorManager.GRAVITY_EARTH;
        accelerometer = 10f;

        //dark_mode background check

//        switchBackground.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                    if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    }
//                    else{
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    }
//                });

//      Music switch setup
        switchMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sound_on = isChecked;
            switchMusic.setChecked(sound_on);
            Toast.makeText(getApplicationContext(), "Music: " + switchMusic.isChecked(), Toast.LENGTH_SHORT).show();
            setSound(sound_on);
        });
    }

    public void done(View view) {
        if (validate()) {
            finish();
        } else{
            Toast.makeText(this, error_message, Toast.LENGTH_SHORT).show();
        }
    }

//    getting input for timer
    public boolean validate() {
        EditText text = findViewById(R.id.seconds_textbox);
        set_seconds = text.getText().toString();

        EditText text_player_name = findViewById(R.id.player_name);
        player_name = text_player_name.getText().toString();

        return validate_entries(set_seconds, player_name);
    }

//    exception handling for timer input
    public boolean validate_entries(String set_seconds, String player_name) {

        if (set_seconds.equals("")) {
            error_message = "Enter in seconds";
            return false;
        } else if (Integer.parseInt(set_seconds) <= 0 | Integer.parseInt(set_seconds) > 1800) {
            error_message = "Invalid Entry. Enter between 0 and 1800";
            return false;
        } else if (player_name.equals("")) {
            error_message = "Enter your name";
            return false;
        } else {
            return true;
        }
    }

    public void setSound(Boolean isSoundOn) {

        if (isSoundOn) {
            if (sound_background.isPlaying()) {
                sound_background.stop();
            }
            sound_background = MediaPlayer.create(getApplicationContext(), R.raw.bgm);
            sound_background.setLooping(true);
            sound_background.start();
        } else {
            sound_background.stop();
        }
    }

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float change_in_position;
            float accelerometer_x = event.values[0];
            float accelerometer_y = event.values[1];
            float accelerometer_z = event.values[2];

            accelerometer_last_value = accelerometer_latest_value;
            accelerometer_latest_value = (float) Math.sqrt(accelerometer_x * accelerometer_x
                    + accelerometer_y * accelerometer_y
                    + accelerometer_z * accelerometer_z);

            change_in_position = accelerometer_latest_value - accelerometer_last_value;
            accelerometer = accelerometer * 0.9f + change_in_position;

            // go to Main Screen
            if (accelerometer > 9.81) {
                navigateUpTo(new Intent(getBaseContext(), MainActivity.class));
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

}
