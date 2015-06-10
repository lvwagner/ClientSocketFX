/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsocketfx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author wagner
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button btnConectar;

    @FXML
    private Button btnEnviar;

    @FXML
    private Label lblCliente;

    @FXML
    private TextField tf_message;

    @FXML
    private TextArea txArea;

    @FXML
    private TextField tf_nick;

    Socket socket;
    InputStream inStream;
    InputStreamReader inReader;
    BufferedReader bufReader;
    PrintStream printStream;
    PrintWriter pWriter;
    Thread trd;

    @FXML
    void enviarMenssagem(ActionEvent event) {
        printStream.println(tf_message.getText());
        printStream.flush();
        tf_message.setText("");
    }

    @FXML
    void logarNoChat(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            System.out.println("client online");
            socket = new Socket("localhost", 2222);
            printStream = new PrintStream(socket.getOutputStream());
            inStream = socket.getInputStream();
            inReader = new InputStreamReader(inStream);
            bufReader = new BufferedReader(inReader);

            trd = new Thread(() -> {
                while (true) {
                    try {
                        String msg = bufReader.readLine();
                        txArea.appendText("-> " + msg + "\n");
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            trd.start();
        } catch (Exception e) {
        }
    }
}
