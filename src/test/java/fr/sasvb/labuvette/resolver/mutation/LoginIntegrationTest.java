package fr.sasvb.labuvette.resolver.mutation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginIntegrationTest {
    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    public void it_should_login() throws IOException {
        // Arrange
        ObjectNode variables = new ObjectMapper().createObjectNode();
        variables.put("username", "ADMIN");
        variables.put("password", "ADMIN");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        graphQLTestTemplate.setHeaders(headers);

        // Act
        GraphQLResponse response = graphQLTestTemplate.perform("graphql/login.graphql", variables);

        // Assert
        Assert.assertTrue(response.isOk());
        Assert.assertEquals(response.get("$.data.login.username"), "ADMIN");
        Assert.assertNotNull(response.get("$.data.login.token"));
    }
}