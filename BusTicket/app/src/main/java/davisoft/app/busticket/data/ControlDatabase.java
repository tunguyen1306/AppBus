package davisoft.app.busticket.data;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import davisoft.app.busticket.data.pojo.DmTaiXe;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ControlDatabase {
    ////Advert Read///////
    static List<DmTaiXe> ItemAllDmTaiXe;
    static  List<String> ListmaTaiXe = new ArrayList<>();
     static List<String> ListtenTaiXe = new ArrayList<>();
    static List<String> Listtuoi = new ArrayList<>();
    static List<String> ListgioiTinh = new ArrayList<>();
    static List<String> ListbangLai = new ArrayList<>();
    static List<String> Listsdt = new ArrayList<>();
    static List<String> ListdiaChiNoiO = new ArrayList<>();
    static  List<String> Listemail = new ArrayList<>();


    ////End Advert Read///////
    public static   void LoadDmTaiXe() {
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
                getAllItemsAllAdvert();
            }
            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }
    public static List<DmTaiXe> getAllItemsAllAdvert() {

        List<DmTaiXe> items = new ArrayList<>();
        for (int i = 0; i < ListmaTaiXe.size(); i++) {
            items.add(
                    new DmTaiXe(
                            ListmaTaiXe.get(i),
                            ListtenTaiXe.get(i),
                            Listtuoi.get(i) ,
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

    public static  List<DmTaiXe>  loadDataAllAdvert() {
        LoadDmTaiXe();
        ItemAllDmTaiXe = getAllItemsAllAdvert();

       return ItemAllDmTaiXe;
    }

}
