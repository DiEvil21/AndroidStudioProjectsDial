package ru.sport.historicalmvvmfootball;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InfoApi {

    @GET("FootballMvvmFacts/facts.json")
    Call<List<FactsData>> getFacts();

    @GET("FootballMvvmFacts/quote.json")
    Call<List<QuotesData>> getQuotes();
}
