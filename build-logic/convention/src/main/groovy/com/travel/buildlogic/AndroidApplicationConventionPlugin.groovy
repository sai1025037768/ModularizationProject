package com.travel.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationConventionPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.pluginManager.apply('com.android.application')
        AndroidConvention.configure(project, false)
    }
}
