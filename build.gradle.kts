/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

val catalog = the<VersionCatalogsExtension>().named("libs")
val pulsarConnectorsVersion = catalog.findVersion("pulsar.connectors").get().requiredVersion

allprojects {
    group = "org.apache.pulsar"
    version = pulsarConnectorsVersion
}

subprojects {
    // The pulsar-dependencies module uses java-platform (enforced BOM),
    // which is mutually exclusive with java-library.
    if (project.name == "pulsar-dependencies") {
        return@subprojects
    }

    apply(plugin = "java-library")

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
        options.compilerArgs.addAll(listOf("-parameters"))
    }

    configurations.all {
        exclude(group = "org.apache.logging.log4j", module = "log4j-slf4j-impl")

        resolutionStrategy.eachDependency {
            if (requested.group.startsWith("com.fasterxml.jackson")) {
                useVersion(rootProject.libs.versions.jackson.get())
            }
        }
    }

    dependencies {
        // All subprojects inherit version constraints from the enforced platform
        "implementation"(enforcedPlatform(project(":pulsar-dependencies")))

        // Lombok
        "compileOnly"(rootProject.libs.lombok)
        "annotationProcessor"(rootProject.libs.lombok)
        "testCompileOnly"(rootProject.libs.lombok)
        "testAnnotationProcessor"(rootProject.libs.lombok)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        jvmArgs(
            "--add-opens", "java.base/jdk.internal.platform=ALL-UNNAMED",
            "--add-opens", "java.base/java.lang=ALL-UNNAMED",
            "-Dpulsar.allocator.pooled=true",
            "-Dpulsar.allocator.exit_on_oom=false",
            "-Dpulsar.allocator.out_of_memory_policy=FallbackToHeap",
        )
    }
}

// Access version catalog from subprojects
val Project.libs: org.gradle.accessors.dm.LibrariesForLibs
    get() = rootProject.extensions.getByType()
