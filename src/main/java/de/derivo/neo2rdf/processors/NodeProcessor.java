package de.derivo.neo2rdf.processors;


import org.neo4j.driver.Value;

public interface NodeProcessor {

    void process(String nodeID, String assignedLabel);

    void process(String nodeID, String propertyKey, Value value);

    void startProcessing();
}
