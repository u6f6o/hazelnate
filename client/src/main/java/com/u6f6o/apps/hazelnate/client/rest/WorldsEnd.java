package com.u6f6o.apps.hazelnate.client.rest;

import com.u6f6o.apps.hazelnate.client.rest.transformer.JsonResponseTransformer;
import com.u6f6o.apps.hazelnate.client.service.WorldService;
import spark.ResponseTransformer;

import static spark.Spark.get;

public class WorldsEnd {
    private static final ResponseTransformer DEFAULT_TRANSFORMER = new JsonResponseTransformer();
    private static final WorldService WORLD_SERVICE = new WorldService();

    public static void main(String[] args) {
        get("/world",
                (req, resp) -> WORLD_SERVICE.fetchWorld(),
                DEFAULT_TRANSFORMER);

        get("/world/countries/all",
                (req, resp) -> WORLD_SERVICE.fetchAllCountries(),
                DEFAULT_TRANSFORMER);

        get("/world/countries/:countryId",
                (req, resp) -> WORLD_SERVICE.fetchCountry(Long.valueOf(req.params(":countryId"))),
                DEFAULT_TRANSFORMER);

        get("/world/cities/all",
                (req, resp) -> WORLD_SERVICE.fetchAllCities(),
                DEFAULT_TRANSFORMER);

        get("/world/cities/:cityId",
                (req, resp) -> WORLD_SERVICE.fetchCity(Long.valueOf(req.params(":cityId"))),
                DEFAULT_TRANSFORMER);
    }
}
