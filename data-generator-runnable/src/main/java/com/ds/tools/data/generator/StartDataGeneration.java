package com.ds.tools.data.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.ds.tools.data.generator.core.Constants;
import com.ds.tools.data.generator.core.DataGenerationStarter;
import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.core.Identifiers;
import com.ds.tools.data.generator.core.Properties;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;

/**
 * Main class which starts the execution of data-generator tool. For more
 * information on tool usage see the
 * <a href= "https://github.com/Sarvesh-D/data-generator">README</a>.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 07-02-2017
 * @version 1.0
 */
@Slf4j
public class StartDataGeneration {

    private static final String README = "https://github.com/Sarvesh-D/data-generator";

    private StartDataGeneration() {
        // suppressing default constructor
    }

    /**
     * Starts the execution of data generation and export. Apart from
     * data-generation and data-export queries one can pass flags such as -X (for
     * debug) and --help (for help).
     *
     * @param args
     *            for data generation and/or export
     */
    public static void main(final String... args) {
        if (ArrayUtils.isEmpty(args)) {
            log.info("No args supplied. Use --help to see tool usage");
            System.exit(0);
        }

        if (ArrayUtils.contains(args, Constants.DISPLAY_HELP)) {
            displayHelp();
            return;
        }

        try {
            DataGenerationStarter.start(StringUtils.join(args, Constants.SPACE));
        } catch (final Exception e) {
            log.error("something went wrong... {}. Visit {} for more info. Data-Generator shall now exit", e.getMessage(), README);
            System.exit(0);
        }
    }

    /**
     * Prints the help to standard output.
     */
    private static void displayHelp() {
        final ArgumentParser parser = ArgumentParsers.newArgumentParser("data-generator")
                                                     .description("generate some random data and export to CSV file.")
                                                     .usage(getUsage());

        parser.addArgument("debug")
              .metavar(Constants.DEBUG_ENABLED)
              .help("enable debug mode.");

        Arrays.stream(DataType.values())
              .forEach(dataType -> parser.addArgument(dataType.toString())
                                         .metavar(Identifiers.TYPE.getIdentifier() + "" + dataType.getIdentifier())
                                         .help(dataType.getHelp()));

        Arrays.stream(Properties.values())
              .forEach(prop -> parser.addArgument(prop.toString())
                                     .metavar(Identifiers.PROPERTY.getIdentifier() + "" + prop.getIdentifier())
                                     .help(prop.getHelp()));

        Arrays.stream(Identifiers.values())
              .forEach(identifier -> parser.addArgument(identifier.toString())
                                           .metavar(identifier.getIdentifier()
                                                              .toString())
                                           .help(identifier.getHelp()));

        parser.printHelp();
    }

    /**
     * Builds tool usage String from usage.txt file
     *
     * @return tool usage string.
     */
    private static String getUsage() {
        final StringBuilder usage = new StringBuilder("\n");
        final String usageFilePath = "/META-INF/usage.txt";
        try (InputStreamReader input = new InputStreamReader(StartDataGeneration.class.getResourceAsStream(usageFilePath)); BufferedReader reader = new BufferedReader(input)) {
            usage.append(reader.lines()
                               .collect(Collectors.joining("\n")));
            usage.append(String.format("%n%nVisit %s for more info", README));
        } catch (final IOException e) {
            log.error("something went wrong while fetching tool usage : visit {} for more info.", README);
        }
        return usage.toString();
    }

}
