package de.test.quarkus;

import io.quarkiverse.jasperreports.repository.ReadOnlyStreamingService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.json.data.JsonDataSource;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ApplicationScoped
public class JasperManager {

    @Inject
    private Logger log;

    @Inject
    ReadOnlyStreamingService jasperService;

    @ConfigProperty(name = "myjasperquarkus.test.outputdir", defaultValue = "/result")
    String destPath;

    final String jsonString="{'name':'Hugo Meier','positions': [{'deliveryTimestamp' : '2024-10-24T16:40:56Z','product':'product1'},{'deliveryTimestamp' : '2024-10-24T16:40:56Z','product':'product2'}]}";
//    final String destPath = System.getProperty("user.home") + File.separator +"result"+ File.separator;

    public String fillSimpleReport() throws JRException {
        Map<String, Object> params = new HashMap<>();
        params.put("net.sf.jasperreports.json.date.pattern", "yyyy-MM-dd");
        params.put("net.sf.jasperreports.json.number.pattern", "#,##0.##");
        params.put("JSON_LOCALE", Locale.US);
       // params.put(JRParameter.REPORT_CLASS_LOADER, tcl); // set our custom class loader
        params.put(JRParameter.REPORT_LOCALE, Locale.forLanguageTag("de")); // output locale -> controls, numbers, dates and language !!!
        String template="SimpleTestReport";

        InputStream fillParameterStream = IOUtils.toInputStream(jsonString, Charset.forName("UTF8")); // convert the json string to input stream
        JasperFillManager jfm = JasperFillManager.getInstance(jasperService.getContext());
        JsonDataSource ds = new JsonDataSource(fillParameterStream);
        JasperPrint jasperPrint = jfm.fillFromRepo(template + ".jasper", params, ds);
        JasperExportManager.exportReportToPdfFile(jasperPrint, destPath+File.separator+template+".pdf");
        return "PDF is generated and stored at "+destPath+File.separator+template+".pdf";
    }


    public String fillDatasetReport() throws JRException {
        Map<String, Object> params = new HashMap<>();
        params.put("net.sf.jasperreports.json.date.pattern", "yyyy-MM-dd");
        params.put("net.sf.jasperreports.json.number.pattern", "#,##0.##");
        params.put("JSON_LOCALE", Locale.US);
        // params.put(JRParameter.REPORT_CLASS_LOADER, tcl); // set our custom class loader
        params.put(JRParameter.REPORT_LOCALE, Locale.forLanguageTag("de")); // output locale -> controls, numbers, dates and language !!!
        String template="TableDatasetReport";
        InputStream fillParameterStream = IOUtils.toInputStream(jsonString, Charset.forName("UTF8")); // convert the json string to input stream
        JasperFillManager jfm = JasperFillManager.getInstance(jasperService.getContext());
        JsonDataSource ds = new JsonDataSource(fillParameterStream);
        JasperPrint jasperPrint = jfm.fillFromRepo(template + ".jasper", params, ds);
        JasperExportManager.exportReportToPdfFile(jasperPrint, destPath+ File.separator+template+".pdf");
        return "PDF is generated and stored at "+destPath+ File.separator+template+".pdf";
    }

    public String fillComplexReport() throws JRException {
        Map<String, Object> params = new HashMap<>();
        params.put("net.sf.jasperreports.json.date.pattern", "yyyy-MM-dd");
        params.put("net.sf.jasperreports.json.number.pattern", "#,##0.##");
        params.put("JSON_LOCALE", Locale.US);
        // params.put(JRParameter.REPORT_CLASS_LOADER, tcl); // set our custom class loader
        params.put(JRParameter.REPORT_LOCALE, Locale.forLanguageTag("de")); // output locale -> controls, numbers, dates and language !!!
        String template="FixedPriceGoAEInvoice";
        InputStream fillParameterStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("testdata.json");
        JasperFillManager jfm = JasperFillManager.getInstance(jasperService.getContext());
        JsonDataSource ds = new JsonDataSource(fillParameterStream);
        JasperPrint jasperPrint = jfm.fillFromRepo(template + ".jasper", params, ds);
        JasperExportManager.exportReportToPdfFile(jasperPrint, destPath+ File.separator+template+".pdf");
        return "PDF is generated and stored at "+destPath+ File.separator+template+".pdf";
    }



}
