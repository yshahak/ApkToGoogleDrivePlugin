package funkey.plugins

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import static org.junit.Assert.assertNotNull
/**
 * @author Annyce Davis on 2/17/16.
 */
public class PluginTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder()

    def projectDir
    def project
    def task

    @Before
    void setUp() {
        projectDir = temporaryFolder.root
        projectDir.mkdirs()

        project = ProjectBuilder.builder().withProjectDir(projectDir).build()
    }

    @Test
    public void pluginShouldBeApplied() {
        project.apply plugin: 'com.android.application'
        project.apply plugin: UploadApkPlugIn

        project.evaluate()

        assertNotNull(project.tasks.findByName('uploadDebug'))
    }

//    @Test
//    public void pluginShouldHandleExtensionVersionInfo() {
//        project.apply plugin: BumpReadMeVersionPlugin
//        project.bumpReadMeVersion {
//            versionName = '1.1'
//        }
//
//        project.evaluate()
//
//        org.junit.Assert.assertEquals('1.1', project.tasks[BumpReadMeVersionPlugin.DISPLAY_VERSION_TASK].versionName)
//    }

}