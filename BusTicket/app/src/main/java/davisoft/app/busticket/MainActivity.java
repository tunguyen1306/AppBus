package davisoft.app.busticket;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.hardware.usb.UsbConfiguration;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import davisoft.app.busticket.adapter.USBAdapter;
import davisoft.app.busticket.data.ControlDatabase;
import davisoft.app.busticket.data.ResClien;
import davisoft.app.busticket.data.pojo.DmTaiXe;
import davisoft.app.busticket.data.pojo.DmTram;
import davisoft.app.busticket.printer.PrintOrder;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {


    private UsbManager mUsbManager;
    private UsbDevice mDevice;
    private PendingIntent mPermissionIntent;
    UsbDeviceConnection connection;
    String TAG = "USB";
    private static final String ACTION_USB_PERMISSION =
            "davisoft.app.busticket.USB_PERMISSION";



    final Context context = this;
    public static List<Integer> dataTickets=new ArrayList<Integer>();
    public  List<DmTaiXe> dmTaixe=new ArrayList<>();
    private int orientation = Configuration.ORIENTATION_LANDSCAPE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initData();
        initEvent();
        initUSB();
        CallDmTaiXe();
        CallDmTram();

    }
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    mDevice = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if(mDevice != null){
                            //call method to set up device communication
                        }
                    }
                    else {
                        Log.d(TAG, "permission denied for device " + mDevice);
                    }
                }
            }
        }
    };

    private  void initUSB()
    {
        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        mPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        context.registerReceiver(mUsbReceiver, filter);
        for (UsbDevice usb: mUsbManager.getDeviceList().values())
        {
            if (usb.getVendorId() == 1155 && usb.getProductId()==22339)
            {
                mDevice = usb;
                break;
            }
        }
        if (mDevice!=null)
        {
            if (!mUsbManager.hasPermission(mDevice))
            {
                mUsbManager.requestPermission(mDevice, mPermissionIntent);
            }
        }


    }

    private void print()
    {
        if (mDevice!=null && mUsbManager.hasPermission(mDevice))
        {
            UsbInterface intf = mDevice.getInterface(0);
            for (int i = 0; i < intf.getEndpointCount(); i++) {
                UsbEndpoint ep = intf.getEndpoint(i);
                if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                    if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {
                        final UsbEndpoint mEndpointBulkOut = ep;
                        connection = mUsbManager.openDevice(mDevice);
                        if(connection!=null)
                        {
                            Log.e("Connection:"," connected");
                            Toast.makeText(context, "Device connected", Toast.LENGTH_SHORT).show();
                        }
                        boolean forceClaim = true;
                        connection.claimInterface(intf, forceClaim );

                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                // TODO Auto-generated method stub
                                Log.i("Thread:", "in run thread");
                                String s= new PrintOrder().testData();
                                byte[] array= s.getBytes(StandardCharsets.UTF_8);
                                s=new String(array,StandardCharsets.ISO_8859_1);
                                array=s.getBytes();
                                Integer b = connection.bulkTransfer(mEndpointBulkOut, array, array.length, 10000);
                                Log.i("Return Status", "b-->" + b);

                            }
                        }).start();

                        connection.releaseInterface(intf);
                        break;
                    }
                }
            }
        }
    }

    private void testPrinter()
    {
        Toast.makeText(this,"Printer",Toast.LENGTH_LONG).show();
        String msg = "This is a test message";
        PrintOrder printer = new PrintOrder();
        printer.Print(this,msg);
    }

    private void  initEvent()
    {

        findViewById(R.id.layout_popup).setAlpha(0f);
        findViewById(R.id.button_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                print();
                findViewById(R.id.layout_popup).animate()
                        .translationY(0)
                        .alpha(1.0f)
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);

                            }
                        });

            }
        });


        findViewById(R.id.layout_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.animate()
                        .translationY(v.getHeight())
                        .alpha(0.0f)
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);

                            }
                        });
            }
        });



    }

    private  void  initData()
    {
        MainActivity.dataTickets.clear();
        MainActivity.dataTickets.add(1);
        MainActivity.dataTickets.add(2);
        MainActivity.dataTickets.add(3);
        MainActivity.dataTickets.add(4);
        MainActivity.dataTickets.add(5);
        MainActivity.dataTickets.add(6);
    }

    private  void  init()
    {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        ((TextView)findViewById(R.id.txt_TimeNow)).setText(String.format("%02d",today.monthDay ) + "/"+ String.format("%02d",(today.month+1)) +"/"+today.year +" "+today.format("%k:%M:%S"));             // Day of the month (1-31)
        switch (getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                // ...
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                // ...
                break;
            case DisplayMetrics.DENSITY_HIGH:
                // ...
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                // ...
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                // ...
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                // ...
                break;
        }
        orientation = getResources().getConfiguration().orientation;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            findViewById(R.id.layout_popup).setTranslationY(findViewById(R.id.container).getHeight());
                            resetLayout();
                        }
                    });
                } catch (Exception e) {

                }

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Time today = new Time(Time.getCurrentTimezone());
                                today.setToNow();
                                ((TextView)findViewById(R.id.txt_TimeNow)).setText(String.format("%02d",today.monthDay ) + "/"+ String.format("%02d",(today.month+1)) +"/"+today.year +" "+today.format("%k:%M:%S"));             // Day of the month (1-31)


                            }
                        });
                    }

                } catch (Exception e) {

                }

            }
        }).start();
        hideSystemUI();
    }

    private void hideSystemUI()
    {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void resetLayout()
    {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            changeColumnCount(((GridLayout) findViewById(R.id.grid_layout_tk)));

        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            changeColumnCount(((GridLayout) findViewById(R.id.grid_layout_tk)));
        }

    }

    private void changeColumnCount(final GridLayout gridLayout)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        int columnCount = gridLayout.getColumnCount();
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            columnCount = 3;

        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            columnCount = 2;

        }

        final int viewsCount = MainActivity.dataTickets.size();

        gridLayout.removeAllViews();
        gridLayout.setColumnCount(columnCount);
        gridLayout.setRowCount((int) Math.ceil((double) viewsCount / (double) columnCount));
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        int count=0;
        for (int i = 0; i < gridLayout.getRowCount(); i++) {
            GridLayout.Spec rowSpec = GridLayout.spec(i, 1f);
            for (int j = 0; j < gridLayout.getColumnCount(); j++) {
                GridLayout.Spec colSpec = GridLayout.spec(j, 1f);
                LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.tikcket_item,null);
                view.findViewById(R.id.txt_button).setVisibility(View.GONE);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resetLayout();
                        testPrinter();
                    }
                });
                count=i*columnCount+j;
                //  lParams.setGravity(Gravity.CENTER);
                if (view != null && count<viewsCount) {
                    GridLayout.LayoutParams lParams = new GridLayout.LayoutParams();
                    lParams.rowSpec = rowSpec;
                    lParams.columnSpec = colSpec;
                    view.setLayoutParams(lParams);
                    gridLayout.addView(view,lParams);
                } else {
                    break;
                }

            }
        }
        gridLayout.requestLayout();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(200);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            int countView=  gridLayout.getChildCount();
                            for (int i = 0; i <countView; i++) {
                                Log.d("W-I-G",gridLayout.getChildAt(i).getWidth()+" - "+gridLayout.getChildAt(i).getHeight());

                                LinearLayout.LayoutParams lParam=(    LinearLayout.LayoutParams) gridLayout.getChildAt(i).findViewById(R.id.layout_button).getLayoutParams();
                                lParam.width=gridLayout.getChildAt(i).getWidth()-30;
                                lParam.height=gridLayout.getChildAt(i).getHeight()-30;
                                gridLayout.getChildAt(i).findViewById(R.id.layout_button).setLayoutParams(lParam);
                                gridLayout.getChildAt(i).requestLayout();

                                //lParam.width=  gridLayout.getChildAt(i).findViewById(R.id.button_text).getLayoutParams().width;
                                // lParam.height=  gridLayout.getChildAt(i).findViewById(R.id.button_text).getLayoutParams().height;
                                // gridLayout.getChildAt(i).findViewById(R.id.button_text).setLayoutParams(lParam);

                                gridLayout.getChildAt(i).findViewById(R.id.txt_button).setVisibility(View.VISIBLE);
                            }
                        }
                    });

                } catch (Exception e) {

                }

            }
        }).start();


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
       }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        orientation = newConfig.orientation;
        super.onConfigurationChanged(newConfig);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(200);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            resetLayout();
                        }
                    });

                } catch (Exception e) {

                }

            }
        }).start();


    }

    ////LoadDMTAIXE///////
    List<DmTaiXe> ItemAllDmTaiXe;
    List<String> ListmaTaiXe = new ArrayList<>();
    List<String> ListtenTaiXe = new ArrayList<>();
    List<String> Listtuoi = new ArrayList<>();
    List<String> ListgioiTinh = new ArrayList<>();
    List<String> ListbangLai = new ArrayList<>();
    List<String> Listsdt = new ArrayList<>();
    List<String> ListdiaChiNoiO = new ArrayList<>();
    List<String> Listemail = new ArrayList<>();

    public void CallDmTaiXe() {
        ResClien restClient = new ResClien();
        restClient.GetService().GetDMTAIXEs(new Callback<List<DmTaiXe>>() {
            @Override
            public void success(List<DmTaiXe> DmTaiXe, Response response) {
                for (int i = 0; i < DmTaiXe.size(); i++) {
                    String tmpStr10 = Integer.toString(DmTaiXe.get(i).getTUOI());
                    ListmaTaiXe.add(DmTaiXe.get(i).getMATAIXE());
                    ListtenTaiXe.add(DmTaiXe.get(i).getTENTAIXE());
                    Listtuoi.add(tmpStr10);
                    ListgioiTinh.add(DmTaiXe.get(i).getGIOITINH());
                    ListbangLai.add(DmTaiXe.get(i).getBANGLAI());
                    Listsdt.add(DmTaiXe.get(i).getSDT());
                    ListdiaChiNoiO.add(DmTaiXe.get(i).getDIACHINOIO());
                    Listemail.add(DmTaiXe.get(i).getEMAIL());
                }
                loadDataAllAdvert();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }
    private List<DmTaiXe> getAllItemsAllAdvert() {
        List<DmTaiXe> items = new ArrayList<>();
        for (int i = 0; i < ListmaTaiXe.size(); i++) {
            items.add(
                    new DmTaiXe(
                            ListmaTaiXe.get(i),
                            ListtenTaiXe.get(i),
                            Listtuoi.get(i),
                            ListgioiTinh.get(i),
                            ListbangLai.get(i),
                            Listsdt.get(i),
                            ListdiaChiNoiO.get(i),
                            Listemail.get(i)

                    )
            );
        }
        return items;
    }
    private void loadDataAllAdvert() {
        ItemAllDmTaiXe = getAllItemsAllAdvert();
    }
    ///LoadDMTAIXE///

    ////LoadDMTAIXE///////
    List<DmTram> ItemAllDmTram;
    List<String> ListID = new ArrayList<>();
    List<String> ListMaTram = new ArrayList<>();
    List<String> ListTenTram = new ArrayList<>();


    public void CallDmTram() {
        ResClien restClient = new ResClien();
        restClient.GetService().GetDMTRAMs(new Callback<List<DmTram>>() {
            @Override
            public void success(List<DmTram> DmTram, Response response) {
                for (int i = 0; i < DmTram.size(); i++) {
                    String tmpStr10 = Integer.toString(DmTram.get(i).getId());
                    ListMaTram.add(DmTram.get(i).getMaTram());
                    ListTenTram.add(DmTram.get(i).getTenTram());
                    ListID.add(tmpStr10);

                }
                loadDataAllTram();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }
    private List<DmTram> getAllTram() {
        List<DmTram> items = new ArrayList<>();
        for (int i = 0; i < ListmaTaiXe.size(); i++) {
            items.add(
                    new DmTram(
                            ListID.get(i),
                            ListMaTram.get(i),
                            ListTenTram.get(i))
            );
        }
        return items;
    }
    private void loadDataAllTram() {
        ItemAllDmTram = getAllTram();
    }
    ///LoadDMTAIXE///

}
