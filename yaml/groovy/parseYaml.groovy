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
  def Yaml yaml = new Yaml()
  def Map yamlMap = yaml.load((yamlPath as File).text)
  printStages(yamlMap)
}

printYaml("./test.yaml")