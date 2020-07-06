package fr.sasvb.labuvette.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class UserNotFoundException extends RuntimeException implements GraphQLError {
    public UserNotFoundException(String s) {
        super(s);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }
}
