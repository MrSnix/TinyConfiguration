<p align="center">
    <img src="./img/TinyConfiguration.png" alt="TinyConfiguration">
</p>

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Example](#example)
- [Build & Changelog](#build--changelog)
- [Feedback](#feedback)
- [Acknowledgments](#acknowledgments)

## Introduction
Easily create and manage configuration files for your programs, 
check that all properties are present and formally valid. 
TinyConfiguration is a software library that takes charge of this work 
and tries to be simple and effective at the same time.

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
* Set **listeners** on properties changes, configuration saving and deleting
* Apply **description** on every property
* Set **strict** or **tolerant** reading **mode**
* Easily **search and retrieve** values
* Escape **special characters** by default

## Example

This tutorial covers most of the methods available in the library, 
for further information, check the javadoc.

```java
public class Main {
    
    public static void main(String[] args){
        
      // Creating a new instance
        Configuration cfg = new Configuration.Builder().
                setFilename("setting.cfg"). // FILE-NAME + .EXT
                setPathname("./"). // PATH-NAME ONLY, NO FILE-NAME
                build();

        /* The next step will be really important
         * because it defines which properties ConfigurationIO.read() can consider valid!
         * Please check configuration.setPolicy() for further information */

        /* These properties are invented just as example,
         * you can put whatever your software needs */

        /* The following method is overloaded as follow:
         * 1) cfg.put(String key, String value); [ RECOMMENDED ]
         * 2) cfg.put(String key, String value, String description); [ RECOMMENDED ]
         * 3) cfg.put(Property p); [ NOT RECOMMENDED ]*/

        cfg.put("CFG_VERSION", "0.1", "This value is used internally, please do not touch it!");
        cfg.put("LANGUAGE", "EN");
        cfg.put("FULLSCREEN", "FALSE");
        cfg.put("MUSIC", "0.8");
        cfg.put("RENDERING_DISTANCE", "100");
        cfg.put("V-SYNC", "FALSE");

        // Checking existence "./setting.cfg" file
        if (ConfigurationIO.exist(cfg)) {

            // Let's import the properties
            try {
                // Reading and updating values inside configuration instance
                ConfigurationIO.read(cfg);
            } catch (IOException e) {
                // You can catch the exception as you wish
                e.printStackTrace();
            }

        } else {

            /* Configuration does not exists,
             * we will use the default properties set initially */

            // But we want to write on the disk, so the user will be able to change them in the future
            try {
                ConfigurationIO.write(cfg);
            } catch (IOException e) {
                // You can catch the exception as you wish
                e.printStackTrace();
            }

        }

        /* At this point, if you need to retrieve any property information you can use cfg.get(String key)
         * ex. cfg.get("LANGUAGE").asString(); | cfg.get("MUSIC").asFloat();
         * IMPORTANT:
         * cfg.get() will return a "Property" object and you can use it to return any kind of value */

        // cfg.get("key").as[data-type];
        
        cfg.get("CFG_VERSION").asString();
        cfg.get("FULLSCREEN").asBoolean();
        cfg.get("RENDERING_DISTANCE").asInt();
        cfg.get("MUSIC").asFloat();

        /* Now, you may want to modify the properties inside your program when something happen
         * (ex. User press "Apply Fullscreen" then "Save" button)
         * How do you do it ? Really simple, use cfg.put(key, value) again, it will replace the old data */

        cfg.put("FULLSCREEN", "TRUE");

        /* Then, after the user has confirmed, you may want to listen for the configuration change,
         *  so your software can run a custom method to make the window full-screen...
         *  Just use this method cfg.addListener(PropertyListener, key, () -> {}) and ... that's it */

        cfg.addListener(PropertyListener.Type.ON_PROPERTY_UPDATE, "FULLSCREEN", property -> {

            /* You can do whatever you want inside this function
             *  It will run when cfg.put will update FULLSCREEN property*/

            /* So, for example you may do something like:
             * window.fullScreen(property.value().asBoolean()); */
        });

        // This will now trigger the listener
        cfg.put("FULLSCREEN", "FALSE");

        /* In the end, it's time to save our new configuration instance, so next time
         * the user will be able to load his preferences... */
        try {
            ConfigurationIO.write(cfg);
        } catch (IOException e) {
            // You can catch the exception as you wish
            e.printStackTrace();
        }


        // For further information check the javadoc

    }
}
```

## Build & Changelog

#### Building the library
TinyConfiguration requires Maven 3 and JDK 8.<br>
The generated JARs is compatible only with Java 8 and higher.

Build command:

````
mvn clean install
````

The " ***target*** " directory with javadoc documentation 
and all JARs will be available at the root directory.

#### Tracking changes

The full changelog is available at [CHANGELOG.md](CHANGELOG.md),
meanwhile the following table is a really short feature-list for each
release.

<br>

| Version |                        Description                        |
|:-------:|:---------------------------------------------------------:|
|   0.1   |                      Initial release                      |


## Feedback
If there's anything you'd like to chat about or 
you want to send me feedback about this project,
<br>you can reach me on my [e-mail](baittiner.giuseppe.dev@gmail.com), 
***feature requests are always welcome***.

## Acknowledgments
Copyright 2019 Giuseppe Baittiner

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.