import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.*;

public class CodingRulesTest {

    private JavaClasses classes;

    @BeforeEach
    public void setup() {
        classes = new ClassFileImporter().importPackagesOf(MyClass.class);
    }

    @Test
    public void classes_should_not_access_standard_streams() {
        noClasses().should(ACCESS_STANDARD_STREAMS).check(classes);
    }

    @Test
    public void classes_should_not_access_standard_streams_from_library() {
        NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS.check(classes);
    }

    @Test
    public void classes_should_not_throw_generic_exceptions() {
        NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS.check(classes);
    }

    @Test
    public void classes_should_use_java_util_logging() {
        NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING.check(classes);
    }

    @Test
    public void interfaces_should_not_have_names_ending_with_the_word_interface() {
        JavaClasses classes = new ClassFileImporter().importClasses(MyClass.class, MyOtherClass.class);

        noClasses().that().areInterfaces().should().haveNameMatching(".*Interface").check(classes);
    }

    @Test
    public void interfaces_should_not_have_simplace_class_names_ending_with_the_word_interface() {
        JavaClasses classes = new ClassFileImporter().importClasses(MyClass.class, MyOtherClass.class);

        noClasses().that().areInterfaces().should().haveSimpleNameContaining("Interface").check(classes);
    }

    @Test
    public void interfaces_must_not_be_placed_in_implementation_packages(){
        JavaClasses classes = new ClassFileImporter().importPackagesOf(MyClass.class, MyOtherClass.class);

        noClasses().that().resideInAPackage("..impl..").should().beInterfaces().check(classes);
    }

}
