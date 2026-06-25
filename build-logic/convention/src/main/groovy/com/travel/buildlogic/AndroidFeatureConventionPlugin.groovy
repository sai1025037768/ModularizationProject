package com.travel.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidFeatureConventionPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        boolean buildAsApplication = (project.findProperty('isBuildModule') ?: 'false').toString().toBoolean()

        project.pluginManager.apply(buildAsApplication ? 'com.android.application' : 'com.android.library')
        AndroidConvention.configure(project, !buildAsApplication)
        AndroidConvention.configureFeatureSourceSets(project, buildAsApplication)
    }
}
