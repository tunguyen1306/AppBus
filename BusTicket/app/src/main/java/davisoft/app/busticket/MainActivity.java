package davisoft.app.busticket;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.hardware.usb.UsbConfiguration;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import davisoft.app.busticket.adapter.USBAdapter;
import davisoft.app.busticket.data.ControlDatabase;
import davisoft.app.busticket.data.DatabaseHelper;
import davisoft.app.busticket.data.ResClien;
import davisoft.app.busticket.data.pojo.Counters;
import davisoft.app.busticket.data.pojo.DichVu;
import davisoft.app.busticket.data.pojo.DmHoaDon;
import davisoft.app.busticket.data.pojo.DmTaiXe;
import davisoft.app.busticket.data.pojo.DmTram;
import davisoft.app.busticket.data.pojo.DmTuyen;
import davisoft.app.busticket.data.pojo.DmTuyenChiTietTram;
import davisoft.app.busticket.data.pojo.DmXe;
import davisoft.app.busticket.data.pojo.LoTrinhChoXe;
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

    public static  String MaXe="V1251";

    private DatabaseHelper databaseHelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initData();
        initEvent();
        initUSB();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);

                } catch (Exception e) {

                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        resetLayout();
                    }
                });

            }
        }).start();

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

    private void print(final String soTien,final String dienGiai) {
       Toast.makeText(getApplicationContext(),"Printing...",Toast.LENGTH_LONG).show();
        final String s = new PrintOrder().buildData(CountersLocal,MaVe,soTien,dienGiai,TenTuyen,BienSoXe,mauSo,kyHieu);
        Log.i("Printer/Info",s);
        if (mDevice != null && mUsbManager.hasPermission(mDevice)) {

            final UsbInterface intf = mDevice.getInterface(0);
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

                                byte[] array = s.getBytes(StandardCharsets.ISO_8859_1);
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
        else {
            initUSB();
        }
    }

    private void initEvent() {

        findViewById(R.id.layout_popup).setAlpha(0f);
        findViewById(R.id.button_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                findViewById(R.id.layout_popup).setVisibility(View.VISIBLE);
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
                                findViewById(R.id.layout_popup).setVisibility(View.GONE);
                            }
                        });
            }
        });


    }

    HashMap<String,Boolean> hasSyncRest=new HashMap<String,Boolean>();
    String CuurentLuot="1";
    String MaTaiXe="";
    String IDTuyen="";
    String BienSoXe="";
    String TenTuyen="";
    String MaVe="0000001";
    String mauSo="";
    String kyHieu="";
    String hoaDonID="";
    Counters CountersLocal=null;
    LoTrinhChoXe LoTrinhChoXeLocal=null;
    DmXe DmXeLocal=null;
    DmTuyen DmTuyenLocal=null;
    List<DmTuyen> ListTuyenByMaXe=new ArrayList<>();
    List<LoTrinhChoXe> ListLoTrinhByMaXe=new ArrayList<>();
    private boolean updateHoaDonByID(final Integer index,final String sotien,final String dienGiai)
    {   mauSo="";
        kyHieu="";
        if (hoaDonID.trim().length()>0)
        {


            ResClien resClient = new ResClien();
            resClient.GetService().CountView(hoaDonID, new Callback<List<DmHoaDon>>() {
                @Override
                public void success(List<DmHoaDon> advertDtos, Response response) {
                    for (int i = 0; i < advertDtos.size(); i++) {
                        String getTONGSOVEPHATHANH = String.valueOf(advertDtos.get(i).getTONGSOVEPHATHANH());
                        String getSOVEHIENTAI = String.valueOf(advertDtos.get(i).getSOVEHIENTAI());
                        String getIDVE = String.valueOf(advertDtos.get(i).getIDVE());
                        IDHOADON.add(advertDtos.get(i).getIDHOADON().toString());
                        MAXEHoaDon.add(advertDtos.get(i).getMAXE().toString());
                        KYHIEUVE.add(advertDtos.get(i).getKYHIEUVE().toString());
                        MAUSO.add(advertDtos.get(i).getMAUSO().toString());
                        TONGSOVEPHATHANH.add(getTONGSOVEPHATHANH);
                        SOVEHIENTAI.add(getSOVEHIENTAI);
                        IDVE.add(getIDVE);


                    }
                    loadDataDmHoaDon();
                    for (int i=0;i<ItemAllDmHoaDon.size();i++)
                    {
                        if(ItemAllDmHoaDon.get(i).getIDHOADON().trim().equals(hoaDonID.trim()))
                        {



                            kyHieu=ItemAllDmHoaDon.get(i).getKYHIEUVE();
                            mauSo=ItemAllDmHoaDon.get(i).getMAUSO();
                            MaVe=String.format("%07d",ItemAllDmHoaDon.get(i).getSOVEHIENTAI()+1);
                            ItemAllDmHoaDon.get(i).setSOVEHIENTAI(Integer.valueOf(MaVe));

                            DichVu dv=new DichVu();


                            dv.setSQMS(MaVe);
                            dv.setMATUYEN(DmTuyenLocal.GETMATUYEN());
                            dv.setTENTUYEN(DmTuyenLocal.GETTENTUYENVN());
                            dv.setLOTRINH(TenTuyen);
                            dv.setBienSoXe(BienSoXe);
                            dv.setDichvu1("dv"+index);
                            dv.setMATAIXE(MaTaiXe);
                            dv.setMAXE(MainActivity.MaXe);
                            dv.setMATRAM("");
                            dv.setIDTUYEN(IDTuyen);
                            dv.setKYHIEUVE(kyHieu);
                            dv.setMAUSO(mauSo);
                            dv.setMATRAMDAU(DmTuyenLocal.GETMATRAMDAU());
                            dv.setMATRAMGIUA(DmTuyenLocal.GETMATRAMGIUA());
                            dv.setMATRAMCUOI(DmTuyenLocal.GETMATRAMCUOI());
                            dv.setNGONNGU("VN");
                            dv.setNGAY(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"/"+Calendar.getInstance().get(Calendar.MONTH)+"/"+Calendar.getInstance().get(Calendar.YEAR));
                            dv.setGIAVE(sotien);
                            dv.setGIO_GOC(Calendar.getInstance().get(Calendar.HOUR)+":"+Calendar.getInstance().get(Calendar.MINUTE)+"/"+Calendar.getInstance().get(Calendar.SECOND));
                            dv.setGIOLAYSO(Calendar.getInstance().get(Calendar.HOUR)+":"+Calendar.getInstance().get(Calendar.MINUTE)+"/"+Calendar.getInstance().get(Calendar.SECOND));


                            dv.setBINH_CHON("N");
                            dv.setContro(false);
                            dv.setDATCHO(false);
                            dv.setPHUCVU(false);
                            dv.setDoc(false);
                            ItemAllDichVu.add(dv);
                            print(sotien,dienGiai);
                            break;
                        }
                    }
                    hoaDonID="";

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });


            //get couunt hoa don
            // insert danh sach dich vu
        }

        return false;
    }
    private void resetTicket()
    {
        DecimalFormat df = new DecimalFormat("#,###");
        if (DmTuyenLocal!=null)
        {
            GridLayout gLayout= ((GridLayout) findViewById(R.id.grid_layout_tk));
            gLayout.getChildAt(0).findViewById(R.id.layout_button).setEnabled(true);
            gLayout.getChildAt(0).findViewById(R.id.layout_button).setBackgroundResource(R.drawable.btn_selector);
            gLayout.getChildAt(1).findViewById(R.id.layout_button).setEnabled(true);
            gLayout.getChildAt(1).findViewById(R.id.layout_button).setBackgroundResource(R.drawable.btn_selector);
            gLayout.getChildAt(2).findViewById(R.id.layout_button).setEnabled(true);
            gLayout.getChildAt(2).findViewById(R.id.layout_button).setBackgroundResource(R.drawable.btn_selector);
            gLayout.getChildAt(3).findViewById(R.id.layout_button).setEnabled(true);
            gLayout.getChildAt(3).findViewById(R.id.layout_button).setBackgroundResource(R.drawable.btn_selector);
            gLayout.getChildAt(4).findViewById(R.id.layout_button).setEnabled(true);
            gLayout.getChildAt(4).findViewById(R.id.layout_button).setBackgroundResource(R.drawable.btn_selector);
            gLayout.getChildAt(5).findViewById(R.id.layout_button).setEnabled(true);
            gLayout.getChildAt(5).findViewById(R.id.layout_button).setBackgroundResource(R.drawable.btn_selector);
            if (DmTuyenLocal.GETCAMVE1())
            {
                gLayout.getChildAt(0).findViewById(R.id.layout_button).setEnabled(false);
                gLayout.getChildAt(0).findViewById(R.id.layout_button).setBackgroundResource(R.drawable.btn_selector_disable);
                ((TextView)gLayout.getChildAt(0).findViewById(R.id.txt_button)).setText("...");
            }else
            {

                gLayout.getChildAt(0).findViewById(R.id.layout_button).setTag("1;"+DmTuyenLocal.GETIDVE1IDHOADON()+";"+df.format(DmTuyenLocal.GETGIAVE1()).replaceAll(",",".")+";"+DmTuyenLocal.GETDIENGIAIVE1());
                if (DmTuyenLocal.GETDIENGIAIVE1()!=null && DmTuyenLocal.GETDIENGIAIVE1().trim()!="")
                {
                    ((TextView)gLayout.getChildAt(0).findViewById(R.id.txt_button)).setText(DmTuyenLocal.GETDIENGIAIVE1());
                }
                else
                {
                    ((TextView)gLayout.getChildAt(0).findViewById(R.id.txt_button)).setText(df.format(DmTuyenLocal.GETGIAVE1()).replaceAll(",",".")+";"+DmTuyenLocal.GETDIENGIAIVE1());
                }
            }
            if (DmTuyenLocal.GETCAMVE2())
            {
                gLayout.getChildAt(1).findViewById(R.id.layout_button).setEnabled(false);
                gLayout.getChildAt(1).findViewById(R.id.layout_button).setBackgroundResource(R.drawable.btn_selector_disable);

                ((TextView)gLayout.getChildAt(1).findViewById(R.id.txt_button)).setText("...");
            }else
            {
                gLayout.getChildAt(1).findViewById(R.id.layout_button).setTag("2;"+DmTuyenLocal.GETIDVE2IDHOADON()+";"+df.format(DmTuyenLocal.GETGIAVE2()).replaceAll(",",".")+";"+DmTuyenLocal.GETDIENGIAIVE2());
                if (DmTuyenLocal.GETDIENGIAIVE2()!=null && DmTuyenLocal.GETDIENGIAIVE2().trim()!="")
                {
                    ((TextView)gLayout.getChildAt(1).findViewById(R.id.txt_button)).setText(DmTuyenLocal.GETDIENGIAIVE2());
                }
                else
                {
                    ((TextView)gLayout.getChildAt(1).findViewById(R.id.txt_button)).setText(df.format(DmTuyenLocal.GETGIAVE2()).replaceAll(",",".")+";"+DmTuyenLocal.GETDIENGIAIVE2());
                }
            }
            if (DmTuyenLocal.GETCAMVE3())
            {
                gLayout.getChildAt(2).findViewById(R.id.layout_button).setEnabled(false);
                gLayout.getChildAt(2).findViewById(R.id.layout_button).setBackgroundResource(R.drawable.btn_selector_disable);
                ((TextView)gLayout.getChildAt(2).findViewById(R.id.txt_button)).setText("...");
            }else
            {
                gLayout.getChildAt(2).findViewById(R.id.layout_button).setTag("3;"+DmTuyenLocal.GETIDVE3IDHOADON()+";"+df.format(DmTuyenLocal.GETGIAVE3()).replaceAll(",",".")+";"+DmTuyenLocal.GETDIENGIAIVE3());
                if (DmTuyenLocal.GETDIENGIAIVE3()!=null && DmTuyenLocal.GETDIENGIAIVE3().trim()!="")
                {
                    ((TextView)gLayout.getChildAt(2).findViewById(R.id.txt_button)).setText(DmTuyenLocal.GETDIENGIAIVE3());
                }
                else
                {
                    ((TextView)gLayout.getChildAt(2).findViewById(R.id.txt_button)).setText(df.format(DmTuyenLocal.GETGIAVE3()).replaceAll(",",".")+";"+DmTuyenLocal.GETDIENGIAIVE3());
                }
            }

            if (DmTuyenLocal.GETCAMVE4())
            {
                gLayout.getChildAt(3).findViewById(R.id.layout_button).setEnabled(false);
                gLayout.getChildAt(3).findViewById(R.id.layout_button).setBackgroundResource(R.drawable.btn_selector_disable);
                ((TextView)gLayout.getChildAt(3).findViewById(R.id.txt_button)).setText("...");
            }else
            {
                gLayout.getChildAt(3).findViewById(R.id.layout_button).setTag("4;"+DmTuyenLocal.GETIDVE4IDHOADON()+";"+df.format(DmTuyenLocal.GETGIAVE4()).replaceAll(",",".")+";"+DmTuyenLocal.GETDIENGIAIVE4());
                if (DmTuyenLocal.GETDIENGIAIVE4()!=null && DmTuyenLocal.GETDIENGIAIVE4().trim()!="")
                {
                    ((TextView)gLayout.getChildAt(3).findViewById(R.id.txt_button)).setText(DmTuyenLocal.GETDIENGIAIVE4());
                }
                else
                {
                    ((TextView)gLayout.getChildAt(3).findViewById(R.id.txt_button)).setText(df.format(DmTuyenLocal.GETGIAVE4()).replaceAll(",",".")+";"+DmTuyenLocal.GETDIENGIAIVE4());
                }
            }

            if (DmTuyenLocal.GETCAMVE5())
            {
                gLayout.getChildAt(4).findViewById(R.id.layout_button).setEnabled(false);
                gLayout.getChildAt(4).findViewById(R.id.layout_button).setBackgroundResource(R.drawable.btn_selector_disable);
                ((TextView)gLayout.getChildAt(4).findViewById(R.id.txt_button)).setText("...");
            }else
            {
                gLayout.getChildAt(4).findViewById(R.id.layout_button).setTag("5;"+DmTuyenLocal.GETIDVE5IDHOADON()+";"+df.format(DmTuyenLocal.GETGIAVE5()).replaceAll(",",".")+";"+DmTuyenLocal.GETDIENGIAIVE5());
                if (DmTuyenLocal.GETDIENGIAIVE5()!=null && DmTuyenLocal.GETDIENGIAIVE5().trim()!="")
                {
                    ((TextView)gLayout.getChildAt(4).findViewById(R.id.txt_button)).setText(DmTuyenLocal.GETDIENGIAIVE5());
                }
                else
                {
                    ((TextView)gLayout.getChildAt(4).findViewById(R.id.txt_button)).setText(df.format(DmTuyenLocal.GETGIAVE5()).replaceAll(",",".")+";"+DmTuyenLocal.GETDIENGIAIVE5());
                }
            }


            if (DmTuyenLocal.GETCAMVE6())
            {
                gLayout.getChildAt(5).findViewById(R.id.layout_button).setEnabled(false);
                gLayout.getChildAt(5).findViewById(R.id.layout_button).setBackgroundResource(R.drawable.btn_selector_disable);
                ((TextView)gLayout.getChildAt(5).findViewById(R.id.txt_button)).setText("...");
            }else
            {
                gLayout.getChildAt(5).findViewById(R.id.layout_button).setTag("6;"+DmTuyenLocal.GETIDVE6IDHOADON()+";"+df.format(DmTuyenLocal.GETGIAVE6()).replaceAll(",",".")+";"+DmTuyenLocal.GETDIENGIAIVE6());
                if (DmTuyenLocal.GETDIENGIAIVE6()!=null && DmTuyenLocal.GETDIENGIAIVE6().trim()!="")
                {
                    ((TextView)gLayout.getChildAt(5).findViewById(R.id.txt_button)).setText(DmTuyenLocal.GETDIENGIAIVE6());
                }
                else
                {
                    ((TextView)gLayout.getChildAt(5).findViewById(R.id.txt_button)).setText(df.format(DmTuyenLocal.GETGIAVE6()).replaceAll(",",".")+";"+DmTuyenLocal.GETDIENGIAIVE6());
                }


            }

        }

    }
    private String getTramDauGiuaCuoi(DmTuyen tuyen)
    {
        String TramDauD="";
        String TramGiuaD="";
        String TramCuoiD="";
        for (DmTram dmtram : ItemAllDmTram) {
            if (tuyen.GETMATRAMDAU() != null && tuyen.GETMATRAMDAU().trim() != "" && dmtram.getMaTram().trim().toLowerCase().equals(tuyen.GETMATRAMDAU().trim().toLowerCase())) {
                TramDauD = dmtram.getTenTram();
            }
            if (tuyen.GETMATRAMGIUA() != null && tuyen.GETMATRAMGIUA().trim() != "" && dmtram.getMaTram().trim().toLowerCase().equals(tuyen.GETMATRAMGIUA().trim().toLowerCase())) {
                TramGiuaD = dmtram.getTenTram();
            }
            if (tuyen.GETMATRAMCUOI() != null && tuyen.GETMATRAMCUOI().trim() != "" && dmtram.getMaTram().trim().toLowerCase().equals(tuyen.GETMATRAMCUOI().trim().toLowerCase())) {
                TramCuoiD = dmtram.getTenTram();
            }
        }


        return TramDauD+ (TramGiuaD.trim().length()>0?(" - " +TramGiuaD):"")+(TramCuoiD.trim().length()>0?(" - " +TramCuoiD):"");

    }
    private String getChiTietTramByTuyen(DmTuyen tuyen)
    {
        String TramChiTiet="";
        for (DmTuyenChiTietTram dmtuyenchitiet : ItemAllCHiTietTuyen) {
            if (dmtuyenchitiet.getIdTuyen().trim().equals(tuyen.GETIDTUYEN().trim()))
            {
                for (DmTram dmtram : ItemAllDmTram) {
                    if(dmtram.getMaTram().trim().equals(dmtuyenchitiet.getMaTram().trim()))
                    {
                        if (  TramChiTiet.length()>0)
                        {
                            TramChiTiet+=" - "+dmtram.getTenTram();
                        }else
                        {
                            TramChiTiet+=dmtram.getTenTram();
                        }
                    }
                }


            }
        }
        return TramChiTiet;
    }
    private void checkAllDataReady()
    {
        boolean check=true;
        if (hasSyncRest.size()>0)
        {
            for (Boolean b:hasSyncRest.values())
            {
                check&=b;
            }

        }
        if(check)
        {
            for (Counters ct : ItemCounters) {
                if (ct.getMAXE().trim().toLowerCase().equals(MainActivity.MaXe.trim().toLowerCase())) {
                    if (CountersLocal==null || !CountersLocal.getMAXE().trim().toLowerCase().equals(MainActivity.MaXe.trim().toLowerCase()))
                        CountersLocal = ct;
                    Time today = new Time(Time.getCurrentTimezone());
                    today.setToNow();
                    String date=String.format("%02d", today.monthDay) + "/" + String.format("%02d", (today.month + 1)) + "/" + today.year;
                    if (CountersLocal.getLastday() != null && CountersLocal.getLastday().trim() != "" && CountersLocal.getLastday().trim().equals(date)) {
                        CuurentLuot = CountersLocal.getLuot();
                    } else {

                        CountersLocal.setLastday(date);
                        CountersLocal.setLuot(CuurentLuot);

                    }
                    if (IDTuyen.trim().length()==0)
                    {
                        for (LoTrinhChoXe ltx : ItemAllLoTrinhChoXe) {
                            if (ltx.getKichHoat() && ltx.getMaXe().trim().toLowerCase().equals(MainActivity.MaXe.toLowerCase())) {
                                LoTrinhChoXeLocal = ltx;
                                MaTaiXe = LoTrinhChoXeLocal.getMaTaiXe();
                                IDTuyen = LoTrinhChoXeLocal.getIdTuyen();

                            }
                            if(ltx.getMaXe().trim().toLowerCase().equals(MainActivity.MaXe.toLowerCase()))
                            {
                                ListLoTrinhByMaXe.add(ltx);
                            }
                        }
                    }else
                    {
                        for (LoTrinhChoXe ltx : ItemAllLoTrinhChoXe) {
                            if (ltx.getKichHoat() &&  LoTrinhChoXeLocal.getIdTuyen().trim().equals(IDTuyen.trim())  && ltx.getMaXe().trim().toLowerCase().equals(MainActivity.MaXe.toLowerCase())) {
                                LoTrinhChoXeLocal = ltx;
                                MaTaiXe = LoTrinhChoXeLocal.getMaTaiXe();
                                IDTuyen = LoTrinhChoXeLocal.getIdTuyen();

                            }
                            if(ltx.getMaXe().trim().toLowerCase().equals(MainActivity.MaXe.toLowerCase()))
                            {
                                ListLoTrinhByMaXe.add(ltx);
                            }
                        }
                    }

                    for (DmXe dmxe : ItemAllDmXe) {
                        if (dmxe.getMaXe().trim().toLowerCase().equals(MainActivity.MaXe.toLowerCase())) {
                            DmXeLocal = dmxe;
                            BienSoXe = DmXeLocal.getSoXe();
                            break;
                        }
                    }
                    for (DmTuyen dmtuyen : ItemAllDmTuyen) {
                        if (dmtuyen.GETIDTUYEN().trim().toLowerCase().equals(IDTuyen.trim().toLowerCase())) {
                            DmTuyenLocal = dmtuyen;

                            break;
                        }
                    }
                    for (LoTrinhChoXe ltx : ListLoTrinhByMaXe) {
                        for (DmTuyen dmtuyen : ItemAllDmTuyen) {
                            if (ltx.getIdTuyen().trim().equals(dmtuyen.GETMATUYEN().trim()))
                            {
                                ListTuyenByMaXe.add(dmtuyen);
                                break;
                            }
                        }
                    }


                    break;
                }
            }
            if (CountersLocal == null) {

            } else if(LoTrinhChoXeLocal==null)
            {

            }else if(DmXeLocal==null)
            {

            }else if(DmTuyenLocal==null)
            {

            }
            else
            {

                ((TextView)findViewById(R.id.txtTitle1)).setText(CountersLocal.getTenCongTy());
                // ((TextView)findViewById(R.id.txtTitle2)).setText("");
                TenTuyen=getTramDauGiuaCuoi(DmTuyenLocal);
                ((TextView)findViewById(R.id.txtTitle3)).setText("Mã Tuyến: "+DmTuyenLocal.GETMATUYEN()+" - "+DmTuyenLocal.GETTENTUYENVN()+" ("+TenTuyen+")");
                ((TextView)findViewById(R.id.txtTitle4)).setText(getChiTietTramByTuyen(DmTuyenLocal));
                resetLayout();
                findViewById(R.id.layout_popup_loading).animate()
                        .alpha(0f)
                        .setDuration(200)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                findViewById(R.id.layout_popup_loading).setVisibility(View.GONE);
                            }
                        });
                initGridViewTuyen();


            }
        }
    }

    public class GridViewTuyenAdapter extends ArrayAdapter<DmTuyen> {
        public GridViewTuyenAdapter(Context context, int resource, List<DmTuyen> objects) {
            super(context, resource, objects);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if(null == v) {
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.tuyen_item, null);
            }
            DmTuyen dmTuyen = getItem(position);

            TextView txtMaTuyen = (TextView) v.findViewById(R.id.txtMaTuyen);
            TextView txtTenTuyen = (TextView) v.findViewById(R.id.txtTenTuyen);
            txtMaTuyen.setText(dmTuyen.GETMATUYEN());
            txtTenTuyen.setText(dmTuyen.GETTENTUYENVN()+" ("+getTramDauGiuaCuoi(dmTuyen)+")");
            txtTenTuyen.setSelected(true);
            txtMaTuyen.setTag(dmTuyen);
            if (dmTuyen.GETTENTUYENVN().trim().equals("Đỏ"))
            {
                txtTenTuyen.setBackground(Resources.getSystem().getDrawable(android.R.color.holo_red_dark));
            }
            if (dmTuyen.GETTENTUYENVN().trim().equals("Xanh Biển"))
            {
                txtTenTuyen.setBackground(Resources.getSystem().getDrawable(android.R.color.holo_blue_dark));
            }
            if (dmTuyen.GETTENTUYENVN().trim().equals("Nâu"))
            {
                txtTenTuyen.setBackground(Resources.getSystem().getDrawable(android.R.color.darker_gray));
            }
            if (dmTuyen.GETTENTUYENVN().trim().equals("Vàng"))
            {
                txtTenTuyen.setBackground(Resources.getSystem().getDrawable(android.R.color.holo_orange_dark));
            }
            if (dmTuyen.GETTENTUYENVN().trim().equals("Xanh Lá"))
            {
                txtTenTuyen.setBackground(Resources.getSystem().getDrawable(android.R.color.holo_green_dark));
            }
            if (dmTuyen.GETTENTUYENVN().trim().equals("Hồng"))
            {
                ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.colorAccent));

                txtTenTuyen.setBackground(colorDrawable);
            }
           for (LoTrinhChoXe ltx: ListLoTrinhByMaXe)
           {
                if(ltx.getIdTuyen().trim().equals(dmTuyen.GETIDTUYEN().trim()) && ltx.getCam())
                {
                    //txtTenTuyen.setBackground(Resources.getSystem().getDrawable(R.drawable.btn_selector_disable));
                    txtMaTuyen.setEnabled(false);
                    txtMaTuyen.setBackground(Resources.getSystem().getDrawable(android.R.color.background_dark));
                    break;
                }
           }
            txtMaTuyen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.isEnabled())
                    {
                        final DmTuyen dmTuyen=  (DmTuyen)v.getTag();
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        //Yes button clicked
                                        chooseTuyen(dmTuyen);
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn chọn tuyến "+dmTuyen.GETMATUYEN()+"?").setPositiveButton("Có", dialogClickListener)
                                .setNegativeButton("Không", dialogClickListener).show();

                        hideSystemUI();


                    }
                }
            });
            return v;
        }
    }

    public void initGridViewTuyen()
    {
        ((GridView)findViewById(R.id.gv_Tuyen)).setAdapter(new GridViewTuyenAdapter(getApplicationContext(),R.layout.tuyen_item,ItemAllDmTuyen));
    }

    public void updateGridView()
    {

        ((GridView)findViewById(R.id.gv_Tuyen)).requestLayout();
        Log.d("W-I-GV", ((GridView)findViewById(R.id.gv_Tuyen)).getWidth() + " - " + ((GridView)findViewById(R.id.gv_Tuyen)).getHeight());
        Log.d("W-I-GV1", MainActivity.convertPixelsToDp(((GridView)findViewById(R.id.gv_Tuyen)).getWidth() ,getApplicationContext()) + " - " +MainActivity.convertPixelsToDp( ((GridView)findViewById(R.id.gv_Tuyen)).getHeight(),getApplicationContext()));
    }

    private void chooseTuyen(DmTuyen dmTuyen)
    {
        DmTuyenLocal=dmTuyen;
        IDTuyen=dmTuyen.GETIDTUYEN();
        CuurentLuot=(Integer.valueOf(CuurentLuot)+1)+"";
        CountersLocal.setLuot(CuurentLuot);
        checkAllDataReady();
        findViewById(R.id.layout_popup).animate()
                .translationY(findViewById(R.id.layout_popup).getHeight())
                .alpha(0.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);}
                });
    }

    private void initData() {

        hasSyncRest=new HashMap<String,Boolean>();
        CuurentLuot="1";
        MaTaiXe="";
        IDTuyen="";
        BienSoXe="";
        TenTuyen="";
        MaVe="0000001";
        CountersLocal=null;
        LoTrinhChoXeLocal=null;
        DmXeLocal=null;
        DmTuyenLocal=null;
        ListTuyenByMaXe=new ArrayList<>();
        ListLoTrinhByMaXe=new ArrayList<>();


        CallDmTaiXe();
        CallDmTram();
        CallDmTuyen();
        CallDmXe();
        CallCHiTietTuyen();
        CallLoTrinhChoXe();
        CallCounters();
        CallDmHoaDon();
        CallDichVu();
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

        ((TextView) findViewById(R.id.txt_TimeNow)).setText("Ngày: "+String.format("%02d", today.monthDay) + "/" + String.format("%02d", (today.month + 1)) + "/" + today.year + "    -   Giờ: " + today.format("%l:%M:%S %P"));             // Day of the month (1-31)

      //  Toast.makeText(context,getResources().getDisplayMetrics().densityDpi+"",Toast.LENGTH_LONG).show();

        switch (getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                // ...
                Log.d("Screen Size/W/S","DENSITY_LOW");
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                // ...
                Log.d("Screen Size/W/S","DENSITY_MEDIUM");
                break;
            case DisplayMetrics.DENSITY_HIGH:
                // ...
                Log.d("Screen Size/W/S","DENSITY_HIGH");
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                // ...
                Log.d("Screen Size/W/S","DENSITY_XHIGH");
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                // ...
                Log.d("Screen Size/W/S","DENSITY_XXHIGH");
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                // ...
                Log.d("Screen Size/W/S","DENSITY_XXXHIGH");
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
                            String strAMPM="AM";
                            int AMPM= Calendar.getInstance().get(Calendar.AM_PM);
                            if (AMPM==Calendar.PM)
                            {
                                strAMPM="PM";
                            }
                                Time today = new Time(Time.getCurrentTimezone());
                                today.setToNow();
                                ((TextView) findViewById(R.id.txt_TimeNow)).setText("Ngày: "+String.format("%02d", today.monthDay) + "/" + String.format("%02d", (today.month + 1)) + "/" + today.year + "    -   Giờ: " + today.format("%l:%M:%S %P"));             // Day of the month (1-31)
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
            updateGridView();

        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            changeColumnCount(((GridLayout) findViewById(R.id.grid_layout_tk)));
            updateGridView();
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
        for (int i=0;i<gridLayout.getChildCount();i++)
        {
            View view=gridLayout.getChildAt(i);
            view.findViewById(R.id.layout_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // resetLayout();
                    // testPrinter();

                    if (v.isEnabled() &&  v.getTag()!=null)
                    {
                        final Integer index=Integer.valueOf(v.getTag().toString().split(";")[0]);
                        final String hdID=v.getTag().toString().split(";")[1];
                        final String sotien=v.getTag().toString().split(";")[2];
                        final String dienGiai=v.getTag().toString().replaceAll(index+";"+hdID+";"+sotien+";","");
                        hoaDonID=hdID;
                        updateHoaDonByID(index,sotien,dienGiai);
                    }

                }
            });

        }


        gridLayout.requestLayout();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {

                }
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        int countView = gridLayout.getChildCount();
                        for (int i = 0; i < countView; i++) {
                            Log.d("W-I-G", gridLayout.getChildAt(i).getWidth() + " - " + gridLayout.getChildAt(i).getHeight());    LinearLayout.LayoutParams lParam = (LinearLayout.LayoutParams) gridLayout.getChildAt(i).findViewById(R.id.layout_button).getLayoutParams();
                            lParam.width = gridLayout.getChildAt(i).getWidth()-(int)MainActivity.convertDpToPixel(10,getApplicationContext());
                            lParam.height = gridLayout.getChildAt(i).getHeight()-(int)MainActivity.convertDpToPixel(10,getApplicationContext());
                            gridLayout.getChildAt(i).findViewById(R.id.layout_button).setLayoutParams(lParam);
                            gridLayout.getChildAt(i).requestLayout();
                            //lParam.width=  gridLayout.getChildAt(i).findViewById(R.id.button_text).getLayoutParams().width;
                            // lParam.height=  gridLayout.getChildAt(i).findViewById(R.id.button_text).getLayoutParams().height;
                            // gridLayout.getChildAt(i).findViewById(R.id.button_text).setLayoutParams(lParam);

                            gridLayout.getChildAt(i).findViewById(R.id.txt_button).setVisibility(View.VISIBLE);
                            resetTicket();
                            gridLayout.getChildAt(i).findViewById(R.id.txt_button).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (((View)v.getParent()).isEnabled() &&  ((View)v.getParent()).getTag()!=null)
                                    {
                                        final Integer index=Integer.valueOf(((View)v.getParent()).getTag().toString().split(";")[0]);
                                        final String hdID=((View)v.getParent()).getTag().toString().split(";")[1];
                                        final String sotien=((View)v.getParent()).getTag().toString().split(";")[2];
                                        final String dienGiai=((View)v.getParent()).getTag().toString().replaceAll(index+";"+hdID+";"+sotien+";","");
                                        hoaDonID=hdID;
                                        updateHoaDonByID(index,sotien,dienGiai);
                                    }
                                }
                            });
                        }


                    }
                });

            }
        }).start();


    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
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
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        hasSyncRest.put(ste[2].getMethodName(),false);
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
                hasSyncRest.put(ste[2].getMethodName(),true);
                checkAllDataReady();
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
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        hasSyncRest.put(ste[2].getMethodName(),false);
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
                hasSyncRest.put(ste[2].getMethodName(),true);
                checkAllDataReady();
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
        for (int i = 0; i < ListID.size(); i++) {
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
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        hasSyncRest.put(ste[2].getMethodName(),false);
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

                    THOIGIANTOANTRAM.add(DmTuyen.get(i).GETTHOIGIANTOANTRAM()!=null?DmTuyen.get(i).GETTHOIGIANTOANTRAM().toString():"");

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
                hasSyncRest.put(ste[2].getMethodName(),true);
                checkAllDataReady();
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
                    new DmTuyen(
                            IDTUYEN.get(i),
                            MATUYEN.get(i),
                            TENTUYENEND.get(i),
                            TENTUYENVN.get(i),
                            MATRAMDAU.get(i),
                            MATRAMGIUA.get(i),
                            MATRAMCUOI.get(i),
                            TONGTRAM.get(i),
                            MUCDO.get(i),
                            THOIGIANTOANTRAM.get(i),
                            GIAVE1.get(i),
                            DIENGIAIVE1.get(i),
                            CAMVE1.get(i),
                            IDVE1IDHOADON.get(i),
                            GIAVE2.get(i),
                            DIENGIAIVE2.get(i)
                            ,CAMVE2.get(i),
                            IDVE2IDHOADON.get(i),
                            GIAVE3.get(i),
                            DIENGIAIVE3.get(i),
                            CAMVE3.get(i),
                            IDVE3IDHOADON.get(i),
                            GIAVE4.get(i),
                            DIENGIAIVE4.get(i),
                            CAMVE4.get(i),
                            IDVE4IDHOADON.get(i),
                            GIAVE5.get(i),
                            DIENGIAIVE5.get(i),
                            CAMVE5.get(i),
                            IDVE5IDHOADON.get(i),
                            GIAVE6.get(i),
                            DIENGIAIVE6.get(i),
                            CAMVE6.get(i),
                            IDVE6IDHOADON.get(i)

                    )
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
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        hasSyncRest.put(ste[2].getMethodName(),false);
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
                hasSyncRest.put(ste[2].getMethodName(),true);
                checkAllDataReady();
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
        for (int i = 0; i < ListMaXe.size(); i++) {
            items.add( new DmXe(ListMaXe.get(i),ListSOXE.get(i),ListLOAIXE.get(i),ListMATAIXE.get(i), ListSOGHE.get(i)));
        }
        return items;
    }

    private void loadDataAllXe() {
        ItemAllDmXe = getAllXe();
    }



    ////LoadCHiTietTuyen//////
    List<DmTuyenChiTietTram> ItemAllCHiTietTuyen;
    List<String> ListIDChiTiet = new ArrayList<>();
    List<String> ListIDTUYEN = new ArrayList<>();
    List<String> ListMATRAM = new ArrayList<>();
    List<String> ListTRAMDAU = new ArrayList<>();
    List<String> ListTRAMCUOI = new ArrayList<>();

    public void CallCHiTietTuyen() {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        hasSyncRest.put(ste[2].getMethodName(),false);
        ResClien restClient = new ResClien();
        restClient.GetService().GetDMTUYENCHITIETTRAMs(new Callback<List<DmTuyenChiTietTram>>() {
            @Override
            public void success(List<DmTuyenChiTietTram> DmXe, Response response) {
                for (int i = 0; i < DmXe.size(); i++) {

                    ListIDChiTiet.add(DmXe.get(i).getId().toString());
                    ListIDTUYEN.add(DmXe.get(i).getIdTuyen());
                    ListMATRAM.add(DmXe.get(i).getMaTram());
                    ListTRAMDAU.add(Boolean.toString(DmXe.get(i).getTramDau()!=null?DmXe.get(i).getTramDau():false));
                    ListTRAMCUOI.add(Boolean.toString(DmXe.get(i).getTramCuoi()!=null?DmXe.get(i).getTramCuoi():false));

                }
                loadCHiTietTuyen();
                hasSyncRest.put(ste[2].getMethodName(),true);
                checkAllDataReady();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }

    private List<DmTuyenChiTietTram> getCHiTietTuyen() {
        List<DmTuyenChiTietTram> items = new ArrayList<>();
        for (int i = 0; i < ListIDChiTiet.size(); i++) {
            items.add( new DmTuyenChiTietTram(ListIDChiTiet.get(i),ListIDTUYEN.get(i),ListMATRAM.get(i),ListTRAMDAU.get(i), ListTRAMCUOI.get(i)));
        }
        return items;
    }

    private void loadCHiTietTuyen() {
        ItemAllCHiTietTuyen = getCHiTietTuyen();
    }

    ////LoadLoTrinhTuyen//////
    List<LoTrinhChoXe> ItemAllLoTrinhChoXe;
    List<String> ListIDLoTrinhChoXe = new ArrayList<>();
    List<String> ListIDTUYENLoTrinhChoXe = new ArrayList<>();
    List<String> ListMAXELoTrinhChoXe= new ArrayList<>();
    List<String> ListMATAIXELoTrinhChoXe = new ArrayList<>();
    List<String> ListCAM = new ArrayList<>();
    List<String> ListKICHHOAT = new ArrayList<>();
    public void CallLoTrinhChoXe() {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        hasSyncRest.put(ste[2].getMethodName(),false);
        ResClien restClient = new ResClien();
        restClient.GetService().GetLOTRINHCHOXEs(new Callback<List<LoTrinhChoXe>>() {
            @Override
            public void success(List<LoTrinhChoXe> DmXe, Response response) {
                for (int i = 0; i < DmXe.size(); i++) {

                    ListIDLoTrinhChoXe.add(DmXe.get(i).getIdLoTrinh().toString());
                    ListIDTUYENLoTrinhChoXe.add(DmXe.get(i).getIdTuyen());
                    ListMAXELoTrinhChoXe.add(DmXe.get(i).getMaXe());
                    ListMATAIXELoTrinhChoXe.add(DmXe.get(i).getMaTaiXe().toString());
                    ListCAM.add(DmXe.get(i).getCam().toString());
                    ListKICHHOAT.add(DmXe.get(i).getKichHoat()!=null? DmXe.get(i).getKichHoat().toString():"True");

                }
                loadLoTrinhChoXe();
                hasSyncRest.put(ste[2].getMethodName(),true);
                checkAllDataReady();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }

    private List<LoTrinhChoXe> getLoTrinhChoXe() {
        List<LoTrinhChoXe> items = new ArrayList<>();
        for (int i = 0; i < ListIDLoTrinhChoXe.size(); i++) {
            items.add( new LoTrinhChoXe(ListIDLoTrinhChoXe.get(i),ListMAXELoTrinhChoXe.get(i),ListIDTUYENLoTrinhChoXe.get(i),ListMATAIXELoTrinhChoXe.get(i), ListCAM.get(i), ListKICHHOAT.get(i)));
        }
        return items;
    }

    private void loadLoTrinhChoXe() {
        ItemAllLoTrinhChoXe = getLoTrinhChoXe();
    }


    /////
    List<Counters> ItemCounters;
    List<String> CopyRight=new ArrayList<>();
    List<String> LogoCopyRight=new ArrayList<>();
    List<String> CopyRightKey=new ArrayList<>();
    List<String> ChedoInCSDL=new ArrayList<>();
    List<String> ChedoInDefault=new ArrayList<>();
    List<String> SetDefaultIn=new ArrayList<>();
    List<String> MutiServices=new ArrayList<>();
    List<String> TransferAuto=new ArrayList<>();
    List<String> TransferTime=new ArrayList<>();
    List<String> PGDCode=new ArrayList<>();
    List<String> AutoNext=new ArrayList<>();
    List<String> BackupSQLserver=new ArrayList<>();
    List<String> TieudeVN=new ArrayList<>();
    List<String> TieudeENG=new ArrayList<>();
    List<String> MST=new ArrayList<>();
    List<String> DT=new ArrayList<>();
    List<String> DCPRINT=new ArrayList<>();
    List<String> MAXE=new ArrayList<>();
    List<String> Luot=new ArrayList<>();
    List<String> Lastday=new ArrayList<>();
    List<String> TenCongTy=new ArrayList<>();
    List<String> DiaChi=new ArrayList<>();
    public void CallCounters() {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        hasSyncRest.put(ste[2].getMethodName(),false);
        ResClien restClient = new ResClien();
        restClient.GetService().GetCounters(new Callback<List<Counters>>() {
            @Override
            public void success(List<Counters> DmXe, Response response) {
                for (int i = 0; i < DmXe.size(); i++) {

                    CopyRight.add(DmXe.get(i).getCopyRight().toString());
                    LogoCopyRight.add(DmXe.get(i).getLogoCopyRight());
                    CopyRightKey.add(DmXe.get(i).getCopyRightKey());
                    ChedoInCSDL.add(DmXe.get(i).getChedoInCSDL()!=null?DmXe.get(i).getChedoInCSDL().toString():"");
                    ChedoInDefault.add(DmXe.get(i).getChedoInDefault()!=null?DmXe.get(i).getChedoInDefault().toString():"");
                    SetDefaultIn.add(DmXe.get(i).getSetDefaultIn()!=null?DmXe.get(i).getSetDefaultIn().toString():"");
                    MutiServices.add(DmXe.get(i).getMutiServices()!=null?DmXe.get(i).getMutiServices().toString():"");
                    TransferAuto.add(DmXe.get(i).getTransferAuto()!=null?DmXe.get(i).getTransferAuto().toString():"");
                    TransferTime.add(DmXe.get(i).getTransferTime()!=null?DmXe.get(i).getTransferTime().toString():"");
                    PGDCode.add(DmXe.get(i).getPGDCode()!=null?DmXe.get(i).getPGDCode():"");
                    AutoNext.add(Boolean.toString(DmXe.get(i).getAutoNext()!=null?DmXe.get(i).getAutoNext():false));
                    BackupSQLserver.add(Boolean.toString(DmXe.get(i).getBackupSQLserver()!=null?DmXe.get(i).getBackupSQLserver():false));
                    TieudeVN.add(DmXe.get(i).getTieudeVN());
                    TieudeENG.add(DmXe.get(i).getTieudeENG());
                    MST.add(DmXe.get(i).getMST());
                    DT.add(DmXe.get(i).getDT());
                    DCPRINT.add(DmXe.get(i).getDCPRINT());
                    MAXE.add(DmXe.get(i).getMAXE());
                    Luot.add(DmXe.get(i).getLuot());
                    Lastday.add(DmXe.get(i).getLastday());
                    TenCongTy.add(DmXe.get(i).getTenCongTy());
                    DiaChi.add(DmXe.get(i).getDiaChi());

                }
                loadCounters();
                hasSyncRest.put(ste[2].getMethodName(),true);
                checkAllDataReady();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }

    private List<Counters> getCounters() {
        List<Counters> items = new ArrayList<>();
        for (int i = 0; i < MAXE.size(); i++) {
            items.add( new Counters(CopyRight.get(i),LogoCopyRight.get(i),CopyRightKey.get(i),ChedoInCSDL.get(i), ChedoInDefault.get(i), SetDefaultIn.get(i),
                    MutiServices.get(i), TransferAuto.get(i), TransferTime.get(i), PGDCode.get(i),
                    AutoNext.get(i), BackupSQLserver.get(i), TieudeVN.get(i), TieudeENG.get(i),
                    MST.get(i), DT.get(i), DCPRINT.get(i), MAXE.get(i),
                    Luot.get(i), Lastday.get(i), TenCongTy.get(i), DiaChi.get(i)));
        }
        return items;
    }

    private void loadCounters() {
        ItemCounters = getCounters();
    }
    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getApplicationContext(), DatabaseHelper.class);
        }
        return databaseHelper;
    }


    private Dao<Counters, Integer> CountersMangasDao;
    private List<Counters> CountersMangasList;
    public void LoadCountersBySqlite() {
        try {
            CountersMangasDao = getHelper().getCountMangasDao();
            QueryBuilder<Counters, Integer> queryBuilder = CountersMangasDao.queryBuilder();
            CountersMangasList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Dao<DmTaiXe, Integer> DmTaiXeMangasDao;
    private List<DmTaiXe> DmTaiXeMangasList;
    public void LoadDmTaiXeBySqlite() {
        try {
            DmTaiXeMangasDao = getHelper().getDmTaiXeMangasDao();
            QueryBuilder<DmTaiXe, Integer> queryBuilder =DmTaiXeMangasDao.queryBuilder();
            DmTaiXeMangasList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Dao<DmTram, Integer> DmTramMangasDao;
    private List<DmTram> DmTramMangasList;
    public void LoadDmTramBySqlite() {
        try {
            DmTramMangasDao = getHelper().getDmTramMangasDao();
            QueryBuilder<DmTram, Integer> queryBuilder =DmTramMangasDao.queryBuilder();
            DmTramMangasList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Dao<DmTuyen, Integer> DmTuyenMangasDao;
    private List<DmTuyen> DmTuyenMangasList;
    public void LoadDmTuyenBySqlite() {
        try {
            DmTuyenMangasDao = getHelper().getDmTuyenMangasDao();
            QueryBuilder<DmTuyen, Integer> queryBuilder =DmTuyenMangasDao.queryBuilder();
            DmTuyenMangasList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Dao<DmTuyenChiTietTram, Integer> DmTuyenChiTietTramMangasDao;
    private List<DmTuyenChiTietTram> DmTuyenChiTietTramMangasList;
    public void LoadDmTuyenChiTietTramBySqlite() {
        try {
            DmTuyenChiTietTramMangasDao = getHelper().getDmTuyenChiTietTramMangasDao();
            QueryBuilder<DmTuyenChiTietTram, Integer> queryBuilder =DmTuyenChiTietTramMangasDao.queryBuilder();
            DmTuyenChiTietTramMangasList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Dao<DmXe, Integer> DmXeMangasDao;
    private List<DmXe> DmXeMangasList;
    public void LoadDmXeBySqlite() {
        try {
            DmXeMangasDao = getHelper().getDmXeMangasDao();
            QueryBuilder<DmXe, Integer> queryBuilder =DmXeMangasDao.queryBuilder();
            DmXeMangasList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Dao<LoTrinhChoXe, Integer> LoTrinhChoXeMangasDao;
    private List<LoTrinhChoXe> LoTrinhChoXeMangasList;
    public void LoadLoTrinhChoXeBySqlite() {
        try {
            LoTrinhChoXeMangasDao = getHelper().getLoTrinhChoXeMangasDao();
            QueryBuilder<LoTrinhChoXe, Integer> queryBuilder =LoTrinhChoXeMangasDao.queryBuilder();
            LoTrinhChoXeMangasList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    ////LoadDmHoaDon///////
    List<DmHoaDon> ItemAllDmHoaDon;
    List<String> IDHOADON= new ArrayList<>();
    List<String> MAXEHoaDon= new ArrayList<>();
    List<String> KYHIEUVE= new ArrayList<>();
    List<String> MAUSO= new ArrayList<>();
    List<String> TONGSOVEPHATHANH= new ArrayList<>();
    List<String> SOVEHIENTAI= new ArrayList<>();
    List<String> IDVE= new ArrayList<>();

    public void CallDmHoaDon() {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        hasSyncRest.put(ste[2].getMethodName(),false);
        ResClien restClient = new ResClien();
        restClient.GetService().GetDMHOADONs(new Callback<List<DmHoaDon>>() {
            @Override
            public void success(List<DmHoaDon> DmHoaDon, Response response) {
                for (int i = 0; i < DmHoaDon.size(); i++) {
                    String getTONGSOVEPHATHANH = String.valueOf(DmHoaDon.get(i).getTONGSOVEPHATHANH());
                    String getSOVEHIENTAI = String.valueOf(DmHoaDon.get(i).getSOVEHIENTAI());
                    String getIDVE = String.valueOf(DmHoaDon.get(i).getIDVE());
                    IDHOADON.add(DmHoaDon.get(i).getIDHOADON().toString());
                    MAXEHoaDon.add(DmHoaDon.get(i).getMAXE().toString());
                    KYHIEUVE.add(DmHoaDon.get(i).getKYHIEUVE().toString());
                    MAUSO.add(DmHoaDon.get(i).getMAUSO().toString());
                    TONGSOVEPHATHANH.add(getTONGSOVEPHATHANH);
                    SOVEHIENTAI.add(getSOVEHIENTAI);
                    IDVE.add(getIDVE);



                }
                loadDataDmHoaDon();
                hasSyncRest.put(ste[2].getMethodName(),true);
                checkAllDataReady();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }

    private List<DmHoaDon> getAllItemsDmHoaDon() {
        List<DmHoaDon> items = new ArrayList<>();
        for (int i = 0; i < IDHOADON.size(); i++) {

            items.add(
                    new DmHoaDon(
                            IDHOADON.get(i),
                            MAXEHoaDon.get(i),
                            KYHIEUVE.get(i),
                            MAUSO.get(i),
                            TONGSOVEPHATHANH.get(i),
                            SOVEHIENTAI.get(i),
                            IDVE.get(i)
                    )
            );
        }
        return items;
    }

    private void loadDataDmHoaDon() {
        ItemAllDmHoaDon = getAllItemsDmHoaDon();
    }



    ////LoadDichVu///////
    List<DichVu> ItemAllDichVu;
    List<String> IDDichVu=new ArrayList<>();
    List<String> NGAYDichVu=new ArrayList<>();
    List<String> SQMSDichVu=new ArrayList<>();
    List<String> GIOLAYSODichVu=new ArrayList<>();
    List<String> dichvu1DichVu=new ArrayList<>();
    List<String> GIAVEDichVu=new ArrayList<>();
    List<String> MATUYENDichVu=new ArrayList<>();
    List<String> TENTUYENDichVu=new ArrayList<>();
    List<String> MATRAMDAUDichVu=new ArrayList<>();
    List<String> MATRAMCUOIDichVu=new ArrayList<>();
    List<String> LOTRINHDichVu=new ArrayList<>();
    List<String> BienSoXeDichVu=new ArrayList<>();
    List<String> TRANGTHAIDichVu=new ArrayList<>();
    List<String> PHUCVUDichVu=new ArrayList<>();
    List<String> GHICHUDichVu=new ArrayList<>();
    List<String> ControDichVu=new ArrayList<>();
    List<String> DocDichVu=new ArrayList<>();
    List<String> DATCHODichVu=new ArrayList<>();
    List<String> GIO_GOCDichVu=new ArrayList<>();
    List<String> BINH_CHONDichVu=new ArrayList<>();
    List<String> GIO_BINHCHONDichVu=new ArrayList<>();
    List<String> NGONNGUDichVu=new ArrayList<>();
    List<String> DIEMGIAODICHDichVu=new ArrayList<>();
    List<String> MANVDichVu=new ArrayList<>();
    List<String> QUAYCHUYENDichVu=new ArrayList<>();
    List<String> QUAYTHAMCHIEUDichVu=new ArrayList<>();
    List<String> SODIENTHOAIDichVu=new ArrayList<>();
    List<String> QUAYDichVu=new ArrayList<>();
    List<String> GIOPHUCVUDichVu=new ArrayList<>();
    List<String> MAXEDichVu=new ArrayList<>();
    List<String> MATAIXEDichVu=new ArrayList<>();
    List<String> MATRAMDichVu=new ArrayList<>();
    List<String> IDTUYENDichVu=new ArrayList<>();
    List<String> KYHIEUVEDichVu=new ArrayList<>();
    List<String> MAUSODichVu=new ArrayList<>();
    List<String> MATRAMGIUADichVu=new ArrayList<>();

    public void CallDichVu() {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        hasSyncRest.put(ste[2].getMethodName(),false);
        ResClien restClient = new ResClien();
        restClient.GetService().GetDICHVUs(new Callback<List<DichVu>>() {
            @Override
            public void success(List<DichVu> DichVu, Response response) {
                for (int i = 0; i < DichVu.size(); i++) {
                    String getID = String.valueOf(DichVu.get(i).getID());
                    String isPHUCVU = String.valueOf(DichVu.get(i).isPHUCVU());
                    String isContro = String.valueOf(DichVu.get(i).isContro());
                    String isDoc = String.valueOf(DichVu.get(i).isDoc());
                    String isDATCHO = String.valueOf(DichVu.get(i).isDATCHO());

                    IDDichVu.add(getID);
                    NGAYDichVu.add( DichVu.get(i).getNGAY());
                    SQMSDichVu.add(DichVu.get(i).getSQMS());
                    GIOLAYSODichVu.add( DichVu.get(i).getGIOLAYSO());
                    dichvu1DichVu.add( DichVu.get(i).getDichvu1());
                    GIAVEDichVu.add(DichVu.get(i).getGIAVE());
                    MATUYENDichVu.add( DichVu.get(i).getMATUYEN());
                    TENTUYENDichVu.add(DichVu.get(i).getTENTUYEN());
                    MATRAMDAUDichVu.add( DichVu.get(i).getMATRAMDAU());
                    MATRAMCUOIDichVu.add(DichVu.get(i).getMATRAMCUOI());
                    LOTRINHDichVu.add( DichVu.get(i).getLOTRINH());
                    BienSoXeDichVu.add( DichVu.get(i).getBienSoXe());
                    TRANGTHAIDichVu.add(DichVu.get(i).getTRANGTHAI());
                    PHUCVUDichVu.add( isPHUCVU);
                    GHICHUDichVu.add(DichVu.get(i).getGHICHU());
                    ControDichVu.add(isContro);
                    DocDichVu.add(isDoc);
                    DATCHODichVu.add(isDATCHO);
                    GIO_GOCDichVu.add( DichVu.get(i).getGIO_GOC());
                    BINH_CHONDichVu.add(DichVu.get(i).getBINH_CHON());
                    GIO_BINHCHONDichVu.add(DichVu.get(i).getGIO_BINHCHON());
                    NGONNGUDichVu.add(   DichVu.get(i).getNGONNGU());
                    DIEMGIAODICHDichVu.add( DichVu.get(i).getDIEMGIAODICH());
                    MANVDichVu.add( DichVu.get(i).getMANV());
                    QUAYCHUYENDichVu.add(DichVu.get(i).getQUAYCHUYEN());
                    QUAYTHAMCHIEUDichVu.add( DichVu.get(i).getQUAYTHAMCHIEU());
                    SODIENTHOAIDichVu.add( DichVu.get(i).getSODIENTHOAI());
                    QUAYDichVu.add( DichVu.get(i).getQUAY());
                    GIOPHUCVUDichVu.add( DichVu.get(i).getGIOPHUCVU());
                    MAXEDichVu.add( DichVu.get(i).getMAXE());
                    MATAIXEDichVu.add(DichVu.get(i).getMATAIXE());
                    MATRAMDichVu.add( DichVu.get(i).getMATRAM());
                    IDTUYENDichVu.add( DichVu.get(i).getIDTUYEN());
                    KYHIEUVEDichVu.add(  DichVu.get(i).getKYHIEUVE());
                    MAUSODichVu.add( DichVu.get(i).getMAUSO());
                    MATRAMGIUADichVu.add(DichVu.get(i).getMATRAMGIUA());


                }
                loadDataDichVu();
                hasSyncRest.put(ste[2].getMethodName(),true);
                checkAllDataReady();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }
    private List<DichVu> getAllItemsDichVu() {
        List<DichVu> items = new ArrayList<>();
        for (int i = 0; i < IDDichVu.size(); i++) {

            items.add(
                    new DichVu(
                            IDDichVu.get(i),
                            NGAYDichVu.get(i),
                            SQMSDichVu.get(i),
                            GIOLAYSODichVu.get(i),
                            dichvu1DichVu.get(i),
                            GIAVEDichVu.get(i),
                            MATUYENDichVu.get(i),
                            TENTUYENDichVu.get(i),
                            MATRAMDAUDichVu.get(i),
                            MATRAMCUOIDichVu.get(i),
                            LOTRINHDichVu.get(i),
                            BienSoXeDichVu.get(i),
                            TRANGTHAIDichVu.get(i),
                            PHUCVUDichVu.get(i),
                            GHICHUDichVu.get(i),
                            ControDichVu.get(i),
                            DocDichVu.get(i),
                            DATCHODichVu.get(i),
                            GIO_GOCDichVu.get(i),
                            BINH_CHONDichVu.get(i),
                            GIO_BINHCHONDichVu.get(i),
                            NGONNGUDichVu.get(i),
                            DIEMGIAODICHDichVu.get(i),
                            MANVDichVu.get(i),
                            QUAYCHUYENDichVu.get(i),
                            QUAYTHAMCHIEUDichVu.get(i),
                            SODIENTHOAIDichVu.get(i),
                            QUAYDichVu.get(i),
                            GIOPHUCVUDichVu.get(i),
                            MAXEDichVu.get(i),
                            MATAIXEDichVu.get(i),
                            MATRAMDichVu.get(i),
                            IDTUYENDichVu.get(i),
                            KYHIEUVEDichVu.get(i),
                            MAUSODichVu.get(i),
                            MATRAMGIUADichVu.get(i)
                    )
            );
        }
        return items;
    }

    private void loadDataDichVu() {
        ItemAllDichVu = getAllItemsDichVu();
    }


}
