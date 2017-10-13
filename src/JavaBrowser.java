import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.embed.swing.SwingNode;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;

import java.awt.*;
import java.io.IOException;
import java.net.IDN;





public class JavaBrowser extends Application implements HyperlinkListener{

    private TextField urlField;
    private BorderPane mainPane, urlPane;
    private Label statLabel, urlIn;

    private JEditorPane editorPane;
    private SwingNode editorNode;



    public JavaBrowser() {
        mainPane = new BorderPane();
        urlField = new TextField(" ");
        urlPane = new BorderPane();
        statLabel = new Label("");
        urlIn = new Label("URL: ");
        editorPane = new JEditorPane();
        editorNode = new SwingNode();
        editorNode.setContent(editorPane);

    }

    public void start(Stage browserStage) {

        urlPane.setLeft(urlIn);
        urlPane.setCenter(urlField);
        urlIn.setTextFill(javafx.scene.paint.Color.WHITE);

        mainPane.setStyle("-fx-background-color: linear-gradient(#4d4d4e,#0a0a0a)");
        editorPane.setBackground(Color.GRAY);

        mainPane.setTop(urlPane);
        mainPane.setCenter(editorNode);
        mainPane.setBottom(statLabel);



        EventHandler<KeyEvent> keyPressListener = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyPressed) {
                try {
                    if (keyPressed.getCode() == KeyCode.ENTER) {
                        String inputURL = urlField.getText().toLowerCase();

                        if (inputURL.startsWith("http://")) {
                            inputURL = inputURL.substring(7);
                        }
                        editorPane.setPage("http://" + IDN.toASCII(inputURL));
                    }
                } catch (IOException ix) {
                    ix.printStackTrace();
                }
            }
        };

        urlField.addEventHandler(KeyEvent.KEY_PRESSED,keyPressListener);


        browserStage.setScene(new Scene(mainPane,500,400));
        browserStage.show();

    }

    public void hyperlinkUpdate(HyperlinkEvent eventUpdate) {
        HyperlinkEvent.EventType eventType = eventUpdate.getEventType();

        if (eventType == HyperlinkEvent.EventType.ENTERED) {
            statLabel.setText(eventUpdate.getURL().toString());
        }
        else if (eventType == HyperlinkEvent.EventType.EXITED) {
            statLabel.setText(" ");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
