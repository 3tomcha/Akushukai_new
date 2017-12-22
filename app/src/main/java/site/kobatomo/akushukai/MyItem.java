package site.kobatomo.akushukai;

import java.util.Date;

/**
 * Created by tomoya on 2017/12/06.
 */

public class MyItem {
        public String member = null;
        public String busuu = null;
        public String url = null;
        public long id = 0;

        public MyItem(String member, String busuu, String url) {
            super();
            this.member = member;
            this.busuu = busuu;
            this.url = url;
            /*画像に関しても加える*/

            this.id = new Date().getTime();
        }

        public String getmember() {
            return member;
        }


        //        URLを画像に変換する
        public String geturl() {
            return url;
        }

        public String getbusuu() {
            return busuu;
        }

        public long getId() {
            return id;
        }

    }

