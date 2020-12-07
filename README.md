<p align="center">
    <img alt="Logo" src="./img/TinyConfiguration.png">
</p>

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Quickstart](#quick-start)
- [Build, download & changelog](#build-download--changelog)
- [Feedback](#feedback)
- [License](#license)

## Introduction
Easily create and manage configuration files for your programs, 
check all properties are present and formally valid. 
TinyConfiguration is a software library that takes charge of this work 
and tries to be simple and effective at the same time.

## Features
A few of the things you can do with TinyConfiguration:

* **Read**, **write** and **delete** custom configuration file (even async)
* Easily **search and retrieve** values
* Execute **validation** on properties values with lambda functions
* Export configuration as **JSON**, **XML**, **YAML** & **CSV**

## Quick-start

### The basics

```java
import org.tinyconfiguration.imp.basic.Configuration;
import org.tinyconfiguration.abc.data.Value;

public class QuickStart {

    public static void main(String[] args){

          // Define your configuration 
          Configuration.Builder b = new Configuration.Builder().
                  setName("ConfigurationTest").
                  setVersion("1.0.0").
                  setPathname("./").
                  setFilename("tiny-configuration.json");

          // Add some properties for your application (as many as you want)
        b.put(new Property.Builder().
                setKey("language").
                setValue("EN").
                setValidator(property -> {
                    // Extract value
                    Value e = property.getValue();
                    // Return as String
                    String data = e.asString();
                    // Test
                    return data.equalsIgnoreCase("EN") || data.equalsIgnoreCase("IT");
                }).
                setDescription("Specifies the language environment for the session").
                build());

          b.put(new Property.Builder().
                  setKey("auto-update").
                  setValue(true).
                  setDescription("Specifies if the application should regularly check for new software releases").
                  build());

          // Build your configuration instance
          Configuration cfg = b.build();

    }
}
```

### I/O

```java

import org.tinyconfiguration.abc.ex.*;
import org.tinyconfiguration.abc.utils.FormatType;
import org.tinyconfiguration.imp.basic.Configuration;

import java.io.IOException;

public class QuickStart {

    public static void main(String[] args){

        // Assume you have a class which returns a Configuration object
        Configuration instance = FooBar.getConfiguration();

        // Does it exists on disk?
        if (!instance.exist()) {

            // If not, let's save it
            try {
                instance.write(FormatType.JSON);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Now, it should exists
            assert(instance.exist());
        }else{
            // Seems like there is already a cfg file, let's read it
            try {
                instance.read(FormatType.JSON);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ConfigurationException e) {
                e.printStackTrace();
            } catch (PropertyException e) {
                e.printStackTrace();
            }

            // If everything is gone well, 
            // the configuration instance now hold the read values
        }

        // Do you want to delete it?
        try {
            instance.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
```

You can find out more on the [wiki](https://github.com/MrSnix/TinyConfiguration/wiki).

## Build, download & changelog

This library **should not** be used in a production environment.  
TinyConfiguration is currently in an ***alpha state***, it's unstable and any new update may break backward compatibility

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
  <version>0.0.3</version>
</dependency>
````

##### Non-Maven

You can get the latest precompiled .jar files 
from [this page](https://github.com/MrSnix/TinyConfiguration/releases).


#### Tracking changes

The full changelog and planned features are available at [CHANGELOG.md](CHANGELOG.md),  
meanwhile the following table is a really short feature-list for each
release.

<br>

| Version |                      Changelog                           |
|:-------:|:--------------------------------------------------------:|
|   0.1   |                   Initial release                        |
|   0.2   |         New arrays methods and export format             |
|         |    Overhauled the entire class and package hierarchy     |
|         |                Rewritten documentation                   |
|   0.3   |                  Simplified I/O methods                  |
|         |                       Bug fixes                          |

## Feedback
If there's anything you'd like to chat about, or 
you want to send me feedback about this project, you can reach me on my [e-mail](mailto:baittiner.giuseppe.dev@gmail.com),
***feature requests are always welcome***.

## License
Copyright 2020 Giuseppe Baittiner

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
