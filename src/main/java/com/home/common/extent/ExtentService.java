package com.home.common.extent;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ConfigurableReporter;
import com.aventstack.extentreports.reporter.ExtentBDDReporter;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentService implements Serializable {

    private static final long serialVersionUID = -5008231199972325650L;

    public static synchronized ExtentReports getInstance() {
        return ExtentReportsLoader.INSTANCE;
    }

    @SuppressWarnings("unused")
    private ExtentReports readResolve() {
        return ExtentReportsLoader.INSTANCE;
    }

    private static class ExtentReportsLoader {

        private static final ExtentReports INSTANCE = new ExtentReports();
        private static final String[] DEFAULT_SETUP_PATH = new String[] { "extent.properties",
                "com/aventstack/adapter/extent.properties" };
        private static final String OUTPUT_PATH = "test-output/";
        private static final String EXTENT_REPORTER = "extent.reporter";
        private static final String START = "start";
        private static final String CONFIG = "config";
        private static final String OUT = "out";
        private static final String DELIM = ".";

        private static final String BDD = "bdd";
        private static final String HTML = "html";

        private static final String INIT_BDD_KEY = EXTENT_REPORTER + DELIM + BDD + DELIM + START;
        private static final String INIT_HTML_KEY = EXTENT_REPORTER + DELIM + HTML + DELIM + START;

        private static final String CONFIG_BDD_KEY = EXTENT_REPORTER + DELIM + BDD + DELIM + CONFIG;
        private static final String CONFIG_HTML_KEY = EXTENT_REPORTER + DELIM + HTML + DELIM + CONFIG;

        private static final String OUT_BDD_KEY = EXTENT_REPORTER + DELIM + BDD + DELIM + OUT;
        private static final String OUT_HTML_KEY = EXTENT_REPORTER + DELIM + HTML + DELIM + OUT;

        static {
            if (INSTANCE.getStartedReporters().isEmpty()) {
                createViaProperties();
                createViaSystem();
            }
        }

        private static void createViaProperties() {
            ClassLoader loader = ExtentReportsLoader.class.getClassLoader();
            Optional<InputStream> is = Arrays.stream(DEFAULT_SETUP_PATH).map(x -> loader.getResourceAsStream(x))
                    .filter(x -> x != null).findFirst();
            if (is.isPresent()) {
                Properties properties = new Properties();
                try {
                    properties.load(is.get());

                    if (properties.containsKey(INIT_BDD_KEY)
                            && "true".equals(String.valueOf(properties.get(INIT_BDD_KEY))))
                        initBdd(properties);

                    if (properties.containsKey(INIT_HTML_KEY)
                            && "true".equals(String.valueOf(properties.get(INIT_HTML_KEY))))
                        initHtml(properties);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private static void createViaSystem() {

        	if ("true".equals(System.getProperty(INIT_BDD_KEY)))
                initBdd(null);

            if ("true".equals(System.getProperty(INIT_HTML_KEY)))
                initHtml(null);

        }

        private static String getOutputPath(Properties properties, String key) {
            String out;
            if (properties != null && properties.get(key) != null)
                out = String.valueOf(properties.get(key));
            else
                out = System.getProperty(key);
            out = out == null || out.equals("null") || out.isEmpty() ? OUTPUT_PATH + key.split("\\.")[2] + "/" : out;
            return out;
        }

        private static void initBdd(Properties properties) {
            String out = getOutputPath(properties, OUT_BDD_KEY);
            ExtentBDDReporter bdd = new ExtentBDDReporter(out);
            attach(bdd, properties, CONFIG_BDD_KEY);
        }

        private static void initHtml(Properties properties) {
            String out = getOutputPath(properties, OUT_HTML_KEY);
            ExtentHtmlReporter html = new ExtentHtmlReporter(out);
            attach(html, properties, CONFIG_HTML_KEY);
        }

        private static void attach(ConfigurableReporter r, Properties properties, String configKey) {
            Object configPath = properties == null ? System.getProperty(configKey) : properties.get(configKey);
            if (configPath != null && !String.valueOf(configPath).isEmpty())
                r.loadXMLConfig(String.valueOf(configPath));
            INSTANCE.attachReporter(((ExtentReporter) r));
        }
    }

}
