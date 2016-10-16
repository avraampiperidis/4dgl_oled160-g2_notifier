package com.example.abraham.iNotify;

import android.annotation.TargetApi;
import android.app.Notification;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

/**
 * Created by abraham on 16/2/2015.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NLService extends NotificationListenerService {

    private void makeText(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


    private static BluetoothAdapter mBluetoothAdapter;
    private static BluetoothSocket mmSocket;
    private static BluetoothDevice mmDevice;
    private static OutputStream mmOutputStream;

    private static boolean inputKeyboard = false;
    private static boolean notifyRead = false;

    private String TAG = this.getClass().getSimpleName();


    public void onCreate() {

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null) {
            makeText("Device does not support Bluetooth! Exiting!");
            return;
        }
        else {

            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
                makeText("Bluetooth Enabled!");
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            for (BluetoothDevice device : pairedDevices) {
                //HC is the remote bluetooth module
                if (device.getName().startsWith("HC")) {
                    mmDevice = device;
                    break;
                }
            }

            //Standard //SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

            try {
                mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
                mmSocket.connect();
                mmOutputStream = mmSocket.getOutputStream();

                TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String carrierName = manager.getNetworkOperatorName();
                if(carrierName == null) {
                    makeText("No telephony support!");
                } else {
                        mmOutputStream.write(carrierName.getBytes());
                        mmOutputStream.write("   iNotify               ".getBytes());
                }

            } catch(Exception ex) {
                makeText("No telephony support!");
            }

        }

        //every 1 minute send time
        new Thread() {
            public void run() {

                while (true) {

                    try {
                        Thread.sleep(60000);

                        if (notifyRead == false) {

                            Calendar c = Calendar.getInstance();
                            int minutes = c.get(Calendar.MINUTE);
                            int hour = c.get(Calendar.HOUR);
                            String time = hour + ":" + minutes;

                            try {
                                mmOutputStream.write('~');
                                mmOutputStream.write(time.getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();


    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // '`' <-cleanMonitor!
        Notification mNotification = sbn.getNotification();
        if(mNotification != null) {

            String titleContent = mNotification.extras.getString(Notification.EXTRA_TITLE);

            if(titleContent.contains("Επιλογή")) {
                inputKeyboard = true;
            }else if(titleContent.contains("Εγκατ.") || titleContent.contains("Install software")) {
            }
            else if(titleContent.contains("σύνδεση στο PC") || titleContent.contains("Connected to PC")) {
            } else if(titleContent.contains("Συνδ. εφαρμ.") || titleContent.contains("USB deb")) {
            } else if(mNotification.extras.getString(Notification.EXTRA_TEXT).contains("Εισερχόμενη") || mNotification.extras.getString(Notification.EXTRA_TEXT).contains("incoming call")) {
                try {
                    Thread.sleep(2000);
                    mmOutputStream.write('>');
                    mmOutputStream.write(Utilities.splitStringNewLine(Utilities.convertEltoEn(mNotification.extras.getString(Notification.EXTRA_TEXT))).getBytes());
                    Thread.sleep(400);
                    mmOutputStream.write('>');
                    mmOutputStream.write(Utilities.splitStringNewLine(Utilities.convertEltoEn(mNotification.extras.getString(Notification.EXTRA_TITLE))).getBytes());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(mNotification.extras.getString(Notification.EXTRA_TITLE).contains("Αναπάντητη")) {
                try {
                    mmOutputStream.write('>');
                    mmOutputStream.write(Utilities.splitStringNewLine(Utilities.convertEltoEn("Anapantiti Klisi!")).getBytes());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mmOutputStream.write('>');
                    mmOutputStream.write(Utilities.splitStringNewLine(Utilities.convertEltoEn(mNotification.extras.getString(Notification.EXTRA_TEXT))).getBytes());
                    notifyRead = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                //could be anything else from sms to facebook/tweeter notification
                try {
                    mmOutputStream.write('>');
                    mmOutputStream.write(Utilities.splitStringNewLine(Utilities.convertEltoEn(mNotification.extras.getString(Notification.EXTRA_TITLE))).getBytes());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mmOutputStream.write('>');
                    mmOutputStream.write(Utilities.splitStringNewLine(Utilities.convertEltoEn(mNotification.extras.getString(Notification.EXTRA_TEXT))).getBytes());
                    notifyRead = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }

    }


    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        if(inputKeyboard == true) {
            inputKeyboard = false;
        } else {
            try {
                notifyRead = false;
                mmOutputStream.write('`');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static  void sendMessage(String s) throws IOException {
        try {
            //String test = "this is a test Stri";
            mmOutputStream.write('>');
            mmOutputStream.write(Utilities.splitStringNewLine(Utilities.convertEltoEn(s)).getBytes());
            //mmOutputStream.close();
            //mmSocket.close();
        } catch (IOException ex) {

        }

    }



}
