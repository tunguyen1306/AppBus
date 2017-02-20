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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import davisoft.app.busticket.adapter.USBAdapter;
import davisoft.app.busticket.data.ControlDatabase;
import davisoft.app.busticket.data.ResClien;
import davisoft.app.busticket.data.pojo.DmTaiXe;
import davisoft.app.busticket.data.pojo.DmTram;
import davisoft.app.busticket.data.pojo.DmTuyen;
import davisoft.app.busticket.data.pojo.DmXe;
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
    public static List<Integer> dataTickets = new ArrayList<Integer>();
    public List<DmTaiXe> dmTaixe = new ArrayList<>();
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
        CallDmTuyen();
        CallDmXe();
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    mDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (mDevice != null) {
                            //call method to set up device communication
                        }
                    } else {
                        Log.d(TAG, "permission denied for device " + mDevice);
                    }
                }
            }
        }
    };

    private void initUSB() {
        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        mPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        context.registerReceiver(mUsbReceiver, filter);
        for (UsbDevice usb : mUsbManager.getDeviceList().values()) {
            if (usb.getVendorId() == 1155 && usb.getProductId() == 22339) {
                mDevice = usb;
                break;
            }
        }
        if (mDevice != null) {
            if (!mUsbManager.hasPermission(mDevice)) {
                mUsbManager.requestPermission(mDevice, mPermissionIntent);
            }
        }


    }

    private void print() {
        if (mDevice != null && mUsbManager.hasPermission(mDevice)) {
            UsbInterface intf = mDevice.getInterface(0);
            for (int i = 0; i < intf.getEndpointCount(); i++) {
                UsbEndpoint ep = intf.getEndpoint(i);
                if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                    if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {
                        final UsbEndpoint mEndpointBulkOut = ep;
                        connection = mUsbManager.openDevice(mDevice);
                        if (connection != null) {
                            Log.e("Connection:", " connected");
                            Toast.makeText(context, "Device connected", Toast.LENGTH_SHORT).show();
                        }
                        boolean forceClaim = true;
                        connection.claimInterface(intf, forceClaim);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                Log.i("Thread:", "in run thread");
                                String s = new PrintOrder().testData();
                                byte[] array = s.getBytes(StandardCharsets.UTF_8);
                                s = new String(array, StandardCharsets.ISO_8859_1);
                                array = s.getBytes();
                                Integer b = connection.bulkTransfer(mEndpointBulkOut, array, array.length, 10000);
                                Log.i("Return Status", "b-->" + b);}
                        }).start();

                        connection.releaseInterface(intf);
                        break;
                    }
                }
            }
        }
    }

    private void testPrinter() {
        Toast.makeText(this, "Printer", Toast.LENGTH_LONG).show();
        String msg = "This is a test message";
        PrintOrder printer = new PrintOrder();
        printer.Print(this, msg);
    }

    private void initEvent() {

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
                                super.onAnimationEnd(animation);}
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
                                super.onAnimationEnd(animation);}
                        });
            }
        });


    }

    private void initData() {
        MainActivity.dataTickets.clear();
        MainActivity.dataTickets.add(1);
        MainActivity.dataTickets.add(2);
        MainActivity.dataTickets.add(3);
        MainActivity.dataTickets.add(4);
        MainActivity.dataTickets.add(5);
        MainActivity.dataTickets.add(6);
    }

    private void init() {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        ((TextView) findViewById(R.id.txt_TimeNow)).setText(String.format("%02d", today.monthDay) + "/" + String.format("%02d", (today.month + 1)) + "/" + today.year + " " + today.format("%k:%M:%S"));             // Day of the month (1-31)
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
                        public void run() {findViewById(R.id.layout_popup).setTranslationY(findViewById(R.id.container).getHeight());
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
                        runOnUiThread(new Runnable() {@Override
                            public void run() {
                                Time today = new Time(Time.getCurrentTimezone());
                                today.setToNow();
                                ((TextView) findViewById(R.id.txt_TimeNow)).setText(String.format("%02d", today.monthDay) + "/" + String.format("%02d", (today.month + 1)) + "/" + today.year + " " + today.format("%k:%M:%S"));             // Day of the month (1-31)
}
                        });
                    }

                } catch (Exception e) {

                }

            }
        }).start();
        hideSystemUI();
    }

    private void hideSystemUI() {
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

    private void resetLayout() {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            changeColumnCount(((GridLayout) findViewById(R.id.grid_layout_tk)));

        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            changeColumnCount(((GridLayout) findViewById(R.id.grid_layout_tk)));
        }

    }

    private void changeColumnCount(final GridLayout gridLayout) {
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
        int count = 0;
        for (int i = 0; i < gridLayout.getRowCount(); i++) {
            GridLayout.Spec rowSpec = GridLayout.spec(i, 1f);
            for (int j = 0; j < gridLayout.getColumnCount(); j++) {
                GridLayout.Spec colSpec = GridLayout.spec(j, 1f);
                LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.tikcket_item, null);
                view.findViewById(R.id.txt_button).setVisibility(View.GONE);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resetLayout();
                        testPrinter();
                    }
                });
                count = i * columnCount + j;
                //  lParams.setGravity(Gravity.CENTER);
                if (view != null && count < viewsCount) {
                    GridLayout.LayoutParams lParams = new GridLayout.LayoutParams();
                    lParams.rowSpec = rowSpec;
                    lParams.columnSpec = colSpec;
                    view.setLayoutParams(lParams);
                    gridLayout.addView(view, lParams);
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
                            int countView = gridLayout.getChildCount();
                            for (int i = 0; i < countView; i++) {
                                Log.d("W-I-G", gridLayout.getChildAt(i).getWidth() + " - " + gridLayout.getChildAt(i).getHeight());    LinearLayout.LayoutParams lParam = (LinearLayout.LayoutParams) gridLayout.getChildAt(i).findViewById(R.id.layout_button).getLayoutParams();
                                lParam.width = gridLayout.getChildAt(i).getWidth() - 30;
                                lParam.height = gridLayout.getChildAt(i).getHeight() - 30;
                                gridLayout.getChildAt(i).findViewById(R.id.layout_button).setLayoutParams(lParam);
                                gridLayout.getChildAt(i).requestLayout();    //lParam.width=  gridLayout.getChildAt(i).findViewById(R.id.button_text).getLayoutParams().width;
                                // lParam.height=  gridLayout.getChildAt(i).findViewById(R.id.button_text).getLayoutParams().height;
                                // gridLayout.getChildAt(i).findViewById(R.id.button_text).setLayoutParams(lParam);    gridLayout.getChildAt(i).findViewById(R.id.txt_button).setVisibility(View.VISIBLE);
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
    public void onWindowFocusChanged(boolean hasFocus) {
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
    public void onConfigurationChanged(Configuration newConfig) {
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


    ////LoadDmTuyen///////
    List<DmTuyen> ItemAllDmTuyen;
    List<String> IDTUYEN = new ArrayList<>();
    List<String> MATUYEN = new ArrayList<>();
    List<String> TENTUYENEND = new ArrayList<>();
    List<String> TENTUYENVN = new ArrayList<>();
    List<String> MATRAMDAU = new ArrayList<>();
    List<String> MATRAMGIUA = new ArrayList<>();
    List<String> MATRAMCUOI = new ArrayList<>();
    List<String> TONGTRAM = new ArrayList<>();
    List<String> MUCDO = new ArrayList<>();
    List<String> THOIGIANTOANTRAM = new ArrayList<>();
    List<String> GIAVE1 = new ArrayList<>();
    List<String> DIENGIAIVE1 = new ArrayList<>();
    List<String> CAMVE1 = new ArrayList<>();
    List<String> IDVE1IDHOADON = new ArrayList<>();
    List<String> GIAVE2 = new ArrayList<>();
    List<String> DIENGIAIVE2 = new ArrayList<>();
    List<String> CAMVE2 = new ArrayList<>();
    List<String> IDVE2IDHOADON = new ArrayList<>();
    List<String> GIAVE3 = new ArrayList<>();
    List<String> DIENGIAIVE3 = new ArrayList<>();
    List<String> CAMVE3 = new ArrayList<>();
    List<String> IDVE3IDHOADON = new ArrayList<>();
    List<String> GIAVE4 = new ArrayList<>();
    List<String> DIENGIAIVE4 = new ArrayList<>();
    List<String> CAMVE4 = new ArrayList<>();
    List<String> IDVE4IDHOADON = new ArrayList<>();
    List<String> GIAVE5 = new ArrayList<>();
    List<String> DIENGIAIVE5 = new ArrayList<>();
    List<String> CAMVE5 = new ArrayList<>();
    List<String> IDVE5IDHOADON = new ArrayList<>();
    List<String> GIAVE6 = new ArrayList<>();
    List<String> DIENGIAIVE6 = new ArrayList<>();
    List<String> CAMVE6 = new ArrayList<>();
    List<String> IDVE6IDHOADON = new ArrayList<>();

    public void CallDmTuyen() {
        ResClien restClient = new ResClien();
        restClient.GetService().GetDMTUYENs(new Callback<List<DmTuyen>>() {
            @Override
            public void success(List<DmTuyen> DmTuyen, Response response) {
                for (int i = 0; i < DmTuyen.size(); i++) {

                    IDTUYEN.add(DmTuyen.get(i).GETIDTUYEN());

                    MATUYEN.add(DmTuyen.get(i).GETMATUYEN());

                    TENTUYENEND.add(DmTuyen.get(i).GETTENTUYENEND());

                    TENTUYENVN.add(DmTuyen.get(i).GETTENTUYENVN());

                    MATRAMDAU.add(DmTuyen.get(i).GETMATRAMDAU());

                    MATRAMGIUA.add(DmTuyen.get(i).GETMATRAMGIUA());

                    MATRAMCUOI.add(DmTuyen.get(i).GETMATRAMCUOI());

                    TONGTRAM.add(DmTuyen.get(i).GETTONGTRAM());

                    MUCDO.add(Integer.toString(DmTuyen.get(i).GETMUCDO()));

                    THOIGIANTOANTRAM.add(DmTuyen.get(i).GETTHOIGIANTOANTRAM().toString());

                    GIAVE1.add(Double.toString(DmTuyen.get(i).GETGIAVE1()));

                    DIENGIAIVE1.add(DmTuyen.get(i).GETDIENGIAIVE1());

                    CAMVE1.add(Boolean.toString(DmTuyen.get(i).GETCAMVE1()));

                    IDVE1IDHOADON.add(DmTuyen.get(i).GETIDVE1IDHOADON());

                    GIAVE2.add(Double.toString(DmTuyen.get(i).GETGIAVE2()));

                    DIENGIAIVE2.add(DmTuyen.get(i).GETDIENGIAIVE2());

                    CAMVE2.add(Boolean.toString(DmTuyen.get(i).GETCAMVE2()));

                    IDVE2IDHOADON.add(DmTuyen.get(i).GETIDVE2IDHOADON());

                    GIAVE3.add(Double.toString(DmTuyen.get(i).GETGIAVE3()));

                    DIENGIAIVE3.add(DmTuyen.get(i).GETDIENGIAIVE3());

                    CAMVE3.add(Boolean.toString(DmTuyen.get(i).GETCAMVE3()));

                    IDVE3IDHOADON.add(DmTuyen.get(i).GETIDVE3IDHOADON());

                    GIAVE4.add(Double.toString(DmTuyen.get(i).GETGIAVE4()));

                    DIENGIAIVE4.add(DmTuyen.get(i).GETDIENGIAIVE4());

                    CAMVE4.add(Boolean.toString(DmTuyen.get(i).GETCAMVE4()));

                    IDVE4IDHOADON.add(DmTuyen.get(i).GETIDVE4IDHOADON());

                    GIAVE5.add(Double.toString(DmTuyen.get(i).GETGIAVE5()));

                    DIENGIAIVE5.add(DmTuyen.get(i).GETDIENGIAIVE5());

                    CAMVE5.add(Boolean.toString(DmTuyen.get(i).GETCAMVE5()));

                    IDVE5IDHOADON.add(DmTuyen.get(i).GETIDVE5IDHOADON());

                    GIAVE6.add(Double.toString(DmTuyen.get(i).GETGIAVE6()));

                    DIENGIAIVE6.add(DmTuyen.get(i).GETDIENGIAIVE6());

                    CAMVE6.add(Boolean.toString(DmTuyen.get(i).GETCAMVE6()));

                    IDVE6IDHOADON.add(DmTuyen.get(i).GETIDVE6IDHOADON());


                }
                loadDataAllDmTuyen();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }

    private List<DmTuyen> getAllDmTuyen() {
        List<DmTuyen> items = new ArrayList<>();
        for (int i = 0; i < IDTUYEN.size(); i++) {
            items.add(
                    new DmTuyen(IDTUYEN.get(i),MATUYEN.get(i),TENTUYENEND.get(i),TENTUYENVN.get(i),MATRAMDAU.get(i),MATRAMGIUA.get(i),MATRAMCUOI.get(i),TONGTRAM.get(i),MUCDO.get(i), THOIGIANTOANTRAM.get(i),GIAVE1.get(i),DIENGIAIVE1.get(i),CAMVE1.get(i),IDVE1IDHOADON.get(i),GIAVE2.get(i),DIENGIAIVE2.get(i),CAMVE2.get(i),IDVE2IDHOADON.get(i),GIAVE3.get(i),DIENGIAIVE3.get(i),CAMVE3.get(i),IDVE3IDHOADON.get(i),GIAVE4.get(i),DIENGIAIVE4.get(i),CAMVE4.get(i),IDVE4IDHOADON.get(i),GIAVE5.get(i),DIENGIAIVE5.get(i),CAMVE5.get(i),IDVE5IDHOADON.get(i),GIAVE6.get(i),DIENGIAIVE6.get(i),CAMVE6.get(i),IDVE6IDHOADON.get(i))
            );
        }
        return items;
    }

    private void loadDataAllDmTuyen() {
        ItemAllDmTuyen = getAllDmTuyen();
    }
///LoadDMTAIXE///


    ////LoadDMTAIXE///////
    List<DmXe> ItemAllDmXe;
    List<String> ListMaXe = new ArrayList<>();
    List<String> ListSOXE = new ArrayList<>();
    List<String> ListLOAIXE = new ArrayList<>();
    List<String> ListMATAIXE = new ArrayList<>();
    List<String> ListSOGHE = new ArrayList<>();

    public void CallDmXe() {
        ResClien restClient = new ResClien();
        restClient.GetService().GetDMXEs(new Callback<List<DmXe>>() {
            @Override
            public void success(List<DmXe> DmXe, Response response) {
                for (int i = 0; i < DmXe.size(); i++) {

                    ListMaXe.add(DmXe.get(i).getMaXe());
                    ListSOXE.add(DmXe.get(i).getSoXe());
                    ListLOAIXE.add(DmXe.get(i).getLoaiXe());
                    ListMATAIXE.add(DmXe.get(i).getMaTaiXe());
                    ListSOGHE.add(DmXe.get(i).getSoGhe());

                }
                loadDataAllXe();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }

    private List<DmXe> getAllXe() {
        List<DmXe> items = new ArrayList<>();
        for (int i = 0; i < ListmaTaiXe.size(); i++) {
            items.add( new DmXe(ListMaXe.get(i),ListSOXE.get(i),ListLOAIXE.get(i),ListMATAIXE.get(i), ListSOGHE.get(i)));
        }
        return items;
    }

    private void loadDataAllXe() {
        ItemAllDmXe = getAllXe();
    }


}
