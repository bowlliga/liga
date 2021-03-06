package com.example.test;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Button btnParents, btnKid, connWear;

    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;

    Handler mBluetoothHandler;
    ConnectedBluetoothThread mThreadConnectedBluetooth;
    BluetoothDevice mBluetoothDevice;
    BluetoothSocket mBluetoothSocket;

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnParents = findViewById(R.id.btnParents);//부모 추가버튼
        btnKid = findViewById(R.id.btnKid);//아이 추가버튼
        connWear = findViewById(R.id.connWear);//웨어러블 연결


        //부모 추가 버튼 클릭시
        btnParents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        "친구(기기) 추가 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        //아이 추가 버튼 클릭시
        btnKid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        "친구(기기) 추가 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        //기기가 블루투스를 지원하는지 알아오는 메소드
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        //웨어러블 연결 클릭시
        connWear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPairedDevices();
            }

        });
    }//onCreate

    //해당 기기에 페어링 되어있는 블루투스 기기 목록을 가져오는 메소드
    void listPairedDevices(){
        if(mBluetoothAdapter.isEnabled()) { //블루투스가 활성화 되어 있으면
            mPairedDevices = mBluetoothAdapter.getBondedDevices();

            if (mPairedDevices.size() > 0) { //페어링 되어 있는 기기가 있으면
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("연결할 기기 선택");

                mListPairedDevices = new ArrayList();
                for (BluetoothDevice device : mPairedDevices) {
                    mListPairedDevices.add(device.getName());
                }
                final CharSequence[] items = mListPairedDevices
                        .toArray(new CharSequence[mListPairedDevices.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        connectSelectDevice(items[item].toString());
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "페어링된 기기가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "블루투스가 비활성화 되어있습니다.", Toast.LENGTH_SHORT).show();
        }
    }//listPairedDevices()

    //블루투스 연결 하는 메소드
    void connectSelectDevice(String selectedDeviceName) {
        for(BluetoothDevice tempDevice : mPairedDevices){
            mBluetoothDevice = tempDevice;
            break;
        }
        try {
            mBluetoothSocket = mBluetoothDevice.createInsecureRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket.connect();
            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
            mThreadConnectedBluetooth.start();
            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "블루투수 연결 중 오류..", Toast.LENGTH_SHORT).show();
        }
    }//connectSelectDevice()

    private class ConnectedBluetoothThread extends Thread{
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedBluetoothThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        SystemClock.sleep(100);
                        bytes = mmInStream.available();
                        bytes = mmInStream.read(buffer, 0, bytes);
                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }
        public void write(String str) {
            byte[] bytes = str.getBytes();
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}