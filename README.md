# pete

pete is a tool for computing metrics that characterize the portability of executable service-oriented processes implemented in several process languages. 
It has been used in this revision for calculating the results of the paper "*Measuring the Installability of Service Orchestrations Using the SQuaRE Method*" presented at [SOCA 2013](http://conferences.computer.org/soca/).
A preprint of the paper [is available here](http://www.uni-bamberg.de/pi/bereich/forschung/publikationen/13-08-harrer-lenhard-wirtz/).

[![Build Status](https://travis-ci.org/lenhard/pete.png?branch=master)](https://travis-ci.org/lenhard/pete)

## Quality Characteristics
Pete aims to support the computation of metrics for four quality characteristics which are aligned to the ISO/IEC Systems and software Quality Requirements and Evaluation (SQuaRE) method. These are:
* Direct code portability (implemented)
* Installability and deployability (implemented)
* Replaceability (ongoing)
* Adaptability (ongoing)

## Software Requirements
Pete uses the gradle wrapper, so anything besides Java will be downloaded and installed automatically.
- JDK 1.7.X
  - `JAVA_HOME` should point to the jdk directory
  - `PATH` should include `JAVA_HOME/bin`
  
## Licensing
[MIT license](http://opensource.org/licenses/MIT)

## Usage
Pete can be built, but also executed using gradle.
```bash
$ gradlew run -Pargs="<ARGS>"

# usage: <metric-option> <path-to-files>
```
`<path-to-files>`: Pete parses single files or recursively traverses directory structures, looking for relevant files. Everything that is analyzed in an execution run is aggregated to a result set and written to CSV files, ready to be consumed by MS Excel or R. Pete is forgiving: It tries to analyzes as many files as possible, sometimes depending on the type, but if the analysis for a file fails or nothing relevant is found, that file is just ignored.

`<metric-option>`: Pete allows for the computation of various types of metrics and the files to be analyzed depend on that type. For that reason, the type of metrics to be computed must be stated on execution. Following options are implemented so far:
* `p`: pete computes direct portability metrics, such as weighted elements portability or activity portability, for process files
* `d`: pete computes deployability metrics, such as deployment descriptor size or effort of package construction, for deployment archives
* `i`: pete computes installability metrics for process engines, such as average installation time or installation effort

```bash
# Examples
$ gradlew run -Pargs="-p src/test/resources/Invoke-Empty.bpel" # Compute portability metrics for a process from the test directory
$ gradlew run -Pargs="-i src/test/resources/installability/server" # Compute installability metrics from all files of a specific test directory 
$ gradlew run -Pargs="-i src/test/resources/installability/deployment" # Compute deployability metrics from all files of a specific test directory 
```
Pete comes with tasks for IntelliJ and Eclipse
```bash
# Generate project files 
$ gradlew idea # Generates Intellij IDEA project files
$ gradlew eclipse # Generates Eclipse project files

# Manually purge temporary files
$ gradlew deleteTmpDir # purge temporary files manually
```

## Output

Pete produces three different files that are written to the root directory and can be analyzed by statistical tooling:
- `results.csv`: A list of all metrics of the selected type and their values for the files analyzed. Values are separated with a semicolon, as preferred by MS Office.
- `r-results.csv`: A list of all metrics of the selected type and their values for the files analyzed. Values are separated with a comma, as preferred by R.
- `raw.csv`: A list of raw data from which several metrics of the above metrics can be computed. Values are separated with a semicolon.

## Project Structure

    src/main/java # the main source code
    src/main/r # scripts written in R that consume the CSV file produced by pete and perform several simple analyses of the data
    src/test/java # JUnit tests for pete
    src/test/resources # files that are analyzed during the unit tests


## Authors 

[Joerg Lenhard](http://www.uni-bamberg.de/pi/team/lenhard-joerg/)

## Contribution Guide
Contribution is appreciated! Feel free to open issues. Apart from that, the normal git workflow applies:

- Fork
- Send Pull Request

## Current State
So far, portability and installability metrics and their computation for BPEL processes is supported. Support for the remaining quality characteristics and the process languages Windows Workflow and BPMN 2.0 is planned.