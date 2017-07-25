# Gradle Quality Configuration

This repository contains gradle quality configuration files which can be used in all Java projects as a git submodule. It includs CheckStyle, FindBugs, Jacoco, PMD and CPD.


### Enable in your Java project

To enable this for your project, simply add this as your git sub module:

```$xslt
git submodule add git@github.com:jiakuan/gradle-quality-conf.git gradle/conf
```

And then commit your changes.

For anyone else who wants to clone your Java project, they can use the following commands to pull the Gradle configuration files.

```$xslt
git submodule init
git submodule update
```

### Configure quality parameters

In your root `build.gradle` file, add this on top:

```
buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        maven {
            url "https://plugins.gradle.org/m2/"
        }
    }
    dependencies {
        // https://plugins.gradle.org/plugin/com.github.kt3k.coveralls
        classpath "org.kt3k.gradle.plugin:coveralls-gradle-plugin:2.6.3"
        // https://github.com/aaschmid/gradle-cpd-plugin
        classpath 'de.aaschmid:gradle-cpd-plugin:1.0'
    }
}
```

And add the following lines:

```$xslt
def skip_quality_check = project.hasProperty('check') && check == 'false'
println("skip_quality_check: " + skip_quality_check)
if (!skip_quality_check) {
    ext.jacocoLimits = [
            'instruction': 68.98,
            'branch'     : 41.1,
            'line'       : 71.89,
            'complexity' : 63.08,
            'method'     : 82.8,
            'class'      : 70.0
    ]
    ext.jacocoExcludes = [
            '**/Console.class', '**/*Module.class'
    ]
    apply from: "$rootDir/gradle/conf/checkstyle.gradle"
    apply from: "$rootDir/gradle/conf/pmd.gradle"
    apply from: "$rootDir/gradle/conf/cpd.gradle"
    apply from: "$rootDir/gradle/conf/findbugs.gradle"
    apply from: "$rootDir/gradle/conf/jacoco.gradle"
}
```

Here, you can tune your minimum coverage limits, also you can configure classes which should be excluded from the coverage calculation.

### Build project without quality check

By default, quality check is enabled. If you want to disable quality check when you are developing new classes and you want to focus on something else, you can build your project without quality check by passing a property `-Pcheck=false`.

For example:

```$xslt
gradle clean build -Pcheck=false
```
