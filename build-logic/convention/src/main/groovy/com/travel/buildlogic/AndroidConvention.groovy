package com.travel.buildlogic

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

final class AndroidConvention {
    private AndroidConvention() {
    }

    static void configure(Project project, boolean configureConsumerProguard) {
        VersionCatalog libs = project.extensions.getByType(VersionCatalogsExtension).named('libs')
        def android = project.extensions.getByName('android')

        android.compileSdkVersion(versionInt(libs, 'compileSdk'))

        android.defaultConfig {
            minSdkVersion versionInt(libs, 'minSdk')
            targetSdkVersion versionInt(libs, 'targetSdk')
            versionCode versionInt(libs, 'appVersionCode')
            versionName version(libs, 'appVersionName')
            testInstrumentationRunner 'androidx.test.runner.AndroidJUnitRunner'

            if (configureConsumerProguard && project.file('consumer-rules.pro').exists()) {
                consumerProguardFiles 'consumer-rules.pro'
            }

            javaCompileOptions {
                annotationProcessorOptions {
                    arguments += [AROUTER_MODULE_NAME: project.name]
                }
            }
        }

        android.buildTypes {
            release {
                minifyEnabled false
                proguardFiles android.getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            }
        }

        android.compileOptions {
            sourceCompatibility JavaVersion.VERSION_17
            targetCompatibility JavaVersion.VERSION_17
        }
    }

    static void configureFeatureSourceSets(Project project, boolean buildAsApplication) {
        def android = project.extensions.getByName('android')

        android.sourceSets {
            main {
                manifest.srcFile buildAsApplication ? 'src/main/debug/AndroidManifest.xml' : 'src/main/release/AndroidManifest.xml'
            }
        }
    }

    private static int versionInt(VersionCatalog libs, String name) {
        return libs.findVersion(name).get().requiredVersion as int
    }

    private static String version(VersionCatalog libs, String name) {
        return libs.findVersion(name).get().requiredVersion
    }
}
