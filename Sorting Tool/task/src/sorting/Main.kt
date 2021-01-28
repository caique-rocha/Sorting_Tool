package sorting

import java.io.File
import java.util.*

fun main(args: Array<String>) {

    val scanner = Scanner(System.`in`)
    val inputs = mutableListOf<Any>()
    val countInputs: MutableMap<Any, Int>

    val dataType = args[args.indexOf("-dataType") + 1]

    if (args.contains("-sortingType") && !args.contains("natural") && !args.contains("byCount")) {
        println("No sorting type defined!")
        return
    }

    if (args.contains("-dataType") && !args.contains("long") && !args.contains("line") && !args.contains("word")) {
        println("No data type defined!")
        return
    }

    if (!args.contains("-sortingType") && !args.contains("-dataType")) {

        args.forEach {
            println("\"$it\" is not a valid parameter. It will be skipped.")
        }
        return
    }

    args.forEach {
        if (it != "-sortingType" && it != "-dataType"  && it != "long" &&
            it != "line"  && it != "word" &&
            it != "byCount"  && it != "natural" &&
            it != "-inputFile"  && it != "-outputFile") {

            println("\"-$it\" is not a valid parameter. It will be skipped.")
        }
    }

    when (dataType) {

        "line" -> {

            if (args.contains("-inputFile")) {

                File(args[args.indexOf("-inputFile") + 1]).forEachLine { inputs.add(it) }

            } else {

                while (scanner.hasNextLine()) {

                    inputs.add(scanner.nextLine())
                }
            }

        } else -> {

            if (args.contains("-inputFile")) {

                File(args[args.indexOf("-inputFile") + 1]).forEachLine { inputs.add(it) }

            } else {

                while (scanner.hasNext()) {

                    val next = scanner.next()
                    if (dataType == "long" && !next.toString().matches(Regex("-?\\d+"))) {

                        println("\"$next\" is not a long. It will be skipped.")
                        continue
                    }
                    inputs.add(next)
                }
            }
        }
    }


    if (args.contains("-sortingType") && args.contains("byCount")) {


        when (dataType) {

            "long" -> {


                inputs.sortBy { it.toString().toInt() }
                countInputs = countOccurrences(inputs)

                val sortLong = countInputs.toList().sortedBy { (key, _) -> key.toString().toInt()}.toMap()
                    .toList().sortedBy {(_, value) -> value}.toMap()

                if (args.contains("-outputFile")) {

                    val outputFile = File(args[args.indexOf("-outputFile") + 1])
                    outputFile.appendText("Total numbers: ${inputs.size}.\n")
                    sortLong.forEach { outputFile.appendText("${it.key}: ${it.value} time(s), ${getPercent(inputs
                        .size, it.value)}%\n") }
                } else {

                    println("Total numbers: ${inputs.size}.")
                    sortLong.forEach { println("${it.key}: ${it.value} time(s), ${getPercent(inputs.size, it.value)}%")  }
                }

            }
            else -> {

                countInputs = countOccurrences(inputs)
                val sort = countInputs.toList().sortedBy { (key, _) -> key.toString()}.toMap()
                    .toList().sortedBy {(_, value) -> value.toInt()}.toMap()


                if (args.contains("-outputFile")) {

                    val outputFile = File(args[args.indexOf("-outputFile") + 1])
                    outputFile.appendText("Total word: ${inputs.size}.\n")
                    sort.forEach { outputFile.appendText("${it.key}: ${it.value} time(s), ${getPercent(inputs
                        .size, it.value)}%\n")  }
                } else {

                    println("Total word: ${inputs.size}.")
                    sort.forEach { println("${it.key}: ${it.value} time(s), ${getPercent(inputs.size, it.value)}%")  }
                }
            }
        }
    } else {

        when (dataType) {

            "long" -> {

                inputs.sortBy { it.toString().toInt() }

                if (args.contains("-outputFile")) {

                    val outputFile = File(args[args.indexOf("-outputFile") + 1])
                    outputFile.appendText("Total numbers: ${inputs.size}.")
                    inputs.forEach { outputFile.appendText("$it ") }
                } else {

                    println("Total numbers: ${inputs.size}.")
                    inputs.forEach { print("$it ") }
                }
            }
        }
    }
}

fun getPercent(total: Int, part: Int): Int {

    return (part / total.toFloat() * 100).toInt()
}

fun countOccurrences(list: MutableList<Any>): MutableMap<Any, Int> {

    val frequencyMap: MutableMap<Any, Int> = HashMap()

    for (s in list) {
        var count = frequencyMap[s]
        if (count == null) count = 0
        frequencyMap[s] = count + 1
    }

    return frequencyMap
}