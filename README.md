<p align="center">
    <img alt="Logo" src="./img/TinyConfiguration.png">
</p>

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Example](#example)
- [Build, download & changelog](#build-download--changelog)
- [What's next?](#whats-next)
- [Feedback](#feedback)
- [License](#license)

## Introduction
Easily create and manage configuration files for your programs, 
check that all properties are present and formally valid. 
TinyConfiguration is a software library that takes charge of this work 
and tries to be simple and effective at the same time.

<br>

<p align="center">
    <b>Screenshot</b>
</p>

<br>

<p align="center">
    <img src="./img/TinyConfiguration%20-%20Sample.png" alt="TinyConfiguration sample">
</p>

## Features
A few of the things you can do with TinyConfiguration:

* **Read**, **write** and **delete** custom configuration file (even async)
* Easily **search and retrieve** values
* Sort by **group** every property
* Set **listeners** on properties changes, configuration saving and deleting
* Escape **special characters**

## Example

This tutorial covers most of the methods available in the library, 
for further information check the javadoc.

````java

    public class Main {
        public static void main(String[] args){
        
      // Creating a new instance
        Configuration cfg = new Configuration.Builder().
                setFilename("setting.cfg"). // FILE-NAME + .EXT
                setPathname("./"). // PATH-NAME ONLY, NO FILE-NAME
                build();


        // For further information check the javadoc

        }
    }
````

## Build, download & changelog

>This library **should not** be used in a production environment.  
>TinyConfiguration is currently in an ++***alpha state***++ and any new update may break backward compatibility


#### Building the library
TinyConfiguration requires Maven 3 and JDK 8.<br>
The generated JARs is compatible only with Java 8 and higher.

Build command:

````
mvn clean package
````

The " ***target*** " directory with javadoc documentation 
and all JARs will be available at the root directory.

#### Download

##### Maven

To use the package, you need to use following Maven dependency:


````
<dependency>
  <groupId>io.github.mrsnix</groupId>
  <artifactId>tiny-configuration</artifactId>
  <version>0.0.1</version>
</dependency>
````
Package has no external dependencies, except for testing (which uses JUnit).

##### Non-Maven

You can get the latest precompiled .jar files 
from [this page](https://github.com/MrSnix/TinyConfiguration/releases).


#### Tracking changes

The full changelog is available at [CHANGELOG.md](CHANGELOG.md),
meanwhile the following table is a really short feature-list for each
release.

<br>

| Version |                      Changelog                           |
|:-------:|:--------------------------------------------------------:|
|   0.1   |                   Initial release                        |
|   0.2   |   New arrays methods, property groups and exports mode   |

## What's next?

**Version:** 0.3

**Release date:** [ *???* ]

**Features:**
- ?
- ?
- ?

The full changelog is available at [CHANGELOG.md](CHANGELOG.md)

## Feedback
If there's anything you'd like to chat about or 
you want to send me feedback about this project,
<br>you can reach me on my [e-mail](mailto:baittiner.giuseppe.dev@gmail.com), 
***feature requests are always welcome***.

## License
Copyright 2019 Giuseppe Baittiner

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
