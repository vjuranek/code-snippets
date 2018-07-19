#!/usr/bin/env groovy

@Grab('org.yaml:snakeyaml:1.21')
import org.yaml.snakeyaml.Yaml

def printJob(Map job) {
  println("\tJob name: ${job.name}")
  println("\tParameters:")
  job.parameters.each {
    println("\t\t${it.key} -> ${it.value}")
  }
  println("----------------------")
} 

def printStages(Map yaml) {
  yaml.stages.each { s ->
    println("Stage name: ${s.name}")
    println("Jobs:")
    s.jobs.each { j ->
      printJob(j)
    }
    println("======================")
  }
}

def printYaml(String yamlPath) {
  InputStream yamlFileIn = new FileInputStream(new File(yamlPath));
  Yaml yaml = new Yaml()
  Map yamlMap = yaml.load(yamlFileIn)
  printStages(yamlMap)
}

printYaml("./test.yaml")