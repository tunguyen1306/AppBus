package davisoft.app.busticket.util;


import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import davisoft.app.busticket.data.pojo.DmTram;

public class Helper {




    public  static  String testTram39="11.3465139;106.6394382|11.3464608;106.6393183|11.3464608;106.6393183|11.3542275;106.6338605|11.3542784;106.6338255|11.3640161;106.6276494|11.3640161;106.6276494|11.3727519;106.6225508|11.3727519;106.6225508|11.3921342;106.6138015|11.3921342;106.6138015|11.3924536;106.6071015|11.3924536;106.6071015|11.3927149;106.5998583|11.3925729;106.5998533|11.3922091;106.6108512|11.3922091;106.6108512|11.3921256;106.6136936|11.3921256;106.6136936|11.396416;106.6137422|11.396416;106.6137422|11.4083958;106.6141056|11.4083958;106.6141056|11.4131795;106.615456|11.4131795;106.615456|11.4159383;106.6155103|11.4159383;106.6155103|11.423972;106.6154493|11.423972;106.6154493|11.4285439;106.615444|11.4285439;106.615444|11.4285406;106.6154922|11.4285406;106.6154922|11.4285439;106.615444|11.4285439;106.615444|11.4370998;106.6154936|11.4370998;106.6154936|11.4439772;106.6155171|11.4439772;106.6155171|11.4542738;106.6155054|11.4542738;106.6155054|11.4647148;106.6152945|11.4647148;106.6152945|11.4740183;106.6149801|11.4740183;106.6149801|11.4829411;106.6145993|11.4829411;106.6145993|11.4830123;106.6139739|11.4830123;106.6139739|11.4830212;106.6139141|11.4830212;106.6139141|11.4830844;106.6125142|11.4830844;106.6125142|11.4831747;106.6105398|11.4831747;106.6105398|11.4833626;106.6069344|11.4833626;106.6069344|11.4833783;106.6063653|11.4833783;106.6063653|11.4831505;106.5887626|11.4831505;106.5887626|11.4718342;106.5891931|11.4718342;106.5891931|11.4260786;106.574032|11.4260786;106.574032|11.4232269;106.6076352|11.4232269;106.6076352|11.4233394;106.6153525|11.4233394;106.6153525|11.4829411;106.6145993|11.4829411;106.6145993|11.4830123;106.6139739|11.4830123;106.6139739|11.4830844;106.6125142|11.4830844;106.6125142|11.4831747;106.6105398|11.4831747;106.6105398|11.4829119;106.6125139|11.4829119;106.6125139|11.4829682;106.613258|11.4829682;106.613258|11.4829466;106.6145037|11.4829466;106.6145037|11.4880862;106.6145523|11.4880862;106.6145523|11.488212;106.6168947|11.488212;106.6168947|11.4883331;106.6195975|11.4883635;106.6196221|11.4884183;106.6145021|11.4884183;106.6145021|11.4963524;106.6144646|11.4963524;106.6144646|11.5084845;106.6144891|11.5084845;106.6144891|11.5178817;106.6147278|11.5178817;106.6147278|11.5260543;106.6153111|11.5260543;106.6153111|11.5341455;106.6158289|11.5341455;106.6158289|11.5506413;106.6162777|11.5506413;106.6162777|11.5615591;106.6166249|11.5615591;106.6166249|11.5738196;106.6170598|11.5738196;106.6170598|11.5816916;106.6139229|11.5816916;106.6139229|11.591129;106.6132982|11.591129;106.6132982|11.5996912;106.6154195|11.5996912;106.6154195|11.6091924;106.6126254|11.6091924;106.6126254|11.6286277;106.6100288";
    public  static Integer Luot=1;
    public  static DmTram oldTram=null;
    public  static DmTram currentTram=null;
    public  static DmTram nextTram=null;
    public  static HashMap<String,Integer> hasCountPlay=new HashMap<String,Integer>();


    public  static DmTram getNextTram(Context context,Location oldLocation,Location location, List<DmTram> ListTrambyTuyen)
    {
        double min=Double.MAX_VALUE;
        DmTram cuurent=null;
        List<String> listDistance=new ArrayList<>();
        for (int i=0;i<ListTrambyTuyen.size();i++)
        {
            DmTram dmTram= ListTrambyTuyen.get(i);
            double distance= SphericalUtil.computeDistanceBetween(new LatLng(location.getLatitude(),location.getLongitude()),new LatLng(dmTram.getLAT(),dmTram.getLNG()));
            if (min>distance)
            {
                min=distance;
                cuurent=dmTram;
            }
            listDistance.add(dmTram.getId()+"_"+distance);

        }
        Collections.sort(listDistance, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Double d1=Double.valueOf(o1.split("_")[1]);
                Double d2=Double.valueOf(o2.split("_")[1]);
                return d1.compareTo(d2);
            }
        });
        if (cuurent!=null && min<50 && Integer.valueOf(listDistance.get(0).split("_")[0])==cuurent.getId())
        {
            if (currentTram!=null && currentTram.getId()!=cuurent.getId())
            {
                if (currentTram!=null)
                {
                    oldTram=(DmTram)deepClone( currentTram);
                }
                currentTram=(DmTram)deepClone(cuurent);
            }
            if (currentTram==null)
            {
                currentTram=(DmTram)deepClone(cuurent);
            }
            if (!hasCountPlay.containsKey(currentTram.getId()+""))
            {
                hasCountPlay.put(currentTram.getId()+"",1);
                try {
                    if (currentTram.getFileTram()!=null&& currentTram.getFileTram().trim().length()>0)
                        //playAudioTrack(currentTram.getFileTram());
                            runFile(context,currentTram.getFileTram());
                       // playAudio(currentTram.getFileTram());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
            else
                hasCountPlay.put(currentTram.getId()+"",hasCountPlay.get(currentTram.getId()+"")+1);
            nextTram=null;
        }
        else
        {
            if (currentTram!=null)
            {
                hasCountPlay.remove(currentTram.getId()+"");
                oldTram=(DmTram)deepClone( currentTram);
            }
            currentTram=null;
            if (oldTram!=null)
            {
                int iTram=0;
                for (int i=0;i<ListTrambyTuyen.size();i++)
                {
                    if (ListTrambyTuyen.get(i).getId().equals(oldTram.getId()))
                    {
                        iTram=i;
                        break;
                    }

                }
                if (iTram>=0 &&iTram<ListTrambyTuyen.size())
                {
                    nextTram=ListTrambyTuyen.get(iTram+1);
                }

            }
            else
            {
                if (oldLocation!=null)
                {
                    double minOld=Double.MAX_VALUE;
                   // DmTram cuurentOld=null;

                    for (int i=0;i<ListTrambyTuyen.size();i++)
                    {
                        DmTram dmTram= ListTrambyTuyen.get(i);
                        double distance= SphericalUtil.computeDistanceBetween(new LatLng(oldLocation.getLatitude(),oldLocation.getLongitude()),new LatLng(dmTram.getLAT(),dmTram.getLNG()));
                        if (min>distance)
                        {
                            minOld=distance;
                         //   cuurentOld=dmTram;
                        }

                    }

                    if (minOld>min)
                    {
                        nextTram=cuurent;
                    }else
                    {
                        nextTram=null;
                        currentTram=null;
                    }

                }
                else
                {
                    nextTram=null;
                    currentTram=null;
                    oldTram=null;
                }
            }


        }

        return  nextTram;
    }
    public static  MediaPlayer mediaPlayer=null;
    public  static  void  runFile(Context context,String filePath)
    {
        String f = Environment.getExternalStorageDirectory().getAbsolutePath();
        f= f + "/davibuss/"+filePath;
        if (mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }

        mediaPlayer = MediaPlayer.create(context, Uri.parse(f));
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }



    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static float convertDpToPixel(float dp, Context context)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
    public static float convertPixelsToDp(float px, Context context)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
