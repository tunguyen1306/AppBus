package davisoft.app.busticket.util;


import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import davisoft.app.busticket.data.pojo.DmTram;

public class Helper {

    public  static Integer Luot=1;
    public  static DmTram oldTram=null;
    public  static DmTram currentTram=null;
    public  static DmTram nextTram=null;
    public  static DmTram getNextTram(Location oldLocation,Location location, List<DmTram> ListTrambyTuyen)
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
            nextTram=null;
        }
        else
        {
            if (currentTram!=null)
            {
                oldTram=(DmTram)deepClone( currentTram);
            }
            currentTram=null;
            if (oldTram!=null)
            {
                int iTram=0;
                for (int i=0;i<ListTrambyTuyen.size();i++)
                {
                    if (ListTrambyTuyen.get(i).getId()==oldTram.getId())
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
                    DmTram cuurentOld=null;

                    for (int i=0;i<ListTrambyTuyen.size();i++)
                    {
                        DmTram dmTram= ListTrambyTuyen.get(i);
                        double distance= SphericalUtil.computeDistanceBetween(new LatLng(oldLocation.getLatitude(),oldLocation.getLongitude()),new LatLng(dmTram.getLAT(),dmTram.getLNG()));
                        if (min>distance)
                        {
                            minOld=distance;
                            cuurentOld=dmTram;
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
}
