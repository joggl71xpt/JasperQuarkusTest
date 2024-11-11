package de.test.quarkus;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import net.sf.jasperreports.engine.JRException;

@Path("/")
@RequestScoped
public class TriggerEndpoint {

    @Inject
    JasperManager jasperManager;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/simple")
    public String simpleJasperTemplate() {
        String result = "";
        try {
            result = jasperManager.fillSimpleReport();
            return result;
        } catch (JRException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/dataset")
    public String dataJasperTemplate() {
        String result = "";
        try {
            result = jasperManager.fillDatasetReport();
            return result;
        } catch (JRException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/complex")
    public String complexJasperTemplate() {
        String result = "";
        try {
            result = jasperManager.fillComplexReport();
            return result;
        } catch (JRException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
