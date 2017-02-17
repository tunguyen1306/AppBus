package davisoft.app.busticket.printer;

import java.io.IOException;
import android.content.Context;

import davisoft.app.busticket.adapter.USBAdapter;

public class PrintOrder {

    public PrintOrder(){

    }

    public void Print(Context context,String msg){
        USBAdapter usba = new USBAdapter();
        usba.createConn(context);
        try {
            usba.printMessage(context,msg);
            usba.closeConnection(context);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
   /* CÔNG TY TNHH XE BUÝT BECAMEX TOKYU
    DC:NP6-5,Đường 30/4,P.Phú Hòa,TP.TDM,Bình Dương
    MST : 3702255565  -   ĐT: 0650-222-0555
    Mẫu số: 01VEDB1/011
    Ký hiệu: AA/13T
    Số vé: 0000234
    Giá 10.000
    Liên:Giao cho hành khách (Vé đã bao gồm bảo hiểm hành khách)
    Ngày: 16/02/2017			Giờ:  5:38:15 PM
    Tuyến 51 Tòa Nhà Becamex -- ĐH Quốc tế Miền Đông
    : Số xe:   61B-007.26
    Giá vé: 10000 đồng/lượt/HK
    Vui lòng giữ vé để kiểm soát
    In tại CÔNG TY TNHH XE BUÝT BECAMEX TOKYU
    MST : 3702255565  -   ĐT : 0650-222-0555
    Liên:Giao cho hành khách*/
    public String testData()
    {
        PrinterOptions p=new PrinterOptions();

        p.resetAll();
        p.initialize();
        p.feedBack((byte)2);
        p.color(0);
        p.alignCenter();
        p.setText("CONG TY TNHH XE BUYT BECAMEX TOKYU");
        p.newLine();
        p.setText("CÔNG TY TNHH XE BUÝT BECAMEX TOKYU");
        p.newLine();
        p.alignCenter();
        p.setText("DC:NP6-5,Đuong 30/4,P.Phu Hoa,TP.TDM,Binh Duong");
        p.newLine();
        p.alignCenter();
        p.setText("MST : 3702255565  -  DT: 0650-222-0555");

        p.newLine();
        p.alignLeft();
        p.setText("\t\t\tMau so: 01VEDB1/011");
        p.alignLeft();
        p.setText("\t\t\tKy hieu: AA/13T");
        p.newLine();
        p.alignLeft();
        p.setText("\t\t\tSo ve: 0000234");
        p.newLine();
        p.alignCenter();
        p.chooseFont(1);
        p.setText("Gia 10.000");

        p.newLine();
        p.chooseFont(1);
        p.alignCenter();
        p.setText("Lien:Giao cho hanh khach (da bao gom bao hiem hanh khach)");

        p.newLine();
        p.alignLeft();
        p.setText("Ngay: 16/02/2017");
        p.setText("\t\tGio:  5:38:15 PM");
        p.newLine();
        p.chooseFont(1);
        p.alignCenter();
        p.setText("Tuyen: 51 Toa nha Becamex -- DH Quoc Te Mien Dong");

        p.newLine();
        p.chooseFont(1);
        p.alignLeft();
        p.setText("So xe: 61B-007.26");

        p.newLine();
        p.alignLeft();

        p.setText("Gia ve: 10000 dong/luot/HK");

        p.newLine();
        p.chooseFont(1);
        p.alignCenter();
        p.setText("Vui long giu ve de kiem soat");


        p.chooseFont(1);
        p.alignCenter();
        p.setText("In tai CONG TY TNHH XE BUYT BECAMEX TOKYU");

        p.chooseFont(1);
        p.alignCenter();
        p.setText("Lien giao cho hanh khach");


        p.newLine();
        p.alignLeft();
        p.setText("MST : 3702255565");

        p.setText("\t\t\tDT: 0650-222-0555");
        p.newLine();

        p.feed((byte)3);
        p.finit();
        return p.finalCommandSet();

    }
    public class PrinterOptions {
        String commandSet="";
        public String initialize()
        {
            final byte[] Init={27,64};
            commandSet+=new String(Init);
            return new String(Init);
        }
        public String chooseFont(int Options)
        {
            String s="";
            final byte[] ChooseFontA={27,77,0};
            final byte[] ChooseFontB={27,77,1};
            final byte[] ChooseFontC={27,77,48};
            final byte[] ChooseFontD={27,77,49};

            switch(Options)
            {
                case 1:
                    s=new String(ChooseFontA);
                    break;

                case 2:
                    s=new String(ChooseFontB);
                    break;

                case 3:
                    s=new String(ChooseFontC);
                    break;

                case 4:
                    s=new String(ChooseFontD);
                    break;

                default:
                    s=new String(ChooseFontB);
            }
            commandSet+=s;
            return new String(s);
        }
        public String feedBack(byte lines)
        {
            final byte[] Feed={27,101,lines};
            String s=new String(Feed);
            commandSet+=s;
            return s;
        }
        public String feed(byte lines)
        {
            final byte[] Feed={27,100,lines};
            String s=new String(Feed);
            commandSet+=s;
            return s;
        }
        public String alignLeft()
        {
            final byte[] AlignLeft={27, 97,48};


            String s=new String(AlignLeft);
            commandSet+=s;
            return s;
        }
        public String alignCenter()
        {
            final byte[] AlignCenter={27, 97,49};


            String s=new String(AlignCenter);
            commandSet+=s;
            return s;
        }
        public String alignRight()
        {
            final byte[] AlignRight={27, 97,50};


            String s=new String(AlignRight);
            commandSet+=s;
            return s;
        }
        public String newLine()
        {
            final  byte[] LF={10};


            String s=new String(LF);
            commandSet+=s;
            return s;
        }
        public String reverseColorMode(boolean enabled)
        {
            final byte[] ReverseModeColorOn={29 ,66,1};
            final byte[] ReverseModeColorOff={29 ,66,0};

            String s="";
            if(enabled)
            {
                s=new String(ReverseModeColorOn);
            }
            else
            {
                s=new String(ReverseModeColorOff);
            }

            commandSet+=s;

            return s;
        }
        public String doubleStrike(boolean enabled)
        {
            final byte[] DoubleStrikeModeOn={27, 71,1};
            final byte[] DoubleStrikeModeOff={27, 71,0};

            String s="";
            if(enabled)
            {
                s=new String(DoubleStrikeModeOn);
            }
            else
            {
                s=new String(DoubleStrikeModeOff);
            }

            commandSet+=s;
            return s;
        }
        public String doubleHeight(boolean enabled)
        {
            final byte[] DoubleHeight={27,33,17};
            final byte[] UnDoubleHeight={27,33,0};
            String s="";
            if(enabled)
            {
                s=new String(DoubleHeight);
            }
            else
            {
                s=new String(UnDoubleHeight);
            }


            commandSet+=s;
            return s;
        }
        public String emphasized(boolean enabled)
        {
            final byte[] EmphasizedOff={27 ,0};
            final byte[] EmphasizedOn={27 ,1};

            String s="";
            if(enabled)
            {
                s=new String(EmphasizedOn);
            }
            else
            {
                s=new String(EmphasizedOff);
            }


            commandSet+=s;
            return s;
        }
        public String underLine(int Options)
        {
            final byte[] UnderLine2Dot={27 ,45,50};
            final byte[] UnderLine1Dot={27 ,45,49};
            final byte[] NoUnderLine={27 ,45,48};

            String s="";
            switch(Options)
            {
                case 0:
                    s=new String(NoUnderLine);
                    break;

                case 1:
                    s=new String(UnderLine1Dot);
                    break;

                default:
                    s=new String(UnderLine2Dot);
            }
            commandSet+=s;
            return new String(s);
        }
        public String color(int Options)
        {

            final byte[] ColorRed={27,114,49};
            final byte[] ColorBlack={27,114,48};
            String s="";
            switch(Options)
            {
                case 0:
                    s=new String(ColorBlack);
                    break;

                case 1:
                    s=new String(ColorRed);
                    break;

                default:
                    s=new String(ColorBlack);
            }

            commandSet+=s;
            return s;
        }
        public String finit()
        {
            final byte[] FeedAndCut={29,'V',66,0};


            String s=new String(FeedAndCut);

            final byte[] DrawerKick={27,70,0,60,120};


            s+=new String(DrawerKick);

            commandSet+=s;
            return s;
        }
        public String addLineSeperator()
        {
            String lineSpace="----------------------------------------";
            commandSet+=lineSpace;
            return lineSpace;
        }
        public void resetAll()
        {
            commandSet="";
        }
        public void setText(String s)
        {
            commandSet+=s;
        }
        public String finalCommandSet()
        {
            return commandSet;
        }
    }

}
