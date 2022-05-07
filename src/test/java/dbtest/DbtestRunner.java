package dbtest;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DbtestRunner {

    private Logger logger = LoggerFactory.getLogger(DbtestRunner.class);

    @BeforeEach
    void beforeEach() {
        System.out.println("  JUnit5Test#beforeEach()");
        logger.info("Hello Logback!!  JUnit5Test#beforeEach()");
    }

    @Karate.Test
    Karate run() { return Karate.run().relativeTo(getClass()); }

    @AfterEach
    void afterEach() {
        System.out.println("  JUnit5Test#afterEach()");
        logger.info("Hello Logback!!  JUnit5Test#afterEach()");
    }
}
