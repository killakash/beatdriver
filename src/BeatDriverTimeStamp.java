import java.sql.Timestamp;

public class BeatDriverTimeStamp{
        private boolean isMorningShift;
        private Timestamp timestamp;

        public boolean isMorningShift() {
            return isMorningShift;
        }

        public void setMorningShift(boolean morningShift) {
            isMorningShift = morningShift;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }
    }