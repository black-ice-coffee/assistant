package com.assistant

import scala.util.control.Breaks._
import collection.mutable._

class TextRank {
  def splitSentences(content: String): Array[String] = {
    val temp = content.replaceAll("\n", ". ").replaceAll("\\n", "")
    return temp.split("\\.").map(str => str.trim).filter(str => !str.startsWith("Ảnh:"))
  }

  def splitParagraphs(content: String): Array[String] = {
    return content.split("\n")
  }

  def sentenceIntersec(sent1: String, sent2: String): Double = {
    val s1 = sent1.split(' ')
    val s2 = sent2.split(' ')
    if(s1.length + s2.length == 0){
      return 0
    }

    return s1.intersect(s2).length/((s1.length + s2.length)/2.0)
  }

  def sentenceRank(content: String): Map[String, Double] ={
    val sentences = splitSentences(content)
    val n = sentences.length
    val si = Array.ofDim[Double](n, n)
    for(i <- 0 to n-1 by 1){
      for(j <- 0 to n - 1 by 1){
        breakable{
          if(i == j){
            break
          }
          si(i)(j) = sentenceIntersec(sentences(i), sentences(j))
        }
      }
    }

    val sentenceScores:Map[String, Double] = Map()
    for(i <- 0 to n-1 by 1){
      var score = 0.0;
      for(j <- 0 to n-1 by 1){
        breakable{
          if( i==j)
            break
          score = score + si(i)(j)
        }
      }
      sentenceScores(sentences(i)) = score
    }
    return sentenceScores
  }

  def getBestSentene(paragraph: String, sentenceScores: Map[String, Double]): String ={
    val sentences = splitSentences(paragraph)
    val n = sentences.length
    if(n < 2)
      return ""
    var bestSentence = ""
    var max = 0.0
    for(i <- 0 to n-1 by 1){
      var score = sentenceScores.get(sentences(i))
      if(score != None && score.get > max){
        max = sentenceScores(sentences(i))
        bestSentence = sentences(i)
      }
    }

    return bestSentence
  }

  def summary(content: String): String ={
    val paragraphs = splitParagraphs(content)
    val sentenceScore = sentenceRank(content)

    var summary = ArrayBuffer[String]()
    for(p <- paragraphs){
      val bestSentence = getBestSentene(p, sentenceScore)
      if(!bestSentence.isEmpty){
        summary.append(bestSentence)
      }
    }

    return summary.mkString(".")
  }
}
