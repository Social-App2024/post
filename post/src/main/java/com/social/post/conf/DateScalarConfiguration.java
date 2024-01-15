package com.social.post.conf;

import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Configuration
public class DateScalarConfiguration {
    @Bean
    public GraphQLScalarType dateScalar() {

        return GraphQLScalarType.newScalar()
                .name("Date")
                .description("A custom scalar that handles date values")
                .coercing(new Coercing() {

                    @Override
                    public Object serialize(Object o) throws CoercingSerializeException {
                        return DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm").format((LocalDate) o);
                    }

                    @Override
                    public Object parseValue(Object o) throws CoercingParseValueException {
                        return LocalDate.parse(o.toString());
                    }

                    @Override
                    public LocalDate parseLiteral(Object o) throws CoercingParseLiteralException {
                        if (o instanceof StringValue) {
                            var value = ((StringValue) o).getValue();
                            try {
                                return LocalDate.parse(value);
                            } catch (DateTimeParseException e) {
                                throw new CoercingParseLiteralException("Cannot parse [" + value + "] to LocalDate", e);
                            }
                        } else {
                            throw new CoercingParseLiteralException(
                                    "Expected literal of type StringValue but was " + o.getClass());
                        }
                    }
//                    @Override
//                    public Object parseLiteral(Object o) throws CoercingParseLiteralException {
//                        if (o == null) {
//                            return null;
//                        }
//                        return parseValue(o.toString());
//                    }

                })
                .build();

    }
}
