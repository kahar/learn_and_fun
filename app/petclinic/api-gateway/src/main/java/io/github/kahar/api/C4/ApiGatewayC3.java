package io.github.kahar.api.C4;

import com.structurizr.model.Component;
import com.structurizr.view.ComponentView;

import static io.github.kahar.C4Common.*;

public class ApiGatewayC3 {
    public static void init() {
        Component apiGatewayCore = apiGateway.addComponent("Core", "Core logic");
        apiGatewayCore.uses(configServer, "Download config");
        apiGatewayCore.uses(discoveryServer, "Discover / register microservices");
        apiGatewayCore.uses(visitService,
                "CRUD visits",
                "HTTP");
        apiGatewayCore.uses(vetService,
                "CRUD vets",
                "HTTP");
        apiGatewayCore.uses(customerService,
                "CRUD pets, pet owners",
                "HTTP");

        prometheusServer.uses(apiGatewayCore,
                "get logs");
        adminServer.uses(apiGatewayCore,
                "display state");

        ComponentView componentView = views.createComponentView(
                apiGateway,
                "Components",
                "Component diagram for the api gateway");
        componentView.addAllComponents();
        componentView.add(configServer);
        componentView.add(discoveryServer);
        componentView.add(visitService);
        componentView.add(vetService);
        componentView.add(customerService);
        componentView.add(prometheusServer);
        componentView.add(adminServer);
    }
}
