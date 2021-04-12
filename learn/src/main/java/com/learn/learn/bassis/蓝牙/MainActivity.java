package com.learn.learn.bassis.蓝牙;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.learn.learn.R;

import java.util.Set;

import androidx.annotation.Nullable;

/**
 * 传统蓝牙的使用
 */
public class MainActivity extends Activity {

    private final int REQUEST_ENABLE = 101;
    private String TAG = "蓝牙使用 ";

    private static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    public static void intentTo(Context context) {
        context.startActivity(newIntent(context));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        //启动设备发现，查询配对的设备列表，使用已知的MAC地址实例化一个BluetoothDevice类，并创建一个
        //BluetoothServerSocket监听来自其他设备的连接请求。
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //搜索蓝牙设备，该过程是异步的，通过下面注册广播接受者，可以监听是否搜到设备。
        //通过广播接收者查看扫描到的蓝牙设备，每扫描到一个设备，系统都会发送此广播
        IntentFilter filter = new IntentFilter();
        //发现设备
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        //设备连接状态改变
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        //蓝牙设备状态改变
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        //注册发现蓝牙广播
        registerReceiver(mBluetoothReceiver, filter);

        findViewById(R.id.btnOpenBluetooth).setOnClickListener(v -> {
            //        开启蓝牙
            if (!mBluetoothAdapter.isEnabled()) {
                //弹出对话框提示用户是后打开
                Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enabler, REQUEST_ENABLE);
                //不做提示，直接打开，不建议用下面的方法，有的手机会有问题。
                // mBluetoothAdapter.enable();
            }
        });
        //获取本机蓝牙名称
        String name = mBluetoothAdapter.getName();
        //获取本机蓝牙地址
        String address = mBluetoothAdapter.getAddress();
        Log.d(TAG, "bluetooth name =" + name + " address =" + address);
        //获取已配对蓝牙设备
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        Log.d(TAG, "bonded device size =" + devices.size());
        for (BluetoothDevice bonddevice : devices) {
            Log.d(TAG, "bonded device name =" + bonddevice.getName() + " address" + bonddevice.getAddress());
        }
        //开始蓝牙扫描
        findViewById(R.id.btnSearchBluetooth).setOnClickListener(v -> mBluetoothAdapter.startDiscovery());
        //停止蓝牙扫描
        findViewById(R.id.btnStopBluetooth).setOnClickListener(v -> mBluetoothAdapter.cancelDiscovery());
        //设置本机蓝牙可见
        findViewById(R.id.setBluetoothVisible).setOnClickListener(v -> {
            if (mBluetoothAdapter.isEnabled()) {
                if (mBluetoothAdapter.getScanMode() !=
                        BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    Intent discoverableIntent = new Intent(
                            BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(
                            BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
                    startActivity(discoverableIntent);
                }
            }
        });
    }

    //获取未配对设备
    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "mBluetoothReceiver action =" + action);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {//每扫描到一个设备，系统都会发送此广播。
                //获取蓝牙设备
                BluetoothDevice scanDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (scanDevice == null || scanDevice.getName() == null) return;
                Log.d(TAG, "name=" + scanDevice.getName() + "address=" + scanDevice.getAddress());
                //蓝牙设备名称
//                String name = scanDevice.getName();
//                if(name != null && name.equals(BLUETOOTH_NAME)){
//                    mBluetoothAdapter.cancelDiscovery();
//                    //取消扫描
//                    mProgressDialog.setTitle(getResources().getString(R.string.progress_connecting));                   //连接到设备。
//                    mBlthChatUtil.connect(scanDevice);
//                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

            }
        }
    };
}
