package com.cdk.ea.tools.data.generation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.cdk.ea.tools.data.generation.core.Constants;
import com.cdk.ea.tools.data.generation.core.DataType;
import com.cdk.ea.tools.data.generation.core.Identifiers;
import com.cdk.ea.tools.data.generation.core.Properties;
import com.cdk.ea.tools.data.generation.generators.DataGenerator;
import com.cdk.ea.tools.data.generation.query.json.JsonQueryBuilder;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;

@Slf4j
public class StartDataGeneration {

    private static ConsoleAppender console = new ConsoleAppender();

    static {
	String pattern = "%-5p: %c - %m%n";
	console.setLayout(new PatternLayout(pattern));
	console.setThreshold(Level.INFO);
	console.activateOptions();
	Logger.getRootLogger().addAppender(console);
    }

    private StartDataGeneration() {
	// suppressing default constructor
    }

    public static void main(String... args) {
	if (ArrayUtils.isEmpty(args)) {
	    log.info("No args supplied. Use --help to see tool usage");
	    System.exit(0);
	}

	if (ArrayUtils.contains(args, Constants.DISPLAY_HELP)) {
	    displayHelp();
	    return;
	}

	if (ArrayUtils.contains(args, Constants.DEBUG_ENABLED))
	    console.setThreshold(Level.DEBUG);

	String finalCMDQuery;

	long start = System.nanoTime();

	try {
	    if (Constants.JSON.equals(args[0])) {
		log.debug("JSON format selected to generate data");
		String[] jsonFiles = Arrays.stream(args).filter(arg -> arg.endsWith(Constants.JSON_EXTENSTION))
			.toArray(size -> new String[size]);
		log.debug("JSON files to generate data : {}", Arrays.toString(jsonFiles));
		finalCMDQuery = new JsonQueryBuilder().build(jsonFiles);
	    } else
		finalCMDQuery = StringUtils.join(args, Constants.SPACE);

	    log.debug("Final query to generate data => {}", finalCMDQuery);
	    DataGenerator.from(finalCMDQuery).generate();
	} catch (Exception e) {
	    log.error("something went wrong... {}", e.getMessage());
	}

	long end = System.nanoTime();
	double timeTaken = (end - start) / 1000000000.0;

	log.info("Time Taken to generate Data : {} seconds", timeTaken);
    }

    private static String getUsage() {
	StringBuilder usage = new StringBuilder("\n");
	final String usageFilePath = "/META-INF/usage.txt";
	try (InputStreamReader input = new InputStreamReader(
		StartDataGeneration.class.getResourceAsStream(usageFilePath));
		BufferedReader reader = new BufferedReader(input)) {
	    usage.append(reader.lines().collect(Collectors.joining("\n")));
	} catch (IOException e) {
	    log.error(e.getMessage());
	}
	return usage.toString();
    }

    private static void displayHelp() {
	ArgumentParser parser = ArgumentParsers.newArgumentParser("data-generator")
		.description("generate some random data and export to CSV file.").usage(getUsage());

	parser.addArgument("debug").metavar(Constants.DEBUG_ENABLED).help("enable debug mode.");

	Arrays.stream(DataType.values()).forEach(dataType -> parser.addArgument(dataType.toString())
		.metavar(Identifiers.TYPE.getIdentifier() + "" + dataType.getIdentifier()).help(dataType.getHelp()));

	Arrays.stream(Properties.values()).forEach(prop -> parser.addArgument(prop.toString())
		.metavar(Identifiers.PROPERTY.getIdentifier() + "" + prop.getIdentifier()).help(prop.getHelp()));

	Arrays.stream(Identifiers.values()).forEach(identifier -> parser.addArgument(identifier.toString())
		.metavar(identifier.getIdentifier().toString()).help(identifier.getHelp()));

	parser.printHelp();
    }

}
