package site.kobatomo.akushukai.Member;

/**
 * Created by tomoya on 2018/06/09.
 */

public class MainMember {
        private String year;
        private String month;
        private String day;
        private String location;
        private String place;
        private String type;
    private String eventId;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBusuu() {
            return busuu;
        }

        public void setBusuu(String busuu) {
            this.busuu = busuu;
        }

        private String busuu;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }
}
