package hotP2B.WageGainTools.android.bean;

import java.util.List;

public class InvestHistory2 extends BaseResponse {

    private static final long serialVersionUID = 1L;

    private List<InvestHistory2Item> data;

    public List<InvestHistory2Item> getData() {
        return data;
    }

    public void setData(List<InvestHistory2Item> data) {
        this.data = data;
    }

    public static class InvestHistory2Item {
        public String getBidName() {
            return BidName == null ? "" : BidName;
        }

        public void setBidName(String bidName) {
            BidName = bidName;
        }

        public String getCreateTime() {
            return CreateTime == null ? "" : CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }

        public String getTransAmt() {
            return TransAmt == null ? "" : TransAmt;
        }

        public void setTransAmt(String transAmt) {
            TransAmt = transAmt;
        }

        public String getInteRest() {
            return InteRest == null ? "" : InteRest;
        }

        public void setInteRest(String inteRest) {
            InteRest = inteRest;
        }

        public String getEndTime() {
            return EndTime == null ? "" : EndTime;
        }

        public void setEndTime(String endTime) {
            EndTime = endTime;
        }

        public String getAlreadyCollection() {
            return AlreadyCollection == null ? "" : AlreadyCollection;
        }

        public void setAlreadyCollection(String alreadyCollection) {
            AlreadyCollection = alreadyCollection;
        }

        public String getAlreadyInteRest() {
            return AlreadyInteRest == null ? "" : AlreadyInteRest;
        }

        public void setAlreadyInteRest(String alreadyInteRest) {
            AlreadyInteRest = alreadyInteRest;
        }

        public String getWaitingCollection() {
            return WaitingCollection == null ? "" : WaitingCollection;
        }

        public void setWaitingCollection(String waitingCollection) {
            WaitingCollection = waitingCollection;
        }

        public String getWaitingInteRest() {
            return WaitingInteRest == null ? "" : WaitingInteRest;
        }

        public void setWaitingInteRest(String waitingInteRest) {
            WaitingInteRest = waitingInteRest;
        }

        private String BidName;
        private String CreateTime;
        private String TransAmt;
        private String InteRest;
        private String EndTime;
        private String AlreadyCollection;
        private String AlreadyInteRest;
        private String WaitingCollection;
        private String WaitingInteRest;
    }
}
