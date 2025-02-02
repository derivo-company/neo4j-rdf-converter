package de.derivo.neo2rdf.conversion.cli;

import picocli.CommandLine;

@CommandLine.Command(
        name = "neo2rdf",
        header = "Converts a Neo4j database to RDF Turtle",
        description = """
                Neo2RDF is a command line application that converts a Neo4j database into an RDF file in Turtle format. It is
                implemented in Java and uses the Cypher query language via the official Neo4j Java driver.
                """,
        subcommands = {Neo4jToTurtleDumpCommand.class, Neo4jToRDFConversionServerCommand.class, CommandLine.HelpCommand.class},
        mixinStandardHelpOptions = true,
        showDefaultValues = true,
        versionProvider = Neo2RDFVersionProvider.class,
        usageHelpWidth = 95)
public class Neo4jToRDFConversionCLI {

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new Neo4jToRDFConversionCLI());
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }

}
