package ru.academicians.myhelper.model;

import javax.validation.constraints.NotNull;

public class SubscriptionRecord {
    @NotNull
    private Long dealId;

    @NotNull
    private Long subscriberId;

    public SubscriptionRecord() {
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    public Long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Long subscriberId) {
        this.subscriberId = subscriberId;
    }
}
