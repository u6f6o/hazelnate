package com.u6f6o.apps.hazelnate.application.rest;

import com.u6f6o.apps.hazelnate.application.rest.model.InhabitantModel;
import com.u6f6o.apps.hazelnate.application.rest.transformer.JsonTransformer;
import com.u6f6o.apps.hazelnate.application.service.WorldService;

import static spark.Spark.get;
import static spark.Spark.post;

public class WorldsEnd {
    private static final JsonTransformer JSON_TRANSFORMER = new JsonTransformer();
    private static final WorldService WORLD_SERVICE = new WorldService();

    public static void main(String[] args) {
        get("/world",
                (req, resp) -> WORLD_SERVICE.fetchWorld(),
                JSON_TRANSFORMER);

        get("/world/countries/all",
                (req, resp) -> WORLD_SERVICE.fetchAllCountries(),
                JSON_TRANSFORMER);

        get("/world/countries/:countryId",
                (req, resp) -> WORLD_SERVICE.fetchCountry(Long.valueOf(req.params(":countryId"))),
                JSON_TRANSFORMER);

        get("/world/cities/all",
                (req, resp) -> WORLD_SERVICE.fetchAllCities(),
                JSON_TRANSFORMER);

        get("/world/cities/:cityId",
                (req, resp) -> WORLD_SERVICE.fetchCity(Long.valueOf(req.params(":cityId"))),
                JSON_TRANSFORMER);

        get("/world/inhabitants/:inhabitantId",
                (req, resp) -> WORLD_SERVICE.fetchInhabitant(Long.valueOf(req.params(":inhabitantId"))),
                JSON_TRANSFORMER);

        post("/world/inhabitants", "application/json",
                (req, resp) -> {
                    try {
                        InhabitantModel inhabitantModel = JSON_TRANSFORMER.parse(req.body(), InhabitantModel.class);
                        return WORLD_SERVICE.createInhabitant(inhabitantModel);
                    } catch (Exception e) {
                        // TODO find right response code
                        resp.status(404);
                    }
                    return null;
                },
                JSON_TRANSFORMER);
        }
}
