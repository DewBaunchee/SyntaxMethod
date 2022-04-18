package by.varyvoda.matvey.syntaxmethod

import by.varyvoda.matvey.syntaxmethod.domain.drawing.Plot
import by.varyvoda.matvey.syntaxmethod.domain.image.SampleImage
import by.varyvoda.matvey.syntaxmethod.domain.learning.Matcher
import javafx.beans.Observable
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.geometry.Insets
import javafx.scene.chart.BarChart
import javafx.scene.chart.XYChart
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color


const val cols = 3 * 1
const val rows = 5 * cols / 3

val activeBackground = Background(BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))
val nonActiveBackground = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))

class HelloController {

    @FXML
    private lateinit var grid: GridPane

    @FXML
    private lateinit var chart: BarChart<String, Double>

    private val matcher: Matcher = Matcher()

    private val plot = Plot(rows, cols)

    private val bars = FXCollections.observableList(mutableListOf<XYChart.Series<String, Double>>())

    private var filterPlotChanges = false

    @FXML
    fun initialize() {
        grid.onMouseReleased = EventHandler { evaluateClasses() }
        for (col in 0 until cols) {
            for (row in 0 until rows) {
                val pane = Pane()
                pane.background = nonActiveBackground

                plot.observablePixels[row][col].addListener { _, _, new ->
                    pane.background = if (new == 1) activeBackground else nonActiveBackground
                }

                val handler = EventHandler { mouseEvent: MouseEvent ->
                    plot.observablePixels[row][col].value = if (mouseEvent.isShiftDown) 0 else 1
                }
                pane.onMouseDragOver = handler
                pane.onMouseClicked = EventHandler { handler.handle(it); evaluateClasses() }

                grid.widthProperty().addListener { _, _, new ->
                    val width = new.toDouble() / cols
                    pane.minWidth = width
                    pane.maxWidth = width
                }

                grid.heightProperty().addListener { _, _, new ->
                    val height = new.toDouble() / rows
                    pane.minHeight = height
                    pane.maxHeight = height
                }

                grid.add(pane, col, row)
            }
        }

        val plotChangeWrapper = { plotChanger: Runnable ->
            filterPlotChanges = true
            plotChanger.run()
            filterPlotChanges = false
            evaluateClasses()
        }

        grid.sceneProperty().addListener { _, _, scene ->
            scene.setOnKeyPressed { keyEvent ->
                if (!keyEvent.isShiftDown) return@setOnKeyPressed

                if (keyEvent.code == KeyCode.R) {
                    plotChangeWrapper {
                        plot.clear()
                    }
                }
                if (keyEvent.code == KeyCode.S) {
                    plotChangeWrapper {
                        plot.setPlot(SampleImage(plot.getIntPlot()).scaleByMin(rows, cols).pixels)
                    }
                }
                if (keyEvent.code == KeyCode.D) {
                    plotChangeWrapper {
                        plot.setPlot(SampleImage(plot.getIntPlot()).normalize(5, 3).pixels)
                    }
                }
            }
        }

        chart.data = bars
        bars.addListener { _: Observable ->
            bars.stream().forEach {
                val max = it.data.stream().max(Comparator.comparing { data -> data.yValue })
                max.ifPresent { maxValue ->
                    maxValue.nodeProperty()
                        .addListener { _, _, newValue ->
                            if (newValue != null)
                                newValue.style = "-fx-bar-fill: #3983E9;"
                        }
                }
            }
        }
    }

    private fun evaluateClasses() {
        bars.clear()
        bars.add(
            matcher.match(SampleImage(plot.getIntPlot()).trim())
                .entries.stream()
                .collect(
                    { XYChart.Series<String, Double>() },
                    { acc, entry -> acc.data.add(XYChart.Data(entry.key.toString(), entry.value)) },
                    { a, b -> a.data.addAll(b.data) }
                )
        )
    }
}
