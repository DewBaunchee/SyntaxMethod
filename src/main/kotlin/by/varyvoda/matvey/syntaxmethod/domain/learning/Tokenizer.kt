package by.varyvoda.matvey.syntaxmethod.domain.learning

import by.varyvoda.matvey.syntaxmethod.domain.image.SampleImage
import kotlin.math.max

class Tokenizer {

    fun imageToString(image: SampleImage): String {
        return stringifyTokens(tokenize(image))
    }

    private fun tokenize(image: SampleImage): List<TokenType> {
        val tokens: MutableList<TokenType> = mutableListOf()
        image.pixels.forEach { row->
            val sliced = SampleImage(listOf(row))
            val trimmed = sliced.trim()
            if(trimmed.isEmpty()) return@forEach

            val slicedLeft = sliced.sliceWidth(0, sliced.width / 2)
            val slicedRight = sliced.sliceWidth(sliced.width / 2, sliced.width)
            val leftEmpty = slicedLeft.isEmpty()
            val rightEmpty = slicedRight.isEmpty()

            if(trimmed.height <  trimmed.width) {
                val trimmedRow = trimmed.pixels[0]
                if(!trimmedRow.contains(0)) {
                    tokens.add(TokenType.HORIZONTAL)
                    return@forEach
                }
            }

            if(!leftEmpty) {
                tokens.add(TokenType.LEFT_VERTICAL)
            }
            if(!rightEmpty) {
                tokens.add(TokenType.RIGHT_VERTICAL)
            }
        }
        return tokens
    }

    private fun stringifyTokens(tokens: List<TokenType>): String {
        if(tokens.isEmpty()) return ""
        return tokens.map { it.symbol }.reduce { acc, symbol -> acc + symbol }
    }
}