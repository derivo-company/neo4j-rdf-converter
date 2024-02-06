package de.derivo.neo2rdf.console;

import de.derivo.neo2rdf.TestUtil;
import de.derivo.neo2rdf.conversion.cli.Neo4jToRDFConversionCLI;
import de.derivo.neo2rdf.conversion.config.ConversionConfig;
import de.derivo.neo2rdf.conversion.config.ConversionConfigBuilder;
import de.derivo.neo2rdf.util.SequenceConversionType;

import java.io.File;

public class TurtleConversionCLITest {

    private static final ConversionConfig config = ConversionConfigBuilder.newBuilder()
            .setSequenceConversionType(SequenceConversionType.SEPARATE_LITERALS)
            .setDerivePropertyHierarchyByRelationshipSubsetCheck(true)
            .setDeriveClassHierarchyByLabelSubsetCheck(true)
            .setSchemaOutputPath(TestUtil.getTempFile("test-schema.ttl"))
            .build();

    public static void main(String[] args) {
        File configPath = TestUtil.getTempFile("config.yaml");
        config.write(configPath);
        File outputPath = TestUtil.getTempFile("command-line-conversion-output.ttl");

        args = new String[]{"dump", "--neo4jDBDirectory=%s".formatted(TestUtil.getResource("neo4j-derived-type-hierarchy").toString()),
                "--config=%s".formatted(configPath.toString()),
                "--outputPath=%s".formatted(outputPath)};
        Neo4jToRDFConversionCLI.main(args);
    }
}
