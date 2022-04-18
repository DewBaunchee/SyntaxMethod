package by.varyvoda.matvey.syntaxmethod

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.input.MouseEvent
import javafx.stage.Stage

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("hello-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 450.0, 800.0)

        scene.addEventFilter(MouseEvent.DRAG_DETECTED) {
            scene.startFullDrag()
        }

        stage.title = "Hello!"
        stage.scene = scene

        stage.show()
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}