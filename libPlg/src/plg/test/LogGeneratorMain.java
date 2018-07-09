package plg.test;

import java.io.FileOutputStream;

import org.deckfour.xes.model.XLog;
import org.deckfour.xes.out.XMxmlSerializer;
import org.deckfour.xes.out.XSerializer;
import org.deckfour.xes.out.XesXmlSerializer;

import plg.generator.ProgressAdapter;
import plg.generator.log.LogGenerator;
import plg.generator.log.SimulationConfiguration;
import plg.io.importer.BPMNImporter;
import plg.model.Process;

public class LogGeneratorMain {

	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.err.println("Please use: java -jar LogGenerator.jar MODEL_FILE LOG_DESTINATION NO_TRACES");
			System.exit(-1);
		}
		
		String modelFile = args[0];
		String logDestination = args[1];
		Integer noTraces = Integer.parseInt(args[2]);
		
		System.out.println("Welcome!");
		
		System.out.println("Model: " + modelFile);
		System.out.println("Log destination: " + logDestination);
		System.out.println("No. of traces: " + noTraces);
		
		System.out.print("1. Importing model... ");
		BPMNImporter importer = new BPMNImporter();
		Process p = importer.importModel(modelFile, new ProgressAdapter());
		System.out.println("done!");
		
		System.out.print("2. Model checking... ");
		p.check();
		System.out.println("done!");
		
		System.out.print("3. Generating log... ");
		SimulationConfiguration sc = new SimulationConfiguration(noTraces);
		LogGenerator generator = new LogGenerator(p, sc, new ProgressAdapter());
		XLog log = generator.generateLog();
		System.out.println("done!");
		
		System.out.print("4. Exporting log... ");
		XSerializer serializer = new XMxmlSerializer();
		if (logDestination.substring(logDestination.length() - 3).equals("xes")) {
			serializer = new XesXmlSerializer();
		}
		serializer.serialize(log, new FileOutputStream(logDestination));
		System.out.println("done!");
	}

}
