package io.github.kahar.api.C4;

import static io.github.kahar.C4Common.*;

public class ApiGatewayC2 {
    public static void init() {

        receptionist.uses(apiGateway, "Use");

        apiGateway.uses(visitService,
                "CRUD visits",
                "HTTP");
        apiGateway.uses(vetService,
                "CRUD vets",
                "HTTP");
        apiGateway.uses(customerService,
                "CRUD pets, pet owners",
                "HTTP");
    }
}
