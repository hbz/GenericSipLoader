[![Codacy Badge](https://app.codacy.com/project/badge/Grade/b17416e2c3ec4ecba6e9353571219601)](https://app.codacy.com/gh/hbz/GenericSipLoader/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![build](https://github.com/hbz/GenericSipLoader/actions/workflows/maven.yml/badge.svg)](https://github.com/hbz/GenericSipLoader/actions)
[![Maven Package](https://github.com/hbz/GenericSipLoader/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/hbz/GenericSipLoader/actions/workflows/maven-publish.yml)

# About #

## About the GenericSiPLoader ##

The GenericSipLoader is a lightweight tool for uploading submission to different Repository-APIs.
It's thought to facilitate upload of zip-based Containers or whole directories as single submission.

The GenericSipLoader therefore provides
- a Zip-Extraction Class
- a File-Scanner for identifying metadata files and content files via string-pattern
- a Jersey REST-Client for Fedora Commons 3.8
- a Jersey REST-Client for to.science (not implemented yet)

## License ##

GenericSipLoader is licensed under [Apache License 2.0](LICENSE)

## Prerequisites ##

- OpenJDK 1.8 or above
- Maven 3.x or so for integration and deployment

## Installation ##

No installation required. Can be used as library within other Software Modules.

## Usage ##

- Either download latest release of genericSipLoader.jar
- Or clone source code via `git clone` and run `mvn clean test compile assembly:single`

Edit properties-Files with your settings

Run:

`java -jar genericSipLoader.jar [target] [start path] [user] [password]`

