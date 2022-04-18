package by.varyvoda.matvey.syntaxmethod.domain.learning

import by.varyvoda.matvey.syntaxmethod.domain.image.SampleImage

class Matcher {

    private val tokenizer = Tokenizer()

    fun match(image: SampleImage): Map<Int, Double> {
        val string = tokenizer.imageToString(image)
        return patterns.mapValues {
            val result = it.value.matchEntire(string) ?: return@mapValues 0.0;
            return@mapValues result.range.size().toDouble()
        }
    }

    companion object {
        private val patterns: Map<Int, Regex> = mapOf(
            Pair(0, Regex("h(lr)+h")),
            Pair(1, Regex("(l+)|(r+)")),
            Pair(2, Regex("hr+hl+h")),
            Pair(3, Regex("h?r+hr+h")),
            Pair(4, Regex("(lr)+hr+")),
            Pair(5, Regex("hl+hr+h")),
            Pair(6, Regex("h?l+h(lr)+h")),
            Pair(7, Regex("hr+")),
            Pair(8, Regex("h(lr)+h(lr)+h")),
            Pair(9, Regex("h(lr)+hr+h?")),
        )
    }
}

private fun IntRange.size(): Int {
    return last - first
}