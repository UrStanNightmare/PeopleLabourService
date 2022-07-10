package ru.academicians.myhelper.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class AllDealsResponse {
    private Set<DealInfoResponse> deals;

    public AllDealsResponse() {
        this.deals = new LinkedHashSet<>();
    }

    public AllDealsResponse(Set<DealInfoResponse> deals) {
        this.deals = deals;
    }

    public Set<DealInfoResponse> getDeals() {
        return deals;
    }

    public void setDeals(Set<DealInfoResponse> deals) {
        this.deals = deals;
    }

    public void addDeal(DealInfoResponse deal){
        this.deals.add(deal);
    }
}
