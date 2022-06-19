package com.pritul.quoteapp;

import java.util.List;

public interface QuoteResponseListener {
    void didFetch(List<QuoteResponse> responses,String message);
    void diderror(String message);
}
