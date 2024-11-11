# JasperQuarkusTest

This project demonstrate the usage of jasperreports with quarkus with a MULTISTAGE Docker build!

# Known issues
* build only works with jdk <=21 ! if you try to build it (see Dockerfile.multistage) with a newer version of java it 
will fail because of not supported "Java Foreign Function and Memory API"

* Generating the 'complex' example fails with a ClassNotFoundException when using
 io.quarkiverse.jasperreports-quarkus-jasperreports v1.0.2

This project contains no error/exception handling, it is just for demo purpose.



## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

## Problem with resource bundles in native run
to be able that the classloader find the translation files, we copy the translation *.properties files during build process
from /src/main/jasperresources to target folder via maven resource plugin

## BUILD
```shell script
docker build -f src/main/docker/Dockerfile.multistage -t example/jaspertest:1.0 .
```
## RUN
destpath - replace it with any valid path on your machine... the generated PDF will be placed here

```shell script
docker run -i --rm -p 8080:8080 -v <destpath>:/tmp example/jaspertest:1.0
```
## TEST IT
Open following urls in browser:

1. Simple report (SimpleTestReport.jrxml)
   this tests a simple report without any datasets
http://localhost:8080/simple

2. Report with dataset (TableDatasetReport.jrxml)
   interestingly this works in native mode (contains a subdata set like in #3)
http://localhost:8080/dataset

3. Complex Report (FixedPriceGoAEInvoice.jrxml)
   this will throw an class not found error in native mode (in DEV it works)
http://localhost:8080/complex

Request to /complex failed, error id: 34ca466c-23d6-4e21-a34e-dad3fef6054d-1: java.lang.RuntimeException: java.lang.ClassNotFoundException: FixedPriceGoAEInvoice_InvoicePositionsDataset_4ffb55f9b3fdec22f0d1299da07c2208f610dc0af680922cf49a5a2163f2ed4f
at net.sf.jasperreports.engine.util.JRClassLoader.loadClass(JRClassLoader.java:29)
at net.sf.jasperreports.engine.util.CompiledClassesLoader.loadCompiledClass(CompiledClassesLoader.java:87)
at net.sf.jasperreports.engine.util.JRClassLoader.loadClassFromBytes(JRClassLoader.java:286)
at net.sf.jasperreports.engine.design.JRAbstractJavaCompiler.loadClass(JRAbstractJavaCompiler.java:168)
at net.sf.jasperreports.engine.design.JRAbstractJavaCompiler.loadEvaluator(JRAbstractJavaCompiler.java:117)
at net.sf.jasperreports.engine.design.JRAbstractCompiler.createEvaluator(JRAbstractCompiler.java:459)
at net.sf.jasperreports.engine.design.JRAbstractCompiler.loadEvaluator(JRAbstractCompiler.java:427)
at net.sf.jasperreports.engine.JasperCompileManager.getEvaluator(JasperCompileManager.java:374)
at net.sf.jasperreports.engine.fill.JRFillDataset.createCalculator(JRFillDataset.java:526)
at net.sf.jasperreports.engine.fill.JRFillDataset.createCalculator(JRFillDataset.java:516)
at net.sf.jasperreports.engine.fill.BaseReportFiller.createDatasets(BaseReportFiller.java:227)
at net.sf.jasperreports.engine.fill.BaseReportFiller.<init>(BaseReportFiller.java:181)
at net.sf.jasperreports.engine.fill.JRBaseFiller.<init>(JRBaseFiller.java:273)
at net.sf.jasperreports.engine.fill.JRVerticalFiller.<init>(JRVerticalFiller.java:82)
at net.sf.jasperreports.engine.fill.JRFiller.createBandReportFiller(JRFiller.java:252)
at net.sf.jasperreports.engine.fill.JRFiller.createReportFiller(JRFiller.java:272)
at net.sf.jasperreports.engine.fill.JRFiller.fill(JRFiller.java:157)
at net.sf.jasperreports.engine.JasperFillManager.fillFromRepo(JasperFillManager.java:671)
at de.test.quarkus.JasperManager.fillComplexReport(JasperManager.java:79)
at de.test.quarkus.JasperManager_ClientProxy.fillComplexReport(Unknown Source)
at de.test.quarkus.TriggerEndpoint.complexJasperTemplate(TriggerEndpoint.java:52)
at de.test.quarkus.TriggerEndpoint_ClientProxy.complexJasperTemplate(Unknown Source)
at de.test.quarkus.TriggerEndpoint$quarkusrestinvoker$complexJasperTemplate_bd0a52edf50a46b8e68d2f2a817a11fca260a0f9.invoke(Unknown Source)
at org.jboss.resteasy.reactive.server.handlers.InvocationHandler.handle(InvocationHandler.java:29)
at io.quarkus.resteasy.reactive.server.runtime.QuarkusResteasyReactiveRequestContext.invokeHandler(QuarkusResteasyReactiveRequestContext.java:141)
at org.jboss.resteasy.reactive.common.core.AbstractResteasyReactiveContext.run(AbstractResteasyReactiveContext.java:147)
at io.quarkus.vertx.core.runtime.VertxCoreRecorder$14.runWith(VertxCoreRecorder.java:627)
at org.jboss.threads.EnhancedQueueExecutor$Task.doRunWith(EnhancedQueueExecutor.java:2675)
at org.jboss.threads.EnhancedQueueExecutor$Task.run(EnhancedQueueExecutor.java:2654)
at org.jboss.threads.EnhancedQueueExecutor.runThreadBody(EnhancedQueueExecutor.java:1627)
at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1594)
at org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:11)
at org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:11)
at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
at java.base@21.0.5/java.lang.Thread.runWith(Thread.java:1596)
at java.base@21.0.5/java.lang.Thread.run(Thread.java:1583)
at org.graalvm.nativeimage.builder/com.oracle.svm.core.thread.PlatformThreads.threadStartRoutine(PlatformThreads.java:896)
at org.graalvm.nativeimage.builder/com.oracle.svm.core.thread.PlatformThreads.threadStartRoutine(PlatformThreads.java:872)
Caused by: java.lang.ClassNotFoundException: FixedPriceGoAEInvoice_InvoicePositionsDataset_4ffb55f9b3fdec22f0d1299da07c2208f610dc0af680922cf49a5a2163f2ed4f
at org.graalvm.nativeimage.builder/com.oracle.svm.core.hub.ClassForNameSupport.forName(ClassForNameSupport.java:122)
at org.graalvm.nativeimage.builder/com.oracle.svm.core.hub.ClassForNameSupport.forName(ClassForNameSupport.java:86)
at java.base@21.0.5/java.lang.Class.forName(DynamicHub.java:1359)
at java.base@21.0.5/java.lang.Class.forName(DynamicHub.java:1348)
at net.sf.jasperreports.engine.util.JRClassLoader.loadClass(JRClassLoader.java:27)
... 37 more
