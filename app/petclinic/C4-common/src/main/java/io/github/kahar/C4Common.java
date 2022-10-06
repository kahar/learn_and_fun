package io.github.kahar;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.view.ViewSet;

public class C4Common {
    public static Workspace workspace = new Workspace("Petclinic", "Internal model");
    public static Model model = workspace.getModel();
    public static ViewSet views = workspace.getViews();

    public static Person client = model.addPerson(
            Location.External,
            "Pet owner",
            "Pet owner, can have zero, one or more animals");

    public static Person receptionist = model.addPerson(
            Location.External,
            "Receptionist",
            "Handle Pet owner communications");

    // C1
    public static SoftwareSystem petClinicSystem = model.addSoftwareSystem(
            Location.Internal,
            "Pet clinic system",
            "It allows you to arrange visits to the vet");

    public static SoftwareSystem microservicesSystem = model.addSoftwareSystem(
            Location.Internal,
            "Microservices / infrastructure system",
            "It allows to manage microservices / aggregate logs etc.");

    public static SoftwareSystem microservicesExternalSystem = model.addSoftwareSystem(
            Location.External,
            "External microservices / infrastructure system",
            "It allows to manage microservices / aggregate logs etc.");

    // C2 / C3

    public static Container adminServer = microservicesSystem.addContainer(
            "Admin server",
            "Beautiful dashboard for admin.",
            "Java, Spring, Spring cloud");
    public static Container configServer = microservicesSystem.addContainer(
            "Config server",
            "Serves configuration. Should interact with every microservice.",
            "Java, Spring, Spring cloud");
    public static Container discoveryServer = microservicesSystem.addContainer(
            "Discovery server",
            "It allows you to register and discover microservices. Should interact with every microservice.",
            "Java, Spring, Spring cloud");
    public static Container grafanaServer = microservicesSystem.addContainer(
            "Grafana server",
            "Display logs. Should interact with every microservice.");
    public static Container prometheusServer = microservicesSystem.addContainer(
            "prometheus server",
            "Aggregate logs. Should interact with every microservice.");

    public static Container apiGateway = petClinicSystem.addContainer(
            "Api gateway",
            "Handles http request and serves as api gateway",
            "Java, Spring, Spring cloud, Angular");
    public static Container visitService = petClinicSystem.addContainer(
            "Visit service",
            "It allows you to make an visit",
            "Java, H2, Spring, Spring cloud");
    public static Container vetService = petClinicSystem.addContainer(
            "Vet service",
            "Stores and shares data about vets",
            "Java, H2, Spring, Spring cloud");
    public static Container customerService = petClinicSystem.addContainer(
            "Customer service",
            "Stores and shares data about pets and pet owners",
            "Java, H2, Spring, Spring cloud");
}
