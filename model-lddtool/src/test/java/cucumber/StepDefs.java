package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class connects the feature files with the Cucumber test code
 */
public class StepDefs {
    // The values of these variables should come from a row in the table in the
    // feature file.
    private String testName;
    private String[] commandArgs;
    private String runCommandOutput;

    @Given("a test {string} with {string} as arguments")
    public void i_have_lddtool_installed(String testName, String commandArgs) {
        this.testName = testName;
        this.commandArgs = commandArgs.split(" ");  // split the commandArgs into a String array

        for (String arg : this.commandArgs) {
            System.out.println("arg: " + arg);
        }
    }

    @When("I execute lddtool")
    public void i_run_lddtool_with() {
        try {
            // run lddtool with the commandArgs and capture the output
            this.runCommandOutput = LddToolRunner.runLddTool(this.commandArgs);
            // this.runCommandOutput = runLddTool(this.commandArgs); // mock response
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new RuntimeException("DMDocument error", ex);
        }
    }

    @Then("the produced output from lddtool command should be similar to {string} or no error reported.")
    public void i_should_receive(String expectedResponse) {
        // compare the actual output of running lddtool with the expected response
        assertTrue(this.runCommandOutput.contains(expectedResponse),
                "Expected response: " + expectedResponse + " Actual response: " + this.runCommandOutput);
    }

    // simulate running lddtool with parameters and returning a mocked response
    private String runLddTool(String[] parameters) {
        // Mocked response based on parameters. 
        // Only testing single arugment commands
        switch (parameters[0]) {
            case "--help":
                return "usage: LDDTool";
            case "--version":
                return "LDDTool Version: 14.4.0-SNAPSHOT";
            case "invalid":
                return "Shows error message";
            default:
                return "Argument not recognized";
        }
    }
}
