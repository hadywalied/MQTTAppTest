package com.lol.mqttapptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MainActivity extends AppCompatActivity {

    EditText et_clintId, et_server, et_port, et_topic, et_username, et_password;
    LinearLayout ll_btns, ll_textview;
    TextView tv_messages;
    RadioGroup rg_qos;
    MqttAndroidClient client;
    MqttConnectOptions options;
    //the main parameters with default values
    private String SERVER = "iot.eclipse.org";
    private String USERNAME;
    private String PASSWORD;
    private String Topic;
    private String Port = "1883";
    private String client_id = MqttClient.generateClientId();
    private int qos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*get the views from the design*/
        et_clintId = findViewById(R.id.et_clientid);
        et_port = findViewById(R.id.et_port);
        et_server = findViewById(R.id.et_server);
        et_topic = findViewById(R.id.et_topic);
        ll_btns = findViewById(R.id.ll_btns);
        ll_textview = findViewById(R.id.ll_textview);
        tv_messages = findViewById(R.id.tv_messages);
        rg_qos = findViewById(R.id.rg_qos);


    }//onCreate

    /*to send a Hello message*/
    public void publishHello(View v) {

        String topic = Topic;
        String Message = "Hello";
        try {

            client.publish(topic, Message.getBytes(), qos, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }//end try catch

    }//publishHello


    /*to send Bye message*/
    public void publishBye(View v) {

        String topic = Topic;
        String Message = "Bye";
        try {
            client.publish(topic, Message.getBytes(), qos, false);

        } catch (MqttException e) {
            e.printStackTrace();
        }//end try catch

    }//publishBye

    private void setSubscription() {
        try {
            client.subscribe(Topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }//end try catch

    }//setSubscription

    public void connect() {
        try {
            /* get the data from the text fields and the radio group*/
            if (et_topic.getText().toString().matches(""))
                Toast.makeText(this, "enter the information in the fields, please", Toast.LENGTH_SHORT).show();
            else Topic = et_topic.getText().toString();

            if (et_clintId.getText().toString().matches(""))
                Toast.makeText(this, "enter the information in the fields, please", Toast.LENGTH_SHORT).show();
            else client_id = et_clintId.getText().toString();

            if (et_server.getText().toString().matches(""))
                Toast.makeText(this, "enter the information in the fields, please", Toast.LENGTH_SHORT).show();
            else SERVER = et_server.getText().toString();

            if (et_port.getText().toString().matches(""))
                Toast.makeText(this, "enter the information in the fields, please", Toast.LENGTH_SHORT).show();
            else Port = et_port.getText().toString();

            USERNAME = et_username.getText().toString();

            PASSWORD = et_password.getText().toString();


            switch (rg_qos.getCheckedRadioButtonId()) {
                case R.id.rb_0:
                    qos = 0;
                    break;
                case R.id.rb_1:
                    qos = 1;
                    break;
                case R.id.rb_2:
                    qos = 2;
                    break;
                default:
                    qos = 0;
            }

            //setting the options
            options = new MqttConnectOptions();
            options.setUserName(USERNAME);
            options.setPassword(PASSWORD.toCharArray());

            //setting the client
            client = new MqttAndroidClient(this.getApplicationContext(), SERVER + ":" + Port, client_id);

            //initiating the connection
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(MainActivity.this, "Connected!!", Toast.LENGTH_LONG).show();
                    setSubscription();
                    ll_btns.setVisibility(View.VISIBLE);
                    ll_textview.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(MainActivity.this, "Connection Failed!", Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }//end try catch for connection check

        //running callBacks for receiving data
        callBacks();

    }//connect

    public void disconnect() {

        try {
            IMqttToken token = client.disconnect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(MainActivity.this, "disconnected!!", Toast.LENGTH_LONG).show();
                    ll_btns.setVisibility(View.GONE);
                    ll_textview.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(MainActivity.this, "disconnection Failed!", Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }//end try catch for connection check

    }//disconnect

    void callBacks() {

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                //add the text view that shows the Message recieved
                tv_messages.append('\n' + topic + ": " + message.toString());

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });//end setCallback

    }//CallBacks

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }//onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect:
                connect();
                return true;
            case R.id.disconnect:
                disconnect();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }//onOptionsItemSelected
}
