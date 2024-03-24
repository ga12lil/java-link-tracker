package edu.java;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;
import org.testcontainers.containers.PostgreSQLContainer;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jooq {
    private static final PostgreSQLContainer<?> PSQL_CONTAINER;
    private static final String CHANGELOG_FILE = "master.xml";
    private static final Path CHANGELOG_PATH = new File("migrations").toPath();

    static {
        PSQL_CONTAINER = new PostgreSQLContainer<>("postgres:15");
        PSQL_CONTAINER.start();
        runMigrations();
    }

    private static void runMigrations() {
        try (Connection connection = DriverManager.getConnection(
                PSQL_CONTAINER.getJdbcUrl(),
                PSQL_CONTAINER.getUsername(),
                PSQL_CONTAINER.getPassword()
        )) {
            liquibase.database.Database db = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            ResourceAccessor resourceAccessor = new DirectoryResourceAccessor(CHANGELOG_PATH);
            Liquibase liquibase = new Liquibase(CHANGELOG_FILE, resourceAccessor, db);
            liquibase.update();
        } catch (SQLException | FileNotFoundException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration()
                .withJdbc(new Jdbc()
                        .withDriver(PSQL_CONTAINER.getDriverClassName())
                        .withUrl(PSQL_CONTAINER.getJdbcUrl())
                        .withUser(PSQL_CONTAINER.getUsername())
                        .withPassword(PSQL_CONTAINER.getPassword()))
                .withGenerator(new Generator()
                        .withGenerate(new Generate()
                                .withGeneratedAnnotation(true)
                                .withGeneratedAnnotationDate(false)
                                .withNullableAnnotation(true)
                                .withNullableAnnotationType("org.jetbrains.annotations.Nullable")
                                .withNonnullAnnotation(true)
                                .withNonnullAnnotationType("org.jetbrains.annotations.NotNull")
                                .withJpaAnnotations(false)
                                .withValidationAnnotations(true)
                                .withSpringAnnotations(true)
                                .withConstructorPropertiesAnnotation(true)
                                .withConstructorPropertiesAnnotationOnPojos(true)
                                .withConstructorPropertiesAnnotationOnRecords(true)
                                .withFluentSetters(false)
                                .withDaos(true)
                                .withPojos(true)
                                .withJavaTimeTypes(true))
                        .withDatabase(new Database()
                                .withName("org.jooq.meta.postgres.PostgresDatabase")
                                .withInputSchema("public"))
                        .withTarget(new Target()
                                .withPackageName("edu.java.scrapper.domain.jooq")
                                .withDirectory("scrapper/src/main/java")));

        GenerationTool.generate(configuration);
    }
}