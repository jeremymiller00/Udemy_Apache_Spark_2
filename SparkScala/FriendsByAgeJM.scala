
def parseAgeNumfriends(line: String) = {
    val fields = line.split(",")
    val age = fields(2).toInt
    val numFriends = fields(3).toInt
    (age, numFriends)
}

def avgFriendsByAge() {
    // read in the file
    val lines = sc.textFile("fakefriends.csv")
    
    // parse the lines for age and friend count
    val rdd = lines.map(parseAgeNumfriends)

    // set up k / v pair for aggregation
    val prepValues = rdd.mapValues(x => (x, 1))

    // calculate totals and N by age
    val totalsByAge = prepValues.reduceByKey((x,y) => (x._1 + y._1, x._2 + y._2))

    // convert totals and N to avg
    val avgByAge = totalsByAge.mapValues(x => (x._1 / x._2))

    // collect results
    val results = avgByAge.collect()

    // disply results
    results.sorted.foreach(println)
}

avgFriendsByAge()