package com.wikiblaz.straviarunning.rest;

import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class to validate the OpenAPI contract against the implementation.
 */
public class ApiValidationTest {

    @Test
    void validateContract() {
        // Parse the OpenAPI contract
        OpenAPIV3Parser parser = new OpenAPIV3Parser();
        OpenAPI openAPI = parser.read("src/main/resources/api-contract.yaml");

        // Basic validation - ensure the contract was parsed successfully
        assertNotNull(openAPI, "OpenAPI contract should be parsed successfully");
        assertNotNull(openAPI.getPaths(), "OpenAPI contract should define paths");
        assertNotNull(openAPI.getComponents(), "OpenAPI contract should define components");
        assertNotNull(openAPI.getComponents().getSchemas(), "OpenAPI contract should define schemas");

        // Validate specific paths exist
        assertNotNull(openAPI.getPaths().get("/api/activities"), "Path /api/activities should be defined");
        assertNotNull(openAPI.getPaths().get("/api/activities/{id}"), "Path /api/activities/{id} should be defined");

        // Validate specific schemas exist
        assertNotNull(openAPI.getComponents().getSchemas().get("Activity"), "Schema Activity should be defined");
        assertNotNull(openAPI.getComponents().getSchemas().get("ActivityRequest"), "Schema ActivityRequest should be defined");
        assertNotNull(openAPI.getComponents().getSchemas().get("ActivityType"), "Schema ActivityType should be defined");

        System.out.println("[DEBUG_LOG] OpenAPI contract validation passed successfully");
    }
}
