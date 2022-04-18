package by.varyvoda.matvey.syntaxmethod.domain.learning

enum class TokenType(val symbol: String) {
    LEFT_VERTICAL("l"), RIGHT_VERTICAL("r"), HORIZONTAL("h")
}
private val numbers: Map<Int, List<List<TokenType>>> = mapOf(
    Pair(
        0, listOf(
            listOf(
                TokenType.HORIZONTAL,
                TokenType.LEFT_VERTICAL,
                TokenType.RIGHT_VERTICAL,
                TokenType.HORIZONTAL,
            )
        )
    ),
    Pair(
        1, listOf(
            listOf(
                TokenType.LEFT_VERTICAL,
            ),
            listOf(
                TokenType.RIGHT_VERTICAL,
            )
        )
    ),
    Pair(
        2, listOf(
            listOf(
                TokenType.HORIZONTAL,
                TokenType.RIGHT_VERTICAL,
                TokenType.HORIZONTAL,
                TokenType.LEFT_VERTICAL,
                TokenType.HORIZONTAL,
            )
        )
    ),
    Pair(
        3, listOf(
            listOf(
                TokenType.RIGHT_VERTICAL,
                TokenType.HORIZONTAL,
                TokenType.RIGHT_VERTICAL,
                TokenType.HORIZONTAL,
            ),
            listOf(
                TokenType.HORIZONTAL,
                TokenType.RIGHT_VERTICAL,
                TokenType.HORIZONTAL,
                TokenType.RIGHT_VERTICAL,
                TokenType.HORIZONTAL,
            ),
        )
    ),
    Pair(
        4, listOf(
            listOf(
                TokenType.LEFT_VERTICAL,
                TokenType.RIGHT_VERTICAL,
                TokenType.HORIZONTAL,
                TokenType.RIGHT_VERTICAL,
            ),
            listOf(
                TokenType.LEFT_VERTICAL,
                TokenType.HORIZONTAL,
                TokenType.RIGHT_VERTICAL,
            ),
        )
    ),
    Pair(
        5, listOf(
            listOf(
                TokenType.HORIZONTAL,
                TokenType.LEFT_VERTICAL,
                TokenType.HORIZONTAL,
                TokenType.RIGHT_VERTICAL,
                TokenType.HORIZONTAL,
            )
        )
    ),
    Pair(
        6, listOf(
            listOf(
                TokenType.HORIZONTAL,
                TokenType.LEFT_VERTICAL,
                TokenType.HORIZONTAL,
                TokenType.LEFT_VERTICAL,
                TokenType.RIGHT_VERTICAL,
                TokenType.HORIZONTAL,
            ),
            listOf(
                TokenType.LEFT_VERTICAL,
                TokenType.HORIZONTAL,
                TokenType.LEFT_VERTICAL,
                TokenType.RIGHT_VERTICAL,
                TokenType.HORIZONTAL,
            ),
        )
    ),
    Pair(
        7, listOf(
            listOf(
                TokenType.HORIZONTAL,
                TokenType.RIGHT_VERTICAL,
            )
        )
    ),
    Pair(
        8, listOf(
            listOf(
                TokenType.HORIZONTAL,
                TokenType.LEFT_VERTICAL,
                TokenType.RIGHT_VERTICAL,
                TokenType.HORIZONTAL,
                TokenType.LEFT_VERTICAL,
                TokenType.RIGHT_VERTICAL,
                TokenType.HORIZONTAL,
            )
        )
    ),
    Pair(
        9, listOf(
            listOf(
                TokenType.HORIZONTAL,
                TokenType.LEFT_VERTICAL,
                TokenType.RIGHT_VERTICAL,
                TokenType.HORIZONTAL,
                TokenType.RIGHT_VERTICAL,
                TokenType.HORIZONTAL,
            ),
            listOf(
                TokenType.HORIZONTAL,
                TokenType.LEFT_VERTICAL,
                TokenType.RIGHT_VERTICAL,
                TokenType.HORIZONTAL,
                TokenType.RIGHT_VERTICAL,
            ),
        )
    ),
)