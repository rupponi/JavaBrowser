import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.embed.swing.SwingNode;

import javax.swing.*;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;
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

        mainPane.setTop(urlPane);
        mainPane.setCenter(editorNode);
        mainPane.setBottom(statLabel);


        urlField.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent urlSearch) {
                String inputURL = urlSearch.getSource().toString().toLowerCase();
                try {
                    if (inputURL.startsWith("http://")) {
                        inputURL = inputURL.substring(7);
                        editorPane.setPage("http://" + IDN.toASCII(inputURL));
                    }
                } catch (IOException ix) {
                    ix.printStackTrace();
                }
            }
        });

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
