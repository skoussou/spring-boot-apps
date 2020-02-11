package it.sia.model;

/**
 * Health Payload
 * 
 * @author Stelios Kousouris
 * 
 */
public class Health {

    public enum STATUS {
        RUNNING, NOT_READY, FAILED;
    }

    private STATUS status;
    private HealthInfo info;

    public Health() {
        this.status = STATUS.RUNNING;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public HealthInfo getInfo() {
        return info;
    }

    public void setInfo(HealthInfo info) {
        this.info = info;
    }

    public static class Builder {
        private STATUS status;
        private HealthInfo info;

        public Builder status(STATUS s) {
            this.status = s;
            return this;
        }

        public Builder writeLatency(double wl) {
            if (info == null)
                this.info = new HealthInfo();
            this.info.setWRITE_LATENCY(wl);
            return this;
        }

        public Builder readLatency(double rl) {
            if (info == null)
                this.info = new HealthInfo();
            this.info.setREAD_LATENCY(rl);
            return this;
        }

        public Builder maxHRThreads(int maxhrt) {
            if (info == null)
                this.info = new HealthInfo();
            this.info.setMAX_HOTROD_THREADS(maxhrt);
            return this;
        }

        public Builder maxHRActiveThreads(int acthrt) {
            if (info == null)
                this.info = new HealthInfo();
            this.info.setMAX_HOTROD_THREADS_ACTIVE(acthrt);
            return this;
        }

        public Builder poolHRThreads(int poolhrt) {
            if (info == null)
                this.info = new HealthInfo();
            this.info.setMAX_HOTROD_THREADS_POOL_SIZE(poolhrt);
            return this;
        }

        public Builder peakHRThreads(int peakhrt) {
            if (info == null)
                this.info = new HealthInfo();
            this.info.setMAX_HOTROD_THREADS_USAGE_PEAK(peakhrt);
            return this;
        }

        public Health build() {
            return new Health(this);
        }
    }

    private Health(Builder b) {
        this.status = b.status;
        this.info = b.info;
    }
}
