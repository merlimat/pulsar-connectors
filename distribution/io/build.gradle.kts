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

// Distribution module — no Java compilation needed
tasks.named("compileJava") { enabled = false }
tasks.named("compileTestJava") { enabled = false }
tasks.named("jar") { enabled = false }

val pulsarConnectorsVersion = project.version.toString()
val rootDir = rootProject.projectDir

// IO connector project paths
val connectorNarProjects = listOf(
    ":cassandra",
    ":kafka",
    ":http",
    ":kinesis",
    ":rabbitmq",
    ":nsq",
    ":jdbc:sqlite",
    ":jdbc:mariadb",
    ":jdbc:clickhouse",
    ":jdbc:postgres",
    ":jdbc:openmldb",
    ":aerospike",
    ":elastic-search",
    ":kafka-connect-adaptor-nar",
    ":hbase",
    ":hdfs3",
    ":file",
    ":canal",
    ":netty",
    ":mongo",
    ":debezium:mysql",
    ":debezium:postgres",
    ":debezium:oracle",
    ":debezium:mssql",
    ":debezium:mongodb",
    ":influxdb",
    ":redis",
    ":solr",
    ":dynamodb",
    ":alluxio",
    ":azure-data-explorer",
)

val ioDistDir by tasks.registering(Sync::class) {
    destinationDir = layout.buildDirectory.dir("apache-pulsar-io-connectors-${pulsarConnectorsVersion}-bin").get().asFile

    from(rootDir.resolve("LICENSE")) {
        into(".")
    }
    from("src/assemble/README") {
        into(".")
    }

    // Collect NAR files from each connector project's build/libs directory
    connectorNarProjects.forEach { projectPath ->
        val narDir = project(projectPath).layout.buildDirectory.dir("libs")
        from(narDir) {
            into(".")
            include("*.nar")
        }
        dependsOn("${projectPath}:nar")
    }
}

tasks.named("assemble") {
    dependsOn(ioDistDir)
}
