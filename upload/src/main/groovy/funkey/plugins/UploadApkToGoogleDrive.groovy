package funkey.plugins

import org.gradle.api.tasks.Exec

class UploadApkToGoogleDrive extends Exec {
    def versionName
    def projectDirPath
    def folderId
//    def commandUpdate = "/usr/local/bin/gdrive update -p  "
    def commandDelete = "/usr/local/bin/gdrive delete "


    UploadApkToGoogleDrive() {
        group = 'uploadApk'
        description = 'upload apk to google driver folder'
    }

    @Override
    protected void exec() {
        if (versionName != null) {
            def commandGetFiles = "/usr/local/bin/gdrive list --order \"createdTime desc\" -q \" '${folderId}' in parents AND trashed = false AND name contains "
            def query = "${commandGetFiles}'${versionName}'\""
            def cmd = ["/bin/bash", "-c", query]
            Process process = cmd.execute()
            def out = new StringBuffer()
            def err = new StringBuffer()
            process.waitForProcessOutput( out, err )
//            println("standardOutput=" + out)
            executable "/bin/sh"
            if (out.toString().length() > 0 && out.toString().split("Created").length > 1 && out.toString().split("Created")[1].split(" ").length > 0){
                def id = out.toString().split("Created")[1].split(" ")[0].trim()
                def command = commandDelete + id// + " " + projectDirPath + "/" + versionName
                 ["/bin/bash", "-c", command].execute().waitFor()
            }
            def commandUpload = "/usr/local/bin/gdrive upload -p ${folderId} " //0BxT8f6nD40l4eGxKOERHQ3BoRlk
            args "-c", commandUpload + projectDirPath + "/" + versionName
            super.exec()
        }
    }


}