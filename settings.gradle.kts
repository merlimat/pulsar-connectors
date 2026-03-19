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

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        maven { url = uri("https://packages.confluent.io/maven/") }
    }
}

rootProject.name = "pulsar-connectors"

// Enforced platform for dependency version management
include("pulsar-dependencies")

// IO connectors
include("aerospike")
include("alluxio")
include("aws")
include("azure-data-explorer")
include("canal")
include("cassandra")
include("dynamodb")
include("elastic-search")
include("file")
include("hbase")
include("hdfs3")
include("http")
include("influxdb")
include("kafka")
include("kafka-connect-adaptor")
include("kafka-connect-adaptor-nar")
include("kinesis")
include("kinesis-kpl-shaded")
include("mongo")
include("netty")
include("nsq")
include("rabbitmq")
include("redis")
include("solr")

// JDBC
include("jdbc")
include("jdbc:core")
project(":jdbc:core").projectDir = file("jdbc/core")
include("jdbc:clickhouse")
project(":jdbc:clickhouse").projectDir = file("jdbc/clickhouse")
include("jdbc:mariadb")
project(":jdbc:mariadb").projectDir = file("jdbc/mariadb")
include("jdbc:openmldb")
project(":jdbc:openmldb").projectDir = file("jdbc/openmldb")
include("jdbc:postgres")
project(":jdbc:postgres").projectDir = file("jdbc/postgres")
include("jdbc:sqlite")
project(":jdbc:sqlite").projectDir = file("jdbc/sqlite")

// Debezium
include("debezium")
include("debezium:core")
project(":debezium:core").projectDir = file("debezium/core")
include("debezium:mongodb")
project(":debezium:mongodb").projectDir = file("debezium/mongodb")
include("debezium:mssql")
project(":debezium:mssql").projectDir = file("debezium/mssql")
include("debezium:mysql")
project(":debezium:mysql").projectDir = file("debezium/mysql")
include("debezium:oracle")
project(":debezium:oracle").projectDir = file("debezium/oracle")
include("debezium:postgres")
project(":debezium:postgres").projectDir = file("debezium/postgres")

// Connector docs (depends on all connectors)
include("docs")

// Distribution
include("distribution:pulsar-io-distribution")
project(":distribution:pulsar-io-distribution").projectDir = file("distribution/io")
