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

/**
 * Main class which starts the execution of data-generator tool.
 * For more information on tool usage see the confluence link.
 * @see TODO add confluence page link
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 07-02-2017
 * @version 1.0
 */
@Slf4j
public class StartDataGeneration {

    private static ConsoleAppender console = new ConsoleAppender();
    
    private static final String WIKI_LINK = "https://confluence.cdk.com/display/EA/Data-Generator+Tool+Wiki";

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

    /**
     * Starts the execution of data generation and export.
     * Apart from data-generation and data-export queries one can pass flags such as
     * -X (for debug) and --help (for help).
     * For complete tool usage see visit TODO add link
     * @param args
     */
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

	try {
	    String finalCMDQuery;
	    long start = System.nanoTime();

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

	    long end = System.nanoTime();
	    double timeTaken = (end - start) / 1000000000.0;

	    log.info("Time Taken to generate Data : {} seconds", timeTaken);
	} catch (Exception e) {
	    log.error("something went wrong... {}. Visit {} for more info. Data-Generator shall now exit", e.getMessage(), WIKI_LINK);
	    System.exit(0);
	}
    }

    /**
     * Builds tool usage String from usage.txt file
     * @return tool usage string.
     */
    private static String getUsage() {
	StringBuilder usage = new StringBuilder("\n");
	final String usageFilePath = "/META-INF/usage.txt";
	try (InputStreamReader input = new InputStreamReader(
		StartDataGeneration.class.getResourceAsStream(usageFilePath));
		BufferedReader reader = new BufferedReader(input)) {
	    usage.append(reader.lines().collect(Collectors.joining("\n")));
	    usage.append(String.format("%n%nVisit %s for more info", WIKI_LINK));
	} catch (IOException e) {
	    log.error("something went wrong while fetching tool usage : visit {} for more info.", WIKI_LINK);
	}
	return usage.toString();
    }

    /**
     * Prints the help to standard output.
     */
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
