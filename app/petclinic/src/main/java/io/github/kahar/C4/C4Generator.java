package io.github.kahar.C4;


import com.structurizr.io.plantuml.PlantUMLWriter;
import com.structurizr.io.plantuml.StructurizrPlantUMLWriter;
import com.structurizr.model.Enterprise;
import com.structurizr.view.ContainerView;
import com.structurizr.view.SystemContextView;
import io.github.kahar.api.C4.ApiGatewayC2;
import io.github.kahar.api.C4.ApiGatewayC3;

import java.io.StringWriter;

import static io.github.kahar.C4Common.*;

public class C4Generator {

    private static void init() {
        model.setEnterprise(new Enterprise("Petclinic project"));
        client.interactsWith(receptionist, "Call, email, direct visit");
        receptionist.uses(petClinicSystem, "Use");
        petClinicSystem.uses(microservicesSystem, "Get microservice data");
        microservicesSystem.uses(petClinicSystem, "Monitor system state");
        petClinicSystem.uses(microservicesExternalSystem, "Get microservice data");
        microservicesExternalSystem.uses(petClinicSystem, "Monitor system state");
    }

    private static void C1() {
        SystemContextView systemContextView = views.createSystemContextView(
                petClinicSystem,
                "SystemContext",
                "Context diagram of the veterinary system");
        systemContextView.addNearestNeighbours(petClinicSystem);
        systemContextView.addNearestNeighbours(microservicesSystem);
        systemContextView.addAnimation(petClinicSystem);
        systemContextView.addNearestNeighbours(client);
        systemContextView.addAnimation(client);
    }

    private static void C2_petClinicSystem() {
        ApiGatewayC2.init();
        ContainerView containerPetclinicView = views.createContainerView(
                petClinicSystem,
                "petclinic Containers",
                "Diagram of containers of the veterinary system");
        containerPetclinicView.add(client);
        containerPetclinicView.add(receptionist);
        client.uses(petClinicSystem, "Use");
        containerPetclinicView.addAllContainers();
        containerPetclinicView.add(microservicesExternalSystem);
        containerPetclinicView.add(microservicesSystem);
        containerPetclinicView.addAnimation(microservicesSystem, microservicesExternalSystem, petClinicSystem);

    }

    private static void C2_microservicesInternalSystem() {
        adminServer.uses(petClinicSystem, "download and display state");
        petClinicSystem.uses(configServer, "download configuration");
        petClinicSystem.uses(discoveryServer, "discover other services");
        petClinicSystem.uses(discoveryServer, "register services");
        petClinicSystem.uses(prometheusServer, "send logs");
        grafanaServer.uses(prometheusServer, "get logs");

        ContainerView microservicesInfrastructureSystem = views.createContainerView(
                microservicesSystem,
                "internal microservices Containers",
                "Diagram of microservices / infrastructure system");
        microservicesInfrastructureSystem.addAllContainers();
        microservicesInfrastructureSystem.add(petClinicSystem);
    }

    private static void C3_ApiGateway() {
        ApiGatewayC3.init();
    }

    public static void main(String[] args) {
        init();
        C1();
        C2_petClinicSystem();
        C2_microservicesInternalSystem();
        C3_ApiGateway();


        StringWriter stringWriter = new StringWriter();
        PlantUMLWriter plantUMLWriter = new StructurizrPlantUMLWriter();
        plantUMLWriter.write(workspace, stringWriter);
        System.out.println("----------------------------");
        System.out.println(stringWriter);
        System.out.println("----------------------------");
    }

}