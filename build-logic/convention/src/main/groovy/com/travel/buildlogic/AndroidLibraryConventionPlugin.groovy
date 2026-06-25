package com.travel.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryConventionPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.pluginManager.apply('com.android.library')
        AndroidConvention.configure(project, true)
    }
}
