[![Codacy Badge](https://app.codacy.com/project/badge/Grade/b17416e2c3ec4ecba6e9353571219601)](https://app.codacy.com/gh/hbz/GenericSipLoader/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![build](https://github.com/hbz/GenericSipLoader/actions/workflows/maven.yml/badge.svg)](https://github.com/hbz/GenericSipLoader/actions)
[![Maven Package](https://github.com/hbz/GenericSipLoader/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/hbz/GenericSipLoader/actions/workflows/maven-publish.yml)

# About #

## About the GenericSiPLoader ##

The GenericSipLoader is a lightweight tool for uploading submissions to different Repository-APIs.
It's thought to facilitate upload of zip-based Containers or whole directories as single submission.

The tool makes extensive use of the Jersey and Jackson Libraries. It also uses java interfaces as part of 
meta data schema modelling in order to simplify code enhancements by using conventions.

The GenericSipLoader therefore provides 

- a Zip-Extraction Class
- a File-Scanner for identifying metadata files and content files via string-pattern
- a Jersey REST-Client for Fedora Commons 3.8
- a Jersey REST-Client for the Toolbox Open Science (to.science)

## License ##

GenericSipLoader is licensed under [Apache License 2.0](LICENSE)

## Prerequisites ##

- OpenJDK 1.8 or above
- Maven 3.x or so for integration and deployment

## Installation ##

No installation required. Can be used as library within other Software Modules.

## Usage ##

- Either download latest release of genericSipLoader.jar
- Or clone source code via git clone and run `mvn clean test compile assembly:single`

### Use GUI ###

By simply calling

`java -jar genericSipLoader.jar`

you will get the graphic user interface. Default settings will be loaded an can be adopted to your setting by integrated editing option.

### Use Console Client ### 

By calling:

`java -jar genericSipLoader.jar cli [target] [start path] [user] [password]`

you will get the Console version of the GenericSipLoader, i.E. for usage with crontab.

* `cli` = selects the console version

* `target` = defines the loading target, either danrw or ktbl

* `start path` = defines the parent directory where the tool finds the *.zip file to upload

* `user, password` = your credentials  
 
