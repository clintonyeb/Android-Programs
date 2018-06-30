package com.example.holys.backendlessdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    /*final String SEC_KEY = "54074F35-D5E3-77BF-FF6B-6029828D1A00";
    final String APP_ID = "8235EF19-054A-877D-FF59-58E7F86AA200";
    BackendlessUser mUser;*/
    TextView view;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://developer.android.com/training/volley/requestqueue.html";
        view = (TextView) findViewById(R.id.textView);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse (String response) {

                        view.setText(response.substring(0, 500));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse (VolleyError error) {
                        view.setText("Failed brother");
                    }
    });
        queue.add(stringRequest);



       /* String apVersion = "v1";
        Backendless.initApp(this, APP_ID, SEC_KEY, apVersion);

       *//* mUser = new BackendlessUser();
        mUser.setEmail("biblecorner29@gmail.com");
        mUser.setPassword("password1");*//*
        //CreateNewUser();
        Register();
        DeliveryOptions deliveryOptions = new DeliveryOptions();
        deliveryOptions.setPushPolicy(PushPolicyEnum.ONLY);
        deliveryOptions.setPushBroadcast(PushBroadcastMask.ALL);
        Backendless.Messaging.publish("default", "Clinton", new AsyncCallback<MessageStatus>() {
            @Override
            public void handleResponse (MessageStatus response) {
                Log.i("OUTPUT", "success");
            }

            @Override
            public void handleFault (BackendlessFault fault) {
                Log.i("OUTPUT", fault.getMessage());
            }
        });*/

        /*Backendless.Messaging.subscribe(new AsyncCallback<List<Message>>() {
            @Override
            public void handleResponse (List<Message> response) {
                Log.i("OUTPUT", "success");
            }

            @Override
            public void handleFault (BackendlessFault fault) {
                Log.i("OUTPUT", fault.getMessage());
            }
        });*/
       /* Backendless.Messaging.subscribe( new AsyncCallback<List<Message>>()
        {
            @Override
            public void handleResponse( List<Message> messages )
            {
                Iterator<Message> messageIterator = messages.iterator();
                while( messageIterator.hasNext() )
                {
                    Message message = messageIterator.next();
                    Log.i( "OUTPUT", "Received message - " + message.getData() );
                }
            }
            @Override
            public void handleFault( BackendlessFault backendlessFault )
            {
                Log.i("OUTPUT", "Server reported an error " + backendlessFault.getMessage() );
            }
        } );
*/

    }

    /*public void CreateNewUser () {
        Backendless.UserService.register(mUser, new BackendlessCallback<BackendlessUser>() {
            @Override
            public void handleResponse (BackendlessUser response) {
                Log.i("Registration", response.getEmail() + " successfully registered");
            }
        });
    }
    public void onClick (View view) {
        Backendless.Persistence.save(new Comment("Am in!", mUser.getEmail()), new BackendlessCallback<Comment>() {
            @Override
            public void handleResponse (Comment response) {
                Log.i("COMMENT", "Got new comments from " + mUser.getEmail());
            }
        });
    }

    public void loginClick (View veiw) {
        Backendless.UserService.login("biblecorner29@gmail.com", "password1", new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse (BackendlessUser response) {
                Log.i("OUTPUT", "logg in success");
            }

            @Override
            public void handleFault (BackendlessFault fault) {
                Log.i("OUTPUT", "failed " + fault.getCode() + fault.getMessage());
            }
        });
    }
    public void registerClick(View view)
    {
        Register();
    }

    private void Register()
    {
        Backendless.Messaging.registerDevice("571238208314", "default", new AsyncCallback<Void>() {
            @Override
            public void handleResponse (Void response) {
                Log.i("OUTPUT", "device registered");
            }

            @Override
            public void handleFault (BackendlessFault fault) {
                Log.i("OUTPUT", "failed " + fault.getMessage());
            }
        });
    }*/
}
