package ru.academicians.myhelper.model;

import javax.validation.constraints.NotNull;

public class SubscribeRequest {

    @NotNull
    private long subscriberId;

    @NotNull
    private long dealId;

    public SubscribeRequest() {
    }

    public long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(long subscriberId) {
        this.subscriberId = subscriberId;
    }

    public long getDealId() {
        return dealId;
    }

    public void setDealId(long dealId) {
        this.dealId = dealId;
    }
}
