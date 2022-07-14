package ru.academicians.myhelper.model;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AllDealsResponse {
    private Set<DealInfoResponse> deals;

    private Map<String, Object> filter;

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

    public Map<String, Object> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, Object> filter) {
        this.filter = filter;
    }
}
