package edu.washington.tg71223.awty;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class PingMessageService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_START = "edu.washington.tg71223.awty.action.START";

    // TODO: Rename parameters
    public static final String EXTRA_MESSAGE_TEXT = "messageText";
    public static final String EXTRA_MESSAGE_NUMBER = "numberText";

    private Handler mHandler;
    private String messageText;
    private String messageNumber;

    public PingMessageService() {
        super("PingMessageService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("PingService", "Started Service");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                this.messageText = intent.getStringExtra(EXTRA_MESSAGE_TEXT);
                this.messageNumber = intent.getStringExtra(EXTRA_MESSAGE_NUMBER);
                handlePing(messageNumber);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handlePing(final String messageNumber) {
        // TODO: Handle action Ping
        Runnable task = new Runnable() {
            @Override
            public void run() {
                Log.i("PingService", "Sent Message");
                Toast.makeText(PingMessageService.this, "Sending text to number: " + messageNumber, Toast.LENGTH_SHORT).show();
            }
        };
        mHandler.post(task);
    }

}
