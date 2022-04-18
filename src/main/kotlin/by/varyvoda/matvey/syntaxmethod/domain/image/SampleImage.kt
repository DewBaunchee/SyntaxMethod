package by.varyvoda.matvey.syntaxmethod.domain.image

import by.varyvoda.matvey.syntaxmethod.domain.Scale
import by.varyvoda.matvey.syntaxmethod.domain.Vector
import java.awt.image.BufferedImage
import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min
import javax.imageio.ImageIO
import kotlin.math.round

class SampleImage(val pixels: List<List<Int>>) {

    val width = pixels.elementAtOrElse(0) { listOf() }.size
    val height = pixels.size

    companion object {

        fun fromBinary(file: File): SampleImage {
            return SampleImage(file.readLines().map { line -> line.split(" ").map { it.toInt() } })
        }

        fun fromImage(file: File): SampleImage {
            return fromBufferedImage(ImageIO.read(file))
        }

        private fun fromBufferedImage(image: BufferedImage): SampleImage {
            val pixels = MutableList(image.height) { MutableList(image.width) { 0 } }
            for (row in pixels.indices) {
                for (col in pixels[row].indices) {
                    pixels[row][col] = if (image.getRGB(col, row) == -16777216) 1 else 0
                }
            }
            return SampleImage(pixels)
        }

        fun empty(height: Int, width: Int): SampleImage {
            return SampleImage(MutableList(height) { MutableList(width) { 0 } })
        }
    }

    fun trim(): SampleImage {
        if (width == 0 || height == 0) return this

        val top = getTop()
        if (top == -1) return SampleImage(emptyList())
        val right = getRight()
        val bottom = getBottom()
        val left = getLeft()
        val trimmedPixels = MutableList(bottom - top + 1) { row ->
            MutableList(right - left + 1) { col ->
                pixels[top + row][left + col]
            }
        }
        return SampleImage(trimmedPixels)
    }

    fun isEmpty(): Boolean {
        return trim().width == 0
    }

    fun sliceHeight(offset: Int, height: Int): SampleImage {
        if (offset >= pixels.size) return SampleImage(emptyList())
        return SampleImage(pixels.subList(offset, min(offset + height, pixels.size)))
    }

    fun sliceWidth(offset: Int, width: Int): SampleImage {
        if(height == 0 || this.width == 0) return SampleImage(emptyList())
        return SampleImage(pixels.map{row -> row.subList(offset, min(offset + width, width))})
    }

    fun scaleTo(rows: Int, cols: Int): SampleImage {
        if (width == 0 || height == 0) return empty(rows, cols)

        val lowestPixel = max(rows, height) - 1
        val rowsScale = Scale(0, lowestPixel, 0, min(rows, height) - 1)
        val rightestPixel = max(cols, width) - 1
        val colsScale = Scale(0, rightestPixel, 0, min(cols, width) - 1)

        return scale(rowsScale, colsScale, rows, cols)
    }

    fun scaleByMin(rows: Int, cols: Int): SampleImage {
        if (width == 0 || height == 0) return empty(rows, cols)

        val lowestPixel = max(rows, height) - 1
        val rowsScale = Scale(0, lowestPixel, 0, min(rows, height) - 1)
        val rightestPixel = max(cols, width) - 1
        val colsScale = Scale(0, rightestPixel, 0, min(cols, width) - 1)

        val minScale = if (rowsScale.compare(colsScale) == 1) rowsScale else colsScale

        return scale(minScale, minScale, rows, cols)
    }

    private fun scale(rowsScale: Scale, colsScale: Scale, rows: Int, cols: Int): SampleImage {
        val lowestPixel = max(rows, height) - 1
        val scaledPixels = MutableList(rows) { MutableList(cols) { mutableListOf(1, 0) } }

        for (row in 0..lowestPixel) {
            val rightestPixel = max(cols, width) - 1

            for (col in 0..rightestPixel) {
                scaledPixels[
                        if (rows - 1 == lowestPixel)
                            row
                        else
                            rowsScale.scale(row)
                ][
                        if (cols - 1 == rightestPixel)
                            col
                        else
                            colsScale.scale(col)
                ][
                        pixels[
                                if (height - 1 == lowestPixel)
                                    row
                                else
                                    rowsScale.scale(row)
                        ][
                                if (width - 1 == rightestPixel)
                                    col
                                else
                                    colsScale.scale(col)
                        ]
                ]++
            }
        }

        return SampleImage(scaledPixels.map { row ->
            row.map { pixel ->
                round(pixel[1].toDouble() / (pixel[0] + pixel[1]) + 0.1).toInt()
            }
        })
    }

    fun getVector(): Vector {
        return Vector(values = pixels.flatten().toIntArray())
    }

    fun normalize(rows: Int, cols: Int): SampleImage {
        return trim().scaleTo(rows, cols)
    }

    fun getTop(): Int {
        for (row in pixels.indices) {
            for (col in pixels[row].indices) {
                if (pixels[row][col] > 0) {
                    return row
                }
            }
        }
        return -1
    }

    fun getRight(): Int {
        for (col in pixels[0].indices.reversed()) {
            for (row in pixels.indices) {
                if (pixels[row][col] > 0) {
                    return col
                }
            }
        }
        return -1
    }

    fun getBottom(): Int {
        for (row in pixels.indices.reversed()) {
            for (col in pixels[row].indices) {
                if (pixels[row][col] > 0) {
                    return row
                }
            }
        }
        return -1
    }

    fun getLeft(): Int {
        for (col in pixels[0].indices) {
            for (row in pixels.indices) {
                if (pixels[row][col] > 0) {
                    return col
                }
            }
        }
        return -1
    }

    override fun toString(): String {
        if(height == 0 || width == 0) return ""
        return pixels.map { row ->
            row.map { pixel ->
                if (pixel == 1) "#" else "-"
            }.reduce { acc, s -> acc + s }
        }.reduce { acc, s -> acc + "\n" + s }.trim()
    }
}

