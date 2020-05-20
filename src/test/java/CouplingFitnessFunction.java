import jdepend.framework.DependencyConstraint;
import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CouplingFitnessFunction {

    private JDepend jDepend = new JDepend();

    @Test
    public void allow_only_some_coupling() {
        DependencyConstraint constraint = new DependencyConstraint();

        JavaPackage persistence = constraint.addPackage("com.xyz.persistence");
        JavaPackage web = constraint.addPackage("com.xyz.web");
        JavaPackage util = constraint.addPackage("com.xyz.util");

        persistence.dependsUpon(util);
        web.dependsUpon(util);

        jDepend.analyze();

        assertEquals(true, jDepend.dependencyMatch(constraint), "Dependency mismatch");
    }
}
