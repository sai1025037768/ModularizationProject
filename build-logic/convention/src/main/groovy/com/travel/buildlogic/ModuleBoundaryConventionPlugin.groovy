package com.travel.buildlogic

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency

class ModuleBoundaryConventionPlugin implements Plugin<Project> {

    private static final String APP_PATH = ':app'
    private static final String FEATURE_HOME_PATH = ':feature-home'
    private static final String FEATURE_PREFIX = ':feature-'
    private static final String FEATURE_API_SUFFIX = '-api'
    private static final String PLATFORM_PREFIX = ':platform-'
    private static final String NAVIGATION_AROUTER_PATH = ':platform-navigation-arouter'
    private static final String APP_PACKAGE_PREFIX = 'com.travel.app'
    private static final String BIZ_PACKAGE_PREFIX = 'com.travel.biz.'
    private static final String CORE_PACKAGE_PREFIX = 'com.travel.core.'
    private static final String FEATURE_PACKAGE_PREFIX = 'com.travel.feature.'
    private static final String PLATFORM_PACKAGE_PREFIX = 'com.travel.platform.'
    private static final String AROUTER_LAUNCHER_PREFIX = 'com.alibaba.android.arouter.launcher.'
    private static final String AROUTER_PREFIX = 'com.alibaba.android.arouter.'
    private static final String AROUTER_ADAPTER_PATH_SEGMENT = '/router/arouter/'
    private static final String ANDROID_PREFIX = 'android.'
    private static final String ANDROIDX_PREFIX = 'androidx.'
    private static final String RETROFIT_PREFIX = 'retrofit2.'
    private static final String RXJAVA_PREFIX = 'rx.'
    private static final String RXJAVA2_PREFIX = 'io.reactivex.'
    private static final List<String> ROUTE_PARAMETER_KEY_LITERAL_MARKERS = [
            '.withString("',
            '.withInt("',
            '.withIntegerArrayList("',
            '.withParcelable("',
            '.withParcelableArrayList("',
            '.getStringExtra("',
            '.getParcelableExtra("',
            '.getParcelableArrayListExtra("',
            '.getIntegerArrayListExtra("'
    ]

    @Override
    void apply(Project project) {
        if (project != project.rootProject) {
            throw new GradleException('travel.module-boundary must be applied to the root project.')
        }

        def checkTask = project.tasks.register('checkModuleBoundaries') { task ->
            task.group = 'verification'
            task.description = 'Checks travel module dependency and source import boundaries.'
            task.doLast {
                checkModuleBoundaries(project.rootProject)
            }
        }

        def checkLifecycleTask = project.tasks.findByName('check')
        if (checkLifecycleTask == null) {
            project.tasks.register('check') { task ->
                task.group = 'verification'
                task.description = 'Runs all verification checks.'
                task.dependsOn(checkTask)
            }
        } else {
            checkLifecycleTask.dependsOn(checkTask)
        }

        project.subprojects { subproject ->
            subproject.tasks.matching { it.name == 'preBuild' }.configureEach { task ->
                task.dependsOn(checkTask)
            }
        }
    }

    private static void checkModuleBoundaries(Project rootProject) {
        Set<String> violations = new LinkedHashSet<>()

        rootProject.subprojects.each { Project owner ->
            projectDependencies(owner).each { Project dependency ->
                validateProjectDependency(owner, dependency, violations)
            }
            validateSourceImports(owner, violations)
        }

        if (!violations.isEmpty()) {
            String message = new StringBuilder()
                    .append('Module boundary check failed:')
                    .append(System.lineSeparator())
                    .append(violations.collect { " - ${it}" }.join(System.lineSeparator()))
                    .append(System.lineSeparator())
                    .append(System.lineSeparator())
                    .append('Rules:')
                    .append(System.lineSeparator())
                    .append(' - feature implementation modules may depend on feature-api modules, but not feature implementations.')
                    .append(System.lineSeparator())
                    .append(' - feature-api modules must not depend on any feature module.')
                    .append(System.lineSeparator())
                    .append(' - feature-home may depend on feature-api modules, but not feature implementations.')
                    .append(System.lineSeparator())
                    .append(' - app may compile against feature-api contracts and assemble feature implementations, but must not import feature implementation packages.')
                    .append(System.lineSeparator())
                    .append(' - core/biz modules must not depend on feature modules.')
                    .append(System.lineSeparator())
                    .append(' - platform modules may depend on core modules and third-party adapters, but not app, feature, biz, or platform modules.')
                    .append(System.lineSeparator())
                    .append(' - only platform-navigation-arouter may import ARouter launcher APIs.')
                    .append(System.lineSeparator())
                    .append(' - feature modules may use ARouter annotations/templates only inside router/arouter adapters.')
                    .append(System.lineSeparator())
                    .append(' - core modules must not depend on app, feature, biz, or other core modules.')
                    .append(System.lineSeparator())
                    .append(' - biz-service-base must not depend on app, feature, or core modules.')
                    .append(System.lineSeparator())
                    .append(' - feature domain packages must stay pure and must not import Android, ARouter, Retrofit, RxJava, core, data, presentation, di, or feature-api types.')
                    .append(System.lineSeparator())
                    .append(' - feature data packages may depend on domain and infrastructure, but not presentation, di, or feature-api types.')
                    .append(System.lineSeparator())
                    .append(' - feature presentation packages must not import data, di, core-network, or core-storage types.')
                    .append(System.lineSeparator())
                    .append(' - route parameter keys must use API constants instead of inline string literals.')
                    .toString()
            throw new GradleException(message)
        }
    }

    private static Set<Project> projectDependencies(Project project) {
        Set<Project> dependencies = new LinkedHashSet<>()
        project.configurations.each { configuration ->
            configuration.dependencies.withType(ProjectDependency).each { ProjectDependency dependency ->
                Project dependencyProject = findDependencyProject(project, dependency)
                if (dependencyProject != null && dependencyProject != project) {
                    dependencies.add(dependencyProject)
                }
            }
        }
        return dependencies
    }

    private static Project findDependencyProject(Project project, ProjectDependency dependency) {
        if (dependency.metaClass.respondsTo(dependency, 'getTargetProjectIdentity')) {
            def projectIdentity = dependency.getTargetProjectIdentity()
            if (projectIdentity != null) {
                Project dependencyProject = project.rootProject.findProject(projectIdentity.projectPath.toString())
                if (dependencyProject != null) {
                    return dependencyProject
                }
            }
        }

        return project.rootProject.findProject(dependency.path)
    }

    private static void validateProjectDependency(Project owner, Project dependency, Set<String> violations) {
        String from = owner.path
        String to = dependency.path

        if (isCoreProject(from)) {
            if (to == APP_PATH || isFeatureProject(to) || isBizProject(to) || isCoreProject(to) || isPlatformProject(to)) {
                violations.add("${from} is a core module and must not depend on project module ${to}.")
            }
            return
        }

        if (isBizProject(from)) {
            if (to == APP_PATH || isFeatureProject(to) || isCoreProject(to) || isPlatformProject(to)) {
                violations.add("${from} is a business contract base module and must not depend on project module ${to}.")
            }
            return
        }

        if (isPlatformProject(from)) {
            if (to == APP_PATH || isFeatureProject(to) || isBizProject(to) || isPlatformProject(to)) {
                violations.add("${from} is a platform adapter module and must not depend on project module ${to}.")
            }
            return
        }

        if (isPlatformProject(to)) {
            if (from != APP_PATH) {
                violations.add("${from} must depend on core abstractions instead of platform adapter ${to}.")
            }
            return
        }

        if (!isFeatureProject(to)) {
            return
        }

        if (from == APP_PATH) {
            return
        }

        if (from == FEATURE_HOME_PATH) {
            if (!isFeatureApi(to)) {
                violations.add("${from} may depend on feature-api modules, but not feature implementation ${to}.")
            }
            return
        }

        if (isFeatureApi(from)) {
            violations.add("${from} is an API module and must not depend on feature module ${to}.")
            return
        }

        if (isFeatureImplementation(from)) {
            if (!isFeatureApi(to)) {
                violations.add("${from} may depend on feature-api modules, but not feature implementation ${to}.")
            }
            return
        }

        if (isCoreOrBiz(from)) {
            violations.add("${from} is a lower-level module and must not depend on feature module ${to}.")
        }
    }

    private static void validateSourceImports(Project project, Set<String> violations) {
        validateArouterImports(project, violations)
        validateRouteParameterKeyLiterals(project, violations)

        if (project.path == APP_PATH) {
            validateAppImports(project, violations)
        } else if (project.path == FEATURE_HOME_PATH) {
            validateFeatureHomeImports(project, violations)
        } else if (isFeatureApi(project.path)) {
            validateFeatureApiImports(project, featureName(project.path), violations)
        } else if (isFeatureImplementation(project.path)) {
            validateFeatureImplementationImports(project, featureName(project.path), violations)
            validateFeatureCleanArchitectureImports(project, featureName(project.path), violations)
        } else if (isCoreProject(project.path)) {
            validateCoreImports(project, violations)
        } else if (isBizProject(project.path)) {
            validateBizImports(project, violations)
        } else if (isPlatformProject(project.path)) {
            validatePlatformImports(project, violations)
        } else if (isCoreOrBiz(project.path)) {
            validateNoFeatureImports(project, violations)
        }
    }

    private static void validateArouterImports(Project project, Set<String> violations) {
        scanImports(project) { TravelImport travelImport ->
            if (!travelImport.importedType.startsWith(AROUTER_PREFIX)) {
                return
            }

            if (project.path == NAVIGATION_AROUTER_PATH) {
                return
            }

            if (travelImport.importedType.startsWith(AROUTER_LAUNCHER_PREFIX)) {
                violations.add("${travelImport.location}: ${project.path} must use core-navigation instead of importing ${travelImport.importedType}.")
                return
            }

            if (isFeatureArouterAdapter(project, travelImport.location)) {
                return
            }

            violations.add("${travelImport.location}: ${project.path} may use ARouter only inside router/arouter adapters, not ${travelImport.importedType}.")
        }
    }

    private static boolean isFeatureArouterAdapter(Project project, String location) {
        return isFeatureProject(project.path)
                && location.replace('\\', '/').contains(AROUTER_ADAPTER_PATH_SEGMENT)
    }

    private static void validateRouteParameterKeyLiterals(Project project, Set<String> violations) {
        project.fileTree(project.projectDir) {
            include 'src/**/*.java'
            include 'src/**/*.kt'
        }.files.sort { it.path }.each { File file ->
            file.eachLine { String line, int lineNumber ->
                if (containsRouteParameterKeyLiteral(line)) {
                    violations.add("${project.rootProject.relativePath(file)}:${lineNumber}: route parameter keys must use API constants instead of inline string literals.")
                }
            }
        }
    }

    private static boolean containsRouteParameterKeyLiteral(String line) {
        ROUTE_PARAMETER_KEY_LITERAL_MARKERS.any { marker -> line.contains(marker) }
    }

    private static void validateAppImports(Project project, Set<String> violations) {
        scanFeatureImports(project) { FeatureImport featureImport ->
            if (featureImport.secondSegment != 'api') {
                violations.add("${featureImport.location}: ${project.path} may import feature APIs, but not feature implementation ${featureImport.importedType}.")
            }
        }
    }

    private static void validateFeatureHomeImports(Project project, Set<String> violations) {
        scanFeatureImports(project) { FeatureImport featureImport ->
            if (featureImport.featureName == 'home') {
                return
            }
            if (featureImport.secondSegment != 'api') {
                violations.add("${featureImport.location}: ${project.path} may import feature APIs, but not feature implementation ${featureImport.importedType}.")
            }
        }
    }

    private static void validateFeatureApiImports(Project project, String ownFeatureName, Set<String> violations) {
        scanFeatureImports(project) { FeatureImport featureImport ->
            if (featureImport.featureName != ownFeatureName) {
                violations.add("${featureImport.location}: ${project.path} must not import another feature API or implementation: ${featureImport.importedType}.")
            } else if (featureImport.secondSegment != 'api') {
                violations.add("${featureImport.location}: ${project.path} must not import its implementation package: ${featureImport.importedType}.")
            }
        }
    }

    private static void validateFeatureImplementationImports(Project project, String ownFeatureName, Set<String> violations) {
        scanFeatureImports(project) { FeatureImport featureImport ->
            if (featureImport.featureName == ownFeatureName || featureImport.secondSegment == 'api') {
                return
            }
            violations.add("${featureImport.location}: ${project.path} may import feature APIs, but not another feature implementation ${featureImport.importedType}.")
        }
    }

    private static void validateFeatureCleanArchitectureImports(Project project, String ownFeatureName, Set<String> violations) {
        scanImports(project) { TravelImport travelImport ->
            String sourceLayer = sourceFeatureLayer(travelImport.location, ownFeatureName)
            if (sourceLayer == null) {
                return
            }

            switch (sourceLayer) {
                case 'domain':
                    validateFeatureDomainImport(project, ownFeatureName, travelImport, violations)
                    break
                case 'data':
                    validateFeatureDataImport(project, ownFeatureName, travelImport, violations)
                    break
                case 'presentation':
                    validateFeaturePresentationImport(project, ownFeatureName, travelImport, violations)
                    break
                case 'di':
                    validateFeatureDiImport(project, ownFeatureName, travelImport, violations)
                    break
                case 'root':
                    validateFeatureRootImport(project, ownFeatureName, travelImport, violations)
                    break
                case 'router':
                    validateFeatureRouterImport(project, ownFeatureName, travelImport, violations)
                    break
            }
        }
    }

    private static void validateFeatureDomainImport(Project project,
                                                    String ownFeatureName,
                                                    TravelImport travelImport,
                                                    Set<String> violations) {
        String importedType = travelImport.importedType
        if (isDomainForbiddenFrameworkImport(importedType)) {
            violations.add("${travelImport.location}: ${project.path} domain must stay pure and must not import ${importedType}.")
            return
        }

        String importedLayer = importedOwnFeatureLayer(importedType, ownFeatureName)
        if (importedLayer != null && importedLayer != 'domain') {
            violations.add("${travelImport.location}: ${project.path} domain may import only its own domain layer, not ${importedType}.")
            return
        }

        if (importedType.startsWith(FEATURE_PACKAGE_PREFIX) && importedLayer == null) {
            violations.add("${travelImport.location}: ${project.path} domain must not import feature contract or implementation type ${importedType}.")
        }
    }

    private static void validateFeatureDataImport(Project project,
                                                  String ownFeatureName,
                                                  TravelImport travelImport,
                                                  Set<String> violations) {
        String importedType = travelImport.importedType
        String importedLayer = importedOwnFeatureLayer(importedType, ownFeatureName)
        if (importedLayer == 'presentation' || importedLayer == 'di' || importedLayer == 'api' || importedLayer == 'root') {
            violations.add("${travelImport.location}: ${project.path} data may depend on domain/infrastructure, not ${importedType}.")
            return
        }

        if (importedType.startsWith(FEATURE_PACKAGE_PREFIX) && importedLayer == null) {
            violations.add("${travelImport.location}: ${project.path} data must not import another feature type ${importedType}.")
        }
    }

    private static void validateFeaturePresentationImport(Project project,
                                                          String ownFeatureName,
                                                          TravelImport travelImport,
                                                          Set<String> violations) {
        String importedType = travelImport.importedType
        String importedLayer = importedOwnFeatureLayer(importedType, ownFeatureName)
        if (importedLayer == 'data' || importedLayer == 'di') {
            violations.add("${travelImport.location}: ${project.path} presentation must not import ${importedType}.")
            return
        }

        if (importedType.startsWith("${CORE_PACKAGE_PREFIX}network.")
                || importedType.startsWith("${CORE_PACKAGE_PREFIX}storage.")) {
            violations.add("${travelImport.location}: ${project.path} presentation must not import infrastructure type ${importedType}.")
        }
    }

    private static void validateFeatureDiImport(Project project,
                                                String ownFeatureName,
                                                TravelImport travelImport,
                                                Set<String> violations) {
        String importedType = travelImport.importedType
        String importedLayer = importedOwnFeatureLayer(importedType, ownFeatureName)
        if (importedLayer == 'presentation') {
            violations.add("${travelImport.location}: ${project.path} di must not import presentation type ${importedType}.")
            return
        }

        if (importedType.startsWith(FEATURE_PACKAGE_PREFIX) && importedLayer == null && !isFeatureApiImport(importedType)) {
            violations.add("${travelImport.location}: ${project.path} di must not import another feature implementation type ${importedType}.")
        }
    }

    private static void validateFeatureRootImport(Project project,
                                                  String ownFeatureName,
                                                  TravelImport travelImport,
                                                  Set<String> violations) {
        String importedLayer = importedOwnFeatureLayer(travelImport.importedType, ownFeatureName)
        if (importedLayer == 'data' || importedLayer == 'presentation') {
            violations.add("${travelImport.location}: ${project.path} entry adapters must not import ${travelImport.importedType} directly.")
        }
    }

    private static void validateFeatureRouterImport(Project project,
                                                    String ownFeatureName,
                                                    TravelImport travelImport,
                                                    Set<String> violations) {
        String importedType = travelImport.importedType
        String importedLayer = importedOwnFeatureLayer(importedType, ownFeatureName)
        if (importedLayer == 'data' || importedLayer == 'domain' || importedLayer == 'di') {
            violations.add("${travelImport.location}: ${project.path} router adapters must not import business internals ${importedType}.")
            return
        }

        if (importedType.startsWith(FEATURE_PACKAGE_PREFIX)
                && importedLayer == null
                && !isFeatureApiImport(importedType)) {
            violations.add("${travelImport.location}: ${project.path} router adapters must not import another feature implementation ${importedType}.")
        }
    }

    private static void validateNoFeatureImports(Project project, Set<String> violations) {
        scanFeatureImports(project) { FeatureImport featureImport ->
            violations.add("${featureImport.location}: ${project.path} is a lower-level module and must not import ${featureImport.importedType}.")
        }
    }

    private static void validateCoreImports(Project project, Set<String> violations) {
        String ownCorePackagePrefix = corePackagePrefix(project.path)
        scanTravelImports(project) { TravelImport travelImport ->
            if (travelImport.importedType.startsWith(APP_PACKAGE_PREFIX)
                    || travelImport.importedType.startsWith(FEATURE_PACKAGE_PREFIX)
                    || travelImport.importedType.startsWith(BIZ_PACKAGE_PREFIX)) {
                violations.add("${travelImport.location}: ${project.path} is a core module and must not import ${travelImport.importedType}.")
                return
            }

            if (travelImport.importedType.startsWith(CORE_PACKAGE_PREFIX)
                    && ownCorePackagePrefix != null
                    && !travelImport.importedType.startsWith(ownCorePackagePrefix)) {
                violations.add("${travelImport.location}: ${project.path} must not import another core module: ${travelImport.importedType}.")
            }
        }
    }

    private static void validateBizImports(Project project, Set<String> violations) {
        scanTravelImports(project) { TravelImport travelImport ->
            if (travelImport.importedType.startsWith(APP_PACKAGE_PREFIX)
                    || travelImport.importedType.startsWith(FEATURE_PACKAGE_PREFIX)
                    || travelImport.importedType.startsWith(CORE_PACKAGE_PREFIX)) {
                violations.add("${travelImport.location}: ${project.path} is a business contract base module and must not import ${travelImport.importedType}.")
            }
        }
    }

    private static void validatePlatformImports(Project project, Set<String> violations) {
        scanTravelImports(project) { TravelImport travelImport ->
            if (travelImport.importedType.startsWith(APP_PACKAGE_PREFIX)
                    || travelImport.importedType.startsWith(FEATURE_PACKAGE_PREFIX)
                    || travelImport.importedType.startsWith(BIZ_PACKAGE_PREFIX)
                    || (travelImport.importedType.startsWith(PLATFORM_PACKAGE_PREFIX)
                    && !travelImport.importedType.startsWith(platformPackagePrefix(project.path)))) {
                violations.add("${travelImport.location}: ${project.path} is a platform adapter module and must not import ${travelImport.importedType}.")
            }
        }
    }

    private static void scanFeatureImports(Project project, Closure onFeatureImport) {
        project.fileTree(project.projectDir) {
            include 'src/**/*.java'
            include 'src/**/*.kt'
        }.files.sort { it.path }.each { File file ->
            file.eachLine { String line, int lineNumber ->
                String importedType = importedType(line)
                if (importedType == null || !importedType.startsWith(FEATURE_PACKAGE_PREFIX)) {
                    return
                }

                String tail = importedType.substring(FEATURE_PACKAGE_PREFIX.length())
                String[] segments = tail.split('\\.')
                if (segments.length < 2) {
                    return
                }

                onFeatureImport(new FeatureImport(
                        project.rootProject.relativePath(file),
                        importedType,
                        segments[0],
                        segments[1]
                ))
            }
        }
    }

    private static void scanTravelImports(Project project, Closure onTravelImport) {
        scanImports(project) { TravelImport travelImport ->
            if (travelImport.importedType.startsWith('com.travel.')) {
                onTravelImport(travelImport)
            }
        }
    }

    private static void scanImports(Project project, Closure onImport) {
        project.fileTree(project.projectDir) {
            include 'src/**/*.java'
            include 'src/**/*.kt'
        }.files.sort { it.path }.each { File file ->
            file.eachLine { String line, int lineNumber ->
                String importedType = importedType(line)
                if (importedType == null) {
                    return
                }

                onImport(new TravelImport(
                        project.rootProject.relativePath(file),
                        importedType
                ))
            }
        }
    }

    private static String importedType(String line) {
        String trimmed = line.trim()
        if (!trimmed.startsWith('import ')) {
            return null
        }

        String importedType = trimmed
                .replaceFirst('^import\\s+static\\s+', '')
                .replaceFirst('^import\\s+', '')
                .replaceFirst(';$', '')
                .trim()
        return importedType
    }

    private static boolean isFeatureProject(String projectPath) {
        return projectPath.startsWith(FEATURE_PREFIX)
    }

    private static boolean isFeatureApi(String projectPath) {
        return projectPath.startsWith(FEATURE_PREFIX) && projectPath.endsWith(FEATURE_API_SUFFIX)
    }

    private static boolean isFeatureImplementation(String projectPath) {
        return projectPath.startsWith(FEATURE_PREFIX)
                && projectPath != FEATURE_HOME_PATH
                && !projectPath.endsWith(FEATURE_API_SUFFIX)
    }

    private static boolean isDomainForbiddenFrameworkImport(String importedType) {
        return importedType.startsWith(ANDROID_PREFIX)
                || importedType.startsWith(ANDROIDX_PREFIX)
                || importedType.startsWith(AROUTER_PREFIX)
                || importedType.startsWith(RETROFIT_PREFIX)
                || importedType.startsWith(RXJAVA_PREFIX)
                || importedType.startsWith(RXJAVA2_PREFIX)
                || importedType.startsWith(CORE_PACKAGE_PREFIX)
    }

    private static boolean isFeatureApiImport(String importedType) {
        if (!importedType.startsWith(FEATURE_PACKAGE_PREFIX)) {
            return false
        }
        String tail = importedType.substring(FEATURE_PACKAGE_PREFIX.length())
        String[] segments = tail.split('\\.')
        return segments.length > 1 && segments[1] == 'api'
    }

    private static String sourceFeatureLayer(String location, String ownFeatureName) {
        String normalized = location.replace('\\', '/')
        String javaPrefix = "src/main/java/com/travel/feature/${ownFeatureName}/"
        String kotlinPrefix = "src/main/kotlin/com/travel/feature/${ownFeatureName}/"
        String prefix = javaPrefix
        int index = normalized.indexOf(prefix)
        if (index < 0) {
            prefix = kotlinPrefix
            index = normalized.indexOf(prefix)
        }
        if (index < 0) {
            return null
        }

        String tail = normalized.substring(index + prefix.length())
        int slashIndex = tail.indexOf('/')
        if (slashIndex < 0) {
            return 'root'
        }

        String firstSegment = tail.substring(0, slashIndex)
        return isCleanArchitectureLayer(firstSegment) ? firstSegment : 'root'
    }

    private static String importedOwnFeatureLayer(String importedType, String ownFeatureName) {
        String ownFeaturePackagePrefix = "${FEATURE_PACKAGE_PREFIX}${ownFeatureName}."
        if (!importedType.startsWith(ownFeaturePackagePrefix)) {
            return null
        }

        String tail = importedType.substring(ownFeaturePackagePrefix.length())
        String[] segments = tail.split('\\.')
        if (segments.length == 0) {
            return 'root'
        }

        String firstSegment = segments[0]
        if (isCleanArchitectureLayer(firstSegment)) {
            return firstSegment
        }
        return firstSegment == 'api' ? 'api' : 'root'
    }

    private static boolean isCleanArchitectureLayer(String segment) {
        return segment == 'domain'
                || segment == 'data'
                || segment == 'presentation'
                || segment == 'di'
                || segment == 'router'
    }

    private static boolean isCoreProject(String projectPath) {
        return projectPath.startsWith(':core-')
    }

    private static boolean isPlatformProject(String projectPath) {
        return projectPath.startsWith(PLATFORM_PREFIX)
    }

    private static boolean isBizProject(String projectPath) {
        return projectPath.startsWith(':biz-')
    }

    private static boolean isCoreOrBiz(String projectPath) {
        return projectPath.startsWith(':core-') || projectPath.startsWith(':biz-')
    }

    private static String corePackagePrefix(String projectPath) {
        if (!isCoreProject(projectPath)) {
            return null
        }
        return "${CORE_PACKAGE_PREFIX}${projectPath.substring(':core-'.length()).replace('-', '.')}"
    }

    private static String platformPackagePrefix(String projectPath) {
        if (!isPlatformProject(projectPath)) {
            return null
        }
        return "${PLATFORM_PACKAGE_PREFIX}${projectPath.substring(PLATFORM_PREFIX.length()).replace('-', '.')}"
    }

    private static String featureName(String projectPath) {
        String name = projectPath.substring(FEATURE_PREFIX.length())
        return name.endsWith(FEATURE_API_SUFFIX)
                ? name.substring(0, name.length() - FEATURE_API_SUFFIX.length())
                : name
    }

    private static class FeatureImport {
        final String location
        final String importedType
        final String featureName
        final String secondSegment

        FeatureImport(String location, String importedType, String featureName, String secondSegment) {
            this.location = location
            this.importedType = importedType
            this.featureName = featureName
            this.secondSegment = secondSegment
        }
    }

    private static class TravelImport {
        final String location
        final String importedType

        TravelImport(String location, String importedType) {
            this.location = location
            this.importedType = importedType
        }
    }
}
