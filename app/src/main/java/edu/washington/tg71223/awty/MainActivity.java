package edu.washington.tg71223.awty;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Boolean started = false;
    private AlarmManager alarm;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        alarm  = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);

        //Button Logic
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!started && testNumber() && testInterval()) {
                    // Get info from edittexts
                    EditText messageText = (EditText) findViewById(R.id.messageText);
                    EditText numberText = (EditText) findViewById(R.id.numberText);
                    EditText intervalText = (EditText) findViewById(R.id.intervalNumber);

                    // Build up intent
                    intent = new Intent(getApplicationContext(), PingMessageService.class);
                    intent.putExtra("messageText", messageText.getText().toString());
                    intent.putExtra("numberText", numberText.getText().toString());
                    intent.setAction("edu.washington.tg71223.awty.action.START");

                    // Set up repeating alarm
                    int alarmInterval = 1000 * 60 * Integer.decode(intervalText.getText().toString());
                    PendingIntent pending = PendingIntent.getService(getApplicationContext(), 0, intent, 0);
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), alarmInterval, pending);

                    // Change button/state logic
                    started = true;
                    Button button = (Button) findViewById(R.id.button);
                    button.setText("Stop");
                } else {
                    PendingIntent pending = PendingIntent.getService(getApplicationContext(), 0, intent, 0);
                    alarm.cancel(pending);
                    Toast.makeText(MainActivity.this, "Repeated texts have been stopped", Toast.LENGTH_SHORT).show();

                    // Change button/state logic
                    started = false;
                    Button button = (Button) findViewById(R.id.button);
                    button.setText("Start");
                }
            }
        });
    }

    private boolean testNumber() {
        EditText phoneText = (EditText) findViewById(R.id.numberText);
        if(phoneText.getText().toString().length() == 10 && phoneText.getText().toString().matches("[0-9]+")) {
            return true;
        } else {
            Toast.makeText(this, "Phone number must be a 10 digit number", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean testInterval() {
        EditText intervalText = (EditText) findViewById(R.id.intervalNumber);
        if(intervalText.getText().toString().matches("[0-9]+")) {
            return true;
        } else {
            Toast.makeText(this, "Interval must be a positive, whole integer", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
