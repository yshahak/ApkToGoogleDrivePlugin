package funkey.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class UploadApkPlugIn implements Plugin<Project> {

    @SuppressWarnings("GrUnresolvedAccess")
    @Override
    void apply(Project project) {
        project.extensions.create('apkPrefixAndFolderId', VersionPluginExtension)

        project.afterEvaluate {
            project.task('uploadDebug', type: UploadApkToGoogleDrive) {variant ->
                def variantName = project.android.defaultConfig.versionName.replaceAll("\\.", "_")
                versionName = "${project?.apkPrefixAndFolderId?.apk_prefix}${variantName}_dbg.apk"
                folderId = project?.apkPrefixAndFolderId?.folder_id
                projectDirPath = "${project.projectDir}/debug"
            }doLast{
                println("${versionName} uploaded to Google Drive")
            }
            project.task('uploadRelease', type: UploadApkToGoogleDrive) {
                def variantName = project.android.defaultConfig.versionName.replaceAll("\\.", "_")
                versionName = "${project?.apkPrefixAndFolderId?.apk_prefix}${variantName}_prd.apk"
                folderId = project?.apkPrefixAndFolderId?.folder_id
//                projectDirPath = project.projectDir
                projectDirPath = "${project.projectDir}/release"
            }doLast{
                println("${versionName} uploaded to Google Drive")
            }
        }
        project.android.applicationVariants.all { variant ->
            variant.outputs.all { output ->
                def variantName = project.android.defaultConfig.versionName.replaceAll("\\.", "_")
                def buildType = variant.variantData.variantConfiguration.buildType.name
                def apkName
                if (buildType == 'debug') {
                    apkName = "${project?.apkPrefixAndFolderId?.apk_prefix}${variantName}_dbg.apk"
                } else {
                    apkName = "${project?.apkPrefixAndFolderId?.apk_prefix}${variantName}_prd.apk"
                }
                output.outputFileName = apkName
//                output.outputFile = new File(output.getOutputFileName, apkName)
                println("apk=${output.outputFileName}")
            }
        }
    }
}

